package br.com.mymarket.delegates;

import java.util.List;

import br.com.mymarket.MyMarketApplication;
import br.com.mymarket.model.ListaCompra;

public interface BuscaListaComprasDelegate {
    MyMarketApplication getMyMarketApplication();
    void processaResultado(List<ListaCompra> listas);
    void processaResultado(Exception e);
}
