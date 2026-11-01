package br.com.fiap.soulup.repository;

import br.com.fiap.soulup.model.RegistroAcao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistroAcaoRepository extends JpaRepository<RegistroAcao, Long> {

    // Historico do usuario, do mais recente para o mais antigo
    @Query("select r from RegistroAcao r join fetch r.acao a join fetch a.categoria "
            + "where r.usuario.id = :idUsuario order by r.dataRegistro desc, r.id desc")
    List<RegistroAcao> listarPorUsuario(@Param("idUsuario") Long idUsuario);

    long countByUsuarioId(Long idUsuario);

    @Query("select count(distinct r.acao.categoria.id) from RegistroAcao r "
            + "where r.usuario.id = :idUsuario")
    long contarCategoriasDistintas(@Param("idUsuario") Long idUsuario);

    @Modifying
    @Query("delete from RegistroAcao r where r.usuario.id = :idUsuario")
    int excluirPorUsuario(@Param("idUsuario") Long idUsuario);
}
