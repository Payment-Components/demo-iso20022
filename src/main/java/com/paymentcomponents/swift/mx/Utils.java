package com.paymentcomponents.swift.mx;

import gr.datamation.mx.Message;
import gr.datamation.validation.error.ValidationError;
import gr.datamation.validation.error.ValidationErrorList;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.GregorianCalendar;

public class Utils {

    public static void printValidMessageOrErrors(Message messageObject, ValidationErrorList validationErrorList) throws JAXBException, UnsupportedEncodingException {
        if (validationErrorList == null || validationErrorList.isEmpty()) {
            System.out.println("Message is valid");
            System.out.println(messageObject.convertToXML());
        } else {
            System.err.println("Message is invalid, and the errors are the following:");
            for (ValidationError error : validationErrorList) {
                System.err.println(error.toString());
                System.err.println(
                        "Error Code: " + error.getErrorCode() + "\n" +
                                "Error Description: " + error.getDescription() + "\n" +
                                "Line number in error inside the tag: " + error.getLine() + "\n"
                );
            }
        }
    }

    public static XMLGregorianCalendar xmlGregorianCalendar() {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        XMLGregorianCalendar xmlDate = null;
        try {
            xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
        return xmlDate;
    }

}
