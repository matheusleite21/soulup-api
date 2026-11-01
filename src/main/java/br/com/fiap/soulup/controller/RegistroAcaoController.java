package br.com.fiap.soulup.controller;

import br.com.fiap.soulup.dto.RegistroRequestDTO;
import br.com.fiap.soulup.dto.RegistroResponseDTO;
import br.com.fiap.soulup.dto.RegistroResultadoDTO;
import br.com.fiap.soulup.service.RegistroAcaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registros")
@Tag(
        name = "Registros de ações",
        description = "Ações sustentáveis realizadas pelos usuários, com pontos e missões"
)
public class RegistroAcaoController {
    private final RegistroAcaoService registroAcaoService;

    public RegistroAcaoController(RegistroAcaoService registroAcaoService) {
        this.registroAcaoService = registroAcaoService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar registro por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro encontrado"),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado")
    })
    public RegistroResponseDTO buscarPorId(
            @Parameter(description = "Identificador do registro", example = "1", required = true)
            @PathVariable Long id) {
        return registroAcaoService.buscarPorId(id);
    }

    @PostMapping
    @Operation(
            summary = "Registrar ação sustentável",
            description = "Soma os pontos da ação ao usuário, recalcula o nível e conclui as missões atingidas."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Ação registrada"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuário ou ação não encontrados")
    })
    public ResponseEntity<RegistroResultadoDTO> criar(@Valid @RequestBody RegistroRequestDTO registro) {
        RegistroResultadoDTO resposta = registroAcaoService.criar(registro);
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Corrigir registro", description = "Troca a ação ou a descrição e ajusta os pontos pela diferença.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro atualizado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Registro ou ação não encontrados"),
            @ApiResponse(responseCode = "409", description = "Pontos já utilizados em resgates")
    })
    public RegistroResultadoDTO atualizar(@PathVariable Long id,
                                          @Valid @RequestBody RegistroRequestDTO registro) {
        return registroAcaoService.atualizar(id, registro);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Excluir registro", description = "Retira do usuário os pontos daquela ação.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Registro excluído"),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado"),
            @ApiResponse(responseCode = "409", description = "Pontos já utilizados em resgates")
    })
    public void excluir(@PathVariable Long id) {
        registroAcaoService.excluir(id);
    }
}
