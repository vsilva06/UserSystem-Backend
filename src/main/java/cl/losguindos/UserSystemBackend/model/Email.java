package cl.losguindos.UserSystemBackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Email {
    private String emailTo;
    private String emailSubject;
    private String emailBody;

}
