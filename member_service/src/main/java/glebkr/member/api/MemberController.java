package glebkr.member.api;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import glebkr.member.annotation.IdConstraint;
import glebkr.member.dto.MemberDTO;
import glebkr.member.exception.InvalidIdException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import glebkr.member.service.MemberService;

@RequestMapping(value = "/api/v1/member")
@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;
    private final CacheManager cacheManager;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MemberDTO createMember(@RequestBody MemberDTO memberDTO) {
        MemberDTO createdPerson = memberService.createMember(memberDTO);
        Cache cache = cacheManager.getCache("members");
        if (cache != null) {
            cache.put(createdPerson.getId(), createdPerson);
        }
        return createdPerson;
    }

    @GetMapping
    @Cacheable(value = "members")
    public List<MemberDTO> getAllMembers() {
        return memberService.findAllMembers();
    }

    @GetMapping("/{memberId}")
    @Cacheable(key = "#memberId", value = "members")
    public MemberDTO getMember(@PathVariable("memberId") UUID memberId) {
  //      checkId(memberId);
        return memberService.findMemberById(memberId);
    }

    @PutMapping("/{memberId}")
    @CachePut(key = "#memberId", value = "members")
    public MemberDTO updateMember(@PathVariable("memberId") UUID memberId, @RequestBody MemberDTO memberDTO) {
   //     checkId(memberId);
        return memberService.updateMember(memberId, memberDTO);
    }

    @DeleteMapping("/{memberId}")
    @CacheEvict(key = "#memberId", value = "members")
    public void deleteMember(@PathVariable("memberId") UUID memberId) {
    //    checkId(memberId);
        memberService.deleteMemberById(memberId);
    }

    private void checkId(Integer memberId) {
        if (memberId == null || memberId <= 0) {
            throw new InvalidIdException();
        }
    }

}
