package br.com.mymarket.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import br.com.mymarket.R;
import br.com.mymarket.adapters.ProdutosAdapter;
import br.com.mymarket.constants.Constants;
import br.com.mymarket.constants.Extras;
import br.com.mymarket.delegates.BuscaInformacaoDelegate;
import br.com.mymarket.evento.EventoProdutoRecebido;
import br.com.mymarket.infra.ActionModeCallback;
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
	private int posicaoItemSelecionado;
	private ActionMode mActionMode;
	private ProdutosAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerBaseActivityReceiver();
        this.listaCompra = (ListaCompra) getIntent().getSerializableExtra(Extras.EXTRA_LISTA_COMPRA);
        this.estado = EstadoProdutosActivity.INICIO;
        this.evento = EventoProdutoRecebido.registraObservador(this);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_lista, menu);
	    menu.setHeaderTitle(R.string.comum_selecione);  
	    menu.add(0, v.getId(), 0, (String)getString(R.string.menu_val_sel_produtos));
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.cxmenu_deletar){
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
			alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
			alertDialog.setTitle(R.string.app_name);
			alertDialog.setMessage(R.string.comum_deletar_registro);
			alertDialog.setPositiveButton(R.string.comum_sim, new OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					//FIXME CHAMAR DELETE.
					if(getItemSelecionado() != null){
						getProdutos().remove(getItemSelecionado());
					}
					alteraEstadoEExecuta(EstadoProdutosActivity.LISTAGEM);//FIXME ALTERAR INICIO.
				}
			});
			alertDialog.setNegativeButton(R.string.comum_nao, null);
			alertDialog.show();
		}else if(item.getItemId() == R.id.cxmenu_alterar){
//			alteraEstadoEExecuta(EstadoProdutosActivity.CADASTRAR);
		}else if(item.getTitle().equals((String)getString(R.string.menu_val_sel_produtos))){
			onListItemSelect(getItemSelecionado(),getPosicaoItemSelecionado());
		}
		return super.onContextItemSelected(item);
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
    	List<Produto> listas = (List<Produto>) obj;
    	atualizaListaCom(listas);  
        this.estado = EstadoProdutosActivity.LISTAGEM;
        this.estado.executa(this);
    }
    
	public void processarException(Exception e) {
		Toast.makeText(this, "Erro na busca dos dados", Toast.LENGTH_SHORT).show();	
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
	
	public void setPosicaoItemSelecionado(int posicaoItemSelecionado) {
		this.posicaoItemSelecionado = posicaoItemSelecionado;
	}
	
	public int getPosicaoItemSelecionado() {
		return posicaoItemSelecionado;
	}
	
	public ActionMode getActionMode(){
		return this.mActionMode;
	}

	public void setarInformacoesAdapter() {
		this.adapter = new ProdutosAdapter(this, getProdutos());
	}

	public ProdutosAdapter getAdapter() {
		return this.adapter;
	}

	public void resetActionMode() {
		this.mActionMode = null;
	}

	public void setarItensComprados(Produto produto, int posicao) {
		if(getActionMode() != null){
			onListItemSelect(produto,posicao);
		}
	}
	@Override
	public void onBackPressed() {
		if(getActionMode() != null){
			resetActionMode();
			return;
		}
		super.onBackPressed();
	}
	
	
  private void onListItemSelect(Produto produto, int posicao) {
	if(!produto.isComprado())
	{
		getAdapter().toggleSelection(posicao);
		boolean hasCheckedItems = getAdapter().getSelectedCount() > 0;
		if (hasCheckedItems && getActionMode() == null){
			this.mActionMode = startActionMode(new ActionModeCallback(this));
		}else if (!hasCheckedItems && getActionMode() != null){
			getActionMode().finish();
		}
		if (getActionMode() != null)
		{
			getActionMode().setTitle(String.valueOf(getAdapter().getSelectedCount()) + " selecionados");
		}
	}
  }

	public void confirmarCompra() {
		MyLog.i("YAY!");
		// TODO ABRIR POPUP INFORMANDO O PRECO PAGO.
		//CONFIRMAR
	}
	
}
