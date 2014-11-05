package com.example.pointeuse.models;

import java.util.Date;

public class Mouvement {

	private long id = -1;
	private Employe employe;
	private Date date;
	private String inDate;
	private String offDate;

	public Mouvement(){}
	
	public Mouvement(Employe employe, Date date, String inDate, String offDate) {
		super();
		this.employe = employe;
		this.inDate = inDate;
		this.offDate = offDate;
		this.date = date;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getInDate() {
		return inDate;
	}

	public void setInDate(String inDate) {
		this.inDate = inDate;
	}

	public String getOffDate() {
		return offDate;
	}

	public void setOffDate(String offDate) {
		this.offDate = offDate;
	}

	public Employe getEmploye() {
		return employe;
	}

	public void setEmploye(Employe employe) {
		this.employe = employe;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
