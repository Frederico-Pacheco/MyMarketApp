package br.com.mymarket.infra;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import br.com.mymarket.model.Pessoa;

public class Contacts {
	
	private Context context;
	
	private List<String> contactsNumber = new ArrayList<String>();
	
	private List<Pessoa> contacts = new ArrayList<Pessoa>();
	
	public Contacts(Context context){
		this.context=context;
	}
	
	public void setContatos() {
		Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
		Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

		ContentResolver contentResolver = context.getContentResolver();
		Cursor cursor = contentResolver.query(CONTENT_URI, null, null, null,null);
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				String contact_id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
				String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
				int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
				if (hasPhoneNumber > 0) {
					// Query and loop for every phone number of the contact
					Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",new String[] { contact_id }, null);
					while (phoneCursor.moveToNext()) {
						String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
						contacts.add(new Pessoa(name,phoneNumber));
						contactsNumber.add(phoneNumber);
					}
					phoneCursor.close();
				}
			}
		}
	}
	
	
	public List<String> getNumbers(){
		return contactsNumber;
	}
	
	public List<Pessoa> getContacts(){
		return contacts;
	}
	
}
