# ISO20022 Message Validator Demo

This project is part of the [FINaplo](https://finaplo.paymentcomponents.com) product and is here to demonstrate how our [SDK](https://finaplo.paymentcomponents.com/financial-messages) for SWIFT MX (ISO20022) Messages Validation works. 
For our demonstration we are going to use the demo SDK which can parse/validate/generate a pacs.002.001.XX message. 

It's a simple maven project, you can download it and run it, with Java 1.8 or above.

## SDK setup
Incorporate the SDK [jar](https://nexus.paymentcomponents.com/repository/public/gr/datamation/mx/mx/21.0.0/mx-21.0.0-demo.jar) into your project by the regular IDE means. 
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
    <version>21.0.0</version>
    <classifier>demo</classifier>
</dependency>
<!-- Import the CBPR+ demo SDK-->
<dependency>
    <groupId>gr.datamation.mx</groupId>
    <artifactId>mx</artifactId>
    <version>21.0.0</version>
    <classifier>demo-cbpr</classifier>
</dependency>
<!--Import the TARGET2 (RTGS) demo SDK-->
<dependency>
    <groupId>gr.datamation.mx</groupId>
    <artifactId>mx</artifactId>
    <version>21.0.0</version>
    <classifier>demo-rtgs</classifier>
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
Import the SDK
```groovy
implementation 'gr.datamation.mx:mx:21.0.0:demo@jar'
implementation 'gr.datamation.mx:mx:21.0.0:demo-cbpr@jar'
implementation 'gr.datamation.mx:mx:21.0.0:demo-rtgs@jar'
```
In case you purchase the SDK you will be given a protected Maven repository with a user name and a password. You can configure your project to download the SDK from there.

#### Other dependencies
There is a dependency in [groovy-all](https://mvnrepository.com/artifact/org.codehaus.groovy/groovy-all/2.4.8) library which is required for some of the included features (version 2.4.8 or later). 
You can use maven or gradle to add the dependencies below or manually include the jar to your project.

##### Maven 
```xml
<dependency>
    <groupId>org.codehaus.groovy</groupId>
    <artifactId>groovy-all</artifactId>
    <version>2.4.8</version>
</dependency>
```
##### Gradle 
```groovy
compile group: 'org.codehaus.groovy', name: 'groovy-all', version: '2.4.8'
```


## HOW-TO Use our SDK

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

1. ##### Add data to the class.
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
    
1. ##### Validate the message.
    After building a Swift MX message using the appropriate class, user may want to validate this message. Of course validation is not mandatory but is the only way to prove that the message is correct. 
    Validation is performed by calling the validate() method and internally is a two step process:
    - Validation against the schema of the message in order to ensure that the message is a well-formed instance of it.  
    - Validation against any Validation Rule as described for that message by the ISO20022 rules.  
    
    The validate() method returns an ArrayList containing the validation errors that may occur. 
    Each error is contained in the ArrayList as a ValidationError object describing the error.
    
    ```java
    ValidationErrorList errorList = message.validate();
    ```
   
### Code Samples

In this project you can see code for all the basic manipulation of an MX message, like:
- [Parse and validate valid pacs.002](src/main/java/com/paymentcomponents/swift/mx/ParseAndValidateValidPacs002_11.java)
- [Parse and validate an invalid pacs.002 (get syntax, network validation errors)](src/main/java/com/paymentcomponents/swift/mx/ParseAndValidateInvalidPacs002_11.java)
- [Build a valid pacs.002](src/main/java/com/paymentcomponents/swift/mx/BuildValidPacs002_11.java)
- [Convert an MX message to XML](src/main/java/com/paymentcomponents/swift/mx/ConvertMX2XML.java)
- [Parse and validate CBPR+ message](src/main/java/com/paymentcomponents/swift/mx/cbpr/ParseAndValidateCbprMessage.java)
- [Parse and validate TARGET2 message](src/main/java/com/paymentcomponents/swift/mx/rtgs/ParseAndValidateRtgsMessage.java)


### More features are included in the paid version like

- #### CBPR+ messages  
    In case you need to handle CBPR+ messages, then you need to handle objects of CbprMessage class.
    
    ##### Parse CBPR+ Message
    ```java
    //Initialize the cbprMessage
    CbprMessage cbprMessage = new CbprMessage(new BusinessApplicationHeader02(), new FIToFICustomerCreditTransfer08());
    //Validate CBPR+ against the xml schema. We can also exit in case of errors in this step.
    ValidationErrorList validationErrorList = cbprMessage.validateXml(new ByteArrayInputStream(validCbprPacs008String.getBytes()));
    //Fill the cbprMessage with data from xml
    cbprMessage.parseXml(validCbprPacs008String); 
    //Perform validation in both header and message object using cbprMessage
    //Use CbprMessage.CbprMsgType enumeration object to select the matching schema (check the table of supported CBPR messages below
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

    ###### AutoParse CBPR+ Message
    ```java
    //Initialize the cbprMessage
    CbprMessage cbprMessage = new CbprMessage();
    //Validate CBPR+ against the xml schema. We can also exit in case of errors in this step.
    ValidationErrorList validationErrorList = cbprMessage.validateXml(new ByteArrayInputStream(validCbprPacs008String.getBytes()));
    //Fill the cbprMessage with data from xml
    cbprMessage.autoParseXml(validCbprPacs008String);
  
    //Perform validation in both header and message object using cbprMessage
    validationErrorList.addAll(cbprMessage.autoValidate());
  
    if (validationErrorList.isEmpty()) {
        System.out.println(fiToFICustomerCreditTransfer.convertToXML()); //Get the generated xml
    } else {
        System.out.println(validationErrorList);
    }
    ```
    
    ##### Construct CBPR+ Message
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
    CbprMessage cbprMessage = new CbprMessage(businessApplicationHeader, fiToFICustomerCreditTransfer);
  
    //Perform validation in both header and message object using cbprMessage
    //Use CbprMessage.CbprMsgType enumeration object to select the matching schema (check the table of supported CBPR messages below)
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
  
    ##### Supported CBPR+ Message Types  
        
    | ISO20022 Message | CbprMsgType ENUM | Library Object class                    | Available in Demo |
    | ---------------- | ---------------- | --------------------                    | :---------------: |
    | camt.029.001.09  | CAMT_029         | ResolutionOfInvestigation09             |                   |
    | camt.052.001.08  | CAMT_052         | BankToCustomerAccountReport08           |                   |
    | camt.053.001.08  | CAMT_053         | BankToCustomerStatement08               |                   |
    | camt.054.001.08  | CAMT_054         | BankToCustomerDebitCreditNotification08 |                   |
    | camt.056.001.08  | CAMT_056         | FIToFIPaymentCancellationRequest08      |                   |
    | camt.057.001.06  | CAMT_057         | NotificationToReceive06                 |                   |
    | camt.060.001.05  | CAMT_060         | AccountReportingRequest05               |                   |
    | pacs.002.001.10  | PACS_002         | FIToFIPaymentStatusReport10             |                   |
    | pacs.004.001.09  | PACS_004         | PaymentReturn09                         |                   |
    | pacs.008.001.08  | PACS_008         | FIToFICustomerCreditTransfer08          |                   |
    | pacs.008.001.08  | PACS_008_STP     | FIToFICustomerCreditTransfer08          |                   |
    | pacs.009.001.08  | PACS_009_CORE    | FinancialInstitutionCreditTransfer08    | &check;           |
    | pacs.009.001.08  | PACS_009_COV     | FinancialInstitutionCreditTransfer08    |                   |
    | pacs.009.001.08  | PACS_009_ADV     | FinancialInstitutionCreditTransfer08    |                   |
    | pacs.010.001.03  | PACS_010         | FinancialInstitutionDirectDebit03       |                   |
    | pain.001.001.09  | PAIN_001         | CustomerCreditTransferInitiation09      |                   |
    | pain.002.001.10  | PAIN_002         | CustomerPaymentStatusReport10           |                   |

- #### TARGET2 (RTGS) messages  
  In case you need to handle TARGET2 (RTGS) messages, then you need to handle objects that extend the ISO20022 classes.

  ##### Parse TARGET2 Message
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

  ###### AutoParse TARGET2 Message
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

  ##### Construct TARGET2 Message
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

  ##### Supported TARGET2 Message Types

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
  
