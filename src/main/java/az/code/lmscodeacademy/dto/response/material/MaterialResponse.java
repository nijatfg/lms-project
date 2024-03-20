package az.code.lmscodeacademy.dto.response.material;

import lombok.Data;

@Data
public class MaterialResponse {
    private Long id;

    private String title;
    private String type;

    private String content;
}
