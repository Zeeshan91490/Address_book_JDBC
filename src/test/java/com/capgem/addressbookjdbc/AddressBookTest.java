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
		boolean isSynced = AddressBookDB.isSyncedWithDB("Bhargav");
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
		Assert.assertEquals(2, noOfContacts.get("Hyderabad"), 0);
	}

	// UC20
	@Ignore
	@Test
	public void addedNewContact_whenContactsCounted_shouldGiveCurrentCountOfContacts() throws DBCustomException {
		String[] book_id = { "AB002", "AB003" };
		AddressBookDB.insertContactInformation("Lakshmi", "Ganesh", "524524524", "klg@gmail.com", "Plot", "Vizag", "AP",
				"542111", "ADD102", book_id, "2019-12-12");
		List<Contact> contactList = AddressBookDB.viewAddressBook();
		Assert.assertEquals(6, contactList.size());
	}

	// UC21
	@Ignore
	@Test
	public void addedMultipleContacts_whenContactsCounted_shouldGiveCurrentContactsCount() throws DBCustomException {
		String[] book_id = { "AB002" };
		Contact[] multipleContacts = {
				new Contact("Venkat", "Abhishek", "524524524", "vka@gmail.com", "Plot", "Vizag", "AP", "542111",
						"ADD102", book_id, "2019-12-12"),
				new Contact("Sai", "Teja", "1591591591", "cst@gmail.com", "Plot", "Vizag", "AP", "542111", "ADD102",
						book_id, "2020-12-12"),
				new Contact("Parwez", "Aktar", "7537537537", "pa@gmail.com", "Plot", "Vizag", "AP", "542111", "ADD102",
						book_id, "2017-12-12") };
		AddressBookDB.insertMultipleContactsUsingThreads(Arrays.asList(multipleContacts));
		List<Contact> contactList = AddressBookDB.viewAddressBook();
		int size = contactList.size();
		Assert.assertEquals(43, size);
		System.out.println("Hello");
	}
