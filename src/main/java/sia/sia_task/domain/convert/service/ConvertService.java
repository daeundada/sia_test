package sia.sia_task.domain.convert.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sia.sia_task.domain.convert.dto.request.ConvertRequestDTO;
import sia.sia_task.domain.convert.entity.ImageMetadata;
import sia.sia_task.domain.convert.repository.ImageMetadataRepository;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ConvertService {

    @Value("${aws.user-name}")
    private String userName;

    @Value("${aws.input-bucket}")
    private String inputBucket;

    @Value("${aws.output-bucket}")
    private String outputBucket;


    private final S3Client s3Client;
    private final ImageMetadataRepository imageMetadataRepository;

    @Transactional
    public String convertSingle(ConvertRequestDTO request) {

        String originalFileName = request.getFileName();

        // 1. 다운로드
        File inputFile = downloadFromS3(inputBucket, originalFileName);

        // 2. sequence 계산
        int sequence = imageMetadataRepository
                .findMaxSequenceByOriginalFileName(originalFileName)
                .orElse(0) + 1;

        // 3. 변환될 파일 이름 생성
        String baseName = originalFileName.replaceAll("(?i)\\.tif{1,2}$", "");
        String convertFileName = baseName + "_to_cog_" + sequence + ".tiff";

        // 4. COG 변환
        File cogFile = convertToCOG(inputFile, convertFileName);

        // 5. 메타데이터 추출 및 저장
        extractAndSaveMetadata(cogFile, originalFileName, convertFileName, sequence);

        // 6. S3 업로드
        String uploadKey = String.format("%s/%s", userName, convertFileName);
        uploadToS3(outputBucket, uploadKey, cogFile);

        return convertFileName;
    }

    public File downloadFromS3(String bucketName,
                               String key) {

        File localFile = new File("/tmp/" + new File(key).getName());

        // 파일이 이미 존재하면 삭제
        if (localFile.exists()) {
            System.out.println("기존 파일 삭제: " + localFile.getAbsolutePath());
            localFile.delete();
        }

        s3Client.getObject(
                GetObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .build(),
                localFile.toPath()
        );

        return localFile;
    }

    public File convertToCOG(File inputFile,
                             String convertFileName) {

        String outputPath = "/tmp/" + convertFileName;
        File outputFile = new File(outputPath);

        try {
            ProcessBuilder pb = new ProcessBuilder(
                    "gdal_translate",
                    "-of", "COG",                  // 출력 형식: COG
                    "-co", "COMPRESS=DEFLATE",     // 압축 방식: DEFLATE (무손실 압축)
                    inputFile.getAbsolutePath(),   // 입력 파일 경로
                    outputFile.getAbsolutePath()   // 출력 파일 경로
            );

            pb.inheritIO(); // 로그

            Process process = pb.start();
            int exitCode = process.waitFor();

            if (exitCode != 0) {
                throw new RuntimeException("COG 변환 실패");
            }

            return outputFile;

        } catch (Exception e) {
            throw new RuntimeException("COG 변환 중 오류 발생", e);
        }
    }

    public void extractAndSaveMetadata(File cogFile,
                                       String originalFileName,
                                       String convertFileName,
                                       int sequence) {

        try {
            BufferedImage image = ImageIO.read(cogFile);

            if (image == null) {
                throw new IllegalArgumentException("이미지를 읽을 수 없습니다");
            }

            ImageMetadata metadata = new ImageMetadata();
            metadata.create(
                    image.getWidth(),
                    image.getHeight(),
                    image.getRaster().getNumBands(),
                    sequence,
                    originalFileName,
                    convertFileName);
            imageMetadataRepository.save(metadata);

        } catch (IOException e) {
            throw new RuntimeException("COG 메타데이터 추출 실패", e);
        }
    }

    public void uploadToS3(String bucket,
                           String key,
                           File file) {

        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucket)
                        .key(key)
                        .build(),
                RequestBody.fromFile(file)
        );
    }
}
