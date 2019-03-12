package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import comm.TCPConnection;
import comm.TCPConnection.ConnectionEvent;
import view.ViewWaitroom;

public class ControllerWaitroom implements ConnectionEvent{

	private ViewWaitroom view;
	TCPConnection connection;
	private int connectionCount;
	
	public ControllerWaitroom(ViewWaitroom view) {
		this.view = view;
	}
	
	public void onButton() {
		System.out.println("Empezando Juego");
		view.setVisible(false);
		//Metodo para empezar el juego en el connection
	}
	
	
	@Override
	public void onConnection() {
		connectionCount++;
		view.setConnectedUsers(connectionCount+"");
		if(connectionCount==3) {
			onButton();
		}
	}
	

	@Override
	public void onMessage(String uuid, String msj) {
		// TODO Auto-generated method stub
		
	}
	
	
}
