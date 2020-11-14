package com.capg.addressbookjdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
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
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
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
		String query = "SELECT id,first_name,last_name,phone,email,address, city, state, pin"
				+ " FROM person_details JOIN address_details" + " ON person_details.add_id = address_details.add_id";
		try (Connection connection = getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(query);
			while (result.next()) {
				int id = result.getInt(1);
				String first_name = result.getString(2);
				String last_name = result.getString(3);
				String phone = result.getString(4);
				String email = result.getString(5);
				String address = result.getString(6);
				String city = result.getString(7);
				String state = result.getString(8);
				String pin = result.getString(9);
				contactList.add(new Contact(id, first_name, last_name, phone, email, address, city, state, pin));
			}
		} catch (SQLException e) {
			throw new DBCustomException("Failed to retreive contacts");
		}
		return contactList;
	}

	/**
	 * UC17
	 * 
	 * @param id
	 * @param first_name
	 * @param last_name
	 * @param phone
	 * @param email
	 * @param address
	 * @param city
	 * @param state
	 * @param pin
	 * @param add_id
	 * @throws DBCustomException
	 */
	public static void updateContactInformation(int id, String first_name, String last_name, String phone, String email,
			String address, String city, String state, String pin, String add_id) throws DBCustomException {

		String personDetails = "UPDATE person_details SET first_name = ?, last_name = ?, phone = ?, email = ?, add_id = ? WHERE id =?";
		String updateAddress = "UPDATE address_details SET address = ?, city = ?, state = ?, pin = ? WHERE add_id = ?";
		String addAddress = "INSERT INTO address_details VALUES(?,?,?,?,?)";
		String query = String.format("SELECT add_id,state FROM address_details WHERE add_id = '%s'", add_id);

		try (Connection connection = getConnection()) {
			connection.setAutoCommit(false);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			int rowsUpdated = 0;
			if (resultSet.next()) {
				PreparedStatement preparedStatement = connection.prepareStatement(updateAddress);
				preparedStatement.setString(1, address);
				preparedStatement.setString(2, city);
				preparedStatement.setString(3, state);
				preparedStatement.setString(4, pin);
				preparedStatement.setString(5, add_id);
				rowsUpdated = preparedStatement.executeUpdate();
			} else {
				PreparedStatement preparedStatement = connection.prepareStatement(addAddress);
				preparedStatement.setString(1, add_id);
				preparedStatement.setString(2, address);
				preparedStatement.setString(3, city);
				preparedStatement.setString(4, state);
				preparedStatement.setString(5, pin);
				rowsUpdated = preparedStatement.executeUpdate();
			}
			if (rowsUpdated != 1) {
				throw new DBCustomException("Failed to update address details");
			}

			PreparedStatement preparedStatement = connection.prepareStatement(personDetails);
			preparedStatement.setString(1, first_name);
			preparedStatement.setString(2, last_name);
			preparedStatement.setString(3, phone);
			preparedStatement.setString(4, email);
			preparedStatement.setString(5, add_id);
			preparedStatement.setInt(6, id);
			rowsUpdated = preparedStatement.executeUpdate();
			if (rowsUpdated != 1) {
				throw new DBCustomException("Failed to update person details");
			}
			connection.commit();
		} catch (SQLException e) {
			throw new DBCustomException("Unable to update details in data base");
		}
	}

	public static List<Contact> viewContactsByName(String firstName) throws DBCustomException {
		List<Contact> contactListByName = new ArrayList<>();
		String query = "SELECT id,first_name,last_name,phone,email,address, city, state, pin"
				+ " FROM person_details JOIN address_details"
				+ " ON person_details.add_id = address_details.add_id WHERE `first_name` = '?'";
		try (Connection connection = getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, firstName);
			ResultSet result = preparedStatement.executeQuery();
			if (result.next()) {
				int id = result.getInt(1);
				String first_name = result.getString(2);
				String last_name = result.getString(3);
				String phone = result.getString(4);
				String email = result.getString(5);
				String address = result.getString(6);
				String city = result.getString(7);
				String state = result.getString(8);
				String pin = result.getString(9);
				contactListByName.add(new Contact(id, first_name, last_name, phone, email, address, city, state, pin));
			}
		} catch (SQLException e) {
			throw new DBCustomException("Failed to retreive contact");
		}
		return contactListByName;
	}

	public static Contact getContactDetails(String firstName) throws DBCustomException {
		return viewAddressBook().stream().filter(c -> c.getFirst_name().equals(firstName)).findFirst().orElse(null);
	}

	public static boolean isSyncedWithDB(String firstName) throws DBCustomException {
		try {
			return viewContactsByName(firstName).get(0).equals(getContactDetails(firstName));
		} catch (IndexOutOfBoundsException e) {
		} catch (Exception e) {
			e.printStackTrace();
			throw new DBCustomException("SQL Exception");
		}
		return false;
	}
	
	/**
	 * UC18
	 * 
	 * @return
	 * @throws DBCustomException
	 */
	public static List<Contact> viewAddressBookByDate(LocalDate startDate, LocalDate endDate) throws DBCustomException {
		List<Contact> contactListByDate = new ArrayList<>();
		String query = "SELECT id,first_name,last_name,phone,email,address, city, state, pin,start_date"
				+ " FROM person_details JOIN address_details" + " ON person_details.add_id = address_details.add_id "
				+ "WHERE `start_date` BETWEEN ? and ?";
		try (Connection connection = getConnection()) {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setDate(1, Date.valueOf(startDate));
			statement.setDate(2, Date.valueOf(endDate));
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				int id = result.getInt(1);
				String first_name = result.getString(2);
				String last_name = result.getString(3);
				String phone = result.getString(4);
				String email = result.getString(5);
				String address = result.getString(6);
				String city = result.getString(7);
				String state = result.getString(8);
				String pin = result.getString(9);
				Contact contact = new Contact(id, first_name, last_name, phone, email, address, city, state, pin);
				System.out.println(contact);
				contactListByDate.add(contact);
			}
		} catch (SQLException e) {
			System.out.println(e);
			throw new DBCustomException("Failed to retreive contacts within given dates");
		}
		return contactListByDate;
	}

}
