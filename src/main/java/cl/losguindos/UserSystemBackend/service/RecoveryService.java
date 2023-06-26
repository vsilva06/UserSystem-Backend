package cl.losguindos.UserSystemBackend.service;

import cl.losguindos.UserSystemBackend.Utils.CustomResponse;
import cl.losguindos.UserSystemBackend.Utils.MyPasswordEncoder;
import cl.losguindos.UserSystemBackend.model.Account;
import cl.losguindos.UserSystemBackend.model.AuxUserPass;
import cl.losguindos.UserSystemBackend.model.Email;
import cl.losguindos.UserSystemBackend.model.dto.AccountDTO;
import cl.losguindos.UserSystemBackend.model.dto.UpdatePassRequest;
import cl.losguindos.UserSystemBackend.repository.AccountRepository;
import cl.losguindos.UserSystemBackend.repository.AuxUserPassRepository;
import cl.losguindos.UserSystemBackend.security.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class RecoveryService {

    @Autowired
    private EmailService emailService;
    @Autowired
    private ManageProfileService manageProfileService;
    @Autowired
    private AuxUserPassRepository auxUserPassRepository;
    @Autowired
    private AccountRepository repository;

    public String sendLink(AccountDTO accountDTO) {
//        if (accountDTO == null) {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            String email = authentication.getName();
//            if (email == null) {
//                throw new RuntimeException(CustomResponse.generateResponse("404", "User not found"));
//            }
//            emailService.sendCustomMailByMail(email);
//            return CustomResponse.generateResponse("200", "An email has been sent with the link to recover the password");
//        } else {
//        }
        try {
            Account account = repository.findByAccEmail(accountDTO.getAccEmail().toLowerCase()).orElseThrow();
            emailService.sendCustomMailByMail(account.getAccEmail());
            return CustomResponse.generateResponse("200", "An email has been sent with the link to recover the password");
        } catch (NoSuchElementException e) {
            throw new RuntimeException(CustomResponse.generateResponse("404", "User not found"));
        }
    }

    public String updatepass(UpdatePassRequest bodyjson) {
        try {
            AuxUserPass auxUser = auxUserPassRepository.findByAuxpassToken(bodyjson.getToken()).orElseThrow(() -> new RuntimeException(CustomResponse.generateResponse("404", "Token not found")));
            Account currentAccount = repository.findByAccEmail(auxUser.getAuxpassAccount().getAccEmail()).orElseThrow(() -> new RuntimeException(CustomResponse.generateResponse("404", "User not found")));
            if (bodyjson.getNewPassword().equals(bodyjson.getConfirmPassword())) {
                currentAccount.setAccPass(MyPasswordEncoder.encode(bodyjson.getNewPassword()));
                repository.save(currentAccount);
                auxUserPassRepository.delete(auxUser);
                return CustomResponse.generateResponse("200", "Password updated successfully");
            } else {
                throw new RuntimeException(CustomResponse.generateResponse("400", "Passwords do not match"));
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
