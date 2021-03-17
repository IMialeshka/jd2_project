package it.academy;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "`t_competencestype`")
public class CompetencesType {
    @Id
    @Column(name = "`IdCompetence`")
    private int id;

    @Column(name = "`Name`", nullable = false)
    private String name;

    @Column(name = "`IsNotActual`", nullable = false)
    private int isNotActual;

    @ManyToMany
    @JoinTable(
            name = "`t_competences_summaries`",
            joinColumns = {@JoinColumn(name = "`IdCompetence`")},
            inverseJoinColumns = {@JoinColumn(name = "`IdSummary`")}

    )
    private List<Summaries> summaries;

}
