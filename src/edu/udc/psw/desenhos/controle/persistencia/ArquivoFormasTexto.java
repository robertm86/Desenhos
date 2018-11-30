package edu.udc.psw.desenhos.controle.persistencia;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import edu.udc.psw.colecao.Iterador;
import edu.udc.psw.colecao.ListaEncadeada;
import edu.udc.psw.modelo.FabricaFormas;
import edu.udc.psw.modelo.FormaGeometrica;

//Classe ConcreteStrategy do padrão Strategy
public class ArquivoFormasTexto implements ArquivoFormaGeometrica {
	private File file;
	
	public ArquivoFormasTexto(File file)
	{
		this.file = file;
	}

	// Metodo AlgorithmInterface() da classe ConcreteStrategy do padrão Strategy
	@Override
	public ListaEncadeada<FormaGeometrica> lerFormas() {
		ListaEncadeada<FormaGeometrica> lista = new ListaEncadeada<FormaGeometrica>();
		Scanner input = null;

		try {
			input = new Scanner( file );
			while (input.hasNextLine()) {
				String str = input.nextLine();
				FormaGeometrica formaAux = FabricaFormas.fabricarFormaGeometrica(str);
				
				lista.inserirFim(formaAux);
			}
			
		}  catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return lista;
	}

	// Metodo AlgorithmInterface() da classe ConcreteStrategy do padrão Strategy
	@Override
	public void salvarFormas(ListaEncadeada<FormaGeometrica> lista) {
		FileWriter output; // objeto utilizado para gerar saída de texto no arquivo
		
		try {
			output = new FileWriter(file);
			
			FormaGeometrica formaAux;
			Iterador<FormaGeometrica> it = lista.getInicio();

			formaAux = it.getObjeto();
			while (formaAux != null) {
				output.append(formaAux.getClass().getSimpleName() + " " + formaAux.toString() + "\n");
				formaAux = it.proximo();
			}
			
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

}
