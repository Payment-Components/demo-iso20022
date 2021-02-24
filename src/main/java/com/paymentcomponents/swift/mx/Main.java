package com.paymentcomponents.swift.mx;

import com.paymentcomponents.swift.mx.cbpr.ParseAndValidateCbprMessage;
import com.paymentcomponents.swift.mx.rtgs.ParseAndValidateRtgsMessage;

public class Main {

    public static void main(String[] args) {
        ParseValidPacs002_11.execute();
        ParseInvalidPacs002_11.execute();
        BuildValidPacs002_11.execute();
        ConvertMX2XML.execute();
        ParseAndValidateCbprMessage.execute();
        ParseAndValidateRtgsMessage.execute();
    }

}
