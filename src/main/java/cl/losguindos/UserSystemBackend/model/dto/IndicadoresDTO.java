package cl.losguindos.UserSystemBackend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndicadoresDTO {

    private Long id;
    private String nombre;
    private String categoria;
    private String responsable;
    private String definicion;
    private String objetivo;
    private String meta;

    public IndicadoresDTO toIndicadores() {
        return new IndicadoresDTO(
                this.id,
                this.nombre,
                this.categoria,
                this.responsable,
                this.definicion,
                this.objetivo,
                this.meta
        );
    }
}
