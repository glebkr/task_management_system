package glebkr.member.api;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import glebkr.member.config.TestCachingConfig;
import glebkr.member.dto.MemberDTO;
import glebkr.member.model.MemberGradeEnum;
import glebkr.member.model.MemberSpecializationEnum;
import glebkr.member.service.MemberService;

@WebMvcTest(MemberController.class)
@Import(TestCachingConfig.class)
// or @SpringBootTest + @AutoConfigureMockMvc for integration tests
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CacheManager cacheManager;

    @MockBean
    private MemberService memberService;

    private MemberDTO memberDTO;

    @BeforeEach
    public void setUp() {
        memberDTO = MemberDTO.builder()
                .id(1)
                .name("Gleb")
                .surname("Kuryanov")
                .specialization(MemberSpecializationEnum.BACKEND)
                .grade(MemberGradeEnum.MIDDLE)
                .build();
    }

    @Test
    public void testCreateMember() throws Exception {
        when(memberService.createMember(any(MemberDTO.class))).thenReturn(memberDTO);

        String memberJson = objectMapper.writeValueAsString(memberDTO);

        mockMvc.perform(post("/api/v1/member")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(memberJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Gleb"));
    }

    @Test
    public void testGetAllMembers() throws Exception {
        MemberDTO memberDTO1 = MemberDTO.builder()
                .id(2)
                .name("Ryan")
                .surname("Gosling")
                .specialization(MemberSpecializationEnum.ANDROID)
                .grade(MemberGradeEnum.SENIOR)
                .build();

        when(memberService.findAllMembers()).thenReturn(Arrays.asList(memberDTO, memberDTO1));

        mockMvc.perform(get("/api/v1/member"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Gleb"))
                .andExpect(jsonPath("$[1].name").value("Ryan"));

        verify(memberService, times(1)).findAllMembers();
    }

    @Test
    public void testGetMember() throws Exception {
        when(memberService.findMemberById(memberDTO.getId())).thenReturn(memberDTO);

        mockMvc.perform(get("/api/v1/member/{id}", memberDTO.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Gleb"));

        verify(memberService, times(1)).findMemberById(memberDTO.getId());
    }

    @Test
    public void testUpdateMember() throws Exception {
        when(memberService.updateMember(eq(memberDTO.getId()), any(MemberDTO.class))).thenReturn(memberDTO);

        String memberJson = objectMapper.writeValueAsString(memberDTO);

        mockMvc.perform(put("/api/v1/member/{id}", memberDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(memberJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(memberDTO.getId()));

        verify(memberService, times(1)).updateMember(eq(memberDTO.getId()), any(MemberDTO.class));
    }

    @Test
    public void testDeleteMember() throws Exception {
        doNothing().when(memberService).deleteMemberById(memberDTO.getId());

        mockMvc.perform(delete("/api/v1/member/{id}", memberDTO.getId()))
                .andExpect(status().isOk());

        verify(memberService, times(1)).deleteMemberById(memberDTO.getId());
    }


}
