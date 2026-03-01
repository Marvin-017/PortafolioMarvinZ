package Tienda_MarvinZ.service;

import Tienda_MarvinZ.domain.Rol;
import Tienda_MarvinZ.repository.RolRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    // Solo obtener todos los roles
    @Transactional(readOnly = true)
    public List<Rol> getRoles() {
        return rolRepository.findAll();
    }

    // Obtener un rol por ID, si en algún futuro se necesita
    @Transactional(readOnly = true)
    public Rol getRolPorId(Integer idRol) {
        return rolRepository.findById(idRol).orElse(null);
    }
}