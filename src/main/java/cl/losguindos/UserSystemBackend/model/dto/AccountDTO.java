package cl.losguindos.UserSystemBackend.model.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    private Long accId;
    private String accName;
    private String accFirstName;
    private String accLastName;
    private String accEmail;
    private String accPass;
    private String role;
}
