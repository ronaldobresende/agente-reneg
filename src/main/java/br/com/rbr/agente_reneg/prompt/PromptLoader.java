package br.com.rbr.agente_reneg.prompt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.stereotype.Component;

@Component
public class PromptLoader {

    @Value("classpath:/prompts/lista-contratos-prompt.st")
    private Resource listaContratoPromptResource;

    public Prompt getListaContratosPrompt() {
         return new SystemPromptTemplate(listaContratoPromptResource).create();
    }

}
