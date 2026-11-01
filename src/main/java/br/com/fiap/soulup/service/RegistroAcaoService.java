package br.com.fiap.soulup.service;

import br.com.fiap.soulup.dto.RegistroRequestDTO;
import br.com.fiap.soulup.dto.RegistroResponseDTO;
import br.com.fiap.soulup.dto.RegistroResultadoDTO;
import br.com.fiap.soulup.model.Acao;
import br.com.fiap.soulup.model.RegistroAcao;
import br.com.fiap.soulup.model.Usuario;
import br.com.fiap.soulup.repository.RegistroAcaoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

// Regras do registro de acoes: a pontuacao vem do catalogo, o impacto e
// calculado pela pontuacao, e registro, pontos e missoes sao gravados juntos
// (se algo falhar, a transacao desfaz tudo).
@Service
public class RegistroAcaoService {
    private final RegistroAcaoRepository registroAcaoRepository;
    private final UsuarioService usuarioService;
    private final AcaoService acaoService;
    private final MissaoService missaoService;

    public RegistroAcaoService(RegistroAcaoRepository registroAcaoRepository,
                               UsuarioService usuarioService,
                               AcaoService acaoService,
                               MissaoService missaoService) {
        this.registroAcaoRepository = registroAcaoRepository;
        this.usuarioService = usuarioService;
        this.acaoService = acaoService;
        this.missaoService = missaoService;
    }

    @Transactional(readOnly = true)
    public List<RegistroResponseDTO> listarPorUsuario(Long idUsuario) {
        usuarioService.buscarEntidadePorId(idUsuario);
        return registroAcaoRepository.listarPorUsuario(idUsuario)
                .stream()
                .map(this::paraResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public RegistroResponseDTO buscarPorId(Long id) {
        return paraResponseDTO(buscarEntidadePorId(id));
    }

    @Transactional
    public RegistroResultadoDTO criar(RegistroRequestDTO dados) {
        Usuario usuario = usuarioService.buscarEntidadePorId(dados.idUsuario());
        Acao acao = acaoService.buscarEntidadePorId(dados.idAcao());
        int pontos = acao.getPontosPadrao();

        RegistroAcao registro = new RegistroAcao();
        registro.setUsuario(usuario);
        registro.setAcao(acao);
        registro.setDescricao(dados.descricao().trim());
        registro.setPontuacao(pontos);
        registro.setImpacto(calcularImpacto(pontos));
        registro.setDataRegistro(LocalDateTime.now());
        RegistroAcao salvo = registroAcaoRepository.save(registro);

        usuarioService.aplicarPontos(usuario, pontos);
        List<String> missoes = missaoService.avaliarMissoes(usuario);

        return new RegistroResultadoDTO(
                paraResponseDTO(salvo),
                usuarioService.paraResponseDTO(usuario),
                missoes);
    }

    // Corrige a acao e a descricao; os pontos do usuario mudam apenas pela diferenca
    @Transactional
    public RegistroResultadoDTO atualizar(Long id, RegistroRequestDTO dados) {
        RegistroAcao registro = buscarEntidadePorId(id);
        Usuario usuario = registro.getUsuario();
        if (!usuario.getId().equals(dados.idUsuario())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Não é possível transferir o registro para outro usuário.");
        }

        Acao acao = acaoService.buscarEntidadePorId(dados.idAcao());
        int novosPontos = acao.getPontosPadrao();
        usuarioService.aplicarPontos(usuario, novosPontos - registro.getPontuacao());

        registro.setAcao(acao);
        registro.setDescricao(dados.descricao().trim());
        registro.setPontuacao(novosPontos);
        registro.setImpacto(calcularImpacto(novosPontos));
        RegistroAcao salvo = registroAcaoRepository.save(registro);

        List<String> missoes = missaoService.avaliarMissoes(usuario);
        return new RegistroResultadoDTO(
                paraResponseDTO(salvo),
                usuarioService.paraResponseDTO(usuario),
                missoes);
    }

    // Retira os pontos da acao; bonus de missoes ja concluidas sao conquistas e permanecem
    @Transactional
    public void excluir(Long id) {
        RegistroAcao registro = buscarEntidadePorId(id);
        usuarioService.aplicarPontos(registro.getUsuario(), -registro.getPontuacao());
        registroAcaoRepository.delete(registro);
    }

    // Faixas aceitas pela restricao CK_REGISTRO_IMPACTO do banco
    public String calcularImpacto(int pontos) {
        if (pontos >= 80) return "ALTO";
        if (pontos >= 50) return "MEDIO";
        if (pontos >= 20) return "BAIXO";
        return "MINIMO";
    }

    private RegistroAcao buscarEntidadePorId(Long id) {
        return registroAcaoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Registro de ação não encontrado com o ID " + id));
    }

    private RegistroResponseDTO paraResponseDTO(RegistroAcao registro) {
        return new RegistroResponseDTO(
                registro.getId(),
                registro.getUsuario().getId(),
                registro.getUsuario().getNome(),
                registro.getAcao().getId(),
                registro.getAcao().getNome(),
                registro.getAcao().getCategoria().getNome(),
                registro.getDescricao(),
                registro.getPontuacao(),
                registro.getImpacto(),
                registro.getDataRegistro()
        );
    }
}
