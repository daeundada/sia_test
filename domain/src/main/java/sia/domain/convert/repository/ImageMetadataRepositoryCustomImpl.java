package sia.domain.convert.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import sia.domain.convert.entity.ImageMetadata;
import sia.domain.convert.entity.QImageMetadata;

import java.util.List;

@RequiredArgsConstructor
public class ImageMetadataRepositoryCustomImpl implements ImageMetadataRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ImageMetadata> search(String originalFileName,
                                      String convertFileName,
                                      Integer bandCount,
                                      Pageable pageable) {

        QImageMetadata imageMetadata = QImageMetadata.imageMetadata;

        // 검색 조건 설정
        BooleanBuilder builder = new BooleanBuilder();

        if (originalFileName != null && !originalFileName.isEmpty()) {
            builder.and(imageMetadata.originalFileName.eq(originalFileName));
        }

        if (convertFileName != null && !convertFileName.isEmpty()) {
            builder.and(imageMetadata.convertFileName.eq(convertFileName));
        }

        if (bandCount != null) {
            builder.and(imageMetadata.bandCount.eq(bandCount));
        }

        // 데이터 조회
        List<ImageMetadata> content = queryFactory
                .selectFrom(imageMetadata)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 총 개수 조회
        Long total = queryFactory
                .select(imageMetadata.count())
                .from(imageMetadata)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(content, pageable, total == null ? 0 : total);
    }
}