package br.com.fiap.soulup.controller;

import br.com.fiap.soulup.dto.AcaoResponseDTO;
import br.com.fiap.soulup.service.AcaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/acoes")
@Tag(name = "Catálogo de ações", description = "Ações sustentáveis que podem ser registradas")
public class AcaoController {
    private final AcaoService acaoService;

    public AcaoController(AcaoService acaoService) {
        this.acaoService = acaoService;
    }

    @GetMapping
    @Operation(summary = "Listar ações", description = "Retorna o catálogo com categoria e pontos de cada ação.")
    public List<AcaoResponseDTO> listar() {
        return acaoService.listar();
    }
}
