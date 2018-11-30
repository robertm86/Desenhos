package edu.udc.psw.gui.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class DialogoDoisPontos extends JDialog {
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textX1;
	private JTextField textY1;
	private JTextField textX2;
	private JTextField textY2;
	private int result = CANCEL;
	public final static int OK = 1;
	public final static int CANCEL = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DialogoDoisPontos dialog = new DialogoDoisPontos("Ler pontos");
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DialogoDoisPontos(String titulo) {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setModal(true);
		setTitle(titulo);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 250, 180);

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		SpringLayout layout = new SpringLayout();
		contentPanel.setLayout(layout);

			JLabel lblX1 = new JLabel("Coordenada x1:");
			layout.putConstraint(SpringLayout.WEST, lblX1, 11, SpringLayout.WEST, contentPanel);
			contentPanel.add(lblX1);
			
			JLabel lblY1 = new JLabel("Coordenada y1:");
			layout.putConstraint(SpringLayout.NORTH, lblY1, 6, SpringLayout.SOUTH, lblX1);
			layout.putConstraint(SpringLayout.WEST, lblY1, 11, SpringLayout.WEST, contentPanel);
			contentPanel.add(lblY1);
			
			JLabel lblX2 = new JLabel("Coordenada x2:");
			layout.putConstraint(SpringLayout.NORTH, lblX2, 6, SpringLayout.SOUTH, lblY1);
			layout.putConstraint(SpringLayout.WEST, lblX2, 11, SpringLayout.WEST, contentPanel);
			contentPanel.add(lblX2);

			JLabel lblY2 = new JLabel("Coordenada y2:");
			layout.putConstraint(SpringLayout.NORTH, lblY2, 6, SpringLayout.SOUTH, lblX2);
			layout.putConstraint(SpringLayout.WEST, lblY2, 11, SpringLayout.WEST, contentPanel);
			contentPanel.add(lblY2);

			
			textX1 = new JTextField();
			layout.putConstraint(SpringLayout.NORTH, textX1, 0, SpringLayout.NORTH, contentPanel);
			layout.putConstraint(SpringLayout.NORTH, lblX1, 3, SpringLayout.NORTH, textX1);
			layout.putConstraint(SpringLayout.WEST, textX1, 100, SpringLayout.WEST, contentPanel);
			layout.putConstraint(SpringLayout.EAST, textX1, 200, SpringLayout.WEST, contentPanel);
			textX1.setEnabled(true);
			textX1.setEditable(true);
			textX1.setText("");
			contentPanel.add(textX1);
			textX1.setColumns(10);


			textY1 = new JTextField();
			layout.putConstraint(SpringLayout.NORTH, textY1, 6, SpringLayout.SOUTH, textX1);
			layout.putConstraint(SpringLayout.NORTH, lblY1, 3, SpringLayout.NORTH, textY1);
			layout.putConstraint(SpringLayout.WEST, textY1, 100, SpringLayout.WEST, contentPanel);
			layout.putConstraint(SpringLayout.EAST, textY1, 200, SpringLayout.WEST, contentPanel);
			textY1.setEnabled(true);
			textY1.setEditable(true);
			textY1.setText("");
			contentPanel.add(textY1);
			textY1.setColumns(10);


			textX2 = new JTextField();
			layout.putConstraint(SpringLayout.NORTH, textX2, 6, SpringLayout.SOUTH, textY1);
			layout.putConstraint(SpringLayout.NORTH, lblX2, 3, SpringLayout.NORTH, textX2);
			layout.putConstraint(SpringLayout.WEST, textX2, 100, SpringLayout.WEST, contentPanel);
			layout.putConstraint(SpringLayout.EAST, textX2, 200, SpringLayout.WEST, contentPanel);
			textX2.setEnabled(true);
			textX2.setEditable(true);
			textX2.setText("");
			contentPanel.add(textX2);
			textX2.setColumns(10);

			textY2 = new JTextField();
			layout.putConstraint(SpringLayout.NORTH, textY2, 6, SpringLayout.SOUTH, textX2);
			layout.putConstraint(SpringLayout.NORTH, lblY2, 3, SpringLayout.NORTH, textY2);
			layout.putConstraint(SpringLayout.WEST, textY2, 100, SpringLayout.WEST, contentPanel);
			layout.putConstraint(SpringLayout.EAST, textY2, 200, SpringLayout.WEST, contentPanel);
			textY2.setEnabled(true);
			textY2.setEditable(true);
			textY2.setText("");
			contentPanel.add(textY2);
			textY2.setColumns(10);

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
						if(!valida())
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
	}
	private boolean valida()
	{
		if(textX1.getText() == null || textX1.getText().length() == 0)
			return false;
		if(textY1.getText() == null || textY1.getText().length() == 0)
			return false;
		if(textX2.getText() == null || textX2.getText().length() == 0)
			return false;
		if(textY2.getText() == null || textY2.getText().length() == 0)
			return false;
		if(!textX1.getText().matches("[0-9]+"))
			return false;
		if(!textY1.getText().matches("[0-9]+"))
			return false;
		if(!textX2.getText().matches("[0-9]+"))
			return false;
		if(!textY2.getText().matches("[0-9]+"))
			return false;
		return true;
	}
	
	public int getResult()
	{
		return result;
	}
	
	public int getX1()
	{
		if(result == OK)
			return Integer.parseInt(textX1.getText());
		else
			return 0;
	}
	public int getY1()
	{
		if(result == OK)
			return Integer.parseInt(textY1.getText());
		else
			return 0;
	}
	
	public int getX2()
	{
		if(result == OK)
			return Integer.parseInt(textX2.getText());
		else
			return 0;
	}
	public int getY2()
	{
		if(result == OK)
			return Integer.parseInt(textY2.getText());
		else
			return 0;
	}

}
