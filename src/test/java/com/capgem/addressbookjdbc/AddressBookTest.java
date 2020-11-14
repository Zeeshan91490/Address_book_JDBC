package com.capg.addressbookjdbc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class AddressBookTest {

	// UC16
	@Ignore
	@Test
	public void givenAddressBookDB_WhenRetrieved_ShouldMatchContactCount() throws DBCustomException {
		List<Contact> contactList = AddressBookDB.viewAddressBook();
		Assert.assertEquals(5, contactList.size());
	}

	// UC17
	@Ignore
	@Test
	public void givenUpdatedContacts_WhenRetrieved_ShouldBeSyncedWithDB() throws DBCustomException {
		AddressBookDB.updateContactInformation(3,"Kashif","Khan", "Home","Aligarh","UP","202001","7017756457","zeeshankhan91490@gmail.com" ,"50011", "ADD105");
		boolean isSynced = AddressBookDB.isSyncedWithDB("Kashif");
		Assert.assertTrue(isSynced);
	}
}
	