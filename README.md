# billomat4j

[![build](https://github.com/osiegmar/billomat4j/workflows/build/badge.svg?branch=master)](https://github.com/osiegmar/billomat4j/actions?query=branch%3Amaster)
[![javadoc](https://javadoc.io/badge2/de.siegmar/billomat4j/javadoc.svg)](https://javadoc.io/doc/de.siegmar/billomat4j)
[![Maven Central](https://img.shields.io/maven-central/v/de.siegmar/billomat4j.svg)](https://search.maven.org/artifact/de.siegmar/billomat4j)

Java Software Development Kit for the [Billomat API](https://www.billomat.com/api/).

## Requirements

- Java 8
- Apache Commons Lang
- Jackson JSON Processor
- SLF4J

## Example

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

## Testing

**WARNING**: Do not run the integration tests with your regular Billomat account.
The tests will wipe out all your invoices, clients and so on.
Ask the Billomat-Team for a dedicated Test-User!

Create a file "src/test/resources/billomat.properties" with this content:

```
billomatId = <your-billomat-id>
billomatApiKey = <your-billomat-api-key>
billomatAppId = <your-billomat-app-id>
billomatAppSecret = <your-billomat-app-secret>
email = <your-email-address>
```

The email address is required for sending test documents (like invoices).

Run the integration test suite by invoking:

```
./gradlew integrationTest
```

## Copyright

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
