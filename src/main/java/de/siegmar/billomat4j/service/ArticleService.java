/*
 * Copyright 2021 Oliver Siegmar
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
 * along with Billomat4J.  If not, see <https://www.gnu.org/licenses/>.
 */

package de.siegmar.billomat4j.service;

import java.util.List;
import java.util.Optional;

import de.siegmar.billomat4j.domain.article.Article;
import de.siegmar.billomat4j.domain.article.ArticleFilter;
import de.siegmar.billomat4j.domain.article.ArticlePropertyValue;
import de.siegmar.billomat4j.domain.article.ArticlePropertyValues;
import de.siegmar.billomat4j.domain.article.ArticleTag;
import de.siegmar.billomat4j.domain.article.ArticleTags;
import de.siegmar.billomat4j.domain.article.Articles;
import de.siegmar.billomat4j.domain.settings.ArticleProperties;
import de.siegmar.billomat4j.domain.settings.ArticleProperty;
import lombok.NonNull;

public class ArticleService extends AbstractService
    implements GenericCustomFieldService, GenericPropertyService<ArticleProperty,
    ArticlePropertyValue>, GenericTagService<ArticleTag> {

    private static final String RESOURCE = "articles";
    private static final String PROPERTIES_RESOURCE = "article-properties";
    private static final String ATTRIBUTE_RESOURCE = "article-property-values";
    private static final String TAG_RESOURCE = "article-tags";

    public ArticleService(final BillomatConfiguration billomatConfiguration) {
        super(billomatConfiguration);
    }

    // Article

    @Override
    public Optional<String> getCustomFieldValue(final int articleId) {
        return getCustomField(RESOURCE, articleId);
    }

    @Override
    public void setCustomFieldValue(final int articleId, final String value) {
        updateCustomField(RESOURCE, articleId, "article", value);
    }

    /**
     * @param articleFilter article filter, may be {@code null} to find unfiltered
     * @return articles found by filter criteria or an empty list if no articles were found - never {@code null}
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public List<Article> findArticles(final ArticleFilter articleFilter) {
        return getAllPagesFromResource(RESOURCE, Articles.class, articleFilter);
    }

    /**
     * Gets an article by its id.
     *
     * @param articleId the article's id
     * @return the article
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public Optional<Article> getArticleById(final int articleId) {
        return getById(RESOURCE, Article.class, articleId);
    }

    /**
     * Gets an article by its article number.
     *
     * @param articleNumber the article number, must not be empty / {@code null}
     * @return the article
     * @throws NullPointerException     if articleNumber is null
     * @throws IllegalArgumentException if articleNumber is empty
     * @throws ServiceException         if an error occurred while accessing the web service
     */
    public Optional<Article> getArticleByNumber(@NonNull final String articleNumber) {
        if (articleNumber.isEmpty()) {
            throw new IllegalArgumentException("articleNumber must not be empty");
        }
        return single(findArticles(new ArticleFilter().byArticleNumber(articleNumber)));
    }

    /**
     * @param article the article to create, must not be {@code null}
     * @throws NullPointerException if article is null
     * @throws ServiceException     if an error occurred while accessing the web service
     */
    public void createArticle(@NonNull final Article article) {
        create(RESOURCE, article);
    }

    /**
     * @param article the article to update, must not be {@code null}
     * @throws NullPointerException if article is null
     * @throws ServiceException     if an error occurred while accessing the web service
     */
    public void updateArticle(@NonNull final Article article) {
        update(RESOURCE, article);
    }

    /**
     * @param articleId the id of the article to be deleted
     * @throws ServiceException if an error occurred while accessing the web service
     */
    public void deleteArticle(final int articleId) {
        delete(RESOURCE, articleId);
    }

    // ArticleProperty

    @Override
    public List<ArticleProperty> getProperties() {
        return getAllPagesFromResource(PROPERTIES_RESOURCE, ArticleProperties.class, null);
    }

    @Override
    public Optional<ArticleProperty> getPropertyById(final int articlePropertyId) {
        return getById(PROPERTIES_RESOURCE, ArticleProperty.class, articlePropertyId);
    }

    @Override
    public void createProperty(@NonNull final ArticleProperty articleProperty) {
        create(PROPERTIES_RESOURCE, articleProperty);
    }

    @Override
    public void updateProperty(@NonNull final ArticleProperty articleProperty) {
        update(PROPERTIES_RESOURCE, articleProperty);
    }

    @Override
    public void deleteProperty(final int articlePropertyId) {
        delete(PROPERTIES_RESOURCE, articlePropertyId);
    }

    // ArticlePropertyValue

    @Override
    public List<ArticlePropertyValue> getPropertyValues(final int articleId) {
        return getAllPagesFromResource(ATTRIBUTE_RESOURCE, ArticlePropertyValues.class, articleIdFilter(articleId));
    }

    private GenericFilter articleIdFilter(final Integer articleId) {
        return articleId == null ? null : new GenericFilter("article_id", articleId);
    }

    @Override
    public Optional<ArticlePropertyValue> getPropertyValueById(final int articlePropertyValueId) {
        return getById(ATTRIBUTE_RESOURCE, ArticlePropertyValue.class, articlePropertyValueId);
    }

    @Override
    public void createPropertyValue(@NonNull final ArticlePropertyValue articlePropertyValue) {
        create(ATTRIBUTE_RESOURCE, articlePropertyValue);
    }

    // ArticleTag

    @Override
    public List<ArticleTag> getTags(final Integer articleId) {
        return getAllPagesFromResource(TAG_RESOURCE, ArticleTags.class, articleIdFilter(articleId));
    }

    @Override
    public Optional<ArticleTag> getTagById(final int articleTagId) {
        return getById(TAG_RESOURCE, ArticleTag.class, articleTagId);
    }

    @Override
    public void createTag(@NonNull final ArticleTag articleTag) {
        create(TAG_RESOURCE, articleTag);
    }

    @Override
    public void deleteTag(final int articleTagId) {
        delete(TAG_RESOURCE, articleTagId);
    }

}
