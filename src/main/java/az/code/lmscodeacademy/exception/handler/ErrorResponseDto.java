package az.code.lmscodeacademy.exception.handler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDto {

    int status;
    String title;
    String details;

    @Builder.Default
    Map<String, String> data = new HashMap<>();
}
