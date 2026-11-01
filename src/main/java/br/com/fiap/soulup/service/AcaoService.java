package br.com.fiap.soulup.service;

import br.com.fiap.soulup.dto.AcaoResponseDTO;
import br.com.fiap.soulup.model.Acao;
import br.com.fiap.soulup.repository.AcaoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AcaoService {
    private final AcaoRepository acaoRepository;

    public AcaoService(AcaoRepository acaoRepository) {
        this.acaoRepository = acaoRepository;
    }

    @Transactional(readOnly = true)
    public List<AcaoResponseDTO> listar() {
        return acaoRepository.listarComCategoria()
                .stream()
                .map(this::paraResponseDTO)
                .toList();
    }

    public Acao buscarEntidadePorId(Long id) {
        return acaoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Ação não encontrada no catálogo com o ID " + id));
    }

    private AcaoResponseDTO paraResponseDTO(Acao acao) {
        return new AcaoResponseDTO(
                acao.getId(),
                acao.getNome(),
                acao.getDescricao(),
                acao.getPontosPadrao(),
                acao.getCategoria().getId(),
                acao.getCategoria().getNome()
        );
    }
}
