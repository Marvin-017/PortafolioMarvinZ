package Tienda_MarvinZ.controller;

import Tienda_MarvinZ.domain.Ruta;
import Tienda_MarvinZ.service.RutaService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RutaController {

    private final RutaService rutaService;

    // Inyección de dependencias por constructor
    public RutaController(RutaService rutaService) {
        this.rutaService = rutaService;
    }

    @GetMapping("/ruta/listado")
    public String listado(Model model) {
        List<Ruta> rutas = rutaService.getRutas();
        model.addAttribute("rutas", rutas);
        return "ruta/listado"; 
    }
}