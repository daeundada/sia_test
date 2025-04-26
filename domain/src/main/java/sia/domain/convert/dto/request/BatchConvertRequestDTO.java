package sia.domain.convert.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.List;

@Getter
public class BatchConvertRequestDTO {

    @NotBlank
    private List<String> fileNames;
}
