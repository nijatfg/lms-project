package az.code.lmscodeacademy.dto.response.course;

import az.code.lmscodeacademy.dto.response.group.GroupResponse;
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
