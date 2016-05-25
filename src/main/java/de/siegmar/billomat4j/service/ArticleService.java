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

import java.util.List;

import de.siegmar.billomat4j.domain.article.Article;
import de.siegmar.billomat4j.domain.article.ArticleFilter;
import de.siegmar.billomat4j.domain.article.ArticlePropertyValue;
import de.siegmar.billomat4j.domain.article.ArticleTag;
import de.siegmar.billomat4j.domain.settings.ArticleProperty;

/**
 * @see <a href="http://www.billomat.com/api/artikel/">API Artikel</a>
 * @see <a href="http://www.billomat.com/api/artikel/attribute/">API Artikel/Attribute</a>
 * @see <a href="http://www.billomat.com/api/artikel/schlagworte/">API Artikel/Schlagworte</a>
 * @see <a href="http://www.billomat.com/api/einstellungen/artikel-attribute/">API Artikel/Attribute/Einstellungen</a>
 */
public interface ArticleService extends
    GenericCustomFieldService,
    GenericPropertyService<ArticleProperty, ArticlePropertyValue>,
    GenericTagService<ArticleTag> {

    /**
     * @param articleFilter
     *            article filter, may be {@code null} to find unfiltered
     * @return articles found by filter criteria or an empty list if no articles were found - never {@code null}
     * @throws de.siegmar.billomat4j.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    List<Article> findArticles(ArticleFilter articleFilter);

    /**
     * Gets an article by its id.
     *
     * @param articleId
     *            the article's id
     * @return the article or {@code null} if not found
     * @throws de.siegmar.billomat4j.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    Article getArticleById(int articleId);

    /**
     * Gets an article by its article number.
     *
     * @param articleNumber
     *            the article number, must not be empty / {@code null}
     * @return the article or {@code null} if not found
     * @throws NullPointerException
     *             if articleNumber is null
     * @throws IllegalArgumentException
     *             if articleNumber is empty
     * @throws de.siegmar.billomat4j.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    Article getArticleByNumber(String articleNumber);

    /**
     * @param article
     *            the article to create, must not be {@code null}
     * @throws NullPointerException
     *             if article is null
     * @throws de.siegmar.billomat4j.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    void createArticle(Article article);

    /**
     * @param article
     *            the article to update, must not be {@code null}
     * @throws NullPointerException
     *             if article is null
     * @throws de.siegmar.billomat4j.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    void updateArticle(Article article);

    /**
     * @param articleId
     *            the id of the article to be deleted
     * @throws de.siegmar.billomat4j.service.impl.ServiceException
     *             if an error occured while accessing the web service
     */
    void deleteArticle(int articleId);

}
