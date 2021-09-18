package fiesc.licenta.gsess;

import android.text.TextUtils;

public class Customers {

    String firstname, lastname, email, phoneNumber, gender, bornDate, password, city, street, streetNumber, postalCode;

    public Customers() {
    }

    public Customers(String firstname, String lastname, String email, String phoneNumber, String gender, String bornDate, String password, String city, String street, String streetNumber, String postalCode) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.bornDate = bornDate;
        this.password = password;
        this.city = city;
        this.street = street;
        this.streetNumber = streetNumber;
        this.postalCode = postalCode;

    }

    public String toString(String firstname, String lastname, String email, String phoneNumber, String gender, String bornDate, String password, String city, String street, String streetNumber, String postalCode) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.bornDate = bornDate;
        this.password = password;
        this.city = city;
        this.street = street;
        this.streetNumber = streetNumber;
        this.postalCode = postalCode;
        return this.firstname.toString() + this.lastname.toString() + this.email.toString() + this.phoneNumber.toString() + this.gender.toString() + this.bornDate.toString() + this.password.toString() + this.city.toString() + this.street.toString() + this.streetNumber.toString() + this.postalCode.toString();

    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBornDate() {
        return bornDate;
    }

    public void setBornDate(String bornDate) {
        this.bornDate = bornDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}

