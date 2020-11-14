package com.capg.addressbookjdbc;

public class Contact {
	int id;
	String first_name, last_name, phone, email, address, city, state, pin;
	private String start_date;
	private String[] book_id;
	private String add_id;

	public Contact(int id, String first_name, String last_name, String phone, String email, String address, String city,
			String state, String pin) {
		super();
		this.id = id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.city = city;
		this.state = state;
		this.pin = pin;
	}

	public Contact(String first_name, String last_name, String phone, String email, String address, String city,
			String state, String pin, String add_id, String[] book_id, String start_date) {
		this.first_name = first_name;
		this.last_name = last_name;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.city = city;
		this.state = state;
		this.pin = pin;
		this.setAdd_id(add_id);
		this.setBook_id(book_id);
		this.setStart_date(start_date);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	@Override
	public String toString() {
		return "Contact [id=" + id + ", first_name=" + first_name + ", last_name=" + last_name + ", phone=" + phone
				+ ", email=" + email + ", address=" + address + ", city=" + city + ", state=" + state + ", pin=" + pin
				+ "]";
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String[] getBook_id() {
		return book_id;
	}

	public void setBook_id(String[] book_id) {
		this.book_id = book_id;
	}

	public String getAdd_id() {
		return add_id;
	}

	public void setAdd_id(String add_id) {
		this.add_id = add_id;
	}

}