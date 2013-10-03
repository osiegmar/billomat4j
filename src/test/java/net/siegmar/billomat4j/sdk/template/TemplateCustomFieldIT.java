package net.siegmar.billomat4j.sdk.template;

import net.siegmar.billomat4j.sdk.AbstractCustomFieldServiceIT;
import net.siegmar.billomat4j.sdk.domain.template.Template;
import net.siegmar.billomat4j.sdk.domain.template.TemplateFormat;
import net.siegmar.billomat4j.sdk.domain.template.TemplateType;


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
        template.setTemplateFile("dummy".getBytes());
        templateService.createTemplate(template);
        return template.getId();
    }

    @Override
    protected void deleteOwner(final int ownerId) {
        templateService.deleteTemplate(ownerId);
    }

}
