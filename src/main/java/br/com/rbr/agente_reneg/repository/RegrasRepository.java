package br.com.rbr.agente_reneg.repository;

import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Repository;

@Repository
public class RegrasRepository {

    public String getRegras() {
        return "Regras de Elegibilidade:\n" +
                "1. Contratos com parcelas em atraso superiores a 90 dias não são renegociáveis.\n" +
                "2. Contratos com valor total inferior a R$ 5.000,00 não são elegíveis para renegociação.\n" +
                "3. Empresas com faturamento anual superior a R$ 10.000.000,00 devem passar por análise especial.\n" +
                "\nDúvidas Frequentes:\n" +
                "1. Quais documentos são necessários para iniciar uma renegociação?\n" +
                "   - Documentos básicos incluem CNPJ, comprovante de endereço e detalhes do contrato.\n" +
                "2. Quanto tempo leva o processo de renegociação?\n" +
                "   - O processo geralmente leva entre 7 a 15 dias úteis, dependendo da complexidade do caso.\n" +
                "3. Posso renegociar mais de um contrato ao mesmo tempo?\n" +
                "   - Sim, desde que todos os contratos estejam dentro das regras de elegibilidade.";
    }

}
