package br.com.mymarket.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import br.com.mymarket.MyMarketApplication;
import br.com.mymarket.R;
import br.com.mymarket.constants.Constants;
import br.com.mymarket.delegates.BuscaListaComprasDelegate;
import br.com.mymarket.evento.EventoListaCompraRecebidas;
import br.com.mymarket.infra.MyLog;
import br.com.mymarket.model.ListaCompra;
import br.com.mymarket.navegacao.EstadoListaComprasActivity;
import br.com.mymarket.tasks.BuscarMaisListaCompraTask;

public class ListaComprasActivity extends AppBaseActivity implements BuscaListaComprasDelegate {
	private List<ListaCompra> listaCompra = new ArrayList<ListaCompra>();
	private EstadoListaComprasActivity estado;
	private BuscarMaisListaCompraTask buscarMaisListaCompraTask;
    private EventoListaCompraRecebidas evento;
    
    private ListaCompra listaCompraSelecionada = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listacompras);
        registerBaseActivityReceiver();
        this.estado = EstadoListaComprasActivity.INICIO;
        this.evento = EventoListaCompraRecebidas.registraObservador(this);
        getActionBar().setTitle(R.string.tela_lista_compras);
    }

    public void lidaComRetorno(Exception e){
        Toast.makeText(this, "Erro na busca dos dados", Toast.LENGTH_SHORT).show();
    }

    public void lidaComRetorno(List<ListaCompra> listas){
        this.estado = EstadoListaComprasActivity.LISTAS_RECEBIDAS;
        this.estado.executa(this);
    }

    public void onDestroy(){
        super.onDestroy();
        MyLog.i("DESTRUIU O PICO");
        this.evento.desregistra(getMyMarketApplication());
        unRegisterBaseActivityReceiver();
    }

    public MyMarketApplication getMyMarketApplication(){
        return (MyMarketApplication) getApplication();
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
        this.buscarMaisListaCompraTask = new BuscarMaisListaCompraTask(getMyMarketApplication());
        this.buscarMaisListaCompraTask.execute();
	}

	public List<ListaCompra> getListaCompras() {
		return listaCompra;
	}
	
   private void atualizaListaCom(List<ListaCompra> listaCompra) {
	   getListaCompras().clear();
	   getListaCompras().addAll(listaCompra);
    }

    public void processaResultado(Exception e){
        Toast.makeText(this, "Erro na busca dos dados", Toast.LENGTH_SHORT).show();
    }

    public void processaResultado(List<ListaCompra> listas){
    	atualizaListaCom(listas);
        this.estado = EstadoListaComprasActivity.LISTAS_RECEBIDAS;
        this.estado.executa(this);
    }

	public void persiste(ListaCompra listaCompra) {
		getListaCompras().add(listaCompra);
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
	public void onBackPressed() {
		if(this.estado == EstadoListaComprasActivity.CADASTRAR_LISTA){
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
		}
		return super.onContextItemSelected(item);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_lista, menu);
	}

	public void setItemSelecionado(ListaCompra itemSelecionado) {
		this.listaCompraSelecionada = itemSelecionado;
	}
	
	public ListaCompra getItemSelecionado() {
		return listaCompraSelecionada;
	}

}
