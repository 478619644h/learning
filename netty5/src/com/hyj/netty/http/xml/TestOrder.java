package com.hyj.netty.http.xml;

import com.hyj.netty.http.entity.Address;
import com.hyj.netty.http.entity.Customer;
import com.hyj.netty.http.entity.Order;
import com.hyj.netty.http.entity.Shipping;
import org.jibx.runtime.*;

import java.io.StringReader;
import java.io.StringWriter;

public class TestOrder {


    private IBindingFactory factory;

    private StringWriter writer;

    private StringReader reader;

    private final static String CHARSET_NAME = "UTF-8";


    private String encode2Xml(Order order) throws JiBXException {
        factory = BindingDirectory.getFactory(Order.class);
        writer = new StringWriter();
        IMarshallingContext context = factory.createMarshallingContext();
        context.setIndent(2);
        context.marshalDocument(order,CHARSET_NAME,null,writer);
        return writer.toString();
    }

    private Order decode2Order(String xmlStr) throws JiBXException {
        reader = new StringReader(xmlStr);
        IUnmarshallingContext unmarshallingContext = factory.createUnmarshallingContext();
        Order order = (Order)unmarshallingContext.unmarshalDocument(reader);
        return order;
    }


    public static void main(String[] args) throws JiBXException {
        Order order = new Order();
        order.setOrderNumber(123);
        Address address = new Address();
        address.setStreet1("热血八番街");
        address.setCity("银月");
        address.setCountry("天星");
        order.setBillTo(address);
        order.setShipTo(address);
        order.setShipping(Shipping.INTERNATIONAL_EXPRESS);
        Customer customer = new Customer();
        customer.setFirstName("浩");
        customer.setLastName("李");
        customer.setCustomerNumber(1231312);
        order.setCustomer(customer);

        TestOrder testOrder = new TestOrder();
        String xml = testOrder.encode2Xml(order);
        System.out.println(xml);

        Order order1 = testOrder.decode2Order(xml);
        System.out.println(order1);
    }


}
