package cl.duocuc.sged.usuario.repository;

import cl.duocuc.sged.usuario.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

/**
 * Repositorio de Usuario.
 * Accede directamente a la base de datos.
 * No contiene lógica de negocio, solo operaciones CRUD.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    Optional<Usuario> findByEmail(String email);
    
    List<Usuario> findByRol(Usuario.Rol rol);
    
    List<Usuario> findByActivo(Boolean activo);
}
