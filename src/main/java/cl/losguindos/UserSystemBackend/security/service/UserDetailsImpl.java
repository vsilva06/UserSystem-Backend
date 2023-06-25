package cl.losguindos.UserSystemBackend.security.service;

import cl.losguindos.UserSystemBackend.model.Account;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {
  private static final long serialVersionUID = 1L;

  private Long accId;

  private String accName;

  private String accFirstName;

  private String accLastName;

  private String accEmail;

  @JsonIgnore
  private String accPass;

  private boolean accEnabled =true;

  private boolean accTokenExpired;

  private Collection<? extends GrantedAuthority> authorities;
//  private GrantedAuthority authorities;
  private LocalDateTime accCreatedDate;

  private LocalDateTime accLastModifiedDate;

  public UserDetailsImpl(Long accId, String accName, String accFirstName, String accLastName,
                         String accEmail, String accPass, boolean accEnabled,
                         boolean accTokenExpired,
                         Collection<? extends GrantedAuthority> authorities,
                         LocalDateTime accCreation_at, LocalDateTime accDate_update) {
    this.accId = accId;
    this.accName = accName;
    this.accFirstName = accFirstName;
    this.accLastName = accLastName;
    this.accEmail = accEmail;
    this.accPass = accPass;
    this.accEnabled = accEnabled;
    this.accTokenExpired = accTokenExpired;
    this.authorities = authorities;
    this.accCreatedDate = accCreation_at;
    this.accLastModifiedDate = accDate_update;
  }


//  public UserDetailsImpl(Long accId, String accName, String accFirstName,
//                         String accLastName, String accEmail, String accPass, boolean accEnabled,
//                         boolean accTokenExpired, GrantedAuthority authorities, LocalDateTime accCreatedDate,
//                         LocalDateTime accLastModifiedDate) {
//    this.accId = accId;
//    this.accName = accName;
//    this.accFirstName = accFirstName;
//    this.accLastName = accLastName;
//    this.accEmail = accEmail;
//    this.accPass = accPass;
//    this.accEnabled = accEnabled;
//    this.accTokenExpired = accTokenExpired;
//    this.authorities = authorities;
//    this.accCreatedDate = accCreatedDate;
//    this.accLastModifiedDate = accLastModifiedDate;
//  }

  public static UserDetailsImpl build(Account account) {
//    GrantedAuthority authority = (GrantedAuthority) account.getAccRole();
    List<GrantedAuthority> authorities = account.getAccRoles().stream()
        .map(privilege -> new SimpleGrantedAuthority(privilege.getRoleName()))
        .collect(Collectors.toList());

    return new UserDetailsImpl(
        account.getAccId(),
        account.getAccName(),
        account.getAccFirstName(),
        account.getAccLastName(),
        account.getAccEmail(),
        account.getAccPass(),
        account.isAccEnabled(),
        account.isAccTokenExpired(),
            authorities,
            account.getAccCreatedDate(),
            account.getAccLastModifiedDate());
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public Long getId() {
    return accId;
  }

  public String getEmail() {
    return accEmail;
  }

  @Override
  public String getPassword() {
    return accPass;
  }

  @Override
  public String getUsername() {
    return accName;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    UserDetailsImpl user = (UserDetailsImpl) o;
    return Objects.equals(accName, user.accId);
  }
}
