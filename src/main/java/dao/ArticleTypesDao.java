package dao;

import domain.modelo.ArticleType;
import io.vavr.control.Either;

import java.util.List;

public interface ArticleTypesDao {
    List<ArticleType> getArticleTypes();

    ArticleType getArticleTypeById(int id);

    Either<String, Boolean> saveArticleType(ArticleType articleType);

    Either<String, Boolean> deleteArticleType(ArticleType articleType);
}
