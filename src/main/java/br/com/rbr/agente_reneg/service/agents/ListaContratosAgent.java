package br.com.rbr.agente_reneg.service.agents;


import br.com.rbr.agente_reneg.tools.ListraContratosTools;
import org.springframework.stereotype.Service;

@Service
public class ListaContratosAgent {

    private final ListraContratosTools listraContratosTools;

    public ListaContratosAgent(ListraContratosTools listraContratosTools) {
        this.listraContratosTools = listraContratosTools;
    }

    public ListraContratosTools.ListaContratoResponse listaContratos(final ListraContratosTools.ListaContratoRequest listaContratoRequest) {
        System.out.println("AGENTE ESPECIALISTA DE CONTRATOS: Fui ativado.");
        return listraContratosTools.getContractsByCnpj(listaContratoRequest);
    }
}