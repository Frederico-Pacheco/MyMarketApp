package br.com.mymarket.delegates;

import br.com.mymarket.MyMarketApplication;
import br.com.mymarket.model.Pessoa;

public interface BuscaPerfilDelegate {
    MyMarketApplication getMyMarketApplication();
    void processaResultado(Pessoa pessoa);
    void processaResultado(Exception e);
}
