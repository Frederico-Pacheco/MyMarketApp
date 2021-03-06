package br.com.mymarket.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import br.com.mymarket.R;
import br.com.mymarket.constants.Constants;
import br.com.mymarket.delegates.BuscaInformacaoDelegate;
import br.com.mymarket.delegates.ReceiverDelegate;
import br.com.mymarket.infra.CheckConnectivity;
import br.com.mymarket.infra.MyLog;
import br.com.mymarket.model.Pessoa;
import br.com.mymarket.navegacao.EstadoMainActivity;
import br.com.mymarket.receivers.OauthReceiver;
import br.com.mymarket.receivers.PerfilReceiver;
import br.com.mymarket.tasks.BuscarMeuPerfilTask;
import br.com.mymarket.tasks.OauthTask;

public class MainActivity extends AppBaseActivity implements
		BuscaInformacaoDelegate {

	private Pessoa perfil;
	private EstadoMainActivity estado;
	private Menu mainMenu;
	private BuscarMeuPerfilTask buscarMeuPerfilTask;
	private String myPhoneNumber;
	private int defaultWindowSystem;
	private ReceiverDelegate eventOauth;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.defaultWindowSystem = getWindow().getDecorView()
				.getSystemUiVisibility();
		hideSystemUI();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		registerBaseActivityReceiver();
		TelephonyManager tMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		this.myPhoneNumber = tMgr.getLine1Number();
		if (getMyMarketApplication().isAuthenticated()) {
			showNormalUI();
			this.estado = EstadoMainActivity.INICIO;
		} else {
			this.estado = EstadoMainActivity.OAUTH;
		}
		this.event = new PerfilReceiver().registraObservador(this);
		this.eventOauth = new OauthReceiver().registraObservador(this);
		adRequest();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
//		super.onSaveInstanceState(outState);
		MyLog.i("SALVANDO ESTADO!!");
		outState.putSerializable(Constants.ESTADO_ATUAL, this.estado);
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		MyLog.i("RESTAURANDO ESTADO!!");
		this.estado = (EstadoMainActivity) savedInstanceState
				.getSerializable(Constants.ESTADO_ATUAL);
	}

	@Override
	public void onResume() {
		super.onResume();
		MyLog.i("EXECUTANDO ESTADO!!" + this.estado);
		this.estado.executa(this);
	}

	public void alteraEstadoEExecuta(EstadoMainActivity estado) {
		this.estado = estado;
		this.estado.executa(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		this.mainMenu = menu;
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		this.mainMenu = menu;
		this.mainMenu.clear();
		if (this.estado != EstadoMainActivity.OAUTH) {
			getMenuInflater().inflate(R.menu.main, this.mainMenu);
			return true;
		}
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(this.estado == EstadoMainActivity.OAUTH){
			if (keyCode == KeyEvent.KEYCODE_BACK) {
				return false;
			}
			if (keyCode == KeyEvent.KEYCODE_MENU) {
				return false;
			}
			if (keyCode == KeyEvent.KEYCODE_HOME) {
				return false;
			}
			if (keyCode == KeyEvent.KEYCODE_SEARCH) {
				return false;
			}
			if (keyCode == KeyEvent.KEYCODE_SETTINGS) {
				return false;
			}			
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.menu_atualizar) {
			alteraEstadoEExecuta(EstadoMainActivity.INICIO);
			return false;
		} else if (item.getItemId() == R.id.menu_lista_compras) {
			startActivity(new Intent(this, ListaComprasActivity.class));
			return false;
		} else if (item.getItemId() == R.id.menu_meus_grupos) {
			startActivity(new Intent(this, GrupoActivity.class));
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

	@Override
	public void onBackPressed() {
		if (getMyMarketApplication().isAuthenticated()) {
			finish();
		}
		super.onBackPressed();
	}

	public void acessarApk(View view) {
		EditText cellPhone = (EditText) findViewById(R.id.oat_cellphone);
		if (CheckConnectivity.isOffLine(this)) {
			Toast.makeText(this, R.string.internet_fora, Toast.LENGTH_LONG)
					.show();
		} else if(!PhoneNumberUtils.isWellFormedSmsAddress(cellPhone.getText().toString())) {
			Toast.makeText(this, R.string.numero_invalido, Toast.LENGTH_LONG).show();
		} else {
			new OauthTask(this.getMyMarketApplication(), this.eventOauth).execute(cellPhone.getText().toString());
			alteraEstadoEExecuta(EstadoMainActivity.AGUARDANDO_PERFIL);
		}
	}

	@Override
	public void processaResultado(Object obj) {
		if(obj instanceof Pessoa){
			Pessoa pessoa = (Pessoa) obj;
			atualizaPerfil(pessoa);
			alteraEstadoEExecuta(EstadoMainActivity.PERFIL);
		}else if(obj instanceof String){
			getMyMarketApplication().setToken((String)obj);
			showNormalUI();
			alteraEstadoEExecuta(EstadoMainActivity.INICIO);
			this.onPrepareOptionsMenu(this.mainMenu);
		}
	}
	
	private void atualizaPerfil(Pessoa pessoa) {
		this.perfil = pessoa;
	}

	public Pessoa getPerfil() {
		return perfil;
	}

	public void buscarMeuPerfil() {
		this.buscarMeuPerfilTask = new BuscarMeuPerfilTask(getMyMarketApplication(), this.myPhoneNumber,this.event);
		this.buscarMeuPerfilTask.execute();
	}

	// This method hides the system bars and resize the content
	private void hideSystemUI() {
		getWindow().getDecorView().setSystemUiVisibility(
				View.SYSTEM_UI_FLAG_LAYOUT_STABLE
						| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
						| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
						| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
						| View.SYSTEM_UI_FLAG_FULLSCREEN);
	}

	public void showNormalUI() {
		getWindow().getDecorView().setSystemUiVisibility(defaultWindowSystem);
	}

}
