// 5. src/main/java/com/example/member/repository/MemberRepository.java
package com.example.member.repository;

import com.example.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    // 이메일로 회원 찾기
    Optional<Member> findByEmail(String email);

    // 이름으로 회원 검색
    List<Member> findByNameContainingIgnoreCase(String name);

    // 이메일로 회원 검색
    List<Member> findByEmailContainingIgnoreCase(String email);

    // 연락처로 회원 검색
    List<Member> findByPhoneContaining(String phone);

    // 통합 검색 (이름, 이메일, 연락처)
    @Query("SELECT m FROM Member m WHERE " +
            "LOWER(m.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(m.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "m.phone LIKE CONCAT('%', :keyword, '%')")
    List<Member> searchMembers(@Param("keyword") String keyword);

    // 성별별 회원 수
    Long countByGender(String gender);

    // 평균 나이
    @Query("SELECT AVG(m.age) FROM Member m")
    Double findAverageAge();
}