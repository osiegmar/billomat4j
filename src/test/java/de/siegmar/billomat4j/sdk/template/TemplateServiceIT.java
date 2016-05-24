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
package de.siegmar.billomat4j.sdk.template;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.siegmar.billomat4j.sdk.domain.template.Template;
import de.siegmar.billomat4j.sdk.domain.template.TemplateFormat;
import de.siegmar.billomat4j.sdk.AbstractServiceIT;
import de.siegmar.billomat4j.sdk.domain.template.ImageFormat;
import de.siegmar.billomat4j.sdk.domain.template.TemplateFilter;
import de.siegmar.billomat4j.sdk.domain.template.TemplateType;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class TemplateServiceIT extends AbstractServiceIT {

    private final List<Template> createdTemplates = new ArrayList<>();

    @AfterMethod
    public void cleanup() {
        for (final Template template : createdTemplates) {
            templateService.deleteTemplate(template.getId());
        }
        createdTemplates.clear();
    }

    @Test
    public void findAll() {
        // Find
        List<Template> templates = templateService.findTemplates(null);
        assertTrue(templates.isEmpty());

        // Create #1 (OFFER)
        final Template template1 = buildTemplate();
        createTemplate(template1);
        assertNotNull(template1.getId());

        // Create #2 (CONFIRMATION)
        final Template template2 = buildTemplate();
        template2.setType(TemplateType.CONFIRMATION);
        createTemplate(template2);
        assertNotNull(template2.getId());

        // Find again
        templates = templateService.findTemplates(null);
        assertTrue(templates.size() == 2);
        final Iterator<Template> templatesIterator = templates.iterator();
        final Template foundTemplate1 = templatesIterator.next();
        final Template foundTemplate2 = templatesIterator.next();
        assertEquals(foundTemplate1.getId(), template1.getId());
        assertEquals(foundTemplate1.getType(), TemplateType.OFFER);
        assertEquals(foundTemplate2.getId(), template2.getId());
        assertEquals(foundTemplate2.getType(), TemplateType.CONFIRMATION);
    }

    private Template buildTemplate() {
        final Template template = new Template();
        template.setFormat(TemplateFormat.rtf);
        template.setName("Test RTF Template");
        template.setType(TemplateType.OFFER);
        template.setTemplateFile(loadFile("template.rtf"));

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
        final Template template1 = buildTemplate();
        createTemplate(template1);
        assertNotNull(template1.getId());

        // Create #2 (CONFIRMATION)
        final Template template2 = buildTemplate();
        template2.setType(TemplateType.CONFIRMATION);
        createTemplate(template2);
        assertNotNull(template2.getId());

        // Create #3 (CONFIRMATION)
        final Template template3 = buildTemplate();
        template3.setType(TemplateType.INVOICE);
        createTemplate(template3);
        assertNotNull(template3.getId());

        // Find again
        templates = templateService.findTemplates(templateFilter);
        assertTrue(templates.size() == 2);
        final Template foundTemplate1 = templates.get(0);
        assertEquals(foundTemplate1.getId(), template1.getId());
        assertEquals(foundTemplate1.getType(), TemplateType.OFFER);

        final Template foundTemplate2 = templates.get(1);
        assertEquals(foundTemplate2.getId(), template3.getId());
        assertEquals(foundTemplate2.getType(), TemplateType.INVOICE);
    }

    @Test
    public void create() {
        // Create
        final Template template = buildTemplate();
        createTemplate(template);
        assertNotNull(template.getId());

        // Load by Id
        final Template templateLoaded = templateService.getTemplateById(template.getId());
        assertEquals(templateLoaded.getCreated(), template.getCreated());
    }

    @Test
    public void update() {
        // Create
        final Template template = buildTemplate();
        createTemplate(template);
        assertNotNull(template.getId());

        // Update
        template.setName("RTF Template");
        templateService.updateTemplate(template);

        // Load by Id
        final Template templateLoaded = templateService.getTemplateById(template.getId());
        assertEquals(templateLoaded.getName(), "RTF Template");
        assertEquals(templateLoaded.getCreated(), template.getCreated());
    }

    @Test
    public void preview() {
        // Create
        final Template template = buildTemplate();
        createTemplate(template);
        assertNotNull(template.getId());

        // Preview
        final byte[] preview = templateService.getTemplatePreview(template.getId(), ImageFormat.jpg);
        final byte[] localPreview = loadFile("preview.jpg");
        assertEquals(preview, localPreview);
    }

}
