package br.com.mymarket.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class Compras implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1390116599283676156L;
	
	private Pessoa pessoa;
	private List<Produto> produtos;
	private BigDecimal valor;
	
	public Pessoa getPessoa() {
		return pessoa;
	}
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	public List<Produto> getProdutos() {
		return produtos;
	}
	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
}
