package cl.losguindos.UserSystemBackend.model.dto;

import cl.losguindos.UserSystemBackend.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private List<String> accRoles;
}
