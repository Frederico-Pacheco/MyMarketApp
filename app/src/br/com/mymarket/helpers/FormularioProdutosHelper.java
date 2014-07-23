package br.com.mymarket.helpers;

import java.util.Calendar;
import java.util.Date;

import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import br.com.mymarket.R;
import br.com.mymarket.model.ListaCompra;
import br.com.mymarket.model.Produto;
import br.com.mymarket.utils.DateUtils;

public class FormularioProdutosHelper {

	private EditText nome;
	private Button botao;
	private Produto produto;
	
	public FormularioProdutosHelper(View view){
		nome = (EditText)view.findViewById(R.id.form_produtos_nome);
		botao = (Button)view.findViewById(R.id.btn_form_produtos);
		produto = new Produto();
	}


	public void colocarProdutoNoFormulario(Produto produto) {
		if(produto != null)
		{
			nome.setText(produto.getNome());
			botao.setText("Alterar");
			this.produto = produto;
		}else{
			botao.setText("Inserir");
		}
	}
	
	public Produto recuperarProduto(){
		produto.setNome(nome.getText().toString());
		return produto;
	}
	
}
