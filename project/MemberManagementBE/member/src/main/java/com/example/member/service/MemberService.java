// 6. src/main/java/com/example/member/service/MemberService.java
package com.example.member.service;

import com.example.member.dto.MemberDto;
import com.example.member.entity.Member;
import com.example.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    // 회원 등록
    public MemberDto.Response createMember(MemberDto.Request request) {
        // 이메일 중복 체크
        if (memberRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        Member member = request.toEntity();
        Member savedMember = memberRepository.save(member);
        return MemberDto.Response.from(savedMember);
    }

    // 회원 수정
    public MemberDto.Response updateMember(Long id, MemberDto.Request request) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        // 이메일 중복 체크 (본인 제외)
        memberRepository.findByEmail(request.getEmail())
                .ifPresent(existingMember -> {
                    if (!existingMember.getId().equals(id)) {
                        throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
                    }
                });

        member.setName(request.getName());
        member.setAge(request.getAge());
        member.setGender(request.getGender());
        member.setEmail(request.getEmail());
        member.setPhone(request.getPhone());
        member.setAddress(request.getAddress());

        Member updatedMember = memberRepository.save(member);
        return MemberDto.Response.from(updatedMember);
    }

    // 회원 삭제
    public void deleteMember(Long id) {
        if (!memberRepository.existsById(id)) {
            throw new IllegalArgumentException("회원을 찾을 수 없습니다.");
        }
        memberRepository.deleteById(id);
    }

    // 회원 조회
    @Transactional(readOnly = true)
    public MemberDto.Response getMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
        return MemberDto.Response.from(member);
    }

    // 전체 회원 조회
    @Transactional(readOnly = true)
    public List<MemberDto.Response> getAllMembers() {
        return memberRepository.findAll().stream()
                .map(MemberDto.Response::from)
                .collect(Collectors.toList());
    }

    // 회원 검색
    @Transactional(readOnly = true)
    public List<MemberDto.Response> searchMembers(String keyword) {
        return memberRepository.searchMembers(keyword).stream()
                .map(MemberDto.Response::from)
                .collect(Collectors.toList());
    }

    // 통계 조회
    @Transactional(readOnly = true)
    public MemberDto.Statistics getStatistics() {
        Long totalMembers = memberRepository.count();
        Long maleMembers = memberRepository.countByGender("남성");
        Long femaleMembers = memberRepository.countByGender("여성");
        Double avgAge = memberRepository.findAverageAge();

        return MemberDto.Statistics.builder()
                .totalMembers(totalMembers)
                .maleMembers(maleMembers)
                .femaleMembers(femaleMembers)
                .avgAge(avgAge != null ? avgAge : 0.0)
                .build();
    }
}