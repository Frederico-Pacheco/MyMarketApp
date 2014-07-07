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
import br.com.mymarket.delegates.BuscaInformacaoDelegate;
import br.com.mymarket.infra.MyLog;
import br.com.mymarket.model.Grupo;
import br.com.mymarket.navegacao.EstadoGrupoActivity;
import br.com.mymarket.receivers.GrupoReceiver;
import br.com.mymarket.tasks.BuscarGrupoTask;

public class GrupoActivity extends AppBaseActivity implements BuscaInformacaoDelegate{
	
	private EstadoGrupoActivity estado;
	private List<Grupo> listaGrupo = new ArrayList<>();
	private Grupo itemSelecionado;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerBaseActivityReceiver();
        this.estado = EstadoGrupoActivity.INICIO;
        this.event = new GrupoReceiver().registraObservador(this);
        getActionBar().setTitle(R.string.tela_grupos);
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
        this.estado = (EstadoGrupoActivity) savedInstanceState.getSerializable(Constants.ESTADO_ATUAL);
    }

    @Override
    public void onResume(){
        super.onResume();
        MyLog.i("EXECUTANDO ESTADO!!" + this.estado);
        this.estado.executa(this);
    }
    
	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_form, menu);
		return true;
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_lista, menu);
		menu.setHeaderTitle(R.string.comum_selecione);
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
						getListaGrupo().remove(getItemSelecionado());
					}
					alteraEstadoEExecuta(EstadoGrupoActivity.LISTAS_RECEBIDAS);//FIXME ALTERAR INICIO.
				}
			});
			alertDialog.setNegativeButton(R.string.comum_nao, null);
			alertDialog.show();
		}else if(item.getItemId() == R.id.cxmenu_alterar){
			alteraEstadoEExecuta(EstadoGrupoActivity.CADASTRAR);
		}
		return super.onContextItemSelected(item);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.menu_atualizar) {
			alteraEstadoEExecuta(EstadoGrupoActivity.INICIO);
			return false;
		}else if(item.getItemId() == R.id.menu_novo) {
			setItemSelecionado(null);
			alteraEstadoEExecuta(EstadoGrupoActivity.CADASTRAR);
			return false;
		} else if(item.getItemId() == R.id.menu_perfil){
			onBackPressed();
			return false;
		}else if (item.getItemId() == R.id.menu_lista_compras) {
			startActivity(new Intent(this, ListaComprasActivity.class));
			return false;
		} else if (item.getItemId() == R.id.menu_configuracoes) {
			return false;
		} else if (item.getItemId() == R.id.menu_meus_lembretes) {
			return false;
		} else if (item.getItemId() == R.id.menu_sair) {
			closeAllActivities();
			return false;
		}
		return super.onOptionsItemSelected(item);
	}
	

	public void processaResultado(Object obj) {
		List<Grupo> listaGrupo = (List<Grupo>) obj; 
    	atualizaListaCom(listaGrupo);  
    	alteraEstadoEExecuta(EstadoGrupoActivity.LISTAS_RECEBIDAS);
	}

	private void atualizaListaCom(List<Grupo> listaGrupo) {
		getListaGrupo().clear();
		getListaGrupo().addAll(listaGrupo);
	}

	public void buscarListaGrupo() {
		new BuscarGrupoTask(getMyMarketApplication(), this.event).execute();
	}

	public void alteraEstadoEExecuta(EstadoGrupoActivity estado) {
		this.estado = estado;
		this.estado.executa(this);
	}

	public List<Grupo> getListaGrupo() {
		return listaGrupo;
	}

	public void setItemSelecionado(Grupo itemSelecionado) {
		this.itemSelecionado = itemSelecionado;
	}
	
	public Grupo getItemSelecionado() {
		return itemSelecionado;
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		MenuItem item = menu.findItem(R.id.menu_meus_grupos);
		item.setVisible(false);
		return super.onPrepareOptionsMenu(menu);
	}

	public void persiste(Grupo grupo) {
		getListaGrupo().add(grupo);
	}
	
}
