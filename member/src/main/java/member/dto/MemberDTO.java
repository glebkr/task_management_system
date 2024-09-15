package member.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import member.model.MemberGradeEnum;
import member.model.MemberSpecializationEnum;

@Data
@Builder
public class MemberDTO {
    private Integer id;
    @NotNull
    private String name;
    @NotNull
    private String surname;
    @NotNull
    private MemberGradeEnum grade;
    @NotNull
    private MemberSpecializationEnum specialization;
}
