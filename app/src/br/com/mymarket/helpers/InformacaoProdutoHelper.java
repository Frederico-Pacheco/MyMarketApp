package br.com.mymarket.helpers;

import android.view.View;
import android.widget.EditText;
import br.com.mymarket.R;
import br.com.mymarket.model.Produto;

public class InformacaoProdutoHelper {
	
	private EditText nome;
	
	public InformacaoProdutoHelper(View view){
		nome = (EditText)view.findViewById(R.id.form_produtos_nome);
	}

	public void colocarProdutoNoFormulario(Produto produto) {
			nome.setText(produto.getNome());
	}
}
