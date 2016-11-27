package vos1;

import org.codehaus.jackson.annotate.JsonProperty;

public class Vuelo {

	
	/**
	 * Identificacion
	 */
	@JsonProperty(value="id")
	private int id;
	
	/**
	 * Identificacion reserva 
	 */
	@JsonProperty(value="id1")
	private int id1;
	
	/**
	 * Identificacion otra reserva
	 */
	@JsonProperty(value="id2")
	private int id2;
	
	/**
	 * Identificacion otra reserva
	 */
	@JsonProperty(value="id3")
	private int id3;

	public Vuelo(int id, int id1, int id2, int id3) {
		super();
		this.id = id;
		this.id1 = id1;
		this.id2 = id2;
		this.id3 = id3;
	}
	
	public Vuelo(int id, int id1, int id2) {
		super();
		this.id = id;
		this.id1 = id1;
		this.id2 = id2;
	}
	
	public Vuelo(int id, int id1) {
		super();
		this.id = id;
		this.id1 = id1;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId1() {
		return id1;
	}

	public void setId1(int id1) {
		this.id1 = id1;
	}

	public int getId2() {
		return id2;
	}

	public void setId2(int id2) {
		this.id2 = id2;
	}

	public int getId3() {
		return id3;
	}

	public void setId3(int id3) {
		this.id3 = id3;
	}
	
	
	
}
