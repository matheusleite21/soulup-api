package br.com.fiap.soulup.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Tag(name = "Status", description = "Verificação de funcionamento da API")
public class StatusController {

    @GetMapping("/")
    @Operation(summary = "Verificar se a API está no ar")
    public Map<String, String> status() {
        return Map.of(
                "api", "SoulUp",
                "status", "online",
                "documentacao", "/swagger-ui.html"
        );
    }
}
