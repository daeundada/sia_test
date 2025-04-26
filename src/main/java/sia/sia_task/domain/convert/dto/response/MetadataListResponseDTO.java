package sia.sia_task.domain.convert.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class MetadataListResponseDTO {

    private final int currentPage;

    private final int totalPages;

    private final long totalElements;

    private final int pageSize;

    List<MetadataResponseDTO> metadataList;

    public MetadataListResponseDTO(List<MetadataResponseDTO> metadataList,
                                   int pageSize,
                                   long totalElements,
                                   int totalPages,
                                   int currentPage) {
        this.metadataList = metadataList;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
    }
}
