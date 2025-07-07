// 4. src/main/java/com/example/member/dto/MemberDto.java
package com.example.member.dto;

import com.example.member.entity.Member;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class MemberDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        @NotBlank(message = "이름은 필수입니다")
        @Size(min = 2, max = 50, message = "이름은 2-50자 사이여야 합니다")
        private String name;

        @NotNull(message = "나이는 필수입니다")
        @Min(value = 1, message = "나이는 1세 이상이어야 합니다")
        @Max(value = 120, message = "나이는 120세 이하여야 합니다")
        private Integer age;

        @NotBlank(message = "성별은 필수입니다")
        private String gender;

        @NotBlank(message = "이메일은 필수입니다")
        @Email(message = "올바른 이메일 형식이 아닙니다")
        private String email;

        @NotBlank(message = "연락처는 필수입니다")
        @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "올바른 연락처 형식이 아닙니다")
        private String phone;

        @NotBlank(message = "주소는 필수입니다")
        private String address;

        public Member toEntity() {
            return Member.builder()
                    .name(name)
                    .age(age)
                    .gender(gender)
                    .email(email)
                    .phone(phone)
                    .address(address)
                    .joinDate(LocalDate.now())
                    .build();
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private String name;
        private Integer age;
        private String gender;
        private String email;
        private String phone;
        private String address;
        private String joinDate;

        public static Response from(Member member) {
            return Response.builder()
                    .id(member.getId())
                    .name(member.getName())
                    .age(member.getAge())
                    .gender(member.getGender())
                    .email(member.getEmail())
                    .phone(member.getPhone())
                    .address(member.getAddress())
                    .joinDate(member.getJoinDate().toString())
                    .build();
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Statistics {
        private Long totalMembers;
        private Long maleMembers;
        private Long femaleMembers;
        private Double avgAge;
    }
}