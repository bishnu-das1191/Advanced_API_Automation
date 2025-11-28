package com.demo.csv;

public class UserPOJO {

    //if we are changing the column names in CSV file then
    // we need to use @CsvBindByName annotation to map the column names to the fields
    private String username;
    private String password;

    public UserPOJO() {
    }

    public UserPOJO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserPOJO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
