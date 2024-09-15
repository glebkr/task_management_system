package member;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import member.entity.Member;
import member.model.MemberGradeEnum;
import member.model.MemberSpecializationEnum;
import member.repository.MemberRepository;
import member.service.MemberService;

@ExtendWith(MockitoExtension.class)
public class MemberServiceImplTest {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Test
    public void testGetMemberById() {
        Member mockMember = Member.builder()
                .name("Gleb")
                .surname("Kuryanov")
                .specialization(MemberSpecializationEnum.BACKEND)
                .grade(MemberGradeEnum.MIDDLE)
                .build();
        ;

        when(memberRepository.findById(1)).thenReturn(Optional.of(mockMember));

        Optional<Member> result = memberRepository.findById(1);

        assertTrue(result.isPresent());
        assertEquals(mockMember, result.get());
    }


}
