# ISO20022 Message Validator Demo

The project is here to demonstrate how our [SDK](https://www.paymentcomponents.com/messaging-libraries/) for 
SWIFT MX (ISO20022) Messages Validation works. For our demonstration we are going to use the demo SDK which can parse/validate/generate a pacs.002.001.XX message. 

It's a simple maven project, you can download it and run it, with Java 1.8 or above.

## SDK setup
Incorporate the SDK [jar](https://nexus.paymentcomponents.com/repository/public/gr/datamation/mx/mx/20.7.0/mx-20.7.0-demo.jar) into your project by the regular IDE means. 
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
    <version>20.7.0</version>
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
Import the SDK
```groovy
implementation 'gr.datamation.mx:mx:20.7.0:demo@jar'
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
    
    Parsing a file
    ```java
    message.parseAndValidate(new File("/path/to/pacs.002.001.11.xml"));
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
- [Parse a valid pacs.002](src/main/java/com/paymentcomponents/swift/mx/ParseValidPacs002_11.java)
- [Parse an invalid pacs.002](src/main/java/com/paymentcomponents/swift/mx/ParseInvalidPacs002_11.java) and get the syntax and network validations errors
- [Build a valid pacs.002](src/main/java/com/paymentcomponents/swift/mx/BuildValidPacs002_11.java)
- [Convert an MX from text to xml](src/main/java/com/paymentcomponents/swift/mx/ConvertMX2XML.java)



