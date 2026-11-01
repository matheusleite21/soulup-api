package br.com.fiap.soulup.service;

import br.com.fiap.soulup.dto.UsuarioRequestDTO;
import br.com.fiap.soulup.dto.UsuarioResponseDTO;
import br.com.fiap.soulup.model.Avatar;
import br.com.fiap.soulup.model.Usuario;
import br.com.fiap.soulup.repository.AvatarRepository;
import br.com.fiap.soulup.repository.InteracaoRepository;
import br.com.fiap.soulup.repository.RegistroAcaoRepository;
import br.com.fiap.soulup.repository.ResgateRepository;
import br.com.fiap.soulup.repository.UsuarioMissaoRepository;
import br.com.fiap.soulup.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

// Regras de negocio da conta do usuario: cadastro com avatar, e-mail unico,
// calculo de nivel e movimentacao de experiencia e saldo.
@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final AvatarRepository avatarRepository;
    private final InteracaoRepository interacaoRepository;
    private final UsuarioMissaoRepository usuarioMissaoRepository;
    private final ResgateRepository resgateRepository;
    private final RegistroAcaoRepository registroAcaoRepository;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          AvatarRepository avatarRepository,
                          InteracaoRepository interacaoRepository,
                          UsuarioMissaoRepository usuarioMissaoRepository,
                          ResgateRepository resgateRepository,
                          RegistroAcaoRepository registroAcaoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.avatarRepository = avatarRepository;
        this.interacaoRepository = interacaoRepository;
        this.usuarioMissaoRepository = usuarioMissaoRepository;
        this.resgateRepository = resgateRepository;
        this.registroAcaoRepository = registroAcaoRepository;
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> listar() {
        return usuarioRepository.findAllByOrderByPontosExperienciaDescNomeAsc()
                .stream()
                .map(this::paraResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public UsuarioResponseDTO buscarPorId(Long id) {
        return paraResponseDTO(buscarEntidadePorId(id));
    }

    // Todo usuario novo comeca no nivel 1, sem pontos, e recebe o avatar Soul
    @Transactional
    public UsuarioResponseDTO criar(UsuarioRequestDTO dados) {
        String email = normalizarEmail(dados.email());
        if (usuarioRepository.existsByEmailIgnoreCase(email)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Já existe um usuário cadastrado com o e-mail " + email);
        }

        Usuario usuario = new Usuario();
        usuario.setNome(dados.nome().trim());
        usuario.setEmail(email);
        usuario.setDataCadastro(LocalDate.now());
        usuario.setPontosExperiencia(0);
        usuario.setSaldoPontos(0);
        usuario.setNivel(1);
        Usuario salvo = usuarioRepository.save(usuario);

        Avatar avatar = new Avatar();
        avatar.setUsuario(salvo);
        avatar.setNome("Soul");
        avatar.setPersonalidade("Motivador");
        avatar.setQuantidadeInteracoes(0);
        avatarRepository.save(avatar);

        return paraResponseDTO(salvo);
    }

    // Apenas nome e e-mail podem ser alterados; pontos e nivel mudam pelas acoes
    @Transactional
    public UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO dados) {
        Usuario usuario = buscarEntidadePorId(id);
        String email = normalizarEmail(dados.email());
        if (usuarioRepository.existsByEmailIgnoreCaseAndIdNot(email, id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Outro usuário já utiliza o e-mail " + email);
        }

        usuario.setNome(dados.nome().trim());
        usuario.setEmail(email);
        return paraResponseDTO(usuarioRepository.save(usuario));
    }

    // Remove a conta e os registros que dependem dela, na ordem das chaves estrangeiras.
    // Os catalogos (acoes, categorias, missoes e recompensas) sao preservados.
    @Transactional
    public void excluir(Long id) {
        Usuario usuario = buscarEntidadePorId(id);
        interacaoRepository.excluirPorUsuario(id);
        avatarRepository.excluirPorUsuario(id);
        usuarioMissaoRepository.excluirPorUsuario(id);
        resgateRepository.excluirPorUsuario(id);
        registroAcaoRepository.excluirPorUsuario(id);
        usuarioRepository.delete(usuario);
    }

    public Usuario buscarEntidadePorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuário não encontrado com o ID " + id));
    }

    // Soma (ou retira) pontos da experiencia e do saldo e recalcula o nivel.
    // O saldo nao pode ficar negativo: pontos ja gastos em resgates nao podem ser retirados.
    public void aplicarPontos(Usuario usuario, int diferenca) {
        int novoSaldo = usuario.getSaldoPontos() + diferenca;
        if (novoSaldo < 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Operação bloqueada: parte desses pontos já foi utilizada em resgates.");
        }
        int novaExperiencia = Math.max(0, usuario.getPontosExperiencia() + diferenca);

        usuario.setSaldoPontos(novoSaldo);
        usuario.setPontosExperiencia(novaExperiencia);
        usuario.setNivel(calcularNivel(novaExperiencia));
    }

    // Faixas de experiencia usadas em todo o SoulUp: 0, 50, 200, 500 e 1000
    public int calcularNivel(int pontosExperiencia) {
        if (pontosExperiencia >= 1000) return 5;
        if (pontosExperiencia >= 500) return 4;
        if (pontosExperiencia >= 200) return 3;
        if (pontosExperiencia >= 50) return 2;
        return 1;
    }

    public UsuarioResponseDTO paraResponseDTO(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getDataCadastro(),
                usuario.getPontosExperiencia(),
                usuario.getSaldoPontos(),
                usuario.getNivel()
        );
    }

    private String normalizarEmail(String email) {
        return email.trim().toLowerCase();
    }
}
