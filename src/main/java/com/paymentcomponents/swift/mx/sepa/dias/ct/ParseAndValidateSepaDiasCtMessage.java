package com.paymentcomponents.swift.mx.sepa.dias.ct;

//import gr.datamation.iso20022.sepa.dias.ct.headers.DiasSctFileHeader;
import gr.datamation.iso20022.sepa.dias.ct.pacs.FIToFIPaymentStatusReport10BatchSepaDiasCt;
import gr.datamation.validation.error.ValidationError;
import gr.datamation.validation.error.ValidationErrorList;

import java.io.ByteArrayInputStream;

public class ParseAndValidateSepaDiasCtMessage {

    public static void main(String... args) {
        execute();
    }

    public static void execute() {
        constructSepaDiasCtMessage();
        parseAndValidateSepaDiasCtMessage();
        constructSepaDiasCtHeader();
        parseAndValidateSepaDiasCtHeader();
    }

    public static void constructSepaDiasCtMessage() {
        try {
            //Initialize the message object
            FIToFIPaymentStatusReport10BatchSepaDiasCt fIToFIPaymentStatusReport10 = new FIToFIPaymentStatusReport10BatchSepaDiasCt();
            //Optionally use an existing message if we do not want to create the object from scratch
            fIToFIPaymentStatusReport10.parseXML(validSepaDiasCtPacs002String);

            //We fill the elements of the message object using setters
            //fIToFIPaymentStatusReport10.getMessage().setGrpHdr(new GroupHeader91());
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

    public static void parseAndValidateSepaDiasCtMessage() {
        try {
            //Initialize the message object
            FIToFIPaymentStatusReport10BatchSepaDiasCt fIToFIPaymentStatusReport10 = new FIToFIPaymentStatusReport10BatchSepaDiasCt();
            //Validate against the xml schema
            ValidationErrorList validationErrorList = fIToFIPaymentStatusReport10.validateXML(new ByteArrayInputStream(validSepaDiasCtPacs002String.getBytes()));
            if (!validationErrorList.isEmpty()) {
                handleValidationError(validationErrorList);
                return;
            }
            //Fill the message with data from xml
            fIToFIPaymentStatusReport10.parseXML(validSepaDiasCtPacs002String);
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

    //YOU NEED THE FULL VERSION TO RUN BELOW METHODS FOR HEADER
    public static void constructSepaDiasCtHeader() {
//        try {
//            //Initialize the message object
//            DiasSctFileHeader diasHeader = new DiasSctFileHeader();
//            //Optionally use an existing message if we do not want to create the object from scratch
//            diasHeader.parseXML(validSepaDiasCtHeaderString);
//
//            //We fill the elements of the message object using setters
//            //diasHeader.getMessage().setFileCycleNo("123")
//
//            //or setElement()
////            diasHeader.setElement("FileCycleNo", "123");
//
////            In case we already have a message object, we can set it to the header
//            FIToFIPaymentStatusReport10BatchSepaDiasCt fIToFIPaymentStatusReport10 = new FIToFIPaymentStatusReport10BatchSepaDiasCt();
//            fIToFIPaymentStatusReport10.parseXML(validSepaDiasCtPacs002String);
//            diasHeader.getMessage().getFIToFIPmtStsRpt().add(fIToFIPaymentStatusReport10.getMessage());
//
//            //Perform validation
//            ValidationErrorList validationErrorList = diasHeader.validate();
//
//            if (validationErrorList.isEmpty()) {
//                System.out.println("Message is valid");
//                System.out.println(diasHeader.convertToXML()); //Get the generated xml
//            } else {
//                handleValidationError(validationErrorList);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.err.println(e.getMessage());
//        }
    }
//
    public static void parseAndValidateSepaDiasCtHeader() {
//        try {
//            //Initialize the message object
//            DiasSctFileHeader diasHeader = new DiasSctFileHeader();
//            //Validate against the xml schema
//            ValidationErrorList validationErrorList = diasHeader.validateXML(new ByteArrayInputStream(validSepaDiasCtHeaderString.getBytes()));
//            if (!validationErrorList.isEmpty()) {
//                handleValidationError(validationErrorList);
//                return;
//            }
//            //Fill the message with data from xml
//            diasHeader.parseXML(validSepaDiasCtHeaderString);
//            //Validate both the xml schema and rules
//            validationErrorList.addAll(diasHeader.validate());
//
//            if (validationErrorList.isEmpty()) {
//                System.out.println("Message is valid");
//                System.out.println(diasHeader.convertToXML()); //Get the generated xml
//            } else {
//                handleValidationError(validationErrorList);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.err.println(e.getMessage());
//        }
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

    private static final String validSepaDiasCtPacs002String = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pacs.002.001.10\">\n" +
            "    <FIToFIPmtStsRpt>\n" +
            "        <GrpHdr>\n" +
            "            <MsgId>FID0000000870542_MSG0</MsgId>\n" +
            "            <CreDtTm>2023-06-21T13:17:24.568+03:00</CreDtTm>\n" +
            "            <InstdAgt><FinInstnId><BICFI>ABCDGRA0</BICFI></FinInstnId></InstdAgt>\n" +
            "        </GrpHdr>\n" +
            "        <OrgnlGrpInfAndSts>\n" +
            "            <OrgnlMsgId>2023062101600002</OrgnlMsgId>\n" +
            "            <OrgnlMsgNmId>pacs.008.001.08</OrgnlMsgNmId>\n" +
            "        </OrgnlGrpInfAndSts>\n" +
            "        <TxInfAndSts>\n" +
            "            <StsId>18170100000000</StsId>\n" +
            "            <OrgnlInstrId>From016To014-Cred-rej</OrgnlInstrId>\n" +
            "            <OrgnlEndToEndId>From016To014-Cred-rej</OrgnlEndToEndId>\n" +
            "            <OrgnlTxId>From016To014-Cred-rej</OrgnlTxId>\n" +
            "            <TxSts>RJCT</TxSts>\n" +
            "            <StsRsnInf>\n" +
            "                <Orgtr><Id><OrgId><AnyBIC>DIASGRA1</AnyBIC></OrgId></Id></Orgtr>\n" +
            "                <Rsn><Prtry>X010</Prtry></Rsn>\n" +
            "                <AddtlInf>Invalid ChargeBearer</AddtlInf>\n" +
            "            </StsRsnInf>\n" +
            "            <OrgnlTxRef>\n" +
            "                <IntrBkSttlmAmt Ccy=\"EUR\">36.00</IntrBkSttlmAmt>\n" +
            "                <IntrBkSttlmDt>2023-06-01</IntrBkSttlmDt>\n" +
            "                <SttlmInf><SttlmMtd>CLRG</SttlmMtd></SttlmInf>\n" +
            "                <PmtTpInf><SvcLvl><Cd>SEPA</Cd></SvcLvl><LclInstrm><Prtry>SCT</Prtry></LclInstrm><CtgyPurp><Cd>CBLK</Cd></CtgyPurp></PmtTpInf>\n" +
            "                <RmtInf><Ustrd>new message for ISO2019</Ustrd></RmtInf>\n" +
            "                <UltmtDbtr><Pty><Nm>Ultimate Debtor Test 1</Nm><Id><PrvtId><Othr><Id>AX035662</Id><SchmeNm><Cd>NIDN</Cd></SchmeNm></Othr></PrvtId></Id></Pty></UltmtDbtr>\n" +
            "                <Dbtr><Pty><Nm>Debtor Name 1</Nm><PstlAdr><Ctry>GR</Ctry><AdrLine>Address Line 1</AdrLine></PstlAdr><Id><OrgId><LEI>984510B3901DEA58OD34</LEI></OrgId></Id></Pty></Dbtr>\n" +
            "                <DbtrAcct><Id><IBAN>GR0899999990000000000000001</IBAN></Id><Prxy><Tp><Cd>PERS</Cd></Tp><Id>Identification</Id></Prxy></DbtrAcct>\n" +
            "                <DbtrAgt><FinInstnId><BICFI>ABCDGRA0</BICFI></FinInstnId><BrnchId><Id>9991</Id></BrnchId></DbtrAgt>\n" +
            "                <CdtrAgt><FinInstnId><BICFI>BCDAGRA0</BICFI></FinInstnId></CdtrAgt>\n" +
            "                <Cdtr><Pty><Nm>Creditor Name 1</Nm><PstlAdr><Ctry>GR</Ctry><AdrLine>Creditor Address Line 1</AdrLine></PstlAdr><Id><OrgId><Othr><Id>CUST</Id><SchmeNm><Prtry>12345678</Prtry></SchmeNm></Othr></OrgId></Id></Pty></Cdtr>\n" +
            "                <CdtrAcct><Id><IBAN>GR4999999990000000123456789</IBAN></Id></CdtrAcct>\n" +
            "                <UltmtCdtr><Pty><Nm>Ultimate Creditor Test 1</Nm><Id><PrvtId><Othr><Id>146378691</Id><SchmeNm><Cd>TXID</Cd></SchmeNm></Othr></PrvtId></Id></Pty></UltmtCdtr>\n" +
            "                <Purp><Cd>BENE</Cd></Purp>\n" +
            "            </OrgnlTxRef>\n" +
            "        </TxInfAndSts>\n" +
            "    </FIToFIPmtStsRpt>\n" +
            "</Document>";


    private static final String validSepaDiasCtHeaderString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<fh:DIASSCTFH xmlns:fh=\"urn:DSCTfh:xsd:$DIASSCTFH\">\n" +
            "    <fh:DIASFileHdr>\n" +
            "        <fh:SndgInst>DIASGRA1</fh:SndgInst>\n" +
            "        <fh:RcvgInst>BCDAGRA0</fh:RcvgInst>\n" +
            "        <fh:FileRef>0000000000870301</fh:FileRef>\n" +
            "        <fh:SrvcID>DCT</fh:SrvcID>\n" +
            "        <fh:TstCode>T</fh:TstCode>\n" +
            "        <fh:FType>XCT</fh:FType>\n" +
            "        <fh:NumGrp>1</fh:NumGrp>\n" +
            "    </fh:DIASFileHdr>\n" +
            "    <fh:FIToFIPmtStsRpt xmlns=\"urn:iso:std:iso:20022:tech:xsd:pacs.002.001.10\">\n" +
            "        <GrpHdr>\n" +
            "            <MsgId>FID0000000870542_MSG0</MsgId>\n" +
            "            <CreDtTm>2023-06-21T13:17:24.568+03:00</CreDtTm>\n" +
            "            <InstdAgt><FinInstnId><BICFI>ABCDGRA0</BICFI></FinInstnId></InstdAgt>\n" +
            "        </GrpHdr>\n" +
            "        <OrgnlGrpInfAndSts>\n" +
            "            <OrgnlMsgId>2023062101600002</OrgnlMsgId>\n" +
            "            <OrgnlMsgNmId>pacs.008.001.08</OrgnlMsgNmId>\n" +
            "        </OrgnlGrpInfAndSts>\n" +
            "        <TxInfAndSts>\n" +
            "            <StsId>18170100000000</StsId>\n" +
            "            <OrgnlInstrId>From016To014-Cred-rej</OrgnlInstrId>\n" +
            "            <OrgnlEndToEndId>From016To014-Cred-rej</OrgnlEndToEndId>\n" +
            "            <OrgnlTxId>From016To014-Cred-rej</OrgnlTxId>\n" +
            "            <TxSts>RJCT</TxSts>\n" +
            "            <StsRsnInf>\n" +
            "                <Orgtr><Id><OrgId><AnyBIC>DIASGRA1</AnyBIC></OrgId></Id></Orgtr>\n" +
            "                <Rsn><Prtry>X010</Prtry></Rsn>\n" +
            "                <AddtlInf>Invalid ChargeBearer</AddtlInf>\n" +
            "            </StsRsnInf>\n" +
            "            <OrgnlTxRef>\n" +
            "                <IntrBkSttlmAmt Ccy=\"EUR\">36.00</IntrBkSttlmAmt>\n" +
            "                <IntrBkSttlmDt>2023-06-01</IntrBkSttlmDt>\n" +
            "                <SttlmInf><SttlmMtd>CLRG</SttlmMtd></SttlmInf>\n" +
            "                <PmtTpInf><SvcLvl><Cd>SEPA</Cd></SvcLvl><LclInstrm><Prtry>SCT</Prtry></LclInstrm><CtgyPurp><Cd>CBLK</Cd></CtgyPurp></PmtTpInf>\n" +
            "                <RmtInf><Ustrd>new message for ISO2019</Ustrd></RmtInf>\n" +
            "                <UltmtDbtr><Pty><Nm>Ultimate Debtor Test 1</Nm><Id><PrvtId><Othr><Id>AX035662</Id><SchmeNm><Cd>NIDN</Cd></SchmeNm></Othr></PrvtId></Id></Pty></UltmtDbtr>\n" +
            "                <Dbtr><Pty><Nm>Debtor Name 1</Nm><PstlAdr><Ctry>GR</Ctry><AdrLine>Address Line 1</AdrLine></PstlAdr><Id><OrgId><LEI>984510B3901DEA58OD34</LEI></OrgId></Id></Pty></Dbtr>\n" +
            "                <DbtrAcct><Id><IBAN>GR0899999990000000000000001</IBAN></Id><Prxy><Tp><Cd>PERS</Cd></Tp><Id>Identification</Id></Prxy></DbtrAcct>\n" +
            "                <DbtrAgt><FinInstnId><BICFI>ABCDGRA0</BICFI></FinInstnId><BrnchId><Id>9991</Id></BrnchId></DbtrAgt>\n" +
            "                <CdtrAgt><FinInstnId><BICFI>BCDAGRA0</BICFI></FinInstnId></CdtrAgt>\n" +
            "                <Cdtr><Pty><Nm>Creditor Name 1</Nm><PstlAdr><Ctry>GR</Ctry><AdrLine>Creditor Address Line 1</AdrLine></PstlAdr><Id><OrgId><Othr><Id>CUST</Id><SchmeNm><Prtry>12345678</Prtry></SchmeNm></Othr></OrgId></Id></Pty></Cdtr>\n" +
            "                <CdtrAcct><Id><IBAN>GR4999999990000000123456789</IBAN></Id></CdtrAcct>\n" +
            "                <UltmtCdtr><Pty><Nm>Ultimate Creditor Test 1</Nm><Id><PrvtId><Othr><Id>146378691</Id><SchmeNm><Cd>TXID</Cd></SchmeNm></Othr></PrvtId></Id></Pty></UltmtCdtr>\n" +
            "                <Purp><Cd>BENE</Cd></Purp>\n" +
            "            </OrgnlTxRef>\n" +
            "        </TxInfAndSts>\n" +
            "    </fh:FIToFIPmtStsRpt></fh:DIASSCTFH>";

}