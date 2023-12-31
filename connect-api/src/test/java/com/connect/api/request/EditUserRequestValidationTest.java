package com.connect.api.request;

import com.connect.api.user.request.EditUserInfoRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.Test;

import java.util.Set;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EditUserRequestValidationTest {

    private ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private Validator validator = validatorFactory.getValidator();

    @Test
    public void test_create_editUserRequest_with_valid_payload_should_pass_validation() {
        EditUserInfoRequest editUserInfoRequest = new EditUserInfoRequest()
                .setPassword("1".repeat(8))
                .setStatus(1)
                .setEmail("1234@123.123")
                .setPhone("123");
        Set<ConstraintViolation<EditUserInfoRequest>> violations = validator.validate(editUserInfoRequest);
        assertTrue(violations.isEmpty(), "Validation should pass for a valid editUser request");
    }

    @Test
    public void test_create_editUserRequest_with_null_password_should_pass_validation() {
        EditUserInfoRequest editUserInfoRequest = new EditUserInfoRequest()
                .setStatus(1)
                .setEmail("1234@123.123")
                .setPhone("123");
        Set<ConstraintViolation<EditUserInfoRequest>> violations = validator.validate(editUserInfoRequest);
        assertTrue(violations.isEmpty(), "Validation should pass for a valid editUser request");
    }

    @Test
    public void test_create_editUserRequest_with_over_max_password_should_fail_validation() {
        EditUserInfoRequest editUserInfoRequest = new EditUserInfoRequest()
                .setPassword("1".repeat(21))
                .setStatus(1)
                .setEmail("1234@123.123")
                .setPhone("123");
        Set<ConstraintViolation<EditUserInfoRequest>> violations = validator.validate(editUserInfoRequest);
        assertEquals(1, violations.size(), "Validation should fail for a invalid editUser request");
    }

    @Test
    public void test_create_editUserRequest_with_below_min_password_should_fail_validation() {
        EditUserInfoRequest editUserInfoRequest = new EditUserInfoRequest()
                .setPassword("1".repeat(7))
                .setStatus(1)
                .setEmail("1234@123.123")
                .setPhone("123");
        Set<ConstraintViolation<EditUserInfoRequest>> violations = validator.validate(editUserInfoRequest);
        assertEquals(1, violations.size(), "Validation should fail for a invalid editUser request");
    }

    @Test
    public void test_create_editUserRequest_with_null_email_should_pass_validation() {
        EditUserInfoRequest editUserInfoRequest = new EditUserInfoRequest()
                .setPassword("1".repeat(8))
                .setStatus(1)
                .setPhone("123");
        Set<ConstraintViolation<EditUserInfoRequest>> violations = validator.validate(editUserInfoRequest);
        assertTrue(violations.isEmpty(), "Validation should pass for a valid editUser request");
    }

    @Test
    public void test_create_editUserRequest_with_invalid_email_should_fail_validation() {
        EditUserInfoRequest editUserInfoRequest = new EditUserInfoRequest()
                .setPassword("1".repeat(8))
                .setStatus(1)
                .setEmail("1234")
                .setPhone("123");
        Set<ConstraintViolation<EditUserInfoRequest>> violations = validator.validate(editUserInfoRequest);
        assertEquals(1, violations.size(), "Validation should fail for a invalid editUser request");
    }

    @Test
    public void test_create_editProfileRequest_with_null_status_should_pass_validation() {
        EditUserInfoRequest editUserInfoRequest = new EditUserInfoRequest()
                .setPassword("1".repeat(8))
                .setEmail("1234@123.123")
                .setPhone("123");
        Set<ConstraintViolation<EditUserInfoRequest>> violations = validator.validate(editUserInfoRequest);
        assertTrue(violations.isEmpty(), "Validation should pass for a valid editProfile request");
    }

    @Test
    public void test_create_editProfileRequest_with_invalid_status_should_fail_validation() {
        EditUserInfoRequest editUserInfoRequest = new EditUserInfoRequest()
                .setPassword("1".repeat(8))
                .setStatus(-1)
                .setEmail("1234@123.123")
                .setPhone("123");
        Set<ConstraintViolation<EditUserInfoRequest>> violations = validator.validate(editUserInfoRequest);
        assertEquals(1, violations.size(), "Validation should fail for a invalid editProfile request");
    }
}

