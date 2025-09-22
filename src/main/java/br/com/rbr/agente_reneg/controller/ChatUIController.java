package br.com.rbr.agente_reneg.controller;

import br.com.rbr.agente_reneg.facade.AgenteRenegFacade;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ChatUIController {

    private final AgenteRenegFacade agenteFacade;
    private final Parser markdownParser; // Parser do CommonMark
    private final HtmlRenderer htmlRenderer; // Renderizador do CommonMark
    private static final List<String> conversationHistory = new ArrayList<>();

    public ChatUIController(AgenteRenegFacade agenteFacade) {
        this.agenteFacade = agenteFacade;
        this.markdownParser = Parser.builder().build();
        this.htmlRenderer = HtmlRenderer.builder().build();
    }

    @PostMapping("/chat")
    public String sendMessage(@RequestParam String message,
                              @RequestParam(required = false) String conversationId,
                              Model model) {

        conversationHistory.add("Você: " + message);

        AgenteResponse response = agenteFacade.processarMensagem(message, conversationId);

        // ---- A MÁGICA ACONTECE AQUI ----
        // 1. Pega o texto Markdown do agente
        String markdownContent = response.content();
        // 2. Converte o Markdown em HTML
        Node document = markdownParser.parse(markdownContent);
        String htmlContent = htmlRenderer.render(document);
        // --------------------------------

        // Adiciona o HTML convertido ao histórico
        conversationHistory.add("Agente: " + htmlContent);

        model.addAttribute("history", conversationHistory);
        model.addAttribute("conversationId", response.conversationId());

        return "chat";
    }

    @PostMapping("/chat/clear")
    public String clearChat() {
        conversationHistory.clear();
        return "redirect:/chat";
    }

    @GetMapping("/chat")
    public String showChat(Model model) {
        if (conversationHistory.isEmpty()) {
            conversationHistory.add("Agente: Olá! Sou o assistente pessoal de renegociação. Para começarmos, por favor, me informe o CNPJ da sua empresa.");
        }
        model.addAttribute("history", conversationHistory);
        model.addAttribute("conversationId", "");
        return "chat";
    }

}