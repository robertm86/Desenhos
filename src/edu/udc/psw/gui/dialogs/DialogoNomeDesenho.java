package edu.udc.psw.gui.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

public class DialogoNomeDesenho extends JDialog implements DocumentListener {
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField nome;
	private JLabel lblValida;
	private int result = CANCEL;
	private int formato = 1;
	public final static int OK = 1;
	public final static int CANCEL = 0;
	private final static String VAZIO = "Entre com o nome do desenho.";
	private JComboBox comboBox;
	private String [] StrFormato = {"DATABASE", "SERIAL", "TEXT", "BINARY"};

	public static void main(String[] args) {
		try {
			DialogoNomeDesenho dialog = new DialogoNomeDesenho("Nome do desenho");
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
	public DialogoNomeDesenho(String titulo) {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setModal(true);
		setTitle(titulo);
		setBounds(100, 100, 340, 150);

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		SpringLayout layout = new SpringLayout();
		contentPanel.setLayout(layout);

		JLabel lblCoordenadaX = new JLabel("Nome");
		layout.putConstraint(SpringLayout.WEST, lblCoordenadaX, 11, SpringLayout.WEST, contentPanel);
		contentPanel.add(lblCoordenadaX);

		nome = new JTextField();
		layout.putConstraint(SpringLayout.NORTH, nome, 0, SpringLayout.NORTH, contentPanel);
		layout.putConstraint(SpringLayout.NORTH, lblCoordenadaX, 3, SpringLayout.NORTH, nome);
		layout.putConstraint(SpringLayout.WEST, nome, 100, SpringLayout.WEST, contentPanel);
		layout.putConstraint(SpringLayout.EAST, nome, 200, SpringLayout.WEST, contentPanel);
		nome.setEnabled(true);
		nome.setEditable(true);
		nome.setText("");
		contentPanel.add(nome);
		nome.setColumns(20);
		
		comboBox = new JComboBox(StrFormato);
		layout.putConstraint(SpringLayout.WEST, comboBox, 102, SpringLayout.WEST, contentPanel);
		layout.putConstraint(SpringLayout.SOUTH, comboBox, 0, SpringLayout.SOUTH, contentPanel);
		//comboBox.setSelectedIndex(4);
		contentPanel.add(comboBox);
		nome.getDocument().addDocumentListener(this);

		

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

		lblValida = new JLabel("  ");
		lblValida.setForeground(Color.BLUE);
		getContentPane().add(lblValida, BorderLayout.NORTH);
		
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
		
		if (nome.getText() == null || nome.getText().length() == 0) {
			lblValida.setText(VAZIO);
			return false;
		}
	
		lblValida.setText(" ");
		return true;
	}

	public int getResult() {
		return result;
	}

	public String getNome() {
		if (result == OK)
			return nome.getText();
		else
			return "";
	}
	
	public int getFormato(){
		return comboBox.getSelectedIndex();
	}
}
