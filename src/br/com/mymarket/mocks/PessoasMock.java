package br.com.mymarket.mocks;

import br.com.mymarket.model.Pessoa;

public class PessoasMock {
	public static Pessoa get(){
		Pessoa p = new Pessoa();
		p.setNome("Pessoa Teste");
		return p;
	}
}
