/*
 * Copyright 2012 Oliver Siegmar
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
 * along with Billomat4J.  If not, see <https://www.gnu.org/licenses/>.
 */

package de.siegmar.billomat4j.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import de.siegmar.billomat4j.domain.types.PaymentType;
import de.siegmar.billomat4j.json.Views;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public abstract class AbstractPayment extends AbstractMeta {

    @Setter(AccessLevel.NONE)
    @JsonView(Views.NonSerialize.class)
    private Integer userId;

    @JsonInclude(Include.NON_NULL)
    private LocalDate date;

    private BigDecimal amount;
    private String comment;

    @JsonProperty("type")
    private PaymentType paymentType;

    private Boolean markInvoiceAsPaid;

    // TODO undocumented fields - clarify with support
    private String transactionPurpose;
    private Currency currencyCode;
    private Boolean quote;
    private String paymentGroup;
    private String finapiTransactionId;
    private BigDecimal finapiTransactionAmount;

}
