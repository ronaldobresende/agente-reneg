package br.com.rbr.agente_reneg.mapper;

import br.com.rbr.agente_reneg.controller.AgenteResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.ai.chat.model.ChatResponse;

import java.time.Instant;

@Mapper(componentModel = "spring")
public interface AgenteResponseMapper {
    @Mapping(target = "content", expression = "java(chatResponse.getResults().get(0).getOutput().getText())")
    AgenteResponse mapToResponse(final ChatResponse chatResponse, final String conversationId, final Instant timestamp);
}