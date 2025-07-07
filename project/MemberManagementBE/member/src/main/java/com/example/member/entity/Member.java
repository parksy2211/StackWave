// 3. src/main/java/com/example/member/entity/Member.java
package com.example.member.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "members")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "이름은 필수입니다")
    @Size(min = 2, max = 50, message = "이름은 2-50자 사이여야 합니다")
    private String name;

    @Column(nullable = false)
    @NotNull(message = "나이는 필수입니다")
    @Min(value = 1, message = "나이는 1세 이상이어야 합니다")
    @Max(value = 120, message = "나이는 120세 이하여야 합니다")
    private Integer age;

    @Column(nullable = false)
    @NotBlank(message = "성별은 필수입니다")
    private String gender;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "이메일은 필수입니다")
    @Email(message = "올바른 이메일 형식이 아닙니다")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "연락처는 필수입니다")
    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "올바른 연락처 형식이 아닙니다")
    private String phone;

    @Column(nullable = false)
    @NotBlank(message = "주소는 필수입니다")
    private String address;

    @Column(nullable = false)
    private LocalDate joinDate;

    @PrePersist
    public void prePersist() {
        if (joinDate == null) {
            joinDate = LocalDate.now();
        }
    }
}