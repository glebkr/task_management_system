package glebkr.member.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import glebkr.member.dto.MemberDTO;
import glebkr.member.entity.Member;
import glebkr.member.mapper.MemberDTOtoEntityMapping;
import glebkr.member.mapper.MemberEntityToDTOMapping;
import glebkr.member.model.MemberGradeEnum;
import glebkr.member.model.MemberSpecializationEnum;
import glebkr.member.repository.MemberRepository;
import glebkr.member.service.MemberServiceImpl;

@ExtendWith(MockitoExtension.class)
public class MemberServiceImplTest {

    @InjectMocks
    private MemberServiceImpl memberServiceImpl;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private MemberEntityToDTOMapping memberEntityToDTOMapping;

    @Mock
    private MemberDTOtoEntityMapping memberDTOtoEntityMapping;

    private MemberDTO memberDTO;
    private Member member;

    @BeforeEach
    public void setUp() {
        memberDTO = MemberDTO.builder()
                .id(1)
                .name("Gleb")
                .surname("Kuryanov")
                .specialization(MemberSpecializationEnum.BACKEND)
                .grade(MemberGradeEnum.MIDDLE)
                .build();
        member = Member.builder()
                .id(1)
                .name("Gleb")
                .surname("Kuryanov")
                .specialization(MemberSpecializationEnum.BACKEND)
                .grade(MemberGradeEnum.MIDDLE)
                .build();
    }

    @Test
    public void testCreateMember() {
        when(memberDTOtoEntityMapping.mapMemberDTOtoEntity(any(MemberDTO.class))).thenReturn(member);
        when(memberRepository.saveAndFlush(any(Member.class))).thenReturn(member);
        when(memberEntityToDTOMapping.mapMemberEntityToDto(any(Member.class))).thenReturn(memberDTO);

        MemberDTO result = memberServiceImpl.createMember(memberDTO);

        assertNotNull(result);
        assertEquals(member.getId(), result.getId());
    }

    @Test
    public void testGetAllMembers() {
        Member member1 = Member.builder()
                .id(2)
                .name("Ryan")
                .surname("Gosling")
                .specialization(MemberSpecializationEnum.FRONTEND)
                .grade(MemberGradeEnum.JUNIOR)
                .build();
        MemberDTO member1DTO = MemberDTO.builder()
                .id(2)
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
    public void testGetMemberById() {
        when(memberRepository.findById(memberDTO.getId())).thenReturn(Optional.of(member));
        when(memberEntityToDTOMapping.mapMemberEntityToDto(any(Member.class))).thenReturn(memberDTO);

        MemberDTO result = memberServiceImpl.findMemberById(member.getId());

        assertNotNull(result);
        assertEquals(member.getId(), result.getId());
    }

    @Test
    public void testUpdateMember() {
        when(memberRepository.findById(member.getId())).thenReturn(Optional.of(member));
        when(memberRepository.saveAndFlush(any(Member.class))).thenReturn(member);
        when(memberEntityToDTOMapping.mapMemberEntityToDto(any(Member.class))).thenReturn(memberDTO);

        MemberDTO result = memberServiceImpl.updateMember(member.getId(), memberDTO);

        assertNotNull(result);
        assertEquals(member.getId(), result.getId());
    }

    @Test
    public void testDeleteMember() {
        doNothing().when(memberRepository).deleteById(member.getId());
        when(memberRepository.findById(member.getId())).thenReturn(Optional.of(member));

        memberServiceImpl.deleteMemberById(member.getId());

        verify(memberRepository, times(1)).deleteById(1);
        verify(memberRepository, times(1)).findById(1);
    }


}
