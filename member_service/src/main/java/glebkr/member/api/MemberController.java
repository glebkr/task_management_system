package glebkr.member.api;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import glebkr.member.dto.MemberDTO;
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
    @CacheEvict(key = "'allMembers'", value = "members")
    public MemberDTO createMember(@RequestBody MemberDTO memberDTO) {
        MemberDTO createdPerson = memberService.createMember(memberDTO);
        Cache cache = cacheManager.getCache("member");
        if (cache != null) {
            cache.put(createdPerson.getId(), createdPerson);
        }
        return createdPerson;
    }

    @GetMapping
    @Cacheable(key = "'allMembers'", value = "members")
    public List<MemberDTO> getAllMembers() {
        return memberService.findAllMembers();
    }

    @GetMapping("/{memberId}")
    @Cacheable(key = "#memberId", value = "member")
    public MemberDTO getMember(@PathVariable("memberId") UUID memberId) {
        return memberService.findMemberById(memberId);
    }

    @PutMapping("/{memberId}")
    @Caching(put = @CachePut(key = "#memberId", value = "member"),
            evict = @CacheEvict(key = "'allMembers'", value = "members"))
    public MemberDTO updateMember(@PathVariable("memberId") UUID memberId, @RequestBody MemberDTO memberDTO) {
        return memberService.updateMember(memberId, memberDTO);
    }

    @PatchMapping("{memberId}")
    @Caching(put = @CachePut(key = "#memberId", value = "member"),
            evict = @CacheEvict(key = "'allMembers'", value = "members"))
    public MemberDTO updateMemberPartially(@PathVariable("memberId") UUID memberId, MemberDTO memberDTO) {
        return memberService.updateMemberPartially(memberId, memberDTO);
    }

    @DeleteMapping("/{memberId}")
    @Caching(evict = { @CacheEvict(key = "#memberId", value = "member"),
            @CacheEvict(key = "'allMembers'", value = "members") })
    public void deleteMember(@PathVariable("memberId") UUID memberId) {
        memberService.deleteMemberById(memberId);
    }

}
