package edu.udc.psw.gui.views;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import edu.udc.psw.colecao.Iterador;
import edu.udc.psw.desenhos.controle.Documento;
import edu.udc.psw.modelo.FormaGeometrica;

//Classe ConcreteObserver do padrão Observer
public class ViewDesenho  extends JPanel implements FormaGeometricaView {
	private static final long serialVersionUID = 1L;
	protected Documento doc;
	
	private static int clique = 0;
	private static String flags = "FALSE";

	public ViewDesenho(Documento doc) {
		this.doc = doc;
		setBackground(new Color(255,255,255));
	}
	
	public void novaFormaGeometrica(FormaGeometrica forma) {
		doc.novaForma(forma);
	}

	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		
		FormaGeometrica formaAux;
		Iterador<FormaGeometrica> it = doc.getIteradorFormas();

		formaAux = it.getObjeto();
		while (formaAux != null) {
			formaAux.getManipulador().paint(g);
			formaAux = it.proximo();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		FormaGeometrica formaAux;
		Iterador<FormaGeometrica> it = doc.getIteradorFormas();

		formaAux = it.getObjeto();
		while (formaAux != null) {
			formaAux.getManipulador().paint(g);
			formaAux = it.proximo();
		}
	}

	// Metodo Update() do padrão Observer
	@Override
	public void atualizar() {
		getParent().revalidate();
		repaint();
	}
	
	public static int getClique() {
		return clique;
	}

	public static void setClique(int click) {
		clique = click;
	}

	public static String getFlags() {
		return flags;
	}

	public static void setFlags(String flag) {
		flags = flag;
	}


}
