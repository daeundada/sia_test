package sia.domain.convert.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sia.domain.convert.entity.ImageMetadata;

public interface ImageMetadataRepositoryCustom {

    Page<ImageMetadata> search(String originalFileName,
                               String convertFileName,
                               Integer bandCount,
                               Pageable pageable);

}