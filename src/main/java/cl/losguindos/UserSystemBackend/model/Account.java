package cl.losguindos.UserSystemBackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "acc_Id")
    private Long accId;

    private String accName;

    private String accFirstName;

    private String accLastName;

    private String accEmail;

    private String accPass;

    private boolean accEnabled =true;

    private boolean accTokenExpired;

    @CreatedDate
    private LocalDateTime accCreatedDate = LocalDateTime.now();

    @LastModifiedDate
    private LocalDateTime accLastModifiedDate = LocalDateTime.now();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "account_role",
            joinColumns = @JoinColumn(name = "acc_Id"),
            inverseJoinColumns = @JoinColumn(name = "role_Id")
    )
    @EqualsAndHashCode.Exclude
    private Set<Role> accRoles;

    public Account pulicAccount(){
        return new Account(
                this.accId,
                this.accName,
                this.accFirstName,
                this.accLastName,
                this.accEmail,
                null,
                this.accEnabled,
                false,
                this.accCreatedDate,
                null,
                null
        );
    }

    public Account privateAccount(){
        return new Account(
                this.accId,
                this.accName,
                this.accFirstName,
                this.accLastName,
                this.accEmail,
                this.accPass,
                this.accEnabled,
                this.accTokenExpired,
                this.accCreatedDate,
                this.accLastModifiedDate,
                this.accRoles.stream().map(Role::toRole).collect(Collectors.toSet())
        );
    }

    public Account toadminAccount(){
        return new Account(
                this.accId,
                this.accName,
                this.accFirstName,
                this.accLastName,
                this.accEmail,
                null,
                this.accEnabled,
                this.accTokenExpired,
                this.accCreatedDate,
                this.accLastModifiedDate,
                this.accRoles.stream().map(Role::toRole).collect(Collectors.toSet())
        );
    }

    public void setToken(String token) {

    }
}
