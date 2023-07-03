package cl.losguindos.UserSystemBackend.service;

import cl.losguindos.UserSystemBackend.model.dto.IndicadoresDTO;
import cl.losguindos.UserSystemBackend.security.Hierarchy;
import cl.losguindos.UserSystemBackend.utils.CustomResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.List;
import java.util.logging.Logger;

@Service
public class RequestIndicadoresService {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    Gson gson;
    Logger logger = Logger.getLogger(RequestIndicadoresService.class.getName());
    @Value("${url.indicadores.api}")
    private String urlIndicadoresApi;
    @Autowired
    private Hierarchy hierarchy;

    public List<IndicadoresDTO> getIndicadores() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated()) {
            throw new RuntimeException(CustomResponse.generateResponse("404", "No user logged in"));
        }
        try {
            var indicadores = gson.fromJson(restTemplate.getForObject(urlIndicadoresApi, String.class), JsonObject.class).get("data");
            ObjectMapper mapper = new ObjectMapper();

            var indicadoresDTO = mapper.readValue(indicadores.toString(), new TypeReference<List<IndicadoresDTO>>() {
                @Override
                public Type getType() {
                    return super.getType();
                }
            });
            if (hierarchy.isCurrentAdmin()) {
                return indicadoresDTO;
            } else if (authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_MODERATOR"))) {
                indicadoresDTO.removeIf(indicador -> indicador.getCategoria().equals("Operacionales"));
                return indicadoresDTO;
            } else if (authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_USER"))) {
                indicadoresDTO.removeIf(indicador -> indicador.getCategoria().equals("Financieros") || indicador.getCategoria().equals("Operacionales"));
                return indicadoresDTO;
            }

        } catch (RestClientException e) {
            throw new RuntimeException("Error al obtener datos de la API");
        } catch (JsonMappingException e) {
            throw new RuntimeException("Error en el mapeo de datos");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}

