package br.com.fiap.soulup.repository;

import br.com.fiap.soulup.model.UsuarioMissao;
import br.com.fiap.soulup.model.UsuarioMissaoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioMissaoRepository extends JpaRepository<UsuarioMissao, UsuarioMissaoId> {

    List<UsuarioMissao> findByUsuarioId(Long idUsuario);

    @Modifying
    @Query("delete from UsuarioMissao um where um.usuario.id = :idUsuario")
    int excluirPorUsuario(@Param("idUsuario") Long idUsuario);
}
