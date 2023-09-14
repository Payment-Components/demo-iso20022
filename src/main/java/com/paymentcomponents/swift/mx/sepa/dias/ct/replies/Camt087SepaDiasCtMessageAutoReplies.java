package com.paymentcomponents.swift.mx.sepa.dias.ct.replies;

import gr.datamation.iso20022.sepa.dias.ct.camt.RequestToModifyPayment06SepaDiasCt;
import gr.datamation.iso20022.sepa.dias.ct.camt.ResolutionOfInvestigation09SepaDiasCt;
import gr.datamation.iso20022.sepa.dias.ct.replies.RequestToModifyPaymentSepaDiasCtAutoReplies;
import gr.datamation.replies.common.ChargesInformation;
import gr.datamation.replies.common.CompensationInformation;
import gr.datamation.replies.common.MsgReplyInfo;
import gr.datamation.replies.common.ReasonInformation;
import gr.datamation.validation.error.ValidationError;
import gr.datamation.validation.error.ValidationErrorList;

import java.math.BigDecimal;
import java.util.Collections;

public class Camt087SepaDiasCtMessageAutoReplies {

    public static void main(String... args) {
        execute();
    }

    public static void execute() {
        resolutionOfInvestigationAutoReply();
    }

    public static void resolutionOfInvestigationAutoReply() {
        try {
            //Initialize the message object
            RequestToModifyPayment06SepaDiasCt requestToModifyPayment = new RequestToModifyPayment06SepaDiasCt();
            //Optionally use an existing message if we do not want to create the object from scratch
            requestToModifyPayment.parseXML(validCamt087SepaDiasCtString);

            //Perform validation
            ValidationErrorList validationErrorList = requestToModifyPayment.validate();

            if (!validationErrorList.isEmpty())
                throw new Exception("Invalid camt.087 message to resolve");

            RequestToModifyPaymentSepaDiasCtAutoReplies<RequestToModifyPayment06SepaDiasCt, ResolutionOfInvestigation09SepaDiasCt> pacs008AutoReplies =
                    new RequestToModifyPaymentSepaDiasCtAutoReplies<>(requestToModifyPayment);

            MsgReplyInfo msgReplyInfo = new MsgReplyInfo();

            ReasonInformation reasonInformation = new ReasonInformation();
            msgReplyInfo.setRsnInf(reasonInformation); //mandatory
            reasonInformation.setValue("ACVA"); //mandatory, allowed values are "ACVA", "RJVA", "CVAA", "MODI"

            msgReplyInfo.setReplyId("pacs008Reply"); //if empty, Assignment Id will be used

            //optional
            ChargesInformation chargesInformation = new ChargesInformation();
            chargesInformation.setAmount(new BigDecimal("1")); //mandatory
            chargesInformation.setAgentBic("TESTBICA"); //if empty, creditor agent from original transaction will be used
            chargesInformation.setAgentAccount("12321543543"); //mandatory
            msgReplyInfo.setChargesInformation(Collections.singletonList(chargesInformation));

            //optional
            CompensationInformation compensationInformation = new CompensationInformation();
            compensationInformation.setAmount(new BigDecimal("1")); //mandatory
            compensationInformation.setAgentAccount("12321543543"); //mandatory
//            msgReplyInfo.setCompensationInformation(compensationInformation);

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

    private static final String validCamt087SepaDiasCtString = "<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:camt.087.001.06\">\n" +
            "    <ReqToModfyPmt>\n" +
            "        <Assgnmt>\n" +
            "            <Id>msgId</Id>\n" +
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
            "            <CreDtTm>2008-09-29T04:49:45</CreDtTm>\n" +
            "        </Assgnmt>\n" +
            "        <Case>\n" +
            "            <Id>caseId</Id>\n" +
            "            <Cretr>\n" +
            "                <Agt>\n" +
            "                    <FinInstnId>\n" +
            "                        <BICFI>TESTBICA</BICFI>\n" +
            "                    </FinInstnId>\n" +
            "                </Agt>\n" +
            "            </Cretr>\n" +
            "            <ReopCaseIndctn>true</ReopCaseIndctn>\n" +
            "        </Case>\n" +
            "        <Undrlyg>\n" +
            "            <IntrBk>\n" +
            "                <OrgnlGrpInf>\n" +
            "                    <OrgnlMsgId>msgId</OrgnlMsgId>\n" +
            "                    <OrgnlMsgNmId>pacs.008.001.08</OrgnlMsgNmId>\n" +
            "                    <OrgnlCreDtTm>2014-06-09T18:15:04+03:00</OrgnlCreDtTm>\n" +
            "                    <OrgnlMsgDlvryChanl>string</OrgnlMsgDlvryChanl>\n" +
            "                </OrgnlGrpInf>\n" +
            "                <OrgnlInstrId>instrId</OrgnlInstrId>\n" +
            "                <OrgnlEndToEndId>endId</OrgnlEndToEndId>\n" +
            "                <OrgnlTxId>txId</OrgnlTxId>\n" +
            "                <OrgnlUETR>ceca3ff2-1dc4-4164-8cc9-ad52c4c5210d</OrgnlUETR>\n" +
            "                <OrgnlIntrBkSttlmAmt Ccy=\"EUR\">1000.00</OrgnlIntrBkSttlmAmt>\n" +
            "                <OrgnlIntrBkSttlmDt>2009-05-16</OrgnlIntrBkSttlmDt>\n" +
            "                <OrgnlTxRef>\n" +
            "                    <IntrBkSttlmAmt Ccy=\"EUR\">1000.00</IntrBkSttlmAmt>\n" +
            "                    <Amt>\n" +
            "                        <InstdAmt Ccy=\"EUR\">1000.00</InstdAmt>\n" +
            "                    </Amt>\n" +
            "                    <SttlmInf>\n" +
            "                        <SttlmMtd>CLRG</SttlmMtd>\n" +
            "                        <ClrSys>\n" +
            "                            <Prtry>ST2</Prtry>\n" +
            "                        </ClrSys>\n" +
            "                    </SttlmInf>\n" +
            "                    <PmtTpInf>\n" +
            "                        <InstrPrty>HIGH</InstrPrty>\n" +
            "                        <ClrChanl>RTNS</ClrChanl>\n" +
            "                        <SvcLvl>\n" +
            "                            <Cd>stri</Cd>\n" +
            "                        </SvcLvl>\n" +
            "                        <LclInstrm>\n" +
            "                            <Cd>string</Cd>\n" +
            "                        </LclInstrm>\n" +
            "                        <CtgyPurp>\n" +
            "                            <Cd>stri</Cd>\n" +
            "                        </CtgyPurp>\n" +
            "                    </PmtTpInf>\n" +
            "                    <PmtMtd>TRF</PmtMtd>\n" +
            "                    <RmtInf>\n" +
            "                        <Ustrd>string</Ustrd>\n" +
            "                    </RmtInf>\n" +
            "                    <Dbtr>\n" +
            "                        <Pty>\n" +
            "                            <Nm>dbtr nm</Nm>\n" +
            "                            <PstlAdr>\n" +
            "                                <AdrTp>\n" +
            "                                    <Cd>ADDR</Cd>\n" +
            "                                </AdrTp>\n" +
            "                                <Dept>string</Dept>\n" +
            "                                <SubDept>string</SubDept>\n" +
            "                                <StrtNm>string</StrtNm>\n" +
            "                                <BldgNb>string</BldgNb>\n" +
            "                                <BldgNm>string</BldgNm>\n" +
            "                                <Flr>string</Flr>\n" +
            "                                <PstBx>string</PstBx>\n" +
            "                                <Room>string</Room>\n" +
            "                                <PstCd>string</PstCd>\n" +
            "                                <TwnNm>string</TwnNm>\n" +
            "                                <TwnLctnNm>string</TwnLctnNm>\n" +
            "                                <DstrctNm>string</DstrctNm>\n" +
            "                                <CtrySubDvsn>string</CtrySubDvsn>\n" +
            "                                <Ctry>GR</Ctry>                <!--0 to 2 repetitions:-->\n" +
            "                                <AdrLine>string</AdrLine>\n" +
            "                            </PstlAdr>\n" +
            "                            <Id>\n" +
            "                                <PrvtId>\n" +
            "                                    <DtAndPlcOfBirth>\n" +
            "                                        <BirthDt>2000-02-21</BirthDt>\n" +
            "                                        <PrvcOfBirth>string</PrvcOfBirth>\n" +
            "                                        <CityOfBirth>ATH</CityOfBirth>\n" +
            "                                        <CtryOfBirth>GR</CtryOfBirth>\n" +
            "                                    </DtAndPlcOfBirth>\n" +
            "                                    <Othr>\n" +
            "                                        <Id>string</Id>\n" +
            "                                        <SchmeNm>\n" +
            "                                            <Cd>stri</Cd>\n" +
            "                                        </SchmeNm>\n" +
            "                                        <Issr>string</Issr>\n" +
            "                                    </Othr>\n" +
            "                                </PrvtId>\n" +
            "                            </Id>\n" +
            "                            <CtryOfRes>GR</CtryOfRes>\n" +
            "                        </Pty>\n" +
            "                    </Dbtr>\n" +
            "                    <DbtrAcct>\n" +
            "                        <Id>\n" +
            "                            <IBAN>CH1708841000987654321</IBAN>\n" +
            "                        </Id>\n" +
            "                        <Tp>\n" +
            "                            <Cd>stri</Cd>\n" +
            "                        </Tp>\n" +
            "                        <Ccy>EUR</Ccy>\n" +
            "                        <Nm>string</Nm>\n" +
            "                        <Prxy>\n" +
            "                            <Tp>\n" +
            "                                <Cd>stri</Cd>\n" +
            "                            </Tp>\n" +
            "                            <Id>string</Id>\n" +
            "                        </Prxy>\n" +
            "                    </DbtrAcct>\n" +
            "                    <DbtrAgt>\n" +
            "                        <FinInstnId>\n" +
            "                            <BICFI>TESTBICA</BICFI>\n" +
            "                        </FinInstnId>\n" +
            "                    </DbtrAgt>\n" +
            "                    <DbtrAgtAcct>\n" +
            "                        <Id>\n" +
            "                            <IBAN>CH1708841000987654321</IBAN>\n" +
            "                        </Id>\n" +
            "                        <Tp>\n" +
            "                            <Cd>stri</Cd>\n" +
            "                        </Tp>\n" +
            "                        <Ccy>EUR</Ccy>\n" +
            "                        <Nm>string</Nm>\n" +
            "                        <Prxy>\n" +
            "                            <Tp>\n" +
            "                                <Cd>stri</Cd>\n" +
            "                            </Tp>\n" +
            "                            <Id>string</Id>\n" +
            "                        </Prxy>\n" +
            "                    </DbtrAgtAcct>\n" +
            "                    <CdtrAgt>\n" +
            "                        <FinInstnId>\n" +
            "                            <BICFI>TESTBICA</BICFI>\n" +
            "                        </FinInstnId>\n" +
            "                    </CdtrAgt>\n" +
            "                    <CdtrAgtAcct>\n" +
            "                        <Id>\n" +
            "                            <IBAN>CH1708841000987654321</IBAN>\n" +
            "                        </Id>\n" +
            "                        <Tp>\n" +
            "                            <Cd>stri</Cd>\n" +
            "                        </Tp>\n" +
            "                        <Ccy>EUR</Ccy>\n" +
            "                        <Nm>string</Nm>\n" +
            "                        <Prxy>\n" +
            "                            <Tp>\n" +
            "                                <Cd>stri</Cd>\n" +
            "                            </Tp>\n" +
            "                            <Id>string</Id>\n" +
            "                        </Prxy>\n" +
            "                    </CdtrAgtAcct>\n" +
            "                    <Purp>\n" +
            "                        <Cd>stri</Cd>\n" +
            "                    </Purp>\n" +
            "                </OrgnlTxRef>\n" +
            "            </IntrBk>\n" +
            "        </Undrlyg>\n" +
            "        <Mod>\n" +
            "            <IntrBkSttlmDt>2015-07-10</IntrBkSttlmDt>\n" +
            "        </Mod>\n" +
            "    </ReqToModfyPmt>\n" +
            "</Document>";

}