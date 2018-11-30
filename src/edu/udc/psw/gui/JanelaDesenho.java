package edu.udc.psw.gui;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import edu.udc.psw.desenhos.controle.Documento;
import edu.udc.psw.gui.dialogs.DialogoDoisPontos;
import edu.udc.psw.gui.dialogs.DialogoNomeDesenho;
import edu.udc.psw.gui.dialogs.DialogoMostrarDesenho;
import edu.udc.psw.gui.dialogs.DialogoTresPontos;
import edu.udc.psw.gui.dialogs.DialogoUmPonto;
import edu.udc.psw.gui.views.ViewDesenho;
import edu.udc.psw.gui.views.ViewDesenhoMouse;
import edu.udc.psw.gui.views.ViewTabela;
import edu.udc.psw.gui.views.ViewTexto;
import edu.udc.psw.modelo.Circulo;
import edu.udc.psw.modelo.Linha;
import edu.udc.psw.modelo.Poligono;
import edu.udc.psw.modelo.Retangulo;
import edu.udc.psw.modelo.Ponto2D;
import edu.udc.psw.modelo.Triangulo;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

public class JanelaDesenho extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ViewTexto viewTexto;
	private ViewDesenho viewDesenho;
	private ViewTabela viewTabela;
	private Documento doc;
	private JCheckBoxMenuItem habilitaMouse;
	private JCheckBoxMenuItem habilitaTexto;
	private JCheckBoxMenuItem habilitaTabela;
	private JScrollPane scrollDesenho;
	private JScrollPane scrollTabela;
	private JScrollPane scrollTexto;


	/**
	 * Create the frame.
	 */
	public JanelaDesenho(Documento doc) {
		setTitle("Janela de desenho");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		this.doc = doc;
		
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		setContentPane(contentPane);
		
		// Inicializa painel de desenho
		viewDesenho = new ViewDesenhoMouse(doc);
		viewDesenho.setBorder(new EmptyBorder(5, 5, 5, 5));
		viewDesenho.setLayout(null);
		scrollDesenho = new JScrollPane();
		scrollDesenho.setViewportView(viewDesenho);
		contentPane.add(scrollDesenho, BorderLayout.CENTER);
		// Adiciona objeto Observador na lista de observadores do objeto Subject do padrão Observer
		doc.adicionaView(viewDesenho);	
		
		// Inicializa painel de texto
		scrollTexto = new JScrollPane();
		contentPane.add(scrollTexto, BorderLayout.WEST);
		viewTexto = new ViewTexto(doc);
		scrollTexto.setViewportView(viewTexto);
		viewTexto.setEditable(false);
		// Adiciona objeto Observador na lista de observadores do objeto Subject do padrão Observer
		doc.adicionaView(viewTexto);	
		
		// Inicializa painel de tabela
		scrollTabela = new JScrollPane();
		viewTabela = new ViewTabela(doc);
		scrollTabela.setViewportView(viewTabela);
		
		setBounds(50, 50, 850, 650); // Posição e tamanho da janela
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnArquivo = new JMenu("Arquivo");
		menuBar.add(mnArquivo);
		
		JMenuItem mntmSalvarSerial = new JMenuItem("Salvar");
		mntmSalvarSerial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				File f = escolherArquivo(true);
				if(f == null)
					return;
				JanelaDesenho.this.doc.salvarFormas(f);
			}
		});
		mnArquivo.add(mntmSalvarSerial);
		
		JMenuItem mntmLerSerial = new JMenuItem("Ler");
		mntmLerSerial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File f = escolherArquivo(false);
				if(f == null)
					return;
				JanelaDesenho.this.doc.lerFormas(f);
			}
		});
		mnArquivo.add(mntmLerSerial);
				
		JMenuItem mntmSalvarBD = new JMenuItem("Salvar BD");
		mntmSalvarBD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DialogoNomeDesenho dialog = new DialogoNomeDesenho("Nome do desenho");
				dialog.setVisible(true);
				if(dialog.getResult() == DialogoNomeDesenho.OK) {
					//System.out.println(dialog.getFormato());
					if (dialog.getFormato() == 0)
						doc.salvarBD(dialog.getNome());
					else if(dialog.getFormato() == 1)
						doc.salvarBDser(dialog.getNome());
					else if(dialog.getFormato() == 2)
						doc.salvarBDtxt(dialog.getNome());
					else if(dialog.getFormato() == 3)
						doc.salvarBDbin(dialog.getNome());
				}
			}
		});
		mnArquivo.add(mntmSalvarBD);
		
		JMenuItem mntmLerBD = new JMenuItem("Ler BD");
		mntmLerBD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				DialogoMostrarDesenho dialogo = new DialogoMostrarDesenho("Mostrar Nome do desenho");
				dialogo.setVisible(true);
				
				if(dialogo.getResult() == DialogoMostrarDesenho.OK) {
					doc.LerBD(dialogo.getNome());
				}
			}
		});
		
		mnArquivo.add(mntmLerBD);
				
		JMenu mnFiguras = new JMenu("Figuras");
		menuBar.add(mnFiguras);
		
		JMenuItem mntmPonto = new JMenuItem("Ponto");
		mntmPonto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Ponto2D ponto;
				if(habilitaMouse.isSelected())
					ponto = new Ponto2D();
				else
					ponto = cmdNovoPonto();
				
				viewDesenho.novaFormaGeometrica(ponto);
			}
		});
		mnFiguras.add(mntmPonto);
		
		JMenuItem mntmLinha = new JMenuItem("Linha");
		mntmLinha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Linha linha;
				if(habilitaMouse.isSelected())
					linha = new Linha();
				else
					linha = cmdNovaLinha();
				
				viewDesenho.novaFormaGeometrica(linha);
			}
		});
		mnFiguras.add(mntmLinha);
		
		JMenuItem mntmRetangulo = new JMenuItem("Retangulo");
		mntmRetangulo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Retangulo retangulo;
				if(habilitaMouse.isSelected())
					retangulo = new Retangulo();
				else
					retangulo = cmdNovoRetangulo();
				viewDesenho.novaFormaGeometrica(retangulo);
			}
		});
		mnFiguras.add(mntmRetangulo);
		
		JMenuItem mntmCirculo = new JMenuItem("Circulo");
		mntmCirculo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Circulo circulo;
				if(habilitaMouse.isSelected())
					circulo = new Circulo();
				else
					circulo = cmdNovoCirculo();
				viewDesenho.novaFormaGeometrica(circulo);
			}
		});
		mnFiguras.add(mntmCirculo);
		
		JMenuItem mntmTriangulo = new JMenuItem("Triangulo");
		mntmTriangulo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Triangulo triangulo;
				if(habilitaMouse.isSelected())
					triangulo = new Triangulo();
				else
					triangulo = cmdNovoTriangulo();
				viewDesenho.novaFormaGeometrica(triangulo);
			}
		});
		mnFiguras.add(mntmTriangulo);
		
		JMenuItem mntmPoligono = new JMenuItem("Poligono");
		mntmPoligono.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Poligono poligono = null;
				if(habilitaMouse.isSelected())
					poligono = new Poligono(10);
				//else
					//poligono = cmdNovoPoligono();
				viewDesenho.novaFormaGeometrica(poligono);
			}
		});
		mnFiguras.add(mntmPoligono);
		
		JMenu mnView = new JMenu("View");
		menuBar.add(mnView);
		
		habilitaMouse = new JCheckBoxMenuItem("Habilita mouse");
		habilitaMouse.setMnemonic('H');
		habilitaMouse.setSelected(true);
		mnView.add(habilitaMouse);
		habilitaMouse.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				// Se o mouse está habilitado, utiliza ViewDesenhoMouse
				if(e.getStateChange() == ItemEvent.SELECTED)
				{
					// Remove objeto Observador da lista de observadores do objeto Subject do padrão Observer
					doc.removeView(viewDesenho);
					scrollDesenho.remove(viewDesenho);
					viewDesenho = new ViewDesenhoMouse(doc);
					viewDesenho.setBorder(new EmptyBorder(5, 5, 5, 5));
					scrollDesenho.setViewportView(viewDesenho);
					// Adiciona objeto Observador na lista de observadores do objeto Subject do padrão Observer
					doc.adicionaView(viewDesenho);
				}
				// Caso contrário, utiliza ViewDesenho
				else
				{
					// Remove objeto Observador da lista de observadores do objeto Subject do padrão Observer
					doc.removeView(viewDesenho);
					scrollDesenho.remove(viewDesenho);
					viewDesenho = new ViewDesenho(doc);
					viewDesenho.setBorder(new EmptyBorder(5, 5, 5, 5));
					scrollDesenho.setViewportView(viewDesenho);
					// Adiciona objeto Observador na lista de observadores do objeto Subject do padrão Observer
					doc.adicionaView(viewDesenho);
				}				
				contentPane.revalidate();
			}			
		});

		habilitaTexto = new JCheckBoxMenuItem("Painel de texto");
		habilitaTexto.setMnemonic('x');
		habilitaTexto.setSelected(true);
		mnView.add(habilitaTexto);
		habilitaTexto.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				// Se o mouse está habilitado, utiliza ViewDesenhoMouse
				if(e.getStateChange() == ItemEvent.SELECTED)
				{
					contentPane.add(scrollTexto, BorderLayout.WEST);
					// Adiciona objeto Observador na lista de observadores do objeto Subject do padrão Observer
					doc.adicionaView(viewTexto);
				}
				// Caso contrário, utiliza ViewDesenho
				else
				{
					// Remove objeto Observador da lista de observadores do objeto Subject do padrão Observer
					doc.removeView(viewTexto);
					contentPane.remove(scrollTexto);					
				}	
				contentPane.revalidate();
			}			
		});

		habilitaTabela = new JCheckBoxMenuItem("Painel de tabela");
		habilitaTabela.setMnemonic('t');
		habilitaTabela.setSelected(false);
		mnView.add(habilitaTabela);
		habilitaTabela.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				// Se o Tabela está habilitado, utiliza ViewTabela
				if(e.getStateChange() == ItemEvent.SELECTED)
				{
					// Remove objeto Observador da lista de observadores do objeto Subject do padrão Observer
					doc.removeView(viewDesenho);
					contentPane.remove(scrollDesenho);			
					contentPane.add(scrollTabela, BorderLayout.CENTER);
					// Adiciona objeto Observador na lista de observadores do objeto Subject do padrão Observer
					doc.adicionaView(viewTabela);
					contentPane.revalidate();
					habilitaMouse.setEnabled(false);
				}
				// Caso contrário, utiliza ViewDesenho
				else
				{
					// Remove objeto Observador da lista de observadores do objeto Subject do padrão Observer
					doc.removeView(viewTabela);
					contentPane.remove(scrollTabela);
					contentPane.add(scrollDesenho, BorderLayout.CENTER);
					// Adiciona objeto Observador na lista de observadores do objeto Subject do padrão Observer
					doc.adicionaView(viewDesenho);
					contentPane.revalidate();
					habilitaMouse.setEnabled(true);
				}				
				contentPane.revalidate();
			}			
		});

	}
	
	private File escolherArquivo(boolean gravar) {
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File(System.getProperty("user.home")));

		FileNameExtensionFilter textFilterS = new FileNameExtensionFilter("Serial file", "ser");
		fc.addChoosableFileFilter(textFilterS);
		FileNameExtensionFilter textFilterT = new FileNameExtensionFilter("Text file", "txt");
		fc.addChoosableFileFilter(textFilterT);
		FileNameExtensionFilter textFilterB = new FileNameExtensionFilter("Binary file", "bin");
		fc.addChoosableFileFilter(textFilterB);
		
		fc.setFileFilter(textFilterT);

		int result = gravar ? fc.showSaveDialog(null) : fc.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			return fc.getSelectedFile();
		}
		return null;
	}
	
	private Ponto2D cmdNovoPonto()
	{
		DialogoUmPonto dlg = new DialogoUmPonto("Ler um ponto");
		dlg.setVisible(true);
		if(dlg.getResult() == DialogoUmPonto.OK)
		{
			return new Ponto2D(dlg.getX(), dlg.getY());
		}
		return null;
	}
	
	private Linha cmdNovaLinha()
	{
		DialogoDoisPontos dlg = new DialogoDoisPontos("Ler uma Linha");
		dlg.setVisible(true);
		if(dlg.getResult() == DialogoDoisPontos.OK)
		{
			return new Linha(dlg.getX1(), dlg.getY1(), dlg.getX2(), dlg.getY2());
		}
		return null;
	}
	
	private Retangulo cmdNovoRetangulo()
	{
		DialogoDoisPontos dlg = new DialogoDoisPontos("Ler um Retangulo");
		dlg.setVisible(true);
		if(dlg.getResult() == DialogoDoisPontos.OK)
		{
			return new Retangulo(dlg.getX1(), dlg.getY1(), dlg.getX2(), dlg.getY2());
		}
		return null;
	}
	
	private Circulo cmdNovoCirculo()
	{
		DialogoDoisPontos dlg = new DialogoDoisPontos("Ler um Circulo");
		dlg.setVisible(true);
		if(dlg.getResult() == DialogoDoisPontos.OK)
		{
			return new Circulo(dlg.getX1(), dlg.getY1(), dlg.getX2(), dlg.getY2());
		}
		return null;
	}
	
	private Triangulo cmdNovoTriangulo()
	{
		DialogoTresPontos dlg = new DialogoTresPontos("Ler um Triangulo");
		dlg.setVisible(true);
		if(dlg.getResult() == DialogoTresPontos.OK)
		{
			return new Triangulo(dlg.getX1(), dlg.getY1(), dlg.getX2(), dlg.getY2(), dlg.getX3(), dlg.getY3());
		}
		return null;
	}
	/*
	private Poligono cmdNovoPoligono()
	{
		DialogoMultiPontos dlg = new DialogoMultiPontos("Ler um Poligono");
		dlg.setVisible(true);
		if(dlg.getResult() == DialogoMultiPontos.OK)
		{
			return new Triangulo(dlg.getX1(), dlg.getY1(), dlg.getX2(), dlg.getY2(), dlg.getX3(), dlg.getY3());
		}
		return null;
	}
	*/
}
