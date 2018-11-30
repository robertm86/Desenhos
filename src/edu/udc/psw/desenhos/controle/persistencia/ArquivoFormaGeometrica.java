package edu.udc.psw.desenhos.controle.persistencia;

import edu.udc.psw.colecao.ListaEncadeada;
import edu.udc.psw.modelo.FormaGeometrica;

// Classe Strategy do padr�o Strategy
public interface ArquivoFormaGeometrica {
	// Metodo AlgorithmInterface() da classe Strategy do padr�o Strategy
	public ListaEncadeada<FormaGeometrica> lerFormas();
	// Metodo AlgorithmInterface() da classe Strategy do padr�o Strategy
	public void salvarFormas(ListaEncadeada<FormaGeometrica> lista);
}
