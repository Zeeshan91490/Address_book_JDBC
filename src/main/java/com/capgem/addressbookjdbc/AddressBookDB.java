package com.capg.addressbookjdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class AddressBookDB {
	public static final String URL = "jdbc:mysql://localhost:3306/address_book_service";
	public static final String USER = "root";
	public static final String PASSWORD = "Star@Sun98";
	private static Connection connection = null;
	
	/**
	 * UC16
	 * @return
	 * @throws DBCustomException
	 */
	public static Connection getConnection() throws DBCustomException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection  = DriverManager.getConnection(URL,USER,PASSWORD);
			System.out.println("Connection established!!");
		} catch (ClassNotFoundException e) {
			throw new DBCustomException("Unable to load driver class!!");
		} catch (SQLException e) {
			throw new DBCustomException("Connection failed!!");
		}
		return connection;
	}
	

	/**
	 * UC16
	 * @return
	 * @throws DBCustomException
	 */
	public static List<Contact> viewAddressBook() throws DBCustomException {
		List<Contact> contactList = new ArrayList<>();
		String query  = "SELECT id,first_name,last_name,phone,email,address, city, state, pin"
						+ " FROM person_details JOIN address_details"
						+ " ON person_details.add_id = address_details.add_id";
		try(Connection connection = getConnection()){
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(query);
			while(result.next()) {
				int id =result.getInt(1);
				String first_name = result.getString(2);
				String last_name = result.getString(3);
				String phone = result.getString(4);
				String email = result.getString(5);
				String address = result.getString(6);
				String city = result.getString(7);
				String state = result.getString(8);
				String pin  = result.getString(9);
				contactList.add(new Contact(id,first_name,last_name,phone,email,address, city, state, pin));
			}
		} catch (SQLException e) {
			throw new DBCustomException("Failed to retreive contacts");
		}
		return contactList;
	}
	
	
	public static void main(String[] args) throws DBCustomException {
		viewAddressBook();
	}
	
}
