package edu.udc.psw.gui.views;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import edu.udc.psw.desenhos.controle.Documento;
import edu.udc.psw.modelo.FormaGeometrica;

public class ViewTabela extends JPanel implements FormaGeometricaView {
	private static final long serialVersionUID = 1L;
	private JTable table;
	private FormaGeometricaTableModel tblModel;
	private Documento doc;

	public ViewTabela(Documento doc) {
		setLayout(new BorderLayout(5, 5));

		tblModel = new FormaGeometricaTableModel(doc);
		table = new JTable();
		table.setModel(tblModel);
		add(new JScrollPane(table), BorderLayout.CENTER);
	}

	@Override
	public void novaFormaGeometrica(FormaGeometrica forma) {
		doc.novaForma(forma);
	}

	@Override
	public void atualizar() {
		tblModel.atualizar();
	}
}

class FormaGeometricaTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private final String columnNames[] = new String[] { "#", "Tipo", "Pontos", "Centro", "Área", "Perimetro", "Tam. H.",
			"Tam. V." };
	private final Class<?> columnTypes[] = new Class[] { Integer.class, String.class, String.class, String.class,
			Float.class, Float.class, Integer.class, Integer.class };
	private Documento doc;

	public FormaGeometricaTableModel(Documento doc)
	{
		this.doc = doc;
	}
	
	public void atualizar() {
		// notifica a JTable de que os dados da tabela foram alterados
		fireTableDataChanged();
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return columnTypes[columnIndex];
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public int getRowCount() {
		return doc.getQtdFiguras();
	}

	@Override
	public int getColumnCount() {
		return 8;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		FormaGeometrica forma = doc.getFigura(rowIndex);
		switch (columnIndex) {
		case 0:
			return rowIndex;
		case 1:
			return forma.getClass().getSimpleName();
		case 2:
			return forma.toString();
		case 3:
			return forma.centro().toString();
		case 4:
			return forma.area();
		case 5:
			return forma.perimetro();
		case 6:
			return forma.base();
		case 7:
			return forma.altura();
		}
		return null;
	}
}
