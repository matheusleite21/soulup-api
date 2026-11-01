package br.com.fiap.soulup.service;

import br.com.fiap.soulup.dto.MissaoResponseDTO;
import br.com.fiap.soulup.dto.MissaoUsuarioResponseDTO;
import br.com.fiap.soulup.model.Missao;
import br.com.fiap.soulup.model.Usuario;
import br.com.fiap.soulup.model.UsuarioMissao;
import br.com.fiap.soulup.repository.MissaoRepository;
import br.com.fiap.soulup.repository.RegistroAcaoRepository;
import br.com.fiap.soulup.repository.UsuarioMissaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

// Regras das missoes: progresso de cada usuario e conclusao automatica com bonus.
// Mesmas regras do sistema em Python (Sprint 4), para os dois sistemas concordarem.
@Service
public class MissaoService {
    public static final String CONCLUIDA = "CONCLUIDA";
    public static final String PENDENTE = "PENDENTE";

    private final MissaoRepository missaoRepository;
    private final UsuarioMissaoRepository usuarioMissaoRepository;
    private final RegistroAcaoRepository registroAcaoRepository;
    private final UsuarioService usuarioService;

    public MissaoService(MissaoRepository missaoRepository,
                         UsuarioMissaoRepository usuarioMissaoRepository,
                         RegistroAcaoRepository registroAcaoRepository,
                         UsuarioService usuarioService) {
        this.missaoRepository = missaoRepository;
        this.usuarioMissaoRepository = usuarioMissaoRepository;
        this.registroAcaoRepository = registroAcaoRepository;
        this.usuarioService = usuarioService;
    }

    @Transactional(readOnly = true)
    public List<MissaoResponseDTO> listar() {
        return missaoRepository.findAllByOrderByIdAsc()
                .stream()
                .map(missao -> new MissaoResponseDTO(
                        missao.getId(),
                        missao.getNome(),
                        missao.getDescricao(),
                        missao.getPontosRecompensa(),
                        missao.getDificuldade(),
                        missao.getTipoCriterio(),
                        missao.getMeta()))
                .toList();
    }

    // Todas as missoes do catalogo com o status e o progresso de um usuario
    @Transactional(readOnly = true)
    public List<MissaoUsuarioResponseDTO> listarPorUsuario(Long idUsuario) {
        Usuario usuario = usuarioService.buscarEntidadePorId(idUsuario);
        Map<Long, UsuarioMissao> situacoes = buscarSituacoes(idUsuario);

        List<MissaoUsuarioResponseDTO> resposta = new ArrayList<>();
        for (Missao missao : missaoRepository.findAllByOrderByIdAsc()) {
            UsuarioMissao situacao = situacoes.get(missao.getId());
            resposta.add(new MissaoUsuarioResponseDTO(
                    missao.getId(),
                    missao.getNome(),
                    missao.getDescricao(),
                    missao.getPontosRecompensa(),
                    missao.getDificuldade(),
                    missao.getTipoCriterio(),
                    missao.getMeta(),
                    calcularProgresso(missao.getTipoCriterio(), usuario),
                    situacao == null ? PENDENTE : situacao.getStatus(),
                    situacao == null ? null : situacao.getDataConclusao(),
                    situacao == null ? null : situacao.getBonusConcedido()));
        }
        return resposta;
    }

    // Conclui as missoes cuja meta foi atingida e credita o bonus uma unica vez.
    // Repete a verificacao porque um bonus pode fazer outra meta ser alcancada
    // (exemplo: o bonus leva o usuario ao nivel exigido por outra missao).
    // Deve ser chamado dentro da transacao que alterou os pontos do usuario.
    public List<String> avaliarMissoes(Usuario usuario) {
        Map<Long, UsuarioMissao> situacoes = buscarSituacoes(usuario.getId());
        List<Missao> missoes = missaoRepository.findAllByOrderByIdAsc();
        List<String> concluidasAgora = new ArrayList<>();

        boolean houveConclusao = true;
        while (houveConclusao) {
            houveConclusao = false;
            for (Missao missao : missoes) {
                UsuarioMissao situacao = situacoes.get(missao.getId());
                boolean jaConcluida = situacao != null && CONCLUIDA.equals(situacao.getStatus());
                Integer progresso = calcularProgresso(missao.getTipoCriterio(), usuario);

                if (jaConcluida || progresso == null || missao.getMeta() == null
                        || progresso < missao.getMeta()) {
                    continue;
                }

                if (situacao == null) {
                    situacao = new UsuarioMissao(usuario, missao, PENDENTE, null, null);
                }
                situacao.setStatus(CONCLUIDA);
                situacao.setDataConclusao(LocalDate.now());
                situacao.setBonusConcedido(missao.getPontosRecompensa());
                usuarioMissaoRepository.save(situacao);
                situacoes.put(missao.getId(), situacao);

                usuarioService.aplicarPontos(usuario, missao.getPontosRecompensa());
                concluidasAgora.add(missao.getNome());
                houveConclusao = true;
            }
        }
        return concluidasAgora;
    }

    // Progresso atual conforme o criterio da missao; null quando a missao nao tem criterio
    private Integer calcularProgresso(String criterio, Usuario usuario) {
        if (criterio == null) {
            return null;
        }
        return switch (criterio) {
            case "QUANTIDADE_ACOES" -> (int) registroAcaoRepository.countByUsuarioId(usuario.getId());
            case "CATEGORIAS_DISTINTAS" -> (int) registroAcaoRepository.contarCategoriasDistintas(usuario.getId());
            case "PONTOS_EXPERIENCIA" -> usuario.getPontosExperiencia();
            case "NIVEL" -> usuario.getNivel();
            default -> null;
        };
    }

    private Map<Long, UsuarioMissao> buscarSituacoes(Long idUsuario) {
        return usuarioMissaoRepository.findByUsuarioId(idUsuario)
                .stream()
                .collect(Collectors.toMap(um -> um.getId().getIdMissao(), Function.identity()));
    }
}
