package edu.udc.psw.modelo;

import java.nio.ByteBuffer;

import edu.udc.psw.modelo.manipulador.ManipuladorFormaGeometrica;
import edu.udc.psw.modelo.manipulador.ManipuladorLinha;

//Classe ConcreteCriator, no padrão Factory Method
public class Linha implements FormaGeometrica {
	public static final long serialVersionUID = 2L;
	private Ponto2D a;
	private Ponto2D b;
	
	public Linha(){
		a = new Ponto2D();
		b = new Ponto2D();
	}

	public Linha(Ponto2D a, Ponto2D b){
		this.a = a.clone();
		this.b = b.clone();
	}
	
	public Linha(float ax, float ay, float bx, float by){
		a = new Ponto2D(ax, ay);
		b = new Ponto2D(bx, by);
	}
	
	public Linha(byte bytes[]){
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
	public byte[] toArray() {
		byte[] bytes = new byte[40];
		ByteBuffer.wrap(bytes,0,8).putLong(serialVersionUID);
		ByteBuffer.wrap(bytes,8,8).putDouble(a.getX());
	    ByteBuffer.wrap(bytes,16,8).putDouble(a.getY());
		ByteBuffer.wrap(bytes,24,8).putDouble(b.getX());
	    ByteBuffer.wrap(bytes,32,8).putDouble(b.getY());
	    return bytes;
	}
	

	@Override
	public Linha clone() {
		return new Linha(a, b);
	}

	public double comprimento(){
		return a.distancia(b);
	}
	
	@Override
	public Ponto2D centro(){
		Ponto2D m = new Ponto2D((a.getX() + b.getX()) / 2, (a.getY() + b.getY()) / 2);
		return m;
	}
	
	@Override
	public double base(){
		if(a.getX() < b.getX())
			return b.getX() - a.getX();
		return a.getX() - b.getX();
	}
	
	@Override
	public double altura(){
		if(a.getY() < b.getY())
			return b.getY() - a.getY();
		return a.getY() - b.getY();
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
	public String toString(){
		return a.toString() + b.toString();
	}

	@Override
	public double area() {
		return 0;
	}

	@Override
	public double perimetro() {
		return comprimento();
	}

	// Factory Method - padrão de projeto
	@Override
	public ManipuladorFormaGeometrica getManipulador() {
		return new ManipuladorLinha(this);
	}

}
