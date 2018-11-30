package edu.udc.psw.modelo.manipulador;

import java.awt.Graphics;

import edu.udc.psw.modelo.Circulo;
import edu.udc.psw.modelo.Ponto2D;

public class ManipuladorCirculo implements ManipuladorFormaGeometrica {

	private Circulo circulo;
	
	public ManipuladorCirculo(Circulo c) {
		circulo = c;
	}
	
	@Override
	public void click(int x, int y) {
		
	}

	@Override
	public void press(int x, int y) {
		
		Ponto2D p = new Ponto2D(x, y);
		circulo.setA(p);
		p = new Ponto2D(x, y);
		circulo.setB(p);
		
	}

	@Override
	public void release(int x, int y) {
		Ponto2D p = new Ponto2D(x, y);
		circulo.setB(p);
	}

	@Override
	public void drag(int x, int y) {
		Ponto2D p = new Ponto2D(x, y);
		circulo.setB(p);
	}

	@Override
	public void paint(Graphics g) {
		
		int xa = (int) circulo.getA().getX();
		int ya = (int) circulo.getA().getY();
		int xb = (int) circulo.getB().getX();
		int yb = (int) circulo.getB().getY();
		//int base = (int) circulo.base();
		//int altura = (int) circulo.altura();
		//int area = (int) circulo.area();
		//int perimetro = (int) circulo.perimetro();
		int raio = (int) circulo.raio();
		
		//g.drawOval(xa < xb ? xa : xb, ya < yb ? ya : yb, 
		//		(int) circulo.base(), (int) circulo.altura() );
		g.drawOval(xa < xb ? xa : xb, ya < yb ? ya : yb, 
				(int) raio*2, (int) raio*2 );
		
		//System.out.println("xa:"+xa);
		//System.out.println("xb:"+xb);
		//System.out.println("ya:"+ya);
		//System.out.println("yb:"+yb);
		//System.out.println("base:"+base);
		//System.out.println("altura:"+altura);
		//System.out.println("area:"+area);
		//System.out.println("perimetro:"+perimetro);
		//System.out.println("raio:"+raio);
		
	}

}
