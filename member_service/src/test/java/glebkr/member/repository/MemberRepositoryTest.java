package glebkr.member.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import glebkr.member.entity.Member;
import glebkr.member.exception.MemberNotFoundException;
import glebkr.member.model.MemberGradeEnum;
import glebkr.member.model.MemberSpecializationEnum;


@DataJpaTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    private Member member;

    @BeforeEach
    public void setUp() {
        member = Member.builder()
                .name("Gleb")
                .surname("Kuryanov")
                .specialization(MemberSpecializationEnum.BACKEND)
                .grade(MemberGradeEnum.MIDDLE)
                .build();
    }

    @Test
    public void save_ShouldPersistMember_WhenValidMember() {
        Member savedMember = memberRepository.save(member);

        assertThat(savedMember).isNotNull();
        assertThat(savedMember.getId()).isNotNull();
        assertThat(savedMember.getName()).isEqualTo("Gleb");
    }

    @Test
    public void findAll_ShouldReturnAllMembers_WhenMembersExist() {
        Member member1 = Member.builder()
                .name("Ryan")
                .surname("Gosling")
                .specialization(MemberSpecializationEnum.FRONTEND)
                .grade(MemberGradeEnum.JUNIOR)
                .build();

        memberRepository.saveAll(List.of(member, member1));

        List<Member> memberList = memberRepository.findAll();

        assertThat(memberList.size()).isEqualTo(2);
        assertThat(memberList.get(0)).isNotNull();
        assertThat(memberList.get(1)).isNotNull();
        assertThat(memberList.get(0).getId()).isNotNull();
        assertThat(memberList.get(1).getId()).isNotNull();
        assertThat(memberList.get(0).getName()).isEqualTo("Gleb");
        assertThat(memberList.get(1).getName()).isEqualTo("Ryan");
    }

    @Test
    public void getMember_ShouldReturnMember_WhenMemberExists() {
        Member savedMember = memberRepository.save(member);

        Optional<Member> foundMember = memberRepository.findById(savedMember.getId());

        assertTrue(foundMember.isPresent());
        assertNotNull(foundMember.get().getId());
        assertEquals(foundMember.get().getName(), "Gleb");
    }

    @Test
    public void getMember_ShouldReturnEmptyOptional_WhenNoMemberExists() {
        UUID nonExistentMemberId = UUID.randomUUID();
        Optional<Member> foundMember = memberRepository.findById(nonExistentMemberId);

        assertFalse(foundMember.isPresent());
    }

    @Test
    public void updateMember_ShouldUpdateMember_WhenValidMember() {
        memberRepository.save(member);

        Member savedMember = memberRepository.findById(member.getId()).orElseThrow(() -> new MemberNotFoundException(member.getId()));

        savedMember.setName("Johny");
        savedMember.setSpecialization(MemberSpecializationEnum.ANDROID);

        memberRepository.save(savedMember);

        Member updatedMember = memberRepository.findById(member.getId()).orElseThrow(() -> new MemberNotFoundException(member.getId()));

        assertThat(updatedMember).isNotNull();
        assertThat(updatedMember.getId()).isNotNull();
        assertThat(updatedMember.getName()).isEqualTo("Johny");
        assertThat(updatedMember.getSpecialization()).isEqualTo(MemberSpecializationEnum.ANDROID);
    }

    @Test
    public void deleteById_ShouldDeleteMember_WhenMemberExists() {
        Member savedMember = memberRepository.save(member);

        Optional<Member> foundMember = memberRepository.findById(savedMember.getId());
        assertTrue(foundMember.isPresent());

        memberRepository.deleteById(savedMember.getId());

        Optional<Member> deletedMember = memberRepository.findById(savedMember.getId());

        assertFalse(deletedMember.isPresent());
    }

}
