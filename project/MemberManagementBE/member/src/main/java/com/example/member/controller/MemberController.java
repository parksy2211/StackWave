// 7. src/main/java/com/example/member/controller/MemberController.java
package com.example.member.controller;

import com.example.member.dto.MemberDto;
import com.example.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // 프론트엔드 연동을 위한 CORS 설정
public class MemberController {

    private final MemberService memberService;

    // 회원 등록
    @PostMapping
    public ResponseEntity<MemberDto.Response> createMember(@Valid @RequestBody MemberDto.Request request) {
        MemberDto.Response response = memberService.createMember(request);
        return ResponseEntity.ok(response);
    }

    // 회원 수정
    @PutMapping("/{id}")
    public ResponseEntity<MemberDto.Response> updateMember(@PathVariable Long id, @Valid @RequestBody MemberDto.Request request) {
        MemberDto.Response response = memberService.updateMember(id, request);
        return ResponseEntity.ok(response);
    }

    // 회원 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }

    // 회원 조회
    @GetMapping("/{id}")
    public ResponseEntity<MemberDto.Response> getMember(@PathVariable Long id) {
        MemberDto.Response response = memberService.getMember(id);
        return ResponseEntity.ok(response);
    }

    // 전체 회원 조회
    @GetMapping
    public ResponseEntity<List<MemberDto.Response>> getAllMembers() {
        List<MemberDto.Response> responses = memberService.getAllMembers();
        return ResponseEntity.ok(responses);
    }

    // 회원 검색
    @GetMapping("/search")
    public ResponseEntity<List<MemberDto.Response>> searchMembers(@RequestParam String keyword) {
        List<MemberDto.Response> responses = memberService.searchMembers(keyword);
        return ResponseEntity.ok(responses);
    }

    // 통계 조회
    @GetMapping("/statistics")
    public ResponseEntity<MemberDto.Statistics> getStatistics() {
        MemberDto.Statistics statistics = memberService.getStatistics();
        return ResponseEntity.ok(statistics);
    }
}
