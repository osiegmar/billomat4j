/*
 * Copyright 2021 Oliver Siegmar
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

package de.siegmar.billomat4j;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import de.siegmar.billomat4j.service.BillomatConfiguration;

public class RequestHelperTest {

    private final RequestHelper requestHelper;

    public RequestHelperTest() {
        final BillomatConfiguration cfg = new BillomatConfiguration();
        cfg.setBillomatId("host");
        requestHelper = new RequestHelper(cfg);
    }

    @Test
    public void pathBuild() {
        final Map<String, String> filter = new LinkedHashMap<>();
        filter.put("a", "b");
        filter.put("name", "müller");
        final URI actualPath = requestHelper.buildUrl(
            "resource",
            "id",
            "method",
            filter);
        assertEquals("https://host.billomat.net/api/resource/id/method?a=b&name=m%C3%BCller", actualPath.toString());
    }

}
