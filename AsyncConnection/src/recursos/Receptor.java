package recursos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Receptor extends Thread{
	private InputStream input;
	private boolean isAlive = true;
	
	public Receptor(InputStream input) {
		this.input = input;
	}
	
	@Override
	public void run() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			while( isAlive ) {
				String line = reader.readLine();
				//Detectamos la desconexión
				if(line ==  null) {
					input.close();
				}
				switch(line) {
				case "whatTimeIsIt":
					System.out.println("Request for Time");
					String hora=new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
					System.out.println( hora );
					TCPConnection.getInstance().sendMessage("Partner Time: "+hora);
					break;
				case "remoteIpConfig":
					System.out.println("Request for ip");
					String ip= InetAddress.getLocalHost().getHostAddress();
					System.out.println(ip);
					TCPConnection.getInstance().sendMessage("Partner Ip: "+ip);
					break;
				case "interface":
					System.out.println("Request for interface");
					String inter=NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).toString();
					System.out.println(inter);
					TCPConnection.getInstance().sendMessage("Partner Interface: "+inter);
					break;
				default:
					if(line.startsWith(Emisor.CARACTER_ENVIO_RTT)) {
						System.out.println("Request for RTT");
						String mensaje=line.replaceAll(Emisor.CARACTER_ENVIO_RTT, "");
						TCPConnection.getInstance().sendMessage(Emisor.CARACTER_RECIBIDO_RTT+mensaje);
						System.out.println("Menssage resent");
					}
					else if(line.startsWith(Emisor.CARACTER_RECIBIDO_RTT)) {
						System.out.println("RT Message recieved");
						System.out.println("RTT: "+TCPConnection.getInstance().finishClock() + " nanoseconds");
					}
					else if(line.startsWith(Emisor.CARACTER_ENVIO_SPEED)) {
						System.out.println("Request for speed");
						String temp=line.replaceAll(Emisor.CARACTER_ENVIO_SPEED,"");
						TCPConnection.getInstance().sendMessage(Emisor.CARACTER_RECIBIDO_SPEED+temp);
						System.out.println("Message resent");
					}
					else if(line.startsWith(Emisor.CARACTER_RECIBIDO_SPEED)) {
						System.out.println("Speed Retransmision Recieved");
						double speed=(line.length()/1000.0)/(TCPConnection.getInstance().finishClock()/1000000000.0);
						System.out.println("Speed: "+speed+" KB/s");
					}
					else {
					System.out.println(line);
					break;
					}
					
				}
				
				
				
				
				
			}
		}catch(IOException ex) {
			
		}
	}

}
