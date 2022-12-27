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

package de.siegmar.billomat4j.domain.settings;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import de.siegmar.billomat4j.domain.AbstractIdentifiable;
import de.siegmar.billomat4j.domain.types.PropertyType;
import de.siegmar.billomat4j.json.Views;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public abstract class AbstractProperty extends AbstractIdentifiable {

    @Setter(AccessLevel.NONE)
    @JsonView(Views.NonSerialize.class)
    private Integer accountId;

    private String name;
    private String defaultValue;

    @JsonProperty("is_nvl")
    private Boolean nvl;

    private PropertyType type;

    @JsonProperty("customfield")
    private String customField;

}
