package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import javax.swing.JOptionPane;

import communication.TCPConnection;
import communication.TCPConnection.ConnectionEvent;
import view.ViewGame;
import view.ViewWaitroom;

public class ControllerWaitroom implements ConnectionEvent, ActionListener{

	
	private ViewWaitroom view;
	private ViewGame viewGame;
	private TCPConnection connection;
	private String ipServer;
	private int messageCount;
	
	
	public ControllerWaitroom(ViewWaitroom viewWaitroom) {
		messageCount=0;
		ipServer="127.0.0.1";
		view=viewWaitroom;
		connection=TCPConnection.getInstance();
		TCPConnection.getInstance().addConnectionEvent(this);
		connection.connect(ipServer, 5010);
		System.out.println("Esperando");
		
	}


	@Override
	public void onConnection() {
		view.changeText("Conectado, esperando inicio juego");
	}


	@Override
	public void onMessage(String msj) {
		messageCount++;
		if(messageCount==1) {
		System.out.println(msj);
		view.setVisible(false);
		viewGame=new ViewGame(this);
		viewGame.setVisible(true);
		viewGame.getbtnSeguir().addActionListener(this);
		viewGame.getbtnRetirar().addActionListener(this);
		}
		else if(msj.startsWith("0")){
			String[] split=msj.split(";;");
			viewGame.gettfCartas().setText("Tus Cartas: "+split[1]);
			
		}
		else if(msj.startsWith("1")) {
			String[] split=msj.split(";;");
			viewGame.gettfMesa().append(split[1]+"\n");
			
		}
		else if(msj.startsWith("2")) {
			String split[]=msj.split(";;");
			viewGame.showMessage(split[1]);
		}
		else if(msj.startsWith("3")) {
			String split[]=msj.split(";;");
			viewGame.getbtnSeguir().setEnabled(false);
			viewGame.getbtnRetirar().setEnabled(false);
			viewGame.showMessage(split[1]);
		}
		
	}


	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource().equals(viewGame.getbtnSeguir())) {
			connection.sendMessage("seguir");
		}
		if(event.getSource().equals(viewGame.getbtnRetirar())) {
			connection.sendMessage("retirar");
		}
		
	}

}
