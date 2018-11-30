package edu.udc.psw.modelo;

import java.nio.ByteBuffer;

import edu.udc.psw.modelo.manipulador.ManipuladorFormaGeometrica;
import edu.udc.psw.modelo.manipulador.ManipuladorTriangulo;

public class Triangulo implements FormaGeometrica {
	public static final long serialVersionUID = 3L;
	private Ponto2D p1;
	private Ponto2D p2;
	private Ponto2D p3;
	
	public Triangulo(){
		p1 = new Ponto2D();
		p2 = new Ponto2D();
		p3 = new Ponto2D();
	}
	
	public Triangulo(Ponto2D a, Ponto2D b, Ponto2D c){
		this.p1 = a.clone();
		this.p2 = b.clone();
		this.p3 = c.clone();
	}
	
	public Triangulo(float ax, float ay, float bx, float by,float cx, float cy){
		p1 = new Ponto2D(ax, ay);
		p2 = new Ponto2D(bx, by);
		p3 = new Ponto2D(cx, cy);
	}
	
	public Triangulo(byte bytes[]){
		if(ByteBuffer.wrap(bytes, 0, 8).getLong() != serialVersionUID) {
			p1 = new Ponto2D();
			p2 = new Ponto2D();
			p3 = new Ponto2D();
			return;
		}
		double ax = ByteBuffer.wrap(bytes, 8, 8).getDouble();
		double ay = ByteBuffer.wrap(bytes, 16, 8).getDouble();
		double bx = ByteBuffer.wrap(bytes, 24, 8).getDouble();
		double by = ByteBuffer.wrap(bytes, 32, 8).getDouble();
		double cx = ByteBuffer.wrap(bytes, 40, 8).getDouble();
		double cy = ByteBuffer.wrap(bytes, 48, 8).getDouble();

		p1 = new Ponto2D(ax, ay);
		p2 = new Ponto2D(bx, by);
		p3 = new Ponto2D(cx, cy);
	}

	@Override
	public byte[] toArray() {
		byte[] bytes = new byte[40];
		ByteBuffer.wrap(bytes,0,8).putLong(serialVersionUID);
		ByteBuffer.wrap(bytes,8,8).putDouble(p1.getX());
	    ByteBuffer.wrap(bytes,16,8).putDouble(p1.getY());
		ByteBuffer.wrap(bytes,24,8).putDouble(p2.getX());
	    ByteBuffer.wrap(bytes,32,8).putDouble(p2.getY());
	    ByteBuffer.wrap(bytes,40,8).putDouble(p3.getX());
	    ByteBuffer.wrap(bytes,48,8).putDouble(p3.getY());
	    return bytes;
	}

	public Ponto2D getP1() {
		return p1.clone();
	}

	public Ponto2D getP2() {
		return p2.clone();
	}
	
	public Ponto2D getP3() {
		return p3.clone();
	}

	public void setP1(Ponto2D a) {
		this.p1 = a.clone();
	}

	public void setP2(Ponto2D b) {
		this.p2 = b.clone();
	}
	
	public void setP3(Ponto2D b) {
		this.p3 = b.clone();
	}
	
	public Ponto2D centro(){
		return new Ponto2D((p1.getX() + p2.getX()) / 2, (p1.getY() + p2.getY()) / 2);
	}
	
	public double base(){
		return Math.abs(p1.getX() - p2.getX() - p3.getX());
	}
	
	public double altura(){
		return Math.abs(p1.getY() - p2.getY() - p3.getY());
	}
	
	public double area(){
		return base() * altura();
	}

	@Override
	public double perimetro() {
		return 2 * base() + 2 * altura();
	}

	@Override
	public ManipuladorFormaGeometrica getManipulador() {
		return new ManipuladorTriangulo(this);
	}

	@Override
	public Triangulo clone() {
		return new Triangulo(p1, p2, p3);
	}
	
	@Override
	public String toString(){
		return p1.toString() + p2.toString() + p3.toString();
	}

}
