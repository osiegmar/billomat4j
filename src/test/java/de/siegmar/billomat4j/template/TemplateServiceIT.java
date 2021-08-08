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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import de.siegmar.billomat4j.ResourceLoader;
import de.siegmar.billomat4j.ServiceHolder;
import de.siegmar.billomat4j.domain.AbstractIdentifiable;
import de.siegmar.billomat4j.domain.template.ImageFormat;
import de.siegmar.billomat4j.domain.template.Template;
import de.siegmar.billomat4j.domain.template.TemplateFilter;
import de.siegmar.billomat4j.domain.template.TemplateFormat;
import de.siegmar.billomat4j.domain.template.TemplateType;
import de.siegmar.billomat4j.service.TemplateService;

public class TemplateServiceIT {

    private final List<Template> createdTemplates = new ArrayList<>();
    private final TemplateService templateService = ServiceHolder.TEMPLATE;

    @AfterEach
    public void cleanup() {
        for (final Template template : createdTemplates) {
            templateService.deleteTemplate(template.getId());
        }
        createdTemplates.clear();
    }

    @Test
    public void findAll() {
        // Find
        final List<Integer> existingTemplates = templateService.findTemplates(null).stream()
            .map(AbstractIdentifiable::getId)
            .collect(Collectors.toList());

        // Create #1 (OFFER)
        final Template template1 = buildTemplate(TemplateType.OFFER);
        createTemplate(template1);
        assertNotNull(template1.getId());

        // Create #2 (CONFIRMATION)
        final Template template2 = buildTemplate(TemplateType.CONFIRMATION);
        createTemplate(template2);
        assertNotNull(template2.getId());

        // Find again
        final List<Template> templates = templateService.findTemplates(null).stream()
            .filter(t -> !existingTemplates.contains(t.getId()))
            .collect(Collectors.toList());
        assertEquals(2, templates.size());
        final Iterator<Template> templatesIterator = templates.iterator();
        final Template foundTemplate1 = templatesIterator.next();
        final Template foundTemplate2 = templatesIterator.next();
        assertEquals(template1.getId(), foundTemplate1.getId());
        assertEquals(TemplateType.OFFER, foundTemplate1.getType());
        assertEquals(template2.getId(), foundTemplate2.getId());
        assertEquals(TemplateType.CONFIRMATION, foundTemplate2.getType());
    }

    private Template buildTemplate(final TemplateType type) {
        final Template template = new Template();
        template.setFormat(TemplateFormat.rtf);
        template.setName("Test RTF Template");
        template.setType(type);
        template.setTemplateFile(ResourceLoader.loadFile("template.rtf"));

        return template;
    }

    private void createTemplate(final Template template) {
        templateService.createTemplate(template);
        createdTemplates.add(template);
    }

    @Test
    public void findFiltered() {
        final TemplateFilter templateFilter = new TemplateFilter().byType(TemplateType.OFFER, TemplateType.INVOICE);

        // Find
        List<Template> templates = templateService.findTemplates(templateFilter);
        assertTrue(templates.isEmpty());

        // Create #1 (OFFER)
        final Template template1 = buildTemplate(TemplateType.OFFER);
        createTemplate(template1);
        assertNotNull(template1.getId());

        // Create #2 (CONFIRMATION)
        final Template template2 = buildTemplate(TemplateType.CONFIRMATION);
        createTemplate(template2);
        assertNotNull(template2.getId());

        // Create #3 (CONFIRMATION)
        final Template template3 = buildTemplate(TemplateType.INVOICE);
        createTemplate(template3);
        assertNotNull(template3.getId());

        // Find again
        templates = templateService.findTemplates(templateFilter);
        assertEquals(2, templates.size());
        final Template foundTemplate1 = templates.get(0);
        assertEquals(template1.getId(), foundTemplate1.getId());
        assertEquals(TemplateType.OFFER, foundTemplate1.getType());

        final Template foundTemplate2 = templates.get(1);
        assertEquals(template3.getId(), foundTemplate2.getId());
        assertEquals(TemplateType.INVOICE, foundTemplate2.getType());
    }

    @Test
    public void create() {
        // Create
        final Template template = buildTemplate(TemplateType.OFFER);
        createTemplate(template);
        assertNotNull(template.getId());

        // Load by Id
        final Template templateLoaded = templateService.getTemplateById(template.getId());
        assertEquals(template.getCreated(), templateLoaded.getCreated());
    }

    @Disabled("BROKEN-UPDATE-TEMPLATE-TYPE")
    @Test
    public void update() {
        // Create
        final Template template = buildTemplate(TemplateType.OFFER);
        createTemplate(template);
        assertNotNull(template.getId());

        // Update
        template.setName("RTF Template");
        templateService.updateTemplate(template);

        // Load by Id
        final Template templateLoaded = templateService.getTemplateById(template.getId());
        assertEquals("RTF Template", templateLoaded.getName());
        assertEquals(template.getCreated(), templateLoaded.getCreated());
    }

    @Test
    public void preview() {
        // Create
        final Template template = buildTemplate(TemplateType.OFFER);
        createTemplate(template);
        assertNotNull(template.getId());

        // Preview
        final byte[] preview = templateService.getTemplatePreview(template.getId(), ImageFormat.jpg);
        assertTrue(preview.length > 0);
    }

}
