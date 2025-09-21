package br.com.rbr.agente_reneg.controller;

public record AgenteRequest(
        String mensagem,
        String conversationId
) {}
