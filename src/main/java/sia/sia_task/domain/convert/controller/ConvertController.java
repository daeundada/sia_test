package sia.sia_task.domain.convert.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sia.sia_task.domain.convert.dto.request.ConvertRequestDTO;
import sia.sia_task.domain.convert.service.ConvertService;
import sia.sia_task.global.response.ApiResponse;

@RequestMapping("/api/v1")
@RestController
@RequiredArgsConstructor
public class ConvertController {

    private final ConvertService conversionService;

    @PostMapping("/convert/single")
    public ResponseEntity<ApiResponse<String>> convertSingle(
            @RequestBody ConvertRequestDTO request) {

        String fileName = conversionService.convertSingle(request);

        return ResponseEntity.ok().body(ApiResponse.of("단건 변환 및 업로드 완료", fileName));
    }
}
