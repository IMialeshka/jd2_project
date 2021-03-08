package it.academy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "`t_contacts`")
public class Contacts {

    @Id
    @Column(name = "`IdContact`")
    @GeneratedValue(generator = "uuid-generator")
    @GenericGenerator(name = "uuid-generator", strategy = "uuid")
    private String id;

    @ManyToOne
    @JoinColumn(name = "`IdContactsType`")
    private ContactsType typeContact;

    @Column(name = "`Contact`", nullable = false)
    private String contact;

    @Column(name = "`IsNotActual`", nullable = false)
    private int isNotActual;

    @ManyToOne
    @JoinColumn(name = "`IdApplicant`")
    private Applicants applicant;
}
