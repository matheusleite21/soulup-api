package br.com.fiap.soulup.repository;

import br.com.fiap.soulup.model.Resgate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ResgateRepository extends JpaRepository<Resgate, Long> {

    @Modifying
    @Query("delete from Resgate r where r.usuario.id = :idUsuario")
    int excluirPorUsuario(@Param("idUsuario") Long idUsuario);
}
