billomat4j
==========

[![Build Status](https://api.travis-ci.org/osiegmar/billomat4j.svg)](https://travis-ci.org/osiegmar/billomat4j)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/de.siegmar/billomat4j/badge.svg)](https://maven-badges.herokuapp.com/maven-central/de.siegmar/billomat4j)

Java Software Development Kit for the [Billomat API](http://www.billomat.com/api).


Latest release
--------------

The most recent release is 1.1.3, released May 26, 2016.

To add a dependency using Maven, use the following:

```xml
<dependency>
    <groupId>de.siegmar</groupId>
    <artifactId>billomat4j</artifactId>
    <version>1.1.3</version>
</dependency>
```

To add a dependency using Gradle:

```gradle
dependencies {
    compile 'de.siegmar:billomat4j:1.1.3'
}
```


Requirements
------------

- Java 8
- Apache Commons Lang
- Jackson JSON Processor
- SLF4J


Example
-------

This example fetches all paid invoices for the last 30 days and prints them out.
This file is also part of this project - see src/test/java/de/siegmar/billomat4j/Example.java

```java
BillomatConfiguration billomatConfiguration = new BillomatConfiguration();
billomatConfiguration.setBillomatId("<billomatId>");
billomatConfiguration.setApiKey("<apiKey>");

InvoiceService invoiceService = new InvoiceServiceImpl(billomatConfiguration);

System.out.println("Paid invoices for the last 30 days:");

InvoiceFilter invoiceFilter = new InvoiceFilter()
    .byFrom(LocalDate.now().minusDays(30))
    .byTo(LocalDate.now())
    .byStatus(InvoiceStatus.PAID);

for (Invoice invoice : invoiceService.findInvoices(invoiceFilter)) {
    System.out.println("Invoice " + invoice.getInvoiceNumber() + ": " + invoice.getTotalNet());
}
```

Testing
-------

**WARNING**: Do not run the integration tests with your regular Billomat account.
The tests will wipe out all your invoices, clients and so on.
Ask the Billomat-Team for a dedicated Test-User!

Create a file "src/test/resources/billomat.properties" with this content:

```
billomatId = <your-billomat-id>
billomatApiKey = <your-billomat-api-key>
email = <your-email-address>
```

The email address is required for sending test documents (like invoices).

Run the integration test suite by invoking:

```
./gradlew integrationTest
```


Contribution
------------

- Fork
- Code
- Add test(s)
- Commit
- Send me a pull request


Copyright
---------

Copyright 2012 Oliver Siegmar

Billomat4J is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Billomat4J is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with Billomat4J.  If not, see <http://www.gnu.org/licenses/>.
