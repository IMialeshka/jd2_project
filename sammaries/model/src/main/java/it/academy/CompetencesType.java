package it.academy;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "`t_сompetencestype`")
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
            joinColumns = {@JoinColumn(name = "`IdSummary`")},
            inverseJoinColumns = {@JoinColumn(name = "`IdCompetence`")}

    )
    private List<Summaries> summaries;

}
