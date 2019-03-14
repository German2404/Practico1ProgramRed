package model;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Casino {
	
	private ArrayList<Player> players;
	private Stack<String> cards;
	public static final String[] pintas= {"P","T","D","C"};
	public static final String[] numeros= {"2","3","4","5","6","7","8","9","10","J","Q","K","A"};
	private ArrayList<String> onTable;
	
	public Casino(String [] ids) {
		onTable=new ArrayList<String>();
		cards=new Stack<String>();
		players=new ArrayList<Player>();
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
	
	public ArrayList<Player> getPlayers(){
		return players;
	}
	
	public void deal() {
		if(onTable.size()==0) {
			onTable.add(cards.pop());
			onTable.add(cards.pop());
			onTable.add(cards.pop());
		}
		else if(onTable.size()>=2&&onTable.size()<5){
			onTable.add(cards.pop());
		}
	}
	public ArrayList<String> getOnTable(){
		return onTable;
	}
	

}