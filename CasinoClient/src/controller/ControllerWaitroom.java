package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import communication.TCPConnection;
import communication.TCPConnection.ConnectionEvent;
import view.ViewGame;
import view.ViewWaitroom;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;

public class ControllerWaitroom implements ConnectionEvent, ActionListener{

	
	private ViewWaitroom view;
	private ViewGame viewGame;
	private TCPConnection connection;
	private String ipServer;
	private int messageCount;
	
	
	public ControllerWaitroom(ViewWaitroom viewWaitroom) {
		messageCount=0;
		ipServer="127.0.0.1";
		FileInputStream credentialsStream;
		try {
			credentialsStream = new FileInputStream("./src/lib/data-2780e-firebase-adminsdk-1bgua-f46be92c9a.json");
			FirebaseOptions options = new FirebaseOptions.Builder()
				    .setCredentials(GoogleCredentials.fromStream(credentialsStream))
				    .setDatabaseUrl("https://data-2780e.firebaseio.com")
				    .build();
				FirebaseApp.initializeApp(options);
				Firestore db = FirestoreClient.getFirestore();
				DocumentReference ref=db.collection("ip").document("direccion");
				ApiFuture<DocumentSnapshot> doc=ref.get();
				ipServer=(String) doc.get().getData().get("currentIp");
				System.out.println(ipServer);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
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
