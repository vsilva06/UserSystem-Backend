package cl.losguindos.UserSystemBackend.service;

import cl.losguindos.UserSystemBackend.Utils.JwtResponse;
import cl.losguindos.UserSystemBackend.Utils.MyPasswordEncoder;
import cl.losguindos.UserSystemBackend.model.Account;
import cl.losguindos.UserSystemBackend.model.ERole;
import cl.losguindos.UserSystemBackend.model.dto.AccountDTO;
import cl.losguindos.UserSystemBackend.repository.AccountRepository;
import cl.losguindos.UserSystemBackend.repository.RoleRepository;
import cl.losguindos.UserSystemBackend.security.jwt.AuthTokenFilter;
import cl.losguindos.UserSystemBackend.security.jwt.JwtUtil;
import cl.losguindos.UserSystemBackend.security.service.UserDetailsImpl;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ManageUsersService {

    @Autowired
    private AccountRepository repository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtil jwtUtil;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    public ResponseEntity<JwtResponse> login(AccountDTO loginRequest) throws JSONException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getAccEmail().toLowerCase(), loginRequest.getAccPass()));
        return getJwtResponseResponseEntity(authentication);
    }

    public ResponseEntity<JwtResponse> createUser(AccountDTO account) throws JSONException {
        if (!verifyEmail(account.getAccEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        try {
            Account newAccount = new Account();
            buildAccount(newAccount, account);
            repository.save(newAccount);
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(account.getAccEmail().toLowerCase(), account.getAccPass()));
            return getJwtResponseResponseEntity(authentication);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Error creating user");
        } catch (RuntimeException e){
            throw new RuntimeException("authentication error");
        }
    }

    private ResponseEntity<JwtResponse> getJwtResponseResponseEntity(Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getAccName(),
                roles));
    }

    private boolean verifyEmail(String email) {
        Optional<Account> userFound = repository.findByAccEmail(email.toLowerCase());
        return userFound.isEmpty();
    }

    private void buildAccount(Account newAccount, AccountDTO account) {
        newAccount.setAccName(account.getAccName());
        newAccount.setAccFirstName(account.getAccFirstName());
        newAccount.setAccLastName(account.getAccLastName());
        newAccount.setAccEmail(account.getAccEmail().toLowerCase());
        newAccount.setAccPass(encoder.encode(account.getAccPass()));
        newAccount.setAccRoles(Set.of(roleRepository.findByRoleName(ERole.ROLE_USER.name()).orElse(null)));
    }

    private boolean verifyPass(String accPass, String accPass1) {
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        return argon2.verify(accPass, accPass1.toCharArray());
    }


}
