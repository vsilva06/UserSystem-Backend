package cl.losguindos.UserSystemBackend.config;

import cl.losguindos.UserSystemBackend.model.*;
import cl.losguindos.UserSystemBackend.repository.AccountRepository;
import cl.losguindos.UserSystemBackend.repository.PrivilegeRepository;
import cl.losguindos.UserSystemBackend.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class SetupDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;

    private static final Logger logger = LoggerFactory.getLogger(SetupDataLoader.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup)
            return;
        Set<Privilege> adminPrivileges = new HashSet<>();

        for(EPrivilege privilegeType : EPrivilege.values()){
            Privilege privilege = privilegeRepository.findByPrivName(privilegeType).orElse(null);
            if (privilege != null) continue;
            privilege = new Privilege();
            privilege.setPrivName(privilegeType);
            adminPrivileges.add(privilege);
            privilegeRepository.save(privilege);
        }
        Role adminRole = createRoleIfNotFound(ERole.ROLE_ADMIN.name(), adminPrivileges);
        adminPrivileges.removeIf(privilege -> privilege.getPrivName().equals(EPrivilege.DELETE_PRIVILEGE));
        Role moderator = createRoleIfNotFound(ERole.ROLE_MODERATOR.name(), adminPrivileges);
        adminPrivileges.removeIf(privilege -> privilege.getPrivName().equals(EPrivilege.UPDATE_PRIVILEGE));
        Role user = createRoleIfNotFound(ERole.ROLE_USER.name(), adminPrivileges);


        Account adminAccount = new Account();
        Account moderatorAccount = new Account();
        Account userAccount = new Account();
        if (accountRepository.findByAccEmail("v.silva06@ufromail.cl").isEmpty()) {
            buildAccount(adminAccount, "Admin", "v.silva06@ufromail.cl", "admin", Set.of(adminRole));
            accountRepository.save(adminAccount);
            logger.info("Admin created {}", adminAccount);
        }
        if (accountRepository.findByAccEmail("moderator@moderator.com").isEmpty()) {
            buildAccount(moderatorAccount, "Moderator", "moderator@moderator.com", "moderator", Set.of(moderator));
            accountRepository.save(moderatorAccount);
            logger.info("Moderator created {}", moderatorAccount);
        }
        if (accountRepository.findByAccEmail("user@user.com").isEmpty()) {
            buildAccount(userAccount, "User", "user@user.com", "user", Set.of(user));
            accountRepository.save(userAccount);
            logger.info("User created {}", userAccount);
        }

        alreadySetup = true;
    }
    private void buildAccount(Account account, String name, String email, String pass, Set<Role> roles){
        account.setAccName(name);
        account.setAccEmail(email);
        account.setAccPass(encoder.encode(pass));
        account.setAccRoles(roles);

    }


    private Role createRoleIfNotFound(String name, Set<Privilege> adminPrivileges) {
        Role role = roleRepository.findByRoleName(name).orElse(null);
        if (role != null) return role;
        role = new Role();
        role.setRoleName(name);
        role.setRolePrivileges(adminPrivileges);
        return roleRepository.save(role);
    }

}
