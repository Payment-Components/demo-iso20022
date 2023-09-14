package com.paymentcomponents.swift.mx.sepa.dias.ct.replies;

import gr.datamation.iso20022.sepa.dias.ct.camt.FIToFIPaymentCancellationRequest08SepaDiasCt;
import gr.datamation.iso20022.sepa.dias.ct.camt.ResolutionOfInvestigation09SepaDiasCt;
import gr.datamation.iso20022.sepa.dias.ct.replies.FIToFIPaymentCancellationRequestSepaDiasCtAutoReplies;
import gr.datamation.replies.common.MsgReplyInfo;
import gr.datamation.replies.common.ReasonInformation;
import gr.datamation.validation.error.ValidationError;
import gr.datamation.validation.error.ValidationErrorList;

import java.util.Collections;

public class Camt056SepaDiasCtMessageAutoReplies {

    public static void main(String... args) {
        execute();
    }

    public static void execute() {
        resolutionOfInvestigationAutoReply();
    }

    public static void resolutionOfInvestigationAutoReply() {
        try {
            //Initialize the message object
            FIToFIPaymentCancellationRequest08SepaDiasCt paymentCancellationRequest = new FIToFIPaymentCancellationRequest08SepaDiasCt();
            //Optionally use an existing message if we do not want to create the object from scratch
            paymentCancellationRequest.parseXML(validCamt056SepaDiasCtString);

            //Perform validation
            ValidationErrorList validationErrorList = paymentCancellationRequest.validate();

            if (!validationErrorList.isEmpty())
                throw new Exception("Invalid camt.056 message to resolve");

            FIToFIPaymentCancellationRequestSepaDiasCtAutoReplies<FIToFIPaymentCancellationRequest08SepaDiasCt, ResolutionOfInvestigation09SepaDiasCt> pacs008AutoReplies =
                    new FIToFIPaymentCancellationRequestSepaDiasCtAutoReplies<>(paymentCancellationRequest);

            MsgReplyInfo msgReplyInfo = new MsgReplyInfo();

            ReasonInformation reasonInformation = new ReasonInformation();
            msgReplyInfo.setRsnInf(reasonInformation); //mandatory
            reasonInformation.setType(ReasonInformation.Type.CD); //mandatory
            reasonInformation.setValue("DUPL"); //mandatory
            reasonInformation.setAddtlInf(Collections.singletonList("ATR053/Additional info")); //mandatory

            msgReplyInfo.setReplyId("pacs008Reply"); //if empty, Assignment Id will be used
            msgReplyInfo.setOrgnlTxId("From014To016-Cred-1"); //OrgnlTxId, OrgnlInstrId or both should be present
            msgReplyInfo.setOrgnlInstrId("18140100000000"); //OrgnlTxId, OrgnlInstrId or both should be present

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

    private static final String validCamt056SepaDiasCtString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:camt.056.001.08\">\n" +
            "    <FIToFIPmtCxlReq>\n" +
            "        <Assgnmt>\n" +
            "            <Id>18214000000001</Id>\n" +
            "            <Assgnr>\n" +
            "                <Pty>\n" +
            "                    <Id>\n" +
            "                        <OrgId>\n" +
            "                            <AnyBIC>DIASGRA1</AnyBIC>\n" +
            "                        </OrgId>\n" +
            "                    </Id>\n" +
            "                </Pty>\n" +
            "            </Assgnr>\n" +
            "            <Assgne>\n" +
            "                <Agt>\n" +
            "                    <FinInstnId>\n" +
            "                        <BICFI>ABCDGRA0</BICFI>\n" +
            "                    </FinInstnId>\n" +
            "                </Agt>\n" +
            "            </Assgne>\n" +
            "            <CreDtTm>2023-06-21T14:19:53.736+03:00</CreDtTm>\n" +
            "        </Assgnmt>\n" +
            "        <CtrlData>\n" +
            "            <NbOfTxs>2</NbOfTxs>\n" +
            "        </CtrlData>\n" +
            "        <Undrlyg>\n" +
            "            <TxInf>\n" +
            "                <CxlId>RECA-014to016-23062101</CxlId>\n" +
            "                <OrgnlGrpInf>\n" +
            "                    <OrgnlMsgId>18158300000001</OrgnlMsgId>\n" +
            "                    <OrgnlMsgNmId>pacs.008.001.08</OrgnlMsgNmId>\n" +
            "                </OrgnlGrpInf>\n" +
            "                <OrgnlInstrId>18140100000000</OrgnlInstrId>\n" +
            "                <OrgnlEndToEndId>From014To016-Cred-1</OrgnlEndToEndId>\n" +
            "                <OrgnlTxId>From014To016-Cred-1</OrgnlTxId>\n" +
            "                <OrgnlIntrBkSttlmAmt Ccy=\"EUR\">10.00</OrgnlIntrBkSttlmAmt>\n" +
            "                <OrgnlIntrBkSttlmDt>2023-06-01</OrgnlIntrBkSttlmDt>\n" +
            "                <CxlRsnInf>\n" +
            "                    <Orgtr>\n" +
            "                        <Id>\n" +
            "                            <OrgId>\n" +
            "                                <AnyBIC>BCDAGRA0</AnyBIC>\n" +
            "                            </OrgId>\n" +
            "                        </Id>\n" +
            "                    </Orgtr>\n" +
            "                    <Rsn>\n" +
            "                        <Cd>DUPL</Cd>\n" +
            "                    </Rsn>\n" +
            "                </CxlRsnInf>\n" +
            "                <OrgnlTxRef>\n" +
            "                    <SttlmInf>\n" +
            "                        <SttlmMtd>CLRG</SttlmMtd>\n" +
            "                        <ClrSys>\n" +
            "                            <Prtry>DIAS</Prtry>\n" +
            "                        </ClrSys>\n" +
            "                    </SttlmInf>\n" +
            "                    <PmtTpInf>\n" +
            "                        <SvcLvl>\n" +
            "                            <Cd>SEPA</Cd>\n" +
            "                        </SvcLvl>\n" +
            "                        <LclInstrm>\n" +
            "                            <Prtry>SCT</Prtry>\n" +
            "                        </LclInstrm>\n" +
            "                        <CtgyPurp>\n" +
            "                            <Cd>CBLK</Cd>\n" +
            "                        </CtgyPurp>\n" +
            "                    </PmtTpInf>\n" +
            "                    <RmtInf>\n" +
            "                        <Ustrd>new message for ISO2019</Ustrd>\n" +
            "                    </RmtInf>\n" +
            "                    <UltmtDbtr>\n" +
            "                        <Pty>\n" +
            "                            <Nm>Ultimate Debtor Test 1</Nm>\n" +
            "                            <Id>\n" +
            "                                <PrvtId>\n" +
            "                                    <Othr>\n" +
            "                                        <Id>AX035662</Id>\n" +
            "                                        <SchmeNm>\n" +
            "                                            <Cd>NIDN</Cd>\n" +
            "                                        </SchmeNm>\n" +
            "                                    </Othr>\n" +
            "                                </PrvtId>\n" +
            "                            </Id>\n" +
            "                        </Pty>\n" +
            "                    </UltmtDbtr>\n" +
            "                    <Dbtr>\n" +
            "                        <Pty>\n" +
            "                            <Nm>Debtor Name 1</Nm>\n" +
            "                            <PstlAdr>\n" +
            "                                <Ctry>GR</Ctry>\n" +
            "                                <AdrLine>Address Line 1</AdrLine>\n" +
            "                            </PstlAdr>\n" +
            "                            <Id>\n" +
            "                                <OrgId>\n" +
            "                                    <LEI>984510B3901DEA58OD34</LEI>\n" +
            "                                </OrgId>\n" +
            "                            </Id>\n" +
            "                        </Pty>\n" +
            "                    </Dbtr>\n" +
            "                    <DbtrAcct>\n" +
            "                        <Id>\n" +
            "                            <IBAN>GR4999999990000000123456789</IBAN>\n" +
            "                        </Id>\n" +
            "                        <Prxy>\n" +
            "                            <Tp>\n" +
            "                                <Cd>PERS</Cd>\n" +
            "                            </Tp>\n" +
            "                            <Id>Identification</Id>\n" +
            "                        </Prxy>\n" +
            "                    </DbtrAcct>\n" +
            "                    <DbtrAgt>\n" +
            "                        <FinInstnId>\n" +
            "                            <BICFI>BCDAGRA0</BICFI>\n" +
            "                        </FinInstnId>\n" +
            "                        <BrnchId>\n" +
            "                            <Id>9991</Id>\n" +
            "                        </BrnchId>\n" +
            "                    </DbtrAgt>\n" +
            "                    <CdtrAgt>\n" +
            "                        <FinInstnId>\n" +
            "                            <BICFI>ABCDGRA0</BICFI>\n" +
            "                        </FinInstnId>\n" +
            "                    </CdtrAgt>\n" +
            "                    <Cdtr>\n" +
            "                        <Pty>\n" +
            "                            <Nm>Creditor Name 1</Nm>\n" +
            "                            <PstlAdr>\n" +
            "                                <Ctry>GR</Ctry>\n" +
            "                                <AdrLine>Creditor Address Line 1</AdrLine>\n" +
            "                            </PstlAdr>\n" +
            "                            <Id>\n" +
            "                                <OrgId>\n" +
            "                                    <Othr>\n" +
            "                                        <Id>CUST</Id>\n" +
            "                                        <SchmeNm>\n" +
            "                                            <Prtry>12345678</Prtry>\n" +
            "                                        </SchmeNm>\n" +
            "                                    </Othr>\n" +
            "                                </OrgId>\n" +
            "                            </Id>\n" +
            "                        </Pty>\n" +
            "                    </Cdtr>\n" +
            "                    <CdtrAcct>\n" +
            "                        <Id>\n" +
            "                            <IBAN>GR0899999990000000000000001</IBAN>\n" +
            "                        </Id>\n" +
            "                    </CdtrAcct>\n" +
            "                    <UltmtCdtr>\n" +
            "                        <Pty>\n" +
            "                            <Nm>Ultimate Creditor Test 1</Nm>\n" +
            "                            <Id>\n" +
            "                                <PrvtId>\n" +
            "                                    <Othr>\n" +
            "                                        <Id>146378691</Id>\n" +
            "                                        <SchmeNm>\n" +
            "                                            <Cd>TXID</Cd>\n" +
            "                                        </SchmeNm>\n" +
            "                                    </Othr>\n" +
            "                                </PrvtId>\n" +
            "                            </Id>\n" +
            "                        </Pty>\n" +
            "                    </UltmtCdtr>\n" +
            "                    <Purp>\n" +
            "                        <Cd>BENE</Cd>\n" +
            "                    </Purp>\n" +
            "                </OrgnlTxRef>\n" +
            "            </TxInf>\n" +
            "            <TxInf>\n" +
            "                <CxlId>RECA-014to016-23062102</CxlId>\n" +
            "                <OrgnlGrpInf>\n" +
            "                    <OrgnlMsgId>18158300000001</OrgnlMsgId>\n" +
            "                    <OrgnlMsgNmId>pacs.008.001.08</OrgnlMsgNmId>\n" +
            "                </OrgnlGrpInf>\n" +
            "                <OrgnlInstrId>18140100000010</OrgnlInstrId>\n" +
            "                <OrgnlEndToEndId>From014To016-Cred-2</OrgnlEndToEndId>\n" +
            "                <OrgnlTxId>From014To016-Cred-2</OrgnlTxId>\n" +
            "                <OrgnlIntrBkSttlmAmt Ccy=\"EUR\">20.00</OrgnlIntrBkSttlmAmt>\n" +
            "                <OrgnlIntrBkSttlmDt>2023-06-01</OrgnlIntrBkSttlmDt>\n" +
            "                <CxlRsnInf>\n" +
            "                    <Orgtr>\n" +
            "                        <Id>\n" +
            "                            <OrgId>\n" +
            "                                <AnyBIC>BCDAGRA0</AnyBIC>\n" +
            "                            </OrgId>\n" +
            "                        </Id>\n" +
            "                    </Orgtr>\n" +
            "                    <Rsn>\n" +
            "                        <Cd>DUPL</Cd>\n" +
            "                    </Rsn>\n" +
            "                </CxlRsnInf>\n" +
            "                <OrgnlTxRef>\n" +
            "                    <SttlmInf>\n" +
            "                        <SttlmMtd>CLRG</SttlmMtd>\n" +
            "                        <ClrSys>\n" +
            "                            <Prtry>DIAS</Prtry>\n" +
            "                        </ClrSys>\n" +
            "                    </SttlmInf>\n" +
            "                    <PmtTpInf>\n" +
            "                        <SvcLvl>\n" +
            "                            <Cd>SEPA</Cd>\n" +
            "                        </SvcLvl>\n" +
            "                        <LclInstrm>\n" +
            "                            <Prtry>SCT</Prtry>\n" +
            "                        </LclInstrm>\n" +
            "                        <CtgyPurp>\n" +
            "                            <Prtry>Test</Prtry>\n" +
            "                        </CtgyPurp>\n" +
            "                    </PmtTpInf>\n" +
            "                    <RmtInf>\n" +
            "                        <Ustrd>new message for ISO2019</Ustrd>\n" +
            "                    </RmtInf>\n" +
            "                    <UltmtDbtr>\n" +
            "                        <Pty>\n" +
            "                            <Nm>Ultimate Debtor Test 2</Nm>\n" +
            "                            <Id>\n" +
            "                                <PrvtId>\n" +
            "                                    <Othr>\n" +
            "                                        <Id>AX035662</Id>\n" +
            "                                        <SchmeNm>\n" +
            "                                            <Cd>NIDN</Cd>\n" +
            "                                        </SchmeNm>\n" +
            "                                    </Othr>\n" +
            "                                </PrvtId>\n" +
            "                            </Id>\n" +
            "                        </Pty>\n" +
            "                    </UltmtDbtr>\n" +
            "                    <Dbtr>\n" +
            "                        <Pty>\n" +
            "                            <Nm>Debtor Name 2</Nm>\n" +
            "                            <PstlAdr>\n" +
            "                                <Flr>Floor 2</Flr>\n" +
            "                                <TwnNm>MAROUSI</TwnNm>\n" +
            "                                <Ctry>GR</Ctry>\n" +
            "                            </PstlAdr>\n" +
            "                            <Id>\n" +
            "                                <OrgId>\n" +
            "                                    <AnyBIC>BCDAGRA0</AnyBIC>\n" +
            "                                </OrgId>\n" +
            "                            </Id>\n" +
            "                        </Pty>\n" +
            "                    </Dbtr>\n" +
            "                    <DbtrAcct>\n" +
            "                        <Id>\n" +
            "                            <IBAN>GR4999999990000000123456789</IBAN>\n" +
            "                        </Id>\n" +
            "                    </DbtrAcct>\n" +
            "                    <DbtrAgt>\n" +
            "                        <FinInstnId>\n" +
            "                            <BICFI>BCDAGRA0</BICFI>\n" +
            "                        </FinInstnId>\n" +
            "                        <BrnchId>\n" +
            "                            <Id>9991</Id>\n" +
            "                        </BrnchId>\n" +
            "                    </DbtrAgt>\n" +
            "                    <CdtrAgt>\n" +
            "                        <FinInstnId>\n" +
            "                            <BICFI>ABCDGRA0</BICFI>\n" +
            "                        </FinInstnId>\n" +
            "                    </CdtrAgt>\n" +
            "                    <Cdtr>\n" +
            "                        <Pty>\n" +
            "                            <Nm>Creditor Name 2</Nm>\n" +
            "                            <PstlAdr>\n" +
            "                                <Dept>Department 2</Dept>\n" +
            "                                <StrtNm>Alamanas 1</StrtNm>\n" +
            "                                <TwnNm>MAROUSI</TwnNm>\n" +
            "                                <Ctry>GR</Ctry>\n" +
            "                            </PstlAdr>\n" +
            "                            <Id>\n" +
            "                                <OrgId>\n" +
            "                                    <Othr>\n" +
            "                                        <Id>CUST</Id>\n" +
            "                                        <SchmeNm>\n" +
            "                                            <Prtry>12345678</Prtry>\n" +
            "                                        </SchmeNm>\n" +
            "                                    </Othr>\n" +
            "                                </OrgId>\n" +
            "                            </Id>\n" +
            "                        </Pty>\n" +
            "                    </Cdtr>\n" +
            "                    <CdtrAcct>\n" +
            "                        <Id>\n" +
            "                            <IBAN>GR0899999990000000000000001</IBAN>\n" +
            "                        </Id>\n" +
            "                    </CdtrAcct>\n" +
            "                    <UltmtCdtr>\n" +
            "                        <Pty>\n" +
            "                            <Nm>Ultimate Creditor Test 1</Nm>\n" +
            "                            <Id>\n" +
            "                                <PrvtId>\n" +
            "                                    <Othr>\n" +
            "                                        <Id>146378691</Id>\n" +
            "                                        <SchmeNm>\n" +
            "                                            <Cd>TXID</Cd>\n" +
            "                                        </SchmeNm>\n" +
            "                                    </Othr>\n" +
            "                                </PrvtId>\n" +
            "                            </Id>\n" +
            "                        </Pty>\n" +
            "                    </UltmtCdtr>\n" +
            "                    <Purp>\n" +
            "                        <Cd>BENE</Cd>\n" +
            "                    </Purp>\n" +
            "                </OrgnlTxRef>\n" +
            "            </TxInf>\n" +
            "        </Undrlyg>\n" +
            "    </FIToFIPmtCxlReq>\n" +
            "</Document>";

}