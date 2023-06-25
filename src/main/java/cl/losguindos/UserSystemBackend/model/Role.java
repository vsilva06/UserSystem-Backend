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

    @ManyToOne(fetch = FetchType.EAGER, cascade =CascadeType.ALL)
    @JoinTable(
            name = "role_account",
            joinColumns = @JoinColumn(name = "role_Id"),
            inverseJoinColumns = @JoinColumn(name = "acc_Id"))
    @EqualsAndHashCode.Exclude
    private Account roleAccount;
//
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accRole", fetch = FetchType.EAGER)
//    @EqualsAndHashCode.Exclude
//    private Set<Account> roleAccounts;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "privRole", fetch = FetchType.EAGER)
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
