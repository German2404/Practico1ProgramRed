package model;

public class Player {
	
	private boolean retired;
	private String[] cards;
	private String id;
	
	
	public boolean isRetired() {
		return retired;
	}


	public void setRetired(boolean retired) {
		this.retired = retired;
	}


	public String[] getCards() {
		return cards;
	}


	public void setCards(String[] cards) {
		this.cards = cards;
	}


	public Player(String id) {
		retired=false;
		cards=new String[2];
		this.id=id;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}
	
	

}
