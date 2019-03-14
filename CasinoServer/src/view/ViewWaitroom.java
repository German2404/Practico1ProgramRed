package view;

import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import controller.ControllerWaitroom;

public class ViewWaitroom extends JFrame implements ActionListener{
	
	//Componentes
	private JLabel labelIntro;
	private ControllerWaitroom controller;
	private TextField connectedUsers;
	private JButton empezar;

	public void setConnectedUsers(String connectedUsers) {
		this.connectedUsers.setText(connectedUsers);
	}
	
	public ViewWaitroom() {
		super("Conexión cliente");
		GridLayout layout = new GridLayout(1, 3);
		this.setLayout(layout);
		this.setSize(500,200);
		empezar=new JButton("Start");
		empezar.addActionListener(this);
		empezar.setActionCommand("aaa");
		this.add(empezar);
		
		labelIntro = new JLabel("Esperando conexiónes...");
		connectedUsers=new TextField("0 Conectados");
		connectedUsers.setEditable(false);
		this.add(labelIntro);
		this.add(connectedUsers);
		
		controller = new ControllerWaitroom(this);
	}
	
	
	//Metodos de acceso a los componentes
	public JLabel getLabelIntro() {
		return labelIntro;
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		controller.onButton();
		
	}	

}
