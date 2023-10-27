package com.paymentcomponents.swift.mx.sepa.sibs.dd;

import gr.datamation.iso20022.sepa.sibs.dd.headers.MPEDDCdfBulkDirectDebit;
import gr.datamation.iso20022.sepa.sibs.dd.pacs.FIToFIPaymentStatusReport10S2SepaSibsDd;
import gr.datamation.validation.error.ValidationError;
import gr.datamation.validation.error.ValidationErrorList;

import java.io.ByteArrayInputStream;

public class ParseAndValidateSepaSibsDdMessage {
    public static void main(String... args) {
        execute();
    }

    public static void execute() {
        constructSepaSibsDdMessage();
        parseAndValidateSepaSibsDdMessage();
        constructSepaSibsDdHeader();
        parseAndValidateSepaSibsDdHeader();
    }

    public static void constructSepaSibsDdMessage() {
        try {
            //Initialize the message object
            FIToFIPaymentStatusReport10S2SepaSibsDd fIToFIPaymentStatusReport10 = new FIToFIPaymentStatusReport10S2SepaSibsDd();
            //Optionally use an existing message if we do not want to create the object from scratch
            fIToFIPaymentStatusReport10.parseXML(validSepaSibsDdPacs002String);

            //We fill the elements with the message object using setters
//            fIToFIPaymentStatusReport10.getMessage().setGrpHdr(new SDdGroupHeader91());
//            fIToFIPaymentStatusReport10.getMessage().getGrpHdr().setMsgId("1234");

            //or setElement()
            //fIToFIPaymentStatusReport10.setElement("GrpHdr/MsgId", "1234");

            //Perform validation
            ValidationErrorList validationErrorList = fIToFIPaymentStatusReport10.validate();

            if (validationErrorList.isEmpty()) {
                System.out.println("Message is valid");
                System.out.println(fIToFIPaymentStatusReport10.convertToXML()); //Get the generated xml
            } else {
                handleValidationError(validationErrorList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }

    public static void parseAndValidateSepaSibsDdMessage() {
        try {
            //Initialize the message object
            FIToFIPaymentStatusReport10S2SepaSibsDd fIToFIPaymentStatusReport10 = new FIToFIPaymentStatusReport10S2SepaSibsDd();
            //Validate against the xml schema
            ValidationErrorList validationErrorList = fIToFIPaymentStatusReport10.validateXML(new ByteArrayInputStream(validSepaSibsDdPacs002String.getBytes()));
            if (!validationErrorList.isEmpty()) {
                handleValidationError(validationErrorList);
                return;
            }
            //Fill the message with data from xml
            fIToFIPaymentStatusReport10.parseXML(validSepaSibsDdPacs002String);
            //Validate both the xml schema and rules
            validationErrorList.addAll(fIToFIPaymentStatusReport10.validate());

            if (validationErrorList.isEmpty()) {
                System.out.println("Message is valid");
                System.out.println(fIToFIPaymentStatusReport10.convertToXML()); //Get the generated xml
            } else {
                handleValidationError(validationErrorList);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }

    public static void constructSepaSibsDdHeader() {
        try {
            //Initialize the message object
            MPEDDCdfBulkDirectDebit cvfHeader = new MPEDDCdfBulkDirectDebit();
            //Optionally use an existing message if we do not want to create the object from scratch
            cvfHeader.parseXML(validSepaSibsDdCdfHeaderString);

            //We fill the elements with the message object using setters
            //cvfHeader.getMessage().setFileCycleNo("123")

            //or setElement()
//            cvfHeader.setElement("FileCycleNo", "123");

//            In case we already have a message object, we can set it to the header
            FIToFIPaymentStatusReport10S2SepaSibsDd fIToFIPaymentStatusReport10 = new FIToFIPaymentStatusReport10S2SepaSibsDd();
            fIToFIPaymentStatusReport10.parseXML(validSepaSibsDdPacs002String);
            cvfHeader.getMessage().getFIToFIPmtStsRptS2().add(fIToFIPaymentStatusReport10.getMessage());

            //Perform validation
            ValidationErrorList validationErrorList = cvfHeader.validate();

            if (validationErrorList.isEmpty()) {
                System.out.println("Message is valid");
                System.out.println(cvfHeader.convertToXML()); //Get the generated xml
            } else {
                handleValidationError(validationErrorList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }

    public static void parseAndValidateSepaSibsDdHeader() {
        try {
            //Initialize the message object
            MPEDDCdfBulkDirectDebit cdfHeader = new MPEDDCdfBulkDirectDebit();
            //Validate against the xml schema
            ValidationErrorList validationErrorList = cdfHeader.validateXML(new ByteArrayInputStream(validSepaSibsDdCdfHeaderString.getBytes()));
            if (!validationErrorList.isEmpty()) {
                handleValidationError(validationErrorList);
                return;
            }
            //Fill the message with data from xml
            cdfHeader.parseXML(validSepaSibsDdCdfHeaderString);
            //Validate both the xml schema and rules
            validationErrorList.addAll(cdfHeader.validate());

            if (validationErrorList.isEmpty()) {
                System.out.println("Message is valid");
                System.out.println(cdfHeader.convertToXML()); //Get the generated xml
            } else {
                handleValidationError(validationErrorList);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }

    private static void handleValidationError(ValidationErrorList validationErrorList) {
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

    private static final String validSepaSibsDdPacs002String = "<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pacs.002.001.10S2\">\n" +
            "    <FIToFIPmtStsRptS2>\n" +
            "        <GrpHdr>\n" +
            "            <MsgId>SCTREJ200020190314300000000001</MsgId>\n" +
            "            <CreDtTm>2019-03-14T00:00:00</CreDtTm>\n" +
            "        </GrpHdr>\n" +
            "        <OrgnlGrpInfAndSts>\n" +
            "            <OrgnlMsgId>SSSSSS111120190313090033395001</OrgnlMsgId>\n" +
            "            <OrgnlMsgNmId>pacs.003.001.08</OrgnlMsgNmId>\n" +
            "            <OrgnlNbOfTxs>1</OrgnlNbOfTxs>\n" +
            "            <OrgnlCtrlSum>5.0</OrgnlCtrlSum>\n" +
            "            <GrpSts>RJCT</GrpSts>\n" +
            "            <StsRsnInf>\n" +
            "                <Orgtr>\n" +
            "                    <Id>\n" +
            "                        <OrgId>\n" +
            "                            <AnyBIC>TESTBICB</AnyBIC>\n" +
            "                        </OrgId>\n" +
            "                    </Id>\n" +
            "                </Orgtr>\n" +
            "                <Rsn>\n" +
            "                    <Cd>AC01</Cd>\n" +
            "                </Rsn>\n" +
            "            </StsRsnInf>\n" +
            "        </OrgnlGrpInfAndSts>\n" +
            "        <TxInfAndSts>\n" +
            "            <StsId>000R907330000001</StsId>\n" +
            "            <OrgnlInstrId>b18332f58ca64ffd87ea9777b9edfba1</OrgnlInstrId>\n" +
            "            <OrgnlEndToEndId>NOTPROVIDED</OrgnlEndToEndId>\n" +
            "            <OrgnlTxId>111T9072000000000000000000000000006</OrgnlTxId>\n" +
            "            <TxSts>RJCT</TxSts>\n" +
            "            <StsRsnInf>\n" +
            "                <Orgtr>\n" +
            "                    <Id>\n" +
            "                        <OrgId>\n" +
            "                            <AnyBIC>TESTBICB</AnyBIC>\n" +
            "                        </OrgId>\n" +
            "                    </Id>\n" +
            "                </Orgtr>\n" +
            "                <Rsn>\n" +
            "                    <Cd>AC01</Cd>\n" +
            "                </Rsn>\n" +
            "            </StsRsnInf>\n" +
            "            <OrgnlTxRef>\n" +
            "                <IntrBkSttlmAmt Ccy=\"EUR\">9.95</IntrBkSttlmAmt>\n" +
            "                <IntrBkSttlmDt>2023-09-28</IntrBkSttlmDt>\n" +
            "                <DbtrAgt>\n" +
            "                    <FinInstnId>\n" +
            "                        <BICFI>TESTBICA</BICFI>\n" +
            "                    </FinInstnId>\n" +
            "                </DbtrAgt>\n" +
            "                <CdtrAgt>\n" +
            "                    <FinInstnId>\n" +
            "                        <BICFI>TESTBICB</BICFI>\n" +
            "                    </FinInstnId>\n" +
            "                </CdtrAgt>\n" +
            "            </OrgnlTxRef>\n" +
            "        </TxInfAndSts>\n" +
            "    </FIToFIPmtStsRptS2>\n" +
            "</Document>";

    private static final String validSepaSibsDdCdfHeaderString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<S2SDDCdf:MPEDDCdfBlkDirDeb xmlns:S2SDDCdf=\"urn:S2SDDCdf:xsd:$MPEDDCdfBlkDirDeb\">\n" +
            "    <S2SDDCdf:SndgInst>TESTBICA</S2SDDCdf:SndgInst>\n" +
            "    <S2SDDCdf:RcvgInst>TESTBICB</S2SDDCdf:RcvgInst>\n" +
            "    <S2SDDCdf:ClrSys>ST2</S2SDDCdf:ClrSys>\n" +
            "    <S2SDDCdf:SrvcId>B2B</S2SDDCdf:SrvcId>\n" +
            "    <S2SDDCdf:TstCode>T</S2SDDCdf:TstCode>\n" +
            "    <S2SDDCdf:FType>CDX</S2SDDCdf:FType>\n" +
            "    <S2SDDCdf:FileRef>string</S2SDDCdf:FileRef>\n" +
            "    <S2SDDCdf:FileBusDt>2011-10-24+03:00</S2SDDCdf:FileBusDt>\n" +
            "    <S2SDDCdf:FileCycleNo>01</S2SDDCdf:FileCycleNo>\n" +
            "    <S2SDDCdf:FIToFIPmtStsRptS2 xmlns=\"urn:iso:std:iso:20022:tech:xsd:pacs.002.001.10S2\">\n" +
            "        <GrpHdr>\n" +
            "            <MsgId>SCTREJ200020190314300000000001</MsgId>\n" +
            "            <CreDtTm>2019-03-14T00:00:00</CreDtTm>\n" +
            "        </GrpHdr>\n" +
            "        <OrgnlGrpInfAndSts>\n" +
            "            <OrgnlMsgId>SSSSSS111120190313090033395001</OrgnlMsgId>\n" +
            "            <OrgnlMsgNmId>pacs.003.001.08</OrgnlMsgNmId>\n" +
            "            <OrgnlNbOfTxs>1</OrgnlNbOfTxs>\n" +
            "            <OrgnlCtrlSum>5.0</OrgnlCtrlSum>\n" +
            "            <GrpSts>RJCT</GrpSts>\n" +
            "            <StsRsnInf>\n" +
            "                <Orgtr>\n" +
            "                    <Id>\n" +
            "                        <OrgId>\n" +
            "                            <AnyBIC>TESTBICB</AnyBIC>\n" +
            "                        </OrgId>\n" +
            "                    </Id>\n" +
            "                </Orgtr>\n" +
            "                <Rsn>\n" +
            "                    <Cd>AC01</Cd>\n" +
            "                </Rsn>\n" +
            "            </StsRsnInf>\n" +
            "        </OrgnlGrpInfAndSts>\n" +
            "        <TxInfAndSts>\n" +
            "            <StsId>000R907330000001</StsId>\n" +
            "            <OrgnlInstrId>b18332f58ca64ffd87ea9777b9edfba1</OrgnlInstrId>\n" +
            "            <OrgnlEndToEndId>NOTPROVIDED</OrgnlEndToEndId>\n" +
            "            <OrgnlTxId>111T9072000000000000000000000000006</OrgnlTxId>\n" +
            "            <TxSts>RJCT</TxSts>\n" +
            "            <StsRsnInf>\n" +
            "                <Orgtr>\n" +
            "                    <Id>\n" +
            "                        <OrgId>\n" +
            "                            <AnyBIC>TESTBICB</AnyBIC>\n" +
            "                        </OrgId>\n" +
            "                    </Id>\n" +
            "                </Orgtr>\n" +
            "                <Rsn>\n" +
            "                    <Cd>AC01</Cd>\n" +
            "                </Rsn>\n" +
            "            </StsRsnInf>\n" +
            "            <OrgnlTxRef>\n" +
            "                <IntrBkSttlmAmt Ccy=\"EUR\">9.95</IntrBkSttlmAmt>\n" +
            "                <IntrBkSttlmDt>2023-09-28</IntrBkSttlmDt>\n" +
            "                <DbtrAgt>\n" +
            "                    <FinInstnId>\n" +
            "                        <BICFI>TESTBICA</BICFI>\n" +
            "                    </FinInstnId>\n" +
            "                </DbtrAgt>\n" +
            "                <CdtrAgt>\n" +
            "                    <FinInstnId>\n" +
            "                        <BICFI>TESTBICB</BICFI>\n" +
            "                    </FinInstnId>\n" +
            "                </CdtrAgt>\n" +
            "            </OrgnlTxRef>\n" +
            "        </TxInfAndSts>\n" +
            "    </S2SDDCdf:FIToFIPmtStsRptS2>\n" +
            "</S2SDDCdf:MPEDDCdfBlkDirDeb>";
}
