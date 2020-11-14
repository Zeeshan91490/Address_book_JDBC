package com.capg.addressbookjdbc;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class AddressBookRestTest {
	AddressBookServiceRestAPI addressBookServiceRestAPI;
	List<Contact> contacts;

	@Before
	public void Setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 3000;
		addressBookServiceRestAPI = new AddressBookServiceRestAPI(getContactList());
	}

	@Test
	public void givenContactsInJSONServer_whenRead_ShouldMatchTheCount() {
		int result = addressBookServiceRestAPI.countEntries();
		Assert.assertEquals(4, result);
	}

	@Test
	public void givenContact_WhenAddedToJSONServer_ShouldMatchWithStatusCode() {
		Contact contact = new Contact(5,"Abishek", "Kumar", "Office", "Ranchi", "jharkhand", "123123",
				"159357", "hulk@gmail.com");
		addressBookServiceRestAPI.addContact(contact);
		Response response = addContactToJsonServer(contact);
		boolean result = response.getStatusCode() == 201 && addressBookServiceRestAPI.countEntries() == 5;
		Assert.assertTrue(result);
	}

	private Response addContactToJsonServer(Contact contact) {
		String contactJson = new Gson().toJson(contact);
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.body(contactJson);
		return request.post("/contacts");
	}
	
	private List<Contact> getContactList() {
		Response response = RestAssured.get("/contacts");
		Contact[] contacts = new Gson().fromJson(response.asString(), Contact[].class);
		return Arrays.asList(contacts);
	}
}