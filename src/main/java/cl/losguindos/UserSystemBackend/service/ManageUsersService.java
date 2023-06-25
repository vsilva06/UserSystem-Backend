package cl.losguindos.UserSystemBackend.service;

import cl.losguindos.UserSystemBackend.Utils.CustomResponse;
import cl.losguindos.UserSystemBackend.model.Account;
import cl.losguindos.UserSystemBackend.model.ERole;
import cl.losguindos.UserSystemBackend.model.Role;
import cl.losguindos.UserSystemBackend.model.dto.AccountDTO;
import cl.losguindos.UserSystemBackend.repository.AccountRepository;
import cl.losguindos.UserSystemBackend.repository.RoleRepository;
import cl.losguindos.UserSystemBackend.security.jwt.AuthTokenFilter;
import cl.losguindos.UserSystemBackend.security.jwt.JwtUtil;
import cl.losguindos.UserSystemBackend.security.service.UserDetailsImpl;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ManageUsersService {

    @Autowired
    private AccountRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    JwtUtil jwtUtil;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    public String login(AccountDTO loginRequest) throws JSONException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String jwt = jwtUtil.generateToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return CustomResponse.tokenResponse(jwt);
    }

    public Account createUser(AccountDTO account) {
        if (!verifyEmail(account.getAccEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        try {
            Account newAccount = new Account();
            buildAccount(newAccount, account);
            Role userRole = new Role();
            userRole = roleRepository.findByRoleName(ERole.ROLE_USER.name())
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            userRole.setRoleAccount(newAccount);
//            Set<Role> roles = new HashSet<Role>();
//            roles.add(userRole);
//            roleRepository.save(userRole);
            newAccount.getAccRoles().add(userRole);
//            newAccount.addRole(userRole);
            return repository.save(newAccount);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error creating user");
        }
    }

    private boolean verifyEmail(String email) {
        System.out.println("email: " + email);
        Optional<Account> userFound = repository.findByAccEmail(email);
        return userFound.isEmpty();
    }

    private void buildAccount(Account newAccount, AccountDTO account) {
        newAccount.setAccName(account.getAccName());
        newAccount.setAccFirstName(account.getAccFirstName());
        newAccount.setAccLastName(account.getAccLastName());
        newAccount.setAccEmail(account.getAccEmail());
        newAccount.setAccPass(account.getAccPass());
    }


}
