package com.hyj.netty.http.entity;

public class Address {

    private String street1;

    private String street2;

    private String city;

    private String state;

    private String postCode;

    private String country;

    public String getStreet1() {
        return street1;
    }

    public Address setStreet1(String street1) {
        this.street1 = street1;
        return this;
    }

    public String getStreet2() {
        return street2;
    }

    public Address setStreet2(String street2) {
        this.street2 = street2;
        return this;
    }

    public String getCity() {
        return city;
    }

    public Address setCity(String city) {
        this.city = city;
        return this;
    }

    public String getState() {
        return state;
    }

    public Address setState(String state) {
        this.state = state;
        return this;
    }

    public String getPostCode() {
        return postCode;
    }

    public Address setPostCode(String postCode) {
        this.postCode = postCode;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public Address setCountry(String country) {
        this.country = country;
        return this;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Address{");
        sb.append("street1='").append(street1).append('\'');
        sb.append(", street2='").append(street2).append('\'');
        sb.append(", city='").append(city).append('\'');
        sb.append(", state='").append(state).append('\'');
        sb.append(", postCode='").append(postCode).append('\'');
        sb.append(", country='").append(country).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
