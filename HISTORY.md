## 1.1.3 (2016-05-26)

* Lowered the Jackson dependency to support version 2.6 and 2.7

## 1.1.2 (2016-05-26)

* Added RecurringAction MAIL (wasn't documented in API so far)

## 1.1.1 (2016-05-25)

* Fixed filtering by date (regression in 1.1.0)

## 1.1.0 (2016-05-25)

* Renamed package from net.siegmar to de.siegmar (preparation for maven central)
* Fixed an issue that made stopping of Recurrings impossible
* Added support for registered Apps (appId / appSecret headers)
* Added payment types INVOICE_CORRECTION and CHECK
* Updated to Java 8 (new time api)
* Updated dependencies

## 1.0.5 (2013-10-03)

* Added support for downloading offer PDFs
* Removed CLI
* Replaced Maven with Gradle for building
* Added support for recurring cycle numbers
* Added support for default_email_sender and print_version in Settings
* Added support for client contacts
* Updated versions of dependencies

## 1.0.4 (2013-04-13)

* Removed setters for read-only elements in settings
* Added support for archiving customers
* Added support for paid_amount and open_amount in invoices
* Added support for bgcolor, color1, color2, color3 in settings
* Added support for user salutation

## 1.0.3 (2013-02-09)

* Run integration tests in a separate profile
* Updated Jackson JSON library to fix null-Enum bug
* Added support for "next" numbers in settings
* Added support for BCC addresses in settings

## 1.0.2 (2012-12-21)

* Replaced JUnit with TestNG
* Updated versions of dependencies
* Added support for recurring e-mail receivers

## 1.0.1 (2012-11-18)

* Billomat JSON-API now supports multiple e-mail recipients

## 1.0.0 (2012-11-04)

* First release
