package net.siegmar.billomat4j.sdk.article;

import net.siegmar.billomat4j.sdk.AbstractCustomFieldServiceTest;
import net.siegmar.billomat4j.sdk.domain.article.Article;


public class ArticleCustomFieldTest extends AbstractCustomFieldServiceTest {

    public ArticleCustomFieldTest() {
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
