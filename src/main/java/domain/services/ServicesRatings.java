package domain.services;

import domain.modelo.ArticleRating;
import domain.modelo.ArticleType;
import io.vavr.control.Either;

import java.util.List;

public interface ServicesRatings {
    List<ArticleRating> getRatings();

    List<ArticleRating> getRatingByArticleType(ArticleType articleType);

    ArticleRating getRatingById(ArticleRating articleRating);

    Either<String, Boolean> saveRating(ArticleRating articleRating);

    Either<String, Boolean> deleteRating(ArticleRating articleRating);
}
