package br.com.mymarket.activities;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.widget.Toast;
import br.com.mymarket.R;
import br.com.mymarket.constants.Constants;
import br.com.mymarket.constants.Extras;
import br.com.mymarket.delegates.BuscaInformacaoDelegate;
import br.com.mymarket.evento.EventoProdutoRecebido;
import br.com.mymarket.infra.MyLog;
import br.com.mymarket.model.ListaCompra;
import br.com.mymarket.model.Produto;
import br.com.mymarket.navegacao.EstadoProdutosActivity;
import br.com.mymarket.tasks.BuscarProdutosTask;

public class ProdutosActivity extends AppBaseActivity implements BuscaInformacaoDelegate{

	private ListaCompra listaCompra = null;
	private List<Produto> produtos = new ArrayList<Produto>();
	private EstadoProdutosActivity estado;
	private EventoProdutoRecebido evento;
	private BuscarProdutosTask buscarProdutosTask;
	private Produto itemSelecionado;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.listaCompra = (ListaCompra) getIntent().getSerializableExtra(Extras.EXTRA_LISTA_COMPRA);
        this.estado = EstadoProdutosActivity.INICIO;
        this.evento = EventoProdutoRecebido.registraObservador(this);
	}
	
	public void buscarProdutos() {
        this.buscarProdutosTask = new BuscarProdutosTask(getMyMarketApplication(),this.listaCompra);
        this.buscarProdutosTask.execute();
	}

	public void alteraEstadoEExecuta(EstadoProdutosActivity estado) {
        this.estado = estado;
        this.estado.executa(this);
    }

    public void processaResultado(Object obj){
    	if(obj instanceof Exception){
    		Toast.makeText(this, "Erro na busca dos dados", Toast.LENGTH_SHORT).show();	
    	}else{
        	List<Produto> listas = (List<Produto>) obj;
        	atualizaListaCom(listas);  
            this.estado = EstadoProdutosActivity.LISTAGEM;
            this.estado.executa(this);
    	}
    }
	
	private void atualizaListaCom(List<Produto> listas) {
	   getProdutos().clear();
	   getProdutos().addAll(listas);
	}

	public List<Produto> getProdutos() {
		return this.produtos;
	}

	@Override
    public void onDestroy(){
        super.onDestroy();
        MyLog.i("DESTRUIU O PICO");
        this.evento.desregistra(getMyMarketApplication());
        unRegisterBaseActivityReceiver();
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        MyLog.i("SALVANDO ESTADO!!");
        outState.putSerializable(Constants.ESTADO_ATUAL,this.estado);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        MyLog.i("RESTAURANDO ESTADO!!");
        this.estado = (EstadoProdutosActivity) savedInstanceState.getSerializable(Constants.ESTADO_ATUAL);
    }

    @Override
    public void onResume(){
        super.onResume();
        MyLog.i("EXECUTANDO ESTADO!!" + this.estado);
        this.estado.executa(this);
    }
    
	public void setItemSelecionado(Produto itemSelecionado) {
		this.itemSelecionado = itemSelecionado;
	}
	
	public Produto getItemSelecionado() {
		return itemSelecionado;
	}
	
}
