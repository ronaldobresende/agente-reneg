package br.com.rbr.agente_reneg.service.agents;


import br.com.rbr.agente_reneg.tools.KnowledgeBaseTools;
import br.com.rbr.agente_reneg.tools.ListraContratosTools;
import org.springframework.stereotype.Service;

@Service
public class KnowledgeSpecialistAgent {

    private final KnowledgeBaseTools knowledgeBaseTools;

    public KnowledgeSpecialistAgent(KnowledgeBaseTools knowledgeBaseTools) {
        this.knowledgeBaseTools = knowledgeBaseTools;
    }

    public KnowledgeBaseTools.KnowledgeResponse answerQuestion(final KnowledgeBaseTools.QuestionRequest questionRequest) {
        System.out.println("AGENTE ESPECIALISTA DE KB: Fui ativado.");
        return knowledgeBaseTools.informacoesSobreRenegociacao(questionRequest);
    }
}