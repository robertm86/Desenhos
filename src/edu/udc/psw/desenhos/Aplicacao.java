package edu.udc.psw.desenhos;

import edu.udc.psw.desenhos.controle.Documento;
import edu.udc.psw.gui.JanelaDesenho;

public class Aplicacao {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		Documento doc = new Documento();
		JanelaDesenho frame = new JanelaDesenho(doc);
		frame.setVisible(true);
	}
}
