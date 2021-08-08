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

package de.siegmar.billomat4j.template;

import java.nio.charset.StandardCharsets;

import de.siegmar.billomat4j.AbstractCustomFieldServiceIT;
import de.siegmar.billomat4j.domain.template.Template;
import de.siegmar.billomat4j.domain.template.TemplateFormat;
import de.siegmar.billomat4j.domain.template.TemplateType;

public class TemplateCustomFieldIT extends AbstractCustomFieldServiceIT {

    public TemplateCustomFieldIT() {
        setService(templateService);
    }

    @Override
    protected int buildOwner() {
        final Template template = new Template();
        template.setName("TemplateCustomFieldTest");
        template.setFormat(TemplateFormat.doc);
        template.setType(TemplateType.CONFIRMATION);
        template.setTemplateFile("dummy".getBytes(StandardCharsets.US_ASCII));
        templateService.createTemplate(template);
        return template.getId();
    }

    @Override
    protected void deleteOwner(final int ownerId) {
        templateService.deleteTemplate(ownerId);
    }

}
