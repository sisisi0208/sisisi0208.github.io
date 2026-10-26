package domain;

public class DeckCard {
	
	private int id;
	private int deckId;
	private String name;
	private String color;
	private String cost;
	private String inc;
	private String type;
	private int quantity;
	
	public DeckCard() {}
	
	public DeckCard(int deckId, String name, String color, String cost, String inc, String type, int quantity) {
		this.deckId = deckId;
		this.name = name;
		this.color = color;
		this.cost = cost;
		this.inc = inc;
		this.type = type;
		this.quantity = quantity;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDeckId() {
		return deckId;
	}

	public void setDeckId(int deckId) {
		this.deckId = deckId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public String getInc() {
		return inc;
	}

	public void setInc(String inc) {
		this.inc = inc;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
