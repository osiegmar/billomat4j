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
package de.siegmar.billomat4j.sdk.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.siegmar.billomat4j.sdk.domain.Filter;
import de.siegmar.billomat4j.sdk.domain.Pageable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectReader;

class Pager<T extends Pageable<E>, E> {

    private static final Logger LOG = LoggerFactory.getLogger(Pager.class);

    private static final int DEFAULT_API_PAGE_SIZE = 100;
    private static final int DEFAULT_SDK_PAGE_SIZE = 1000;

    private final Class<T> clazz;
    private final RequestHelper requestHelper;
    private final ObjectReader objectReader;
    private int pageSize = DEFAULT_SDK_PAGE_SIZE;

    public Pager(final Class<T> clazz, final RequestHelper requestHelper, final ObjectReader objectReader) {
        this.clazz = clazz;
        this.requestHelper = requestHelper;
        this.objectReader = objectReader;
    }

    public void setPageSize(final int pageSize) {
        this.pageSize = pageSize;
    }

    public List<E> getAll(final String resource, final Filter filter) {
        final List<E> records = new ArrayList<>();

        final Map<String, String> params = new HashMap<>();
        if (pageSize != DEFAULT_API_PAGE_SIZE) {
            params.put("per_page", Integer.toString(pageSize));
        }
        if (filter != null) {
            params.putAll(filter.toMap());
        }

        int page = 1;
        int pages = 1;
        do {
            if (page > 1) {
                LOG.debug("Request page {} of {} (fetched {} records so far}", page, pages, records.size());
                params.put("page", Integer.toString(page));
            } else {
                LOG.debug("Request first page with up to {} records", pageSize);
            }
            final T recordWrapper = fetchRecords(resource, params);

            if (recordWrapper == null || recordWrapper.getTotal() == 0) {
                break;
            }

            records.addAll(recordWrapper.getEntries());

            if (page == 1 && recordWrapper.getTotal() > recordWrapper.getPerPage()) {
                pages = (int) Math.ceil((float) recordWrapper.getTotal() / (float) recordWrapper.getPerPage());
                LOG.debug("Result consists of {} pages ({} records in total, up to {} records per page) - " +
                        "continue fetching...", pages, recordWrapper.getTotal(), recordWrapper.getPerPage());
            }
        } while (pages > page++);

        LOG.debug("Fetched {} record(s) on {} page(s)", records.size(), pages);

        return records;
    }

    private T fetchRecords(final String resource, final Map<String, String> params) {
        try {
            final byte[] data = requestHelper.get(resource, null, null, params);
            if (data == null) {
                return null;
            }
            return objectReader.withType(clazz).readValue(data);
        } catch (final IOException e) {
            throw new ServiceException(e);
        }
    }

}
