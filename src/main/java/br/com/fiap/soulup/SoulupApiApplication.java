package br.com.fiap.soulup;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Equipe: SoulUp | Turma: 1TDSPO
// Matheus Ruiz Giatti (RM 570701) e Matheus Leite Souza das Virgens (RM 573416)

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
        title = "SoulUp API",
        version = "1.0",
        description = "API RESTful do SoulUp: usuários, ações sustentáveis, pontos, níveis e missões."
))
public class SoulupApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoulupApiApplication.class, args);
    }

}
