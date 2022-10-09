package dao;

import domain.modelo.Article;
import io.vavr.control.Either;

import java.util.List;

public interface ArticlesDao {
    List<Article> getArticles();

    Article getArticleById(int id);

    Either<String, Boolean> saveArticle(Article article);

    Either<String, Boolean> deleteArticle(Article article);
}
