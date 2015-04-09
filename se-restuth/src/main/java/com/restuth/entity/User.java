package com.restuth.entity;

import java.util.ArrayList;

public class User {
	
	private String id;
	private String fname;
	private String lname;
	private String email;
	private ArrayList<String> interestList;

	public User()
	{}
	
	public User(String fname,String lname,String email)
	{
		this.fname=fname;
		this.lname=lname;
		this.email=email;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	
	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ArrayList<String> getInterestList() {
		return interestList;
	}

	public void setInterestList(ArrayList<String> interestList) {
		this.interestList = interestList;
	}

	
	@Override
	public String toString()
	{
		StringBuilder sb=new StringBuilder();
		sb.append(" fname: "+fname);
		sb.append(" lname: "+lname);
		sb.append(" email: "+email);
		sb.append(" Interested in : ");
		for(String inlist:interestList)
		{
			sb.append(inlist+" ");
		}
		return sb.toString();
		
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
