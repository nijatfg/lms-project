package az.code.lmscodeacademy.controller.material;

import az.code.lmscodeacademy.dto.request.material.MaterialRequest;
import az.code.lmscodeacademy.dto.response.material.MaterialResponse;
import az.code.lmscodeacademy.service.material.MaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/materials")
@RequiredArgsConstructor
@CrossOrigin
public class MaterialController {

    private final MaterialService materialService;


    @GetMapping
    public ResponseEntity<List<MaterialResponse>> getAllMaterials() {
        List<MaterialResponse> materials = materialService.getAllMaterials();
        return ResponseEntity.ok(materials);
    }

    @PostMapping("/upload")
    @PreAuthorize("hasAuthority('TEACHER')")
    public ResponseEntity<MaterialResponse> uploadMaterial(@ModelAttribute MaterialRequest materialRequest,
                                                           @RequestParam("file") MultipartFile file,
                                                           @RequestParam("groupId") Long groupId) throws IOException {
        MaterialResponse uploadedMaterial = materialService.uploadMaterial(materialRequest, file, groupId);
        return ResponseEntity.status(HttpStatus.CREATED).body(uploadedMaterial);
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName) {
        byte[] data = materialService.downloadFile(fileName);

        ByteArrayResource resource = new ByteArrayResource(data);

        return ResponseEntity.
                ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }


    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
        return new ResponseEntity<>(materialService.deleteFile(fileName), HttpStatus.OK);
    }

    @GetMapping("/groups/{groupId}")
    public ResponseEntity<List<MaterialResponse>> findMaterialsByGroup(@PathVariable Long groupId) {
        return new ResponseEntity<>(materialService.findMaterialsByGroup(groupId), HttpStatus.OK);
    }
}
