package br.com.fiap.soulup.repository;

import br.com.fiap.soulup.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Ranking: maior experiencia primeiro; empate resolvido pelo nome
    List<Usuario> findAllByOrderByPontosExperienciaDescNomeAsc();

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);
}
