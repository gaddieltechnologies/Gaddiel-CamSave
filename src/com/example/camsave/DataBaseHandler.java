package com.example.camsave;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHandler extends SQLiteOpenHelper {

 // All Static variables
	
	
	
 // Database Version
 private static final int DATABASE_VERSION = 2;

 // Database Name
 private static final String DATABASE_NAME = "imageDBse";

 // Contacts table name
 private static final String TABLE_CONTACTS = "contact_list";
 private static final String TABLE_USER = "contact_user";

 // Contacts Table Columns names
 private static final String KEY_ID = "id";
 private static final String KEY_NAME = "name";
 private static final String KEY_IMAGE = "image";
 private static final String KEY_LAT = "lat";
 private static final String KEY_LON = "lon";
 private static final String KEY_ACT = "active_code";
 private static final String KEY_REFNO = "refno";
 private static final String KEY_SDESC = "s"
 		+ "desc";
 
//Contacts Table Columns names
private static final String USER_ID = "id";
private static final String USER_ACT = "active_code";

//private static final Double Lon = null;

//private static final Double Lat = null;

 public DataBaseHandler(Context context) {
  super(context, DATABASE_NAME, null, DATABASE_VERSION);
 }

 // Creating Tables
 @Override
 public void onCreate(SQLiteDatabase db) {
	 System.out.print("oncreate statement");
 String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"+ KEY_IMAGE + " BLOB," + KEY_LAT + " REAL,"+ KEY_LON + " REAL,"+ KEY_ACT + " TEXT,"+ KEY_REFNO + " TEXT,"+ KEY_SDESC + " TEXT "+ ")";
 String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("+ USER_ID + " INTEGER PRIMARY KEY," + USER_ACT + " TEXT"+ ")";
  db.execSQL(CREATE_CONTACTS_TABLE);
  db.execSQL(CREATE_USER_TABLE);
 }

 // Upgrading database
 @Override
 public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
  // Drop older table if existed
  db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
  db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
  // Create tables again
  onCreate(db);
 }

 /**
 * All CRUD(Create, Read, Update, Delete) Operations
 */

 public void addContact(Contact contact) {
  SQLiteDatabase db = this.getWritableDatabase();

  ContentValues values = new ContentValues();
  values.put(KEY_NAME, contact._name); // Contact Name
  values.put(KEY_IMAGE, contact._image);
  values.put(KEY_LAT, contact._lat);
  values.put(KEY_LON, contact._lon);
  values.put(KEY_ACT,contact._activecode);
  values.put(KEY_REFNO,contact._refno);
  values.put(KEY_SDESC,contact._sdesc);

  // Inserting Row
  db.insert(TABLE_CONTACTS, null, values);
  db.close(); // Closing database connection
 }
 

 // Getting single contact
 Contact getContact(int id) {
  SQLiteDatabase db = this.getReadableDatabase();

  Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
    KEY_NAME, KEY_IMAGE }, KEY_ID + "=?",
    new String[] { String.valueOf(id) }, null, null, null, null);
  if (cursor != null)
   cursor.moveToFirst();

  Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
    cursor.getString(1),cursor.getBlob(2),cursor.getDouble(3),cursor.getDouble(4),cursor.getString(5),cursor.getString(6),cursor.getString(7));

  // return contact
  return contact;

 }

 public List<Contact> getOneContacts(Contact contacts) {
	 List<Contact> contactList = new ArrayList<Contact>();
	  // Select All Query
	  String selectQuery = "SELECT  * FROM contact_list WHERE id = "+ contacts.getID();
	  SQLiteDatabase db = this.getWritableDatabase();
	  Cursor cursor = db.rawQuery(selectQuery, null);
	  // looping through all rows and adding to list
	  if (cursor.moveToFirst()) {
	   do {
	    Contact contact = new Contact();
	    contact.setID(Integer.parseInt(cursor.getString(0)));
	    contact.setName(cursor.getString(1));
	    contact.setImage(cursor.getBlob(2));
	    contact.setLat(cursor.getDouble(3));
	    contact.setLon(cursor.getDouble(4));
	    contact.setActiveCode(cursor.getString(5));
	    contact.setRefno(cursor.getString(6));
	    contact.setSdesc(cursor.getString(7));
	    // Adding contact to list
	    contactList.add(contact);
	   } while (cursor.moveToNext());
	  }
	  // close inserting data from database
	  db.close();
	  // return contact list
	  return contactList;
	 }
 
 // Getting All Contacts
 public List<Contact> getAllContacts() {
  List<Contact> contactList = new ArrayList<Contact>();
  // Select All Query
  String selectQuery = "SELECT  * FROM contact_list ";

  SQLiteDatabase db = this.getWritableDatabase();
  Cursor cursor = db.rawQuery(selectQuery, null);
  // looping through all rows and adding to list
  if (cursor.moveToFirst()) {
   do {
    Contact contact = new Contact();
    contact.setID(Integer.parseInt(cursor.getString(0)));
    contact.setName(cursor.getString(1));
    contact.setImage(cursor.getBlob(2));
    contact.setLat(cursor.getDouble(3));
    contact.setLon(cursor.getDouble(4));
    contact.setActiveCode(cursor.getString(5));
    contact.setRefno(cursor.getString(6));
    contact.setSdesc(cursor.getString(7));
    
    
    // Adding contact to list
    contactList.add(contact);
   } while (cursor.moveToNext());
  }
  // close inserting data from database
  db.close();
  // return contact list
  return contactList;

 }
 
 // Updating single contact
 public int updateContact(Contact contact) {
  SQLiteDatabase db = this.getWritableDatabase();

  ContentValues values = new ContentValues();
  values.put(KEY_NAME, contact.getName());
  values.put(KEY_IMAGE, contact.getImage());
   values.put(KEY_LAT, contact.getLat());
   values.put(KEY_LON, contact.getLon()); 
   values.put(KEY_ACT,contact.getActiveCode());
   values.put(KEY_REFNO,contact.getRefno());
   values.put(KEY_SDESC,contact.getSdesc());
  // updating row
  return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
    new String[] { String.valueOf(contact.getID()) });

 }
 
 public void deleteContact(Contact contact) {
	  SQLiteDatabase db = this.getWritableDatabase();
	  db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
	    new String[] { String.valueOf(contact.getID()) });
	  db.close();
	 }

 


 // Getting contacts Count
 public int getContactsCount() {
  String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
  SQLiteDatabase db = this.getReadableDatabase();
  Cursor cursor = db.rawQuery(countQuery, null);
  cursor.close();

  // return count
  return cursor.getCount();
 }
 public void resetTables(){
     SQLiteDatabase db = this.getWritableDatabase();
     // Delete All Rows
     db.delete(TABLE_CONTACTS, null, null);
     db.close();
 }


public void addUser(User user) {
	 SQLiteDatabase db = this.getWritableDatabase();

	  ContentValues values = new ContentValues();
	  values.put(USER_ACT,user._activecode ); // Contact Name
	  
	 
	  // Inserting Row
	  db.insert(TABLE_USER, null, values);
	  db.close(); // Closing database connection
	
}
public List<User> getAllUser() {
	  List<User> contactList = new ArrayList<User>();
	  //List<Contact> contactList = new ArrayList<Contact>();
	  // Select All Query
	  String selectQuery = "SELECT  * FROM contact_user ";

	  SQLiteDatabase db = this.getWritableDatabase();
	  Cursor cursor = db.rawQuery(selectQuery, null);
	  // looping through all rows and adding to list
	  if (cursor.moveToFirst()) {
	   do {
	    User user = new User();
	    user.setID(Integer.parseInt(cursor.getString(0)));
	    user.setActiveCode(cursor.getString(1));
	    contactList.add(user);
	   } while (cursor.moveToNext());
	  }
	  // close inserting data from database
	  db.close();
	  // return contact list
	return contactList;
	 

	 }

public List<User> getOneUser() {
	 List<User> usr = new ArrayList<User>();
	  
	  // Select All Query
	  String selectQuery = "SELECT  * FROM contact_user";

	  SQLiteDatabase db = this.getWritableDatabase();
	  Cursor cursor = db.rawQuery(selectQuery, null);
	  // looping through all rows and adding to list
	  if (cursor.moveToFirst()) {
	   do {
	    User user = new User();
	    user.setID(Integer.parseInt(cursor.getString(0)));
	    user.setActiveCode(cursor.getString(1));
	    usr.add(user);
	   } while (cursor.moveToNext());
	  }
	  // close inserting data from database
	  db.close();
	  // return contact list
	return usr;
	 }


public void updateNote(int id, String sdesc){
	SQLiteDatabase db = this.getWritableDatabase();
	  ContentValues values = new ContentValues();
	  values.put(KEY_SDESC,sdesc);
	  db.update(TABLE_CONTACTS, values,  "id="+id, null);
	  db.close(); // Closing database connect
	    Log.i("pass id",  String.valueOf(id));
 
}




}

