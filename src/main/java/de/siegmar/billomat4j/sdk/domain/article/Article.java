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
 * along with Billomat4J.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.siegmar.billomat4j.sdk.domain.article;

import java.math.BigDecimal;
import java.util.Currency;

import de.siegmar.billomat4j.sdk.domain.AbstractMeta;
import de.siegmar.billomat4j.sdk.json.Views;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonView;

@JsonRootName("article")
public class Article extends AbstractMeta {

    @JsonView(Views.NonSerialize.class)
    private String articleNumber;

    private Integer number;
    private String numberPre;
    private Integer numberLength;
    private String title;
    private String description;
    private BigDecimal salesPrice;
    private BigDecimal salesPrice2;
    private BigDecimal salesPrice3;
    private BigDecimal salesPrice4;
    private BigDecimal salesPrice5;
    private Currency currencyCode;
    private Integer unitId;
    private Integer taxId;

    public String getArticleNumber() {
        return articleNumber;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(final Integer number) {
        this.number = number;
    }

    public String getNumberPre() {
        return numberPre;
    }

    public void setNumberPre(final String numberPre) {
        this.numberPre = numberPre;
    }

    public Integer getNumberLength() {
        return numberLength;
    }

    public void setNumberLength(final Integer numberLength) {
        this.numberLength = numberLength;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public BigDecimal getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(final BigDecimal salesPrice) {
        this.salesPrice = salesPrice;
    }

    public BigDecimal getSalesPrice2() {
        return salesPrice2;
    }

    public void setSalesPrice2(final BigDecimal salesPrice2) {
        this.salesPrice2 = salesPrice2;
    }

    public BigDecimal getSalesPrice3() {
        return salesPrice3;
    }

    public void setSalesPrice3(final BigDecimal salesPrice3) {
        this.salesPrice3 = salesPrice3;
    }

    public BigDecimal getSalesPrice4() {
        return salesPrice4;
    }

    public void setSalesPrice4(final BigDecimal salesPrice4) {
        this.salesPrice4 = salesPrice4;
    }

    public BigDecimal getSalesPrice5() {
        return salesPrice5;
    }

    public void setSalesPrice5(final BigDecimal salesPrice5) {
        this.salesPrice5 = salesPrice5;
    }

    public Currency getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(final Currency currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(final Integer unitId) {
        this.unitId = unitId;
    }

    public Integer getTaxId() {
        return taxId;
    }

    public void setTaxId(final Integer taxId) {
        this.taxId = taxId;
    }

}
