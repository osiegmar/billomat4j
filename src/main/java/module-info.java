module billomat4j {

    exports de.siegmar.billomat4j.json to com.fasterxml.jackson.databind;

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

    opens de.siegmar.billomat4j.domain;
    opens de.siegmar.billomat4j.domain.article;
    opens de.siegmar.billomat4j.domain.client;
    opens de.siegmar.billomat4j.domain.confirmation;
    opens de.siegmar.billomat4j.domain.creditnote;
    opens de.siegmar.billomat4j.domain.deliverynote;
    opens de.siegmar.billomat4j.domain.invoice;
    opens de.siegmar.billomat4j.domain.offer;
    opens de.siegmar.billomat4j.domain.recurring;
    opens de.siegmar.billomat4j.domain.reminder;
    opens de.siegmar.billomat4j.domain.settings;
    opens de.siegmar.billomat4j.domain.template;
    opens de.siegmar.billomat4j.domain.types;
    opens de.siegmar.billomat4j.domain.unit;
    opens de.siegmar.billomat4j.domain.user;

    requires java.net.http;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires org.slf4j;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires static lombok;

}
