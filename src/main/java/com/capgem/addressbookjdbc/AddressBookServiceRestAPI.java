package com.capg.addressbookjdbc;

import java.util.ArrayList;
import java.util.List;
//uc22

public class AddressBookServiceRestAPI {
	public static List<Contact> contactList;

	public AddressBookServiceRestAPI(List<Contact> contacts) {
		contactList = new ArrayList<Contact>(contacts);
	}

	public int countEntries() {
		return contactList.size();
	}

	public void addContact(Contact contact) {
		contactList.add(contact);
	}
}

