package br.com.mymarket.mocks;

import java.util.Arrays;
import java.util.List;

import br.com.mymarket.model.Grupo;

public class ListaGrupoMock {
	public static List<Grupo> get(){
		Grupo grupo1 = new Grupo("Grupo 1");
		Grupo grupo2 = new Grupo("Grupo 2");
		Grupo grupo3 = new Grupo("Grupo 3");
		return Arrays.asList(grupo1,grupo2,grupo3);
	}
}
