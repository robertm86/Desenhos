package edu.udc.psw.gui.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JComboBox;
import javax.swing.JList;

import edu.udc.psw.desenhos.DB.DAO.DesenhosDAO;

public class DialogoMostrarDesenho extends JDialog implements DocumentListener {
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private int result = CANCEL;
	public final static int OK = 1;
	public final static int CANCEL = 0;
	private JList<String> list;

	public static void main(String[] args) {
		try {
			DialogoMostrarDesenho dialog = new DialogoMostrarDesenho("Mostrar Nome do desenho");
			dialog.setVisible(true);
			if(dialog.getResult() == OK) {
				JOptionPane.showMessageDialog(null, dialog.getNome());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DialogoMostrarDesenho(String titulo) {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setModal(true);
		setTitle(titulo);
		setBounds(100, 100, 249, 300);

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		SpringLayout layout = new SpringLayout();
		contentPanel.setLayout(layout);
		
		DesenhosDAO desenhosDAO = new DesenhosDAO();
		DefaultListModel<String> model = new DefaultListModel<String>();

		try {
			desenhosDAO.getDesenho();
			ResultSet resultSet = desenhosDAO.BuscarTudo();

			while (resultSet.next()) {
				model.addElement(resultSet.getString(1));
			} // fim do while
		} catch (IllegalStateException | SQLException e) {
			e.printStackTrace();
		}
		
		
		list = new JList<String>(model);
		//JList list = new JList();
		layout.putConstraint(SpringLayout.NORTH, list, 10, SpringLayout.NORTH, contentPanel);
		layout.putConstraint(SpringLayout.WEST, list, 10, SpringLayout.WEST, contentPanel);
		layout.putConstraint(SpringLayout.SOUTH, list, 194, SpringLayout.NORTH, contentPanel);
		layout.putConstraint(SpringLayout.EAST, list, 213, SpringLayout.WEST, contentPanel);
		contentPanel.add(list);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!valida())
					return;

				result = OK;
				setVisible(false);
			}
		});

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);

		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				result = CANCEL;
				setVisible(false);
			}

		});
		
		valida();
	}

	public void changedUpdate(DocumentEvent e) {
		valida();
	}

	public void removeUpdate(DocumentEvent e) {
		valida();
	}

	public void insertUpdate(DocumentEvent e) {
		valida();
	}

	private boolean valida() {
		if(list.getSelectedValue() == null)
			return false;
	
		return true;
	}

	public int getResult() {
		return result;
	}

	public String getNome() {
		if (result == OK)
			return (String) list.getSelectedValue();
		else
			return "";
	}
}
