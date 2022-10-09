package domain.services;

import domain.modelo.Article;
import domain.modelo.ArticleType;
import io.vavr.control.Either;

import java.util.List;

public interface ServicesArticles {
    List<Article> getArticles();

    List<Article> getArticlesByType(int typeId);

    List<ArticleType> getArticleTypes();

    Either<String, Boolean> saveArticle(Article article);

    Either<String, Boolean> saveArticleType(ArticleType articleType);

    Either<String, Boolean> deleteArticle(Article article);

    Either<String, Boolean> deleteArticleByNewspaperId(int newspaperId);
}
