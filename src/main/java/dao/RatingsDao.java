package dao;

import domain.modelo.ArticleRating;
import io.vavr.control.Either;

import java.util.List;

public interface RatingsDao {
    List<ArticleRating> getRatings();

    ArticleRating getRatingById(ArticleRating articleRating);

    Either<String, Boolean> saveRating(ArticleRating articleRating);

    Either<String, Boolean> deleteRating(ArticleRating articleRating);
}
