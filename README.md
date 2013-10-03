billomat4j
==========

Java Software Development Kit for the [Billomat](http://www.billomat.com/) [API](http://www.billomat.com/api).


Requirements
------------

- At runtime:
    - Java 7
    - Apache Commons Lang
    - Jackson JSON Processor
    - SLF4J
- Additionally at buildtime:
    - Apache Maven


Example
-------

This example fetches all paid invoices for the last 30 days and prints them out. This file is also part of this project - see src/test/java/net/siegmar/billomat4j/sdk/Example.java

    BillomatConfiguration billomatConfiguration = new BillomatConfiguration();
    billomatConfiguration.setBillomatId("<billomatId>");
    billomatConfiguration.setApiKey("<apiKey>");

    InvoiceService invoiceService = new InvoiceServiceImpl(billomatConfiguration);

    System.out.println("Paid invoices for the last 30 days:");

    InvoiceFilter invoiceFilter = new InvoiceFilter()
        .byFrom(DateUtils.addDays(new Date(), -30))
        .byTo(new Date())
        .byStatus(InvoiceStatus.PAID);

    for (Invoice invoice : invoiceService.findInvoices(invoiceFilter)) {
        System.out.println("Invoice " + invoice.getInvoiceNumber() + ": " + invoice.getTotalNet());
    }


Testing
-------

**WARNING**: Do not run the integration tests with your regular Billomat account. The tests will wipe out all your invoices, clients and so on. Ask the Billomat-Team for a dedicated Test-User!

Create a file "src/test/resources/billomat.properties" with this content:

    billomatId = <your-billomat-id>
    billomatApiKey = <your-billomat-api-key>
    email = <your-email-address>

The email address is required for sending test documents (like invoices).

Run the integration test suite by invoking:

    mvn -P integration-tests package


Contribution
------------

- Fork
- Code
- Add test(s)
- Commit
- Send me a pull request


Copyright
---------

Copyright 2012 Oliver Siegmar <oliver@siegmar.net>

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

