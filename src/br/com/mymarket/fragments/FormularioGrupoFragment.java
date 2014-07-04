package br.com.mymarket.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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
}
