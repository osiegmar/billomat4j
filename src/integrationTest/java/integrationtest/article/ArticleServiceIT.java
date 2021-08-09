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

package integrationtest.article;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import de.siegmar.billomat4j.domain.article.Article;
import de.siegmar.billomat4j.domain.article.ArticleFilter;
import de.siegmar.billomat4j.service.ArticleService;
import integrationtest.ServiceHolder;

public class ArticleServiceIT {

    private final List<Article> createdArticles = new ArrayList<>();
    private final ArticleService articleService = ServiceHolder.ARTICLE;

    // Article

    @AfterEach
    public void cleanup() {
        for (final Article article : createdArticles) {
            articleService.deleteArticle(article.getId());
        }
        createdArticles.clear();
    }

    @Test
    public void findAll() {
        assertTrue(articleService.findArticles(null).isEmpty());
        createArticle("Test Article (findAll)");
        assertFalse(articleService.findArticles(null).isEmpty());
    }

    private Article createArticle(final String title) {
        final Article article = new Article();
        article.setNumber(1);
        article.setNumberPre("AR");
        article.setNumberLength(5);
        article.setTitle(title);
        article.setDescription("Test Description");
        article.setSalesPrice(new BigDecimal("25"));
        article.setSalesPrice2(new BigDecimal("23"));
        article.setSalesPrice3(new BigDecimal("21"));
        article.setSalesPrice4(new BigDecimal("18.99"));
        article.setSalesPrice5(new BigDecimal("17.11"));
        article.setCurrencyCode(Currency.getInstance("EUR"));
        articleService.createArticle(article);

        createdArticles.add(article);

        return article;
    }

    @Test
    public void update() {
        final Article article = createArticle("Test Article (update)");
        article.setTitle("Test Article (updated)");
        articleService.updateArticle(article);
        assertEquals("Test Article (updated)", article.getTitle());
        assertEquals("Test Article (updated)", articleService.getArticleById(article.getId()).orElseThrow().getTitle());
    }

    @Test
    public void findFiltered() {
        final ArticleFilter articleFilter = new ArticleFilter().byTitle("Test Article1 (findFiltered)");
        List<Article> articles = articleService.findArticles(articleFilter);
        assertEquals(0, articles.size());

        final Article article1 = createArticle("Test Article1 (findFiltered)");
        assertNotNull(article1.getId());
        assertNotNull(article1.getArticleNumber());

        final Article article2 = createArticle("Test Article2 (findFiltered)");
        assertNotNull(article2.getId());
        assertNotNull(article2.getArticleNumber());

        articles = articleService.findArticles(articleFilter);
        assertEquals(1, articles.size());
        assertEquals(article1.getId(), articles.get(0).getId());
    }

}
