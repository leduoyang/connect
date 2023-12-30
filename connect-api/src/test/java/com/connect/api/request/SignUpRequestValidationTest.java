package com.connect.api.request;

import com.connect.api.user.request.SignUpRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hibernate.validator.internal.util.Contracts.assertTrue;

public class SignUpRequestValidationTest {

    private ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private Validator validator = validatorFactory.getValidator();

    @Test
    public void test_create_signUpRequest_with_valid_payload_should_pass_validation() {
        SignUpRequest signUpRequest = new SignUpRequest()
                .setUsername("12345678")
                .setPassword("12345678")
                .setEmail("1234@123.123")
                .setUid("12345678");
        Set<ConstraintViolation<SignUpRequest>> violations = validator.validate(signUpRequest);
        assertTrue(violations.isEmpty(), "Validation should pass for a valid singUp request");
    }

    @Test
    public void test_create_signUpRequest_with_null_userId_should_fail_validation() {
        SignUpRequest signUpRequest = new SignUpRequest()
                .setPassword("12345678")
                .setEmail("1234@123.123")
                .setUid("12345678");
        Set<ConstraintViolation<SignUpRequest>> violations = validator.validate(signUpRequest);
        assertEquals(2, violations.size(), "Validation should fail for a invalid singUp request");
    }

    @Test
    public void test_create_signUpRequest_with_blank_userId_should_fail_validation() {
        SignUpRequest signUpRequest = new SignUpRequest()
                .setUsername("")
                .setPassword("12345678")
                .setEmail("1234@123.123")
                .setUid("12345678");
        Set<ConstraintViolation<SignUpRequest>> violations = validator.validate(signUpRequest);
        assertEquals(1, violations.size(), "Validation should fail for a invalid singUp request");
    }

    @Test
    public void test_create_signUpRequest_with_over_max_userId_should_fail_validation() {
        SignUpRequest signUpRequest = new SignUpRequest()
                .setUsername("123456789012345678901")
                .setPassword("12345678")
                .setEmail("1234@123.123")
                .setUid("12345678");
        Set<ConstraintViolation<SignUpRequest>> violations = validator.validate(signUpRequest);
        assertEquals(1, violations.size(), "Validation should fail for a invalid singUp request");
    }

    @Test
    public void test_create_signUpRequest_with_null_password_should_fail_validation() {
        SignUpRequest signUpRequest = new SignUpRequest()
                .setUsername("12345678")
                .setEmail("1234@123.123")
                .setUid("12345678");
        Set<ConstraintViolation<SignUpRequest>> violations = validator.validate(signUpRequest);
        assertEquals(1, violations.size(), "Validation should fail for a invalid singUp request");
    }

    @Test
    public void test_create_signUpRequest_with_below_min_password_should_fail_validation() {
        SignUpRequest signUpRequest = new SignUpRequest()
                .setUsername("12345678")
                .setPassword("1234567")
                .setEmail("1234@123.123")
                .setUid("12345678");
        Set<ConstraintViolation<SignUpRequest>> violations = validator.validate(signUpRequest);
        assertEquals(1, violations.size(), "Validation should fail for a invalid singUp request");
    }

    @Test
    public void test_create_signUpRequest_with_over_max_password_should_fail_validation() {
        SignUpRequest signUpRequest = new SignUpRequest()
                .setUsername("12345678")
                .setPassword("1234567890123456789012")
                .setEmail("1234@123.123")
                .setUid("12345678");
        Set<ConstraintViolation<SignUpRequest>> violations = validator.validate(signUpRequest);
        assertEquals(1, violations.size(), "Validation should fail for a invalid singUp request");
    }

    @Test
    public void test_create_signUpRequest_with_over_max_description_should_fail_validation() {
        SignUpRequest signUpRequest = new SignUpRequest()
                .setUsername("12345678")
                .setPassword("12345678")
                .setDescription("a". repeat(201))
                .setEmail("1234@123.123")
                .setUid("12345678");
        Set<ConstraintViolation<SignUpRequest>> violations = validator.validate(signUpRequest);
        assertEquals(1, violations.size(), "Validation should fail for a invalid singUp request");
    }

    @Test
    public void test_create_signUpRequest_with_null_email_should_fail_validation() {
        SignUpRequest signUpRequest = new SignUpRequest()
                .setUsername("12345678")
                .setPassword("12345678")
                .setUid("12345678");
        Set<ConstraintViolation<SignUpRequest>> violations = validator.validate(signUpRequest);
        assertEquals(1, violations.size(), "Validation should fail for a invalid singUp request");
    }

    @Test
    public void test_create_signUpRequest_with_invalid_email_should_fail_validation() {
        SignUpRequest signUpRequest = new SignUpRequest()
                .setUsername("12345678")
                .setPassword("12345678")
                .setEmail("123.123")
                .setUid("12345678");
        Set<ConstraintViolation<SignUpRequest>> violations = validator.validate(signUpRequest);
        assertEquals(1, violations.size(), "Validation should fail for a invalid singUp request");
    }

    @Test
    public void test_create_signUpRequest_with_null_uid_should_fail_validation() {
        SignUpRequest signUpRequest = new SignUpRequest()
                .setUsername("12345678")
                .setPassword("12345678")
                .setEmail("1234@123.123");
        Set<ConstraintViolation<SignUpRequest>> violations = validator.validate(signUpRequest);
        assertEquals(1, violations.size(), "Validation should fail for a invalid singUp request");
    }
}

