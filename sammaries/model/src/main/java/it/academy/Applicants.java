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
@Table(name = "`t_applicants`")
public class Applicants {
     @Id
     @Column(name = "`IdApplicant`")
     @GeneratedValue(generator = "uuid-generator")
     @GenericGenerator(name = "uuid-generator", strategy = "uuid")
     private String id;

     @Column(name = "`FirstName`", nullable = false)
     private String firstName;

     @Column(name = "`LastName`", nullable = false)
     private String lastName;

     @Column(name = "`Patronymic`")
     private String patronymic;

     @Column(name = "`DateBirth`", nullable = false)
     private Date dateBirth;

     @ManyToOne
     @JoinColumn(name = "`IdGender`")
     private GenderType gender;

     @Column(name = "`DateCreate`", nullable = false)
     private Date dateCreate;

     @Column(name = "`DateFinish`", nullable = false)
     private Date dateFinish;

     @OneToMany(mappedBy = "applicant")
     private List<Summaries> summaries;

     @OneToMany(mappedBy = "applicant")
     List<Contacts> contacts;

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
