package com.sparta.vicky.user.dto;

import jakarta.validation.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

class SignupRequestTest {

    private final Validator validator;

    public SignupRequestTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    @DisplayName("회원가입 요청 DTO 생성 성공 ")
    void createSignupRequestDTO_success() {
        // given
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("aAbBcCdDeE12345");
        signupRequest.setPassword("aAbBcC1$3$5");
        signupRequest.setName("testName");
        signupRequest.setEmail("test@google.com");
        signupRequest.setIntroduce("test introduce. I'm test");

        // when
        SignupRequestTest validator = new SignupRequestTest();
        List<String> messages = validator.validate(signupRequest);

        // then
        assertThat(messages).isEmpty();
    }

    @Test
    @DisplayName("회원가입 요청 DTO 생성 실패 - 잘못된 username")
    void createSignupRequestDTO_fail_WrongUsername() {

        // given
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("#abcde");
        signupRequest.setPassword("aAbBcC1$3$5");
        signupRequest.setName("testName");
        signupRequest.setEmail("test@google.com");
        signupRequest.setIntroduce("test introduce. I'm test");

        // when
        SignupRequestTest validator = new SignupRequestTest();
        List<String> messages = validator.validate(signupRequest);

        // then
        assertThat(messages).hasSize(2);
        assertThat(messages)
                .containsExactlyInAnyOrder(
                        "ID는 최소 10자 이상, 최대 20자 이하이어야 합니다.",
                        "ID는 영문 또는 숫자만 가능합니다."
                );
    }

    private List<String> validate(SignupRequest signupRequest) {
        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signupRequest);
        List<String> messages = new ArrayList<>();
        for (ConstraintViolation<SignupRequest> violation : violations) {
            messages.add(violation.getMessage());
        }
        return messages;
    }

//    private Set<ConstraintViolation<SignupRequest>> validate(SignupRequest signupRequest) {
//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        Validator validator = factory.getValidator();
//        return validator.validate(signupRequest);
//    }
}