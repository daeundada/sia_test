package sia.sia_task.domain.convert.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sia.sia_task.domain.convert.entity.ImageMetadata;

public interface ImageMetadataRepositoryCustom {

    Page<ImageMetadata> search(String originalFileName,
                               String convertFileName,
                               Integer bandCount,
                               Pageable pageable);

}