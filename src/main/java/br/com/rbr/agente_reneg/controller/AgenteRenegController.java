package br.com.rbr.agente_reneg.controller;

import br.com.rbr.agente_reneg.facade.AgenteRenegFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/agente")
public class AgenteRenegController {

    @Autowired
    private AgenteRenegFacade agenteRenegFacade;

    @PostMapping("/renegociar")
    public AgenteResponse renegociar(final @RequestBody AgenteRequest request) {
        return agenteRenegFacade.processarMensagem(request.mensagem(), request.conversationId());
    }
}
