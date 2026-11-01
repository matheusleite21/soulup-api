package br.com.fiap.soulup.repository;

import br.com.fiap.soulup.model.Acao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AcaoRepository extends JpaRepository<Acao, Long> {

    // join fetch traz a categoria na mesma consulta
    @Query("select a from Acao a join fetch a.categoria order by a.id")
    List<Acao> listarComCategoria();
}
