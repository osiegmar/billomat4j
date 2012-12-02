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
package net.siegmar.billomat4j.sdk.offer;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import net.siegmar.billomat4j.sdk.AbstractServiceTest;
import net.siegmar.billomat4j.sdk.domain.Email;
import net.siegmar.billomat4j.sdk.domain.EmailRecipients;
import net.siegmar.billomat4j.sdk.domain.client.Client;
import net.siegmar.billomat4j.sdk.domain.offer.Offer;
import net.siegmar.billomat4j.sdk.domain.offer.OfferFilter;
import net.siegmar.billomat4j.sdk.domain.offer.OfferItem;
import net.siegmar.billomat4j.sdk.domain.offer.OfferStatus;
import net.siegmar.billomat4j.sdk.domain.template.Template;
import net.siegmar.billomat4j.sdk.domain.template.TemplateFormat;
import net.siegmar.billomat4j.sdk.domain.template.TemplateType;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class OfferServiceTest extends AbstractServiceTest {

    private final List<Offer> createdOffers = new ArrayList<>();

    // Offer

    @AfterMethod
    public void cleanup() {
        for (final Offer offer : createdOffers) {
            final int clientId = offer.getClientId();
            offerService.deleteOffer(offer.getId());
            clientService.deleteClient(clientId);
        }
        createdOffers.clear();
    }

    @Test
    public void findAll() {
        assertTrue(offerService.findOffers(null).isEmpty());
        createOffer(1);
        assertFalse(offerService.findOffers(null).isEmpty());
    }

    @Test
    public void findFiltered() {
        final OfferFilter offerFilter = new OfferFilter().byOfferNumber("1");
        List<Offer> offers = offerService.findOffers(offerFilter);
        assertTrue(offerService.findOffers(null).isEmpty());

        final Offer offer1 = createOffer(1);
        createOffer(2);

        offers = offerService.findOffers(offerFilter);
        assertEquals(offers.size(), 1);
        assertEquals(offers.get(0).getId(), offer1.getId());
    }

    @Test
    public void create() {
        final Offer offer = createOffer(1);
        assertNotNull(offer.getId());
    }

    @Test
    public void getById() {
        final Offer offer = createOffer(1);
        assertEquals(offerService.getOfferById(offer.getId()).getId(), offer.getId());
    }

    @Test
    public void update() {
        final Offer offer = createOffer(1);
        offer.setLabel("Test Label");
        offerService.updateOffer(offer);
        assertEquals(offer.getLabel(), "Test Label");
        assertEquals(offerService.getOfferById(offer.getId()).getLabel(), "Test Label");
    }

    @Test
    public void delete() {
        final Offer offer = createOffer(1);

        final int clientId = offer.getClientId();
        offerService.deleteOffer(offer.getId());
        clientService.deleteClient(clientId);

        assertNull(offerService.getOfferById(offer.getId()));

        createdOffers.remove(offer);
    }

    @Test
    public void complete() {
        final Offer offer = createOffer(1);
        offerService.completeOffer(offer.getId(), null);
        assertEquals(offerService.getOfferById(offer.getId()).getStatus(), OfferStatus.OPEN);
    }

    @Test
    public void completeWithTemplate() {
        final Template template = buildTemplate();
        templateService.createTemplate(template);

        try {
            final Offer offer = createOffer(1);
            offerService.completeOffer(offer.getId(), null);
            assertEquals(offerService.getOfferById(offer.getId()).getStatus(), OfferStatus.OPEN);
        } finally {
            templateService.deleteTemplate(template.getId());
        }
    }

    private Template buildTemplate() {
        final Template template = new Template();
        template.setFormat(TemplateFormat.rtf);
        template.setName("Test RTF Template");
        template.setType(TemplateType.OFFER);
        template.setTemplateFile(loadFile("template.rtf"));

        return template;
    }

    @Test(enabled = false)
    public void sendOfferViaEmail() {
        final Offer offer = createOffer(1);
        offerService.completeOffer(offer.getId(), null);
        final Email email = new Email();
        email.setSubject("Test Offer Mail");
        email.setBody("Here's your offer");
        email.setFrom(getEmail());
        final EmailRecipients emailRecipients = new EmailRecipients();
        emailRecipients.addTo(getEmail());
        email.setRecipients(emailRecipients);
        offerService.sendOfferViaEmail(offer.getId(), email);
    }

    @Test
    public void cancel() {
        final Offer offer = createOffer(1);
        offerService.completeOffer(offer.getId(), null);
        offerService.cancelOffer(offer.getId());
        assertEquals(offerService.getOfferById(offer.getId()).getStatus(), OfferStatus.CANCELED);
    }

    @Test
    public void win() {
        final Offer offer = createOffer(1);
        offerService.completeOffer(offer.getId(), null);
        offerService.winOffer(offer.getId());
        assertEquals(offerService.getOfferById(offer.getId()).getStatus(), OfferStatus.WON);
    }

    @Test
    public void lose() {
        final Offer offer = createOffer(1);
        offerService.completeOffer(offer.getId(), null);
        offerService.loseOffer(offer.getId());
        assertEquals(offerService.getOfferById(offer.getId()).getStatus(), OfferStatus.LOST);
    }

    @Test
    public void clear() {
        final Offer offer = createOffer(1);
        offerService.completeOffer(offer.getId(), null);
        offerService.winOffer(offer.getId());
        offerService.clearOffer(offer.getId());
        assertEquals(offerService.getOfferById(offer.getId()).getStatus(), OfferStatus.CLEARED);
    }

    @Test
    public void unclear() {
        final Offer offer = createOffer(1);
        offerService.completeOffer(offer.getId(), null);
        offerService.winOffer(offer.getId());
        offerService.clearOffer(offer.getId());
        offerService.unclearOffer(offer.getId());
        assertEquals(offerService.getOfferById(offer.getId()).getStatus(), OfferStatus.WON);
    }

    private Offer createOffer(final int number) {
        final Client client = new Client();
        client.setName("OfferServiceTest Client");
        clientService.createClient(client);

        final Offer offer = new Offer();
        offer.setClientId(client.getId());
        offer.setNumber(number);

        final OfferItem offerItem1 = new OfferItem();
        offerItem1.setTitle("Offer Item #1");
        offerItem1.setUnitPrice(new BigDecimal("11.11"));
        offerItem1.setQuantity(BigDecimal.ONE);
        offer.addItem(offerItem1);

        final OfferItem offerItem2 = new OfferItem();
        offerItem2.setTitle("Offer Item #2");
        offerItem2.setUnitPrice(new BigDecimal("22.22"));
        offerItem2.setQuantity(BigDecimal.ONE);
        offer.addItem(offerItem2);

        offerService.createOffer(offer);

        createdOffers.add(offer);

        return offer;
    }

    // OfferItem

    @Test
    public void getOfferItemsByOfferId() {
        final Offer offer = createOffer(1);
        assertNotNull(offer.getId());

        final List<OfferItem> items = offerService.getItems(offer.getId());
        assertEquals(items.size(), 2);
    }

    @Test
    public void getOfferItemById() {
        final Offer offer = createOffer(1);
        assertNotNull(offer.getId());

        final List<OfferItem> items = offerService.getItems(offer.getId());
        final OfferItem item = offerService.getItemById(items.get(0).getId());
        assertEquals(item.getOfferId(), offer.getId());
    }

    @Test
    public void createOfferItem() {
        final Offer offer = createOffer(1);
        assertNotNull(offer.getId());

        final OfferItem item = new OfferItem();
        item.setOfferId(offer.getId());
        item.setTitle("Offer Item #3");
        item.setUnitPrice(new BigDecimal("33.33"));
        item.setQuantity(BigDecimal.ONE);
        offerService.createItem(item);

        final List<OfferItem> items = offerService.getItems(offer.getId());
        assertEquals(items.size(), 3);
    }

    @Test
    public void updateOfferItem() {
        final Offer offer = createOffer(1);
        assertNotNull(offer.getId());

        final OfferItem item = new OfferItem();
        item.setOfferId(offer.getId());
        item.setTitle("Offer Item #3");
        item.setUnitPrice(new BigDecimal("33.33"));
        item.setQuantity(BigDecimal.ONE);
        offerService.createItem(item);

        item.setQuantity(new BigDecimal("2"));
        offerService.updateItem(item);
        assertEquals(item.getQuantity(), new BigDecimal("2"));
        assertEquals(offerService.getItemById(item.getId()).getQuantity(), new BigDecimal("2"));
    }

    @Test
    public void deleteOfferItem() {
        final Offer offer = createOffer(1);
        assertNotNull(offer.getId());

        final List<OfferItem> items = offerService.getItems(offer.getId());
        offerService.deleteItem(items.get(0).getId());
        assertEquals(offerService.getItems(offer.getId()).size(), 1);
    }

}
