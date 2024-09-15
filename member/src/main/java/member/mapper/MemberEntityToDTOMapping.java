package member.mapper;

import org.springframework.stereotype.Component;

import member.dto.MemberDTO;
import member.entity.Member;

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
