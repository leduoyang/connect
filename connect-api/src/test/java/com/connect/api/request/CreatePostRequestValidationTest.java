package com.connect.api.request;

import com.connect.api.post.request.CreatePostRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.Test;


import java.util.Set;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreatePostRequestValidationTest {

    private ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private Validator validator = validatorFactory.getValidator();

    @Test
    public void test_create_createPostRequest_with_valid_payload_should_pass_validation() {
        CreatePostRequest createPostRequest = new CreatePostRequest()
                .setStatus(1)
                .setContent("1".repeat(500));
        Set<ConstraintViolation<CreatePostRequest>> violations = validator.validate(createPostRequest);
        assertTrue(violations.isEmpty(), "Validation should pass for a valid createPostRequest request");
    }

    @Test
    public void test_create_createPostRequest_with_null_referenceId_should_pass_validation() {
        CreatePostRequest createPostRequest = new CreatePostRequest();
        Set<ConstraintViolation<CreatePostRequest>> violations = validator.validate(createPostRequest);
        assertTrue(violations.isEmpty(), "Validation should pass for a valid createPostRequest request");
    }

    @Test
    public void test_create_createPostRequest_with_null_status_should_use_default_and_pass_validation() {
        CreatePostRequest createPostRequest = new CreatePostRequest()
                .setContent("1".repeat(500));
        Set<ConstraintViolation<CreatePostRequest>> violations = validator.validate(createPostRequest);
        assertTrue(violations.isEmpty(), "Validation should pass for a valid createPostRequest request");
        assertTrue(createPostRequest.getStatus() == 0, "Status should be 0");
    }

    @Test
    public void test_create_createPostRequest_with_invalid_status_should_fail_validation() {
        CreatePostRequest createPostRequest = new CreatePostRequest()
                .setStatus(-1)
                .setContent("1".repeat(500));
        Set<ConstraintViolation<CreatePostRequest>> violations = validator.validate(createPostRequest);
        assertEquals(1, violations.size(), "Validation should fail for a invalid createPostRequest request");
    }

    @Test
    public void test_create_createPostRequest_with_null_content_should_pass_validation() {
        CreatePostRequest createPostRequest = new CreatePostRequest();
        Set<ConstraintViolation<CreatePostRequest>> violations = validator.validate(createPostRequest);
        assertTrue(violations.isEmpty(), "Validation should pass for a valid createPostRequest request");
    }

    @Test
    public void test_create_createPostRequest_with_blank_content_should_fail_validation() {
        CreatePostRequest createPostRequest = new CreatePostRequest().setContent("");
        Set<ConstraintViolation<CreatePostRequest>> violations = validator.validate(createPostRequest);
        assertEquals(1, violations.size(), "Validation should fail for a invalid createPostRequest request");
    }

    @Test
    public void test_create_createPostRequest_with_over_max_content_should_fail_validation() {
        CreatePostRequest createPostRequest = new CreatePostRequest()
                .setContent("1".repeat(2001));
        Set<ConstraintViolation<CreatePostRequest>> violations = validator.validate(createPostRequest);
        assertEquals(1, violations.size(), "Validation should fail for a invalid createPostRequest request");
    }
}

