package com.connect.api.request;

import com.connect.api.user.request.EditProfileRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.Test;

import java.util.Set;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EditProfileRequestValidationTest {

    private ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private Validator validator = validatorFactory.getValidator();

    @Test
    public void test_create_editProfileRequest_with_valid_payload_should_pass_validation() {
        EditProfileRequest editProfileRequest = new EditProfileRequest()
                .setUsername("12345678")
                .setStatus(1)
                .setDescription("12345678");
        Set<ConstraintViolation<EditProfileRequest>> violations = validator.validate(editProfileRequest);
        assertTrue(violations.isEmpty(), "Validation should pass for a valid editProfile request");
    }

    @Test
    public void test_create_editProfileRequest_with_null_status_should_pass_validation() {
        EditProfileRequest editProfileRequest = new EditProfileRequest()
                .setUsername("12345678")
                .setDescription("12345678");
        Set<ConstraintViolation<EditProfileRequest>> violations = validator.validate(editProfileRequest);
        assertTrue(violations.isEmpty(), "Validation should pass for a valid editProfile request");
    }

    @Test
    public void test_create_editProfileRequest_with_invalid_status_should_fail_validation() {
        EditProfileRequest editProfileRequest = new EditProfileRequest()
                .setStatus(-1)
                .setDescription("12345678");
        Set<ConstraintViolation<EditProfileRequest>> violations = validator.validate(editProfileRequest);
        assertEquals(1, violations.size(), "Validation should fail for a invalid editProfile request");
    }

    @Test
    public void test_create_editProfileRequest_with_over_max_description_should_fail_validation() {
        EditProfileRequest editProfileRequest = new EditProfileRequest()
                .setDescription("a".repeat(201));
        Set<ConstraintViolation<EditProfileRequest>> violations = validator.validate(editProfileRequest);
        assertEquals(1, violations.size(), "Validation should fail for a invalid editProfile request");
    }

    @Test
    public void test_create_editProfileRequest_with_blank_userId_should_fail_validation() {
        EditProfileRequest editProfileRequest = new EditProfileRequest()
                .setUsername("")
                .setStatus(1)
                .setDescription("12345678");
        Set<ConstraintViolation<EditProfileRequest>> violations = validator.validate(editProfileRequest);
        assertEquals(1, violations.size(), "Validation should fail for a invalid editProfile request");
    }

    @Test
    public void test_create_editProfileRequest_with_over_max_userId_should_fail_validation() {
        EditProfileRequest editProfileRequest = new EditProfileRequest()
                .setUsername("a".repeat(21))
                .setStatus(1)
                .setDescription("12345678");
        Set<ConstraintViolation<EditProfileRequest>> violations = validator.validate(editProfileRequest);
        assertEquals(1, violations.size(), "Validation should fail for a invalid editProfile request");
    }
}

