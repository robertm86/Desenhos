package edu.udc.psw.gui.views;

import edu.udc.psw.modelo.FormaGeometrica;

//Classe Observer do padr�o Observer
public interface FormaGeometricaView {
	public void novaFormaGeometrica(FormaGeometrica forma);
	// Metodo Update() do padr�o Observer
	public void atualizar();
}
