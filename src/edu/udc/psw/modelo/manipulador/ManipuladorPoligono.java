package edu.udc.psw.modelo.manipulador;

import java.awt.Graphics;

import edu.udc.psw.gui.views.ViewDesenho;
import edu.udc.psw.modelo.Poligono;
import edu.udc.psw.modelo.Ponto2D;

public class ManipuladorPoligono implements ManipuladorFormaGeometrica {

	private Poligono poligono;
	private int clique = 0;

	public ManipuladorPoligono(Poligono p) {
		poligono = p;
	}

	@Override
	public void click(int x, int y) {
		clique = ViewDesenho.getClique();
		ViewDesenho.setFlags("FALSE");

		if (clique >= 0 && clique < 9) {
			Ponto2D p = new Ponto2D(x, y);
			poligono.addPonto(p, clique);
			clique++;
			ViewDesenho.setClique(clique);
		} else if (clique == 9) {
			Ponto2D p = new Ponto2D(x, y);
			poligono.addPonto(p, clique);
			clique++;
			ViewDesenho.setClique(clique);
		} else if (clique == 10) {
			ViewDesenho.setFlags("TRUE");
		}

	}

	@Override
	public void press(int x, int y) {

	}

	@Override
	public void release(int x, int y) {

	}

	@Override
	public void drag(int x, int y) {

	}

	@Override
	public void paint(Graphics g) {
		Ponto2D pos1 = poligono.getPonto(0);
		int x1 = (int) pos1.getX();
		int y1 = (int) pos1.getY();
		
		//int x1 = (int) poligono.getPonto(0).getX();
		//int y1 = (int) poligono.getPonto(0).getY();

		if (clique > 0) {
			
			Ponto2D pol = poligono.getPonto(clique - 1);
			g.drawOval((int) pol.getX(), (int) pol.getY(), 2, 2);

			if (clique > 1) {
				for (int i = 1; i < clique; i++) {
					Ponto2D pol1 = poligono.getPonto(i - 1);
					Ponto2D pol2 = poligono.getPonto(i);

					g.drawLine((int) pol1.getX(), (int) pol1.getY(),
							(int) pol2.getX(), (int) pol2.getY());

					if (i == 9) {
						g.drawLine((int) pol2.getX(), (int) pol2.getY(), x1, y1);
						clique = 0;
						ViewDesenho.setClique(clique);
						break;
					}else if(x1 == pol2.getX() && y1 == pol2.getY()){
						g.drawLine((int) pol2.getX(), (int) pol2.getY(), x1, y1);
						clique = 0;
						ViewDesenho.setClique(clique);
						break;
					}else if(x1 == pol2.getX()-1 && y1 == pol2.getY()){
						g.drawLine((int) pol2.getX(), (int) pol2.getY(), x1, y1);
						clique = 0;
						ViewDesenho.setClique(clique);
						break;
					}else if(x1 == pol2.getX() && y1 == pol2.getY()-1){
						g.drawLine((int) pol2.getX(), (int) pol2.getY(), x1, y1);
						clique = 0;
						ViewDesenho.setClique(clique);
						break;
					}else if(x1 == pol2.getX()-1 && y1 == pol2.getY()-1){
						g.drawLine((int) pol2.getX(), (int) pol2.getY(), x1, y1);
						clique = 0;
						ViewDesenho.setClique(clique);
						break;
					}
				}
			}
		}
		
		if (clique == 0) {

			//int x = (int) poligono.getPonto(0).getX();
			//int y = (int) poligono.getPonto(0).getY();

			if (x1 > 0 & y1 > 0) {
				for (int i = 0; i < 9; i++) {
					Ponto2D pol1 = poligono.getPonto(i);
					Ponto2D pol2 = poligono.getPonto(i+1);

					g.drawLine((int) pol1.getX(), (int) pol1.getY(),
							(int) pol2.getX(), (int) pol2.getY());

					if (i == 8) {
						g.drawLine((int) pol2.getX(), (int) pol2.getY(), x1, y1);
						ViewDesenho.setFlags("FALSE");
						break;
					}else if(x1 == pol2.getX() && y1 == pol2.getY()){
						g.drawLine((int) pol2.getX(), (int) pol2.getY(), x1, y1);
						ViewDesenho.setFlags("FALSE");
						break;
					}else if(x1 == pol2.getX()-1 && y1 == pol2.getY()){
						g.drawLine((int) pol2.getX(), (int) pol2.getY(), x1, y1);
						ViewDesenho.setFlags("FALSE");
						break;
					}else if(x1 == pol2.getX() && y1 == pol2.getY()-1){
						g.drawLine((int) pol2.getX(), (int) pol2.getY(), x1, y1);
						ViewDesenho.setFlags("FALSE");
						break;
					}else if(x1 == pol2.getX()-1 && y1 == pol2.getY()-1){
						g.drawLine((int) pol2.getX(), (int) pol2.getY(), x1, y1);
						ViewDesenho.setFlags("FALSE");
						break;
					}
				}
			}
		}

	}
}
