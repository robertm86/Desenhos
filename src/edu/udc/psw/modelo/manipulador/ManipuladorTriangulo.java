package edu.udc.psw.modelo.manipulador;

import java.awt.Graphics;

import edu.udc.psw.gui.views.ViewDesenho;
import edu.udc.psw.modelo.Ponto2D;
import edu.udc.psw.modelo.Triangulo;

public class ManipuladorTriangulo implements ManipuladorFormaGeometrica {

	private Triangulo triangulo;
	private int clique = 0;

	public ManipuladorTriangulo(Triangulo t) {
		triangulo = t;
	}

	@Override
	public void click(int x, int y) {

		clique = ViewDesenho.getClique();
		ViewDesenho.setFlags("FALSE");

		if (clique == 0) {
			Ponto2D p1 = new Ponto2D(x, y);
			triangulo.setP1(p1);
			clique++;
			ViewDesenho.setClique(clique);
		} else if (clique == 1) {
			Ponto2D p2 = new Ponto2D(x, y);
			triangulo.setP2(p2);
			clique++;
			ViewDesenho.setClique(clique);
		} else if (clique == 2) {
			Ponto2D p3 = new Ponto2D(x, y);
			triangulo.setP3(p3);
			clique++;
			ViewDesenho.setClique(clique);
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

		if (clique == 1) {

			g.drawOval((int) triangulo.getP1().getX(), (int) triangulo.getP1()
					.getY(), 2, 2);

		}

		if (clique == 2) {

			g.drawOval((int) triangulo.getP2().getX(), (int) triangulo.getP1()
					.getY(), 2, 2);

			g.drawLine((int) triangulo.getP1().getX(), (int) triangulo.getP1()
					.getY(), (int) triangulo.getP2().getX(), (int) triangulo
					.getP2().getY());

		}

		if (clique == 3) {

			g.drawOval((int) triangulo.getP3().getX(), (int) triangulo.getP3()
					.getY(), 2, 2);

			g.drawLine((int) triangulo.getP1().getX(), (int) triangulo.getP1()
					.getY(), (int) triangulo.getP2().getX(), (int) triangulo
					.getP2().getY());

			g.drawLine((int) triangulo.getP2().getX(), (int) triangulo.getP2()
					.getY(), (int) triangulo.getP3().getX(), (int) triangulo
					.getP3().getY());

			g.drawLine((int) triangulo.getP3().getX(), (int) triangulo.getP3()
					.getY(), (int) triangulo.getP1().getX(), (int) triangulo
					.getP1().getY());

			clique = 0;
			ViewDesenho.setClique(clique);

		}

		if (clique == 0) {

			int x1 = (int) triangulo.getP1().getX();
			int y1 = (int) triangulo.getP1().getY();

			int x2 = (int) triangulo.getP2().getX();
			int y2 = (int) triangulo.getP2().getY();

			int x3 = (int) triangulo.getP3().getX();
			int y3 = (int) triangulo.getP3().getY();

			if (x1 > 0 && y1 > 0 && x2 > 0 && y2 > 0 && x3 > 0 && y3 > 0) {

				g.drawLine((int) triangulo.getP1().getX(), (int) triangulo
						.getP1().getY(), (int) triangulo.getP2().getX(),
						(int) triangulo.getP2().getY());

				g.drawLine((int) triangulo.getP2().getX(), (int) triangulo
						.getP2().getY(), (int) triangulo.getP3().getX(),
						(int) triangulo.getP3().getY());

				g.drawLine((int) triangulo.getP3().getX(), (int) triangulo
						.getP3().getY(), (int) triangulo.getP1().getX(),
						(int) triangulo.getP1().getY());

				ViewDesenho.setFlags("FALSE");
			}
		}

	}
}
