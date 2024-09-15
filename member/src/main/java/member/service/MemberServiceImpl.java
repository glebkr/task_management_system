package member.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import member.dto.MemberDTO;
import member.entity.Member;
import member.exception.MemberNotFoundException;
import member.mapper.MemberDTOtoEntityMapping;
import member.mapper.MemberEntityToDTOMapping;
import member.model.MemberGradeEnum;
import member.model.MemberSpecializationEnum;
import member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberEntityToDTOMapping memberEntityToDTOMapping;
    private final MemberDTOtoEntityMapping memberDTOtoEntityMapping;

    @Override
    public MemberDTO createMember(MemberDTO memberDTO) {
        Member member = memberDTOtoEntityMapping.mapMemberDTOtoEntity(memberDTO);
        Member createdMember = memberRepository.saveAndFlush(member);
        return memberEntityToDTOMapping.mapMemberEntityToDto(createdMember);
    }

    @Override
    public List<MemberDTO> findAllMembers() {
        return memberRepository.findAll().stream().map(memberEntityToDTOMapping::mapMemberEntityToDto).collect(Collectors.toList());
    }

    @Override
    public MemberDTO findMemberById(Integer memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException(memberId));
        return memberEntityToDTOMapping.mapMemberEntityToDto(member);
    }

    @Override
    @Transactional
    public MemberDTO updateMember(Integer memberId, MemberDTO memberDTO) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException(memberId));
        member.setName(memberDTO.getName());
        member.setSurname(memberDTO.getSurname());
        member.setSpecialization(memberDTO.getSpecialization());
        member.setGrade(memberDTO.getGrade());
        Member savedMember = memberRepository.saveAndFlush(member);
        return memberEntityToDTOMapping.mapMemberEntityToDto(savedMember);
    }

    @Override
    public void deleteMemberById(Integer id) {
        memberRepository.deleteById(id);
    }

    @PostConstruct
    public void addMembers() {
        Member member = Member.builder()
                .name("Gleb")
                .surname("Kuryanov")
                .specialization(MemberSpecializationEnum.BACKEND)
                .grade(MemberGradeEnum.MIDDLE)
                .build();
        memberRepository.save(member);
    }

}
