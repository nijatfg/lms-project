package az.code.lmscodeacademy.service.submission;

import az.code.lmscodeacademy.dto.request.material.MaterialRequest;
import az.code.lmscodeacademy.dto.request.sumbission.SubmissionRequest;
import az.code.lmscodeacademy.dto.response.material.MaterialResponse;
import az.code.lmscodeacademy.dto.response.submission.SubmissionResponse;
import az.code.lmscodeacademy.entity.assignment.Assignment;
import az.code.lmscodeacademy.entity.group.Group;
import az.code.lmscodeacademy.entity.material.Material;
import az.code.lmscodeacademy.entity.submission.Submission;
import az.code.lmscodeacademy.entity.user.User;
import az.code.lmscodeacademy.exception.assignment.AssignmentNotFoundException;
import az.code.lmscodeacademy.exception.handler.ErrorCodes;
import az.code.lmscodeacademy.exception.users.UserNotFoundException;
import az.code.lmscodeacademy.repository.assignment.AssignmentRepository;
import az.code.lmscodeacademy.repository.submission.SubmissionRepository;
import az.code.lmscodeacademy.repository.user.UserRepository;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubmissionService {

    @Value("${application.submission.bucket.name}")
    private String bucketName;

    private final AmazonS3 s3Client;
    private final SubmissionRepository submissionRepository;
    private final AssignmentRepository assignmentRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    public SubmissionResponse submitAssignmentWithFile(SubmissionRequest request, Long assignmentId, Long userId, MultipartFile file) throws IOException {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new AssignmentNotFoundException(ErrorCodes.ASSIGNMENT_NOT_FOUND));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(ErrorCodes.USER_NOT_FOUND));

        validateFileAndLink(request, file);

        Submission submission = new Submission();
        submission.setAssignment(assignment);
        submission.setUser(user);
        if (request.getLink() != null && !request.getLink().isEmpty()) {
            submission.setLink(request.getLink());
        } else {
            String fileName = saveFileToS3(file);
            submission.setContent(fileName);
        }

        return modelMapper.map(submissionRepository.save(submission), SubmissionResponse.class);
    }

    public SubmissionResponse submitAssignmentWithoutFile(Long assignmentId, Long userId, SubmissionRequest request) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new AssignmentNotFoundException(ErrorCodes.ASSIGNMENT_NOT_FOUND));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(ErrorCodes.USER_NOT_FOUND));

        if (request.getLink() == null || request.getLink().isEmpty()) {
            throw new IllegalArgumentException("Link must be provided.");
        }

        Submission submission = new Submission();
        submission.setAssignment(assignment);
        submission.setUser(user);
        submission.setLink(request.getLink());

        return modelMapper.map(submissionRepository.save(submission), SubmissionResponse.class);
    }

    private void validateFileAndLink(SubmissionRequest request, MultipartFile file) {
        if (request.getLink() == null || request.getLink().isEmpty()) {
            if (file == null || file.isEmpty()) {
                throw new IllegalArgumentException("Either file or link must be provided.");
            }
        }
    }

    private String saveFileToS3(MultipartFile file) throws IOException {
        File convertedFile = convertMultiPartFileToFile(file);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, convertedFile));
        convertedFile.delete();
        return fileName;
    }

    public byte[] downloadFile(String fileName) {
        S3Object s3Object = s3Client.getObject(bucketName, fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            byte[] content = IOUtils.toByteArray(inputStream);
            return content;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private File convertMultiPartFileToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        }
        return convertedFile;
    }

    public List<SubmissionResponse> getAllSubmissionsByAssignmentId(Long assignmentId) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new AssignmentNotFoundException(ErrorCodes.ASSIGNMENT_NOT_FOUND));

        List<Submission> submissions = submissionRepository.findByAssignment(assignment);

        return submissions.stream()
                .map(submission -> modelMapper.map(submission, SubmissionResponse.class))
                .collect(Collectors.toList());
    }
}
