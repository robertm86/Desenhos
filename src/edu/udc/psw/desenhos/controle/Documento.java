package edu.udc.psw.desenhos.controle;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import edu.udc.psw.colecao.Iterador;
import edu.udc.psw.colecao.ListaEncadeada;
import edu.udc.psw.desenhos.DB.DAO.CirculoDAO;
import edu.udc.psw.desenhos.DB.DAO.DesenhosDAO;
import edu.udc.psw.desenhos.DB.DAO.FormaDAO;
import edu.udc.psw.desenhos.DB.DAO.LinhaDAO;
import edu.udc.psw.desenhos.DB.DAO.PontoDAO;
import edu.udc.psw.desenhos.DB.DAO.RetanguloDAO;
import edu.udc.psw.desenhos.DB.DAO.TrianguloDAO;
import edu.udc.psw.desenhos.DB.data.Desenhos;
import edu.udc.psw.desenhos.controle.persistencia.ArquivoFormaGeometrica;
import edu.udc.psw.desenhos.controle.persistencia.ArquivoFormasBinario;
import edu.udc.psw.desenhos.controle.persistencia.ArquivoFormasSerial;
import edu.udc.psw.desenhos.controle.persistencia.ArquivoFormasTexto;
import edu.udc.psw.gui.views.FormaGeometricaView;
import edu.udc.psw.modelo.Circulo;
import edu.udc.psw.modelo.FabricaFormas;
import edu.udc.psw.modelo.FormaGeometrica;
import edu.udc.psw.modelo.Linha;
import edu.udc.psw.modelo.Ponto2D;
import edu.udc.psw.modelo.Retangulo;
import edu.udc.psw.modelo.Triangulo;

// Classe Context do padrão Strategy
// Classe ConcreteSubject do padrão Observer
public class Documento {
	private ListaEncadeada<FormaGeometrica> listaFormas;
	// Lista de observadores - padrão Observer
	private ListaEncadeada<FormaGeometricaView> listaViews;

	public Documento() {
		super();
		listaFormas = new ListaEncadeada<FormaGeometrica>();
		listaViews = new ListaEncadeada<FormaGeometricaView>();
	}

	// Metodo Attach(Observer) do padrão Observer
	public void adicionaView(FormaGeometricaView view) {
		listaViews.inserirFim(view);
		atualizaViews();
	}

	// Metodo Detach(Observer) do padrão Observer
	public void removeView(FormaGeometricaView view) {
		listaViews.remover(view);
		atualizaViews();
	}

	// Metodo Notify() do padrão Observer
	public void atualizaViews() {
		Iterador<FormaGeometricaView> i = listaViews.getInicio();
		FormaGeometricaView view;
		while ((view = (FormaGeometricaView) i.proximo()) != null) {
			// Invoca o metodo Update do objeto Observer
			view.atualizar();
		}
	}

	public void novaForma(FormaGeometrica forma) {
		listaFormas.inserirFim(forma);
		// Uso do metodo Notify() do padrão Observer
		atualizaViews();
	}

	public void novoPonto(int x, int y) {
		Ponto2D p = new Ponto2D(x, y);
		listaFormas.inserirFim(p);
		// Uso do metodo Notify() do padrão Observer
		atualizaViews();
	}

	public void novaLinha(int xi, int yi, int xf, int yf) {
		Linha l = new Linha(new Ponto2D(xi, yi), new Ponto2D(xf, yf));
		listaFormas.inserirFim(l);
		// Uso do metodo Notify() do padrão Observer
		atualizaViews();
	}

	public void novoRetangulo(int xi, int yi, int xf, int yf) {
		Retangulo r = new Retangulo(new Ponto2D(xi, yi), new Ponto2D(xf, yf));
		listaFormas.inserirFim(r);
		// Uso do metodo Notify() do padrão Observer
		atualizaViews();
	}

	public void novoCirculo(int xi, int yi, int xf, int yf) {
		Circulo c = new Circulo(new Ponto2D(xi, yi), new Ponto2D(xf, yf));
		listaFormas.inserirFim(c);
		// Uso do metodo Notify() do padrão Observer
		atualizaViews();
	}

	public void novoTriangulo(int xi, int yi, int xf, int yf, int xg, int yg) {
		Triangulo t = new Triangulo(new Ponto2D(xi, yi), new Ponto2D(xf, yf),
				new Ponto2D(xg, yg));
		listaFormas.inserirFim(t);
		// Uso do metodo Notify() do padrão Observer
		atualizaViews();
	}

	public int getQtdFiguras() {
		return listaFormas.getTamanho();
	}

	public FormaGeometrica getFigura(int pos) {
		return (FormaGeometrica) listaFormas.pesquisar(pos);
	}

	public Iterador<FormaGeometrica> getIteradorFormas() {
		return listaFormas.getInicio();
	}

	// Metodo ContextInterface da classe Context no padrão Strategy
	public void salvarFormas(File file) {
		ArquivoFormaGeometrica arq = null;

		String name = file.getName();
		String ext = name.substring(name.lastIndexOf('.') + 1);

		// Determina qual algoritmo será utilizado, no padrão Strategy
		if (ext.compareTo("ser") == 0)
			arq = new ArquivoFormasSerial(file);
		if (ext.compareTo("txt") == 0)
			arq = new ArquivoFormasTexto(file);
		if (ext.compareTo("bin") == 0)
			arq = new ArquivoFormasBinario(file);
		// Uso do metodo AlgorithmInterface() da classe Strategy
		arq.salvarFormas(listaFormas);

	}

	// Metodo ContextInterface da classe Context no padrão Strategy
	public void lerFormas(File file) {
		ArquivoFormaGeometrica arq = null;

		String name = file.getName();
		String ext = name.substring(name.lastIndexOf('.') + 1);

		// Determina qual algoritmo será utilizado, no padrão Strategy
		if (ext.compareTo("ser") == 0)
			arq = new ArquivoFormasSerial(file);
		if (ext.compareTo("txt") == 0)
			arq = new ArquivoFormasTexto(file);
		if (ext.compareTo("bin") == 0)
			arq = new ArquivoFormasBinario(file);
		// Uso do metodo AlgorithmInterface() da classe Strategy
		listaFormas = arq.lerFormas();

		// Uso do metodo Notify() do padrão Observer
		atualizaViews();
	}

	public void salvarBD(String nomeDesenho) {
		DesenhosDAO desenhosDAO = new DesenhosDAO();
		long time = System.currentTimeMillis();
		Desenhos desenho = new Desenhos(0, nomeDesenho, new Timestamp(time),
				new Timestamp(time), 0);

		try {
			desenhosDAO.insert(desenho);
		} catch (IllegalStateException | SQLException e) {
			e.printStackTrace();
		}

		PontoDAO pontoDAO = new PontoDAO((int) desenho.getId());
		LinhaDAO linhaDAO = new LinhaDAO((int) desenho.getId());
		RetanguloDAO retanguloDAO = new RetanguloDAO((int) desenho.getId());
		CirculoDAO circuloDAO = new CirculoDAO((int) desenho.getId());
		TrianguloDAO trianguloDAO = new TrianguloDAO((int) desenho.getId());

		Iterador<FormaGeometrica> i = listaFormas.getInicio();
		FormaGeometrica forma;
		while ((forma = (FormaGeometrica) i.proximo()) != null) {
			if (forma.getClass().equals(Ponto2D.class)) {
				try {
					pontoDAO.insert((Ponto2D) forma);
				} catch (IllegalStateException | SQLException e) {
					e.printStackTrace();
				}
			}
			if (forma.getClass().equals(Linha.class)) {
				try {
					linhaDAO.insert((Linha) forma);
				} catch (IllegalStateException | SQLException e) {
					e.printStackTrace();
				}
			}
			if (forma.getClass().equals(Retangulo.class)) {
				try {
					retanguloDAO.insert((Retangulo) forma);
				} catch (IllegalStateException | SQLException e) {
					e.printStackTrace();
				}
			}
			if (forma.getClass().equals(Circulo.class)) {
				try {
					circuloDAO.insert((Circulo) forma);
				} catch (IllegalStateException | SQLException e) {
					e.printStackTrace();
				}
			}

			if (forma.getClass().equals(Triangulo.class)) {
				try {
					trianguloDAO.insert((Triangulo) forma);
				} catch (IllegalStateException | SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void salvarBDtxt(String nomeDesenho) {
		DesenhosDAO desenhosDAO = new DesenhosDAO();
		long time = System.currentTimeMillis();
		Desenhos desenho = new Desenhos(0, nomeDesenho, new Timestamp(time),
				new Timestamp(time), 2);

		try {
			desenhosDAO.insert(desenho);
		} catch (IllegalStateException | SQLException e) {
			e.printStackTrace();
		}

		FormaDAO formaDAO = new FormaDAO((int) desenho.getId());

		Iterador<FormaGeometrica> i = listaFormas.getInicio();
		FormaGeometrica forma;

		String output = "";
		int tipoForma = 0;

		while ((forma = (FormaGeometrica) i.proximo()) != null) {
			tipoForma = 0;

			if (forma.getClass().equals(Ponto2D.class))
				tipoForma = 1;
			if (forma.getClass().equals(Linha.class))
				tipoForma = 2;
			if (forma.getClass().equals(Retangulo.class))
				tipoForma = 3;
			if (forma.getClass().equals(Circulo.class))
				tipoForma = 4;
			if (forma.getClass().equals(Triangulo.class))
				tipoForma = 5;

			try {
				output = forma.getClass().getSimpleName() + " "
						+ forma.toString();
				formaDAO.insertTxt(output, tipoForma);

			} catch (IllegalStateException | SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void salvarBDser(String nomeDesenho) {
		DesenhosDAO desenhosDAO = new DesenhosDAO();
		long time = System.currentTimeMillis();
		Desenhos desenho = new Desenhos(0, nomeDesenho, new Timestamp(time),
				new Timestamp(time), 1);

		try {
			desenhosDAO.insert(desenho);
		} catch (IllegalStateException | SQLException e) {
			e.printStackTrace();
		}

		FormaDAO formaDAO = new FormaDAO((int) desenho.getId());

		Iterador<FormaGeometrica> i = listaFormas.getInicio();
		FormaGeometrica forma = null;

		/*
		 * ObjectOutputStream oos = null; try { oos = new ObjectOutputStream(new
		 * FileOutputStream("arq.ser")); } catch (IOException e1) {
		 * 
		 * e1.printStackTrace(); }
		 */
		int tipoForma = 0;

		while ((forma = (FormaGeometrica) i.proximo()) != null) {

			tipoForma = 0;

			if (forma.getClass().equals(Ponto2D.class))
				tipoForma = 1;
			if (forma.getClass().equals(Linha.class))
				tipoForma = 2;
			if (forma.getClass().equals(Retangulo.class))
				tipoForma = 3;
			if (forma.getClass().equals(Circulo.class))
				tipoForma = 4;
			if (forma.getClass().equals(Triangulo.class))
				tipoForma = 5;

			/*
			 * try {
			 * 
			 * //oos.writeObject(forma.getClass().getSimpleName() + " " // +
			 * forma.toString()); //formaDAO.insertSer(oos, tipoForma);
			 * System.out.println("forma:"+forma.getClass().getSimpleName() +
			 * " " + forma.toString()); String teste =
			 * forma.getClass().getSimpleName() + " " + forma.toString();
			 * 
			 * oos.writeObject(forma); formaDAO.insertSer(oos, tipoForma);
			 * 
			 * } catch (IllegalStateException e) { e.printStackTrace(); } catch
			 * (IOException e) { e.printStackTrace(); } catch (SQLException e) {
			 * e.printStackTrace(); }
			 */
			byte[] byteArrayObject = null;
			try {

				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(bos);
				oos.writeObject(forma.getClass().getSimpleName() + " "
						+ forma.toString());

				oos.close();
				bos.close();
				byteArrayObject = bos.toByteArray();
				formaDAO.insertSer(oos, tipoForma);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		/*
		 * try { oos.close(); } catch (IOException e) { e.printStackTrace(); }
		 */

	}

	public void salvarBDbin(String nomeDesenho) {
		DesenhosDAO desenhosDAO = new DesenhosDAO();
		long time = System.currentTimeMillis();
		Desenhos desenho = new Desenhos(0, nomeDesenho, new Timestamp(time),
				new Timestamp(time), 3);

		try {
			desenhosDAO.insert(desenho);
		} catch (IllegalStateException | SQLException e) {
			e.printStackTrace();
		}

		FormaDAO formaDAO = new FormaDAO((int) desenho.getId());

		Iterador<FormaGeometrica> i = listaFormas.getInicio();
		FormaGeometrica forma = null;

		ObjectOutputStream oos = null;

		byte[] bytes = new byte[4];

		byte array[];

		try {
			oos = new ObjectOutputStream(new FileOutputStream("arq.bin"));
		} catch (IOException e1) {

			e1.printStackTrace();
		}

		int tipoForma = 0;

		while ((forma = (FormaGeometrica) i.proximo()) != null) {

			tipoForma = 0;

			if (forma.getClass().equals(Ponto2D.class))
				tipoForma = 1;
			if (forma.getClass().equals(Linha.class))
				tipoForma = 2;
			if (forma.getClass().equals(Retangulo.class))
				tipoForma = 3;
			if (forma.getClass().equals(Circulo.class))
				tipoForma = 4;
			if (forma.getClass().equals(Triangulo.class))
				tipoForma = 5;

			try {

				array = forma.toArray();
				ByteBuffer.wrap(bytes).putInt(array.length);
				oos.write(bytes);
				oos.write(array);

				formaDAO.insertBin(oos, tipoForma);

			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		try {
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void LerBD(String nomeDesenho) {

		DesenhosDAO desenhosDAO = new DesenhosDAO();
		Desenhos desenho = null;

		try {
			desenhosDAO.getDesenho();
			ResultSet resultSet = desenhosDAO.BuscarID(nomeDesenho);

			resultSet.next();
			desenho = new Desenhos(resultSet.getInt(4), resultSet.getString(1),
					resultSet.getTimestamp(2), resultSet.getTimestamp(3),
					resultSet.getInt(5));

		} catch (IllegalStateException | SQLException e) {
			e.printStackTrace();
		}
		if (desenho.getFormato() == 0) { // Leitura Banco de Dados

			PontoDAO pontoDAO = new PontoDAO((int) desenho.getId());
			LinhaDAO linhaDAO = new LinhaDAO((int) desenho.getId());
			RetanguloDAO retanguloDAO = new RetanguloDAO((int) desenho.getId());
			CirculoDAO circuloDAO = new CirculoDAO((int) desenho.getId());
			TrianguloDAO trianguloDAO = new TrianguloDAO((int) desenho.getId());

			ListaEncadeada<FormaGeometrica> lista = new ListaEncadeada<FormaGeometrica>();

			try {
				ResultSet resultPonto = pontoDAO
						.BuscarID((int) desenho.getId());
				ResultSet resultLinha = linhaDAO
						.BuscarID((int) desenho.getId());
				ResultSet resultRetangulo = retanguloDAO.BuscarID((int) desenho
						.getId());
				ResultSet resultCirculo = circuloDAO.BuscarID((int) desenho
						.getId());
				ResultSet resultTriangulo = trianguloDAO.BuscarID((int) desenho
						.getId());

				while (resultPonto.next()) {
					novoPonto((int) resultPonto.getDouble(2),
							(int) resultPonto.getDouble(3));
				}

				while (resultLinha.next()) {
					novaLinha((int) resultLinha.getDouble(2),
							(int) resultLinha.getDouble(3),
							(int) resultLinha.getDouble(4),
							(int) resultLinha.getDouble(5));
				}

				while (resultRetangulo.next()) {
					novoRetangulo((int) resultRetangulo.getDouble(2),
							(int) resultRetangulo.getDouble(3),
							(int) resultRetangulo.getDouble(4),
							(int) resultRetangulo.getDouble(5));
				}

				while (resultCirculo.next()) {
					novoCirculo((int) resultCirculo.getDouble(2),
							(int) resultCirculo.getDouble(3),
							(int) (resultCirculo.getDouble(4) * 2)
									+ (int) resultCirculo.getDouble(2),
							(int) (resultCirculo.getDouble(4) * 2)
									+ (int) resultCirculo.getDouble(3));
				}

				while (resultTriangulo.next()) {
					novoTriangulo((int) resultTriangulo.getDouble(2),
							(int) resultTriangulo.getDouble(3),
							(int) resultTriangulo.getDouble(4),
							(int) resultTriangulo.getDouble(5),
							(int) resultTriangulo.getDouble(6),
							(int) resultTriangulo.getDouble(7));
				}

			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	


		if (desenho.getFormato() == 1) { // Leitura Banco de Dados Texto

			FormaDAO formaDAO = new FormaDAO((int) desenho.getId());

			FormaGeometrica forma;

			try {
				ResultSet resultForma = formaDAO
						.BuscarID((int) desenho.getId());

				while (resultForma.next()) {

					ByteArrayInputStream bais;
					ObjectInputStream ins;
					try {

						bais = new ByteArrayInputStream(resultForma.getBytes(4));
						ins = new ObjectInputStream(bais);
						forma = (FormaGeometrica) ins.readObject();
						
						
						String str = (String) ins.readObject();
						forma = FabricaFormas.fabricarFormaGeometrica(str);
						

						listaFormas.inserirFim(forma);
						atualizaViews();

						ins.close();

					} catch (Exception e) {
						e.printStackTrace();
					}

				}

			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		if (desenho.getFormato() == 2) { //Leitura Banco de Dados Texto
			
			FormaDAO formaDAO = new FormaDAO((int) desenho.getId());

			ListaEncadeada<FormaGeometrica> lista = new ListaEncadeada<FormaGeometrica>();
			
			FormaGeometrica forma = null;
			
			int nforma = 0;

			try {
				ResultSet resultForma = formaDAO.BuscarID((int) desenho.getId());
				
				while (resultForma.next()) {
					
					String str = resultForma.getString(3);
					forma = FabricaFormas.fabricarFormaGeometrica(str);
					listaFormas.inserirFim(forma);

					atualizaViews();
				}
				

			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		

		if (desenho.getFormato() == 3) { // Leitura Banco de Dados Binario

			FormaDAO formaDAO = new FormaDAO((int) desenho.getId());
			FormaGeometrica forma = null;

			try {
				ResultSet resultForma = formaDAO
						.BuscarID((int) desenho.getId());
				
				while (resultForma.next()) {
					
					String str = (String) resultForma.getObject(4);
					forma = FabricaFormas.fabricarFormaGeometrica(str);
					listaFormas.inserirFim(forma);

					atualizaViews();
				}

			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		
	}

}
