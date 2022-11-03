package com.paymentcomponents.swift.mx;

import com.paymentcomponents.swift.mx.cbpr.ParseAndValidateCbprMessage;
import com.paymentcomponents.swift.mx.target2.ParseAndValidateRtgsMessage;

public class Main {

    public static void main(String[] args) {
        ParseAndValidateValidPacs002_12.execute();
        ParseAndValidateInvalidPacs002_12.execute();
        BuildValidPacs002_12.execute();
        ConvertMX2XML.execute();
        ParseAndValidateCbprMessage.execute();
        ParseAndValidateRtgsMessage.execute();
    }

}
