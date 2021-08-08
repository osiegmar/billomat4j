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

package de.siegmar.billomat4j.offer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import de.siegmar.billomat4j.ServiceHolder;
import de.siegmar.billomat4j.TemplateSetup;
import de.siegmar.billomat4j.domain.Email;
import de.siegmar.billomat4j.domain.EmailRecipients;
import de.siegmar.billomat4j.domain.client.Client;
import de.siegmar.billomat4j.domain.offer.Offer;
import de.siegmar.billomat4j.domain.offer.OfferFilter;
import de.siegmar.billomat4j.domain.offer.OfferItem;
import de.siegmar.billomat4j.domain.offer.OfferStatus;
import de.siegmar.billomat4j.service.impl.ClientService;
import de.siegmar.billomat4j.service.impl.OfferService;

@ExtendWith(TemplateSetup.class)
public class OfferServiceIT {

    private final List<Offer> createdOffers = new ArrayList<>();
    private final OfferService offerService = ServiceHolder.OFFER;
    private final ClientService clientService = ServiceHolder.CLIENT;

    // Offer

    @AfterEach
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
        assertEquals(1, offers.size());
        assertEquals(offer1.getId(), offers.get(0).getId());
    }

    @Test
    public void create() {
        final Offer offer = createOffer(1);
        assertNotNull(offer.getId());
    }

    @Test
    public void getById() {
        final Offer offer = createOffer(1);
        assertEquals(offer.getId(), offerService.getOfferById(offer.getId()).getId());
    }

    @Test
    public void update() {
        final Offer offer = createOffer(1);
        offer.setLabel("Test Label");
        offerService.updateOffer(offer);
        assertEquals("Test Label", offer.getLabel());
        assertEquals("Test Label", offerService.getOfferById(offer.getId()).getLabel());
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
        assertEquals(OfferStatus.OPEN, offerService.getOfferById(offer.getId()).getStatus());
    }

    @Test
    @Disabled("E-Mail")
    public void sendOfferViaEmail() {
        final Offer offer = createOffer(1);
        offerService.completeOffer(offer.getId(), null);
        final Email email = new Email();
        email.setSubject("Test Offer Mail");
        email.setBody("Here's your offer");
        email.setFrom(ServiceHolder.getEmail());
        final EmailRecipients emailRecipients = new EmailRecipients();
        emailRecipients.addTo(ServiceHolder.getEmail());
        email.setRecipients(emailRecipients);
        offerService.sendOfferViaEmail(offer.getId(), email);
    }

    @Test
    public void cancel() {
        final Offer offer = createOffer(1);
        offerService.completeOffer(offer.getId(), null);
        offerService.cancelOffer(offer.getId());
        assertEquals(OfferStatus.CANCELED, offerService.getOfferById(offer.getId()).getStatus());
    }

    @Test
    public void win() {
        final Offer offer = createOffer(1);
        offerService.completeOffer(offer.getId(), null);
        offerService.winOffer(offer.getId());
        assertEquals(OfferStatus.WON, offerService.getOfferById(offer.getId()).getStatus());
    }

    @Test
    public void lose() {
        final Offer offer = createOffer(1);
        offerService.completeOffer(offer.getId(), null);
        offerService.loseOffer(offer.getId());
        assertEquals(OfferStatus.LOST, offerService.getOfferById(offer.getId()).getStatus());
    }

    @Test
    public void clear() {
        final Offer offer = createOffer(1);
        offerService.completeOffer(offer.getId(), null);
        offerService.winOffer(offer.getId());
        offerService.clearOffer(offer.getId());
        assertEquals(OfferStatus.CLEARED, offerService.getOfferById(offer.getId()).getStatus());
    }

    @Test
    public void unclear() {
        final Offer offer = createOffer(1);
        offerService.completeOffer(offer.getId(), null);
        offerService.winOffer(offer.getId());
        offerService.clearOffer(offer.getId());
        offerService.unclearOffer(offer.getId());
        assertEquals(OfferStatus.WON, offerService.getOfferById(offer.getId()).getStatus());
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
        assertEquals(2, items.size());
    }

    @Test
    public void getOfferItemById() {
        final Offer offer = createOffer(1);
        assertNotNull(offer.getId());

        final List<OfferItem> items = offerService.getItems(offer.getId());
        final OfferItem item = offerService.getItemById(items.get(0).getId());
        assertEquals(offer.getId(), item.getOfferId());
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
        assertEquals(3, items.size());
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
        assertEquals(new BigDecimal("2"), item.getQuantity());
        assertEquals(new BigDecimal("2"), offerService.getItemById(item.getId()).getQuantity());
    }

    @Test
    public void deleteOfferItem() {
        final Offer offer = createOffer(1);
        assertNotNull(offer.getId());

        final List<OfferItem> items = offerService.getItems(offer.getId());
        offerService.deleteItem(items.get(0).getId());
        assertEquals(1, offerService.getItems(offer.getId()).size());
    }

}
