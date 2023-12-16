package com.connect.api.request;

import com.connect.api.user.request.SignInRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.Test;

import java.util.Set;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SignInRequestValidationTest {

    private ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private Validator validator = validatorFactory.getValidator();

    @Test
    public void test_create_signInRequest_with_valid_payload_should_pass_validation() {
        SignInRequest signInRequest = new SignInRequest()
                .setUserId("12345678")
                .setPassword("12345678");
        Set<ConstraintViolation<SignInRequest>> violations = validator.validate(signInRequest);
        assertTrue(violations.isEmpty(), "Validation should pass for a valid signIn request");
    }

    @Test
    public void test_create_signInRequest_with_null_userId_should_fail_validation() {
        SignInRequest signInRequest = new SignInRequest()
                .setPassword("12345678");
        Set<ConstraintViolation<SignInRequest>> violations = validator.validate(signInRequest);
        assertEquals(2, violations.size(), "Validation should fail for a invalid signIn request");
    }

    @Test
    public void test_create_signInRequest_with_null_password_should_fail_validation() {
        SignInRequest signInRequest = new SignInRequest()
                .setUserId("12345678");
        Set<ConstraintViolation<SignInRequest>> violations = validator.validate(signInRequest);
        assertEquals(2, violations.size(), "Validation should fail for a invalid signIn request");
    }

    @Test
    public void test_create_signInRequest_with_blank_userId_should_fail_validation() {
        SignInRequest signInRequest = new SignInRequest()
                .setUserId("")
                .setPassword("12345678");
        Set<ConstraintViolation<SignInRequest>> violations = validator.validate(signInRequest);
        assertEquals(1, violations.size(), "Validation should fail for a invalid signIn request");
    }

    @Test
    public void test_create_signInRequest_with_blank_password_should_fail_validation() {
        SignInRequest signInRequest = new SignInRequest()
                .setUserId("12345678")
                .setPassword("");
        Set<ConstraintViolation<SignInRequest>> violations = validator.validate(signInRequest);
        assertEquals(1, violations.size(), "Validation should fail for a invalid signIn request");
    }
}

