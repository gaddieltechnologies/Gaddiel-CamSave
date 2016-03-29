package com.example.camsave;

public class Contact {

// private variables
int _id;
String _name;
byte[] _image;
Double _lat;
Double _lon;
String _activecode;
String _refno;
String _sdesc;
boolean selected = false;
// Empty constructor
public Contact() {

}

// constructor
public Contact(int keyId, String name, byte[] image, Double lat, Double lon,String activecode,String refrenceno,String sdesc) {
	this._id = keyId;
	 this._name = name;
	 this._image = image;
	 this._lat = lat;
	 this._lon = lon;
	 this._activecode= activecode;
	 this._refno= refrenceno;
	 this._sdesc= sdesc;
	
}
public Contact(int keyId, String sdesc) {
	this._id = keyId;
	 this._sdesc = sdesc;
	
}
public Contact(int keyId) {
 this._id = keyId;

}

// getting ID
public int getID() {
 return this._id;
}

// setting id
public void setID(int keyId) {
 this._id = keyId;
}

// getting name
public String getName() {
 return this._name;
}

// setting name
public void setName(String name) {
 this._name = name;
}
//getting reference
public String getRefno() {
	 return this._refno;
	}

	// setting shortdesc
	public void setSdesc(String sdesc) {
	 this._sdesc = sdesc;
	}
	//getting shortdesc
	public String getSdesc() {
		 return this._sdesc;
		}

		// setting reference
		public void setRefno(String refno) {
		 this._refno = refno;
		}
// getting image
public byte[] getImage() {
 return this._image;
}

// setting image
public void setImage(byte[] image) {
 this._image = image;
}

public void setLat(Double Lat) {
	 this._lat = Lat;
	}

public Double getLat() {
	return this._lat ;
	}

public void setLon(Double Lon) {
	 this._lon = Lon;
	}

public Double getLon() {
	return this._lon;
	}
//getting ActiveCode
public String getActiveCode() {
return this._activecode;
}

//setting ActiveCode
public void setActiveCode(String activecode) {
this._activecode = activecode;
}


	

	public String get(String refno) {
		// TODO Auto-generated method stub
		return null;
	} 
	 
}



 

