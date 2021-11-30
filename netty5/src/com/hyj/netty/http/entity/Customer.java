package com.hyj.netty.http.entity;

import java.util.List;

public class Customer {

    private long customerNumber;

    private String firstName;

    private String lastName;

    private List<String> middleName;

    public long getCustomerNumber() {
        return customerNumber;
    }

    public Customer setCustomerNumber(long customerNumber) {
        this.customerNumber = customerNumber;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public Customer setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Customer setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public List<String> getMiddleName() {
        return middleName;
    }

    public Customer setMiddleName(List<String> middleName) {
        this.middleName = middleName;
        return this;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Customer{");
        sb.append("customerNumber=").append(customerNumber);
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", middleName=").append(middleName);
        sb.append('}');
        return sb.toString();
    }
}
