package com.tellfan;

import java.util.ArrayList;
import java.util.Date;

public class User {
	public String name;
	public String password;
	public String email;
	public String phone;
	public Boolean isClient;

	public ArrayList<Greeting> greetingList;
	
	/** new **/
	public int id;
	public String firstName;
	public String lastName;
	public String thumbnail;
	public String userType;
	public Date expires;
	
	
	ArrayList<User> clientList; 
	public User()
	{
		clientList = new ArrayList<User>();
		greetingList = new ArrayList<Greeting>();
	}
	
}
