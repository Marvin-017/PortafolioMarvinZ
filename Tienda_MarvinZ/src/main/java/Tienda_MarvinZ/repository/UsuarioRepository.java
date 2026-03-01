/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Tienda_MarvinZ.repository;

/**
 *
 * @author marvi
 */
import Tienda_MarvinZ.domain.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    
    public Optional<Usuario> findByUsernameAndActivoTrue(String username);
    
    public List<Usuario> findByActivoTrue();

    public Optional<Usuario> findByUsername(String username);

    public Optional<Usuario> findByUsernameAndPassword(String username, String Password);

    public Optional<Usuario> findByUsernameOrCorreo(String username, String correo);

    public boolean existsByUsernameOrCorreo(String username, String correo);
    
}