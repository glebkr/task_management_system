package member.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import member.model.MemberGradeEnum;
import member.model.MemberSpecializationEnum;

@Entity
@Table(name = "member")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue
    private Integer id;
    @NotNull
    private String name;
    @NotNull
    private String surname;
    @Enumerated(EnumType.STRING)
    @NotNull
    private MemberGradeEnum grade;
    @Enumerated(EnumType.STRING)
    @NotNull
    private MemberSpecializationEnum specialization;
}
