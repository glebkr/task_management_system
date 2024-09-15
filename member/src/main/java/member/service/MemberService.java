package member.service;

import java.util.List;

import member.dto.MemberDTO;
import member.exception.MemberNotFoundException;

public interface MemberService {
    MemberDTO createMember(MemberDTO memberDTO);

    List<MemberDTO> findAllMembers();

    MemberDTO findMemberById(Integer id);

    MemberDTO updateMember(Integer memberId, MemberDTO memberDTO);

    void deleteMemberById(Integer id);

}
