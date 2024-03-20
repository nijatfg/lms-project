package az.code.lmscodeacademy.dto.response;

import az.code.lmscodeacademy.entity.Group;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CourseResponse {
    private Long id;

    private String name;
    private String description;

    List<GroupResponse> groups = new ArrayList<>();
}
