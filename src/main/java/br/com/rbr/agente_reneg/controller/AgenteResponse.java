package br.com.rbr.agente_reneg.controller;

import java.time.Instant;

public record AgenteResponse(
        String content,
        String conversationId,
        Instant timestamp
) {}