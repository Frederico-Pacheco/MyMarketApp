package br.com.mymarket.converters;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import br.com.mymarket.model.ListaCompra;
import br.com.mymarket.utils.DateUtils;

public class ListaCompraConverter {
	   public List<ListaCompra> convert(String json) {
	        try {
	            JSONArray js = new JSONObject(json).getJSONArray("list");

	            List<ListaCompra> listas = new ArrayList<ListaCompra>();
	            for (int i = 0; i < js.length(); i++) {
	                JSONObject listacompra = js.getJSONObject(i);
	                
	                Long id = listacompra.getLong("id");
	                Calendar data = Calendar.getInstance();
	                data.setTime(DateUtils.toDate((String)listacompra.getString("data")));
	                String nome = listacompra.getString("nome");
	                
	                listas.add(new ListaCompra(id, data, nome));
	            }
	            return listas;
	        } catch (Exception e) {
	            throw new RuntimeException(e);
	        }
	    }
}
