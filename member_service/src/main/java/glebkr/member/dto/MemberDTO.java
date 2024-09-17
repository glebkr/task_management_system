package glebkr.member.dto;

import java.util.UUID;

import glebkr.member.model.MemberGradeEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import glebkr.member.model.MemberSpecializationEnum;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
    private UUID id;
    @NotNull
    private String name;
    @NotNull
    private String surname;
    @NotNull
    private MemberGradeEnum grade;
    @NotNull
    private MemberSpecializationEnum specialization;
}
