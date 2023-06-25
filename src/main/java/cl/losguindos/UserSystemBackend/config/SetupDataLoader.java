package cl.losguindos.UserSystemBackend.config;

import cl.losguindos.UserSystemBackend.model.*;
import cl.losguindos.UserSystemBackend.repository.AccountRepository;
import cl.losguindos.UserSystemBackend.repository.PrivilegeRepository;
import cl.losguindos.UserSystemBackend.repository.RoleRepository;
import cl.losguindos.UserSystemBackend.security.jwt.AuthTokenFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
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

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup)
            return;

        for(ERole roletype : ERole.values()){
            Role role = new Role();
            role.setRoleName(roletype.name());
            createRoleIfNotFound(role);
        }





//
//        Role adminRole = new Role();
//        adminRole.setRoleName(ERole.ROLE_ADMIN.name());
//        for (Privilege privilege : Set.of(read, write, delete, update)) {
//            adminRole.getRolePrivileges().add(privilege);
//        }


//        adminPrivileges.addAll((Collection<? extends Privilege>) privilegeRepository.findAll());
//        Iterable<Privilege> moderatorPrivileges =(privilegeRepository.findAllById(Set.of(1L,2L,4L)));
//        Iterable<Privilege> userPrivileges =(privilegeRepository.findAllById(Set.of(1L,2L)));
//        Role adminRole = createRoleIfNotFound(ERole.ROLE_ADMIN.name(), adminPrivileges);
////        createRoleIfNotFound(ERole.ROLE_MODERATOR.name(), moderatorPrivileges);
//        createRoleIfNotFound(ERole.ROLE_USER.name(), userPrivileges);

//        boolean isUserCreated = accountRepository.findByAccEmail("test@test.com").isPresent();
//        System.out.println("isUserCreated: " + isUserCreated);
//        if (isUserCreated)
//            return;
//        Account account = new Account();
//        account.setAccEmail("test@test.com");
//        account.setAccName("Administrador");
//        account.setAccPass(MyPasswordEncoder.encode("admin"));
//        account.setRoles(Collections.singleton(adminRole));
//        accountRepository.save(account);
//
//        alreadySetup = true;
//    }
//
//
//    public Privilege createPrivilegeIfNotFound(EPrivilege name) {
//        Privilege privilege = new Privilege();
//        privilege.setPrivName(name);
//        return privilegeRepository.save(privilege);
//    }
//
//

    }
    public void createRoleIfNotFound(Role role) {
        Set<Privilege> adminPrivileges = new HashSet<>();
        Privilege read = new Privilege();
        read.setPrivName(EPrivilege.READ_PRIVILEGE);
        Privilege write = new Privilege();
        write.setPrivName(EPrivilege.WRITE_PRIVILEGE);
        Privilege delete = new Privilege();
        delete.setPrivName(EPrivilege.DELETE_PRIVILEGE);
        Privilege update = new Privilege();
        update.setPrivName(EPrivilege.UPDATE_PRIVILEGE);

        adminPrivileges.add(read);
        adminPrivileges.add(write);
        adminPrivileges.add(delete);
        adminPrivileges.add(update);

        read.setPrivRole(role);
        write.setPrivRole(role);
        delete.setPrivRole(role);
        update.setPrivRole(role);



        role.setRolePrivileges(adminPrivileges);

        roleRepository.save(role);
    }


}
