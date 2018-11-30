package edu.udc.psw.gui.views;

import edu.udc.psw.modelo.FormaGeometrica;

//Classe Observer do padrão Observer
public interface FormaGeometricaView {
	public void novaFormaGeometrica(FormaGeometrica forma);
	// Metodo Update() do padrão Observer
	public void atualizar();
}
