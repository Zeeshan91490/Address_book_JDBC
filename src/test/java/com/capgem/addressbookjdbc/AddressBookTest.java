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

	// UC18
	@Ignore
	@Test
	public void givenAddressBookDB_WhenRetrievedByDate_ShouldMatchContactCount() throws DBCustomException {
		List<Contact> contactListByDate = AddressBookDB.viewAddressBookByDate(LocalDate.of(2019, 01, 01),
				LocalDate.now());
		Assert.assertEquals(3, contactListByDate.size());
	}

	// UC19
	@Ignore
	@Test
	public void givenAddressBookDB_WhenCountedByState_ShouldMatchCount() throws DBCustomException {
		Map<String, Integer> noOfContacts = AddressBookDB.viewCountByCityOrState("state");
		Assert.assertEquals(2, noOfContacts.get("AP"), 0);
	}

	@Ignore
	@Test
	public void givenAddressBookDB_WhenCountedByCity_ShouldMatchCount() throws DBCustomException {
		Map<String, Integer> noOfContacts = AddressBookDB.viewCountByCityOrState("city");
		Assert.assertEquals(2, noOfContacts.get("UP"), 0);
	}

	