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
package net.siegmar.billomat4j.sdk.template;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import net.siegmar.billomat4j.sdk.AbstractServiceTest;
import net.siegmar.billomat4j.sdk.domain.template.ImageFormat;
import net.siegmar.billomat4j.sdk.domain.template.Template;
import net.siegmar.billomat4j.sdk.domain.template.TemplateFilter;
import net.siegmar.billomat4j.sdk.domain.template.TemplateFormat;
import net.siegmar.billomat4j.sdk.domain.template.TemplateType;

import org.junit.After;
import org.junit.Test;

public class TemplateServiceTest extends AbstractServiceTest {

    @After
    public void cleanup() {
        // Clean up all Templates
        List<Template> templates = templateService.findTemplates(null);
        if (!templates.isEmpty()) {
            for (final Template template : templates) {
                templateService.deleteTemplate(template.getId());
            }

            templates = templateService.findTemplates(null);
            assertTrue(templates.isEmpty());
        }
    }

    @Test
    public void findAll() {
        // Find
        List<Template> templates = templateService.findTemplates(null);
        assertTrue(templates.isEmpty());

        // Create #1 (OFFER)
        final Template template1 = buildTemplate();
        templateService.createTemplate(template1);
        assertNotNull(template1.getId());

        // Create #2 (CONFIRMATION)
        final Template template2 = buildTemplate();
        template2.setType(TemplateType.CONFIRMATION);
        templateService.createTemplate(template2);
        assertNotNull(template2.getId());

        // Find again
        templates = templateService.findTemplates(null);
        assertTrue(templates.size() == 2);
        final Iterator<Template> templatesIterator = templates.iterator();
        final Template foundTemplate1 = templatesIterator.next();
        final Template foundTemplate2 = templatesIterator.next();
        assertEquals(template1.getId(), foundTemplate1.getId());
        assertEquals(TemplateType.OFFER, foundTemplate1.getType());
        assertEquals(template2.getId(), foundTemplate2.getId());
        assertEquals(TemplateType.CONFIRMATION, foundTemplate2.getType());
    }

    private Template buildTemplate() {
        final Template template = new Template();
        template.setFormat(TemplateFormat.rtf);
        template.setName("Test RTF Template");
        template.setType(TemplateType.OFFER);
        template.setTemplateFile(loadFile("template.rtf"));

        return template;
    }

    @Test
    public void findFiltered() {
        final TemplateFilter templateFilter = new TemplateFilter().byType(TemplateType.OFFER, TemplateType.INVOICE);

        // Find
        List<Template> templates = templateService.findTemplates(templateFilter);
        assertTrue(templates.isEmpty());

        // Create #1 (OFFER)
        final Template template1 = buildTemplate();
        templateService.createTemplate(template1);
        assertNotNull(template1.getId());

        // Create #2 (CONFIRMATION)
        final Template template2 = buildTemplate();
        template2.setType(TemplateType.CONFIRMATION);
        templateService.createTemplate(template2);
        assertNotNull(template2.getId());

        // Create #3 (CONFIRMATION)
        final Template template3 = buildTemplate();
        template3.setType(TemplateType.INVOICE);
        templateService.createTemplate(template3);
        assertNotNull(template3.getId());

        // Find again
        templates = templateService.findTemplates(templateFilter);
        assertTrue(templates.size() == 2);
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
        final Template template = buildTemplate();
        templateService.createTemplate(template);
        assertNotNull(template.getId());

        // Load by Id
        final Template templateLoaded = templateService.getTemplateById(template.getId());
        assertEquals(template.getCreated(), templateLoaded.getCreated());
    }

    @Test
    public void update() {
        // Create
        final Template template = buildTemplate();
        templateService.createTemplate(template);
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
        final Template template = buildTemplate();
        templateService.createTemplate(template);
        assertNotNull(template.getId());

        // Preview
        final byte[] preview = templateService.getTemplatePreview(template.getId(), ImageFormat.jpg);
        final byte[] localPreview = loadFile("preview.jpg");
        assertTrue(Arrays.equals(localPreview, preview));
    }

}
