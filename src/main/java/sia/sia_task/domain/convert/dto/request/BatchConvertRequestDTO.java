package sia.sia_task.domain.convert.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class BatchConvertRequestDTO {

    private List<String> fileNames;
}
