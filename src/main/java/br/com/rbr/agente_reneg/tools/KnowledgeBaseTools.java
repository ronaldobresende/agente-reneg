package br.com.rbr.agente_reneg.tools;


import jakarta.annotation.PostConstruct;
import org.springframework.ai.tool.annotation.Tool;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

@Component
public class KnowledgeBaseTools {

    private String fixedContext;

    @Value("classpath:/regras-renegociacao.md")
    private Resource knowledgeBaseResource;

    @PostConstruct
    public void initialize() throws IOException {
        try (Reader reader = new InputStreamReader(knowledgeBaseResource.getInputStream(), StandardCharsets.UTF_8)) {
            this.fixedContext = FileCopyUtils.copyToString(reader);
        }
    }

    @Tool(description = "Use esta ferramenta para obter o texto das regras e políticas de renegociação do banco. Ela retorna o conteúdo da base de conhecimento para que você possa responder a perguntas.")
    public KnowledgeResponse informacoesRenegociacao() {
        System.out.println("*** Ferramenta usada: KnowledgeBaseTools ***");
        return new KnowledgeResponse(this.fixedContext);
    }

    public record KnowledgeResponse(String context) {}
}