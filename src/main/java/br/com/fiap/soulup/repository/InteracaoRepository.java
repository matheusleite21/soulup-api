package br.com.fiap.soulup.repository;

import br.com.fiap.soulup.model.Interacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InteracaoRepository extends JpaRepository<Interacao, Long> {

    @Modifying
    @Query("delete from Interacao i where i.avatar.id in "
            + "(select a.id from Avatar a where a.usuario.id = :idUsuario)")
    int excluirPorUsuario(@Param("idUsuario") Long idUsuario);
}
