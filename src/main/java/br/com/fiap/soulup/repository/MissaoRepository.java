package br.com.fiap.soulup.repository;

import br.com.fiap.soulup.model.Missao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MissaoRepository extends JpaRepository<Missao, Long> {

    List<Missao> findAllByOrderByIdAsc();
}
