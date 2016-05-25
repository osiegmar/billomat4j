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
package de.siegmar.billomat4j.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.Validate;

import de.siegmar.billomat4j.domain.template.ImageFormat;
import de.siegmar.billomat4j.domain.template.Template;
import de.siegmar.billomat4j.domain.template.TemplateFilter;
import de.siegmar.billomat4j.domain.template.Templates;
import de.siegmar.billomat4j.service.TemplateService;

public class TemplateServiceImpl extends AbstractService implements TemplateService {

    private static final String RESOURCE = "templates";

    public TemplateServiceImpl(final BillomatConfiguration billomatConfiguration) {
        super(billomatConfiguration);
    }

    // Template

    @Override
    public String getCustomFieldValue(final int templateId) {
        return getCustomField(RESOURCE, templateId);
    }

    @Override
    public void setCustomFieldValue(final int templateId, final String value) {
        updateCustomField(RESOURCE, templateId, "template", value);
    }

    @Override
    public List<Template> findTemplates(final TemplateFilter templateFilter) {
        return getAllPagesFromResource(RESOURCE, Templates.class, templateFilter);
    }

    @Override
    public Template getTemplateById(final int id) {
        return getById(RESOURCE, Template.class, id);
    }

    @Override
    public void createTemplate(final Template template) {
        create(RESOURCE, Validate.notNull(template));
    }

    @Override
    public void updateTemplate(final Template template) {
        update(RESOURCE, Validate.notNull(template));
    }

    @Override
    public void deleteTemplate(final int id) {
        delete(RESOURCE, id);
    }

    @Override
    public byte[] getTemplatePreview(final int id, final ImageFormat imageFormat) {
        Validate.notNull(imageFormat);

        try {
            return requestHelper.get(RESOURCE, Integer.toString(id), "thumb",
                    new GenericFilter("format", imageFormat).toMap());
        } catch (final IOException e) {
            throw new ServiceException(e);
        }
    }

}
