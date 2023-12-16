package com.connect.api.request;

import com.connect.api.comment.request.CreateCommentRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.Test;


import java.util.Set;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateCommentRequestValidationTest {

    private ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private Validator validator = validatorFactory.getValidator();

    @Test
    public void test_create_createCommentRequest_with_valid_payload_should_pass_validation() {
        CreateCommentRequest createCommentRequest = new CreateCommentRequest()
                .setPostId(1L)
                .setStatus(1)
                .setContent("1".repeat(500));
        Set<ConstraintViolation<CreateCommentRequest>> violations = validator.validate(createCommentRequest);
        assertTrue(violations.isEmpty(), "Validation should pass for a valid createCommentRequest request");
    }

    @Test
    public void test_create_createCommentRequest_with_null_postId_should_fail_validation() {
        CreateCommentRequest createCommentRequest = new CreateCommentRequest()
                .setStatus(1)
                .setContent("1".repeat(500));
        Set<ConstraintViolation<CreateCommentRequest>> violations = validator.validate(createCommentRequest);
        assertEquals(1, violations.size(), "Validation should fail for a invalid createCommentRequest request");
    }

    @Test
    public void test_create_createCommentRequest_with_null_status_should_use_default_and_pass_validation() {
        CreateCommentRequest createCommentRequest = new CreateCommentRequest()
                .setPostId(1L)
                .setContent("1".repeat(500));
        Set<ConstraintViolation<CreateCommentRequest>> violations = validator.validate(createCommentRequest);
        assertTrue(violations.isEmpty(), "Validation should pass for a valid createCommentRequest request");
        assertTrue(createCommentRequest.getStatus() == 0, "Status should be 0");
    }

    @Test
    public void test_create_createCommentRequest_with_invalid_status_should_fail_validation() {
        CreateCommentRequest createCommentRequest = new CreateCommentRequest()
                .setPostId(1L)
                .setStatus(-1)
                .setContent("1".repeat(500));
        Set<ConstraintViolation<CreateCommentRequest>> violations = validator.validate(createCommentRequest);
        assertEquals(1, violations.size(), "Validation should fail for a invalid createCommentRequest request");
    }

    @Test
    public void test_create_createCommentRequest_with_below_min_content_should_fail_validation() {
        CreateCommentRequest createCommentRequest = new CreateCommentRequest()
                .setPostId(1L)
                .setStatus(1)
                .setContent("");
        Set<ConstraintViolation<CreateCommentRequest>> violations = validator.validate(createCommentRequest);
        assertEquals(1, violations.size(), "Validation should fail for a invalid createCommentRequest request");
    }

    @Test
    public void test_create_createCommentRequest_with_over_max_content_should_fail_validation() {
        CreateCommentRequest createCommentRequest = new CreateCommentRequest()
                .setPostId(1L)
                .setStatus(1)
                .setContent("1".repeat(501));
        Set<ConstraintViolation<CreateCommentRequest>> violations = validator.validate(createCommentRequest);
        assertEquals(1, violations.size(), "Validation should fail for a invalid createCommentRequest request");
    }
}

