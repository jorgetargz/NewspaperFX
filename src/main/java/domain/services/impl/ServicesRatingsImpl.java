package domain.services.impl;

import dao.ArticlesDao;
import dao.RatingsDao;
import dao.ReadersDao;
import domain.modelo.Article;
import domain.modelo.ArticleRating;
import domain.modelo.ArticleType;
import domain.services.ServicesRatings;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;

public class ServicesRatingsImpl implements ServicesRatings {

    private final RatingsDao daoRatings;
    private final ArticlesDao daoArticles;
    private final ReadersDao daoReaders;

    @Inject
    public ServicesRatingsImpl(RatingsDao daoRatings, ArticlesDao daoArticles, ReadersDao daoReaders) {
        this.daoRatings = daoRatings;
        this.daoArticles = daoArticles;
        this.daoReaders = daoReaders;
    }

    @Override
    public List<ArticleRating> getRatings() {
        return daoRatings.getRatings();
    }

    @Override
    public List<ArticleRating> getRatingByArticleType(ArticleType articleType) {
        List<Integer> articlesIds = daoArticles.getArticles().stream()
                .filter(article -> article.getArticleType() == articleType.getId())
                .map(Article::getId)
                .toList();
        return getRatings().stream()
                .filter(articleRating -> articlesIds.contains(articleRating.getIdArticle()))
                .toList();
    }

    @Override
    public ArticleRating getRatingById(ArticleRating articleRating) {
        return daoRatings.getRatingById(articleRating);
    }

    @Override
    public Either<String, Boolean> saveRating(ArticleRating articleRating) {
        if (daoReaders.getReaderById(articleRating.getIdReader()) == null) {
            return Either.left("Reader not found");
        } else if (daoArticles.getArticleById(articleRating.getIdArticle()) == null) {
            return Either.left("Article not found");
        } else {
            return daoRatings.saveRating(articleRating);
        }
    }

    @Override
    public Either<String, Boolean> deleteRating(ArticleRating articleRating) {
        return daoRatings.deleteRating(articleRating);
    }

}
