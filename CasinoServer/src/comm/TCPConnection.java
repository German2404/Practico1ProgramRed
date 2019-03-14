package comm;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class TCPConnection {
	
	private static TCPConnection instance = null;
	
	private TCPConnection() {
		listeners = new ArrayList<>();
		connections = new HashMap<>();
		turnos=new ArrayList<String>();
	}
	
	public synchronized static TCPConnection getInstance() {
		if(instance == null) {
			instance = new TCPConnection();
		}
		return instance;
	}
	
	
	
	//Global
	private ServerSocket server;
	private HashMap<String, Connection> connections;
	private List<ConnectionEvent> listeners;
	private boolean open;
	private ArrayList<String> turnos;
	
	
	//Metodo del servidor
	public void waitForConnection(int port) {

		try {
			open=true;
			server = new ServerSocket(port);

			
			while(open) {
				System.out.println("Esperando cliente");
				Socket socket = server.accept();
				System.out.println("Cliente conectado!");
				Connection connection = new Connection(socket);
				connection.defineListeners(listeners);
				connection.init();
				connections.put(connection.getUuid(), connection);
				turnos.add(connection.getUuid());
				for(int i=0 ; i<listeners.size() ; i++) listeners.get(i).onConnection();
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	//Hacer la clase sea observable
	public interface ConnectionEvent{
		void onConnection();
		void onMessage(String uuid, String msj);
	}
	
	
	
	public void addConnectionEvent(ConnectionEvent listener) {
		listeners.add(listener);
	}

	public int getClientsCount() {
		return connections.size();
	}

	public void sendBroadcast(String line) {
		for( String key : connections.keySet() ) {
			connections.get(key).sendMessage(line);
		}		
	}

	public Connection getConnectionById(String uuid) {
		return connections.get(uuid);
	}

	public void sendDirectMessage(String remitente, String destinatario, String mensaje) {
		getConnectionById(destinatario).sendMessage(mensaje);		
	}
	
	public void stopConnectionsThread() {
		open=false;
	}
	
	public  ArrayList<String> getTurnos() {
		return turnos;
	}
	
	
	
}
