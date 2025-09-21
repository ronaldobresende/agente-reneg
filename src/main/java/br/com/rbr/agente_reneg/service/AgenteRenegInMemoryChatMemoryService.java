package br.com.rbr.agente_reneg.service;

import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AgenteRenegInMemoryChatMemoryService {

    @Autowired
    private InMemoryChatMemoryRepository chatMemoryRepository;

    public List<Message> obterHistorico(final String mensagem, final String conversationId) {
        final List<Message> historico = new ArrayList<>(chatMemoryRepository.findByConversationId(conversationId));

        final UserMessage novaMensagem = new UserMessage(mensagem);
        historico.add(novaMensagem);

        return historico;
    }

    public void salvarHistorico(final List<Message> historico, final AssistantMessage assistantMessage, final String conversationId) {
        historico.add(assistantMessage);
        chatMemoryRepository.saveAll(conversationId, historico);
    }
}
