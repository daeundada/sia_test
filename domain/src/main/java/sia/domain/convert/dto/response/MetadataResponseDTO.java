package sia.domain.convert.dto.response;

import lombok.Getter;
import sia.domain.convert.entity.ImageMetadata;

import java.util.UUID;

@Getter
public class MetadataResponseDTO {

    private final UUID imageId;

    private final String originalFileName;

    private final String convertFileName;

    private final int width;

    private final int height;

    private final int bandCount;

    public MetadataResponseDTO(ImageMetadata imageMetadata) {
        this.imageId = imageMetadata.getImageId();
        this.originalFileName = imageMetadata.getOriginalFileName();
        this.convertFileName = imageMetadata.getConvertFileName();
        this.width = imageMetadata.getWidth();
        this.height = imageMetadata.getHeight();
        this.bandCount = imageMetadata.getBandCount();
    }
}