package cl.losguindos.UserSystemBackend.controller;

import cl.losguindos.UserSystemBackend.model.dto.IndicadoresDTO;
import cl.losguindos.UserSystemBackend.service.RequestIndicadoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/indicadores")
public class IndicadoresController {

    @Autowired
    private RequestIndicadoresService service;

    @GetMapping("/get")
    public List<IndicadoresDTO> getIndicadores() {
        return service.getIndicadores().stream()
                .map(IndicadoresDTO::toIndicadores)
                .toList();
    }
}
