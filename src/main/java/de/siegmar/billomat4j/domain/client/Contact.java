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

package de.siegmar.billomat4j.domain.client;

import com.fasterxml.jackson.annotation.JsonRootName;

import de.siegmar.billomat4j.domain.AbstractMeta;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonRootName("contact")
public class Contact extends AbstractMeta {

    private Integer clientId;
    private String label;
    private String name;
    private String salutation;
    private String firstName;
    private String lastName;
    private String street;
    private String zip;
    private String city;
    private String state;
    private String countryCode;
    private String phone;
    private String fax;
    private String mobile;
    private String email;
    private String www;

    private String address; // TODO undocumented feature - clarify with support
    private String locale; // TODO undocumented feature - clarify with support

}
