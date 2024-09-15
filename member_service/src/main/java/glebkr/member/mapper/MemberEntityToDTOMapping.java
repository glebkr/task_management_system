package glebkr.member.mapper;

import org.springframework.stereotype.Component;

import glebkr.member.dto.MemberDTO;
import glebkr.member.entity.Member;

@Component
public class MemberEntityToDTOMapping {
    public MemberDTO mapMemberEntityToDto(Member member) {
        return MemberDTO.builder()
                .id(member.getId())
                .name(member.getName())
                .surname(member.getSurname())
                .grade(member.getGrade())
                .specialization(member.getSpecialization())
                .build();
    }
}
