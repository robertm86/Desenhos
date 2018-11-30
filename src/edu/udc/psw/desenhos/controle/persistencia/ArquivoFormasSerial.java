package edu.udc.psw.desenhos.controle.persistencia;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import edu.udc.psw.colecao.Iterador;
import edu.udc.psw.colecao.ListaEncadeada;
import edu.udc.psw.modelo.FormaGeometrica;

//Classe ConcreteStrategy do padrão Strategy
public class ArquivoFormasSerial implements ArquivoFormaGeometrica {
	private File file;
	
	public ArquivoFormasSerial(File file)
	{
		this.file = file;
	}

	// Metodo AlgorithmInterface() da classe ConcreteStrategy do padrão Strategy
	@Override
	public ListaEncadeada<FormaGeometrica> lerFormas() {	
		ListaEncadeada<FormaGeometrica> lista = new ListaEncadeada<FormaGeometrica>();

		FormaGeometrica formaAux = null;
		ObjectInputStream ois = null;
		
		try {
			ois = new ObjectInputStream(new FileInputStream(file));
			while (true) {
				formaAux = (FormaGeometrica) ois.readObject();
				
				lista.inserirFim(formaAux);
			}
		} catch (EOFException endOfFileException) {
			try {
				ois.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // fim do arquivo foi alcançado
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lista;
	}

	// Metodo AlgorithmInterface() da classe ConcreteStrategy do padrão Strategy
	@Override
	public void salvarFormas(ListaEncadeada<FormaGeometrica> lista) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));

			FormaGeometrica formaAux;
			Iterador<FormaGeometrica> it = lista.getInicio();

			formaAux = it.getObjeto();
			while (formaAux != null) {
				oos.writeObject(formaAux);
				formaAux = it.proximo();
			}

			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
