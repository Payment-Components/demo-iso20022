package com.paymentcomponents.swift.mx.sepa.epc.ct;

import gr.datamation.iso20022.sepa.epc.ct.pacs.FIToFIPaymentStatusReport10SepaEpcCt;
import gr.datamation.validation.error.ValidationError;
import gr.datamation.validation.error.ValidationErrorList;

import java.io.ByteArrayInputStream;

public class ParseAndValidateSepaEpcCtMessage {

    public static void main(String... args) {
        execute();
    }

    public static void execute() {
        constructSepaEpcCtMessage();
        parseAndValidateSepaEpcCtMessage();
    }

    public static void constructSepaEpcCtMessage() {
        try {
            //Initialize the message object
            FIToFIPaymentStatusReport10SepaEpcCt fIToFIPaymentStatusReport10 = new FIToFIPaymentStatusReport10SepaEpcCt();
            //Optionally use an existing message if we do not want to create the object from scratch
            fIToFIPaymentStatusReport10.parseXML(validSepaEpcCtPacs002String);

            //We fill the elements of the message object using setters
            //fIToFIPaymentStatusReport10.getMessage().setGrpHdr(new GroupHeader93());
            //fIToFIPaymentStatusReport10.getMessage().getGrpHdr().setMsgId("1234");

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

    public static void parseAndValidateSepaEpcCtMessage() {
        try {
            //Initialize the message object
            FIToFIPaymentStatusReport10SepaEpcCt fIToFIPaymentStatusReport10 = new FIToFIPaymentStatusReport10SepaEpcCt();
            //Validate against the xml schema
            ValidationErrorList validationErrorList = fIToFIPaymentStatusReport10.validateXML(new ByteArrayInputStream(validSepaEpcCtPacs002String.getBytes()));
            if (!validationErrorList.isEmpty()) {
                handleValidationError(validationErrorList);
                return;
            }
            //Fill the message with data from xml
            fIToFIPaymentStatusReport10.parseXML(validSepaEpcCtPacs002String);
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

    private static final String validSepaEpcCtPacs002String = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!--Inbound_pacs.002_Sepa_Epc_Ct-->\n" +
            "<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pacs.002.001.10\">\n" +
            "    <FIToFIPmtStsRpt>\n" +
            "        <GrpHdr>\n" +
            "            <MsgId>SCTREJ200020190314300000000001</MsgId>\n" +
            "            <CreDtTm>2019-03-14T00:00:00</CreDtTm>\n" +
            "            <InstdAgt>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>TESTBICA</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </InstdAgt>\n" +
            "        </GrpHdr>\n" +
            "        <OrgnlGrpInfAndSts>\n" +
            "            <OrgnlMsgId>SSSSSS111120190313090033395001</OrgnlMsgId>\n" +
            "            <OrgnlMsgNmId>pacs.008.001.02</OrgnlMsgNmId>\n" +
            "            <GrpSts>PART</GrpSts>\n" +
            "        </OrgnlGrpInfAndSts>\n" +
            "        <TxInfAndSts>\n" +
            "            <StsId>000R907330000001</StsId>\n" +
            "            <OrgnlInstrId>b18332f58ca64ffd87ea9777b9edfba1</OrgnlInstrId>\n" +
            "            <OrgnlEndToEndId>NOTPROVIDED</OrgnlEndToEndId>\n" +
            "            <OrgnlTxId>111T9072000000000000000000000000006</OrgnlTxId>\n" +
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
            "                <Dbtr>\n" +
            "                    <Pty>\n" +
            "                        <Nm>Schneider</Nm>\n" +
            "                        <PstlAdr>\n" +
            "                            <StrtNm>Kuertman Strasse</StrtNm>\n" +
            "                            <BldgNb>45</BldgNb>\n" +
            "                            <PstCd>50475</PstCd>\n" +
            "                            <TwnNm>Koln</TwnNm>\n" +
            "                            <Ctry>DE</Ctry>\n" +
            "                        </PstlAdr>\n" +
            "                    </Pty>\n" +
            "                </Dbtr>\n" +
            "                <DbtrAcct>\n" +
            "                    <Id>\n" +
            "                        <IBAN>ES1011110001087939390799</IBAN>\n" +
            "                    </Id>\n" +
            "                </DbtrAcct>\n" +
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
            "                <Cdtr>\n" +
            "                    <Pty>\n" +
            "                        <Nm>Schneider</Nm>\n" +
            "                        <PstlAdr>\n" +
            "                            <StrtNm>Kuertman Strasse</StrtNm>\n" +
            "                            <BldgNb>45</BldgNb>\n" +
            "                            <PstCd>50475</PstCd>\n" +
            "                            <TwnNm>Koln</TwnNm>\n" +
            "                            <Ctry>DE</Ctry>\n" +
            "                        </PstlAdr>\n" +
            "                    </Pty>\n" +
            "                </Cdtr>\n" +
            "                <CdtrAcct>\n" +
            "                    <Id>\n" +
            "                        <IBAN>ES5410820934549505717622</IBAN>\n" +
            "                    </Id>\n" +
            "                </CdtrAcct>\n" +
            "            </OrgnlTxRef>\n" +
            "        </TxInfAndSts>\n" +
            "    </FIToFIPmtStsRpt>\n" +
            "</Document>\n";

}