package com.infosupport;

/**
 * Created by StijnG on 6/12/2017.
 */
public class PojoRequest{
    private String firstName;
    private String lastName;
    private String httpMethod;

    public PojoRequest(String firstName, String lastName, String httpMethod) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.httpMethod = httpMethod;
    }

    public PojoRequest(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public PojoRequest() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }
}