package edu.udc.psw.gui.views;

import java.awt.Color;

import javax.swing.JTextArea;

import edu.udc.psw.colecao.Iterador;
import edu.udc.psw.desenhos.controle.Documento;
import edu.udc.psw.modelo.FormaGeometrica;

public class ViewTexto extends JTextArea implements FormaGeometricaView {
	private static final long serialVersionUID = 3L;
	protected Documento doc;

	public ViewTexto(Documento doc) {
		super(8,15);
		this.doc = doc;
		setBackground(new Color(255,255,255));
	}

	@Override
	public void novaFormaGeometrica(FormaGeometrica forma) {
		doc.novaForma(forma);
	}

	@Override
	public void atualizar() {
		StringBuffer buf = new StringBuffer();
		Iterador<FormaGeometrica> i = doc.getIteradorFormas();
		FormaGeometrica forma;
		while((forma = i.proximo()) != null) {
			buf.append(forma);
			buf.append("\n");
		}
		setText(buf.toString());
	}		

}
