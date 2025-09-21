package br.com.rbr.agente_reneg.facade;


import br.com.rbr.agente_reneg.controller.AgenteResponse;
import br.com.rbr.agente_reneg.mapper.AgenteResponseMapper;
import br.com.rbr.agente_reneg.service.AgenteRenegService;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;;

@Component
public class AgenteRenegFacade {
    @Autowired
    private AgenteRenegService agenteRenegService;
    @Autowired
    private AgenteResponseMapper agenteResponseMapper;

    public AgenteResponse processarMensagem(final String mensagem, final String idConversa) {
        final String conversationId = Optional.ofNullable(idConversa)
                .filter(id -> !id.isEmpty())
                .orElseGet(() -> UUID.randomUUID().toString());

        final ChatResponse chatResponse = agenteRenegService.execute(mensagem, conversationId);

        return agenteResponseMapper.mapToResponse(chatResponse, conversationId, Instant.now());
    }
}
