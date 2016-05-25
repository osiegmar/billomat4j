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
package de.siegmar.billomat4j.service.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.Validate;

import de.siegmar.billomat4j.domain.Filter;

class CombinedFilter implements Filter {

    private final Map<String, String> map;

    public CombinedFilter(final Filter... filters) {
        Validate.notEmpty(filters);

        map = new LinkedHashMap<>(filters.length);

        for (final Filter filter : filters) {
            if (filter != null) {
                final Map<String, String> filterMap = filter.toMap();
                if (filterMap != null && !filterMap.isEmpty()) {
                    map.putAll(filterMap);
                }
            }
        }
    }

    @Override
    public Map<String, String> toMap() {
        return map;
    }

    @Override
    public boolean isConfigured() {
        return !map.isEmpty();
    }

}
