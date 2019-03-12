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

	public void setConnectedUsers(String connectedUsers) {
		this.connectedUsers.setText(connectedUsers);
		controller=new ControllerWaitroom(this);
	}


	private ControllerWaitroom controller;
	private TextField connectedUsers;
	private JButton empezar;
	
	public ViewWaitroom() {
		super("Conexión cliente");
		GridLayout layout = new GridLayout(1, 3);
		this.setLayout(layout);
		this.setSize(500,500);
		empezar=new JButton("Start");
		empezar.addActionListener(this);
		empezar.setActionCommand("aaa");
		
		labelIntro = new JLabel("Esperando conexiónes...");
		connectedUsers=new TextField("0");
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
