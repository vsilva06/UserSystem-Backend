package cl.losguindos.UserSystemBackend.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class AuxUserPass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long auxpassId;

    @Column
    private String auxpassToken;

    @OneToOne
    @JoinColumn(name = "acc_id")
    private Account auxpassAccount;
}
