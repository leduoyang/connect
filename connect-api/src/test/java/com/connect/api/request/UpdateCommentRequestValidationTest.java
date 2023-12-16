package com.connect.api.request;

import com.connect.api.comment.request.UpdateCommentRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.Test;


import java.util.Set;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UpdateCommentRequestValidationTest {

    private ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private Validator validator = validatorFactory.getValidator();

    @Test
    public void test_create_updateCommentRequest_with_valid_payload_should_pass_validation() {
        UpdateCommentRequest updateCommentRequest = new UpdateCommentRequest()
                .setStatus(1)
                .setContent("1".repeat(500));
        Set<ConstraintViolation<UpdateCommentRequest>> violations = validator.validate(updateCommentRequest);
        assertTrue(violations.isEmpty(), "Validation should pass for a valid updateComment request");
    }

    @Test
    public void test_create_updateCommentRequest_with_null_status_should_pass_validation() {
        UpdateCommentRequest updateCommentRequest = new UpdateCommentRequest().setContent("1".repeat(500));
        Set<ConstraintViolation<UpdateCommentRequest>> violations = validator.validate(updateCommentRequest);
        assertTrue(violations.isEmpty(), "Validation should pass for a valid updateComment request");
        assertTrue(updateCommentRequest.getStatus() == null, "status should be null");
    }

    @Test
    public void test_create_updateCommentRequest_with_invalid_status_should_fail_validation() {
        UpdateCommentRequest updateCommentRequest = new UpdateCommentRequest()
                .setStatus(-1)
                .setContent("1".repeat(500));
        Set<ConstraintViolation<UpdateCommentRequest>> violations = validator.validate(updateCommentRequest);
        assertEquals(1, violations.size(), "Validation should fail for a invalid updateComment request");
    }

    @Test
    public void test_create_updateCommentRequest_with_blank_content_should_fail_validation() {
        UpdateCommentRequest updateCommentRequest = new UpdateCommentRequest()
                .setStatus(1)
                .setContent("");
        Set<ConstraintViolation<UpdateCommentRequest>> violations = validator.validate(updateCommentRequest);
        assertEquals(1, violations.size(), "Validation should fail for a invalid updateComment request");
    }

    @Test
    public void test_create_updateCommentRequest_with_over_max_content_should_fail_validation() {
        UpdateCommentRequest updateCommentRequest = new UpdateCommentRequest()
                .setStatus(1)
                .setContent("1".repeat(501));
        Set<ConstraintViolation<UpdateCommentRequest>> violations = validator.validate(updateCommentRequest);
        assertEquals(1, violations.size(), "Validation should fail for a invalid updateComment request");
    }
}

