package member;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import member.api.MemberController;
import member.dto.MemberDTO;
import member.model.MemberGradeEnum;
import member.model.MemberSpecializationEnum;
import member.service.MemberService;

@WebMvcTest(MemberController.class)
// or @SpringBootTest + @AutoConfigureMockMvc for integration tests
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @Test
    public void testGetAllMembers() throws Exception {
        MemberDTO mockMember = MemberDTO.builder()
                .id(1)
                .name("Gleb")
                .surname("Kuryanov")
                .specialization(MemberSpecializationEnum.BACKEND)
                .grade(MemberGradeEnum.MIDDLE)
                .build();

        when(memberService.findMemberById(1)).thenReturn(mockMember);

        mockMvc.perform(get("/api/v1/member/get/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Gleb"));
    }

}
