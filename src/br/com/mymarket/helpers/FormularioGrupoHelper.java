package br.com.mymarket.helpers;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import br.com.mymarket.R;
import br.com.mymarket.model.Grupo;
import br.com.mymarket.model.Pessoa;


public class FormularioGrupoHelper {

	private EditText nome;
	private Button botao;
	
	private Grupo grupo;
	
	public FormularioGrupoHelper(View view){
		nome = (EditText)view.findViewById(R.id.form_grupo_nome);
		botao = (Button)view.findViewById(R.id.btn_form);
		grupo = new Grupo();
	}


	public void colocarGrupoNoFormulario(Grupo grupo) {
		if(grupo != null)
		{
			nome.setText(grupo.getNome());
			botao.setText("Alterar");
			this.grupo = grupo;
		}else{
			botao.setText("Inserir");
		}
	}
	
	public Grupo recuperarGrupo(){
		grupo.setNome(nome.getText().toString());
		return grupo;
	}

	public List<Pessoa> getIntegrantes() {
		List<Pessoa> integrantes = new ArrayList<>();
		if(this.grupo != null && this.grupo.getIntegrantes() != null && !this.grupo.getIntegrantes().isEmpty()){
			integrantes = this.grupo.getIntegrantes();
		}
		return integrantes;
	}

}
