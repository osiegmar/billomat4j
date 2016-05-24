/*
 * Copyright 2012 Oliver Siegmar <oliver@siegmar.net>
 *
 * This file is part of Billomat4J.
 *
 * Billomat4J is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Billomat4J is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Billomat4J.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.siegmar.billomat4j.sdk.domain.invoice;

import de.siegmar.billomat4j.sdk.domain.ActionKey;

public enum InvoiceActionKey implements ActionKey {

    COMMENT,
    CREATE,
    COPY,
    CREATE_FROM_OFFER,
    CREATE_FROM_RECURRING,
    STATUS,
    PAYMENT,
    PAYMENT_ERROR,
    DELETE_PAYMENT,
    MAIL,
    LETTER,
    FAX,
    SIGN,
    SIGN_MAIL,
    CANCEL,
    ERROR_MAIL,
    CREATE_CREDIT_NOTE,
    REMINDER_CREATE,
    REMINDER_STATUS,
    REMINDER_MAIL,
    REMINDER_ERROR_MAIL,
    REMINDER_LETTER,
    REMINDER_FAX,
    REMINDER_SIGN,
    REMINDER_SIGN_MAIL,
    REMINDER_CANCEL,
    REMINDER_DELETE

}
