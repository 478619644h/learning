package com.hyj.netty.http.entity;

public class Order {
    private long orderNumber;

    private Customer customer;

    private Address billTo;

    private Shipping shipping;

    private Address shipTo;

    private Float total;

    public long getOrderNumber() {
        return orderNumber;
    }

    public Order setOrderNumber(long orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Order setCustomer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public Address getBillTo() {
        return billTo;
    }

    public Order setBillTo(Address billTo) {
        this.billTo = billTo;
        return this;
    }

    public Shipping getShipping() {
        return shipping;
    }

    public Order setShipping(Shipping shipping) {
        this.shipping = shipping;
        return this;
    }

    public Address getShipTo() {
        return shipTo;
    }

    public Order setShipTo(Address shipTo) {
        this.shipTo = shipTo;
        return this;
    }

    public Float getTotal() {
        return total;
    }

    public Order setTotal(Float total) {
        this.total = total;
        return this;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Order{");
        sb.append("orderNumber=").append(orderNumber);
        sb.append(", customer=").append(customer);
        sb.append(", billTo=").append(billTo);
        sb.append(", shipping=").append(shipping);
        sb.append(", shipTo=").append(shipTo);
        sb.append(", total=").append(total);
        sb.append('}');
        return sb.toString();
    }
}
