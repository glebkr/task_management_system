package member.mapper;

import org.springframework.stereotype.Component;

import member.dto.MemberDTO;
import member.entity.Member;

@Component
public class MemberDTOtoEntityMapping {
    public Member mapMemberDTOtoEntity(MemberDTO member) {
        return Member.builder()
                .name(member.getName())
                .surname(member.getSurname())
                .grade(member.getGrade())
                .specialization(member.getSpecialization())
                .build();
    }
}
