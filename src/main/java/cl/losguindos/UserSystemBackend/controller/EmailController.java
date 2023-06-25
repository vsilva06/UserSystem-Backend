package cl.losguindos.UserSystemBackend.controller;

import cl.losguindos.UserSystemBackend.model.Email;
import cl.losguindos.UserSystemBackend.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("mail")
public class EmailController {

    @Autowired
    private EmailService service;

    @GetMapping("/sendmail/{receiver}/{subject}/{body}")
    public void sendMail(@PathVariable String receiver, @PathVariable String subject, @PathVariable String body) {
        service.sendMail(receiver, subject, body);
    }

    @PostMapping("/sendmail")
    public void sendMail(@RequestBody Email email) {
        service.sendCustomMail(email);
    }

}
