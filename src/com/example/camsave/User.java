package com.example.camsave;

public class User {


// private variables
int _id;
String _activecode;

// Empty constructor
public User() {

}

// constructor
public User(int userId,String activecode) {
	this._id = userId;
	this._activecode= activecode;
	
	
}

public User(int userId) {
 this._id = userId;

}

// getting ID
public int getID() {
 return this._id;
}

// setting id
public void setID(int userId) {
 this._id = userId;
}

//getting ActiveCode
public String getActiveCode() {
return this._activecode;
}

//setting ActiveCode
public void setActiveCode(String activecode) {
this._activecode = activecode;
}

	 
}



 

