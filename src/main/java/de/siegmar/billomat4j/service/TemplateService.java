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

package de.siegmar.billomat4j.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.Validate;

import de.siegmar.billomat4j.domain.template.ImageFormat;
import de.siegmar.billomat4j.domain.template.Template;
import de.siegmar.billomat4j.domain.template.TemplateFilter;
import de.siegmar.billomat4j.domain.template.Templates;

public class TemplateService extends AbstractService implements GenericCustomFieldService {

    private static final String RESOURCE = "templates";

    public TemplateService(final BillomatConfiguration billomatConfiguration) {
        super(billomatConfiguration);
    }

    // Template

    @Override
    public Optional<String> getCustomFieldValue(final int templateId) {
        return getCustomField(RESOURCE, templateId);
    }

    @Override
    public void setCustomFieldValue(final int templateId, final String value) {
        updateCustomField(RESOURCE, templateId, "template", value);
    }

    /**
     * @param templateFilter template filter, may be {@code null} to find unfiltered
     * @return templates found by filter criteria or an empty list if no templates were found - never {@code null}
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public List<Template> findTemplates(final TemplateFilter templateFilter) {
        return getAllPagesFromResource(RESOURCE, Templates.class, templateFilter);
    }

    /**
     * Gets a template by its id.
     *
     * @param templateId the template's id
     * @return the template
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public Optional<Template> getTemplateById(final int templateId) {
        return getById(RESOURCE, Template.class, templateId);
    }

    /**
     * @param template the template to create, must not be {@code null}
     * @throws NullPointerException if template is null
     * @throws ServiceException     if an error occurred while accessing the web service
     */
    public void createTemplate(final Template template) {
        create(RESOURCE, Validate.notNull(template));
    }

    /**
     * @param template the template to update, must not be {@code null}
     * @throws NullPointerException if template is null
     * @throws ServiceException     if an error occurred while accessing the web service
     */
    public void updateTemplate(final Template template) {
        update(RESOURCE, Validate.notNull(template));
    }

    /**
     * @param templateId the id of the template to be deleted
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public void deleteTemplate(final int templateId) {
        delete(RESOURCE, templateId);
    }

    /**
     * @param templateId  the id of the template to obtain a preview for
     * @param imageFormat the image file format this method should render
     * @return the template preview as binary data
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public Optional<byte[]> getTemplatePreview(final int templateId, final ImageFormat imageFormat) {
        Validate.notNull(imageFormat);

        try {
            return Optional.ofNullable(requestHelper.get(RESOURCE, Integer.toString(templateId), "thumb",
                new GenericFilter("format", imageFormat).toMap()));
        } catch (final IOException e) {
            throw new ServiceException(e);
        }
    }

}
