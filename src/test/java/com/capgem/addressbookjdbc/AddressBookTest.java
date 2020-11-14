package com.capg.addressbookjdbc;


import java.util.List;

import org.junit.Assert;
import org.junit.Test;


public class AddressBookTest 
{	
    @Test
    public void givenAddressBookDB_WhenRetrieved_ShouldMatchContactCount() throws DBCustomException{
		List<Contact> contactList = AddressBookDB.viewAddressBook();
		Assert.assertEquals(5, contactList.size());
	}
}