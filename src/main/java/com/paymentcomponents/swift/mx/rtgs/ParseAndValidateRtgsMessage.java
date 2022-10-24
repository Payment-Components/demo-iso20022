package com.paymentcomponents.swift.mx.rtgs;

import gr.datamation.mx.Message;
import gr.datamation.iso20022.target2.RtgsUtils;
import gr.datamation.iso20022.target2.pacs.FinancialInstitutionCreditTransfer08Rtgs;
import gr.datamation.validation.error.ValidationError;
import gr.datamation.validation.error.ValidationErrorList;

import java.io.ByteArrayInputStream;

public class ParseAndValidateRtgsMessage {

    public static void main(String... args) {
        execute();
    }

    public static void execute() {
        constructRTGSMessage();
        parseAndValidateRTGSMessage();
        autoParseAndValidateRTGSMessage();
    }

    public static void constructRTGSMessage() {
        try {
            //Initialize the message object
            FinancialInstitutionCreditTransfer08Rtgs financialInstitutionCreditTransfer08 = new FinancialInstitutionCreditTransfer08Rtgs();
            //Optionally use an existing message if we do not want to create the object from scratch
            financialInstitutionCreditTransfer08.parseXML(validRtgsPacs009String);

            //We fill the elements of the message object using setters
            //financialInstitutionCreditTransfer08.getMessage().setGrpHdr(new GroupHeader93());
            //financialInstitutionCreditTransfer08.getMessage().getGrpHdr().setMsgId("1234");

            //or setElement()
            //financialInstitutionCreditTransfer08.setElement("GrpHdr/MsgId", "1234");

            //Perform validation
            ValidationErrorList validationErrorList = financialInstitutionCreditTransfer08.validate();

            if (validationErrorList.isEmpty()) {
                System.out.println("Message is valid");
                System.out.println(financialInstitutionCreditTransfer08.convertToXML()); //Get the generated xml
            } else {
                handleValidationError(validationErrorList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }

    public static void parseAndValidateRTGSMessage() {
        try {
            //Initialize the message object
            FinancialInstitutionCreditTransfer08Rtgs financialInstitutionCreditTransfer08 = new FinancialInstitutionCreditTransfer08Rtgs();
            //Validate against the xml schema
            ValidationErrorList validationErrorList = financialInstitutionCreditTransfer08.validateXML(new ByteArrayInputStream(validRtgsPacs009String.getBytes()));
            if (!validationErrorList.isEmpty()) {
                handleValidationError(validationErrorList);
                return;
            }
            //Fill the message with data from xml
            financialInstitutionCreditTransfer08.parseXML(validRtgsPacs009String);
            //Validate both the xml schema and rules
            validationErrorList.addAll(financialInstitutionCreditTransfer08.validate());

            if (validationErrorList.isEmpty()) {
                System.out.println("Message is valid");
                System.out.println(financialInstitutionCreditTransfer08.convertToXML()); //Get the generated xml
            } else {
                handleValidationError(validationErrorList);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }

    public static void autoParseAndValidateRTGSMessage() {
        try {
            //Validate against the xml schema without knowing the message type
            ValidationErrorList validationErrorList = RtgsUtils.autoValidateXML(new ByteArrayInputStream(validRtgsPacs009String.getBytes()));
            if (!validationErrorList.isEmpty()) {
                handleValidationError(validationErrorList);
                return;
            }
            //Fill the message with data from xml without knowing the message type
            Message message = RtgsUtils.autoParseXML(validRtgsPacs009String);
            //Validate both the xml schema and rules
            validationErrorList.addAll(message.validate());

            if (validationErrorList.isEmpty()) {
                System.out.println("Message is valid");
                System.out.println(message.convertToXML()); //Get the generated xml
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

    private static final String validRtgsPacs009String = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!--Inbound_pacs.009_RTGS_FICreditTransferOrder_COV_bs028-->\n" +
            "<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08 RTGS_pacs_guidelines_pacs_009_FIToFIFinancialInstitutionCreditTransfer_pacs_009_001_08_20191021_1544%20(1).xsd\">\n" +
            "    <FICdtTrf>\n" +
            "        <GrpHdr>\n" +
            "            <MsgId>NONREF</MsgId>\n" +
            "            <CreDtTm>2019-10-07T13:45:00+00:00</CreDtTm>\n" +
            "            <NbOfTxs>1</NbOfTxs>\n" +
            "            <SttlmInf>\n" +
            "                <SttlmMtd>CLRG</SttlmMtd>\n" +
            "                <ClrSys>\n" +
            "                    <Cd>TGT</Cd>\n" +
            "                </ClrSys>\n" +
            "            </SttlmInf>\n" +
            "        </GrpHdr>\n" +
            "        <CdtTrfTxInf>\n" +
            "            <PmtId>\n" +
            "                <InstrId>Inp009b028-InsId</InstrId>\n" +
            "                <EndToEndId>Inp008b028-E2EId</EndToEndId>\n" +
            "                <UETR>e008b028-59c5-41e9-be4c-d45102fc201e</UETR>\n" +
            "            </PmtId>\n" +
            "            <IntrBkSttlmAmt Ccy=\"EUR\">61250.00</IntrBkSttlmAmt>\n" +
            "            <IntrBkSttlmDt>2019-10-07</IntrBkSttlmDt>\n" +
            "            <InstgAgt>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>PBBBDEFFXXX</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </InstgAgt>\n" +
            "            <InstdAgt>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>PBAADEFFAC2</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </InstdAgt>\n" +
            "            <Dbtr>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>PBBBDEFFXXX</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </Dbtr>\n" +
            "            <Cdtr>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>PBAADEFFAC2</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </Cdtr>\n" +
            "            <UndrlygCstmrCdtTrf>\n" +
            "                <UltmtDbtr>\n" +
            "                    <Nm>Ultimate debtor name</Nm>\n" +
            "                    <Id>\n" +
            "                        <OrgId>\n" +
            "                            <AnyBIC>ULTMDBTRBIC</AnyBIC>\n" +
            "                        </OrgId>\n" +
            "                    </Id>\n" +
            "                </UltmtDbtr>\n" +
            "                <Dbtr>\n" +
            "                    <Nm>Debit customer name</Nm>\n" +
            "                    <PstlAdr>\n" +
            "                        <TwnNm>Frankfurt</TwnNm>\n" +
            "                        <Ctry>DE</Ctry>\n" +
            "                    </PstlAdr>\n" +
            "                </Dbtr>\n" +
            "                <DbtrAgt>\n" +
            "                    <FinInstnId>\n" +
            "                        <BICFI>PBBBDEFFXXX</BICFI>\n" +
            "                    </FinInstnId>\n" +
            "                </DbtrAgt>\n" +
            "                <CdtrAgt>\n" +
            "                    <FinInstnId>\n" +
            "                        <BICFI>PBAADEFFXXX</BICFI>\n" +
            "                    </FinInstnId>\n" +
            "                </CdtrAgt>\n" +
            "                <Cdtr>\n" +
            "                    <Nm>Credit customer name</Nm>\n" +
            "                    <PstlAdr>\n" +
            "                        <TwnNm>Dusseldorf</TwnNm>\n" +
            "                        <Ctry>DE</Ctry>\n" +
            "                    </PstlAdr>\n" +
            "                </Cdtr>\n" +
            "                <UltmtCdtr>\n" +
            "                    <Nm>Ultimate creditor name</Nm>\n" +
            "                    <Id>\n" +
            "                        <OrgId>\n" +
            "                            <AnyBIC>ULTMCDTRBIC</AnyBIC>\n" +
            "                        </OrgId>\n" +
            "                    </Id>\n" +
            "                </UltmtCdtr>\n" +
            "            </UndrlygCstmrCdtTrf>\n" +
            "        </CdtTrfTxInf>\n" +
            "    </FICdtTrf>\n" +
            "</Document>\n";

}