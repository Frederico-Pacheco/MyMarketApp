package br.com.mymarket.helpers;

import java.util.Calendar;
import java.util.Date;

import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import br.com.mymarket.R;
import br.com.mymarket.model.ListaCompra;
import br.com.mymarket.utils.DateUtils;


public class FormularioListaCompraHelper {

	private EditText nome;
	private DatePicker data;
	private Button botao;
	
	private ListaCompra listaCompra;
	
	public FormularioListaCompraHelper(View view){
		nome = (EditText)view.findViewById(R.id.form_list_compras_nome);
		data = (DatePicker)view.findViewById(R.id.form_lista_compra_date);
		botao = (Button)view.findViewById(R.id.btn_form_lista_compras);
		listaCompra = new ListaCompra();
	}


	public void colocarListaComprasNoFormulario(ListaCompra listaCompra) {
		if(listaCompra != null)
		{
			nome.setText(listaCompra.getNome());
			data.init(listaCompra.getData().getYear(), listaCompra.getData().getMonth(), listaCompra.getData().getDay(), null);
			botao.setText("Alterar");
			this.listaCompra = listaCompra;
		}else{
			botao.setText("Inserir");
		}
	}
	
	public ListaCompra recuperarListaCompra(){
		listaCompra.setNome(nome.getText().toString());
		Date date = Calendar.getInstance().getTime();
		date.setDate(data.getDayOfMonth());
		date.setMonth(data.getMonth());
		date.setYear(data.getYear());
		DateUtils.zerarData(date);
		listaCompra.setData(date);
		return listaCompra;
	}

}
