package net.siegmar.billomat4j.sdk.article;

import net.siegmar.billomat4j.sdk.AbstractCustomFieldServiceIT;
import net.siegmar.billomat4j.sdk.domain.article.Article;


public class ArticleCustomFieldIT extends AbstractCustomFieldServiceIT {

    public ArticleCustomFieldIT() {
        setService(articleService);
    }

    @Override
    protected int buildOwner() {
        final Article article = new Article();
        article.setTitle("ArticleCustomFieldTest");
        articleService.createArticle(article);
        return article.getId();
    }

    @Override
    protected void deleteOwner(final int ownerId) {
        articleService.deleteArticle(ownerId);
    }

}
