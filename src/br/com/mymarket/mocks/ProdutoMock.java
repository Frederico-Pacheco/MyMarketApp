package br.com.mymarket.mocks;

import java.util.Arrays;
import java.util.List;

import br.com.mymarket.enuns.StatusProduto;
import br.com.mymarket.model.Produto;

public class ProdutoMock {
	public static List<Produto> get(){
		Produto prod1 = new Produto("Produto p comprar ", StatusProduto.COMPRADO);
		Produto prod2 = new Produto("Produto comprado", StatusProduto.COMPRADO);
		Produto prod3 = new Produto("Produto p comprar", StatusProduto.COMPRADO);
		Produto prod4 = new Produto("Produto comprado", StatusProduto.COMPRADO);
		Produto prod5 = new Produto("Produto p comprar", StatusProduto.COMPRADO);
		Produto prod6 = new Produto("Produto p comprar", StatusProduto.COMPRADO);
		Produto prod7 = new Produto("Produto p comprar", StatusProduto.PENDENTE);
		Produto prod8 = new Produto("Produto p comprar", StatusProduto.PENDENTE);
		Produto prod9 = new Produto("Produto p comprar", StatusProduto.PENDENTE);
		Produto prod10 = new Produto("Produto comprado", StatusProduto.PENDENTE);
		Produto prod11 = new Produto("Produto p comprar", StatusProduto.PENDENTE);
		Produto prod12 = new Produto("Produto comprado", StatusProduto.PENDENTE);
		Produto prod13 = new Produto("Produto p comprar", StatusProduto.PENDENTE);
		Produto prod14 = new Produto("Produto comprado", StatusProduto.PENDENTE);
		Produto prod15 = new Produto("Produto comprado", StatusProduto.PENDENTE);
		Produto prod16 = new Produto("Produto comprado", StatusProduto.PENDENTE);
		return Arrays.asList(prod1,prod2,prod3,prod4,prod5,prod6,prod7,prod8,prod9,prod10,prod11,prod12,prod13,prod14,prod15,prod16);
	}
}
