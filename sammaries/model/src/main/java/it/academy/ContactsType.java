package it.academy;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "`t_contactstype`")
public class ContactsType {
    @Id
    @Column(name = "`IdContactsType`")
    private int id;

    @Column(name = "`Name`", nullable = false)
    private String name;

    @Column(name = "`IsNotActual`", nullable = false)
    private int isNotActual;

    @OneToMany(mappedBy = "typeContact")
    private List<Contacts> contacts;
}
