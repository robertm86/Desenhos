package edu.udc.psw.desenhos.controle.persistencia;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import edu.udc.psw.colecao.Iterador;
import edu.udc.psw.colecao.ListaEncadeada;
import edu.udc.psw.modelo.FabricaFormas;
import edu.udc.psw.modelo.FormaGeometrica;

// Classe ConcreteStrategy do padrão Strategy
public class ArquivoFormasBinario implements ArquivoFormaGeometrica {
	private File file;
	
	public ArquivoFormasBinario(File file)
	{
		this.file = file;
	}

	// Metodo AlgorithmInterface() da classe ConcreteStrategy do padrão Strategy
	@Override
	public ListaEncadeada<FormaGeometrica> lerFormas() {
		ListaEncadeada<FormaGeometrica> lista = new ListaEncadeada<FormaGeometrica>();
		FileInputStream input = null;

		try {
			input = new FileInputStream(file);
			FormaGeometrica formaAux;
			byte tam[] = new byte[4];
			byte array[];
			while (input.read(tam) != -1) {
				array = new byte[ByteBuffer.wrap(tam).getInt()];
				input.read(array);
				formaAux = (FormaGeometrica) FabricaFormas.fabricarFormaGeometrica(array);
				lista.inserirFim(formaAux);
			}
			input.close();
		} catch (EOFException endOfFileException) { // fim do arquivo foi alcançado
			try {
				input.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lista;
	}

	// Metodo AlgorithmInterface() da classe ConcreteStrategy do padrão Strategy
	@Override
	public void salvarFormas(ListaEncadeada<FormaGeometrica> lista) {
		FileOutputStream output = null;

		try {
			output = new FileOutputStream(file);

			FormaGeometrica formaAux;
			Iterador<FormaGeometrica> it = lista.getInicio();

			byte[] bytes = new byte[4];
			
			byte array[];
			formaAux = it.getObjeto();
			while (formaAux != null) {
				array = formaAux.toArray();
				ByteBuffer.wrap(bytes).putInt(array.length);
				output.write(bytes); 
				output.write(array);
				formaAux = it.proximo();
			}

			output.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
