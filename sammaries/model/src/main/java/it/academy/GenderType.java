package it.academy;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "`t_gendertype`")
public class GenderType {
    @Id
    @Column(name = "`IdGender`")
    private int id;

    @Column(name = "`Name`", nullable = false)
    private String name;

    @OneToMany(mappedBy = "gender")
    private List<Applicants> applicants;

}
