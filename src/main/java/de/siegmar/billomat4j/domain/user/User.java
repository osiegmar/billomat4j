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

package de.siegmar.billomat4j.domain.user;

import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import de.siegmar.billomat4j.domain.AbstractMeta;
import de.siegmar.billomat4j.domain.types.SalutationType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonRootName("user")
public class User extends AbstractMeta {

    private SalutationType salutation;
    private String firstName;
    private String lastName;
    private String email;
    private Locale locale;
    private TimeZone timezone;
    private Integer roleId;

    private Map<String, String> rights;

    // TODO undocumented fields - clarify with support

    @JsonProperty("is_account_owner")
    private Boolean accountOwner;

}
