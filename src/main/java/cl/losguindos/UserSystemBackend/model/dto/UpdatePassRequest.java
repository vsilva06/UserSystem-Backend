package cl.losguindos.UserSystemBackend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePassRequest {
    private String newPassword;
    private String confirmPassword;
    private String token;
}
