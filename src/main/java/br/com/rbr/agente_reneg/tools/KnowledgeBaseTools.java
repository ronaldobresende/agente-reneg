package br.com.rbr.agente_reneg.tools;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

@Component
public class KnowledgeBaseTools {

    private String fixedContext;
    private final ChatClient chatClient;

    @Value("classpath:/regras-renegociacao.md")
    private Resource knowledgeBaseResource;

    public KnowledgeBaseTools(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

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

    @Tool(description = "Use esta ferramenta para responder a dúvidas gerais sobre políticas de renegociação ou para explicar por que um contrato específico não é elegível para renegociação.")
    public KnowledgeResponse informacoesSobreRenegociacao(QuestionRequest request) {
        System.out.println("Ferramenta de conhecimento chamada com a pergunta: " + request.question());

        String promptText = """
                Use exclusivamente o contexto abaixo para responder à pergunta do usuário.
                Se a resposta não estiver no contexto, diga que não possui essa informação.
                                    
                Contexto:
                ---
                {context}
                ---
                                    
                Pergunta do Usuário: {question}
                """;

        Message systemMessage = new SystemPromptTemplate(promptText).createMessage();
        Message userMessage = new UserMessage(request.question()     );

        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
        ChatResponse response = chatClient.prompt(prompt).call().chatResponse();

        String content = Objects.requireNonNull(response).getResult().getOutput().getText();
        return new KnowledgeResponse(content);
    }

    public record KnowledgeResponse(String context) {}
    public record QuestionRequest(String question) {}

}