package glebkr.member.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import glebkr.member.dto.MemberDTO;
import glebkr.member.entity.Member;
import glebkr.member.exception.MemberNotFoundException;
import glebkr.member.mapper.MemberDTOToEntityMapping;
import glebkr.member.mapper.MemberEntityToDTOMapping;
import glebkr.member.model.MemberGradeEnum;
import glebkr.member.model.MemberSpecializationEnum;
import glebkr.member.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
public class MemberServiceImplTest {

    @InjectMocks
    private MemberServiceImpl memberServiceImpl;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private MemberEntityToDTOMapping memberEntityToDTOMapping;

    @Mock
    private MemberDTOToEntityMapping memberDTOtoEntityMapping;

    private MemberDTO memberDTO;
    private Member member;

    @BeforeEach
    public void setUp() {
        UUID memberId = UUID.randomUUID();
        memberDTO = MemberDTO.builder()
                .id(memberId)
                .name("Gleb")
                .surname("Kuryanov")
                .specialization(MemberSpecializationEnum.BACKEND)
                .grade(MemberGradeEnum.MIDDLE)
                .build();

        member = Member.builder()
                .id(memberId)
                .name("Gleb")
                .surname("Kuryanov")
                .specialization(MemberSpecializationEnum.BACKEND)
                .grade(MemberGradeEnum.MIDDLE)
                .build();
    }

    @Test
    public void createMember_ShouldSaveAndReturnMember_WhenValidInput() {
        when(memberDTOtoEntityMapping.mapMemberDTOtoEntity(any(MemberDTO.class))).thenReturn(member);
        when(memberRepository.save(any(Member.class))).thenReturn(member);
        when(memberEntityToDTOMapping.mapMemberEntityToDto(any(Member.class))).thenReturn(memberDTO);

        MemberDTO result = memberServiceImpl.createMember(memberDTO);

        assertNotNull(result);
        assertEquals(member.getId(), result.getId());
    }

    @Test
    public void finaAllMembers_ShouldReturnAllMembers_WhenMembersExist() {
        UUID memberId = UUID.randomUUID();
        Member member1 = Member.builder()
                .id(memberId)
                .name("Ryan")
                .surname("Gosling")
                .specialization(MemberSpecializationEnum.FRONTEND)
                .grade(MemberGradeEnum.JUNIOR)
                .build();
        MemberDTO member1DTO = MemberDTO.builder()
                .id(memberId)
                .name("Ryan")
                .surname("Gosling")
                .specialization(MemberSpecializationEnum.FRONTEND)
                .grade(MemberGradeEnum.JUNIOR)
                .build();


        when(memberRepository.findAll()).thenReturn(List.of(member, member1));
        when(memberEntityToDTOMapping.mapMemberEntityToDto(member)).thenReturn(memberDTO);
        when(memberEntityToDTOMapping.mapMemberEntityToDto(member1)).thenReturn(member1DTO);

        List<MemberDTO> result = memberServiceImpl.findAllMembers();

        assertNotNull(result);
        assertEquals(result.size(), 2);
        assertEquals(result.get(0).getName(), "Gleb");
        assertEquals(result.get(1).getName(), "Ryan");
    }

    @Test
    public void findMemberById_ShouldReturnMember_WhenMemberExists() {
        when(memberRepository.findById(memberDTO.getId())).thenReturn(Optional.of(member));
        when(memberEntityToDTOMapping.mapMemberEntityToDto(any(Member.class))).thenReturn(memberDTO);

        MemberDTO result = memberServiceImpl.findMemberById(member.getId());

        assertNotNull(result);
        assertEquals(member.getId(), result.getId());
    }

    @Test
    public void getMember_ShouldThrowMemberNotFoundException_WhenNoMemberExists() {
        UUID nonExistentMemberId = UUID.randomUUID();
        when(memberRepository.findById(nonExistentMemberId)).thenThrow(new MemberNotFoundException(nonExistentMemberId));

        assertThrows(MemberNotFoundException.class, () ->
                memberServiceImpl.findMemberById(nonExistentMemberId)
        );
    }

    @Test
    public void updateMember_ShouldUpdateMember_WhenValidInput() {
        when(memberRepository.findById(member.getId())).thenReturn(Optional.of(member));
        when(memberRepository.save(any(Member.class))).thenReturn(member);
        when(memberEntityToDTOMapping.mapMemberEntityToDto(any(Member.class))).thenReturn(memberDTO);

        MemberDTO result = memberServiceImpl.updateMember(member.getId(), memberDTO);

        assertNotNull(result);
        assertEquals(member.getId(), result.getId());
    }

    @Test
    public void deleteMemberById_ShouldDeleteMember_WhenMemberExists() {
        doNothing().when(memberRepository).deleteById(member.getId());
        when(memberRepository.findById(member.getId())).thenReturn(Optional.of(member));

        memberServiceImpl.deleteMemberById(member.getId());

        verify(memberRepository, times(1)).deleteById(member.getId());
        verify(memberRepository, times(1)).findById(member.getId());
    }


}
