package member.api;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import member.dto.MemberDTO;
import member.exception.InvalidIdException;
import member.service.MemberService;

@Validated
@RequestMapping(value = "/api/v1/member")
@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public MemberDTO createMember(@RequestBody MemberDTO memberDTO) {
        return memberService.createMember(memberDTO);
    }

    @GetMapping("/get/all")
    public List<MemberDTO> getAllMembers() {
        return memberService.findAllMembers();
    }

    @PostMapping("/get/{memberId}")
    public MemberDTO getMember(@PathVariable("memberId") Integer memberId) {
        checkId(memberId);
        return memberService.findMemberById(memberId);
    }

    @PutMapping("/update/{memberId}")
    public MemberDTO updateMember(@PathVariable("memberId") Integer memberId, @RequestBody MemberDTO memberDTO) {
        checkId(memberId);
        return memberService.updateMember(memberId, memberDTO);
    }

    @DeleteMapping("/delete/{memberId}")
    public void deleteMember(@PathVariable("memberId") Integer memberId) {
        checkId(memberId);
        memberService.deleteMemberById(memberId);
    }

    private void checkId(Integer memberId) {
        if (memberId == null || memberId <= 0) {
            throw new InvalidIdException();
        }
    }

}
