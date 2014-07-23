package br.com.mymarket.delegates;

import br.com.mymarket.MyMarketApplication;

public interface BuscaInformacaoDelegate {
    MyMarketApplication getMyMarketApplication();
    void processaResultado(Object obj);
    void processarException(Exception e);
}
