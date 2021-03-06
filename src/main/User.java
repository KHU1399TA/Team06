package main;

import java.util.Date;

abstract class User {

    String firstName;
    String lastName;
    String phoneNumber;
    String username;
    String password;
    AccessLevel accessLevel;
    Date registrationDate = new Date();
    Date lastLoginDate = new Date();

    @Override
    public String toString() {
        return username + " " + firstName + " " + lastName + " " + phoneNumber + " " + accessLevel + " ("
                + registrationDate + ") (" + lastLoginDate + ")";
    }

}
