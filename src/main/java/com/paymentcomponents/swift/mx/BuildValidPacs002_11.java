package com.paymentcomponents.swift.mx;

import gr.datamation.mx.message.pacs.FIToFIPaymentStatusReport11;
import gr.datamation.validation.error.ValidationErrorList;
import iso.pacs_002_001_11.GroupHeader91;
import iso.pacs_002_001_11.OriginalGroupHeader17;

public class BuildValidPacs002_11 {

    public static void main(String... args) {
        execute();
    }

    public static void execute() {
        buildPacs002_method1();
        buildPacs002_method2();
    }

    public static void buildPacs002_method1() {
        try {
            System.out.println("Build pacs.002.001.11 using setElement()");
            FIToFIPaymentStatusReport11 messageObject = new FIToFIPaymentStatusReport11();

            // Set elements for GroupHeader
            messageObject.setElement("GrpHdr/MsgId", "MESSAGEID");
            messageObject.setElement("GrpHdr/CreDtTm", Utils.xmlGregorianCalendar());

            // Set elements for OriginalGroupInformation
            messageObject.setElement("OrgnlGrpInfAndSts[0]/OrgnlMsgId", "ORIGINALMESSAGEID");
            messageObject.setElement("OrgnlGrpInfAndSts[0]/OrgnlMsgNmId", "pacs.008.001");
            messageObject.setElement("OrgnlGrpInfAndSts[0]/GrpSts", "ACCP");

            //Use validate() to check the messageObject and validate the content
            //It returns a ValidationErrorList which contains any issue found during validation
            ValidationErrorList errorList = messageObject.validate();
            Utils.printValidMessageOrErrors(messageObject, errorList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void buildPacs002_method2() {
        try {
            System.out.println("Build pacs.002.001.11 using message classes()");
            FIToFIPaymentStatusReport11 messageObject = new FIToFIPaymentStatusReport11();

            // Set elements for GroupHeader
            messageObject.getMessage().setGrpHdr(new GroupHeader91());
            messageObject.getMessage().getGrpHdr().setMsgId("MESSAGEID");
            messageObject.getMessage().getGrpHdr().setCreDtTm(Utils.xmlGregorianCalendar());

            // Set elements for OriginalGroupInformation
            messageObject.getMessage().getOrgnlGrpInfAndSts().add(new OriginalGroupHeader17());
            messageObject.getMessage().getOrgnlGrpInfAndSts().get(0).setOrgnlMsgId("ORIGINALMESSAGEID");
            messageObject.getMessage().getOrgnlGrpInfAndSts().get(0).setOrgnlMsgNmId("pacs.008.001");
            messageObject.getMessage().getOrgnlGrpInfAndSts().get(0).setGrpSts("ACCP");

            //Use validate() to check the messageObject and validate the content
            //It returns a ValidationErrorList which contains any issue found during validation
            ValidationErrorList errorList = messageObject.validate();
            Utils.printValidMessageOrErrors(messageObject, errorList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
