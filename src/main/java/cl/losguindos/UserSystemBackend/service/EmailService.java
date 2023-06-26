package cl.losguindos.UserSystemBackend.service;

import cl.losguindos.UserSystemBackend.model.Account;
import cl.losguindos.UserSystemBackend.model.AuxUserPass;
import cl.losguindos.UserSystemBackend.model.Email;
import cl.losguindos.UserSystemBackend.repository.AccountRepository;
import cl.losguindos.UserSystemBackend.repository.AuxUserPassRepository;
import cl.losguindos.UserSystemBackend.security.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class EmailService {

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AuxUserPassRepository auxUserPassRepository;

    @Autowired
    JwtUtil jwtUtil;


    public void sendMail(String receiver, String subject, String body) {

    }

    public void sendCustomMail(Email email) {

    }

    public void sendCustomMailByMail(String emailusr) {
        Email email = new Email();
        Account currentAccount = accountRepository.findByAccEmail(emailusr).orElse(null);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(emailusr);
        assert currentAccount != null;
        String token = jwtUtil.generateToken(authentication);


        AuxUserPass auxUserPass = new AuxUserPass();
        auxUserPass.setAuxpassAccount(currentAccount);
        auxUserPass.setAuxpassToken(token);

        auxUserPassRepository.save(auxUserPass);
        String link = "http://localhost:8080/user/password/recovery" + token;
        email.setEmail_to(currentAccount.getAccEmail());
        email.setEmail_subject("Password recovery");
        email.setEmail_body("Hello " + currentAccount.getAccName() + " " + currentAccount.getAccFirstName() + " To recover your password, go to the following link:" + link);

        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(email.getEmail_to());
        mensaje.setSubject(email.getEmail_subject());
        mensaje.setText(email.getEmail_body());
        javaMailSender.send(mensaje);
    }
}
