package com.paymentcomponents.swift.mx.sepa.eba.ct;

import gr.datamation.iso20022.sepa.eba.ct.headers.CvfBulkCreditTransferSepaEbaCt;
import gr.datamation.iso20022.sepa.eba.ct.pacs.FIToFIPaymentStatusReport10SepaEbaCt;
import gr.datamation.validation.error.ValidationError;
import gr.datamation.validation.error.ValidationErrorList;

import java.io.ByteArrayInputStream;

public class ParseAndValidateSepaEbaCtMessage {

    public static void main(String... args) {
        execute();
    }

    public static void execute() {
        constructSepaEbaCtMessage();
        parseAndValidateSepaEbaCtMessage();
        constructSepaEbaCtHeader();
        parseAndValidateSepaEbaCtHeader();
    }

    public static void constructSepaEbaCtMessage() {
        try {
            //Initialize the message object
            FIToFIPaymentStatusReport10SepaEbaCt fIToFIPaymentStatusReport10 = new FIToFIPaymentStatusReport10SepaEbaCt();
            //Optionally use an existing message if we do not want to create the object from scratch
            fIToFIPaymentStatusReport10.parseXML(validSepaEbaCtPacs002String);

            //We fill the elements of the message object using setters
            //fIToFIPaymentStatusReport10.getMessage().setGrpHdr(new SCTGroupHeader91());
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

    public static void parseAndValidateSepaEbaCtMessage() {
        try {
            //Initialize the message object
            FIToFIPaymentStatusReport10SepaEbaCt fIToFIPaymentStatusReport10 = new FIToFIPaymentStatusReport10SepaEbaCt();
            //Validate against the xml schema
            ValidationErrorList validationErrorList = fIToFIPaymentStatusReport10.validateXML(new ByteArrayInputStream(validSepaEbaCtPacs002String.getBytes()));
            if (!validationErrorList.isEmpty()) {
                handleValidationError(validationErrorList);
                return;
            }
            //Fill the message with data from xml
            fIToFIPaymentStatusReport10.parseXML(validSepaEbaCtPacs002String);
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

    public static void constructSepaEbaCtHeader() {
        try {
            //Initialize the message object
            CvfBulkCreditTransferSepaEbaCt cvfHeader = new CvfBulkCreditTransferSepaEbaCt();
            //Optionally use an existing message if we do not want to create the object from scratch
            cvfHeader.parseXML(validSepaEbaCtCvfHeaderString);

            //We fill the elements of the message object using setters
            //cvfHeader.getMessage().setFileCycleNo("123")

            //or setElement()
//            cvfHeader.setElement("FileCycleNo", "123");

//            In case we already have a message object, we can set it to the header
            FIToFIPaymentStatusReport10SepaEbaCt fIToFIPaymentStatusReport10 = new FIToFIPaymentStatusReport10SepaEbaCt();
            fIToFIPaymentStatusReport10.parseXML(validSepaEbaCtPacs002String);
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

    public static void parseAndValidateSepaEbaCtHeader() {
        try {
            //Initialize the message object
            CvfBulkCreditTransferSepaEbaCt cvfHeader = new CvfBulkCreditTransferSepaEbaCt();
            //Validate against the xml schema
            ValidationErrorList validationErrorList = cvfHeader.validateXML(new ByteArrayInputStream(validSepaEbaCtCvfHeaderString.getBytes()));
            if (!validationErrorList.isEmpty()) {
                handleValidationError(validationErrorList);
                return;
            }
            //Fill the message with data from xml
            cvfHeader.parseXML(validSepaEbaCtCvfHeaderString);
            //Validate both the xml schema and rules
            validationErrorList.addAll(cvfHeader.validate());

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

    private static final String validSepaEbaCtPacs002String = "<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pacs.002.001.10S2\">\n" +
            "    <FIToFIPmtStsRpt>\n" +
            "        <GrpHdr>\n" +
            "            <MsgId>SCTREJ200020190314300000000001</MsgId>\n" +
            "            <CreDtTm>2019-03-14T00:00:00</CreDtTm>\n" +
            "        </GrpHdr>\n" +
            "        <OrgnlGrpInfAndSts>\n" +
            "            <OrgnlMsgId>SSSSSS111120190313090033395001</OrgnlMsgId>\n" +
            "            <OrgnlMsgNmId>pacs.008.001.02</OrgnlMsgNmId>\n" +
            "            <OrgnlNbOfTxs>1</OrgnlNbOfTxs>\n" +
            "            <OrgnlCtrlSum>1</OrgnlCtrlSum>\n" +
            "            <GrpSts>PART</GrpSts>\n" +
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
            "            <TxSts>str1</TxSts>\n" +
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
            "                <IntrBkSttlmDt>2015-09-28</IntrBkSttlmDt>\n" +
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
            "    </FIToFIPmtStsRpt>\n" +
            "</Document>";


    private static final String validSepaEbaCtCvfHeaderString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<S2SCTCvf:SCTCvfBlkCredTrf xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
            "                           xmlns:S2SCTCvf=\"urn:S2SCTCvf:xsd:$SCTCvfBlkCredTrf\"\n" +
            "                           xsi:schemaLocation=\"urn:S2SCTCvf:xsd:$SCTCvfBlkCredTrf SCTCvfBlkCredTrf.xsd\"\n" +
            ">\n" +
            "    <S2SCTCvf:SndgInst>TESTBICB</S2SCTCvf:SndgInst>\n" +
            "    <S2SCTCvf:RcvgInst>TESTBICA</S2SCTCvf:RcvgInst>\n" +
            "    <S2SCTCvf:SrvcId>SCT</S2SCTCvf:SrvcId>\n" +
            "    <S2SCTCvf:TstCode>T</S2SCTCvf:TstCode>\n" +
            "    <S2SCTCvf:FType>CVF</S2SCTCvf:FType>\n" +
            "    <S2SCTCvf:FileRef>2018111800000001</S2SCTCvf:FileRef>\n" +
            "    <S2SCTCvf:FileDtTm>2018-11-17T00:00:00</S2SCTCvf:FileDtTm>\n" +
            "    <S2SCTCvf:OrigFRef>123456</S2SCTCvf:OrigFRef>\n" +
            "    <S2SCTCvf:OrigFName>Fname</S2SCTCvf:OrigFName>\n" +
            "    <S2SCTCvf:OrigDtTm>2018-11-17T00:00:00</S2SCTCvf:OrigDtTm>\n" +
            "    <S2SCTCvf:FileRjctRsn>ASD</S2SCTCvf:FileRjctRsn>\n" +
            "    <S2SCTCvf:FileBusDt>2018-11-18</S2SCTCvf:FileBusDt>\n" +
            "    <S2SCTCvf:FileCycleNo>01</S2SCTCvf:FileCycleNo>\n" +
            "    <S2SCTCvf:FIToFIPmtStsRptS2 xmlns=\"urn:iso:std:iso:20022:tech:xsd:pacs.002.001.10S2\">\n" +
            "        <GrpHdr>\n" +
            "            <MsgId>SCTREJ200020190314300000000001</MsgId>\n" +
            "            <CreDtTm>2019-03-14T00:00:00</CreDtTm>\n" +
            "        </GrpHdr>\n" +
            "        <OrgnlGrpInfAndSts>\n" +
            "            <OrgnlMsgId>SSSSSS111120190313090033395001</OrgnlMsgId>\n" +
            "            <OrgnlMsgNmId>pacs.008.001.02</OrgnlMsgNmId>\n" +
            "            <OrgnlNbOfTxs>1</OrgnlNbOfTxs>\n" +
            "            <OrgnlCtrlSum>1</OrgnlCtrlSum>\n" +
            "            <GrpSts>PART</GrpSts>\n" +
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
            "            <TxSts>str1</TxSts>\n" +
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
            "                <IntrBkSttlmDt>2015-09-28</IntrBkSttlmDt>\n" +
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
            "    </S2SCTCvf:FIToFIPmtStsRptS2>\n" +
            "</S2SCTCvf:SCTCvfBlkCredTrf>\n";

}