package br.com.mymarket.mocks;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.mymarket.model.ListaCompra;

public class ListaComprasMocks {

	public static List<ListaCompra> get(){
		Calendar data = Calendar.getInstance();
		return Arrays.asList(new ListaCompra(1L, data, "LISTA COMPRA 1"),new ListaCompra(2L, data, "LISTA COMPRA 2"),new ListaCompra(3L, data, "LISTA COMPRA 3"),new ListaCompra(4L, data, "LISTA COMPRA 4"));
	}
	
}
