package com.paymentcomponents.swift.mx;

import com.paymentcomponents.swift.mx.cbpr.ParseAndValidateCbprMessage;
import com.paymentcomponents.swift.mx.rtgs.ParseAndValidateRtgsMessage;

public class Main {

    public static void main(String[] args) {
        ParseAndValidateValidPacs002_11.execute();
        ParseAndValidateInvalidPacs002_11.execute();
        BuildValidPacs002_11.execute();
        ConvertMX2XML.execute();
        ParseAndValidateCbprMessage.execute();
        ParseAndValidateRtgsMessage.execute();
    }

}
