package cl.losguindos.UserSystemBackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "priv_Id")
    private Long privId;

    @Enumerated(EnumType.STRING)
    private EPrivilege privName;

    @ManyToOne(fetch = FetchType.EAGER, cascade =CascadeType.PERSIST)
    @JoinTable(
            name = "role_privileges",
            joinColumns = @JoinColumn(name = "priv_Id"),
            inverseJoinColumns = @JoinColumn(name = "role_Id"))
    @EqualsAndHashCode.Exclude
    private Role privRole;

    public Privilege toPrivilege(){
        return new Privilege(
                this.privId,
                this.privName,
                null
        );
    }

}




