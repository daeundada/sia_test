package sia.domain.convert.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class ImageMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID imageId;

    private String originalFileName;

    @Column(unique = true)
    private String convertFileName;

    private int width;

    private int height;

    private int bandCount;

    private int sequence;

    private final LocalDateTime createdAt = LocalDateTime.now();

    public void create(int width,
                       int height,
                       int bandCount,
                       int sequence,
                       String originalFileName,
                       String convertFileName) {
        this.width = width;
        this.height = height;
        this.bandCount = bandCount;
        this.sequence = sequence;
        this.originalFileName = originalFileName;
        this.convertFileName = convertFileName;
    }
}
