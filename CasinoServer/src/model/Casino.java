package model;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Casino {
	
	private ArrayList<Player> players;
	private Stack<String> cards;
	public static final String[] pintas= {"P","T","D","C"};
	public static final String[] numeros= {"2","3","4","5","6","7","8","9","10","J","Q","K","A"};
	
	public Casino(String [] ids) {
		cards=new Stack<String>();
		generateCards();
		for (int i = 0; i < ids.length; i++) {
			players.add(new Player(ids[i]));
		}
		for (int i = 0; i < players.size(); i++) {
			String [] cardsplayer = {cards.pop(),cards.pop()};
			players.get(i).setCards(cardsplayer);
		}
	}
	
	
	
	private void generateCards() {
		ArrayList<String> all=new ArrayList<String>();
		for (int i = 0; i < numeros.length; i++) {
			for (int j = 0; j < pintas.length; j++) {
				all.add(numeros[i]+pintas[j]);
			}
		}
		cards.addAll(RandomizeArray(all));
	}
	
	private static ArrayList<String> RandomizeArray(ArrayList<String> array){
		Random rgen = new Random(); 		
 
		for (int i=0; i<array.size(); i++) {
		    int randomPosition = rgen.nextInt(array.size());
		    String temp = array.get(i);
		    array.set(i, array.get(randomPosition));
		    array.set(randomPosition, temp);
		}
 
		return array;
	}
	

}