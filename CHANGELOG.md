# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/)
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]
### Added
- Added LetterService
- Added lots of new attributes
- Added configurable Accept-Language header

### Fixed
- Fix JSON name for confirmation-items, credit-note-items, delivery-note-items and reminder-items

### Changed
- Use Lombok to ease maintainability
- Refactored some attribute names
- Removed dependency to commons-lang3
- Handle unknown enum values lenient

## [2.0.1] - 2022-12-26
### Added
- Added email_template_id
- Added template_id

### Changed
- Updated slf4j-api dependency to version 1.7.36

## [2.0.0] - 2022-01-29
### Added
- Added customer portal URL ([#3](https://github.com/osiegmar/billomat4j/pull/3))
- Added SEPA mandate to client ([#4](https://github.com/osiegmar/billomat4j/pull/4))
- Added LETTER template type

### Changed
- Update to Java 11 and modularized code
- Make use of Optional as return type
- Update dependency versions
- Replaced TestNG with JUnit 5

### Removed
- Removed service interfaces

## [1.1.3] - 2016-05-26
### Changed
- Lowered the Jackson dependency to support version 2.6 and 2.7

## [1.1.2] - 2016-05-26
### Added
- Added RecurringAction MAIL (wasn't documented in API so far)

## [1.1.1] - 2016-05-25
### Fixed
- Fixed filtering by date (regression in 1.1.0)

## [1.1.0] - 2016-05-25
### Added
- Added support for registered Apps (appId / appSecret headers)
- Added payment types INVOICE_CORRECTION and CHECK

### Changed
- Renamed package from net.siegmar to de.siegmar (preparation for maven central)
- Updated to Java 8 (new time api)
- Updated dependencies

### Fixed
- Fixed an issue that made stopping of Recurrings impossible

## [1.0.5] - 2013-10-03
### Added
- Added support for downloading offer PDFs
- Added support for recurring cycle numbers
- Added support for default_email_sender and print_version in Settings
- Added support for client contacts

### Changed
- Replaced Maven with Gradle for building
- Updated versions of dependencies

### Removed
- Removed CLI

## [1.0.4] - 2013-04-13
### Added
- Added support for archiving customers
- Added support for paid_amount and open_amount in invoices
- Added support for bgcolor, color1, color2, color3 in settings
- Added support for user salutation

### Removed
- Removed setters for read-only elements in settings

## [1.0.3] - 2013-02-09
### Added
- Added support for "next" numbers in settings
- Added support for BCC addresses in settings

### Changed
- Run integration tests in a separate profile

### Fixed
- Updated Jackson JSON library to fix null-Enum bug

## [1.0.2] - 2012-12-21
### Added
- Added support for recurring e-mail receivers

### Changed
- Replaced JUnit with TestNG
- Updated versions of dependencies

## [1.0.1] - 2012-11-18
### Added
- Billomat JSON-API now supports multiple e-mail recipients

## 1.0.0 - 2012-11-04

- First release

[Unreleased]: https://github.com/osiegmar/billomat4j/compare/v2.0.1...develop
[2.0.1]: https://github.com/osiegmar/billomat4j/compare/v2.0.0...v2.0.1
[2.0.0]: https://github.com/osiegmar/billomat4j/compare/v1.1.3...v2.0.0
[1.1.3]: https://github.com/osiegmar/billomat4j/compare/v1.1.2...v1.1.3
[1.1.2]: https://github.com/osiegmar/billomat4j/compare/v1.1.1...v1.1.2
[1.1.1]: https://github.com/osiegmar/billomat4j/compare/v1.0.0...v1.1.1
[1.1.0]: https://github.com/osiegmar/billomat4j/compare/v1.0.5...v1.1.0
[1.0.5]: https://github.com/osiegmar/billomat4j/compare/v1.0.4...v1.0.5
[1.0.4]: https://github.com/osiegmar/billomat4j/compare/v1.0.3...v1.0.4
[1.0.3]: https://github.com/osiegmar/billomat4j/compare/v1.0.2...v1.0.3
[1.0.2]: https://github.com/osiegmar/billomat4j/compare/v1.0.1...v1.0.2
[1.0.1]: https://github.com/osiegmar/billomat4j/compare/v1.0.0...v1.0.1
