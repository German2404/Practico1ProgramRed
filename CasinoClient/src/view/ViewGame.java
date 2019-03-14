package view;

import java.awt.GridLayout;
import java.awt.ScrollPane;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import controller.ControllerWaitroom;

public class ViewGame extends JFrame{
	
	private JTextField tfCartas;
	private JTextArea tfMesa;
	private JButton btnSeguir;
	private JButton btnRetirar;
	private ControllerWaitroom controller;
	
	public ViewGame(ControllerWaitroom c) {
		super("Chat cliente");
		GridLayout layout = new GridLayout(0, 1);
		this.setLayout(layout);
		this.setSize(500,500);
		
		tfMesa = new JTextArea("");
		 JScrollPane scroll = new JScrollPane(tfMesa);
		 scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		tfCartas = new JTextField();
		btnSeguir = new JButton("Seguir");
		btnRetirar=new JButton("Retirar");
		tfCartas.setEditable(false);
		tfMesa.setEditable(false);
		
		this.add(tfCartas);
		this.add(scroll);
		this.add(btnSeguir);
		this.add(btnRetirar);
		
		controller = c;
	}

	public JTextField gettfCartas() {
		return tfCartas;
	}

	public JTextArea gettfMesa() {
		return tfMesa;
	}

	public JButton getbtnSeguir() {
		return btnSeguir;
	}
	public JButton getbtnRetirar() {
		return btnRetirar;
	}
	

	public ControllerWaitroom getController() {
		return controller;
	}

	public void showMessage(String string) {
		JOptionPane.showMessageDialog(this, string, "Atención", JOptionPane.INFORMATION_MESSAGE);
		
	}
	
	

}
