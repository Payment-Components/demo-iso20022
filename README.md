<a id="logo" href="https://www.paymentcomponents.com" title="Payment Components" target="_blank">
    <img loading="lazy" src="https://i.postimg.cc/yN5TNy29/LOGO-HORIZONTAL2.png" alt="Payment Components">
</a>

# ISO20022 Message Validator Demo

This project is part of the [FINaplo](https://finaplo.paymentcomponents.com) product and is here to demonstrate how our [SDK](https://finaplo.paymentcomponents.com/financial-messages) for SWIFT MX (ISO20022) Messages Validation works. 
For our demonstration we are going to use the demo SDK which can parse/validate/generate a pacs.002.001.XX message. 

It's a simple maven project, you can download it and run it, with Java 1.8 or above.

## Table of Contents
1. [SDK Setup](#sdk-setup)
2. [HOW-TO Use the SDK](#how-to-use-the-sdk)
3. [Auto Replies](#auto-replies)
4. [Universal Confirmations](#universal-confirmations)
3. [CBPR+ Messages](#cbpr-messages)
4. [SCRIPS (MEPS+) messages](#scrips-meps-messages)
5. [MEPS+ Like4Like messages](#meps-like4like-messages)
6. [TARGET2 (RTGS) messages](#target2-rtgs-messages)
7. [FedNow messages](#fednow-messages)
8. [SIC/euroSIC messages](#siceurosic-messages)
9. [BAHTNET messages](#bahtnet-messages)
10. [CGI-MP messages](#CGI-MP-messages)
11. [Swiftcase messages](#swiftcase-messages)
12. SEPA Messages
    - [SEPA-EPC Credit Transfer](#sepa-epc-credit-transfer)
    - [SEPA-EPC Direct Debit](#sepa-epc-direct-debit)
    - [SEPA-EPC Instant Payment](#sepa-epc-instant-payment)
    - [SEPA-EBA Credit Transfer](#sepa-eba-credit-transfer)
    - [SEPA-DIAS Credit Transfer](#sepa-dias-credit-transfer)
    - [SEPA-SIBS Credit Transfer](#sepa-sibs-credit-transfer)
    - [SEPA-SIBS Direct Debit](#sepa-sibs-direct-debit)

## SDK setup
Incorporate the SDK [jar](https://nexus.paymentcomponents.com/repository/public/gr/datamation/mx/mx/24.20.0/mx-24.20.0-demo.jar) into your project by the regular IDE means. 
This process will vary depending upon your specific IDE and you should consult your documentation on how to deploy a bean. 
For example in Eclipse all that needs to be done is to import the jar files into a project.
Alternatively, you can import it as a Maven or Gradle dependency.  

##### Maven
Define repository in the repositories section
```xml
<repository>
    <id>paymentcomponents</id>
    <url>https://nexus.paymentcomponents.com/repository/public</url>
</repository>
```
Import the SDK
```xml
<dependency>
    <groupId>gr.datamation.mx</groupId>
    <artifactId>mx</artifactId>
    <version>24.20.0</version>
    <classifier>demo</classifier>
</dependency>
```

##### Gradle 
Define repository in the repositories section
```groovy
repositories {
    maven {
        url "https://nexus.paymentcomponents.com/repository/public"
    }
}
```
Import the SDK git push https://gantoniadispc14:hGgxJztpi8HNFTZ@github.com/Payment-Components/demo-iso20022.git main

```groovy
implementation 'gr.datamation.mx:mx:24.20.0:demo@jar'
```
In case you purchase the SDK you will be given a protected Maven repository with a user name and a password. You can configure your project to download the SDK from there.

#### Other dependencies
There is a dependency in [groovy-all](https://mvnrepository.com/artifact/org.codehaus.groovy/groovy-all/2.4.8) library which is required for some of the included features (version 2.4.8 or later). 
There is also a dependency in [classgraph](https://mvnrepository.com/artifact/io.github.classgraph/classgraph/4.8.153) library which is required for some of the included features (version 4.8.153 or later). 
You can use maven or gradle to add the dependencies below or manually include the jar to your project.

##### Maven 
```xml
<dependency>
    <groupId>org.codehaus.groovy</groupId>
    <artifactId>groovy-all</artifactId>
    <version>2.4.8</version>
</dependency>
<dependency>
   <groupId>io.github.classgraph</groupId>
   <artifactId>classgraph</artifactId>
   <version>4.8.153</version>
</dependency>

```
##### Gradle 
```groovy
compile group: 'org.codehaus.groovy', name: 'groovy-all', version: '2.4.8'
compile group: 'io.github.classgraph', name: 'classgraph', version: '4.8.153'
```

## HOW-TO Use the SDK

All ISO20022 messages are identified by a code id and a name. The code id (`FIToFIPmtStsRpt`) and the name (`FIToFIPaymentStatusReportV11`) are located in the .xsd file that describes the XML schema of each message. 
Both the name and the code id of the message are available in the ISO20022 messages catalogue.

*Inside pacs.002.001.11.xsd*
```xml
<xs:complexType name="Document">
    <xs:sequence>
        <xs:element name="FIToFIPmtStsRpt" type="FIToFIPaymentStatusReportV11"/>
    </xs:sequence>
</xs:complexType>
```

### Message objects
For every ISO20022 message there is an equivalent class in our SDK. The name of the class is equivalent to the name of the message. 
For example the name of the class for the message named `FIToFIPaymentStatusReportV11` is `FIToFIPaymentStatusReport11` (using version `11` of `FIToFIPmtStsRpt`).
Each message has its own versions which are all maintained according to the yearly ISO20022 guidelines.   

#### Building and validating messages
There are three steps the user must follow in order to build a new Swift MX message:

1. ##### Initialize the class corresponding to the message.
    The initialization of the class is as simple as initializing any class in Java. For the example message we are using here (FIToFIPaymentStatusReport11) the initialization would be
    ```java
    FIToFIPaymentStatusReport11 message = new FIToFIPaymentStatusReport11();
    ```
    The above command will initialize a class for this message named FIToFIPaymentStatusReport11 which is initially empty.  
    
    Validating a text
    ```java
    ValidationErrorList errorList = message.validateXML(new ByteArrayInputStream("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                                                                 "<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pacs.002.001.11\">\n" +
                                                                                 "  <FIToFIPmtStsRpt>\n" +
                                                                                 "     ............\n" +
                                                                                 "  </FIToFIPmtStsRpt>\n" +
                                                                                 "</Document>".getBytes()));
   ```
    Parsing a text
    ```java
    message.parseXML("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                     "<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pacs.002.001.11\">\n" +
                     "  <FIToFIPmtStsRpt>\n" +
                     "     ............\n" +
                     "  </FIToFIPmtStsRpt>\n" +
                     "</Document>");
    ```

    ###### Auto parse and validate
    In case we do not know the class of the message, we can use the auto parse and validate from the xml.
    Auto validating a text
    ```java
    ValidationErrorList errorList = MXUtils.autoValidateXML(new ByteArrayInputStream("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                                                                     "<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pacs.002.001.11\">\n" +
                                                                                     "  <FIToFIPmtStsRpt>\n" +
                                                                                     "     ............\n" +
                                                                                     "  </FIToFIPmtStsRpt>\n" +
                                                                                     "</Document>".getBytes()));
    ```
    Auto parsing a text
    ```java
    Message message = MXUtils.autoParseXML("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                            "<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pacs.002.001.11\">\n" +
                                            "  <FIToFIPmtStsRpt>\n" +
                                            "     ............\n" +
                                            "  </FIToFIPmtStsRpt>\n" +
                                            "</Document>");
    ```

2. ##### Add data to the class.
    The next step is to add data to the new message. In order to add some data to the message, user must know which element in the message tree he wants to add. 
    The element he wants to add is identified by an XML path. The value of the element user wants to add may be a String, a Boolean or a complex type that is described in the SWIFT MX type catalog.
    
    So to enter some data into the message, user must call the following method of the previously instantiated object
    where the path argument is a String identifying the element to add and the value argument is an Object.
    ```java
    message.setElement("path/to/field", "value");
    ```  
    User can also work with the XSD defined classes that represent a tag. e.g. GroupHeader91 for GrpHdr tag.
    ```java
    message.getMessage().setGrpHdr(new GroupHeader91()); // setGrpHdr() method accepts iso.pacs_002_001_11.GroupHeader91 objects
    ```
    
3. ##### Validate the message.
    After building a Swift MX message using the appropriate class, user may want to validate this message. Of course validation is not mandatory but is the only way to prove that the message is correct. 
    Validation is performed by calling the validate() method and internally is a two step process:
    - Validation against the schema of the message in order to ensure that the message is a well-formed instance of it.  
    - Validation against any Validation Rule as described for that message by the ISO20022 rules.  
    
    The validate() method returns an ArrayList containing the validation errors that may occur. 
    Each error is contained in the ArrayList as a ValidationError object describing the error.
    
    ```java
    ValidationErrorList errorList = message.validate();
    ```
   
#### Resolve Paths
For each message object we can call the `resolvePaths()` method on the object.  
This method returns the xml as paths, from root element to the leaf.  
It returns a List and each element of the List is a String array consisted of 3 elements:
1. Field Path excluding the leaf code
2. Leaf code
3. Leaf value
4. Leaf attributes (e.g. `Ccy=EUR;`)

For example if we call the `resolvePaths()` on a `pacs.002.001.12`
```java
FIToFIPaymentStatusReport12 pacs002 = new FIToFIPaymentStatusReport12();
//fill the message object or parse from an xml
List<String[]> paths = pacs002.resolvePaths();
for (String[] path: paths)
        System.out.println("Field path: " + path[0] + " | " + "Field code: " + path[1] +  " | " + "Field value: " + path[2] + " | " + "Field attributes: " + path[3]);
```
part of the output we will receive is
```
Field path: /Document/FIToFIPmtStsRpt/GrpHdr | Field code: MsgId | Field value: ABABUS23-STATUS-456/04 | Field attributes: null
Field path: /Document/FIToFIPmtStsRpt/GrpHdr | Field code: CreDtTm | Field value: 2015-06-29T09:56:00 | Field attributes: null
Field path: /Document/FIToFIPmtStsRpt/OrgnlGrpInfAndSts | Field code: OrgnlMsgId | Field value: AAAA100628-123v | Field attributes: null
Field path: /Document/FIToFIPmtStsRpt/OrgnlGrpInfAndSts | Field code: OrgnlMsgNmId | Field value: pacs.003.001.09 | Field attributes: null
Field path: /Document/FIToFIPmtStsRpt/TxInfAndSts | Field code: OrgnlEndToEndId | Field value: VA060327/0123 | Field attributes: null
Field path: /Document/FIToFIPmtStsRpt/TxInfAndSts | Field code: OrgnlTxId | Field value: AAAAUS29/100628/ad458 | Field attributes: null
Field path: /Document/FIToFIPmtStsRpt/TxInfAndSts | Field code: TxSts | Field value: RJCT | Field attributes: null
```
In case the child of an xml element is an array, we will receive an index of the element.  
In our case, if pacs.002 had more than one `FIToFIPmtStsRpt`, the path would have an index. E.g.
```
/Document/FIToFIPmtStsRpt[0]/TxInfAndSts
/Document/FIToFIPmtStsRpt[1]/TxInfAndSts
```

#### Get Element
For each message object we can call the `getElement()` method on the object.  
This method receives a field path until leaf and returns the field value. The value could be a simple String or a complex Object.  
For example we can call the below in a pacs.002 message
```java
pacs002.getElement("TxInfAndSts[0]/TxSts");
```
the output will be a `"RJCT"` String. If we call
```java
pacs002.getElement("TxInfAndSts[0]");
```
the output will be a `PaymentTransaction130` Object.  
The use of index is necessary in case the element is an array.

### Code Samples

In this project you can see code for all the basic manipulation of an MX message, like:
- [Parse and validate valid pacs.002](src/main/java/com/paymentcomponents/swift/mx/ParseAndValidateValidPacs002_12.java)
- [Parse and validate an invalid pacs.002 (get syntax, network validation errors)](src/main/java/com/paymentcomponents/swift/mx/ParseAndValidateInvalidPacs002_12.java)
- [Build a valid pacs.002](src/main/java/com/paymentcomponents/swift/mx/BuildValidPacs002_12.java)
- [Convert an MX message to XML](src/main/java/com/paymentcomponents/swift/mx/ConvertMX2XML.java)

## Auto Replies

### Additional Dependencies

In order to use auto-replies, the use of `gson` is required
**Maven**
```xml
<dependency>
   <groupId>com.google.code.gson</groupId>
   <artifactId>gson</artifactId>
   <version>2.8.9</version>
</dependency>
```

The library supports the creation of reply message. For example, for a `pacs.008` message, you can create a `pacs.004` message.  
The correspondent classes belong to `gr.datamation.replies` package and extends the `CoreMessageAutoReplies` abstract Class.  
This class contains the `abstract <R extends CoreMessage> R autoReply(R replyMessage, List<MsgReplyInfo> msgReplyInfo)` method.  
`MsgReplyInfo` contains information for the reply and is a list because some reply messages may contain many transactions.  
For example, for a `pacs.008.001.10`, the class for replies should be `FIToFICustomerCreditTransferAutoReplies` that extends `CoreMessageAutoReplies`.  
We initiate this class with the source message.  
For a `pacs.008.001.10`, the initialization should be:
```java
FIToFICustomerCreditTransfer10 pacs008 = new FIToFICustomerCreditTransfer10();
//fill the message object or parse from an xml
FIToFICustomerCreditTransferAutoReplies<FIToFICustomerCreditTransfer10, PaymentReturn11> pacs008Replies = 
    new FIToFICustomerCreditTransferAutoReplies<>(pacs008);
```
If we want to create a `pacs.004.001.11` reply for this pacs.008, we should call the `autoReply()` like this:
```java
MsgReplyInfo msgReplyInfo = new MsgReplyInfo();
ReasonInformation reasonInformation = new ReasonInformation();
msgReplyInfo.setRsnInf(reasonInformation);

reasonInformation.setType(ReasonInformation.Type.CD);
reasonInformation.setValue("AC01");
reasonInformation.setAddtlInf(Collections.singletonList("Additional info"));

msgReplyInfo.setOrgnlInstrId("instrId");
msgReplyInfo.setReplyId("pacs008Reply");
msgReplyInfo.setIntrBkSttlmDt(new Date());

ChargesInformation chargesInformation = new ChargesInformation();
chargesInformation.setAmount(new BigDecimal("2.00"));
chargesInformation.setAgentBic("AAAAGB2L");
msgReplyInfo.setChargesInformation(Collections.singletonList(chargesInformation));

msgReplyInfo.setChargeBearer("SLEV");

PaymentReturn11 pacs004 = pacs008Replies.autoReply(new PaymentReturn11(), Collections.singletonList(msgReplyInfo));
```

The following replies for generic iso20022 messages are supported:

| Source Message  | Reply Message   | Source Class                         | Reply Class                        | AutoReplies Class                             |
|-----------------|-----------------|--------------------------------------|------------------------------------|-----------------------------------------------|
| pacs.008.001.xx | pacs.004.001.xx | FIToFICustomerCreditTransferXX       | PaymentReturnXX                    | FIToFICustomerCreditTransferAutoReplies       |
| pacs.008.001.xx | pacs.002.001.xx | FIToFICustomerCreditTransferXX       | FIToFIPaymentStatusReportXX        | FIToFICustomerCreditTransferAutoReplies       |
| pacs.008.001.xx | camt.056.001.xx | FIToFICustomerCreditTransferXX       | FIToFIPaymentCancellationRequestXX | FIToFICustomerCreditTransferAutoReplies       |
| pacs.008.001.xx | camt.027.001.xx | FIToFICustomerCreditTransferXX       | ClaimNonReceiptXX                  | FIToFICustomerCreditTransferAutoReplies       |
| pacs.008.001.xx | camt.087.001.xx | FIToFICustomerCreditTransferXX       | RequestToModifyPaymentXX           | FIToFICustomerCreditTransferAutoReplies       |
| pacs.008.001.xx | camt.106.001.xx | FIToFICustomerCreditTransferXX       | ChargesPaymentRequestXX            | FIToFICustomerCreditTransferAutoReplies       |
| pacs.009.001.xx | pacs.004.001.xx | FinancialInstitutionCreditTransferXX | PaymentReturnXX                    | FinancialInstitutionCreditTransferAutoReplies |
| pacs.009.001.xx | camt.056.001.xx | FinancialInstitutionCreditTransferXX | FIToFIPaymentCancellationRequestXX | FinancialInstitutionCreditTransferAutoReplies |
| camt.056.001.xx | camt.029.001.xx | FIToFIPaymentCancellationRequestXX   | ResolutionOfInvestigationXX        | FIToFIPaymentCancellationRequestAutoReplies   |
| camt.056.001.xx | pacs.028.001.xx | FIToFIPaymentCancellationRequestXX   | FIToFIPaymentStatusRequestXX       | FIToFIPaymentCancellationRequestAutoReplies   |
| camt.027.001.xx | camt.029.001.xx | ClaimNonReceiptXX                    | ResolutionOfInvestigationXX        | ClaimNonReceiptAutoReplies                    |
| camt.087.001.xx | camt.029.001.xx | RequestToModifyPaymentXX             | ResolutionOfInvestigationXX        | RequestToModifyPaymentAutoReplies             |
| pacs.003.001.xx | pacs.004.001.xx | FIToFICustomerDirectDebitXX          | PaymentReturnXX                    | FIToFICustomerDirectDebitAutoReplies          |
| pacs.003.001.xx | pacs.002.001.xx | FIToFICustomerDirectDebitXX          | FIToFIPaymentStatusReportXX        | FIToFICustomerDirectDebitAutoReplies          |
| pacs.003.001.xx | pacs.007.001.xx | FIToFICustomerDirectDebitXX          | FIToFIPaymentReversalXX            | FIToFICustomerDirectDebitAutoReplies          |
| pacs.007.001.xx | pacs.004.001.xx | FIToFIPaymentReversalXX              | PaymentReturnXX                    | FIToFIPaymentReversalAutoReplies              |

_* Where XX represents the version of the message._  
Sample code for `FIToFICustomerCreditTransferAutoReplies` can be found [here](https://gist.github.com/johnmara-pc14/884bf8ac6bcdb715e047e8db83b3cb30).  
Sample code for `FinancialInstitutionCreditTransferAutoReplies` can be found [here](https://gist.github.com/johnmara-pc14/609bc30465ac16783fcfcce890d9f4fc).  
Sample code for `FIToFIPaymentCancellationRequestAutoReplies` can be found [here](https://gist.github.com/johnmara-pc14/5cc032704225df3a9d3faa7ca067e70d).

## Universal Confirmations

You can create Universal Confirmations `trck.001.001.03` for a `pacs.008` messages. It is the equivalent of creating MT199 Universal Confirmation for an MT103.  
First, you need to initiate `FIToFICustomerCreditTransferAutoReplies` class since the method for Universal Confirmation exists there.  
A Universal Confirmations message is represented by `UniversalConfirmationsMessage` and consists of the ApplicationHeader(`head.001.001.02`) and the Document(`trck.001.001.03`).  
Available statuses are: `ACCC`, `ACSP` and `RJCT`.  
Available paymentScenario are: `CCTR` and `RCCT`.  
Below you can see how to use it.
```java
//initiate a UniversalConfirmationsMessage instance
String status = "ACSP";
String paymentScenario = "CCTR";
ReasonInformation rsnInf1 = new ReasonInformation();
rsnInf1.setValue("G001");
MsgReplyInfo msgReplyInfo1 = new MsgReplyInfo();
msgReplyInfo1.setRsnInf(rsnInf1);
msgReplyInfo1.setOrgnlInstrId("BBBB/150928-CCT/JPY/123/0");
msgReplyInfo1.setChargeBearer("CRED"); //optional
msgReplyInfo1.setChargesInformation(new ArrayList<>()); //optional
msgReplyInfo1.getChargesInformation().add(new ChargesInformation()); //optional
msgReplyInfo1.getChargesInformation().get(0).setAmount(new BigDecimal("1")); //optional

//OR
String status = "ACCC";
String paymentScenario = "CCTR";
MsgReplyInfo msgReplyInfo1 = new MsgReplyInfo();
msgReplyInfo1.setOrgnlInstrId("BBBB/150928-CCT/JPY/123/0");

//OR
String status = "RJCT";
String paymentScenario = "CCTR";
ReasonInformation rsnInf1 = new ReasonInformation();
rsnInf1.setValue("AM06");
MsgReplyInfo msgReplyInfo1 = new MsgReplyInfo();
msgReplyInfo1.setRsnInf(rsnInf1);
msgReplyInfo1.setOrgnlInstrId("BBBB/150928-CCT/JPY/123/0");
        
UniversalConfirmationsMessage universalConfirmationsMessage =
   new UniversalConfirmationsMessage(new BusinessApplicationHeader03UniversalConfirmations(), new PaymentStatusTrackerUpdate03UniversalConfirmations());

//initiate the Reply Class instance
UniversalConfirmationsAutoReplies<FIToFICustomerCreditTransfer08> universalConfirmationsAutoReplies =
   new UniversalConfirmationsAutoReplies<>(pacs008);

//call method that generates the universal confirmation
universalConfirmationsAutoReplies.autoReply(universalConfirmationsMessage, Arrays.asList(msgReplyInfo1), status, paymentScenario);
```

## CBPR+ messages

### SDK Setup
#### Maven
```xml
<!-- Import the CBPR+ demo SDK-->
<dependency>
    <groupId>gr.datamation.mx</groupId>
    <artifactId>mx</artifactId>
    <version>24.20.0</version>
    <classifier>demo-cbpr</classifier>
</dependency>
```
#### Gradle
```groovy
implementation 'gr.datamation.mx:mx:24.20.0:demo-cbpr@jar'
```
Please refer to [General SDK Setup](#SDK-setup) for more details.

### Parse & Validate CBPR+ Message
In case you need to handle CBPR+ messages, then you need to handle objects of CbprMessage class.

```java
//Initialize the cbprMessage
CbprMessage<BusinessApplicationHeader02, FIToFICustomerCreditTransfer08> cbprMessage = new CbprMessage<>(new BusinessApplicationHeader02(), new FIToFICustomerCreditTransfer08());
//Fill the cbprMessage with data from xml validate CBPR+ against the xml schema. We can also exit in case of errors in this step.
ValidationErrorList validationErrorList = cbprMessage.autoParseAndValidateXml(new ByteArrayInputStream(validCbprPacs008String.getBytes()));
//Perform validation in both header and message object using cbprMessage
//Use CbprMessage.CbprMsgType enumeration object to select the matching schema (check the table of supported CBPR messages below
//CbprMessage.extractCbprMsgType() can also be used
validationErrorList.addAll(cbprMessage.validate(CbprMessage.CbprMsgType.PACS_008));

if (validationErrorList.isEmpty()) {
    System.out.println(fiToFICustomerCreditTransfer.convertToXML()); //Get the generated xml
} else {
    System.out.println(validationErrorList);
}
 
//Extract the header and the core message from cbprMessage object
BusinessApplicationHeader02 businessApplicationHeader = (BusinessApplicationHeader02)cbprMessage.getAppHdr();
FIToFICustomerCreditTransfer08 fiToFICustomerCreditTransfer = (FIToFICustomerCreditTransfer08) cbprMessage.getDocument();
```

### AutoParse & Validate CBPR+ Message
```java
//Initialize the cbprMessage
CbprMessage<?, ?> cbprMessage = new CbprMessage<>();
//Fill the cbprMessage with data from xml and validate CBPR+ against the xml schema. We can also exit in case of errors in this step.
ValidationErrorList validationErrorList = cbprMessage.autoParseAndValidateXml(new ByteArrayInputStream(validCbprPacs008String.getBytes()));

//Perform validation in both header and message object using cbprMessage
validationErrorList.addAll(cbprMessage.autoValidate());

if (validationErrorList.isEmpty()) {
    System.out.println(fiToFICustomerCreditTransfer.convertToXML()); //Get the generated xml
} else {
    System.out.println(validationErrorList);
}
```

### Construct CBPR+ Message
```java
//Initialize the header object
BusinessApplicationHeader02 businessApplicationHeader = new BusinessApplicationHeader02();
businessApplicationHeader.parseXML(validCbprPacs008HeaderString);

//Initialize the document object
FIToFICustomerCreditTransfer08 fiToFICustomerCreditTransfer = new FIToFICustomerCreditTransfer08();
fiToFICustomerCreditTransfer.parseXML(validCbprPacs008DocumentString);

//We fill the elements of the message object using setters
fiToFICustomerCreditTransfer.getMessage().setGrpHdr(new GroupHeader93());
fiToFICustomerCreditTransfer.getMessage().getGrpHdr().setMsgId("1234");
//or setElement()
fiToFICustomerCreditTransfer.setElement("GrpHdr/MsgId", "1234");

//Construct the CBPR message object      
CbprMessage<BusinessApplicationHeader02, FIToFICustomerCreditTransfer08> cbprMessage = new CbprMessage<>(businessApplicationHeader, fiToFICustomerCreditTransfer);

//Perform validation in both header and message object using cbprMessage
//Use CbprMessage.CbprMsgType enumeration object to select the matching schema (check the table of supported CBPR messages below)
//CbprMessage.extractCbprMsgType() can also be used
ValidationErrorList validationErrorList = cbprMessage.validate(CbprMessage.CbprMsgType.PACS_008); 

if (validationErrorList.isEmpty()) {
    System.out.println(fiToFICustomerCreditTransfer.convertToXML()); //Get the generated xml
} else {
    System.out.println(validationErrorList);
}
```

In case you want to enclose the CBPR+ message under another Root Element, use the code below
```java
cbprMessage.encloseCbprMessage("RequestPayload") //In case you want RequestPayload
```

### Code samples
[Parse and validate CBPR+ message](src/main/java/com/paymentcomponents/swift/mx/cbpr/ParseAndValidateCbprMessage.java)

### Supported CBPR+ Message Types (v2.1)

| ISO20022 Message | CbprMsgType ENUM | Library Object class                      | Available in Demo |
|------------------|------------------|-------------------------------------------|:-----------------:|
| admi.024.001.01  | ADMI_024         | NotificationOfCorrespondence01            |                   |
| camt.029.001.09  | CAMT_029         | ResolutionOfInvestigation09               |                   |
| camt.052.001.08  | CAMT_052         | BankToCustomerAccountReport08             |                   |
| camt.053.001.08  | CAMT_053         | BankToCustomerStatement08                 |                   |
| camt.054.001.08  | CAMT_054         | BankToCustomerDebitCreditNotification08   |                   |
| camt.055.001.08  | CAMT_055         | CustomerPaymentCancellationRequest08      |                   |
| camt.056.001.08  | CAMT_056         | FIToFIPaymentCancellationRequest08        |                   |
| camt.057.001.06  | CAMT_057         | NotificationToReceive06                   |                   |
| camt.058.001.08  | CAMT_058         | NotificationToReceiveCancellationAdvice08 |                   |
| camt.060.001.05  | CAMT_060         | AccountReportingRequest05                 |                   |
| camt.105.001.02  | CAMT_105         | ChargesPaymentNotification02              |                   |
| camt.105.001.02  | CAMT_105_MLP     | ChargesPaymentNotification02              |                   |
| camt.106.001.02  | CAMT_106         | ChargesPaymentRequest02                   |                   |
| camt.106.001.02  | CAMT_106_MLP     | ChargesPaymentRequest02                   |                   |
| camt.107.001.01  | CAMT_107         | ChequePresentmentNotification01           |                   |
| camt.108.001.01  | CAMT_108         | ChequeCancellationOrStopRequest01         |                   |
| camt.109.001.01  | CAMT_109         | ChequeCancellationOrStopReport01          |                   |
| pacs.002.001.10  | PACS_002         | FIToFIPaymentStatusReport10               |                   |
| pacs.003.001.08  | PACS_003         | FIToFICustomerDirectDebit08               |                   |
| pacs.004.001.09  | PACS_004         | PaymentReturn09                           |                   |
| pacs.008.001.08  | PACS_008         | FIToFICustomerCreditTransfer08            |                   |
| pacs.008.001.08  | PACS_008_STP     | FIToFICustomerCreditTransfer08            |                   |
| pacs.009.001.08  | PACS_009_CORE    | FinancialInstitutionCreditTransfer08      |      &check;      |
| pacs.009.001.08  | PACS_009_COV     | FinancialInstitutionCreditTransfer08      |                   |
| pacs.009.001.08  | PACS_009_ADV     | FinancialInstitutionCreditTransfer08      |                   |
| pacs.010.001.03  | PACS_010         | FinancialInstitutionDirectDebit03         |                   |
| pacs.010.001.03  | PACS_010_COL     | FinancialInstitutionDirectDebit03         |                   |
| pain.001.001.09  | PAIN_001         | CustomerCreditTransferInitiation09        |                   |
| pain.002.001.10  | PAIN_002         | CustomerPaymentStatusReport10             |                   |  
| pain.008.001.08  | PAIN_008         | CustomerDirectDebitInitiation08           |                   |  

### Auto replies

| Source Message  | Reply Message   | Source Class                         | Reply Class                        | AutoReplies Class                                 |
| --------------- |-----------------| ------------------------------------ |------------------------------------| ------------------------------------------------- |
| pacs.008.001.08 | pacs.004.001.09 | FIToFICustomerCreditTransfer08       | PaymentReturn09                    | FIToFICustomerCreditTransferCbprAutoReplies       |
| pacs.008.001.08 | camt.056.001.08 | FIToFICustomerCreditTransfer08       | FIToFIPaymentCancellationRequest08 | FIToFICustomerCreditTransferCbprAutoReplies       |
| pacs.008.001.08 | pacs.002.001.10 | FIToFICustomerCreditTransfer08       | FIToFIPaymentStatusReport10        | FIToFICustomerCreditTransferCbprAutoReplies       |
| pacs.009.001.08 | pacs.004.001.09 | FinancialInstitutionCreditTransfer08 | PaymentReturn09                    | FinancialInstitutionCreditTransferCbprAutoReplies |
| pacs.009.001.08 | camt.056.001.08 | FinancialInstitutionCreditTransfer08 | FIToFIPaymentCancellationRequest08 | FinancialInstitutionCreditTransferCbprAutoReplies |
| camt.056.001.08 | camt.029.001.09 | FIToFIPaymentCancellationRequest08   | ResolutionOfInvestigation09        | FIToFIPaymentCancellationRequestCbprAutoReplies   |

Sample code for `FIToFICustomerCreditTransferCbprAutoReplies` can be found [here](https://gist.github.com/johnmara-pc14/939ce4830069d2b6bad1e54aee1357c2).  
Sample code for `FinancialInstitutionCreditTransferCbprAutoReplies` can be found [here](https://gist.github.com/johnmara-pc14/f6568aeca8fce8b6afb1a69523571e39).  
Sample code for `FIToFIPaymentCancellationRequestCbprAutoReplies` can be found [here](https://gist.github.com/johnmara-pc14/3aad11dd46801eba1d873d9dd1279f87).

Please refer to [general auto replies](#auto-replies) for more details.

## SCRIPS (MEPS+) messages

### Parse & Validate SCRIPS Message
In case you need to handle SCRIPS messages, then you need to handle objects of ScripsMessage class.

```java
//Initialize the scripsMessage
ScripsMessage<BusinessApplicationHeader02, FinancialInstitutionCreditTransfer08> scripsMessage = new ScripsMessage<>(new BusinessApplicationHeader02(), new FinancialInstitutionCreditTransfer08());
//Fill the scripsMessage with data from xml and validate SCRIPS against the xml schema
ValidationErrorList validationErrorList = scripsMessage.autoParseAndValidateXml(new ByteArrayInputStream(validScripsPacs009CoreString.getBytes()));
//Perform validation in both header and message object using scripsMessage
//Use ScripsMessage.ScripsMsgType enumeration object to select the matching schema (check the table of supported SCRIPS messages below
//ScripsMessage.extractScripsMsgType() can also be used
validationErrorList.addAll(scripsMessage.validate(ScripsMessage.ScripsMsgType.PACS_009_CORE));

if (validationErrorList.isEmpty()) {
    System.out.println(fiToFICustomerCreditTransfer.convertToXML()); //Get the generated xml
} else {
    System.out.println(validationErrorList);
}
 
//Extract the header and the core message from scripsMessage object
BusinessApplicationHeader02 businessApplicationHeader = (BusinessApplicationHeader02)scripsMessage.getAppHdr();
FIToFICustomerCreditTransfer08 fiToFICustomerCreditTransfer = (FIToFICustomerCreditTransfer08) scripsMessage.getDocument();
```

### AutoParse SCRIPS Message
```java
//Initialize the scripsMessage
ScripsMessage<?, ?> scripsMessage = new ScripsMessage<>();
//Fill the scripsMessage with data from xml and validate SCRIPS against the xml schema
ValidationErrorList validationErrorList = scripsMessage.autoParseAndValidateXml(new ByteArrayInputStream(validScripsPacs009CoreString.getBytes()));

//Perform validation in both header and message object using scripsMessage
validationErrorList.addAll(scripsMessage.autoValidate());

if (validationErrorList.isEmpty()) {
    System.out.println("Message is valid");
    System.out.println(scripsMessage.convertToXML()); //Get the generated xmls for head and document
} else {
    handleValidationError(validationErrorList);
}
```

### Construct SCRIPS Message
```java
//Initialize the document object
FinancialInstitutionCreditTransfer08 financialInstitutionCreditTransfer08 = new FinancialInstitutionCreditTransfer08();
financialInstitutionCreditTransfer08.parseXML(validScripsPacs009CoreDocumentString);

//We fill the elements of the message object using setters
//financialInstitutionCreditTransfer08.getMessage().setGrpHdr(new GroupHeader93())
//financialInstitutionCreditTransfer08.getMessage().getGrpHdr().setMsgId("1234")

//or setElement()
//financialInstitutionCreditTransfer08.setElement("GrpHdr/MsgId", "1234")

//Construct the SCRIPS message object using two separate objects, header, document
ScripsMessage<BusinessApplicationHeader02, FinancialInstitutionCreditTransfer08> scripsMessage = new ScripsMessage<>(businessApplicationHeader, financialInstitutionCreditTransfer08);

//Perform validation in both header and message object using scripsMessage
//Use ScripsMessage.ScripsMsgType enumeration object to select the matching schema (check the table of supported SCRIPS messages below
//ScripsMessage.extractScripsMsgType() can also be used
ValidationErrorList validationErrorList = scripsMessage.validate(ScripsMessage.ScripsMsgType.PACS_009_CORE);

if (validationErrorList.isEmpty()) {
    System.out.println("Message is valid");
    System.out.println(scripsMessage.convertToXML()); //Get the generated xmls for head and document
} else {
    handleValidationError(validationErrorList);
}
```

### Code samples
[Parse and validate SCRIPS message](https://gist.github.com/johnmara-pc14/0cacc9f83d1969f34d7adeb340abf186)

### Supported SCRIPS Message Types (v2.4)

| ISO20022 Message | ScripsMsgType ENUM | Library Object class                 |
|------------------|--------------------|--------------------------------------|
| camt.029.001.09  | CAMT_029           | ResolutionOfInvestigation09          |
| camt.056.001.08  | CAMT_056           | FIToFIPaymentCancellationRequest08   |
| pacs.008.001.08  | PACS_008           | FIToFICustomerCreditTransfer08       |
| pacs.009.001.08  | PACS_009_CORE      | FinancialInstitutionCreditTransfer08 |
| pacs.009.001.08  | PACS_009_COV       | FinancialInstitutionCreditTransfer08 |
| camt.053.001.08  | CAMT_053           | BankToCustomerStatement08            |
| camt.053.001.08  | CAMT_053_AOS       | BankToCustomerStatement08            |


## MEPS+ Like4Like messages

### Parse & Validate MEPS+ Like4Like Message
In case you need to handle MEPS+ Like4Like messages, then you need to handle objects of MepsMessage class.

```java
//Initialize the mepsMessage
MepsMessage<BusinessApplicationHeader02, FinancialInstitutionCreditTransfer08> mepsMessage = new MepsMessage<>(new BusinessApplicationHeader02(), new FinancialInstitutionCreditTransfer08());
//Fill the mepsMessage with data from xml and validate MEPS against the xml schema
ValidationErrorList validationErrorList = mepsMessage.autoParseAndValidateXml(new ByteArrayInputStream(validMepsPacs009CoreString.getBytes()));
//Perform validation in both header and message object using mepsMessage
//Use MepsMessage.MepsMsgType enumeration object to select the matching schema (check the table of supported MEPS messages below
//MepsMessage.extractMepsMsgType() can also be used
validationErrorList.addAll(mepsMessage.validate(MepsMessage.MepsMsgType.PACS_009_CORE));

if (validationErrorList.isEmpty()) {
    System.out.println(fiToFICustomerCreditTransfer.convertToXML()); //Get the generated xml
} else {
    System.out.println(validationErrorList);
}
 
//Extract the header and the core message from mepsMessage object
BusinessApplicationHeader02 businessApplicationHeader = (BusinessApplicationHeader02)mepsMessage.getAppHdr();
FIToFICustomerCreditTransfer08 fiToFICustomerCreditTransfer = (FIToFICustomerCreditTransfer08) mepsMessage.getDocument();
```

### AutoParse MEPS+ Like4Like Message
```java
//Initialize the mepsMessage
MepsMessage<?, ?> mepsMessage = new MepsMessage<>();
//Fill the mepsMessage with data from xml and validate MEPS+ Lik4Like against the xml schema
ValidationErrorList validationErrorList = mepsMessage.autoParseAndValidateXml(new ByteArrayInputStream(validMepsPacs009CoreString.getBytes()));

//Perform validation in both header and message object using mepsMessage
validationErrorList.addAll(mepsMessage.autoValidate());

if (validationErrorList.isEmpty()) {
    System.out.println("Message is valid");
    System.out.println(mepsMessage.convertToXML()); //Get the generated xmls for head and document
} else {
    handleValidationError(validationErrorList);
}
```

### Construct MEPS+ Like4Like Message
```java
//Initialize the document object
FinancialInstitutionCreditTransfer08 financialInstitutionCreditTransfer08 = new FinancialInstitutionCreditTransfer08();
financialInstitutionCreditTransfer08.parseXML(validMepsPacs009CoreDocumentString);

//We fill the elements of the message object using setters
//financialInstitutionCreditTransfer08.getMessage().setGrpHdr(new GroupHeader93())
//financialInstitutionCreditTransfer08.getMessage().getGrpHdr().setMsgId("1234")

//or setElement()
//financialInstitutionCreditTransfer08.setElement("GrpHdr/MsgId", "1234")

//Construct the MEPS message object using two separate objects, header, document
MepsMessage<BusinessApplicationHeader02, FinancialInstitutionCreditTransfer08> mepsMessage = new MepsMessage<>(businessApplicationHeader, financialInstitutionCreditTransfer08);

//Perform validation in both header and message object using mepsMessage
//Use MepsMessage.MepsMsgType enumeration object to select the matching schema (check the table of supported MEPS messages below
//MepsMessage.extractMepsMsgType() can also be used
ValidationErrorList validationErrorList = mepsMessage.validate(MepsMessage.MepsMsgType.PACS_009_CORE);

if (validationErrorList.isEmpty()) {
    System.out.println("Message is valid");
    System.out.println(mepsMessage.convertToXML()); //Get the generated xmls for head and document
} else {
    handleValidationError(validationErrorList);
}
```

### Code samples
[Parse and validate MEPS+ Like4Like message](https://gist.github.com/johnmara-pc14/1ac712b7ff6371b9256734160ef90d86)

### Supported MEPS+ Like4Like Message Types (v1.2)

| ISO20022 Message | MepsMsgType ENUM | Library Object class                 |
|------------------|------------------|--------------------------------------|
| camt.029.001.09  | CAMT_029         | ResolutionOfInvestigation09          |
| camt.056.001.08  | CAMT_056         | FIToFIPaymentCancellationRequest08   |
| pacs.008.001.08  | PACS_008         | FIToFICustomerCreditTransfer08       |
| pacs.008.001.08  | PACS_008_STP     | FIToFICustomerCreditTransfer08       |
| pacs.009.001.08  | PACS_009_CORE    | FinancialInstitutionCreditTransfer08 |
| pacs.009.001.08  | PACS_009_COV     | FinancialInstitutionCreditTransfer08 |
| camt.053.001.08  | CAMT_053         | BankToCustomerStatement08            |
| camt.053.001.08  | CAMT_053_AOS     | BankToCustomerStatement08            |


## TARGET2 (RTGS) messages

### SDK Setup
#### Maven
```xml
<!-- Import the TARGET2 (RTGS) demo SDK-->
<dependency>
    <groupId>gr.datamation.mx</groupId>
    <artifactId>mx</artifactId>
    <version>24.20.0</version>
    <classifier>demo-rtgs</classifier>
</dependency>
```
#### Gradle
```groovy
implementation 'gr.datamation.mx:mx:24.20.0:demo-rtgs@jar'
```
Please refer to [General SDK Setup](#SDK-setup) for more details.

### Parse & Validate TARGET2 Message
In case you need to handle TARGET2 (RTGS) messages, then you need to handle objects that extend the ISO20022 classes.
```java
//Initialize the message object
FIToFICustomerCreditTransfer08Rtgs fiToFICustomerCreditTransfer = new FIToFICustomerCreditTransfer08Rtgs();
//Validate against the xml schema. We can also exit in case of errors in this step.
ValidationErrorList validationErrorList = fiToFICustomerCreditTransfer.validateXML(new ByteArrayInputStream(validRtgsPacs008String.getBytes()));
//Fill the message with data from xml
fiToFICustomerCreditTransfer.parseXML(validRtgsPacs008String);
//Validate both the xml schema and rules
validationErrorList.addAll(fiToFICustomerCreditTransfer.validate());  

if (validationErrorList.isEmpty()) {
    System.out.println(fiToFICustomerCreditTransfer.convertToXML()); //Get the generated xml
} else {
    System.out.println(validationErrorList);
}
```

### AutoParse TARGET2 Message
```java
//Validate against the xml schema without knowing the message type. We can also exit in case of errors in this step.
ValidationErrorList validationErrorList = RtgsUtils.autoValidateXML(new ByteArrayInputStream(validRtgsPacs008String.getBytes()));
//Fill the message with data from xml without knowing the message type
Message message = RtgsUtils.autoParseXML(validRtgsPacs008String);
//Validate both the xml schema and rules
validationErrorList.addAll(message.validate());

if (validationErrorList.isEmpty()) {
    System.out.println(fiToFICustomerCreditTransfer.convertToXML()); //Get the generated xml
} else {
    System.out.println(validationErrorList);
}
```

### Construct TARGET2 Message
```java
//Initialize the message object
FIToFICustomerCreditTransfer08 fiToFICustomerCreditTransfer = new FIToFICustomerCreditTransfer08();

//We fill the elements of the message object using setters
fiToFICustomerCreditTransfer.getMessage().setGrpHdr(new GroupHeader93());
fiToFICustomerCreditTransfer.getMessage().getGrpHdr().setMsgId("1234");
//or setElement()
fiToFICustomerCreditTransfer.setElement("GrpHdr/MsgId", "1234");

//Perform validation
ValidationErrorList validationErrorList = fiToFICustomerCreditTransfer.validate(); 

if (validationErrorList.isEmpty()) {
    System.out.println(fiToFICustomerCreditTransfer.convertToXML()); //Get the generated xml
} else {
    System.out.println(validationErrorList);
}
```

### Code samples
[Parse and validate TARGET2 message](src/main/java/com/paymentcomponents/swift/mx/target2/ParseAndValidateRtgsMessage.java)

### Supported TARGET2 Message Types (v2.2)

| ISO20022 Message|Library Object class                         | Available in Demo |
| --------------- |-------------------                          | :---------------: |
| admi.007.001.01 | ReceiptAcknowledgement01Rtgs                |                   |
| camt.025.001.05 | Receipt05Rtgs                               |                   |
| camt.029.001.09 | ResolutionOfInvestigation09Rtgs             |                   |
| camt.050.001.05 | LiquidityCreditTransfer05Rtgs               |                   |
| camt.053.001.08 | BankToCustomerStatement08Rtgs               |                   |
| camt.054.001.08 | BankToCustomerDebitCreditNotification08Rtgs |                   |
| camt.056.001.08 | FIToFIPaymentCancellationRequest08Rtgs      |                   |
| pacs.002.001.10 | FIToFIPaymentStatusReport10Rtgs             |                   |
| pacs.004.001.09 | PaymentReturn09Rtgs                         |                   |
| pacs.008.001.08 | FIToFICustomerCreditTransfer08Rtgs          |                   |
| pacs.009.001.08 | FinancialInstitutionCreditTransfer08Rtgs    | &check;           |
| pacs.010.001.03 | FinancialInstitutionDirectDebit03Rtgs       |                   |

### Auto replies

| Source Message  | Reply Message   | Source Class                             | Reply Class                            | AutoReplies Class                                 |
| --------------- | --------------- | ---------------------------------------- | -------------------------------------- | ------------------------------------------------- |
| pacs.008.001.08 | pacs.004.001.09 | FIToFICustomerCreditTransfer08Rtgs       | PaymentReturn09Rtgs                    | FIToFICustomerCreditTransferRtgsAutoReplies       |
| pacs.008.001.08 | camt.056.001.08 | FIToFICustomerCreditTransfer08Rtgs       | FIToFIPaymentCancellationRequest08Rtgs | FIToFICustomerCreditTransferRtgsAutoReplies       |
| pacs.009.001.08 | pacs.004.001.09 | FinancialInstitutionCreditTransfer08Rtgs | PaymentReturn09Rtgs                    | FinancialInstitutionCreditTransferRtgsAutoReplies |
| pacs.009.001.08 | camt.056.001.08 | FinancialInstitutionCreditTransfer08Rtgs | FIToFIPaymentCancellationRequest08Rtgs | FinancialInstitutionCreditTransferRtgsAutoReplies |
| camt.056.001.08 | camt.029.001.09 | FIToFIPaymentCancellationRequest08Rtgs   | ResolutionOfInvestigation09Rtgs        | FIToFIPaymentCancellationRequestRtgsAutoReplies   |

Sample code for `FIToFICustomerCreditTransferRtgsAutoReplies` can be found [here](https://gist.github.com/johnmara-pc14/664a90fcff8d6a998d348b39b4a896b3).  
Sample code for `FinancialInstitutionCreditTransferRtgsAutoReplies` can be found [here](https://gist.github.com/johnmara-pc14/ae0bcf26b114a692a963ce6568706952).  
Sample code for `FIToFIPaymentCancellationRequestRtgsAutoReplies` can be found [here](https://gist.github.com/johnmara-pc14/128efb049020662d394c5e09e341635e).

Please refer to [general auto replies](#auto-replies) for more details.

## FedNow messages

### Parse & Validate FedNow Message
In case you need to handle FedNow messages, then you need to handle objects that extend the ISO20022 classes.
```java
//Initialize the message object
FIToFICustomerCreditTransfer08Fednow fiToFICustomerCreditTransfer = new FIToFICustomerCreditTransfer08Fednow();
//Validate against the xml schema. We can also exit in case of errors in this step.
ValidationErrorList validationErrorList = fiToFICustomerCreditTransfer.validateXML(new ByteArrayInputStream(validFednowPacs008String.getBytes()));
//Fill the message with data from xml
fiToFICustomerCreditTransfer.parseXML(validFednowPacs008String);
//Validate both the xml schema and rules
validationErrorList.addAll(fiToFICustomerCreditTransfer.validate());  

if (validationErrorList.isEmpty()) {
    System.out.println(fiToFICustomerCreditTransfer.convertToXML()); //Get the generated xml
} else {
    System.out.println(validationErrorList);
}
````

### Auto Parse and Validate FedNow Message
```java
//Validate against the xml schema without knowing the message type. We can also exit in case of errors in this step.
//We can use FednowUtils.FedNowMessageType enumeration in order to specify the fednow type we are interested in messages with multiple types (pacs.002, camt.052, camt.029)
ValidationErrorList validationErrorList = FednowUtils.autoValidateXML(new ByteArrayInputStream(validFednowPacs008String.getBytes()));
//Fill the message with data from xml without knowing the message type
Message message = FednowUtils.autoParseXML(validFednowPacs008String);
//Validate both the xml schema and rules
validationErrorList.addAll(message.validate());

if (validationErrorList.isEmpty()) {
    System.out.println(fiToFICustomerCreditTransfer.convertToXML()); //Get the generated xml
} else {
    System.out.println(validationErrorList);
}
```

### Construct FedNow Message
```java
//Initialize the message object
FIToFICustomerCreditTransfer08Fednow fiToFICustomerCreditTransfer = new FIToFICustomerCreditTransfer08Fednow();

//We fill the elements of the message object using setters
fiToFICustomerCreditTransfer.getMessage().setGrpHdr(new GroupHeader93());
fiToFICustomerCreditTransfer.getMessage().getGrpHdr().setMsgId("1234");
//or setElement()
fiToFICustomerCreditTransfer.setElement("GrpHdr/MsgId", "1234");

//Perform validation
ValidationErrorList validationErrorList = fiToFICustomerCreditTransfer.validate(); 

if (validationErrorList.isEmpty()) {
    System.out.println(fiToFICustomerCreditTransfer.convertToXML()); //Get the generated xml
} else {
    System.out.println(validationErrorList);
}
```

### Code samples
[Parse and validate FedNow message](https://gist.github.com/johnmara-pc14/de651dc9a44a558e7e59126bb5484fe4)

### Supported FedNow Message Types (29 June 2022)

| ISO20022 Message | Library Object class                                            |            Category            |
|------------------|-----------------------------------------------------------------|:------------------------------:|
| pacs.008.001.08  | FIToFICustomerCreditTransfer08Fednow                            |   Customer Credit Transfers    |
| pacs.002.001.10  | FIToFIPaymentStatusReport10FednowPaymentStatusFednow            |   Customer Credit Transfers    |
| pacs.002.001.10  | FIToFIPaymentStatusReport10ParticipantPaymentStatusFednow       |   Customer Credit Transfers    |
| pacs.028.001.03  | FIToFIPaymentStatusRequest03Fednow                              |   Customer Credit Transfers    |
| camt.029.001.09  | ResolutionOfInvestigation09ReturnRequestResponseFednow          |        Payment Returns         |
| camt.056.001.08  | FIToFIPaymentCancellationRequest08Fednow                        |        Payment Returns         |
| pacs.004.001.10  | PaymentReturn10Fednow                                           |        Payment Returns         |
| pacs.009.001.08  | FinancialInstitutionCreditTransfer08Fednow                      | Liquidity Management Transfers |
| admi.002.001.01  | MessageReject01Fednow                                           |        System Messages         |
| admi.007.001.01  | ReceiptAcknowledgement01Fednow                                  |        System Messages         |
| camt.060.001.05  | AccountReportingRequest05Fednow                                 |       Account Reporting        |
| camt.052.001.08  | BankToCustomerAccountReport08AccountActivityDetailsReportFednow |       Account Reporting        |
| camt.052.001.08  | BankToCustomerAccountReport08AccountActivityTotalsReportFednow  |       Account Reporting        |
| camt.052.001.08  | BankToCustomerAccountReport08AccountBalanceReportFednow         |       Account Reporting        |
| camt.054.001.08  | BankToCustomerDebitCreditNotification08Fednow                   |       Account Reporting        |
| head.001.001.02  | BusinessApplicationHeader02Fednow                               |                                |

### Auto replies

| Source Message  | Reply Message   | Source Class                             | Reply Class                                            | AutoReplies Class                                 |
| --------------- | --------------- | ---------------------------------------- |--------------------------------------------------------| ------------------------------------------------- |
| pacs.008.001.08 | pacs.004.001.10 | FIToFICustomerCreditTransfer08Fednow     | PaymentReturn10Fednow                                  | FIToFICustomerCreditTransferFednowAutoReplies     |
| pacs.008.001.08 | camt.056.001.08 | FIToFICustomerCreditTransfer08Fednow     | FIToFIPaymentCancellationRequest08Fednow               | FIToFICustomerCreditTransferFednowAutoReplies   |
| camt.056.001.08 | camt.029.001.09 | FIToFIPaymentCancellationRequest08Fednow | ResolutionOfInvestigation09ReturnRequestResponseFednow | FIToFIPaymentCancellationRequestFednowAutoReplies |

Sample code for `FIToFICustomerCreditTransferFednowAutoReplies` can be found [here](https://gist.github.com/johnmara-pc14/a03821408d286cbf55e1e5ad66b83f51).  
Sample code for `FIToFIPaymentCancellationRequestFednowAutoReplies` can be found [here](https://gist.github.com/johnmara-pc14/8cebbc6a7a63426b2f225e07edc399b5).

## SIC/euroSIC messages

### Parse & Validate SIC/euroSIC Message

```java
 //Initialize the message object
 FIToFICustomerCreditTransfer08SicEurosic fiToFICustomerCreditTransfer = new FIToFICustomerCreditTransfer08SicEurosic();
 //Validate against the xml schema. We can also exit in case of errors in this step.
 ValidationErrorList validationErrorList = fiToFICustomerCreditTransfer.validateXML(new ByteArrayInputStream(validSicEuroSicPacs008String.getBytes()));
 //Fill the message with data from xml
 fiToFICustomerCreditTransfer.parseXML(validSicEuroSicPacs008String);
 
 //Validate both the xml schema and rules
 validationErrorList.addAll(fiToFICustomerCreditTransfer.validate());
 
 if (validationErrorList.isEmpty()) {
 System.out.println(fiToFICustomerCreditTransfer.convertToXML()); //Get the generated xml
 } else {
 System.out.println(validationErrorList);
 }
```

### Auto Parse and Validate SIC/euroSIC Message
```java
 //Initialize the message object
 FIToFICustomerCreditTransfer08SicEurosic fiToFICustomerCreditTransfer = new FIToFICustomerCreditTransfer08SicEurosic();

 //Validate against the xml schema without knowing the message type. We can also exit in case of errors in this step.
 ValidationErrorList validationErrorList = SicEurosicUtils.autoValidateXML(new ByteArrayInputStream(validSicEuroSicPacs008String.getBytes()));
 //Fill the message with data from xml without knowing the message type
 Message message = SicEurosicUtils.autoParseXML(validSicEuroSicPacs008String);
 
 //Validate both the xml schema and rules
 validationErrorList.addAll(message.validate());
 
 if (validationErrorList.isEmpty()) {
 System.out.println(fiToFICustomerCreditTransfer.convertToXML()); //Get the generated xml
 } else {
 System.out.println(validationErrorList);
 }
```
### Construct SIC/euroSIC Message
```java
 //Initialize the message object
 FIToFICustomerCreditTransfer08SicEurosic fiToFICustomerCreditTransfer = new FIToFICustomerCreditTransfer08SicEurosic();

 //We fill the elements of the message object using setters
 fiToFICustomerCreditTransfer.getMessage().setGrpHdr(new GroupHeader93());
 fiToFICustomerCreditTransfer.getMessage().getGrpHdr().setMsgId("1234");
 fiToFICustomerCreditTransfer.getMessage().getGrpHdr().setNbOfTxs("0");
 //or setElement()
 fiToFICustomerCreditTransfer.setElement("GrpHdr/MsgId", "1234");
 fiToFICustomerCreditTransfer.setElement("GrpHdr/NbOfTxs", "0");

 //Perform validation
 ValidationErrorList validationErrorList = fiToFICustomerCreditTransfer.validate();

 if (validationErrorList.isEmpty()) {
 System.out.println(fiToFICustomerCreditTransfer.convertToXML()); //Get the generated xml
 } else {
 System.out.println(validationErrorList);
```
### Code samples
[Parse and validate SIC/euroSIC message](https://gist.github.com/gantoniadispc14/3548898d326ff08247f3892c8a5ed784)

### Supported SIC/euroSIC Message Types (version 4.9)

| ISO20022 Message | Library Object class                         |
|------------------|----------------------------------------------|
| pacs.008.001.08  | FIToFICustomerCreditTransfer08SicEurosic     |
| pacs.002.001.10  | FIToFIPaymentStatusReport10SicEurosic        |
| camt.029.001.09  | ResolutionOfInvestigation09SicEurosic        |
| camt.056.001.08  | FIToFIPaymentCancellationRequest08SicEurosic |
| pacs.004.001.09  | FIToFIPaymentStatusReport09SicEurosic        |

## BAHTNET messages

### Parse & Validate Bahtnet Message
In case you need to handle Bahtnet messages, then you need to handle objects of `BahtnetMessage` class.

```java
//Initialize the bahtnetMessage
BahtnetMessage<BusinessApplicationHeader02, FIToFICustomerCreditTransfer08> bahtnetMessage = new BahtnetMessage<>(new BusinessApplicationHeader02(), new FIToFICustomerCreditTransfer08());
//Fill the bahtnetMessage with data from xml validate Bahtnet against the xml schema. We can also exit in case of errors in this step.
ValidationErrorList validationErrorList = bahtnetMessage.autoParseAndValidateXml(new ByteArrayInputStream(validBahtnetPacs008String.getBytes()));
//Perform validation in both header and message object using bahtnetMessage
//Use BahtnetMessage.BahtnetMsgType enumeration object to select the matching schema (check the table of supported Bahtnet messages below
//BahtnetMessage.extractBahtnetMsgType() can also be used
validationErrorList.addAll(bahtnetMessage.validate(BahtnetMessage.BahtnetMsgType.PACS_008));

if (validationErrorList.isEmpty()) {
    System.out.println(fiToFICustomerCreditTransfer.convertToXML()); //Get the generated xml
} else {
    System.out.println(validationErrorList);
}
 
//Extract the header and the core message from bahtnetMessage object
BusinessApplicationHeader02 businessApplicationHeader = (BusinessApplicationHeader02)bahtnetMessage.getAppHdr();
FIToFICustomerCreditTransfer08 fiToFICustomerCreditTransfer = (FIToFICustomerCreditTransfer08) bahtnetMessage.getDocument();
```

### AutoParse & Validate Bahtnet Message
```java
//Initialize the bahtnetMessage
BahtnetMessage<?, ?> bahtnetMessage = new BahtnetMessage<>();
//Fill the bahtnetMessage with data from xml and validate Bahtnet against the xml schema. We can also exit in case of errors in this step.
ValidationErrorList validationErrorList = bahtnetMessage.autoParseAndValidateXml(new ByteArrayInputStream(validBahtnetPacs008String.getBytes()));

//Perform validation in both header and message object using bahtnetMessage
validationErrorList.addAll(bahtnetMessage.autoValidate());

if (validationErrorList.isEmpty()) {
    System.out.println(fiToFICustomerCreditTransfer.convertToXML()); //Get the generated xml
} else {
    System.out.println(validationErrorList);
}
```

### Construct Bahtnet Message
```java
//Initialize the header object
BusinessApplicationHeader02 businessApplicationHeader = new BusinessApplicationHeader02();
businessApplicationHeader.parseXML(validBahtnetPacs008HeaderString);

//Initialize the document object
FIToFICustomerCreditTransfer08 fiToFICustomerCreditTransfer = new FIToFICustomerCreditTransfer08();
fiToFICustomerCreditTransfer.parseXML(validBahtnetPacs008DocumentString);

//We fill the elements of the message object using setters
fiToFICustomerCreditTransfer.getMessage().setGrpHdr(new GroupHeader93());
fiToFICustomerCreditTransfer.getMessage().getGrpHdr().setMsgId("1234");
//or setElement()
fiToFICustomerCreditTransfer.setElement("GrpHdr/MsgId", "1234");

//Construct the Bahtnet message object      
BahtnetMessage<BusinessApplicationHeader02, FIToFICustomerCreditTransfer08> bahtnetMessage = new BahtnetMessage<>(businessApplicationHeader, fiToFICustomerCreditTransfer);

//Perform validation in both header and message object using bahtnetMessage
//Use BahtnetMessage.BahtnetMsgType enumeration object to select the matching schema (check the table of supported Bahtnet messages below)
//BahtnetMessage.extractBahtnetMsgType() can also be used
ValidationErrorList validationErrorList = bahtnetMessage.validate(BahtnetMessage.BahtnetMsgType.PACS_008); 

if (validationErrorList.isEmpty()) {
    System.out.println(fiToFICustomerCreditTransfer.convertToXML()); //Get the generated xml
} else {
    System.out.println(validationErrorList);
}
```

In case you want to enclose the Bahtnet message under another Root Element, use the code below
```java
bahtnetMessage.encloseBahtnetMessage("RequestPayload") //In case you want RequestPayload
```

### Code samples
[Parse and validate BAHTNET message](https://gist.github.com/PaymentComponents/ded189c834a093c722b2965724033a3e)

### Supported BAHTNET Message Types (03 March 2022)

| ISO20022 Message | BahtnetMsgType ENUM     | Library Object class                            |
|------------------|-------------------------|-------------------------------------------------|
| camt.053.001.08  | CAMT_053                | FIToFICustomerCreditTransfer08Bahtnet           |
| camt.054.001.08  | CAMT_054_CDT            | FIToFIPaymentStatusReport10Bahtnet              |   
| camt.054.001.08  | CAMT_054_DBT            | FinancialInstitutionCreditTransfer08Bahtnet     |
| camt.056.001.08  | CAMT_056_CAN            | FinancialInstitutionCreditTransfer08CovBahtnet  |
| camt.056.001.08  | CAMT_056_RTN            | BankToCustomerCreditNotification08Bahtnet       |
| camt.087.001.06  | CAMT_087                | BankToCustomerDebitNotification08Bahtnet        |
| camt.998.001.02  | CAMT_998                | BankToCustomerStatement08Bahtnet                |
| mft.01           | MFT_01                  | FIToFIPaymentCancellationRequest08Bahtnet       |
| pacs.002.001.10  | PACS_002                | FIToFIPaymentCancellationRequestReturn08Bahtnet |
| pacs.008.001.08  | PACS_008                | ProprietaryMessage02Bahtnet                     |
| pacs.009.001.08  | PACS_009_CORE           | RequestToModifyPayment06Bahtnet                 |
| pacs.009.001.08  | PACS_009_COV            | MftDetailBahtnet01                              |

## CGI-MP messages

### SDK Setup
#### Maven
```xml
<!-- Import the TARGET2 (RTGS) demo SDK-->
<dependency>
    <groupId>gr.datamation.mx</groupId>
    <artifactId>mx</artifactId>
    <version>24.20.0</version>
    <classifier>{CLIENT_CLASSIFIER}</classifier>
</dependency>
```
#### Gradle
```groovy
implementation 'gr.datamation.mx:mx:24.20.0:{CLIENT_CLASSIFIER}@jar'
```
Please refer to [General SDK Setup](#SDK-setup) for more details.

### Parse & Validate CGI-MP Message
In case you need to handle CGI-MP messages, then you need to handle objects that extend the ISO20022 classes.
```java
//Initialize the message object
CustomerCreditTransferInitiation09RelayServiceCgiMp customerCreditTransfer = new CustomerCreditTransferInitiation09RelayServiceCgiMp();
//Validate against the xml schema. We can also exit in case of errors in this step.
ValidationErrorList validationErrorList = customerCreditTransfer.validateXML(new ByteArrayInputStream(validPain001String.getBytes()));
//Fill the message with data from xml
customerCreditTransfer.parseXML(validPain001String);
//Validate both the xml schema and rules
validationErrorList.addAll(customerCreditTransfer.validate());  

if (validationErrorList.isEmpty()) {
    System.out.println(customerCreditTransfer.convertToXML()); //Get the generated xml
} else {
    System.out.println(validationErrorList);
}
```

### Construct CGI-MP Message
```java
//Initialize the message object
CustomerCreditTransferInitiation09RelayServiceCgiMp customerCreditTransfer = new CustomerCreditTransferInitiation09RelayServiceCgiMp();

//We fill the elements of the message object using setters
customerCreditTransfer.getMessage().setGrpHdr(new GroupHeader85());
customerCreditTransfer.getMessage().getGrpHdr().setMsgId("1234");
//or setElement()
customerCreditTransfer.setElement("GrpHdr/MsgId", "1234");

//Perform validation
ValidationErrorList validationErrorList = customerCreditTransfer.validate(); 

if (validationErrorList.isEmpty()) {
    System.out.println(customerCreditTransfer.convertToXML()); //Get the generated xml
} else {
    System.out.println(validationErrorList);
}

### Code samples
[Parse and validate CGI-MP message](https://gist.github.com/PaymentComponents/bcb63721ba2a598a2312c88d2939dc8c)

### Supported CGI-MP Message Types

| ISO20022 Message                | Library Object class                                  |
|---------------------------------|-------------------------------------------------------|
| pain.001.001.09.relay.service   | CustomerCreditTransferInitiation09RelayServiceCgiMp   |
| pain.001.001.09.urgent.payments | CustomerCreditTransferInitiation09UrgentPaymentsCgiMp |

## SEPA-EPC Credit Transfer

### SDK Setup
#### Maven
```xml
<!-- Import the SEPA-EPC-CT demo SDK-->
<dependency>
    <groupId>gr.datamation.mx</groupId>
    <artifactId>mx</artifactId>
    <version>24.20.0</version>
    <classifier>demo-sepa</classifier>
</dependency>
```
#### Gradle
```groovy
implementation 'gr.datamation.mx:mx:24.20.0:demo-sepa@jar'
```
Please refer to [General SDK Setup](#SDK-setup) for more details.

## Swiftcase messages

### SDK Setup
#### Maven
```xml
<!-- Import the Swiftcase SDK-->
<dependency>
    <groupId>gr.datamation.mx</groupId>
    <artifactId>mx</artifactId>
    <version>24.20.0</version>
    <classifier>swiftcase</classifier>
</dependency>
```
#### Gradle
```groovy
implementation 'gr.datamation.mx:mx:24.20.0:swiftcase@jar'
```
Please refer to [General SDK Setup](#SDK-setup) for more details.

### Parse & Validate Swiftcase Message
In case you need to handle Swiftcase messages, then you need to handle objects of SwiftcaseMessage class.

```java
//Initialize the swiftcaseMessage
SwiftcaseMessage<BusinessApplicationHeader02, InvestigationRequest01> swiftcaseMessage = new SwiftcaseMessage<>(new BusinessApplicationHeader02(), new InvestigationRequest01());
//Fill the swiftcaseMessage with data from xml validate Swiftcase against the xml schema. We can also exit in case of errors in this step.
ValidationErrorList validationErrorList = swiftcaseMessage.autoParseAndValidateXml(new ByteArrayInputStream(validSwiftcaseCamt110String.getBytes()));
//Perform validation in both header and message object using swiftcaseMessage
//Use SwiftcaseMessage.SwiftcaseMsgType enumeration object to select the matching schema (check the table of supported Swiftcase messages below
//SwiftcaseMessage.extractSwiftcaseMsgType() can also be used
validationErrorList.addAll(swiftcaseMessage.validate(SwiftcaseMessage.SwiftcaseMsgType.CAMT_110_OTHR));

if (validationErrorList.isEmpty()) {
    System.out.println(investigationRequest01.convertToXML()); //Get the generated xml
} else {
    System.out.println(validationErrorList);
}
 
//Extract the header and the core message from swiftcaseMessage object
BusinessApplicationHeader02 businessApplicationHeader = (BusinessApplicationHeader02)swiftcaseMessage.getAppHdr();
InvestigationRequest01 investigationRequest01 = (InvestigationRequest01) swiftcaseMessage.getDocument();
```

### AutoParse & Validate Swiftcase Message
```java
//Initialize the swiftcaseMessage
SwiftcaseMessage<?, ?> swiftcaseMessage = new SwiftcaseMessage<>();
//Fill the swiftcaseMessage with data from xml and validate Swiftcase against the xml schema. We can also exit in case of errors in this step.
ValidationErrorList validationErrorList = swiftcaseMessage.autoParseAndValidateXml(new ByteArrayInputStream(validSwiftcaseCamt110String.getBytes()));

//Perform validation in both header and message object using swiftcaseMessage
validationErrorList.addAll(swiftcaseMessage.autoValidate());

if (validationErrorList.isEmpty()) {
    System.out.println(investigationRequest01.convertToXML()); //Get the generated xml
} else {
    System.out.println(validationErrorList);
}
```

### Construct Swiftcase Message
```java
//Initialize the header object
BusinessApplicationHeader02 businessApplicationHeader = new BusinessApplicationHeader02();
businessApplicationHeader.parseXML(validSwiftcaseCamt110HeaderString);

//Initialize the document object
InvestigationRequest01 investigationRequest01 = new InvestigationRequest01();
investigationRequest01.parseXML(validSwiftcaseCamt110DocumentString);

//We fill the elements of the message object using setters
investigationRequest01.getMessage().setGrpHdr(new GroupHeader93());
investigationRequest01.getMessage().getGrpHdr().setMsgId("1234");
//or setElement()
investigationRequest01.setElement("GrpHdr/MsgId", "1234");

//Construct the Swiftcase message object      
SwiftcaseMessage<BusinessApplicationHeader02, InvestigationRequest01> swiftcaseMessage = new SwiftcaseMessage<>(businessApplicationHeader, investigationRequest01);

//Perform validation in both header and message object using swiftcaseMessage
//Use SwiftcaseMessage.SwiftcaseMsgType enumeration object to select the matching schema (check the table of supported Swiftcase messages below)
//SwiftcaseMessage.extractSwiftcaseMsgType() can also be used
ValidationErrorList validationErrorList = swiftcaseMessage.validate(SwiftcaseMessage.SwiftcaseMsgType.CAMT_110_OTHR); 

if (validationErrorList.isEmpty()) {
    System.out.println(investigationRequest01.convertToXML()); //Get the generated xml
} else {
    System.out.println(validationErrorList);
}
```

In case you want to enclose the Swiftcase message under another Root Element, use the code below
```java
swiftcaseMessage.encloseSwiftcaseMessage("RequestPayload") //In case you want RequestPayload
```

### Code samples
[Parse and validate Swiftcase message](src/main/java/com/paymentcomponents/swift/mx/swiftcase/ParseAndValidateSwiftcaseMessage.java)

### Supported Swiftcase Message Types

| ISO20022 Message          | SwiftcaseMsgType ENUM | Library Object class    | Available in Demo |
|---------------------------|-----------------------|-------------------------|:-----------------:|
| camt.110.001.01 CCNR_CONR | CAMT_110_CCNR_CONR    | investigationRequest01  |                   |
| camt.111.001.02 CCNR_CONR | CAMT_111_CCNR_CONR    | InvestigationResponse02 |                   |
| camt.110.001.01 OTHR      | CAMT_110_OTHR         | investigationRequest01  |                   |
| camt.111.001.02 OTHR      | CAMT_111_OTHR         | InvestigationResponse02 |                   |
| camt.110.001.01 RQCH      | CAMT_110_RQCH         | investigationRequest01  |                   |
| camt.111.001.02 RQCH      | CAMT_111_RQCH         | InvestigationResponse02 |                   |
| camt.110.001.01 RQDA      | CAMT_110_RQDA         | investigationRequest01  |                   |
| camt.111.001.02 RQDA      | CAMT_111_RQDA         | InvestigationResponse02 |                   |
| camt.110.001.01 RQFI_COMP | CAMT_110_RQFI_COMP    | investigationRequest01  |                   |
| camt.111.001.02 RQFI_COMP | CAMT_111_RQFI_COMP    | InvestigationResponse02 |                   |
| camt.110.001.01 RQFI_SANC | CAMT_110_RQFI_SANC    | investigationRequest01  |                   |
| camt.111.001.02 RQFI_SANC | CAMT_111_RQFI_SANC    | InvestigationResponse02 |                   |
| camt.110.001.01 RQFI_UTEX | CAMT_110_RQFI_UTEX    | investigationRequest01  |                   |
| camt.111.001.02 RQFI_UTEX | CAMT_111_RQFI_UTEX    | InvestigationResponse02 |                   |
| camt.110.001.01 RQUF      | CAMT_110_RQUF         | investigationRequest01  |                   |
| camt.111.001.02 RQUF      | CAMT_111_RQUF         | InvestigationResponse02 |                   |
| camt.110.001.01 RQVA      | CAMT_110_RQVA         | investigationRequest01  |                   |
| camt.111.001.02 RQVA      | CAMT_111_RQVA         | InvestigationResponse02 |                   |
| camt.110.001.01 UTAP      | CAMT_110_UTAP         | investigationRequest01  |                   |
| camt.111.001.02 UTAP      | CAMT_111_UTAP         | InvestigationResponse02 |                   |

### Parse & Validate SEPA Message
In case you need to handle SEPA-EPC-CT messages, then you need to handle objects that extend the ISO20022 classes.
```java
//Initialize the message object
FIToFIPaymentStatusReport10SepaEpcCt fiToFIPaymentStatusReport = new FIToFIPaymentStatusReport10SepaEpcCt();
//Validate against the xml schema. We can also exit in case of errors in this step.
ValidationErrorList validationErrorList = fiToFIPaymentStatusReport.validateXML(new ByteArrayInputStream(validSepaPacs002String.getBytes()));
//Fill the message with data from xml
fiToFIPaymentStatusReport.parseXML(validSepaPacs002String);
//Validate both the xml schema and rules
validationErrorList.addAll(fiToFIPaymentStatusReport.validate());  

if (validationErrorList.isEmpty()) {
    System.out.println(fiToFIPaymentStatusReport.convertToXML()); //Get the generated xml
} else {
    System.out.println(validationErrorList);
}
```

### Construct SEPA-EPC-CT Message
```java
//Initialize the message object
FIToFIPaymentStatusReport10 fiToFIPaymentStatusReport = new FIToFIPaymentStatusReport10();

//We fill the elements of the message object using setters
fiToFIPaymentStatusReport.getMessage().setGrpHdr(new GroupHeader93());
fiToFIPaymentStatusReport.getMessage().getGrpHdr().setMsgId("1234");
//or setElement()
fiToFIPaymentStatusReport.setElement("GrpHdr/MsgId", "1234");

//Perform validation
ValidationErrorList validationErrorList = fiToFIPaymentStatusReport.validate(); 

if (validationErrorList.isEmpty()) {
    System.out.println(fiToFIPaymentStatusReport.convertToXML()); //Get the generated xml
} else {
    System.out.println(validationErrorList);
}
```

### Code samples
[Parse and validate SEPA-EPC-CT message](src/main/java/com/paymentcomponents/swift/mx/sepa/epc/ct/ParseAndValidateSepaEpcCtMessage.java)

### Supported SEPA-EPC-CT Message Types (2023 Version 1.0)

| ISO20022 Message|Library Object class                                                   | Available in Demo |
| --------------- |-------------------                                                    | :---------------: |
| camt.027.001.07 | ClaimNonReceipt07SepaEpcCt                                            |                   |
| camt.029.001.09 | ResolutionOfInvestigation09ConfirmationPositiveReplyCamt087SepaEpcCt  |                   |
| camt.029.001.09 | ResolutionOfInvestigation09NegativeReplyCamt027SepaEpcCt              |                   |
| camt.029.001.09 | ResolutionOfInvestigation09NegativeReplyCamt087SepaEpcCt              |                   |
| camt.029.001.09 | ResolutionOfInvestigation09NegativeReplyRecallSepaEpcCt               |                   |
| camt.029.001.09 | ResolutionOfInvestigation09NegativeReplyRfroSepaEpcCt                 |                   |
| camt.029.001.09 | ResolutionOfInvestigation09PositiveReplyCamt027SepaEpcCt              |                   |
| camt.029.001.09 | ResolutionOfInvestigation09PositiveReplyCamt087SepaEpcCt              |                   |
| camt.056.001.08 | FIToFIPaymentCancellationRequest08RecallSepaEpcCt                     |                   |
| camt.056.001.08 | FIToFIPaymentCancellationRequest08RfroSepaEpcCt                       |                   |
| camt.087.001.06 | RequestToModifyPayment06SepaEpcCt                                     |                   |
| pacs.002.001.10 | FIToFIPaymentStatusReport10SepaEpcCt                                  | &check;           |
| pacs.004.001.09 | PaymentReturn09PositiveReplyRecallSepaEpcCt                           |                   |
| pacs.004.001.09 | PaymentReturn09PositiveReplyRfroSepaEpcCt                             |                   |
| pacs.004.001.09 | PaymentReturn09ReturnSepaEpcCt                                        |                   |
| pacs.008.001.08 | FIToFICustomerCreditTransfer08FcSepaEpcCt                             |                   |
| pacs.008.001.08 | FIToFICustomerCreditTransfer08RtiSepaEpcCt                            |                   |
| pacs.008.001.08 | FIToFICustomerCreditTransfer08SepaEpcCt                               |                   |
| pacs.028.001.03 | FIToFIPaymentStatusRequest03InquirySepaEpcCt                          |                   |
| pacs.028.001.03 | FIToFIPaymentStatusRequest03RecallSepaEpcCt                           |                   |
| pacs.028.001.03 | FIToFIPaymentStatusRequest03RfroSepaEpcCt                             |                   |

### Auto replies

| Source Message  | Reply Message   | Source Class                                | Reply Class                                  | AutoReplies Class                                                      |
| --------------- |-----------------|---------------------------------------------------|----------------------------------------------------------|------------------------------------------------------|
| pacs.008.001.08 | pacs.004.001.09 | FIToFICustomerCreditTransfer08SepaEpcCt           | PaymentReturn09ReturnSepaEpcCt                           | FIToFICustomerCreditTransferSepaEpcCtAutoReplies     |
| pacs.008.001.08 | camt.056.001.08 | FIToFICustomerCreditTransfer08SepaEpcCt           | FIToFIPaymentCancellationRequest08RecallSepaEpcCt        | FIToFICustomerCreditTransferSepaEpcCtAutoReplies     |
| pacs.008.001.08 | camt.029.001.09 | FIToFICustomerCreditTransfer08SepaEpcCt           | ResolutionOfInvestigation09NegativeReplyRecallSepaEpcCt  | FIToFICustomerCreditTransferSepaEpcCtAutoReplies     |
| pacs.008.001.08 | camt.027.001.07 | FIToFICustomerCreditTransfer08SepaEpcCt           | ClaimNonReceipt07SepaEpcCt                               | FIToFICustomerCreditTransferSepaEpcCtAutoReplies     |
| pacs.008.001.08 | camt.087.001.06 | FIToFICustomerCreditTransfer08SepaEpcCt           | RequestToModifyPayment06SepaEpcCt                        | FIToFICustomerCreditTransferSepaEpcCtAutoReplies     |
| pacs.008.001.08 | pacs.028.001.03 | FIToFICustomerCreditTransfer08SepaEpcCt           | FIToFIPaymentStatusRequest03InquirySepaEpcCt             | FIToFICustomerCreditTransferSepaEpcCtAutoReplies     |
| camt.056.001.08 | camt.029.001.09 | FIToFIPaymentCancellationRequest08RecallSepaEpcCt | ResolutionOfInvestigation09NegativeReplyRecallSepaEpcCt  | FIToFIPaymentCancellationRequestSepaEpcCtAutoReplies |
| camt.027.001.07 | camt.029.001.09 | ClaimNonReceipt07SepaEpcCt                        | ResolutionOfInvestigation09NegativeReplyRecallSepaEpcCt  | ClaimNonReceiptSepaEpcCtAutoReplies                           |
| camt.027.001.07 | camt.029.001.09 | ClaimNonReceipt07SepaEpcCt                        | ResolutionOfInvestigation09PositiveReplyCamt027SepaEpcCt | ClaimNonReceiptSepaEpcCtAutoReplies                           |
| camt.087.001.06 | camt.029.001.09 | RequestToModifyPayment06SepaEpcCt                 | ResolutionOfInvestigation09NegativeReplyCamt087SepaEpcCt | ClaimNonReceiptSepaEpcCtAutoReplies                           |
| camt.087.001.06 | camt.029.001.09 | RequestToModifyPayment06SepaEpcCt                 | ResolutionOfInvestigation09PositiveReplyCamt087SepaEpcCt  | RequestToModifyPaymentSepaEpcCtAutoReplies                    |


Sample code for `FIToFICustomerCreditTransferSepaEpcCtAutoReplies` can be found [here](https://gist.github.com/Gizpc14/c217fae5b9b707fe16fce735378180aa).  
Sample code for `FIToFIPaymentCancellationRequestSepaEpcCtAutoReplies` can be found [here](https://gist.github.com/Gizpc14/b420e035aef83c53097ba596333d831f).  
Sample code for `ClaimNonReceiptSepaEpcCtAutoReplies` can be found [here](https://gist.github.com/Gizpc14/330c8b2613a08811fb48d512fb204131).  
Sample code for `RequestToModifyPaymentSepaEpcCtAutoReplies` can be found [here](https://gist.github.com/Gizpc14/d2afcc314953bfdfd8af4c5e62c8b536).

Please refer to [general auto replies](#auto-replies) for more details.

## SEPA-EPC Direct Debit

### SDK Setup
#### Maven
```xml
<!-- Import the SEPA-EPC-DD demo SDK-->
<dependency>
    <groupId>gr.datamation.mx</groupId>
    <artifactId>mx</artifactId>
    <version>24.20.0</version>
    <classifier>demo-sepa</classifier>
</dependency>
```
#### Gradle
```groovy
implementation 'gr.datamation.mx:mx:24.20.0:demo-sepa@jar'
```
Please refer to [General SDK Setup](#SDK-setup) for more details.

### Parse & Validate SEPA Message
In case you need to handle SEPA-EPC-DD messages, then you need to handle objects that extend the ISO20022 classes.
```java
//Initialize the message object
FIToFIPaymentStatusReport10SepaEpcDd fiToFIPaymentStatusReport = new FIToFIPaymentStatusReport10SepaEpcDd();
//Validate against the xml schema. We can also exit in case of errors in this step.
ValidationErrorList validationErrorList = fiToFIPaymentStatusReport.validateXML(new ByteArrayInputStream(validSepaPacs002String.getBytes()));
//Fill the message with data from xml
fiToFIPaymentStatusReport.parseXML(validSepaPacs002String);
//Validate both the xml schema and rules
validationErrorList.addAll(fiToFIPaymentStatusReport.validate());  

if (validationErrorList.isEmpty()) {
    System.out.println(fiToFIPaymentStatusReport.convertToXML()); //Get the generated xml
} else {
    System.out.println(validationErrorList);
}
```

### Construct SEPA-EPC-DD Message
```java
//Initialize the message object
FIToFIPaymentStatusReport10 fiToFIPaymentStatusReport = new FIToFIPaymentStatusReport10();

//We fill the elements of the message object using setters
fiToFIPaymentStatusReport.getMessage().setGrpHdr(new GroupHeader93());
fiToFIPaymentStatusReport.getMessage().getGrpHdr().setMsgId("1234");
//or setElement()
fiToFIPaymentStatusReport.setElement("GrpHdr/MsgId", "1234");

//Perform validation
ValidationErrorList validationErrorList = fiToFIPaymentStatusReport.validate(); 

if (validationErrorList.isEmpty()) {
    System.out.println(fiToFIPaymentStatusReport.convertToXML()); //Get the generated xml
} else {
    System.out.println(validationErrorList);
}
```

### Code samples
[Parse and validate SEPA-EPC-DD message](https://gist.github.com/PaymentComponents/cdd6ebc01318ee07ad4f5ce7ad21fe88)

### Supported SEPA-EPC-DD Message Types (2023 Version 1.0)

| ISO20022 Message | Library Object class                 | Available in Demo |
|------------------|--------------------------------------|:-----------------:|
| pacs.002.001.10  | FIToFIPaymentStatusReport10SepaEpcDd |                   |
| pacs.003.001.08  | FIToFICustomerDirectDebit08SepaEpcDd |                   |
| pacs.004.001.09  | PaymentReturn09SepaEpcDd             |                   |
| pacs.007.001.09  | FIToFIPaymentReversal09SepaEpcDd     |                   |

### Auto replies

| Source Message  | Reply Message   | Source Class                         | Reply Class                          | AutoReplies Class                               |
|-----------------|-----------------|--------------------------------------|--------------------------------------|-------------------------------------------------|
| pacs.003.001.08 | pacs.002.001.10 | FIToFIPaymentStatusReport10SepaEpcDd | FIToFIPaymentStatusReport10SepaEpcDd | FIToFICustomerDirectDebit08SepaEpcDdAutoReplies |
| pacs.003.001.08 | pacs.004.001.09 | FIToFIPaymentStatusReport10SepaEpcDd | PaymentReturn09SepaEpcDd             | FIToFICustomerDirectDebit08SepaEpcDdAutoReplies |
| pacs.003.001.08 | pacs.007.001.09 | FIToFIPaymentStatusReport10SepaEpcDd | FIToFIPaymentReversal09SepaEpcDd     | FIToFICustomerDirectDebit08SepaEpcDdAutoReplies |
| pacs.007.001.09 | pacs.004.001.09 | FIToFIPaymentReversal09SepaEpcDd     | PaymentReturn09SepaEpcDd             | FIToFIPaymentReversalSepaEpcDdAutoReplies       |

Sample code for `FIToFICustomerDirectDebit08SepaEpcDdAutoReplies` can be
found [here](https://gist.github.com/gantoniadispc14/961ea60d9bdfc6fdc3328ee798f607e5).

Sample code for `FIToFIPaymentReversalSepaEpcDdAutoReplies` can be
found [here](https://gist.github.com/gantoniadispc14/0876e7473e4d578b64fd1ab08f576ea5).

## SEPA-EPC Instant Payment

### SDK Setup
#### Maven
```xml
<!-- Import the SEPA-EPC-INST demo SDK-->
<dependency>
    <groupId>gr.datamation.mx</groupId>
    <artifactId>mx</artifactId>
    <version>24.20.0</version>
    <classifier>demo-sepa</classifier>
</dependency>
```
#### Gradle
```groovy
implementation 'gr.datamation.mx:mx:23.00.0:demo-sepa@jar'
```
Please refer to [General SDK Setup](#SDK-setup) for more details.

### Parse & Validate SEPA Message
In case you need to handle SEPA-EPC-INST messages, then you need to handle objects that extend the ISO20022 classes.
```java
//Initialize the message object
FIToFICustomerCreditTransfer08SepaEpcInst fiCustomerCreditTransfer08SepaEpcInst = new FIToFICustomerCreditTransfer08SepaEpcInst();
//Validate against the xml schema
ValidationErrorList validationErrorList = fiCustomerCreditTransfer08SepaEpcInst.validateXML(new ByteArrayInputStream(validSepaEpcInstPacs008string.getBytes()));
//Fill the message with data from xml
fiCustomerCreditTransfer08SepaEpcInst.parseXML(validSepaEpcInstPacs008string);
//Validate both the xml schema and rules
validationErrorList.addAll(fiCustomerCreditTransfer08SepaEpcInst.validate());

if (validationErrorList.isEmpty()) {
    System.out.println("Message is valid");
    System.out.println(fiCustomerCreditTransfer08SepaEpcInst.convertToXML()); //Get the generated xml
} else {
     System.out.println(validationErrorList);
}
```

### Construct SEPA-EPC-INST Message
```java
//Initialize the message object
FIToFICustomerCreditTransfer08SepaEpcInst fIToFIPaymentStatusReport10 = new FIToFICustomerCreditTransfer08SepaEpcInst();

//We fill the elements with the message object using setters
fIToFIPaymentStatusReport10.getMessage().setGrpHdr(new GroupHeader93());
fIToFIPaymentStatusReport10.getMessage().getGrpHdr().setMsgId("1234");
//or setElement()
fIToFIPaymentStatusReport10.setElement("GrpHdr/MsgId", "1234");

//Perform validation
ValidationErrorList validationErrorList = fIToFIPaymentStatusReport10.validate();

if (validationErrorList.isEmpty()) {
    System.out.println(fIToFIPaymentStatusReport10.convertToXML()); //Get the generated xml
} else {
     System.out.println(validationErrorList);
}
```

### Code samples
[Parse and validate SEPA-EPC-INST message](https://gist.github.com/gantoniadispc14/ae339065b6ccbe47aa7b802633c12f24)

### Supported SEPA-EPC-INST Message Types (2023 Version 1.0)

| ISO20022 Message | Library Object class                                       | Available in Demo |
|------------------|------------------------------------------------------------|:-----------------:|
| pacs.008.001.08  | FIToFICustomerCreditTransfer08SepaEpcInst                  |                   |
| pacs.002.001.10  | FIToFIPaymentStatusReport10NegativeSepaEpcInst             |                   |
| pacs.002.001.10  | FIToFIPaymentStatusReport10PositiveSepaEpcInst             |                   |
| pacs.028.001.03  | FIToFIPaymentStatusRequest03RecallSepaEpcInst              |                   |
| pacs.028.001.03  | FIToFIPaymentStatusRequest03RfroSepaEpcInst                |                   |
| pacs.028.001.03  | FIToFIPaymentStatusRequest03StatusInvestigationSepaEpcInst |                   |
| pacs.004.001.09  | PaymentReturn09PosReRecalSepaEpcInst                       |                   |
| pacs.004.001.09  | PaymentReturn09PosReRfroSepaEpcInst                        |                   |
| camt.056.001.08  | FIToFIPaymentCancellationRequest08RecallSepaEpcInst        |                   |
| camt.056.001.08  | FIToFIPaymentCancellationRequest08RfroSepaEpcInst          |                   |
| camt.029.001.09  | ResolutionOfInvestigation09NegReRecallSepaEpcInst          |                   |
| camt.029.001.09  | ResolutionOfInvestigation09NegReRfroSepaEpcInst            |                   |

### Auto replies

| Source Message  | Reply Message   | Source Class                                        | Reply Class                                         | AutoReplies Class                                  |
|-----------------|-----------------|-----------------------------------------------------|-----------------------------------------------------|----------------------------------------------------|
| pacs.008.001.08 | pacs.004.001.09 | FIToFICustomerCreditTransfer08SepaEpcInst           | PaymentReturn09PosReRecalSepaEpcInst                | FIToFICustomerCreditTransferSepaEpcInstAutoReplies |
| pacs.008.001.08 | pacs.002.001.10 | FIToFICustomerCreditTransfer08SepaEpcInst           | FIToFIPaymentStatusReport10PositiveSepaEpcInst      | FIToFICustomerCreditTransferSepaEpcInstAutoReplies |
| pacs.008.001.08 | pacs.002.001.10 | FIToFICustomerCreditTransfer08SepaEpcInst           | FIToFIPaymentStatusReport10NegativeSepaEpcInst      | FIToFICustomerCreditTransferSepaEpcInstAutoReplies |
| pacs.008.001.08 | camt.056.001.08 | FIToFICustomerCreditTransfer08SepaEpcInst           | FIToFIPaymentCancellationRequest08RecallSepaEpcInst | FIToFICustomerCreditTransferSepaEpcInstAutoReplies |
| pacs.008.001.08 | camt.029.001.09 | FIToFICustomerCreditTransfer08SepaEpcInst           | ResolutionOfInvestigation09NegReRecallSepaEpcInst   | FIToFICustomerCreditTransferSepaEpcInstAutoReplies |
| camt.056.001.08 | pacs.028.001.03 | FIToFIPaymentCancellationRequest08RecallSepaEpcInst | FIToFIPaymentStatusRequest03RecallSepaEpcInst       | FIToFIPaymentCancellationRequestEpcInstAutoReplies |

Sample code for `FIToFICustomerCreditTransferSepaEpcInstAutoReplies` can be found [here](https://gist.github.com/gantoniadispc14/c886fb5ef341241d90b003c88c7cc156).  
Sample code for `FIToFIPaymentCancellationRequestEpcInstAutoReplies` can be found [here](https://gist.github.com/gantoniadispc14/6b9c63d2ddb0ee7b74b4d03408d8ff98).

## SEPA-EBA Credit Transfer

### SDK Setup
#### Maven
```xml
<!-- Import the SEPA-EΒΑ-CT demo SDK-->
<dependency>
    <groupId>gr.datamation.mx</groupId>
    <artifactId>mx</artifactId>
    <version>24.20.0</version>
    <classifier>demo-sepa</classifier>
</dependency>
```
#### Gradle
```groovy
implementation 'gr.datamation.mx:mx:24.20.0:demo-sepa@jar'
```
Please refer to [General SDK Setup](#SDK-setup) for more details.

### Parse & Validate SEPA Message
In case you need to handle SEPA-ΕΒΑ-CT messages, there is a dedicated class for each message type.
```java
//Initialize the message object
FIToFIPaymentStatusReport10SepaEbaCt fIToFIPaymentStatusReport10 = new FIToFIPaymentStatusReport10SepaEbaCt();
//Validate against the xml schema
ValidationErrorList validationErrorList = fIToFIPaymentStatusReport10.validateXML(new ByteArrayInputStream(validSepaEbaCtPacs002String.getBytes()));

//Fill the message with data from xml
fIToFIPaymentStatusReport10.parseXML(validSepaEbaCtPacs002String);
//Validate both the xml schema and rules
validationErrorList.addAll(fIToFIPaymentStatusReport10.validate());

if (validationErrorList.isEmpty()) {
    System.out.println(fIToFIPaymentStatusReport10.convertToXML()); //Get the generated xml
} else {
    System.out.println(validationErrorList);
}
```

### Construct SEPA-EBA-CT Message
```java
//Initialize the message object
FIToFIPaymentStatusReport10SepaEbaCt fIToFIPaymentStatusReport10 = new FIToFIPaymentStatusReport10SepaEbaCt();

//We fill the elements of the message object using setters
fIToFIPaymentStatusReport10.getMessage().setGrpHdr(new SCTGroupHeader91());
fIToFIPaymentStatusReport10.getMessage().getGrpHdr().setMsgId("1234");
//or setElement()
fIToFIPaymentStatusReport10.setElement("GrpHdr/MsgId", "1234");

//Perform validation
ValidationErrorList validationErrorList = fIToFIPaymentStatusReport10.validate();

if (validationErrorList.isEmpty()) {
    System.out.println(fIToFIPaymentStatusReport10.convertToXML()); //Get the generated xml
} else {
    System.out.println(validationErrorList);
}
```

### Code samples
[Parse and validate SEPA-EPC-CT message](src/main/java/com/paymentcomponents/swift/mx/sepa/eba/ct/ParseAndValidateSepaEbaCtMessage.java)

### Supported SEPA-EBA-CT Message Types

| ISO20022 Message  | Library Object class                         | Available in Demo |
|-------------------|----------------------------------------------|:-----------------:|
| camt.027.001.07   | ClaimNonReceipt07SepaEbaCt                   |                   |
| camt.029.001.09   | ResolutionOfInvestigation09SepaEbaCt         |                   |
| camt.056.001.08   | FIToFIPaymentCancellationRequest08SepaEbaCt  |                   |
| camt.087.001.06   | RequestToModifyPayment06SepaEbaCt            |                   |
| pacs.002.001.10   | FIToFIPaymentStatusReport10SepaEbaCt         |      &check;      |
| pacs.004.001.09   | PaymentReturn09ReturnSepaEbaCt               |                   |
| pacs.008.001.08   | FIToFICustomerCreditTransfer08FcSepaEbaCt    |                   |
| pacs.028.001.03   | FIToFIPaymentStatusRequest03InquirySepaEbaCt |                   |
| SCTCvfBlkCredTrf  | CvfBulkCreditTransferSepaEbaCt               |     &check;       |
| SCTIcfBlkCredTrf  | IcfBulkCreditTransferSepaEbaCt               |                   |
| SCTIqfBlkCredTrf  | IqfBulkCreditTransferSepaEbaCt               |                   |
| SCTOqfBlkCredTrf  | OqfBulkCreditTransferSepaEbaCt               |                   |
| SCTPcfBlkCredTrf  | PcfBulkCreditTransferSepaEbaCt               |                   |
| SCTQvfBlkCredTrf  | QvfBulkCreditTransferSepaEbaCt               |                   |
| SCTRsfBlkCredTrf  | RsfBulkCreditTransferSepaEbaCt               |                   |
| SCTScfBlkCredTrf  | ScfBulkCreditTransferSepaEbaCt               |                   |

### Auto replies

| Source Message  | Reply Message   | Source Class                                | Reply Class                                  | AutoReplies Class                                    |
| --------------- |-----------------|---------------------------------------------|----------------------------------------------|------------------------------------------------------|
| pacs.008.001.08 | pacs.004.001.09 | FIToFICustomerCreditTransfer08SepaEbaCt     | PaymentReturn09SepaEbaCt                     | FIToFICustomerCreditTransferSepaEbaCtAutoReplies     |
| pacs.008.001.08 | camt.056.001.08 | FIToFICustomerCreditTransfer08SepaEbaCt     | FIToFIPaymentCancellationRequest08SepaEbaCt  | FIToFICustomerCreditTransferSepaEbaCtAutoReplies     |
| pacs.008.001.08 | camt.027.001.07 | FIToFICustomerCreditTransfer08SepaEbaCt     | ClaimNonReceipt07SepaEbaCt                   | FIToFICustomerCreditTransferSepaEbaCtAutoReplies     |
| pacs.008.001.08 | camt.087.001.06 | FIToFICustomerCreditTransfer08SepaEbaCt     | RequestToModifyPayment06SepaEbaCt            | FIToFICustomerCreditTransferSepaEbaCtAutoReplies     |
| pacs.008.001.08 | pacs.028.001.03 | FIToFICustomerCreditTransfer08SepaEbaCt     | FIToFIPaymentStatusRequest03InquirySepaEbaCt | FIToFICustomerCreditTransferSepaEbaCtAutoReplies     |
| camt.056.001.08 | camt.029.001.09 | FIToFIPaymentCancellationRequest08SepaEbaCt | ResolutionOfInvestigation09SepaEbaCt         | FIToFIPaymentCancellationRequestSepaEbaCtAutoReplies |
| camt.027.001.07 | camt.029.001.09 | ClaimNonReceipt07SepaEbaCt                  | ResolutionOfInvestigation09SepaEbaCt         | ClaimNonReceiptSepaEbaCtAutoReplies                  |
| camt.087.001.06 | camt.029.001.09 | RequestToModifyPayment06SepaEbaCt           | ResolutionOfInvestigation09SepaEbaCt         | RequestToModifyPaymentSepaEbaCtAutoReplies           |

Sample code for `FIToFICustomerCreditTransferSepaEbaCtAutoReplies` can be found [here](https://gist.github.com/PaymentComponents/2b2a43d1ae5b8e25a8b93e2cc218e209).  
Sample code for `FIToFIPaymentCancellationRequestSepaEbaCtAutoReplies` can be found [here](https://gist.github.com/PaymentComponents/a99f2911e36f15f8454a1cc8ba647ef5).  
Sample code for `ClaimNonReceiptSepaEbaCtAutoReplies` can be found [here](https://gist.github.com/PaymentComponents/9a45aa976da4343aaf35a42457b63dbb).  
Sample code for `RequestToModifyPaymentSepaEbaCtAutoReplies` can be found [here](https://gist.github.com/PaymentComponents/9ad2a36059d41f994c3cf30b648e24ff).

Please refer to [general auto replies](#auto-replies) for more details.

## SEPA-DIAS Credit Transfer

### SDK Setup
#### Maven
```xml
<!-- Import the SEPA-DIAS-CT demo SDK-->
<dependency>
    <groupId>gr.datamation.mx</groupId>
    <artifactId>mx</artifactId>
    <version>24.20.0</version>
    <classifier>demo-sepa</classifier>
</dependency>
```
#### Gradle
```groovy
implementation 'gr.datamation.mx:mx:24.20.0:demo-sepa@jar'
```
Please refer to [General SDK Setup](#SDK-setup) for more details.

### Parse & Validate SEPA Message
In case you need to handle SEPA-DIAS-CT messages, there is a dedicated class for each message type.
```java
//Initialize the message object
FIToFIPaymentStatusReport10BatchSepaDiasCt fIToFIPaymentStatusReport10 = new FIToFIPaymentStatusReport10BatchSepaDiasCt();
//Validate against the xml schema
ValidationErrorList validationErrorList = fIToFIPaymentStatusReport10.validateXML(new ByteArrayInputStream(validSepaDiasCtPacs002String.getBytes()));

//Fill the message with data from xml
fIToFIPaymentStatusReport10.parseXML(validSepaDiasCtPacs002String);
//Validate both the xml schema and rules
validationErrorList.addAll(fIToFIPaymentStatusReport10.validate());

if (validationErrorList.isEmpty()) {
    System.out.println(fIToFIPaymentStatusReport10.convertToXML()); //Get the generated xml
} else {
    System.out.println(validationErrorList);
}
```

### Construct SEPA-DIAS-CT Message
```java
//Initialize the message object
FIToFIPaymentStatusReport10BatchSepaDiasCt fIToFIPaymentStatusReport10 = new FIToFIPaymentStatusReport10BatchSepaDiasCt();

//We fill the elements of the message object using setters
fIToFIPaymentStatusReport10.getMessage().setGrpHdr(new GroupHeader91());
fIToFIPaymentStatusReport10.getMessage().getGrpHdr().setMsgId("1234");
//or setElement()
fIToFIPaymentStatusReport10.setElement("GrpHdr/MsgId", "1234");

//Perform validation
ValidationErrorList validationErrorList = fIToFIPaymentStatusReport10.validate();

if (validationErrorList.isEmpty()) {
    System.out.println(fIToFIPaymentStatusReport10.convertToXML()); //Get the generated xml
} else {
    System.out.println(validationErrorList);
}
```

### Code samples
[Parse and validate SEPA-DIAS-CT message](src/main/java/com/paymentcomponents/swift/mx/sepa/dias/ct/ParseAndValidateSepaDiasCtMessage.java)

### Supported SEPA-DIAS-CT Message Types

| ISO20022 Message | Library Object class                          | Available in Demo |
|------------------|-----------------------------------------------|:-----------------:|
| camt.027.001.07  | ClaimNonReceipt07SepaDiasCt                   |                   |
| camt.029.001.09  | ResolutionOfInvestigation09SepaDiasCt         |                   |
| camt.056.001.08  | FIToFIPaymentCancellationRequest08SepaDiasCt  |                   |
| camt.087.001.06  | RequestToModifyPayment06SepaDiasCt            |                   |
| pacs.002.001.10  | FIToFIPaymentStatusReport10BatchSepaDiasCt    |      &check;      |
| pacs.004.001.09  | PaymentReturn09ReturnSepaDiasCt               |                   |
| pacs.008.001.08  | FIToFICustomerCreditTransfer08FcSepaDiasCt    |                   |
| pacs.028.001.03  | FIToFIPaymentStatusRequest03InquirySepaDiasCt |                   |
| DIASSCTFH        | DiasSctFileHeader                             |                   |

### Auto replies

| Source Message  | Reply Message   | Source Class                                 | Reply Class                                   | AutoReplies Class                                     |
| --------------- |-----------------|----------------------------------------------|-----------------------------------------------|-------------------------------------------------------|
| pacs.008.001.08 | pacs.004.001.09 | FIToFICustomerCreditTransfer08SepaDiasCt     | PaymentReturn09SepaDiasCt                     | FIToFICustomerCreditTransferSepaDiasCtAutoReplies     |
| pacs.008.001.08 | camt.056.001.08 | FIToFICustomerCreditTransfer08SepaDiasCt     | FIToFIPaymentCancellationRequest08SepaDiasCt  | FIToFICustomerCreditTransferSepaDiasCtAutoReplies     |
| pacs.008.001.08 | camt.027.001.07 | FIToFICustomerCreditTransfer08SepaDiasCt     | ClaimNonReceipt07SepaDiasCt                   | FIToFICustomerCreditTransferSepaDiasCtAutoReplies     |
| pacs.008.001.08 | camt.087.001.06 | FIToFICustomerCreditTransfer08SepaDiasCt     | RequestToModifyPayment06SepaDiasCt            | FIToFICustomerCreditTransferSepaDiasCtAutoReplies     |
| pacs.008.001.08 | pacs.028.001.03 | FIToFICustomerCreditTransfer08SepaDiasCt     | FIToFIPaymentStatusRequest03InquirySepaDiasCt | FIToFICustomerCreditTransferSepaDiasCtAutoReplies     |
| camt.056.001.08 | camt.029.001.09 | FIToFIPaymentCancellationRequest08SepaDiasCt | ResolutionOfInvestigation09SepaDiasCt         | FIToFIPaymentCancellationRequestSepaDiasCtAutoReplies |
| camt.027.001.07 | camt.029.001.09 | ClaimNonReceipt07SepaDiasCt                  | ResolutionOfInvestigation09SepaDiasCt         | ClaimNonReceiptSepaDiasCtAutoReplies                  |
| camt.087.001.06 | camt.029.001.09 | RequestToModifyPayment06SepaDiasCt           | ResolutionOfInvestigation09SepaDiasCt         | RequestToModifyPaymentSepaDiasCtAutoReplies           |

Sample code for `FIToFICustomerCreditTransferSepaDiasCtAutoReplies` can be found [here](https://gist.github.com/PaymentComponents/2b2a43d1ae5b8e25a8b93e2cc218e209).  
Sample code for `FIToFIPaymentCancellationRequestSepaDiasCtAutoReplies` can be found [here](https://gist.github.com/PaymentComponents/a99f2911e36f15f8454a1cc8ba647ef5).  
Sample code for `ClaimNonReceiptSepaDiasCtAutoReplies` can be found [here](https://gist.github.com/PaymentComponents/9a45aa976da4343aaf35a42457b63dbb).
Sample code for `RequestToModifyPaymentSepaDiasCtAutoReplies` can be found [here](https://gist.github.com/PaymentComponents/9ad2a36059d41f994c3cf30b648e24ff).

Please refer to [general auto replies](#auto-replies) for more details.

## SEPA-SIBS Credit Transfer

### SDK Setup
#### Maven
```xml
<!-- Import the SEPA-SIBS-CT demo SDK-->
<dependency>
    <groupId>gr.datamation.mx</groupId>
    <artifactId>mx</artifactId>
    <version>24.20.0</version>
    <classifier>demo-sepa</classifier>
</dependency>
```
#### Gradle
```groovy
implementation 'gr.datamation.mx:mx:24.20.0:demo-sepa@jar'
```
Please refer to [General SDK Setup](#SDK-setup) for more details.

### Parse & Validate SEPA Message
In case you need to handle SEPA-SIBS-CT messages, there is a dedicated class for each message type.
```java
//Initialize the message object
FIToFIPaymentStatusReport10SepaSibsCt fIToFIPaymentStatusReport10 = new FIToFIPaymentStatusReport10SepaSibsCt();
//Validate against the xml schema
ValidationErrorList validationErrorList = fIToFIPaymentStatusReport10.validateXML(new ByteArrayInputStream(validSepaSibsCtPacs002String.getBytes()));

//Fill the message with data from xml
fIToFIPaymentStatusReport10.parseXML(validSepaSibsCtPacs002String);
//Validate both the xml schema and rules
validationErrorList.addAll(fIToFIPaymentStatusReport10.validate());

if (validationErrorList.isEmpty()) {
System.out.println(fIToFIPaymentStatusReport10.convertToXML()); //Get the generated xml
} else {
System.out.println(validationErrorList);
}
```

### Construct SEPA-SIBS-CT Message
```java
//Initialize the message object
FIToFIPaymentStatusReport10SepaSibsCt fIToFIPaymentStatusReport10 = new FIToFIPaymentStatusReport10SepaSibsCt();
//We fill the elements with the message object using setters
fIToFIPaymentStatusReport10.getMessage().setGrpHdr(new SCTGroupHeader91());
fIToFIPaymentStatusReport10.getMessage().getGrpHdr().setMsgId("1234");
//or setElement()
fIToFIPaymentStatusReport10.setElement("GrpHdr/MsgId", "1234");

//Perform validation
ValidationErrorList validationErrorList = fIToFIPaymentStatusReport10.validate();

if (validationErrorList.isEmpty()) {
System.out.println(fIToFIPaymentStatusReport10.convertToXML()); //Get the generated xml
} else {
System.out.println(validationErrorList);
}
```

### Code samples
[Parse and validate SEPA-SIBS-CT message](src/main/java/com/paymentcomponents/swift/mx/sepa/sibs/ct/ParseAndValidateSepaSibsCtMessage.java)

### Supported SEPA-SIBS-CT Message Types

| ISO20022 Message | Library Object class                         | Available in Demo |
|------------------|----------------------------------------------|:-----------------:|
| camt.027.001.07  | ClaimNonReceipt07SepaSibsCt                  |                   |
| camt.029.001.09  | ResolutionOfInvestigation09SepaSibsCt        |                   |
| camt.056.001.08  | FIToFIPaymentCancellationRequest08SepaSibsCt |                   |
| camt.087.001.06  | RequestToModifyPayment06SepaSibsCt           |                   |
| pacs.002.001.10  | FIToFIPaymentStatusReport10SepaSibsCt        |                   |
| pacs.004.001.09  | PaymentReturn09SepaSibsCt                    |                   |
| pacs.008.001.08  | FIToFICustomerCreditTransfer08SepaSibsCt     |                   |
| pacs.028.001.03  | FIToFIPaymentStatusRequest03SepaSibsCt       |                   |
| SCTCvfBlkCredTrf | CvfBulkCreditTransferSepaSibsCt              |                   |
| SCTIcfBlkCredTrf | IcfBulkCreditTransferSepaSibsCt              |                   |
| SCTIqfBlkCredTrf | IqfBulkCreditTransferSepaSibsCt              |                   |
| SCTOqfBlkCredTrf | OqfBulkCreditTransferSepaSibsCt              |                   |
| SCTPcfBlkCredTrf | PcfBulkCreditTransferSepaSibsCt              |                   |
| SCTQvfBlkCredTrf | QvfBulkCreditTransferSepaSibsCt              |                   |
| SCTRsfBlkCredTrf | RsfBulkCreditTransferSepaSibsCt              |                   |
| SCTScfBlkCredTrf | ScfBulkCreditTransferSepaSibsCt              |                   |

### Auto replies

| Source Message  | Reply Message   | Source Class                                 | Reply Class                                  | AutoReplies Class                                     |
|-----------------|-----------------|----------------------------------------------|----------------------------------------------|-------------------------------------------------------|
| pacs.008.001.08 | pacs.004.001.09 | FIToFICustomerCreditTransfer08SepaSibsCt     | PaymentReturn09SepaSibsCt                    | FIToFICustomerCreditTransferSepaSibsCtAutoReplies     |
| pacs.008.001.08 | camt.056.001.08 | FIToFICustomerCreditTransfer08SepaSibsCt     | FIToFIPaymentCancellationRequest08SepaSibsCt | FIToFICustomerCreditTransferSepaSibsCtAutoReplies     |
| pacs.008.001.08 | camt.027.001.07 | FIToFICustomerCreditTransfer08SepaSibsCt     | ClaimNonReceipt07SepaSibsCt                  | FIToFICustomerCreditTransferSepaSibsCtAutoReplies     |
| pacs.008.001.08 | camt.087.001.06 | FIToFICustomerCreditTransfer08SepaSibsCt     | RequestToModifyPayment06SepaSibsCt           | FIToFICustomerCreditTransferSepaSibsCtAutoReplies     |
| pacs.008.001.08 | pacs.028.001.03 | FIToFICustomerCreditTransfer08SepaSibsCt     | FIToFIPaymentStatusRequest03SepaSibsCt       | FIToFICustomerCreditTransferSepaSibsCtAutoReplies     |
| camt.056.001.08 | camt.029.001.09 | FIToFIPaymentCancellationRequest08SepaSibsCt | ResolutionOfInvestigation09SepaSibsCt        | FIToFIPaymentCancellationRequestSepaSibsCtAutoReplies |
| camt.027.001.07 | camt.029.001.09 | ClaimNonReceipt07SepaSibsCt                  | ResolutionOfInvestigation09SepaSibsCt        | ClaimNonReceiptSepaSibsCtAutoReplies                  |
| camt.087.001.06 | camt.029.001.09 | RequestToModifyPayment06SepaSibsCt           | ResolutionOfInvestigation09SepaSibsCt        | RequestToModifyPaymentSepaSibsCtAutoReplies           |

Sample code for `FIToFICustomerCreditTransferSepaSibsCtAutoReplies` can be found [here](https://gist.github.com/PaymentComponents/cc0e7ea40ad1ba6fae5f6cf4e6e11e0f).  
Sample code for `FIToFIPaymentCancellationRequestSepaSibsCtAutoReplies` can be found [here](https://gist.github.com/PaymentComponents/5aa981322b1d3f7d3ed24584c6b58b90).  
Sample code for `ClaimNonReceiptSepaSibsCtAutoReplies` can be found [here](https://gist.github.com/PaymentComponents/174b43008a6f8cbfe610ef453626da0f).  
Sample code for `RequestToModifyPaymentSepaEbaCtAutoReplies` can be found [here](https://gist.github.com/PaymentComponents/1e1c1fb2b3caad1b775e3f6e96a1aee6).

Please refer to [general auto replies](#auto-replies) for more details.

## SEPA-SIBS Direct Debit

### SDK Setup
#### Maven
```xml
<!-- Import the SEPA-SIBS-CT demo SDK-->
<dependency>
    <groupId>gr.datamation.mx</groupId>
    <artifactId>mx</artifactId>
    <version>24.20.0</version>
    <classifier>demo-sepa</classifier>
</dependency>
```
#### Gradle
```groovy
implementation 'gr.datamation.mx:mx:24.20.0:demo-sepa@jar'
```
Please refer to [General SDK Setup](#SDK-setup) for more details.

### Parse & Validate SEPA Message
In case you need to handle SEPA-SIBS-DD messages, there is a dedicated class for each message type.
```java
 //Initialize the message object
FIToFIPaymentStatusReport10S2SepaSibsDd fIToFIPaymentStatusReport10 = new FIToFIPaymentStatusReport10S2SepaSibsDd();
//Validate against the xml schema
ValidationErrorList validationErrorList = fIToFIPaymentStatusReport10.validateXML(new ByteArrayInputStream(validSepaSibsDdPacs002String.getBytes()));

//Fill the message with data from xml
fIToFIPaymentStatusReport10.parseXML(validSepaSibsDdPacs002String);
//Validate both the xml schema and rules
validationErrorList.addAll(fIToFIPaymentStatusReport10.validate());

if (validationErrorList.isEmpty()) {
System.out.println(fIToFIPaymentStatusReport10.convertToXML()); //Get the generated xml
} else {
System.out.println(validationErrorList);
}
```

### Construct SEPA-SIBS-DD Message
```java
//Initialize the message object
FIToFIPaymentStatusReport10S2SepaSibsDd fIToFIPaymentStatusReport10 = new FIToFIPaymentStatusReport10S2SepaSibsDd();
//We fill the elements with the message object using setters
fIToFIPaymentStatusReport10.getMessage().setGrpHdr(new SDDGroupHeader91());
fIToFIPaymentStatusReport10.getMessage().getGrpHdr().setMsgId("1234");
//or setElement()
fIToFIPaymentStatusReport10.setElement("GrpHdr/MsgId", "1234");

//Perform validation
ValidationErrorList validationErrorList = fIToFIPaymentStatusReport10.validate();

if (validationErrorList.isEmpty()) {
System.out.println(fIToFIPaymentStatusReport10.convertToXML()); //Get the generated xml
} else {
System.out.println(validationErrorList);
}
```

### Code samples
[Parse and validate SEPA-SIBS-DD message](src/main/java/com/paymentcomponents/swift/mx/sepa/sibs/ct/ParseAndValidateSepaSibsDdMessage.java)

### Supported SEPA-SIBS-DD Message Types

| ISO20022 Message  | Library Object class                         | Available in Demo |
|-------------------|----------------------------------------------|:-----------------:|
| camt.056.001.08   | FIToFIPaymentCancellationRequest08SepaSibsDd |                   |
| pacs.002.001.10   | FIToFIPaymentStatusReport10SepaSibsDd        |                   |
| pacs.002.001.10S2 | FIToFIPaymentStatusReport10S2SepaSibsDd      |                   |
| pacs.003.001.08   | FIToFICustomerDirectDebit08SepaSibsDd        |                   |
| pacs.004.001.09   | PaymentReturn09SepaSibsDd                    |                   |
| pacs.007.001.09   | FIToFIPaymentReversal09SepaSibsDd            |                   |
| MPEDDCdfBlkDirDeb | MPEDDCdfBulkDirectDebit                      |                   |
| MPEDDDnxBlkDirDeb | MPEDDDnxBulkDirectDebit                      |                   |
| MPEDDDrxBlkDirDeb | MPEDDDrxBulkDirectDebit                      |                   |
| MPEDDDvfBlkDirDeb | MPEDDDvfBulkDirectDebit                      |                   |
| MPEDDIdxBlkDirDeb | MPEDDIdxBulkDirectDebit                      |                   |
| MPEDDIrxBlkDirDeb | MPEDDIrxBulkDirectDebit                      |                   |
| MPEDDRsfBlkDirDeb | MPEDDRsfBulkDirectDebit                      |                   |
| MPEDDSdfBlkDirDeb | MPEDDSdfBulkDirectDebit                      |                   |

### Auto replies

| Source Message  | Reply Message   | Source Class                          | Reply Class                           | AutoReplies Class                              |
|-----------------|-----------------|---------------------------------------|---------------------------------------|------------------------------------------------|
| pacs.003.001.08 | pacs.002.001.10 | FIToFICustomerDirectDebit08SepaSibsDd | FIToFIPaymentStatusReport10SepaSibsDd | FIToFICustomerDirectDebitSepaSibsDdAutoReplies |
| pacs.003.001.08 | pacs.004.001.09 | FIToFICustomerDirectDebit08SepaSibsDd | PaymentReturn09SepaSibsDd             | FIToFICustomerDirectDebitSepaSibsDdAutoReplies |
| pacs.003.001.08 | pacs.007.001.09 | FIToFICustomerDirectDebit08SepaSibsDd | FIToFIPaymentReversal09SepaSibsDd     | FIToFICustomerDirectDebitSepaSibsDdAutoReplies |
| pacs.007.001.09 | pacs.004.001.09 | FIToFIPaymentReversal09SepaSibsDd     | PaymentReturn09SepaSibsDd             | FIToFIPaymentReversalSepaSibsDdAutoReplies     |

Sample code for `FIToFICustomerDirectDebitSepaSibsDdAutoReplies` can be found [here](https://gist.github.com/gantoniadispc14/f6539013d526670ac4b922f9345655a4).

Sample code for `FIToFIPaymentReversalSepaSibsDdAutoReplies` can be found [here](https://gist.github.com/gantoniadispc14/fd3e6f26451d3f4edb54f354355f7cbe).

Please refer to [general auto replies](#auto-replies) for more details.


### See more provided SDKs on ISO20022 and SWIFT MT [here](https://github.com/Payment-Components)
