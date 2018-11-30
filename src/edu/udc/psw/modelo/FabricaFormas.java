package edu.udc.psw.modelo;

import java.nio.ByteBuffer;

// Classe ConcreteCreator padrão Factory Method 
public class FabricaFormas {
	
	// Metodo Construct() da classe Director, padrão Builder
	public static FormaGeometrica fabricarFormaGeometrica(String forma) {
		int i = forma.indexOf(' ');
		String nome = forma.substring(0, i);
		FormaGeometrica formaGeometrica = null;
		
		if(nome.equals(Linha.class.getSimpleName()))
			formaGeometrica = fabricarLinha(forma.substring(i+1));
		else if(nome.equals(Retangulo.class.getSimpleName()))
			formaGeometrica = fabricarRetangulo(forma.substring(i+1));
		else if(nome.equals(Ponto2D.class.getSimpleName()))
			formaGeometrica = fabricarPonto2D(forma.substring(i+1));
		else if(nome.equals(Circulo.class.getSimpleName()))
			formaGeometrica = fabricarCirculo(forma.substring(i+1)); //new Circulo();
		else if(nome.equals(Triangulo.class.getSimpleName()))
			formaGeometrica = fabricarTriangulo(forma.substring(i+1)); //new Triangulo();
		
		
		return formaGeometrica;
	}
	
	// Metodo FactoryMethod() da classe ConcreteCreator do padrão Factory Method
	public static Linha fabricarLinha(String linha) {
		int i = linha.indexOf(')');
		Ponto2D p1 = fabricarPonto2D(linha.substring(0, i));
		Ponto2D p2 = fabricarPonto2D(linha.substring(i+1));
		Linha l = new Linha(p1, p2);
		return l;
	}

	// Metodo FactoryMethod() da classe ConcreteCreator do padrão Factory Method
	public static Retangulo fabricarRetangulo(String retangulo) {
		int i = retangulo.indexOf(')');
		Ponto2D p1 = fabricarPonto2D(retangulo.substring(0, i));
		Ponto2D p2 = fabricarPonto2D(retangulo.substring(i+1));
		Retangulo r = new Retangulo(p1, p2);
		return r;
	}

	// Metodo FactoryMethod() da classe ConcreteCreator do padrão Factory Method
	public static Ponto2D fabricarPonto2D(String ponto) {
		int i = ponto.indexOf(';');
		double x = Double.parseDouble(ponto.substring(1, i).replace(',', '.'));
		double y = Double.parseDouble(ponto.substring(i+1, ponto.length()-1).replace(',', '.'));
		Ponto2D p = new Ponto2D(x, y);
		return p;
	}

	public static Circulo fabricarCirculo(String circulo) {
		int i = circulo.indexOf(')');
		Ponto2D p1 = fabricarPonto2D(circulo.substring(0, i));
		Ponto2D p2 = fabricarPonto2D(circulo.substring(i+1));
		Circulo c = new Circulo(p1, p2);
		//System.out.println(r.getClass().getSimpleName() + " " + r.toString());
		return c;
	}
	
	public static Triangulo fabricarTriangulo(String triangulo) {
		String aux = triangulo;
		int i = 0;
		int d = 0;
		int e = 0;
				
		i = triangulo.indexOf(')');
		Ponto2D p1 = fabricarPonto2D(triangulo.substring(0, i));
		aux = triangulo.substring(i+1, triangulo.length());
		
		d = aux.indexOf(')');
		Ponto2D p2 = fabricarPonto2D(aux.substring(0, d));
		aux = aux.substring(d+1, aux.length());
		
		e = aux.indexOf(')');
		Ponto2D p3 = fabricarPonto2D(aux.substring(0, e));
		Triangulo t = new Triangulo(p1, p2, p3);
		return t;
	}
	
	// Metodo Construct() da classe Director, padrão Builder
	public static FormaGeometrica fabricarFormaGeometrica(byte bytes[]) {
		long forma = ByteBuffer.wrap(bytes, 0, 8).getLong();
		FormaGeometrica formaGeometrica = null;
		
		if(forma == Linha.serialVersionUID)
			formaGeometrica = new Linha(bytes);
		else if(forma == Retangulo.serialVersionUID)
			formaGeometrica = new Retangulo(bytes);
		else if(forma == Ponto2D.serialVersionUID)
			formaGeometrica = new Ponto2D(bytes);
		else if(forma == Circulo.serialVersionUID)
			formaGeometrica = new Circulo(bytes);
		else if(forma == Triangulo.serialVersionUID)
			formaGeometrica = new Triangulo(bytes);
		
		return formaGeometrica;
	}
	
	// Metodo FactoryMethod() da classe ConcreteCreator do padrão Factory Method
	public static Ponto2D fabricarPonto2D(byte bytes[]) {
		double x = ByteBuffer.wrap(bytes, 0, 8).getDouble();
		double y = ByteBuffer.wrap(bytes, 8, 8).getDouble();
		return new Ponto2D(x, y);
	}

	// Metodo FactoryMethod() da classe ConcreteCreator do padrão Factory Method
	public static Retangulo fabricarRetangulo(byte bytes[]) {
		double ax = ByteBuffer.wrap(bytes, 0, 8).getDouble();
		double ay = ByteBuffer.wrap(bytes, 8, 8).getDouble();
		double bx = ByteBuffer.wrap(bytes, 16, 8).getDouble();
		double by = ByteBuffer.wrap(bytes, 24, 8).getDouble();
		return new Retangulo(new Ponto2D(ax, ay), new Ponto2D(bx, by));
	}

	// Metodo FactoryMethod() da classe ConcreteCreator do padrão Factory Method
	public static Linha fabricarLinha(byte bytes[]) {
		double ax = ByteBuffer.wrap(bytes, 0, 8).getDouble();
		double ay = ByteBuffer.wrap(bytes, 8, 8).getDouble();
		double bx = ByteBuffer.wrap(bytes, 16, 8).getDouble();
		double by = ByteBuffer.wrap(bytes, 24, 8).getDouble();
		return new Linha(new Ponto2D(ax, ay), new Ponto2D(bx, by));
	}
	
	public static Circulo fabricarCirculo(byte bytes[]) {
		double ax = ByteBuffer.wrap(bytes, 0, 8).getDouble();
		double ay = ByteBuffer.wrap(bytes, 8, 8).getDouble();
		double bx = ByteBuffer.wrap(bytes, 16, 8).getDouble();
		double by = ByteBuffer.wrap(bytes, 24, 8).getDouble();
		return new Circulo(new Ponto2D(ax, ay), new Ponto2D(bx, by));
	}
	
	public static Triangulo fabricarTriangulo(byte bytes[]) {
		double ax = ByteBuffer.wrap(bytes, 0, 8).getDouble();
		double ay = ByteBuffer.wrap(bytes, 8, 8).getDouble();
		double bx = ByteBuffer.wrap(bytes, 16, 8).getDouble();
		double by = ByteBuffer.wrap(bytes, 24, 8).getDouble();
		double cx = ByteBuffer.wrap(bytes, 32, 8).getDouble();
		double cy = ByteBuffer.wrap(bytes, 40, 8).getDouble();
		return new Triangulo(new Ponto2D(ax, ay), new Ponto2D(bx, by),new Ponto2D(cx, cy));
	}

}
