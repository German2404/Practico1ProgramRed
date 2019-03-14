package recursos;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Random;

public class Emisor {
	private OutputStream out;
	public static final byte[] A= {27,27,27};
	public static final byte[] B= {27,32,27};
	public static final byte[] C= {27,32,32};
	public static final byte[] D= {32,32,27};
	public static final String CARACTER_ENVIO_RTT=new String(A);
	public static final String CARACTER_RECIBIDO_RTT=new String(B);
	public static final String CARACTER_ENVIO_SPEED=new String(C);
	public static final String CARACTER_RECIBIDO_SPEED=new String(D);
	
	public Emisor(OutputStream out) {
		this.out = out;
	}
	
	public void enviarMensaje(String msj) {
		
		new Thread(){
			@Override
			public void run() {
				PrintWriter writer = new PrintWriter(new OutputStreamWriter(new BufferedOutputStream(out)));
				switch(msj) {
				
				case "RTT":
					TCPConnection.getInstance().startClock();
					String mensaje=new String(generarBytes(1024));
					writer.println(CARACTER_ENVIO_RTT+mensaje);
					writer.flush();
					break;
				case "speed":
					TCPConnection.getInstance().startClock();
					int n=4096;
					String msg=new String(generarBytes(n));
					writer.println(CARACTER_ENVIO_SPEED+msg);
					writer.flush();
					
					break;
				default:
					writer.println(msj);
					writer.flush();
					break;
				}
				
			}
		}.start();
		
		/*
		new Thread( () -> {
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(new BufferedOutputStream(out)));
			writer.println(msj);
			writer.flush();
		}).start();
		*/
		
		
	}
	
	
	public byte[] generarBytes(int n) {
		Random r= new Random();
		byte[] message= new  byte[n];
		for (int i = 0; i < message.length; i++) {
			message[i]=(byte)(32+r.nextInt(96));
			if(message[i]==10) {
				message[i]=64;
			}
		}
		return message;
	}
	
}
