package com.connect.api.request;

import com.connect.api.user.request.EditUserRequest;
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
        EditUserRequest editUserRequest = new EditUserRequest()
                .setUserId("1".repeat(20))
                .setPassword("1".repeat(8))
                .setStatus(1)
                .setEmail("1234@123.123")
                .setDescription("1".repeat(200))
                .setPhone("123");
        Set<ConstraintViolation<EditUserRequest>> violations = validator.validate(editUserRequest);
        assertTrue(violations.isEmpty(), "Validation should pass for a valid editUser request");
    }

    @Test
    public void test_create_editUserRequest_with_null_userId_should_pass_validation() {
        EditUserRequest editUserRequest = new EditUserRequest()
                .setPassword("1".repeat(8))
                .setStatus(1)
                .setEmail("1234@123.123")
                .setDescription("1".repeat(200))
                .setPhone("123");
        Set<ConstraintViolation<EditUserRequest>> violations = validator.validate(editUserRequest);
        assertTrue(violations.isEmpty(), "Validation should pass for a valid editUser request");
    }

    @Test
    public void test_create_editUserRequest_with_over_max_userId_should_fail_validation() {
        EditUserRequest editUserRequest = new EditUserRequest()
                .setUserId("1".repeat(21))
                .setPassword("1".repeat(8))
                .setStatus(1)
                .setEmail("1234@123.123")
                .setDescription("1".repeat(200))
                .setPhone("123");
        Set<ConstraintViolation<EditUserRequest>> violations = validator.validate(editUserRequest);
        assertEquals(1, violations.size(), "Validation should fail for a invalid editUser request");
    }

    @Test
    public void test_create_editUserRequest_with_null_password_should_pass_validation() {
        EditUserRequest editUserRequest = new EditUserRequest()
                .setUserId("1".repeat(20))
                .setStatus(1)
                .setEmail("1234@123.123")
                .setDescription("1".repeat(200))
                .setPhone("123");
        Set<ConstraintViolation<EditUserRequest>> violations = validator.validate(editUserRequest);
        assertTrue(violations.isEmpty(), "Validation should pass for a valid editUser request");
    }

    @Test
    public void test_create_editUserRequest_with_over_max_password_should_fail_validation() {
        EditUserRequest editUserRequest = new EditUserRequest()
                .setUserId("1".repeat(20))
                .setPassword("1".repeat(21))
                .setStatus(1)
                .setEmail("1234@123.123")
                .setDescription("1".repeat(200))
                .setPhone("123");
        Set<ConstraintViolation<EditUserRequest>> violations = validator.validate(editUserRequest);
        assertEquals(1, violations.size(), "Validation should fail for a invalid editUser request");
    }

    @Test
    public void test_create_editUserRequest_with_below_min_password_should_fail_validation() {
        EditUserRequest editUserRequest = new EditUserRequest()
                .setUserId("1".repeat(20))
                .setPassword("1".repeat(7))
                .setStatus(1)
                .setEmail("1234@123.123")
                .setDescription("1".repeat(200))
                .setPhone("123");
        Set<ConstraintViolation<EditUserRequest>> violations = validator.validate(editUserRequest);
        assertEquals(1, violations.size(), "Validation should fail for a invalid editUser request");
    }

    @Test
    public void test_create_editUserRequest_with_over_max_description_should_fail_validation() {
        EditUserRequest editUserRequest = new EditUserRequest()
                .setUserId("1".repeat(20))
                .setPassword("1".repeat(8))
                .setStatus(1)
                .setEmail("1234@123.123")
                .setDescription("1".repeat(201))
                .setPhone("123");
        Set<ConstraintViolation<EditUserRequest>> violations = validator.validate(editUserRequest);
        assertEquals(1, violations.size(), "Validation should fail for a invalid editUser request");
    }

    @Test
    public void test_create_editUserRequest_with_null_email_should_pass_validation() {
        EditUserRequest editUserRequest = new EditUserRequest()
                .setUserId("1".repeat(20))
                .setPassword("1".repeat(8))
                .setStatus(1)
                .setDescription("1".repeat(200))
                .setPhone("123");
        Set<ConstraintViolation<EditUserRequest>> violations = validator.validate(editUserRequest);
        assertTrue(violations.isEmpty(), "Validation should pass for a valid editUser request");
    }

    @Test
    public void test_create_editUserRequest_with_invalid_email_should_fail_validation() {
        EditUserRequest editUserRequest = new EditUserRequest()
                .setUserId("1".repeat(20))
                .setPassword("1".repeat(8))
                .setStatus(1)
                .setEmail("1234")
                .setDescription("1".repeat(200))
                .setPhone("123");
        Set<ConstraintViolation<EditUserRequest>> violations = validator.validate(editUserRequest);
        assertEquals(1, violations.size(), "Validation should fail for a invalid editUser request");
    }

    @Test
    public void test_create_editProfileRequest_with_null_status_should_pass_validation() {
        EditUserRequest editUserRequest = new EditUserRequest()
                .setUserId("1".repeat(20))
                .setPassword("1".repeat(8))
                .setEmail("1234@123.123")
                .setDescription("1".repeat(200))
                .setPhone("123");
        Set<ConstraintViolation<EditUserRequest>> violations = validator.validate(editUserRequest);
        assertTrue(violations.isEmpty(), "Validation should pass for a valid editProfile request");
    }

    @Test
    public void test_create_editProfileRequest_with_invalid_status_should_fail_validation() {
        EditUserRequest editUserRequest = new EditUserRequest()
                .setUserId("1".repeat(20))
                .setPassword("1".repeat(8))
                .setStatus(-1)
                .setEmail("1234@123.123")
                .setDescription("1".repeat(200))
                .setPhone("123");
        Set<ConstraintViolation<EditUserRequest>> violations = validator.validate(editUserRequest);
        assertEquals(1, violations.size(), "Validation should fail for a invalid editProfile request");
    }

    @Test
    public void test_create_editProfileRequest_with_blank_userId_should_fail_validation() {
        EditUserRequest editUserRequest = new EditUserRequest()
                .setUserId("")
                .setPassword("1".repeat(8))
                .setStatus(1)
                .setEmail("1234@123.123")
                .setDescription("1".repeat(200))
                .setPhone("123");
        Set<ConstraintViolation<EditUserRequest>> violations = validator.validate(editUserRequest);
        assertEquals(1, violations.size(), "Validation should fail for a invalid editProfile request");
    }

    @Test
    public void test_create_editProfileRequest_with_over_max_userId_should_fail_validation() {
        EditUserRequest editUserRequest = new EditUserRequest()
                .setUserId("1".repeat(21))
                .setPassword("1".repeat(8))
                .setStatus(1)
                .setEmail("1234@123.123")
                .setDescription("1".repeat(200))
                .setPhone("123");
        Set<ConstraintViolation<EditUserRequest>> violations = validator.validate(editUserRequest);
        assertEquals(1, violations.size(), "Validation should fail for a invalid editProfile request");
    }
}

