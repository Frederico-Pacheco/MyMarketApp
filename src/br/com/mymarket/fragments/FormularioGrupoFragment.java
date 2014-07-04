package br.com.mymarket.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import br.com.mymarket.R;
import br.com.mymarket.activities.GrupoActivity;
import br.com.mymarket.helpers.FormularioGrupoHelper;
import br.com.mymarket.model.Grupo;
import br.com.mymarket.navegacao.EstadoGrupoActivity;

public class FormularioGrupoFragment extends Fragment {
	
	private FormularioGrupoHelper formularioGrupoHelper;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.fragment_form_grupo, container, false);
    	final GrupoActivity activity = ((GrupoActivity)this.getActivity());
    	formularioGrupoHelper = new FormularioGrupoHelper(view);
    	formularioGrupoHelper.colocarGrupoNoFormulario(activity.getItemSelecionado());
    	Button button = (Button)view.findViewById(R.id.btn_form);
    	
    	
    	AutoCompleteTextView textView = (AutoCompleteTextView) view.findViewById(R.id.search_contatos);
    	String[] countries = getResources().getStringArray(R.array.countries_array);
    	ArrayAdapter<String> testAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, countries);
    	textView.setAdapter(testAdapter);
    	
//    	EditText editText = (EditText)view.findViewById(R.id.search_contatos);
//    	editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//    	    @Override
//    	    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//    	        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//    	        	MyLog.i("BUSCAR");
//    	            performSearch();//write code in function what you wanna do....
//    	            return true;
//    	        }
//    	        return false;
//    	    }
//
//    	});
    	
    	
    	button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Grupo grupo = formularioGrupoHelper.recuperarGrupo();
				//TODO PERSIST OR POST OR PUT.
				activity.persiste(grupo);
				activity.alteraEstadoEExecuta(EstadoGrupoActivity.LISTAS_RECEBIDAS);
			}
		});
        return view;
    }
    
	public void performSearch() {
		// TODO Auto-generated method stub
		
	}
}
