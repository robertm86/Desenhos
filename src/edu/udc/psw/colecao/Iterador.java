package edu.udc.psw.colecao;

//Classe Iterator do padr�o Iterator
public interface Iterador<T> {
	
	T getObjeto();
	T proximo();
	T anterior();

}
