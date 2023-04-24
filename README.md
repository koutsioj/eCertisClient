# ECertisClient - Java library
Java library for executing eCertis API calls. 
 
 ## Table of contents
* [Introduction](#introduction)
* [Functionality](#functionality)
* [Technologies](#technologies)
* [Setup](#setup)

## Introduction
This Java library was created for the purpose of familiarizing myself with the eCertis REST API, in addition to Jackson libraries and the Gradle build tool. It is part of my undergraduate thesis for my Digital Systems studies at the University of Piraeus.

## Functionality
ECertisClient is a Java library that provides a simple way to execute eCertis API calls from your Java application. 
The library includes methods for retrieving information on 'criteria' and 'evidences' in JSON format and supports filtering by country, language, version and more. 

Demonstration of how to use the library :

```
// create a new instance of the ECertisClient
ECertisClient client = new ECertisClient();

// set the method parameters
String uri = "https://webgate.acceptance.ec.europa.eu/tools3/ecertis2/criteria/";
String id = "c27b7c4e-c837-4529-b867-ed55ce639db5";
String version = "2";
String lang = "";
String countryFilter = "be";
String json = null;
try {
     // call the getCriterion method with the parameters
     json = client.criterionCall(uri, id, version, lang, countryFilter);

     System.out.println(json);
} catch (Exception e) {
     // handle any exceptions that might be thrown
     System.err.println("An error occurred: " + e.getMessage());
}
```

You may also enable caching by using the boolean constructor as shown below :
```
ECertisClient client = new ECertisClient(true);
...
 json = client.criterionCall(uri, id, version, lang, countryFilter);
...
```

## Technologies
* Java 17
* Gradle 7.4.2

## Setup
To use this library, download the .jar file in 'build/libs' and add it as an external library to your Java project. 
Alternatively, downloading the project and using the assemble/build gradle tasks automatically publishes the library to your maven local repository. Then, you can use the maven local repository to add the library as a dependency by the name of `com.github.koutsioj:eCertisClient:1.0.0` to your other projects.