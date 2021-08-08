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

import static org.junit.jupiter.api.extension.ExtensionContext.Namespace.GLOBAL;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import de.siegmar.billomat4j.domain.template.Template;
import de.siegmar.billomat4j.domain.template.TemplateFormat;
import de.siegmar.billomat4j.domain.template.TemplateType;

public class TemplateSetup implements BeforeAllCallback, ExtensionContext.Store.CloseableResource {

    private final List<Template> templates = new ArrayList<>();

    @Override
    public void beforeAll(final ExtensionContext context) {
        context.getRoot().getStore(GLOBAL).put("TemplateSetup", this);
        setupTemplateService();
    }

    private void setupTemplateService() {
        for (final TemplateType value : TemplateType.values()) {
            final Template template = buildTemplate(value);
            ServiceHolder.TEMPLATE.createTemplate(template);
            templates.add(template);
        }
    }

    private Template buildTemplate(final TemplateType type) {
        final Template template = new Template();
        template.setFormat(TemplateFormat.rtf);
        template.setName("Test RTF Template");
        template.setType(type);
        template.setTemplateFile(ResourceLoader.loadFile("template.rtf"));

        return template;
    }

    @Override
    public void close() {
        for (final Template template : templates) {
            ServiceHolder.TEMPLATE.deleteTemplate(template.getId());
        }
    }

}
