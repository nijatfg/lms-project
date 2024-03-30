package az.code.lmscodeacademy.controller.group;

import az.code.lmscodeacademy.dto.request.group.GroupRequest;
import az.code.lmscodeacademy.dto.response.group.GroupResponse;
import az.code.lmscodeacademy.service.group.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/groups")
@RequiredArgsConstructor
@CrossOrigin
public class GroupController {

    private final GroupService groupService;

    @GetMapping
    public ResponseEntity<List<GroupResponse>> getAllGroups() {
        List<GroupResponse> groups = groupService.findAll();
        return ResponseEntity.ok(groups);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupResponse> getGroupById(@PathVariable("id") Long groupId) {
        GroupResponse group = groupService.findById(groupId);
        return ResponseEntity.ok(group);
    }

    @PostMapping("/{courseName}")
    public ResponseEntity<GroupResponse> createGroup(
            @RequestBody GroupRequest groupRequest,
            @PathVariable("courseName") String courseName
    ) {
        GroupResponse createdGroup = groupService.save(groupRequest, courseName);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGroup);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupResponse> updateGroup(
            @PathVariable("id") Long groupId,
            @RequestBody GroupRequest groupRequest
    ) {
        GroupResponse updatedGroup = groupService.updateGroup(groupId, groupRequest);
        return ResponseEntity.ok(updatedGroup);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable("id") Long groupId) {
        groupService.delete(groupId);
        return ResponseEntity.noContent().build();
    }
}
