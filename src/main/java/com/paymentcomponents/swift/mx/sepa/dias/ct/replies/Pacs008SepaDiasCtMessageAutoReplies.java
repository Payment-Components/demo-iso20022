package com.paymentcomponents.swift.mx.sepa.dias.ct.replies;

import gr.datamation.iso20022.sepa.dias.ct.camt.ClaimNonReceipt07SepaDiasCt;
import gr.datamation.iso20022.sepa.dias.ct.camt.FIToFIPaymentCancellationRequest08SepaDiasCt;
import gr.datamation.iso20022.sepa.dias.ct.camt.RequestToModifyPayment06SepaDiasCt;
import gr.datamation.iso20022.sepa.dias.ct.pacs.FIToFICustomerCreditTransfer08SepaDiasCt;
import gr.datamation.iso20022.sepa.dias.ct.pacs.FIToFIPaymentStatusRequest03SepaDiasCt;
import gr.datamation.iso20022.sepa.dias.ct.pacs.PaymentReturn09SepaDiasCt;
import gr.datamation.iso20022.sepa.dias.ct.replies.FIToFICustomerCreditTransferSepaDiasCtAutoReplies;
import gr.datamation.replies.common.ChargesInformation;
import gr.datamation.replies.common.MsgReplyInfo;
import gr.datamation.replies.common.ReasonInformation;
import gr.datamation.validation.error.ValidationError;
import gr.datamation.validation.error.ValidationErrorList;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

public class Pacs008SepaDiasCtMessageAutoReplies {

    public static void main(String... args) {
        execute();
    }

    public static void execute() {
        paymentReturnAutoReply();
        paymentCancellationRequestAutoReply();
        claimNonReceiptAutoReply();
        requestToModifyPaymentAutoReply();
        fiToFIPaymentStatusRequestAutoReply();
    }

    public static void paymentReturnAutoReply() {
        try {
            //Initialize the message object
            FIToFICustomerCreditTransfer08SepaDiasCt fiToFICustomerCreditTransfer = new FIToFICustomerCreditTransfer08SepaDiasCt();
            //Optionally use an existing message if we do not want to create the object from scratch
            fiToFICustomerCreditTransfer.parseXML(validPacs008SepaDiasCtIncomingString);

            //Perform validation
            ValidationErrorList validationErrorList = fiToFICustomerCreditTransfer.validate();

            if (!validationErrorList.isEmpty())
                throw new Exception("Invalid pacs.008 message to return");

            FIToFICustomerCreditTransferSepaDiasCtAutoReplies<FIToFICustomerCreditTransfer08SepaDiasCt, PaymentReturn09SepaDiasCt> pacs008AutoReplies =
                    new FIToFICustomerCreditTransferSepaDiasCtAutoReplies<>(fiToFICustomerCreditTransfer);

            MsgReplyInfo msgReplyInfo = new MsgReplyInfo();
            ReasonInformation reasonInformation = new ReasonInformation();
            msgReplyInfo.setRsnInf(reasonInformation);

            reasonInformation.setType(ReasonInformation.Type.CD); //mandatory
            reasonInformation.setValue("FOCR"); //mandatory
            reasonInformation.setAddtlInf(Collections.singletonList("Additional info")); //optional
            msgReplyInfo.setReplyId("pacs008Reply"); //if empty, pacs.004 Message Id will be used
            msgReplyInfo.setOrgnlTxId("From014To016-Cred-1"); //OrgnlTxId, OrgnlInstrId or both should be present
            msgReplyInfo.setOrgnlInstrId("18140100000000"); //OrgnlTxId, OrgnlInstrId or both should be present
            msgReplyInfo.setIntrBkSttlmDt(LocalDate.now()); //optional, if empty, current date will be used

            msgReplyInfo.setChargesInformation(Collections.singletonList(new ChargesInformation())); //optional
            msgReplyInfo.getChargesInformation().get(0).setAmount(new BigDecimal("2.00")); //mandatory
            msgReplyInfo.getChargesInformation().get(0).setAgentBic("AAAAGB2L"); //optional, if empty creditor agent will be used
            msgReplyInfo.setChargeBearer("SLEV"); //optional, if empty, SLEV will be used

            PaymentReturn09SepaDiasCt pacs004 = pacs008AutoReplies.autoReply(new PaymentReturn09SepaDiasCt(), Collections.singletonList(msgReplyInfo));
            validationErrorList = pacs004.validate();

            if (validationErrorList.isEmpty()) {
                System.out.println("Return Message is valid");
                System.out.println(pacs004.convertToXML()); //Get the generated xml
            } else {
                handleValidationError(validationErrorList);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }

    }

    public static void paymentCancellationRequestAutoReply() {
        try {
            //Initialize the message object
            FIToFICustomerCreditTransfer08SepaDiasCt fiToFICustomerCreditTransfer = new FIToFICustomerCreditTransfer08SepaDiasCt();
            //Optionally use an existing message if we do not want to create the object from scratch
            fiToFICustomerCreditTransfer.parseXML(validPacs008SepaDiasCtOutgoingString);

            //Perform validation
            ValidationErrorList validationErrorList = fiToFICustomerCreditTransfer.validate();

            if (!validationErrorList.isEmpty())
                throw new Exception("Invalid pacs.008 message to cancel");

            FIToFICustomerCreditTransferSepaDiasCtAutoReplies<FIToFICustomerCreditTransfer08SepaDiasCt, FIToFIPaymentCancellationRequest08SepaDiasCt> pacs008AutoReplies =
                    new FIToFICustomerCreditTransferSepaDiasCtAutoReplies<>(fiToFICustomerCreditTransfer);

            MsgReplyInfo msgReplyInfo = new MsgReplyInfo();
            ReasonInformation reasonInformation = new ReasonInformation();
            msgReplyInfo.setRsnInf(reasonInformation);

            reasonInformation.setType(ReasonInformation.Type.CD); //mandatory
            reasonInformation.setValue("CUST"); //mandatory
            reasonInformation.setAddtlInf(Collections.singletonList("Additional info")); //optional, can be used only in case of the following codes: CUST, FRAD, AM09, AC03
            msgReplyInfo.setReplyId("pacs008Reply"); //if empty, camt.056 Message Id will be used
            msgReplyInfo.setOrgnlTxId("From014To016-Cred-1"); //OrgnlTxId, OrgnlInstrId or both should be present
            msgReplyInfo.setOrgnlInstrId("18140100000000"); //OrgnlTxId, OrgnlInstrId or both should be present

            FIToFIPaymentCancellationRequest08SepaDiasCt camt056 = pacs008AutoReplies.autoReply(new FIToFIPaymentCancellationRequest08SepaDiasCt(),
                    Collections.singletonList(msgReplyInfo));
            validationErrorList = camt056.validate();

            if (validationErrorList.isEmpty()) {
                System.out.println("Cancellation Request Message is valid");
                System.out.println(camt056.convertToXML()); //Get the generated xml
            } else {
                handleValidationError(validationErrorList);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }

    }

    public static void claimNonReceiptAutoReply() {
        try {
            //Initialize the message object
            FIToFICustomerCreditTransfer08SepaDiasCt fiToFICustomerCreditTransfer = new FIToFICustomerCreditTransfer08SepaDiasCt();
            //Optionally use an existing message if we do not want to create the object from scratch
            fiToFICustomerCreditTransfer.parseXML(validPacs008SepaDiasCtOutgoingString);

            //Perform validation
            ValidationErrorList validationErrorList = fiToFICustomerCreditTransfer.validate();

            if (!validationErrorList.isEmpty())
                throw new Exception("Invalid pacs.008 message to claim");

            FIToFICustomerCreditTransferSepaDiasCtAutoReplies<FIToFICustomerCreditTransfer08SepaDiasCt, ClaimNonReceipt07SepaDiasCt> pacs008AutoReplies =
                    new FIToFICustomerCreditTransferSepaDiasCtAutoReplies<>(fiToFICustomerCreditTransfer);

            MsgReplyInfo msgReplyInfo = new MsgReplyInfo();
            ReasonInformation reasonInformation = new ReasonInformation();
            msgReplyInfo.setRsnInf(reasonInformation);

            msgReplyInfo.setOrgnlTxId("From014To016-Cred-1"); //OrgnlTxId, OrgnlInstrId or both should be present
            msgReplyInfo.setOrgnlInstrId("18140100000000"); //OrgnlTxId, OrgnlInstrId or both should be present

            ClaimNonReceipt07SepaDiasCt camt027 = pacs008AutoReplies.autoReply(new ClaimNonReceipt07SepaDiasCt(),
                    Collections.singletonList(msgReplyInfo));
            validationErrorList = camt027.validate();

            if (validationErrorList.isEmpty()) {
                System.out.println("Claim Non Receipt Message is valid");
                System.out.println(camt027.convertToXML()); //Get the generated xml
            } else {
                handleValidationError(validationErrorList);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }

    }

    public static void requestToModifyPaymentAutoReply() {
        try {
            //Initialize the message object
            FIToFICustomerCreditTransfer08SepaDiasCt fiToFICustomerCreditTransfer = new FIToFICustomerCreditTransfer08SepaDiasCt();
            //Optionally use an existing message if we do not want to create the object from scratch
            fiToFICustomerCreditTransfer.parseXML(validPacs008SepaDiasCtOutgoingString);

            //Perform validation
            ValidationErrorList validationErrorList = fiToFICustomerCreditTransfer.validate();

            if (!validationErrorList.isEmpty())
                throw new Exception("Invalid pacs.008 message to modify");

            FIToFICustomerCreditTransferSepaDiasCtAutoReplies<FIToFICustomerCreditTransfer08SepaDiasCt, RequestToModifyPayment06SepaDiasCt> pacs008AutoReplies =
                    new FIToFICustomerCreditTransferSepaDiasCtAutoReplies<>(fiToFICustomerCreditTransfer);

            MsgReplyInfo msgReplyInfo = new MsgReplyInfo();
            ReasonInformation reasonInformation = new ReasonInformation();
            msgReplyInfo.setRsnInf(reasonInformation);

            msgReplyInfo.setOrgnlTxId("From014To016-Cred-1"); //OrgnlTxId, OrgnlInstrId or both should be present
            msgReplyInfo.setOrgnlInstrId("18140100000000"); //OrgnlTxId, OrgnlInstrId or both should be present
            msgReplyInfo.setIntrBkSttlmDt(LocalDate.parse("2025-05-05")); //mandatory

            RequestToModifyPayment06SepaDiasCt camt087 = pacs008AutoReplies.autoReply(new RequestToModifyPayment06SepaDiasCt(),
                    Collections.singletonList(msgReplyInfo));
            validationErrorList = camt087.validate();

            if (validationErrorList.isEmpty()) {
                System.out.println("Request To Modify Payment Message is valid");
                System.out.println(camt087.convertToXML()); //Get the generated xml
            } else {
                handleValidationError(validationErrorList);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }

    }

    public static void fiToFIPaymentStatusRequestAutoReply() {
        try {
            //Initialize the message object
            FIToFICustomerCreditTransfer08SepaDiasCt fiToFICustomerCreditTransfer = new FIToFICustomerCreditTransfer08SepaDiasCt();
            //Optionally use an existing message if we do not want to create the object from scratch
            fiToFICustomerCreditTransfer.parseXML(validPacs008SepaDiasCtOutgoingString);

            //Perform validation
            ValidationErrorList validationErrorList = fiToFICustomerCreditTransfer.validate();

            if (!validationErrorList.isEmpty())
                throw new Exception("Invalid pacs.008 message to request status");

            FIToFICustomerCreditTransferSepaDiasCtAutoReplies<FIToFICustomerCreditTransfer08SepaDiasCt, FIToFIPaymentStatusRequest03SepaDiasCt> pacs008AutoReplies =
                    new FIToFICustomerCreditTransferSepaDiasCtAutoReplies<>(fiToFICustomerCreditTransfer);

            MsgReplyInfo msgReplyInfo = new MsgReplyInfo();
            ReasonInformation reasonInformation = new ReasonInformation();
            msgReplyInfo.setRsnInf(reasonInformation);

            msgReplyInfo.setOrgnlMsgId("20210928"); //mandatory
            msgReplyInfo.setOrgnlMsgNmId("camt.056.001.08"); //mandatory, or `camt.027.001.07`, `camt.087.001.06`
            msgReplyInfo.setOrgnlTxId("From014To016-Cred-1"); //OrgnlTxId, OrgnlInstrId or both should be present
            msgReplyInfo.setOrgnlInstrId("18140100000000"); //OrgnlTxId, OrgnlInstrId or both should be present

            FIToFIPaymentStatusRequest03SepaDiasCt pacs028 = pacs008AutoReplies.autoReply(new FIToFIPaymentStatusRequest03SepaDiasCt(),
                    Collections.singletonList(msgReplyInfo));

            validationErrorList = pacs028.validate();

            if (validationErrorList.isEmpty()) {
                System.out.println("Payment Status Request Message is valid");
                System.out.println(pacs028.convertToXML()); //Get the generated xml
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

    private static final String validPacs008SepaDiasCtIncomingString = "<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08\">\n" +
            "    <FIToFICstmrCdtTrf>\n" +
            "        <GrpHdr>\n" +
            "            <MsgId>18158300000001</MsgId>\n" +
            "            <CreDtTm>2023-06-21T12:12:17.212+03:00</CreDtTm>\n" +
            "            <NbOfTxs>1</NbOfTxs>\n" +
            "            <TtlIntrBkSttlmAmt Ccy='EUR'>10.00</TtlIntrBkSttlmAmt>\n" +
            "            <IntrBkSttlmDt>2023-06-01</IntrBkSttlmDt>\n" +
            "            <SttlmInf>\n" +
            "                <SttlmMtd>CLRG</SttlmMtd>\n" +
            "                <ClrSys><Prtry>DIAS</Prtry></ClrSys>\n" +
            "            </SttlmInf>\n" +
            "            <InstdAgt><FinInstnId><BICFI>ABCDGRA0</BICFI></FinInstnId></InstdAgt>\n" +
            "        </GrpHdr>\n" +
            "        <CdtTrfTxInf>\n" +
            "            <PmtId>\n" +
            "                <InstrId>18140100000000</InstrId>\n" +
            "                <EndToEndId>From014To016-Cred-1</EndToEndId>\n" +
            "                <TxId>From014To016-Cred-1</TxId>\n" +
            "            </PmtId>\n" +
            "            <PmtTpInf>\n" +
            "                <SvcLvl><Cd>SEPA</Cd></SvcLvl>\n" +
            "                <LclInstrm><Prtry>SCT</Prtry></LclInstrm>\n" +
            "                <CtgyPurp>\n" +
            "                    <Cd>CBLK</Cd>\n" +
            "                </CtgyPurp>\n" +
            "            </PmtTpInf>\n" +
            "            <IntrBkSttlmAmt Ccy=\"EUR\">10.00</IntrBkSttlmAmt>\n" +
            "            <ChrgBr>SLEV</ChrgBr>\n" +
            "            <InstgAgt>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>BCDAGRA0</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </InstgAgt>\n" +
            "            <UltmtDbtr>\n" +
            "                <Nm>Ultimate Debtor Test 1</Nm>\n" +
            "                <Id>\n" +
            "                    <PrvtId>\n" +
            "                        <Othr>\n" +
            "                            <Id>AX035662</Id>\n" +
            "                            <SchmeNm>\n" +
            "                                <Cd>NIDN</Cd>\n" +
            "                            </SchmeNm>\n" +
            "                        </Othr>\n" +
            "                    </PrvtId>\n" +
            "                </Id>\n" +
            "            </UltmtDbtr>\n" +
            "            <Dbtr>\n" +
            "                <Nm>Debtor Name 1</Nm>\n" +
            "                <PstlAdr>\n" +
            "                    <Ctry>GR</Ctry>\n" +
            "                    <AdrLine>Address Line 1</AdrLine>\n" +
            "                </PstlAdr>\n" +
            "                <Id>\n" +
            "                    <OrgId>\n" +
            "                        <LEI>984510B3901DEA58OD34</LEI>\n" +
            "                    </OrgId>\n" +
            "                </Id>\n" +
            "            </Dbtr>\n" +
            "            <DbtrAcct>\n" +
            "                <Id><IBAN>GR4999999990000000123456789</IBAN></Id>\n" +
            "                <Prxy><Tp><Cd>PERS</Cd></Tp><Id>Identification</Id></Prxy>\n" +
            "            </DbtrAcct>\n" +
            "            <DbtrAgt>\n" +
            "                <FinInstnId><BICFI>BCDAGRA0</BICFI></FinInstnId>\n" +
            "                <BrnchId><Id>9991</Id></BrnchId>\n" +
            "            </DbtrAgt>\n" +
            "            <CdtrAgt><FinInstnId><BICFI>ABCDGRA0</BICFI></FinInstnId></CdtrAgt>\n" +
            "            <Cdtr>\n" +
            "                <Nm>Creditor Name 1</Nm>\n" +
            "                <PstlAdr>\n" +
            "                    <Ctry>GR</Ctry>\n" +
            "                    <AdrLine>Creditor Address Line 1</AdrLine>\n" +
            "                </PstlAdr>\n" +
            "                <Id>\n" +
            "                    <OrgId>\n" +
            "                        <Othr><Id>CUST</Id><SchmeNm><Prtry>12345678</Prtry></SchmeNm></Othr>\n" +
            "                    </OrgId>\n" +
            "                </Id>\n" +
            "            </Cdtr>\n" +
            "            <CdtrAcct>\n" +
            "                <Id><IBAN>GR0899999990000000000000001</IBAN></Id>\n" +
            "            </CdtrAcct>\n" +
            "            <UltmtCdtr>\n" +
            "                <Nm>Ultimate Creditor Test 1</Nm>\n" +
            "                <Id>\n" +
            "                    <PrvtId>\n" +
            "                        <Othr><Id>146378691</Id><SchmeNm><Cd>TXID</Cd></SchmeNm></Othr>\n" +
            "                    </PrvtId>\n" +
            "                </Id>\n" +
            "            </UltmtCdtr>\n" +
            "            <Purp><Cd>BENE</Cd></Purp>\n" +
            "            <RmtInf><Ustrd>new message for ISO2019</Ustrd></RmtInf>\n" +
            "        </CdtTrfTxInf>\n" +
            "    </FIToFICstmrCdtTrf>\n" +
            "</Document>";

    private static final String validPacs008SepaDiasCtOutgoingString = "<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08\">\n" +
            "    <FIToFICstmrCdtTrf>\n" +
            "        <GrpHdr>\n" +
            "            <MsgId>18158300000001</MsgId>\n" +
            "            <CreDtTm>2023-06-21T12:12:17.212+03:00</CreDtTm>\n" +
            "            <NbOfTxs>1</NbOfTxs>\n" +
            "            <TtlIntrBkSttlmAmt Ccy='EUR'>10.00</TtlIntrBkSttlmAmt>\n" +
            "            <IntrBkSttlmDt>2023-06-01</IntrBkSttlmDt>\n" +
            "            <SttlmInf>\n" +
            "                <SttlmMtd>CLRG</SttlmMtd>\n" +
            "                <ClrSys><Prtry>DIAS</Prtry></ClrSys>\n" +
            "            </SttlmInf>\n" +
            "            <InstgAgt>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>BCDAGRA0</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </InstgAgt>\n" +
            "        </GrpHdr>\n" +
            "        <CdtTrfTxInf>\n" +
            "            <PmtId>\n" +
            "                <InstrId>18140100000000</InstrId>\n" +
            "                <EndToEndId>From014To016-Cred-1</EndToEndId>\n" +
            "                <TxId>From014To016-Cred-1</TxId>\n" +
            "            </PmtId>\n" +
            "            <PmtTpInf>\n" +
            "                <SvcLvl><Cd>SEPA</Cd></SvcLvl>\n" +
            "                <LclInstrm><Prtry>SCT</Prtry></LclInstrm>\n" +
            "                <CtgyPurp>\n" +
            "                    <Cd>CBLK</Cd>\n" +
            "                </CtgyPurp>\n" +
            "            </PmtTpInf>\n" +
            "            <IntrBkSttlmAmt Ccy=\"EUR\">10.00</IntrBkSttlmAmt>\n" +
            "            <ChrgBr>SLEV</ChrgBr>\n" +
            "            <UltmtDbtr>\n" +
            "                <Nm>Ultimate Debtor Test 1</Nm>\n" +
            "                <Id>\n" +
            "                    <PrvtId>\n" +
            "                        <Othr>\n" +
            "                            <Id>AX035662</Id>\n" +
            "                            <SchmeNm>\n" +
            "                                <Cd>NIDN</Cd>\n" +
            "                            </SchmeNm>\n" +
            "                        </Othr>\n" +
            "                    </PrvtId>\n" +
            "                </Id>\n" +
            "            </UltmtDbtr>\n" +
            "            <Dbtr>\n" +
            "                <Nm>Debtor Name 1</Nm>\n" +
            "                <PstlAdr>\n" +
            "                    <Ctry>GR</Ctry>\n" +
            "                    <AdrLine>Address Line 1</AdrLine>\n" +
            "                </PstlAdr>\n" +
            "                <Id>\n" +
            "                    <OrgId>\n" +
            "                        <LEI>984510B3901DEA58OD34</LEI>\n" +
            "                    </OrgId>\n" +
            "                </Id>\n" +
            "            </Dbtr>\n" +
            "            <DbtrAcct>\n" +
            "                <Id><IBAN>GR4999999990000000123456789</IBAN></Id>\n" +
            "                <Prxy><Tp><Cd>PERS</Cd></Tp><Id>Identification</Id></Prxy>\n" +
            "            </DbtrAcct>\n" +
            "            <DbtrAgt>\n" +
            "                <FinInstnId><BICFI>BCDAGRA0</BICFI></FinInstnId>\n" +
            "                <BrnchId><Id>9991</Id></BrnchId>\n" +
            "            </DbtrAgt>\n" +
            "            <CdtrAgt><FinInstnId><BICFI>ABCDGRA0</BICFI></FinInstnId></CdtrAgt>\n" +
            "            <Cdtr>\n" +
            "                <Nm>Creditor Name 1</Nm>\n" +
            "                <PstlAdr>\n" +
            "                    <Ctry>GR</Ctry>\n" +
            "                    <AdrLine>Creditor Address Line 1</AdrLine>\n" +
            "                </PstlAdr>\n" +
            "                <Id>\n" +
            "                    <OrgId>\n" +
            "                        <Othr><Id>CUST</Id><SchmeNm><Prtry>12345678</Prtry></SchmeNm></Othr>\n" +
            "                    </OrgId>\n" +
            "                </Id>\n" +
            "            </Cdtr>\n" +
            "            <CdtrAcct>\n" +
            "                <Id><IBAN>GR0899999990000000000000001</IBAN></Id>\n" +
            "            </CdtrAcct>\n" +
            "            <UltmtCdtr>\n" +
            "                <Nm>Ultimate Creditor Test 1</Nm>\n" +
            "                <Id>\n" +
            "                    <PrvtId>\n" +
            "                        <Othr><Id>146378691</Id><SchmeNm><Cd>TXID</Cd></SchmeNm></Othr>\n" +
            "                    </PrvtId>\n" +
            "                </Id>\n" +
            "            </UltmtCdtr>\n" +
            "            <Purp><Cd>BENE</Cd></Purp>\n" +
            "            <RmtInf><Ustrd>new message for ISO2019</Ustrd></RmtInf>\n" +
            "        </CdtTrfTxInf>\n" +
            "    </FIToFICstmrCdtTrf>\n" +
            "</Document>";


}