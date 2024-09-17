package glebkr.member.mapper;

import org.springframework.stereotype.Component;

import glebkr.member.dto.MemberDTO;
import glebkr.member.entity.Member;

@Component
public class MemberDTOToEntityMapping {
    public Member mapMemberDTOtoEntity(MemberDTO member) {
        return Member.builder()
                .name(member.getName())
                .surname(member.getSurname())
                .grade(member.getGrade())
                .specialization(member.getSpecialization())
                .build();
    }
}
