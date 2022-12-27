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

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractFilter<T extends Filter> implements Filter {

    private final Map<String, String> filterMap = new LinkedHashMap<>();

    @SuppressWarnings("unchecked")
    protected T add(final String key, final Object value) {
        if (value instanceof Object[]) {
            filterMap.put(key, join((Object[]) value));
        } else {
            filterMap.put(key, value.toString());
        }

        return (T) this;
    }

    private static String join(final Object... value) {
        return Arrays.stream(value).map(Object::toString).collect(Collectors.joining(","));
    }

    @Override
    public Map<String, String> toMap() {
        return filterMap;
    }

    @Override
    public boolean isConfigured() {
        return !filterMap.isEmpty();
    }

}
