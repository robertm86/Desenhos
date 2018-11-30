package edu.udc.psw.gui.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import java.awt.Color;

public class DialogoUmPonto extends JDialog implements DocumentListener {
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textX;
	private JTextField textY;
	private JLabel lblValida;
	private int result = CANCEL;
	public final static int OK = 1;
	public final static int CANCEL = 0;
	private final static String X_VAZIO = "Entre com o valor da coordenada x.";
	private final static String Y_VAZIO = "Entre com o valor da coordenada y.";
	private final static String INTEIRO = "Apenas números inteiros são permitidos.";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DialogoUmPonto dialog = new DialogoUmPonto("Ler ponto");
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DialogoUmPonto(String titulo) {
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

		JLabel lblCoordenadaX = new JLabel("Coordenada x");
		layout.putConstraint(SpringLayout.WEST, lblCoordenadaX, 11, SpringLayout.WEST, contentPanel);
		contentPanel.add(lblCoordenadaX);

		textX = new JTextField();
		layout.putConstraint(SpringLayout.NORTH, textX, 0, SpringLayout.NORTH, contentPanel);
		layout.putConstraint(SpringLayout.NORTH, lblCoordenadaX, 3, SpringLayout.NORTH, textX);
		layout.putConstraint(SpringLayout.WEST, textX, 100, SpringLayout.WEST, contentPanel);
		layout.putConstraint(SpringLayout.EAST, textX, 200, SpringLayout.WEST, contentPanel);
		textX.setEnabled(true);
		textX.setEditable(true);
		textX.setText("");
		contentPanel.add(textX);
		textX.setColumns(10);
		textX.getDocument().addDocumentListener(this);

		JLabel lblCoordenadaY = new JLabel("Coordenada y");
		layout.putConstraint(SpringLayout.NORTH, lblCoordenadaY, 6, SpringLayout.SOUTH, lblCoordenadaX);
		layout.putConstraint(SpringLayout.WEST, lblCoordenadaY, 11, SpringLayout.WEST, contentPanel);
		contentPanel.add(lblCoordenadaY);

		textY = new JTextField();
		layout.putConstraint(SpringLayout.NORTH, textY, 6, SpringLayout.SOUTH, lblCoordenadaX);
		layout.putConstraint(SpringLayout.WEST, textY, 100, SpringLayout.WEST, contentPanel);
		layout.putConstraint(SpringLayout.EAST, textY, 200, SpringLayout.WEST, contentPanel);
		textY.setEnabled(true);
		textY.setEditable(true);
		textY.setText("");
		textY.getDocument().addDocumentListener(this);

		contentPanel.add(textY);
		textY.setColumns(10);

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
		if (textX.getText() == null || textX.getText().length() == 0) {
			lblValida.setText(X_VAZIO);
			return false;
		}
		if (textY.getText() == null || textY.getText().length() == 0) {
			lblValida.setText(Y_VAZIO);
			return false;
		}
		if (!textX.getText().matches("[0-9]+")) {
			lblValida.setText(INTEIRO);
			return false;
		}
		if (!textY.getText().matches("[0-9]+")) {
			lblValida.setText(INTEIRO);
			return false;
		}
		lblValida.setText(" ");
		return true;
	}

	public int getResult() {
		return result;
	}

	public int getX() {
		if (result == OK)
			return Integer.parseInt(textX.getText());
		else
			return 0;
	}

	public int getY() {
		if (result == OK)
			return Integer.parseInt(textY.getText());
		else
			return 0;
	}
}
