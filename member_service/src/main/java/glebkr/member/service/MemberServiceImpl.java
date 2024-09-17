package glebkr.member.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import glebkr.member.dto.MemberDTO;
import glebkr.member.entity.Member;
import glebkr.member.exception.MemberNotFoundException;
import glebkr.member.mapper.MemberDTOToEntityMapping;
import glebkr.member.mapper.MemberEntityToDTOMapping;
import glebkr.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberEntityToDTOMapping memberEntityToDTOMapping;
    private final MemberDTOToEntityMapping memberDTOtoEntityMapping;

    @Override
    public MemberDTO createMember(MemberDTO memberDTO) {
        Member member = memberDTOtoEntityMapping.mapMemberDTOtoEntity(memberDTO);
        Member createdMember = memberRepository.save(member);
        return memberEntityToDTOMapping.mapMemberEntityToDto(createdMember);
    }

    @Override
    public List<MemberDTO> findAllMembers() {
        return memberRepository.findAll().stream().map(memberEntityToDTOMapping::mapMemberEntityToDto).collect(Collectors.toList());
    }

    @Override
    public MemberDTO findMemberById(UUID memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException(memberId));
        return memberEntityToDTOMapping.mapMemberEntityToDto(member);
    }

    @Override
    public MemberDTO updateMember(UUID memberId, MemberDTO memberDTO) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException(memberId));
        member.setName(memberDTO.getName());
        member.setSurname(memberDTO.getSurname());
        member.setSpecialization(memberDTO.getSpecialization());
        member.setGrade(memberDTO.getGrade());
        Member savedMember = memberRepository.save(member);
        return memberEntityToDTOMapping.mapMemberEntityToDto(savedMember);
    }

    @Override
    public void deleteMemberById(UUID memberId) {
        memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException(memberId));
        memberRepository.deleteById(memberId);
    }

}
