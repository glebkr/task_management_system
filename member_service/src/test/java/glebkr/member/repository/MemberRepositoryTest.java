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

import glebkr.member.entity.Member;
import glebkr.member.model.MemberGradeEnum;
import glebkr.member.model.MemberSpecializationEnum;
import glebkr.member.repository.MemberRepository;


@DataJpaTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    private Member member;

    @BeforeEach
    public void setUp() {
        member = Member.builder()
                .id(1)
                .name("Gleb")
                .surname("Kuryanov")
                .specialization(MemberSpecializationEnum.BACKEND)
                .grade(MemberGradeEnum.MIDDLE)
                .build();
    }

    @Test
    public void testSaveMember() {
        Member savedMember = memberRepository.save(member);

        assertThat(savedMember).isNotNull();
        assertThat(savedMember.getId()).isNotNull();
        assertThat(savedMember.getName()).isEqualTo("Gleb");
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

        memberRepository.saveAllAndFlush(List.of(member, member1));

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
    public void testGetMember() {
        Member savedMember = memberRepository.saveAndFlush(member);

        Optional<Member> foundMember = memberRepository.findById(savedMember.getId());

        assertTrue(foundMember.isPresent());
        assertNotNull(foundMember.get().getId());
        assertEquals(foundMember.get().getName(), "Gleb");
    }

    @Test
    public void testUpdateMember() {
        Member savedMember = memberRepository.saveAndFlush(member);

        savedMember.setName("Updated name");
        Member updatedMember = memberRepository.saveAndFlush(savedMember);

        assertThat(updatedMember).isNotNull();
        assertThat(updatedMember.getId()).isNotNull();
        assertThat(updatedMember.getName()).isEqualTo("Updated name");
    }

    @Test
    public void testDeleteMember() {
        Member savedMember = memberRepository.save(member);

        Optional<Member> foundMember = memberRepository.findById(savedMember.getId());
        assertTrue(foundMember.isPresent());

        memberRepository.deleteById(savedMember.getId());

        Optional<Member> deletedMember = memberRepository.findById(savedMember.getId());

        assertFalse(deletedMember.isPresent());
    }

}
