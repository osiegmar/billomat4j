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

import java.util.Currency;

import de.siegmar.billomat4j.sdk.domain.AbstractFilter;

public class ArticleFilter extends AbstractFilter<ArticleFilter> {

    public ArticleFilter byArticleNumber(final String articleNumber) {
        return add("article_number", articleNumber);
    }

    public ArticleFilter byTitle(final String title) {
        return add("title", title);
    }

    public ArticleFilter byDescription(final String description) {
        return add("description", description);
    }

    public ArticleFilter byCurrencyCode(final Currency currencyCode) {
        return add("currency_code", currencyCode);
    }

    public ArticleFilter byUnitId(final Integer unitId) {
        return add("unit_id", unitId);
    }

}
