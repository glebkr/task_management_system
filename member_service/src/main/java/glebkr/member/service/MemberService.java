package glebkr.member.service;

import java.util.List;
import java.util.UUID;

import glebkr.member.dto.MemberDTO;

public interface MemberService {
    MemberDTO createMember(MemberDTO memberDTO);

    List<MemberDTO> findAllMembers();

    MemberDTO findMemberById(UUID memberId);

    MemberDTO updateMember(UUID memberId, MemberDTO memberDTO);

    void deleteMemberById(UUID memberId);

}
