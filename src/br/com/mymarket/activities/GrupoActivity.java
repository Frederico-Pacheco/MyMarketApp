package br.com.mymarket.activities;

import java.util.List;

import android.os.Bundle;
import br.com.mymarket.R;
import br.com.mymarket.delegates.BuscaInformacaoDelegate;
import br.com.mymarket.infra.MyLog;
import br.com.mymarket.model.Grupo;
import br.com.mymarket.navegacao.EstadoGrupoActivity;

public class GrupoActivity extends AppBaseActivity implements BuscaInformacaoDelegate{
	
	private EstadoGrupoActivity estado;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerBaseActivityReceiver();
//        this.estado = EstadoListaComprasActivity.INICIO;
//        this.evento = EventoListaCompraRecebidas.registraObservador(this);
        getActionBar().setTitle(R.string.tela_lista_compras);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        MyLog.i("DESTRUIU O PICO");
//        this.evento.desregistra(getMyMarketApplication());
        unRegisterBaseActivityReceiver();
    }

	public void processaResultado(Object obj) {
		List<Grupo> listaGrupo = (List<Grupo>) obj; 
		
	}

	public void buscarListaGrupo() {
		// TODO Auto-generated method stub
		
	}

	public void alteraEstadoEExecuta(EstadoGrupoActivity aguardandoListagem) {
		// TODO Auto-generated method stub
		
	}
	
	
}
