package com.paymentcomponents.swift.mx;

import gr.datamation.mx.Message;
import gr.datamation.mx.message.pacs.FIToFIPaymentStatusReport11;
import gr.datamation.validation.error.ValidationErrorList;

import java.io.ByteArrayInputStream;

public class ParseAndValidateValidPacs002_11 {

    public static void main(String... args) {
        execute();
    }

    public static void execute() {
        //You have to initiate the message object using the suitable constructor.
        //In order to parse and validate a pacs.002.001.11 you need to use FIToFIPaymentStatusReport11
        //FIToFIPaymentStatusReport > matches the xml element <FIToFIPmtStsRpt>
        //11 > the version of the pacs.002.001.VERSION which can be found in xmlns attribute of the xml
        Message messageObject = new FIToFIPaymentStatusReport11();
        try {
            //Use validateXML() to validate the xml schema of the message
            ValidationErrorList errorList = messageObject.validateXML(new ByteArrayInputStream(validPacs002String.getBytes()));
            if(!errorList.isEmpty()) {
                Utils.printInvalidMessageErrors(errorList);
                return;
            }
            //Use parseXML() to fill the messageObject the content of the message
            messageObject.parseXML(validPacs002String);
            //Use validate() to check the messageObject and validate the content
            //It returns a ValidationErrorList which contains any issue found during validation
            errorList = messageObject.validate();
            Utils.printValidMessageOrErrors(messageObject, errorList);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Message cannot be parsed");
        }
    }

    private static final String validPacs002String = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pacs.002.001.11\">\n" +
            "  <FIToFIPmtStsRpt>\n" +
            "    <GrpHdr>\n" +
            "      <MsgId>ABABUS23-STATUS-456/04</MsgId>\n" +
            "      <CreDtTm>2015-06-29T09:56:00</CreDtTm>\n" +
            "      <InstgAgt>\n" +
            "        <FinInstnId>\n" +
            "          <BICFI>ABABUS23</BICFI>\n" +
            "        </FinInstnId>\n" +
            "      </InstgAgt>\n" +
            "      <InstdAgt>\n" +
            "        <FinInstnId>\n" +
            "          <BICFI>AAAAUS29</BICFI>\n" +
            "        </FinInstnId>\n" +
            "      </InstdAgt>\n" +
            "    </GrpHdr>\n" +
            "    <OrgnlGrpInfAndSts>\n" +
            "      <OrgnlMsgId>AAAA100628-123v</OrgnlMsgId>\n" +
            "      <OrgnlMsgNmId>pacs.003.001.08</OrgnlMsgNmId>\n" +
            "      <OrgnlCreDtTm>2015-06-28T10:05:00</OrgnlCreDtTm>\n" +
            "    </OrgnlGrpInfAndSts>\n" +
            "    <TxInfAndSts>\n" +
            "      <StsId>AB/8568</StsId>\n" +
            "      <OrgnlEndToEndId>VA060327/0123</OrgnlEndToEndId>\n" +
            "      <OrgnlTxId>AAAAUS29/100628/ad458</OrgnlTxId>\n" +
            "      <TxSts>RJCT</TxSts>\n" +
            "      <StsRsnInf>\n" +
            "        <Orgtr>\n" +
            "          <Id>\n" +
            "            <OrgId>\n" +
            "              <AnyBIC>ABABUS23</AnyBIC>\n" +
            "            </OrgId>\n" +
            "          </Id>\n" +
            "        </Orgtr>\n" +
            "        <Rsn>\n" +
            "          <Cd>AM05</Cd>\n" +
            "        </Rsn>\n" +
            "      </StsRsnInf>\n" +
            "      <OrgnlTxRef>\n" +
            "        <IntrBkSttlmAmt Ccy=\"USD\">1025</IntrBkSttlmAmt>\n" +
            "        <IntrBkSttlmDt>2015-06-28</IntrBkSttlmDt>\n" +
            "        <ReqdColltnDt>2015-07-13</ReqdColltnDt>\n" +
            "        <MndtRltdInf>\n" +
            "          <DrctDbtMndt>\n" +
            "            <MndtId>VIRGAY123</MndtId>\n" +
            "          </DrctDbtMndt>\n" +
            "        </MndtRltdInf>\n" +
            "        <Dbtr>\n" +
            "          <Pty>\n" +
            "            <Nm>Jones</Nm>\n" +
            "            <PstlAdr>\n" +
            "              <StrtNm>Hudson Street</StrtNm>\n" +
            "              <BldgNb>19</BldgNb>\n" +
            "              <PstCd>NJ 07302</PstCd>\n" +
            "              <TwnNm>Jersey City</TwnNm>\n" +
            "              <Ctry>US</Ctry>\n" +
            "            </PstlAdr>\n" +
            "          </Pty>\n" +
            "        </Dbtr>\n" +
            "        <Cdtr>\n" +
            "          <Pty>\n" +
            "            <Nm>Virgay</Nm>\n" +
            "            <PstlAdr>\n" +
            "              <StrtNm>Virginia Lane</StrtNm>\n" +
            "              <BldgNb>36</BldgNb>\n" +
            "              <PstCd>NJ 07311</PstCd>\n" +
            "              <TwnNm>Jersey City</TwnNm>\n" +
            "              <Ctry>US</Ctry>\n" +
            "            </PstlAdr>\n" +
            "          </Pty>\n" +
            "        </Cdtr>\n" +
            "      </OrgnlTxRef>\n" +
            "    </TxInfAndSts>\n" +
            "  </FIToFIPmtStsRpt>\n" +
            "</Document>\n";

}
