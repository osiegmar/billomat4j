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
package net.siegmar.billomat4j.sdk.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public abstract class AbstractGroupFilter<T extends AbstractGroupFilter<?>> implements Filter {

    private final Collection<String> groupCriteria = new LinkedHashSet<>();

    @SuppressWarnings("unchecked")
    public T byClient() {
        groupCriteria.add("client");
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T byStatus() {
        groupCriteria.add("status");
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T byDay() {
        groupCriteria.add("day");
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T byWeek() {
        groupCriteria.add("week");
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T byMonth() {
        groupCriteria.add("month");
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T byYear() {
        groupCriteria.add("year");
        return (T) this;
    }

    @Override
    public Map<String, String> toMap() {
        if (groupCriteria.isEmpty()) {
            return null;
        }

        return Collections.singletonMap("group_by", StringUtils.join(groupCriteria, ','));
    }

    @Override
    public boolean isConfigured() {
        return !groupCriteria.isEmpty();
    }

}
