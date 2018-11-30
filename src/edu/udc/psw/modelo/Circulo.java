package edu.udc.psw.modelo;

import java.nio.ByteBuffer;

import edu.udc.psw.modelo.manipulador.ManipuladorCirculo;
import edu.udc.psw.modelo.manipulador.ManipuladorFormaGeometrica;

public class Circulo implements FormaGeometrica {

	public static final long serialVersionUID = 3L;
	private Ponto2D a;
	private Ponto2D b;
	
	public Circulo(){
		a = new Ponto2D();
		b = new Ponto2D();
	}
	
	public Circulo(Ponto2D a, Ponto2D b){
		this.a = a.clone();
		this.b = b.clone();
	}
	
	public Circulo(float ax, float ay, float bx, float by){
		a = new Ponto2D(ax, ay);
		b = new Ponto2D(bx, by);
	}
	
	public Circulo(float ax, float ay, float raio){
		a = new Ponto2D(ax, ay);
		b = new Ponto2D((raio*2)+ax, (raio*2)+ax);
	}
	
	public Ponto2D getA() {
		return a.clone();
	}

	public Ponto2D getB() {
		return b.clone();
	}

	public void setA(Ponto2D a) {
		this.a = a.clone();
	}

	public void setB(Ponto2D b) {
		this.b = b.clone();
	}
	
	
	@Override
	public Ponto2D centro() {
		// TODO Auto-generated method stub
		return new Ponto2D((a.getX() + b.getX()) / 2, (a.getY() + b.getY()) / 2);
		//return null;
	}

	@Override
	public double area() {
		// TODO Auto-generated method stub
		return base() * altura();
		//return 0;
	}

	@Override
	public double perimetro() {
		// TODO Auto-generated method stub
		return 2 * base() + 2 * altura();
		//return 0;
	}

	@Override
	public double base() {
		// TODO Auto-generated method stub
		return Math.abs(a.getX() - b.getX());
		//return 0;
	}

	@Override
	public double altura() {
		// TODO Auto-generated method stub
		return Math.abs(a.getY() - b.getY());
		//return 0;
	}
	
	public double raio() {
		// TODO Auto-generated method stub
		return Math.abs((b.getX() - a.getX())/2);
		//return 0;
	}

	@Override
	public byte[] toArray() {
		byte[] bytes = new byte[40];
		ByteBuffer.wrap(bytes,0,8).putLong(serialVersionUID);
		ByteBuffer.wrap(bytes,8,8).putDouble(a.getX());
	    ByteBuffer.wrap(bytes,16,8).putDouble(a.getY());
		ByteBuffer.wrap(bytes,24,8).putDouble(b.getX());
	    ByteBuffer.wrap(bytes,32,8).putDouble(b.getY());
	    return bytes;
	}
	
	public Circulo(byte bytes[]){
		if(ByteBuffer.wrap(bytes, 0, 8).getLong() != serialVersionUID) {
			a = new Ponto2D();
			b = new Ponto2D();
			return;
		}
		double ax = ByteBuffer.wrap(bytes, 8, 8).getDouble();
		double ay = ByteBuffer.wrap(bytes, 16, 8).getDouble();
		double bx = ByteBuffer.wrap(bytes, 24, 8).getDouble();
		double by = ByteBuffer.wrap(bytes, 32, 8).getDouble();

		a = new Ponto2D(ax, ay);
		b = new Ponto2D(bx, by);
	}

	@Override
	public ManipuladorFormaGeometrica getManipulador() {
		// TODO Auto-generated method stub
		//return null;
		return new ManipuladorCirculo(this);
	}
	
	@Override
	public Circulo clone() {
		return new Circulo(a, b);
	}
	
	@Override
	public String toString(){
		return a.toString() + b.toString();
	}

}
