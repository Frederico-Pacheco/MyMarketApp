package br.com.mymarket.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import br.com.mymarket.R;
import br.com.mymarket.constants.Constants;
import br.com.mymarket.constants.Extras;
import br.com.mymarket.delegates.BuscaInformacaoDelegate;
import br.com.mymarket.infra.MyLog;
import br.com.mymarket.model.ListaCompra;
import br.com.mymarket.navegacao.EstadoListaComprasActivity;
import br.com.mymarket.receivers.ListaCompraReceiver;
import br.com.mymarket.tasks.BuscarMaisListaCompraTask;

public class ListaComprasActivity extends AppBaseActivity implements BuscaInformacaoDelegate {
	
	private List<ListaCompra> listaCompra = new ArrayList<ListaCompra>();
	private EstadoListaComprasActivity estado;
	private BuscarMaisListaCompraTask buscarMaisListaCompraTask;
    
    private ListaCompra listaCompraSelecionada = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerBaseActivityReceiver();
        this.estado = EstadoListaComprasActivity.INICIO;
        this.event = new ListaCompraReceiver();
        this.event.registraObservador(this);
        getActionBar().setTitle(R.string.tela_lista_compras);
    }

    public void alteraEstadoEExecuta(EstadoListaComprasActivity estado){
        this.estado = estado;
        this.estado.executa(this);
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
        this.estado = (EstadoListaComprasActivity) savedInstanceState.getSerializable(Constants.ESTADO_ATUAL);
    }

    @Override
    public void onResume(){
        super.onResume();
        MyLog.i("EXECUTANDO ESTADO!!" + this.estado);
        this.estado.executa(this);
    }

	public void buscarListasDeCompras() {
        this.buscarMaisListaCompraTask = new BuscarMaisListaCompraTask(getMyMarketApplication(),this.event);
        this.buscarMaisListaCompraTask.execute();
	}

	public List<ListaCompra> getListaCompras() {
		return listaCompra;
	}
	
   private void atualizaListaCom(List<ListaCompra> listaCompra) {
	   getListaCompras().clear();
	   getListaCompras().addAll(listaCompra);
    }

    public void processaResultado(Object obj){
    	List<ListaCompra> listas = (List<ListaCompra>) obj;
    	atualizaListaCom(listas);
    	alteraEstadoEExecuta(EstadoListaComprasActivity.LISTAS_RECEBIDAS);
    }
    

	public void persiste(ListaCompra listaCompra) {
		getListaCompras().add(listaCompra);
	}

	@Override
	public void onBackPressed() {
		if(this.estado == EstadoListaComprasActivity.CADASTRAR_LISTA || this.estado == EstadoListaComprasActivity.INFORMACOES_COMPRAS){
			alteraEstadoEExecuta(EstadoListaComprasActivity.LISTAS_RECEBIDAS);//FIXME ALTERAR INICIO
			return;
		}
		super.onBackPressed();
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
						getListaCompras().remove(getItemSelecionado());
					}
					alteraEstadoEExecuta(EstadoListaComprasActivity.LISTAS_RECEBIDAS);//FIXME ALTERAR INICIO.
				}
			});
			alertDialog.setNegativeButton(R.string.comum_nao, null);
			alertDialog.show();
		}else if(item.getItemId() == R.id.cxmenu_alterar){
			alteraEstadoEExecuta(EstadoListaComprasActivity.CADASTRAR_LISTA);
		}else if(item.getTitle().equals((String)getString(R.string.menu_val_add_produtos))){
			Intent produtos = new Intent(this,ProdutosActivity.class);
			produtos.putExtra(Extras.EXTRA_LISTA_COMPRA, (ListaCompra)getItemSelecionado());
			startActivity(produtos);
		}else if(item.getTitle().equals((String)getString(R.string.menu_val_ja_comprado))){
			alteraEstadoEExecuta(EstadoListaComprasActivity.INFORMACOES_COMPRAS);
		}
		return super.onContextItemSelected(item);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_lista, menu);
	    menu.setHeaderTitle(R.string.comum_selecione);  
	    menu.add(0, v.getId(), 0, (String)getString(R.string.menu_val_add_produtos));
	    menu.add(0, v.getId(), 0, (String)getString(R.string.menu_val_ja_comprado));
	}

	public void setItemSelecionado(ListaCompra itemSelecionado) {
		this.listaCompraSelecionada = itemSelecionado;
	}
	
	public ListaCompra getItemSelecionado() {
		return listaCompraSelecionada;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_form, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.menu_perfil){
			this.estado = EstadoListaComprasActivity.LISTAS_RECEBIDAS;
			onBackPressed();
			return false;
		}else if(item.getItemId() == R.id.menu_novo) {
			setItemSelecionado(null);
			alteraEstadoEExecuta(EstadoListaComprasActivity.CADASTRAR_LISTA);
			return false;
		} else if (item.getItemId() == R.id.menu_meus_grupos) {
			startActivity(new Intent(this, GrupoActivity.class));
			return false;
		}else if(item.getItemId() == R.id.menu_atualizar){
			alteraEstadoEExecuta(EstadoListaComprasActivity.INICIO);
			return false;
		}else if(item.getItemId() == R.id.menu_sair){
			closeAllActivities();
			return false;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		MenuItem item = menu.findItem(R.id.menu_lista_compras);
		item.setVisible(false);
		return super.onPrepareOptionsMenu(menu);
	}

}
