package com.example.boot_sns.service;

import com.example.boot_sns.model.Member;
import com.example.boot_sns.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public void save(Member member) {
        memberRepository.save(member);
    }

    public List<Member> findAll(){
        return memberRepository.findAll();
    }

    public Member login(String username, String password) {
        Optional<Member> member = memberRepository.findByUsername(username);
        if (member.isPresent() && member.get().getPassword().equals(password)) {
            return member.get();
        }
        return null;
    }

    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }
}
