package cl.losguindos.UserSystemBackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Email{
    private String email_to;
    private String email_subject;
    private String email_body;

}
