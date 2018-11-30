package edu.udc.psw.colecao;

//Classe Iterator do padrão Iterator
public interface Iterador<T> {
	
	T getObjeto();
	T proximo();
	T anterior();

}
