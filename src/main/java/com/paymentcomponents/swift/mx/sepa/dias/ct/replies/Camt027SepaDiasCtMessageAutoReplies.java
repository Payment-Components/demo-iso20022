package com.paymentcomponents.swift.mx.sepa.dias.ct.replies;

import gr.datamation.iso20022.sepa.dias.ct.camt.ClaimNonReceipt07SepaDiasCt;
import gr.datamation.iso20022.sepa.dias.ct.camt.ResolutionOfInvestigation09SepaDiasCt;
import gr.datamation.iso20022.sepa.dias.ct.replies.ClaimNonReceiptSepaDiasCtAutoReplies;
import gr.datamation.replies.common.MsgReplyInfo;
import gr.datamation.replies.common.ReasonInformation;
import gr.datamation.validation.error.ValidationError;
import gr.datamation.validation.error.ValidationErrorList;

import java.time.LocalDate;import java.util.Collections;

public class Camt027SepaDiasCtMessageAutoReplies {

    public static void main(String... args) {
        execute();
    }

    public static void execute() {
        resolutionOfInvestigationAutoReply();
    }

    public static void resolutionOfInvestigationAutoReply() {
        try {
            //Initialize the message object
            ClaimNonReceipt07SepaDiasCt claimNonReceipt = new ClaimNonReceipt07SepaDiasCt();
            //Optionally use an existing message if we do not want to create the object from scratch
            claimNonReceipt.parseXML(validCamt027SepaDiasCtString);

            //Perform validation
            ValidationErrorList validationErrorList = claimNonReceipt.validate();

            if (!validationErrorList.isEmpty())
                throw new Exception("Invalid camt.027 message to resolve");

            ClaimNonReceiptSepaDiasCtAutoReplies<ClaimNonReceipt07SepaDiasCt, ResolutionOfInvestigation09SepaDiasCt> pacs008AutoReplies =
                    new ClaimNonReceiptSepaDiasCtAutoReplies<>(claimNonReceipt);

            MsgReplyInfo msgReplyInfo = new MsgReplyInfo();

            ReasonInformation reasonInformation = new ReasonInformation();
            msgReplyInfo.setRsnInf(reasonInformation); //optional, if empty, claim will be accepted
            reasonInformation.setType(ReasonInformation.Type.CD); //mandatory
            reasonInformation.setValue("NOOR"); //mandatory

            msgReplyInfo.setReplyId("pacs008Reply"); //if empty, Assignment Id will be used

            msgReplyInfo.setIntrBkSttlmDt(LocalDate.now()); //optional, in case of acceptance, if not used, current day will be used

            //optional, only in case of acceptance is allowed
//            ChargesInformation chargesInformation = new ChargesInformation();
//            chargesInformation.setAmount(new BigDecimal("1")); //mandatory
//            chargesInformation.setAgentBic("TESTBICA"); //if empty, creditor agent from original transaction will be used
//            chargesInformation.setAgentAccount("12321543543"); //mandatory
//            msgReplyInfo.setChargesInformation(Collections.singletonList(chargesInformation));

            ResolutionOfInvestigation09SepaDiasCt camt029 = pacs008AutoReplies.autoReply(new ResolutionOfInvestigation09SepaDiasCt(), Collections.singletonList(msgReplyInfo));
            validationErrorList = camt029.validate();

            if (validationErrorList.isEmpty()) {
                System.out.println("Resolution Message is valid");
                System.out.println(camt029.convertToXML()); //Get the generated xml
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

    private static final String validCamt027SepaDiasCtString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:camt.027.001.07\">\n" +
            "    <ClmNonRct>\n" +
            "        <Assgnmt>\n" +
            "            <Id>CNRVVVVGB2L201203020001</Id>\n" +
            "            <Assgnr>\n" +
            "                <Pty>\n" +
            "                    <Id>\n" +
            "                        <OrgId>\n" +
            "                            <AnyBIC>DIASGRAA</AnyBIC>\n" +
            "                        </OrgId>\n" +
            "                    </Id>\n" +
            "                </Pty>\n" +
            "            </Assgnr>\n" +
            "            <Assgne>\n" +
            "                <Agt>\n" +
            "                    <FinInstnId>\n" +
            "                        <BICFI>FFFFFRPP</BICFI>\n" +
            "                    </FinInstnId>\n" +
            "                </Agt>\n" +
            "            </Assgne>\n" +
            "            <CreDtTm>2012-03-06T10:35:23</CreDtTm>\n" +
            "        </Assgnmt>\n" +
            "        <Case>\n" +
            "            <Id>CNR0903PAY09876543</Id>\n" +
            "            <Cretr>\n" +
            "                <Agt>\n" +
            "                    <FinInstnId>\n" +
            "                        <BICFI>UUUUGB2L</BICFI>\n" +
            "                    </FinInstnId>\n" +
            "                </Agt>\n" +
            "            </Cretr>\n" +
            "        </Case>\n" +
            "        <Undrlyg>\n" +
            "            <IntrBk>\n" +
            "                <OrgnlGrpInf>\n" +
            "                    <OrgnlMsgId>345601234</OrgnlMsgId>\n" +
            "                    <OrgnlMsgNmId>pacs.008.001.08</OrgnlMsgNmId>\n" +
            "                </OrgnlGrpInf>\n" +
            "                <OrgnlInstrId>345601234</OrgnlInstrId>\n" +
            "                <OrgnlEndToEndId>str0123456789</OrgnlEndToEndId>\n" +
            "                <OrgnlTxId>str12345678</OrgnlTxId>\n" +
            "                <OrgnlIntrBkSttlmAmt Ccy=\"EUR\">13498.52</OrgnlIntrBkSttlmAmt>\n" +
            "                <OrgnlIntrBkSttlmDt>2012-03-01</OrgnlIntrBkSttlmDt>\n" +
            "                <OrgnlTxRef>\n" +
            "                    <DbtrAgt>\n" +
            "                        <FinInstnId>\n" +
            "                            <BICFI>UUUUGB2L</BICFI>\n" +
            "                        </FinInstnId>\n" +
            "                    </DbtrAgt>\n" +
            "                    <CdtrAgt>\n" +
            "                        <FinInstnId>\n" +
            "                            <BICFI>FFFFFRPP</BICFI>\n" +
            "                        </FinInstnId>\n" +
            "                    </CdtrAgt>\n" +
            "                </OrgnlTxRef>\n" +
            "            </IntrBk>\n" +
            "        </Undrlyg>\n" +
            "    </ClmNonRct>\n" +
            "</Document>\n";

}