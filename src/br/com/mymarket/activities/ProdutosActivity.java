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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import br.com.mymarket.R;
import br.com.mymarket.adapters.ProdutosAdapter;
import br.com.mymarket.constants.Constants;
import br.com.mymarket.constants.Extras;
import br.com.mymarket.delegates.BuscaInformacaoDelegate;
import br.com.mymarket.infra.ActionModeProdutoCallback;
import br.com.mymarket.infra.MyLog;
import br.com.mymarket.model.ListaCompra;
import br.com.mymarket.model.Produto;
import br.com.mymarket.navegacao.EstadoProdutosActivity;
import br.com.mymarket.receivers.ProdutoReceiver;
import br.com.mymarket.tasks.BuscarProdutosTask;

public class ProdutosActivity extends AppBaseActivity implements BuscaInformacaoDelegate{

	private ListaCompra listaCompra = null;
	private List<Produto> produtos = new ArrayList<Produto>();
	private EstadoProdutosActivity estado;
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
        this.event = new ProdutoReceiver().registraObservador(this);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_lista, menu);
		menu.setHeaderTitle(R.string.comum_selecione);
		MenuItem deletar = menu.findItem(R.id.cxmenu_deletar);
		MenuItem alterar = menu.findItem(R.id.cxmenu_alterar);
		if(getItemSelecionado().isComprado()){
			deletar.setVisible(false);
			alterar.setVisible(false);			
		    menu.add(0, v.getId(), 0, (String)getString(R.string.cxmenu_informacao));
		}else{
			deletar.setVisible(true);
			alterar.setVisible(true);
		    menu.add(0, v.getId(), 0, (String)getString(R.string.menu_val_sel_produtos));			
		}
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
			alteraEstadoEExecuta(EstadoProdutosActivity.CADASTRAR);
		}else if(item.getTitle().equals((String)getString(R.string.menu_val_sel_produtos))){
			onListItemSelect(getItemSelecionado(),getPosicaoItemSelecionado());
		}else if(item.getTitle().equals((String)getString(R.string.cxmenu_informacao))){
			alteraEstadoEExecuta(EstadoProdutosActivity.INFORMACOES);
		}
		return super.onContextItemSelected(item);
	}
	
	public void buscarProdutos() {
        this.buscarProdutosTask = new BuscarProdutosTask(getMyMarketApplication(),this.listaCompra,this.event);
        this.buscarProdutosTask.execute();
	}

	public void alteraEstadoEExecuta(EstadoProdutosActivity estado) {
        this.estado = estado;
        this.estado.executa(this);
    }

    public void processaResultado(Object obj){
    	List<Produto> listas = (List<Produto>) obj;
    	atualizaListaCom(listas);
    	alteraEstadoEExecuta(EstadoProdutosActivity.LISTAGEM);
    }
    
	private void atualizaListaCom(List<Produto> listas) {
	   getProdutos().clear();
	   getProdutos().addAll(listas);
	}

	public List<Produto> getProdutos() {
		return this.produtos;
	}

    @Override
    public void onSaveInstanceState(Bundle outState){
//        super.onSaveInstanceState(outState);
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
		}else if(this.estado == EstadoProdutosActivity.CADASTRAR || this.estado == EstadoProdutosActivity.INFORMACOES){
			alteraEstadoEExecuta(EstadoProdutosActivity.INICIO);//FIXME ALTERAR INICIO
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
			this.mActionMode = startActionMode(new ActionModeProdutoCallback(this));
		}else if (!hasCheckedItems && getActionMode() != null){
			getActionMode().finish();
		}
		if (getActionMode() != null)
		{
			String selecionado = getAdapter().getSelectedCount() == 1 ? (String)getString(R.string.comum_selecionado) : (String)getString(R.string.comum_selecionados);
			getActionMode().setTitle(String.valueOf(getAdapter().getSelectedCount()) + " " + selecionado);
		}
	}
  }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.simple_menu_form, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.menu_novo) {
			setItemSelecionado(null);
			alteraEstadoEExecuta(EstadoProdutosActivity.CADASTRAR);
			return false;
		}else if(item.getItemId() == R.id.menu_atualizar){
			alteraEstadoEExecuta(EstadoProdutosActivity.INICIO);
			return false;
		}else if(item.getItemId() == R.id.menu_sair){
			closeAllActivities();
			return false;
		}
		return super.onOptionsItemSelected(item);
	}

	public void persiste(Produto produto) {
		getProdutos().add(produto);
	}
	
	public void popupConfirmarCompra(final List<Produto> listProdutosSelecionados){
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
		alertDialog.setTitle(R.string.app_name);
		alertDialog.setMessage(R.string.comum_confirmar_compra);
		LayoutInflater inflater = getLayoutInflater();
		View inflate = inflater.inflate(R.layout.popup, null);
		final EditText editText = (EditText) inflate.findViewById(R.id.popup_valor_compra);
		alertDialog.setView(inflate);
		alertDialog.setPositiveButton(R.string.comum_confirmar, new OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				//FIXME CHAMAR POST PARA GERAR COMPRA. 
				MyLog.i(listProdutosSelecionados.size()+" - " + editText.getText());
			}
		});
		alertDialog.setNegativeButton(R.string.comum_cancelar, null);
		alertDialog.show();
	}
	
}
