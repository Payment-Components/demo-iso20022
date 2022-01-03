package com.paymentcomponents.swift.mx.cbpr;

import gr.datamation.mx.CbprMessage;
import gr.datamation.mx.message.head.BusinessApplicationHeader02;
import gr.datamation.mx.message.pacs.FinancialInstitutionCreditTransfer08;
import gr.datamation.validation.error.ValidationError;
import gr.datamation.validation.error.ValidationErrorList;

import java.io.ByteArrayInputStream;

public class ParseAndValidateCbprMessage {

    public static void main(String... args) {
        execute();
    }

    public static void execute() {
        constructCBPRMessage();
        parseAndValidateCBPRMessage();
        autoParseAndValidateCBPRMessage();
    }

    public static void constructCBPRMessage() {
        try {
            //Initialize the header object
            BusinessApplicationHeader02 businessApplicationHeader = new BusinessApplicationHeader02();
            businessApplicationHeader.parseXML(validCbprPacs009HeaderString);

            //Initialize the document object
            FinancialInstitutionCreditTransfer08 financialInstitutionCreditTransfer08 = new FinancialInstitutionCreditTransfer08();
            financialInstitutionCreditTransfer08.parseXML(validCbprPacs009DocumentString);

            //We fill the elements of the message object using setters
            //financialInstitutionCreditTransfer08.getMessage().setGrpHdr(new GroupHeader93());
            //financialInstitutionCreditTransfer08.getMessage().getGrpHdr().setMsgId("1234");

            //or setElement()
            //financialInstitutionCreditTransfer08.setElement("GrpHdr/MsgId", "1234");

            //Construct the CBPR message object using two separate objects, header, document
            CbprMessage<BusinessApplicationHeader02, FinancialInstitutionCreditTransfer08> cbprMessage = new CbprMessage<>(businessApplicationHeader, financialInstitutionCreditTransfer08);

            //Perform validation in both header and message object using cbprMessage
            //Use CbprMessage.CbprMsgType enumeration object to select the matching schema (check the table of supported CBPR messages below
            ValidationErrorList validationErrorList = cbprMessage.validate(CbprMessage.CbprMsgType.PACS_009_CORE);

            if (validationErrorList.isEmpty()) {
                System.out.println("Message is valid");
                System.out.println(cbprMessage.convertToXML()); //Get the generated xmls for head and document
            } else {
                handleValidationError(validationErrorList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }

    public static void parseAndValidateCBPRMessage() {
        try {
            //Initialize the cbprMessage
            CbprMessage<BusinessApplicationHeader02, FinancialInstitutionCreditTransfer08> cbprMessage = new CbprMessage<>(new BusinessApplicationHeader02(), new FinancialInstitutionCreditTransfer08());
            //Fill the cbprMessage with data from xml and validate CBPR+ against the xml schema
            ValidationErrorList validationErrorList = cbprMessage.autoParseAndValidateXml(new ByteArrayInputStream(validCbprPacs009String.getBytes()));
            if (!validationErrorList.isEmpty()) {
                handleValidationError(validationErrorList);
                return;
            }

            //Perform validation in both header and message object using cbprMessage
            //Use CbprMessage.CbprMsgType enumeration object to select the matching schema (check the table of supported CBPR messages below
            validationErrorList.addAll(cbprMessage.validate(CbprMessage.CbprMsgType.PACS_009_CORE));

            if (validationErrorList.isEmpty()) {
                System.out.println("Message is valid");
                System.out.println(cbprMessage.convertToXML()); //Get the generated xmls for head and document
            } else {
                handleValidationError(validationErrorList);
            }

            //Extract the header and the core message from cbprMessage object
            BusinessApplicationHeader02 businessApplicationHeader = cbprMessage.getAppHdr();
            FinancialInstitutionCreditTransfer08 financialInstitutionCreditTransfer08 = cbprMessage.getDocument();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }

    public static void autoParseAndValidateCBPRMessage() {
        try {
            //Initialize the cbprMessage
            CbprMessage<BusinessApplicationHeader02, FinancialInstitutionCreditTransfer08> cbprMessage = new CbprMessage<>();
            //Fill the cbprMessage with data from xml and validate CBPR+ against the xml schema
            ValidationErrorList validationErrorList = cbprMessage.autoParseAndValidateXml(new ByteArrayInputStream(validCbprPacs009String.getBytes()));
            if (!validationErrorList.isEmpty()) {
                handleValidationError(validationErrorList);
                return;
            }

            //Perform validation in both header and message object using cbprMessage
            validationErrorList.addAll(cbprMessage.autoValidate());

            if (validationErrorList.isEmpty()) {
                System.out.println("Message is valid");
                System.out.println(cbprMessage.convertToXML()); //Get the generated xmls for head and document
            } else {
                handleValidationError(validationErrorList);
            }

            //Extract the header and the core message from cbprMessage object
            BusinessApplicationHeader02 businessApplicationHeader = cbprMessage.getAppHdr();
            FinancialInstitutionCreditTransfer08 financialInstitutionCreditTransfer08 = cbprMessage.getDocument();

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

    private static final String validCbprPacs009String = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<AppHdr xmlns=\"urn:iso:std:iso:20022:tech:xsd:head.001.001.02\">\n" +
            "    <Fr>\n" +
            "        <FIId>\n" +
            "            <FinInstnId>\n" +
            "                <BICFI>BBBBUS33</BICFI>\n" +
            "            </FinInstnId>\n" +
            "        </FIId>\n" +
            "    </Fr>\n" +
            "    <To>\n" +
            "        <FIId>\n" +
            "            <FinInstnId>\n" +
            "                <BICFI>CCCCJPJT</BICFI>\n" +
            "            </FinInstnId>\n" +
            "        </FIId>\n" +
            "    </To>\n" +
            "    <BizMsgIdr>BBBB/120928-FICT/JPY/430</BizMsgIdr>\n" +
            "    <MsgDefIdr>pacs.009.001.08</MsgDefIdr>\n" +
            "    <BizSvc>swift.cbprplus.01</BizSvc>\n" +
            "    <MktPrctc>\n" +
            "        <Regy>string</Regy>\n" +
            "        <Id>string</Id>\n" +
            "    </MktPrctc>\n" +
            "    <CreDt>2008-09-29T04:49:45+03:00</CreDt>\n" +
            "    <CpyDplct>CODU</CpyDplct>\n" +
            "    <PssblDplct>true</PssblDplct>\n" +
            "    <Prty>NORM</Prty>\n" +
            "    <Rltd>\n" +
            "        <Fr>\n" +
            "            <FIId>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>BBBBUS33</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </FIId>\n" +
            "        </Fr>\n" +
            "        <To>\n" +
            "            <FIId>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>CCCCJPJT</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </FIId>\n" +
            "        </To>\n" +
            "        <BizMsgIdr>BBBB/120928-FICT/JPY/430</BizMsgIdr>\n" +
            "        <MsgDefIdr>pacs.009.001.08</MsgDefIdr>\n" +
            "        <BizSvc>swift.cbprplus.01</BizSvc>\n" +
            "        <CreDt>2014-06-09T18:15:04+03:00</CreDt>\n" +
            "        <CpyDplct>COPY</CpyDplct>\n" +
            "        <Prty>NORM</Prty>\n" +
            "    </Rltd>\n" +
            "</AppHdr>\n" +
            "\n" +
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08\">\n" +
            "    <FICdtTrf>\n" +
            "        <GrpHdr>\n" +
            "            <MsgId>BBBB/120928-FICT/JPY/430</MsgId>\n" +
            "            <CreDtTm>2012-09-28T16:00:00+13:00</CreDtTm>\n" +
            "            <NbOfTxs>1</NbOfTxs>\n" +
            "            <SttlmInf>\n" +
            "                <SttlmMtd>INDA</SttlmMtd>\n" +
            "                <SttlmAcct>\n" +
            "                    <Id>\n" +
            "                        <Othr>\n" +
            "                            <Id>ACCOUNTID</Id>\n" +
            "                        </Othr>\n" +
            "                    </Id>\n" +
            "                </SttlmAcct>\n" +
            "            </SttlmInf>\n" +
            "        </GrpHdr>\n" +
            "        <CdtTrfTxInf>\n" +
            "            <PmtId>\n" +
            "                <InstrId>BBBB/120928-FICT</InstrId>\n" +
            "                <EndToEndId>ABC/4562/2012-09-08</EndToEndId>\n" +
            "                <TxId>BBBB/120928-CCT/123/1</TxId>\n" +
            "                <UETR>00000000-0000-4000-8000-000000000000</UETR>\n" +
            "            </PmtId>\n" +
            "            <PmtTpInf>\n" +
            "                <InstrPrty>NORM</InstrPrty>\n" +
            "            </PmtTpInf>\n" +
            "            <IntrBkSttlmAmt Ccy=\"JPY\">10000000</IntrBkSttlmAmt>\n" +
            "            <IntrBkSttlmDt>2012-09-29</IntrBkSttlmDt>\n" +
            "            <SttlmTmIndctn>\n" +
            "                <CdtDtTm>2012-09-28T16:00:00+13:00</CdtDtTm>\n" +
            "            </SttlmTmIndctn>\n" +
            "            <SttlmTmReq>\n" +
            "                <CLSTm>12:12:12+13:00</CLSTm>\n" +
            "            </SttlmTmReq>\n" +
            "            <PrvsInstgAgt1>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>TESTBICD</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </PrvsInstgAgt1>\n" +
            "            <InstgAgt>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>BBBBUS33</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </InstgAgt>\n" +
            "            <InstdAgt>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>CCCCJPJT</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </InstdAgt>\n" +
            "            <IntrmyAgt1>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>INTERBIC</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </IntrmyAgt1>\n" +
            "            <IntrmyAgt1Acct>\n" +
            "                <Id>\n" +
            "                    <Othr>\n" +
            "                        <Id>INTERAGTACCT</Id>\n" +
            "                    </Othr>\n" +
            "                </Id>\n" +
            "            </IntrmyAgt1Acct>\n" +
            "            <Dbtr>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>BBBBUS33</BICFI>\n" +
            "                    <Nm>Debtor Name</Nm>\n" +
            "                    <PstlAdr>\n" +
            "                        <AdrLine>Address</AdrLine>\n" +
            "                    </PstlAdr>\n" +
            "                </FinInstnId>\n" +
            "            </Dbtr>\n" +
            "            <DbtrAcct>\n" +
            "                <Id>\n" +
            "                    <Othr>\n" +
            "                        <Id>DBTRACCT</Id>\n" +
            "                    </Othr>\n" +
            "                </Id>\n" +
            "            </DbtrAcct>\n" +
            "            <DbtrAgt>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>BBBBUS33</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </DbtrAgt>\n" +
            "            <CdtrAgt>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>AAAAJPJT</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </CdtrAgt>\n" +
            "            <CdtrAgtAcct>\n" +
            "                <Id>\n" +
            "                    <Othr>\n" +
            "                        <Id>CDTRAGTACCT</Id>\n" +
            "                    </Othr>\n" +
            "                </Id>\n" +
            "            </CdtrAgtAcct>\n" +
            "            <Cdtr>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>AAAAGB2L</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </Cdtr>\n" +
            "            <CdtrAcct>\n" +
            "                <Id>\n" +
            "                    <Othr>\n" +
            "                        <Id>CDTRACCT</Id>\n" +
            "                    </Othr>\n" +
            "                </Id>\n" +
            "            </CdtrAcct>\n" +
            "        </CdtTrfTxInf>\n" +
            "    </FICdtTrf>\n" +
            "</Document>";

    private static final String validCbprPacs009HeaderString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<AppHdr xmlns=\"urn:iso:std:iso:20022:tech:xsd:head.001.001.02\">\n" +
            "    <Fr>\n" +
            "        <FIId>\n" +
            "            <FinInstnId>\n" +
            "                <BICFI>BBBBUS33</BICFI>\n" +
            "            </FinInstnId>\n" +
            "        </FIId>\n" +
            "    </Fr>\n" +
            "    <To>\n" +
            "        <FIId>\n" +
            "            <FinInstnId>\n" +
            "                <BICFI>CCCCJPJT</BICFI>\n" +
            "            </FinInstnId>\n" +
            "        </FIId>\n" +
            "    </To>\n" +
            "    <BizMsgIdr>BBBB/120928-FICT/JPY/430</BizMsgIdr>\n" +
            "    <MsgDefIdr>pacs.009.001.08</MsgDefIdr>\n" +
            "    <BizSvc>swift.cbprplus.01</BizSvc>\n" +
            "    <MktPrctc>\n" +
            "        <Regy>string</Regy>\n" +
            "        <Id>string</Id>\n" +
            "    </MktPrctc>\n" +
            "    <CreDt>2008-09-29T04:49:45+03:00</CreDt>\n" +
            "    <CpyDplct>CODU</CpyDplct>\n" +
            "    <PssblDplct>true</PssblDplct>\n" +
            "    <Prty>NORM</Prty>\n" +
            "    <Rltd>\n" +
            "        <Fr>\n" +
            "            <FIId>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>BBBBUS33</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </FIId>\n" +
            "        </Fr>\n" +
            "        <To>\n" +
            "            <FIId>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>CCCCJPJT</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </FIId>\n" +
            "        </To>\n" +
            "        <BizMsgIdr>BBBB/120928-FICT/JPY/430</BizMsgIdr>\n" +
            "        <MsgDefIdr>pacs.009.001.08</MsgDefIdr>\n" +
            "        <BizSvc>swift.cbprplus.01</BizSvc>\n" +
            "        <CreDt>2014-06-09T18:15:04+03:00</CreDt>\n" +
            "        <CpyDplct>COPY</CpyDplct>\n" +
            "        <Prty>NORM</Prty>\n" +
            "    </Rltd>\n" +
            "</AppHdr>";

    private static final String validCbprPacs009DocumentString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08\">\n" +
            "    <FICdtTrf>\n" +
            "        <GrpHdr>\n" +
            "            <MsgId>BBBB/120928-FICT/JPY/430</MsgId>\n" +
            "            <CreDtTm>2012-09-28T16:00:00+13:00</CreDtTm>\n" +
            "            <NbOfTxs>1</NbOfTxs>\n" +
            "            <SttlmInf>\n" +
            "                <SttlmMtd>INDA</SttlmMtd>\n" +
            "                <SttlmAcct>\n" +
            "                    <Id>\n" +
            "                        <Othr>\n" +
            "                            <Id>ACCOUNTID</Id>\n" +
            "                        </Othr>\n" +
            "                    </Id>\n" +
            "                </SttlmAcct>\n" +
            "            </SttlmInf>\n" +
            "        </GrpHdr>\n" +
            "        <CdtTrfTxInf>\n" +
            "            <PmtId>\n" +
            "                <InstrId>BBBB/120928-FICT</InstrId>\n" +
            "                <EndToEndId>ABC/4562/2012-09-08</EndToEndId>\n" +
            "                <TxId>BBBB/120928-CCT/123/1</TxId>\n" +
            "                <UETR>00000000-0000-4000-8000-000000000000</UETR>\n" +
            "            </PmtId>\n" +
            "            <PmtTpInf>\n" +
            "                <InstrPrty>NORM</InstrPrty>\n" +
            "            </PmtTpInf>\n" +
            "            <IntrBkSttlmAmt Ccy=\"JPY\">10000000</IntrBkSttlmAmt>\n" +
            "            <IntrBkSttlmDt>2012-09-29</IntrBkSttlmDt>\n" +
            "            <SttlmTmIndctn>\n" +
            "                <CdtDtTm>2012-09-28T16:00:00+13:00</CdtDtTm>\n" +
            "            </SttlmTmIndctn>\n" +
            "            <SttlmTmReq>\n" +
            "                <CLSTm>12:12:12+13:00</CLSTm>\n" +
            "            </SttlmTmReq>\n" +
            "            <PrvsInstgAgt1>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>TESTBICD</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </PrvsInstgAgt1>\n" +
            "            <InstgAgt>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>BBBBUS33</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </InstgAgt>\n" +
            "            <InstdAgt>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>CCCCJPJT</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </InstdAgt>\n" +
            "            <IntrmyAgt1>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>INTERBIC</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </IntrmyAgt1>\n" +
            "            <IntrmyAgt1Acct>\n" +
            "                <Id>\n" +
            "                    <Othr>\n" +
            "                        <Id>INTERAGTACCT</Id>\n" +
            "                    </Othr>\n" +
            "                </Id>\n" +
            "            </IntrmyAgt1Acct>\n" +
            "            <Dbtr>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>BBBBUS33</BICFI>\n" +
            "                    <Nm>Debtor Name</Nm>\n" +
            "                    <PstlAdr>\n" +
            "                        <AdrLine>Address</AdrLine>\n" +
            "                    </PstlAdr>\n" +
            "                </FinInstnId>\n" +
            "            </Dbtr>\n" +
            "            <DbtrAcct>\n" +
            "                <Id>\n" +
            "                    <Othr>\n" +
            "                        <Id>DBTRACCT</Id>\n" +
            "                    </Othr>\n" +
            "                </Id>\n" +
            "            </DbtrAcct>\n" +
            "            <DbtrAgt>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>BBBBUS33</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </DbtrAgt>\n" +
            "            <CdtrAgt>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>AAAAJPJT</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </CdtrAgt>\n" +
            "            <CdtrAgtAcct>\n" +
            "                <Id>\n" +
            "                    <Othr>\n" +
            "                        <Id>CDTRAGTACCT</Id>\n" +
            "                    </Othr>\n" +
            "                </Id>\n" +
            "            </CdtrAgtAcct>\n" +
            "            <Cdtr>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>AAAAGB2L</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </Cdtr>\n" +
            "            <CdtrAcct>\n" +
            "                <Id>\n" +
            "                    <Othr>\n" +
            "                        <Id>CDTRACCT</Id>\n" +
            "                    </Othr>\n" +
            "                </Id>\n" +
            "            </CdtrAcct>\n" +
            "        </CdtTrfTxInf>\n" +
            "    </FICdtTrf>\n" +
            "</Document>";

}