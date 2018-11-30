package edu.udc.psw.modelo;

import edu.udc.psw.modelo.manipulador.ManipuladorFormaGeometrica;
import edu.udc.psw.modelo.manipulador.ManipuladorPoligono;

public class Poligono implements FormaGeometrica {
	// tarefa -> não foi feito

	private Ponto2D pontos[] = new Ponto2D[10];
	private int lados;

	public Poligono(int lados) {
		this.lados = lados;
		for (int i = 0; i < lados; i++) {
			pontos[i] = new Ponto2D(i, i);
		}
	}

	public Poligono(Ponto2D[] ponto) {
		lados = ponto.length;
		for (int i = 0; i < lados; i++) {
			pontos[i] = new Ponto2D(i, i);
		}
	}

	@Override
	public Poligono clone() {
		return new Poligono(lados);
	}

	public Ponto2D centro() {
		double x = 0, y = 0;

		for (int i = 0; i < lados; i++) {
			x += pontos[i].getX();
			y += pontos[i].getY();
		}

		x /= lados;
		y /= lados;

		return new Ponto2D(x, y);
	}

	@Override
	public double area() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double perimetro() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double base() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double altura() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public byte[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ManipuladorFormaGeometrica getManipulador() {
		return new ManipuladorPoligono(this);
	}

	public int getTamanho() {
		return pontos.length;
	}

	public Ponto2D[] getPonto() {
		return pontos;
	}

	public void addPonto(Ponto2D ponto, int pos) {
		pontos[pos] = ponto;
		int x, y;

		// for(int i = 0; i < lados; i++){
		// x = (int) pontos[i].getX();
		// y = (int) pontos[i].getY();
		// System.out.println("pos:" + i + " X:" + x + " Y:" + y);
		// }
	}

	public Ponto2D getPonto(int pos) {
		return pontos[pos];
	}

	@Override
	public String toString() {
		String retorno = "";

		for (int i = 0; i < lados; i++) {
			retorno += pontos[i].toString();
		}

		return retorno;
	}
}
