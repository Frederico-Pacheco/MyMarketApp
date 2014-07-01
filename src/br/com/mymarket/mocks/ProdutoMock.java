package br.com.mymarket.mocks;

import java.util.Arrays;
import java.util.List;

import br.com.mymarket.model.Produto;

public class ProdutoMock {
	public static List<Produto> get(){
		Produto prod1 = new Produto("Produto p comprar ", 4.0, 4.5, false);
		Produto prod2 = new Produto("Produto comprado", 2.0, 6.6, true);
		Produto prod3 = new Produto("Produto p comprar", 2.0, 6.6, false);
		Produto prod4 = new Produto("Produto comprado", 2.0, 6.6, true);
		Produto prod5 = new Produto("Produto p comprar", 2.0, 6.6, false);
		Produto prod6 = new Produto("Produto p comprar", 2.0, 6.6, false);
		Produto prod7 = new Produto("Produto p comprar", 2.0, 6.6, false);
		Produto prod8 = new Produto("Produto p comprar", 2.0, 6.6, false);
		Produto prod9 = new Produto("Produto p comprar", 2.0, 6.6, false);
		Produto prod10 = new Produto("Produto comprado", 2.0, 6.6, true);
		Produto prod11 = new Produto("Produto p comprar", 2.0, 6.6, false);
		Produto prod12 = new Produto("Produto comprado", 2.0, 6.6, true);
		Produto prod13 = new Produto("Produto p comprar", 2.0, 6.6, false);
		Produto prod14 = new Produto("Produto comprado", 2.0, 6.6, true);
		Produto prod15 = new Produto("Produto comprado", 2.0, 6.6, true);
		Produto prod16 = new Produto("Produto comprado", 2.0, 6.6, true);
		return Arrays.asList(prod1,prod2,prod3,prod4,prod5,prod6,prod7,prod8,prod9,prod10,prod11,prod12,prod13,prod14,prod15,prod16);
	}
}
