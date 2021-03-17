package it.academy;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "`t_summaries`")
public class Summaries {
    @Id
    @Column(name = "`IdSummary`")
    @GeneratedValue(generator = "uuid-generator")
    @GenericGenerator(name = "uuid-generator", strategy = "uuid")
    private String id;

    @Column(name = "`DateCreate`", nullable = false)
    private Date dateCreate;

    @Column(name = "`DateFinish`", nullable = false)
    private Date dateFinish;

    @ManyToOne
    @JoinColumn(name = "`IdApplicant`")
    private Applicants applicant;

    @ManyToMany
    @JoinTable(
      name = "`t_competences_summaries`",
            joinColumns = {@JoinColumn(name = "`IdSummary`")},
            inverseJoinColumns = {@JoinColumn(name = "`IdCompetence`")}

    )
    private List<CompetencesType> competences;

    @PrePersist
    public void prePersist(){
        if(dateCreate == null){
            dateCreate = Date.valueOf("01-01-1900");
        }

        if(dateFinish == null){
            dateFinish = Date.valueOf("01-01-2900");
        }
    }
}
