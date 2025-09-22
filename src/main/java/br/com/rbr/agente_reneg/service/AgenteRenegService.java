package br.com.rbr.agente_reneg.service;

import br.com.rbr.agente_reneg.prompt.PromptLoader;
import br.com.rbr.agente_reneg.tools.KnowledgeBaseTools;
import br.com.rbr.agente_reneg.tools.ListraContratosTools;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ai.chat.model.ChatResponse;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AgenteRenegService {
    private ChatClient chatClient;
    @Autowired
    private AgenteRenegInMemoryChatMemoryService agenteRenegChatMemoryService;
    @Autowired
    private PromptLoader promptLoader;
    @Autowired
    private ListraContratosTools listraContratosTools;
    @Autowired
    private KnowledgeBaseTools knowledgeBaseTools;

    public AgenteRenegService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public ChatResponse execute(final String mensagem, final String conversationId) {
        final List<Message> historico = agenteRenegChatMemoryService.obterHistorico(mensagem, conversationId);
        var systemPrompt = promptLoader.getListaContratosPrompt();

        final ChatResponse response =  chatClient.prompt()
                .system(systemPrompt.getContents())
                .messages(historico)
                .tools(listraContratosTools, knowledgeBaseTools)
                .call()
                .chatResponse();

        agenteRenegChatMemoryService.salvarHistorico(historico, Objects.requireNonNull(response).getResult().getOutput(), conversationId);

        return response;
    }

}
