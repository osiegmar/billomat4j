module billomat4j {

    exports de.siegmar.billomat4j.domain;
    exports de.siegmar.billomat4j.service;

    exports de.siegmar.billomat4j.domain.article;
    exports de.siegmar.billomat4j.domain.client;
    exports de.siegmar.billomat4j.domain.confirmation;
    exports de.siegmar.billomat4j.domain.creditnote;
    exports de.siegmar.billomat4j.domain.deliverynote;
    exports de.siegmar.billomat4j.domain.invoice;
    exports de.siegmar.billomat4j.domain.offer;
    exports de.siegmar.billomat4j.domain.recurring;
    exports de.siegmar.billomat4j.domain.reminder;
    exports de.siegmar.billomat4j.domain.settings;
    exports de.siegmar.billomat4j.domain.template;
    exports de.siegmar.billomat4j.domain.types;
    exports de.siegmar.billomat4j.domain.unit;
    exports de.siegmar.billomat4j.domain.user;

    opens de.siegmar.billomat4j.domain to com.fasterxml.jackson.databind;
    opens de.siegmar.billomat4j.domain.article to com.fasterxml.jackson.databind;
    opens de.siegmar.billomat4j.domain.client to com.fasterxml.jackson.databind;
    opens de.siegmar.billomat4j.domain.confirmation to com.fasterxml.jackson.databind;
    opens de.siegmar.billomat4j.domain.creditnote to com.fasterxml.jackson.databind;
    opens de.siegmar.billomat4j.domain.deliverynote to com.fasterxml.jackson.databind;
    opens de.siegmar.billomat4j.domain.invoice to com.fasterxml.jackson.databind;
    opens de.siegmar.billomat4j.domain.offer to com.fasterxml.jackson.databind;
    opens de.siegmar.billomat4j.domain.recurring to com.fasterxml.jackson.databind;
    opens de.siegmar.billomat4j.domain.reminder to com.fasterxml.jackson.databind;
    opens de.siegmar.billomat4j.domain.settings to com.fasterxml.jackson.databind;
    opens de.siegmar.billomat4j.domain.template to com.fasterxml.jackson.databind;
    opens de.siegmar.billomat4j.domain.types to com.fasterxml.jackson.databind;
    opens de.siegmar.billomat4j.domain.unit to com.fasterxml.jackson.databind;
    opens de.siegmar.billomat4j.domain.user to com.fasterxml.jackson.databind;

    requires java.net.http;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires org.apache.commons.lang3;
    requires org.slf4j;
    requires com.fasterxml.jackson.datatype.jsr310;

}
