package br.com.mymarket.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import br.com.mymarket.R;
import br.com.mymarket.activities.GrupoActivity;
import br.com.mymarket.adapters.ContatosAdapter;
import br.com.mymarket.adapters.FormGrupoContatoAdapter;
import br.com.mymarket.helpers.FormularioGrupoHelper;
import br.com.mymarket.infra.MyLog;
import br.com.mymarket.model.Grupo;
import br.com.mymarket.model.Pessoa;
import br.com.mymarket.navegacao.EstadoGrupoActivity;

public class FormularioGrupoFragment extends Fragment {
	
	private FormularioGrupoHelper formularioGrupoHelper;
	private FormGrupoContatoAdapter formAdapter;
	private ContatosAdapter contatosAdapter;
	private List<Pessoa> contatos;
	private GrupoActivity activity;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	MyLog.i("FORM GRUPO CRIADO");
    	
    	View view = inflater.inflate(R.layout.fragment_form_grupo, container, false);
    	activity = ((GrupoActivity)this.getActivity());
    	contatos = activity.getMyMarketApplication().getContato();
    	
    	formularioGrupoHelper = new FormularioGrupoHelper(view);
    	formularioGrupoHelper.colocarGrupoNoFormulario(activity.getItemSelecionado());
    	Button button = (Button)view.findViewById(R.id.btn_form);
    	
    	formAdapter = new FormGrupoContatoAdapter(activity, formularioGrupoHelper.getIntegrantes());
    	contatosAdapter = new ContatosAdapter(activity,R.layout.listview_contatos, contatos);
    	
    	
    	ListView listContatoAdd = (ListView)view.findViewById(R.id.contatos_grupo);
    	listContatoAdd.setAdapter(formAdapter);
    	
    	final AutoCompleteTextView autoCompleteView = (AutoCompleteTextView) view.findViewById(R.id.search_contatos);
    	autoCompleteView.setAdapter(contatosAdapter);
    	autoCompleteView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int posicao,long id) {
				Pessoa pessoaAdd = (Pessoa)adapter.getItemAtPosition(posicao);
				formAdapter.add(pessoaAdd);
				contatosAdapter.remove(pessoaAdd);
				autoCompleteView.setText("");
			}
		});
    	
    	
    	listContatoAdd.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int posicao,long id) {
				Pessoa pessoaRemove = (Pessoa)adapter.getItemAtPosition(posicao);
				formAdapter.remove(pessoaRemove);
				contatosAdapter.add(pessoaRemove);
			}
		});
    	
    	button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Grupo grupo = formularioGrupoHelper.recuperarGrupo();
				grupo.setIntegrantes(formAdapter.getLista());
				//TODO PERSIST OR POST OR PUT.
				activity.persiste(grupo);
				activity.alteraEstadoEExecuta(EstadoGrupoActivity.LISTAS_RECEBIDAS);
			}
		});
        return view;
    }

}
