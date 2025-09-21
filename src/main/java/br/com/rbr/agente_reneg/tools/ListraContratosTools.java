
package br.com.rbr.agente_reneg.tools;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;



import java.util.Collections;
import java.util.List;

@Component
public class ListraContratosTools {

    @Tool(description = "Verifica os contratos de dívida de uma empresa (Pessoa Jurídica) usando o CNPJ. Use esta função para obter a lista de contratos renegociáveis e não renegociáveis.")
    public ListaContratoResponse getContractsByCnpj(ListaContratoRequest request) {
        String cnpj = request.cnpj().replaceAll("[^0-9]", "");
        if (cnpj.length() != 14) {
            return new ListaContratoResponse(Collections.emptyList(), "ERRO: O serviço é exclusivo para Pessoas Jurídicas (CNPJ). O valor informado não possui 14 dígitos.");
        }
        System.out.println("*** Ferramenta usada: ListraContratosTools ***");

        return mockChamadaListaContratos(cnpj);
    }

    public record ListaContratoRequest(String cnpj) {}
    public record ListaContratoResponse(List<Contrato> contratos, String error) {}
    public record Contrato(
            @JsonProperty("numeroContrato") String numeroContrato,
            @JsonProperty("statusRenegociacao") String statusRenegociacao,
            @JsonProperty("detalhes") DetalhesContrato detalhes) {}
    public record DetalhesContrato(
            @JsonProperty("tipoProduto") String tipoProduto,
            @JsonProperty("diasEmAtraso") int diasEmAtraso,
            @JsonProperty("sistemaOrigem") String sistemaOrigem,
            @JsonProperty("possuiGarantias") boolean possuiGarantias) {}


    private ListaContratoResponse mockChamadaListaContratos(String cnpj) {
        if ("11222333000144".equals(cnpj)) {
            var nonRenegotiableContract = new Contrato("987654", "Não Renegociável",
                    new DetalhesContrato("Financiamento de Veículo", 35, "FINAN_SYS", true));
            var renegotiableContract = new Contrato("123456", "Renegociável",
                    new DetalhesContrato("Capital de Giro", 120, "MAIN_SYS", false));
            return new ListaContratoResponse(List.of(renegotiableContract, nonRenegotiableContract), null);
        }
        return new ListaContratoResponse(Collections.emptyList(), null);
    }

}