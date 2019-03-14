package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import comm.TCPConnection;
import comm.TCPConnection.ConnectionEvent;
import model.Casino;
import view.ViewWaitroom;

public class ControllerWaitroom implements ConnectionEvent{

	private ViewWaitroom view;
	private TCPConnection connection;
	private Casino mundo;
	private int turno;
	private int ronda;
	
	public ControllerWaitroom(ViewWaitroom view) {
		this.view = view;
		turno=0;
		ronda=0;
		connection=TCPConnection.getInstance();
		TCPConnection.getInstance().addConnectionEvent(this);
		System.out.println("Printing");
		Thread t = new Thread(() -> {
		    connection.waitForConnection(5010);
		});
		t.start();
	}
	
	public void onButton() {
		if(connection.getClientsCount()>=2) {
			System.out.println("Empezando Juego");
			view.getLabelIntro().setText("Juego empezado");
			connection.stopConnectionsThread();
			connection.sendBroadcast("Starting game");
			view.setVisible(false);
			startGame();
		}
		else {
			view.setConnectedUsers("No hay suficientes jugadores;;;;"+connection.getClientsCount());
		}
		
	}
	
	public void startGame() {
		mundo=new Casino(TCPConnection.getInstance().getTurnos().toArray(new String[0]));
		for (int i = 0; i < mundo.getPlayers().size() ; i++) {
			String mensaje="0;;"+mundo.getPlayers().get(i).getCards()[0]+"  "+mundo.getPlayers().get(i).getCards()[1];
			connection.sendDirectMessage("", TCPConnection.getInstance().getTurnos().get(i), mensaje);
		}
		TCPConnection.getInstance().sendBroadcast("1;;"+"Es el turno de "+TCPConnection.getInstance().getTurnos().get(turno));
		connection.sendDirectMessage("", TCPConnection.getInstance().getTurnos().get(turno), "2;;Es tu turno");
		
	}
	
	

	public void onEachConnection() {
		view.setConnectedUsers(connection.getClientsCount()+"");
		if(connection.getClientsCount()==3) {
			onButton();
		}
	}
	

	@Override
	public void onMessage(String uuid, String msj) {
		if(TCPConnection.getInstance().getTurnos().get(turno).equals(uuid)) {
			if(msj.equals("retirar")) {
				TCPConnection.getInstance().getTurnos().remove(turno);
				if(TCPConnection.getInstance().getTurnos().size()==1) {
					endGame();
				}
				TCPConnection.getInstance().sendBroadcast("1;;"+"Se ha retirado "+uuid);
				TCPConnection.getInstance().sendDirectMessage("", uuid, "3;;Te has retirado");
				if(turno==TCPConnection.getInstance().getTurnos().size()) {
					mundo.deal();
					turno=0;
					TCPConnection.getInstance().sendBroadcast("1;;Cartas en la mesa: "+mundo.getOnTable().toString());
					ronda++;
				}
				if(ronda==4) {
					endGame();
				}
				else {
				TCPConnection.getInstance().sendBroadcast("1;;"+"Es el turno de "+TCPConnection.getInstance().getTurnos().get(turno));
				TCPConnection.getInstance().sendDirectMessage("", TCPConnection.getInstance().getTurnos().get(turno), "2;;Es tu turno");
				}
			}
			if(msj.equals("seguir")) {
				turno++;
				if(turno==TCPConnection.getInstance().getTurnos().size()) {
					mundo.deal();
					turno=0;
					TCPConnection.getInstance().sendBroadcast("1;;Cartas en la mesa: "+mundo.getOnTable().toString());
					ronda++;
				}
				if(ronda==4) {
					endGame();
				}
				else {
				TCPConnection.getInstance().sendBroadcast("1;;"+"Es el turno de "+TCPConnection.getInstance().getTurnos().get(turno));
				TCPConnection.getInstance().sendDirectMessage("", TCPConnection.getInstance().getTurnos().get(turno), "2;;Es tu turno");
				}
			}
		}
		else {
			TCPConnection.getInstance().sendDirectMessage("", uuid, "2;;No es tu turno");
		}
	}
	
	public void endGame() {
		TCPConnection.getInstance().sendBroadcast("3;;Juego finalizado");
	}

	@Override
	public void onConnection() {
		this.onEachConnection();
	}
	
	
}
