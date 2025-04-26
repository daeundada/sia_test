package sia.domain.convert.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sia.domain.convert.entity.ImageMetadata;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ImageMetadataRepository
        extends JpaRepository<ImageMetadata, UUID>, ImageMetadataRepositoryCustom {

    @Query("SELECT MAX(m.sequence) FROM ImageMetadata m WHERE m.originalFileName = :originalFileName")
    Optional<Integer> findMaxSequenceByOriginalFileName(@Param("originalFileName") String originalFileName);
}