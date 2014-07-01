package br.com.mymarket.mocks;

import java.util.Arrays;
import java.util.List;

import br.com.mymarket.model.Produto;

public class ProdutoMock {
	public static List<Produto> get(){
		Produto prod1 = new Produto("Produto p comprar", 4.0, 4.5, false);
		Produto prod2 = new Produto("Produto comprado", 2.0, 6.6, true);
		return Arrays.asList(prod1,prod2);
	}
}
