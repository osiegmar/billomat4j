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
package net.siegmar.billomat4j.sdk.service.impl;

import java.util.List;

import net.siegmar.billomat4j.sdk.domain.article.Article;
import net.siegmar.billomat4j.sdk.domain.article.ArticleFilter;
import net.siegmar.billomat4j.sdk.domain.article.ArticlePropertyValue;
import net.siegmar.billomat4j.sdk.domain.article.ArticlePropertyValues;
import net.siegmar.billomat4j.sdk.domain.article.ArticleTag;
import net.siegmar.billomat4j.sdk.domain.article.ArticleTags;
import net.siegmar.billomat4j.sdk.domain.article.Articles;
import net.siegmar.billomat4j.sdk.domain.settings.ArticleProperties;
import net.siegmar.billomat4j.sdk.domain.settings.ArticleProperty;
import net.siegmar.billomat4j.sdk.service.ArticleService;

import org.apache.commons.lang3.Validate;

public class ArticleServiceImpl extends AbstractService implements ArticleService {

    private static final String RESOURCE = "articles";
    private static final String PROPERTIES_RESOURCE = "article-properties";
    private static final String ATTRIBUTE_RESOURCE = "article-property-values";
    private static final String TAG_RESOURCE = "article-tags";

    public ArticleServiceImpl(final BillomatConfiguration billomatConfiguration) {
        super(billomatConfiguration);
    }

    // Article

    @Override
    public String getCustomFieldValue(final int articleId) {
        return getCustomField(RESOURCE, articleId);
    }

    @Override
    public void setCustomFieldValue(final int articleId, final String value) {
        updateCustomField(RESOURCE, articleId, "article", value);
    }

    @Override
    public List<Article> findArticles(final ArticleFilter articleFilter) {
        return getAllPagesFromResource(RESOURCE, Articles.class, articleFilter);
    }

    @Override
    public Article getArticleById(final int id) {
        return getById(RESOURCE, Article.class, id);
    }

    @Override
    public Article getArticleByNumber(final String articleNumber) {
        return single(findArticles(new ArticleFilter().byArticleNumber(Validate.notEmpty(articleNumber))));
    }

    @Override
    public void createArticle(final Article article) {
        create(RESOURCE, Validate.notNull(article));
    }

    @Override
    public void updateArticle(final Article article) {
        update(RESOURCE, Validate.notNull(article));
    }

    @Override
    public void deleteArticle(final int id) {
        delete(RESOURCE, id);
    }

    // ArticleProperty

    @Override
    public List<ArticleProperty> getProperties() {
        return getAllPagesFromResource(PROPERTIES_RESOURCE, ArticleProperties.class, null);
    }

    @Override
    public ArticleProperty getPropertyById(final int articlePropertyId) {
        return getById(PROPERTIES_RESOURCE, ArticleProperty.class, articlePropertyId);
    }

    @Override
    public void createProperty(final ArticleProperty articleProperty) {
        create(PROPERTIES_RESOURCE, Validate.notNull(articleProperty));
    }

    @Override
    public void updateProperty(final ArticleProperty articleProperty) {
        update(PROPERTIES_RESOURCE, Validate.notNull(articleProperty));
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
    public ArticlePropertyValue getPropertyValueById(final int articlePropertyValueId) {
        return getById(ATTRIBUTE_RESOURCE, ArticlePropertyValue.class, articlePropertyValueId);
    }

    @Override
    public void createPropertyValue(final ArticlePropertyValue articlePropertyValue) {
        create(ATTRIBUTE_RESOURCE, Validate.notNull(articlePropertyValue));
    }

    // ArticleTag

    @Override
    public List<ArticleTag> getTags(final Integer articleId) {
        return getAllPagesFromResource(TAG_RESOURCE, ArticleTags.class, articleIdFilter(articleId));
    }

    @Override
    public ArticleTag getTagById(final int articleTagId) {
        return getById(TAG_RESOURCE, ArticleTag.class, articleTagId);
    }

    @Override
    public void createTag(final ArticleTag articleTag) {
        create(TAG_RESOURCE, Validate.notNull(articleTag));
    }

    @Override
    public void deleteTag(final int articleTagId) {
        delete(TAG_RESOURCE, articleTagId);
    }

}
