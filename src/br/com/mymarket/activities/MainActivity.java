package br.com.mymarket.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import br.com.mymarket.evento.EventoPerfilRecebido;
import br.com.mymarket.helpers.OauthHelper;
import br.com.mymarket.infra.CheckConnectivity;
import br.com.mymarket.infra.MyLog;
import br.com.mymarket.model.Pessoa;
import br.com.mymarket.navegacao.EstadoMainActivity;
import br.com.mymarket.tasks.BuscarMeuPerfilTask;

public class MainActivity extends AppBaseActivity implements
		BuscaInformacaoDelegate {

	private Pessoa perfil;

	private EstadoMainActivity estado;
	private OauthHelper oauthHelper;
	private Menu mainMenu;
	private EventoPerfilRecebido evento;
	private BuscarMeuPerfilTask buscarMeuPerfilTask;
	private String myPhoneNumber;
	private int defaultWindowSystem;

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
		oauthHelper = new OauthHelper(this);
		if (oauthHelper.verifyPhoneNumber(this.myPhoneNumber)) {
			showNormalUI();
			this.estado = EstadoMainActivity.INICIO;
		} else {
			this.estado = EstadoMainActivity.OAUTH;
		}
		this.evento = EventoPerfilRecebido.registraObservador(this);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
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
		setUIInterface();
	}

	private void setUIInterface() {
		if (this.estado != EstadoMainActivity.OAUTH) {
			showNormalUI();
			onPrepareOptionsMenu(this.mainMenu);
		} else if (this.estado == EstadoMainActivity.OAUTH) {
			hideSystemUI();
		}
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
		if (new OauthHelper(this).verifyPhoneNumber(this.myPhoneNumber)) {
			finish();
		}
		super.onBackPressed();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		this.evento.desregistra(getMyMarketApplication());
		unRegisterBaseActivityReceiver();
	}

	public void acessarApk(View view) {
		EditText cellPhone = (EditText) findViewById(R.id.oat_cellphone);
		if (CheckConnectivity.isOffLine(this)) {
			Toast.makeText(this, R.string.internet_fora, Toast.LENGTH_LONG)
					.show();
		} else if (!oauthHelper.acessarAplicativo(cellPhone.getText()
				.toString())) {
			Toast.makeText(this, R.string.numero_invalido, Toast.LENGTH_LONG)
					.show();
		} else {
			showNormalUI();
			alteraEstadoEExecuta(EstadoMainActivity.INICIO);
			this.onPrepareOptionsMenu(this.mainMenu);
		}
	}

	@Override
	public void processaResultado(Object obj) {
		Pessoa pessoa = (Pessoa) obj;
		atualizaPerfil(pessoa);
		this.estado = EstadoMainActivity.PERFIL;
		this.estado.executa(this);	
	}
	
	public void processarException(Exception e) {
		Toast.makeText(this, "Erro na busca dos dados", Toast.LENGTH_SHORT).show();	
	}

	private void atualizaPerfil(Pessoa pessoa) {
		this.perfil = pessoa;
	}

	public Pessoa getPerfil() {
		return perfil;
	}

	public void buscarMeuPerfil() {
		this.buscarMeuPerfilTask = new BuscarMeuPerfilTask(
				getMyMarketApplication(), this.myPhoneNumber);
		this.buscarMeuPerfilTask.execute();
	}

	// This method hides the system bars and resize the content
	private void hideSystemUI() {
		getWindow().getDecorView().setSystemUiVisibility(
				View.SYSTEM_UI_FLAG_LAYOUT_STABLE
						| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
						| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
						| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
						| View.SYSTEM_UI_FLAG_FULLSCREEN
						| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
	}

	public void showNormalUI() {
		getWindow().getDecorView().setSystemUiVisibility(defaultWindowSystem);
	}

}
