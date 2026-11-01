package br.com.fiap.soulup.controller;

import br.com.fiap.soulup.dto.MissaoResponseDTO;
import br.com.fiap.soulup.service.MissaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/missoes")
@Tag(name = "Missões", description = "Desafios que rendem pontos extras")
public class MissaoController {
    private final MissaoService missaoService;

    public MissaoController(MissaoService missaoService) {
        this.missaoService = missaoService;
    }

    @GetMapping
    @Operation(summary = "Listar missões", description = "Retorna o catálogo de missões com critério e meta.")
    public List<MissaoResponseDTO> listar() {
        return missaoService.listar();
    }
}
