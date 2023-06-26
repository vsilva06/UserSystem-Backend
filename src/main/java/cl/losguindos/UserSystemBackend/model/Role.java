package cl.losguindos.UserSystemBackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_Id")
    private Long roleId;

    private String roleName;

    @ManyToMany(mappedBy = "accRoles")
    @EqualsAndHashCode.Exclude
    private Set<Account> roleAccounts;

    @ManyToMany
    @JoinTable(
            name = "role_privilege",
            joinColumns = @JoinColumn(name = "role_Id"),
            inverseJoinColumns = @JoinColumn(name = "privilege_Id")
    )
    @EqualsAndHashCode.Exclude
    private Set<Privilege> rolePrivileges;

    public Role toRole(){
        return new Role(
                this.roleId,
                this.roleName,
                null,
                this.rolePrivileges.stream().map(Privilege::toPrivilege).collect(Collectors.toSet())
        );
    }

}
