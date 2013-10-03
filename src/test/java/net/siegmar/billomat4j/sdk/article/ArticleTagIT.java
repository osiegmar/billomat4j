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

import net.siegmar.billomat4j.sdk.AbstractTagIT;
import net.siegmar.billomat4j.sdk.domain.article.Article;
import net.siegmar.billomat4j.sdk.domain.article.ArticleTag;


public class ArticleTagIT extends AbstractTagIT<ArticleTag> {

    public ArticleTagIT() {
        setService(articleService);
    }

    @Override
    protected int createOwner() {
        final Article article = new Article();
        articleService.createArticle(article);

        return article.getId();
    }

    @Override
    protected ArticleTag constructTag(final int ownerId) {
        final ArticleTag tag = new ArticleTag();
        tag.setArticleId(ownerId);
        return tag;
    }

    @Override
    protected void deleteOwner(final int ownerId) {
        articleService.deleteArticle(ownerId);
    }

}
