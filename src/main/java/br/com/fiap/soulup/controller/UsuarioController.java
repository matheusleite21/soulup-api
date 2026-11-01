package br.com.fiap.soulup.controller;

import br.com.fiap.soulup.dto.MissaoUsuarioResponseDTO;
import br.com.fiap.soulup.dto.RegistroResponseDTO;
import br.com.fiap.soulup.dto.UsuarioRequestDTO;
import br.com.fiap.soulup.dto.UsuarioResponseDTO;
import br.com.fiap.soulup.service.MissaoService;
import br.com.fiap.soulup.service.RegistroAcaoService;
import br.com.fiap.soulup.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@Tag(
        name = "Usuários",
        description = "Cadastro, ranking, perfil, histórico e missões dos usuários"
)
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final RegistroAcaoService registroAcaoService;
    private final MissaoService missaoService;

    public UsuarioController(UsuarioService usuarioService,
                             RegistroAcaoService registroAcaoService,
                             MissaoService missaoService) {
        this.usuarioService = usuarioService;
        this.registroAcaoService = registroAcaoService;
        this.missaoService = missaoService;
    }

    @GetMapping
    @Operation(
            summary = "Listar usuários",
            description = "Retorna todos os usuários em ordem de ranking (maior experiência primeiro)."
    )
    public List<UsuarioResponseDTO> listar() {
        return usuarioService.listar();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public UsuarioResponseDTO buscarPorId(
            @Parameter(description = "Identificador do usuário", example = "1", required = true)
            @PathVariable Long id) {
        return usuarioService.buscarPorId(id);
    }

    @PostMapping
    @Operation(summary = "Cadastrar usuário", description = "Cria a conta no nível 1 e o avatar Soul.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "409", description = "E-mail já cadastrado")
    })
    public ResponseEntity<UsuarioResponseDTO> criar(@Valid @RequestBody UsuarioRequestDTO usuario) {
        UsuarioResponseDTO resposta = usuarioService.criar(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar nome e e-mail do usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário atualizado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "409", description = "E-mail já usado por outro usuário")
    })
    public UsuarioResponseDTO atualizar(@PathVariable Long id,
                                        @Valid @RequestBody UsuarioRequestDTO usuario) {
        return usuarioService.atualizar(id, usuario);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Excluir usuário", description = "Remove a conta, o avatar e o histórico do usuário.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuário excluído"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public void excluir(@PathVariable Long id) {
        usuarioService.excluir(id);
    }

    @GetMapping("/{id}/registros")
    @Operation(summary = "Histórico de ações do usuário", description = "Do registro mais recente para o mais antigo.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Histórico encontrado (pode ser uma lista vazia)"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public List<RegistroResponseDTO> listarRegistros(@PathVariable Long id) {
        return registroAcaoService.listarPorUsuario(id);
    }

    @GetMapping("/{id}/missoes")
    @Operation(summary = "Missões do usuário", description = "Status, progresso e bônus de cada missão para o usuário.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Missões encontradas"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public List<MissaoUsuarioResponseDTO> listarMissoes(@PathVariable Long id) {
        return missaoService.listarPorUsuario(id);
    }
}
