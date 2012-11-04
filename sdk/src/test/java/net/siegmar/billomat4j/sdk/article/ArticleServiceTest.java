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
package net.siegmar.billomat4j.sdk.article;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

import net.siegmar.billomat4j.sdk.AbstractServiceTest;
import net.siegmar.billomat4j.sdk.domain.article.Article;
import net.siegmar.billomat4j.sdk.domain.article.ArticleFilter;

import org.junit.After;
import org.junit.Test;

public class ArticleServiceTest extends AbstractServiceTest {

    // Article

    @After
    public void cleanup() {
        final List<Article> articles = articleService.findArticles(null);
        if (!articles.isEmpty()) {
            for (final Article article : articles) {
                articleService.deleteArticle(article.getId());
            }
            assertTrue(articleService.findArticles(null).isEmpty());
        }
    }

    @Test
    public void findAll() {
        assertTrue(articleService.findArticles(null).isEmpty());
        createArticle("Test Article");
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

        return article;
    }

    @Test
    public void update() {
        final Article article = createArticle("Test Article");
        article.setTitle("Test Article Update");
        articleService.updateArticle(article);
        assertEquals("Test Article Update", article.getTitle());
        assertEquals("Test Article Update", articleService.getArticleById(article.getId()).getTitle());
    }

    @Test
    public void findFiltered() {
        List<Article> articles = articleService.findArticles(null);
        assertTrue(articles.isEmpty());

        final Article article1 = createArticle("Test Article1");
        assertNotNull(article1.getId());
        assertNotNull(article1.getArticleNumber());

        final Article article2 = createArticle("Test Article2");
        assertNotNull(article2.getId());
        assertNotNull(article2.getArticleNumber());

        articles = articleService.findArticles(new ArticleFilter().byTitle("Test Article1"));
        assertEquals(1, articles.size());
        assertEquals(article1.getId(), articles.get(0).getId());
    }

}
