package cl.losguindos.UserSystemBackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;


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

    @ManyToMany(mappedBy = "rolePrivileges")
    @EqualsAndHashCode.Exclude
    private Set<Role> privRoles;

    public Privilege toPrivilege(){
        return new Privilege(
                this.privId,
                this.privName,
                null
        );
    }

}




