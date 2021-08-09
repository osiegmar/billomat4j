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
 * along with Billomat4J.  If not, see <https://www.gnu.org/licenses/>.
 */

package integrationtest.article;

import de.siegmar.billomat4j.domain.article.Article;
import integrationtest.AbstractCustomFieldServiceIT;
import integrationtest.ServiceHolder;

public class ArticleCustomFieldIT extends AbstractCustomFieldServiceIT {

    public ArticleCustomFieldIT() {
        setService(ServiceHolder.ARTICLE);
    }

    @Override
    protected int buildOwner() {
        final Article article = new Article();
        article.setTitle("ArticleCustomFieldTest");
        ServiceHolder.ARTICLE.createArticle(article);
        return article.getId();
    }

    @Override
    protected void deleteOwner(final int ownerId) {
        ServiceHolder.ARTICLE.deleteArticle(ownerId);
    }

}
