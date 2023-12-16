package com.connect.api.request;

import com.connect.api.post.request.UpdatePostRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.Test;


import java.util.Set;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UpdatePostRequestValidationTest {

    private ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private Validator validator = validatorFactory.getValidator();

    @Test
    public void test_update_updatePostRequest_with_valid_payload_should_pass_validation() {
        UpdatePostRequest updatePostRequest = new UpdatePostRequest()
                .setStatus(1)
                .setContent("1".repeat(500));
        Set<ConstraintViolation<UpdatePostRequest>> violations = validator.validate(updatePostRequest);
        assertTrue(violations.isEmpty(), "Validation should pass for a valid updatePost request");
    }

    @Test
    public void test_update_updatePostRequest_with_null_referenceId_should_pass_validation() {
        UpdatePostRequest updatePostRequest = new UpdatePostRequest();
        Set<ConstraintViolation<UpdatePostRequest>> violations = validator.validate(updatePostRequest);
        assertTrue(violations.isEmpty(), "Validation should pass for a valid updatePost request");
    }

    @Test
    public void test_update_updatePostRequest_with_null_status_should_pass_validation() {
        UpdatePostRequest updatePostRequest = new UpdatePostRequest()
                .setContent("1".repeat(500));
        Set<ConstraintViolation<UpdatePostRequest>> violations = validator.validate(updatePostRequest);
        assertTrue(violations.isEmpty(), "Validation should pass for a valid updatePost request");
        assertTrue(updatePostRequest.getStatus() == null, "Status should be null");
    }

    @Test
    public void test_update_updatePostRequest_with_invalid_status_should_fail_validation() {
        UpdatePostRequest updatePostRequest = new UpdatePostRequest()
                .setStatus(-1)
                .setContent("1".repeat(500));
        Set<ConstraintViolation<UpdatePostRequest>> violations = validator.validate(updatePostRequest);
        assertEquals(1, violations.size(), "Validation should fail for a invalid updatePost request");
    }

    @Test
    public void test_update_updatePostRequest_with_null_content_should_pass_validation() {
        UpdatePostRequest updatePostRequest = new UpdatePostRequest();
        Set<ConstraintViolation<UpdatePostRequest>> violations = validator.validate(updatePostRequest);
        assertTrue(violations.isEmpty(), "Validation should pass for a valid updatePost request");
    }

    @Test
    public void test_update_updatePostRequest_with_blank_content_should_fail_validation() {
        UpdatePostRequest updatePostRequest = new UpdatePostRequest().setContent("");
        Set<ConstraintViolation<UpdatePostRequest>> violations = validator.validate(updatePostRequest);
        assertEquals(1, violations.size(), "Validation should fail for a invalid updatePost request");
    }

    @Test
    public void test_update_updatePostRequest_with_over_max_content_should_fail_validation() {
        UpdatePostRequest updatePostRequest = new UpdatePostRequest()
                .setContent("1".repeat(2001));
        Set<ConstraintViolation<UpdatePostRequest>> violations = validator.validate(updatePostRequest);
        assertEquals(1, violations.size(), "Validation should fail for a invalid updatePost request");
    }
}

