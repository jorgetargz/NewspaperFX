package domain.services.impl;

import dao.ArticleTypesDao;
import dao.ArticlesDao;
import dao.NewspapersDao;
import domain.modelo.Article;
import domain.modelo.ArticleType;
import domain.services.ServicesArticles;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;


public class ServicesArticlesImpl implements ServicesArticles {

    private final ArticlesDao daoArticles;
    private final NewspapersDao daoNewspapers;
    private final ArticleTypesDao daoArticleTypes;

    @Inject
    public ServicesArticlesImpl(ArticlesDao daoArticles, NewspapersDao daoNewspapers, ArticleTypesDao daoArticleTypes) {
        this.daoArticles = daoArticles;
        this.daoNewspapers = daoNewspapers;
        this.daoArticleTypes = daoArticleTypes;
    }

    @Override
    public List<Article> getArticles() {
        return daoArticles.getArticles();
    }

    @Override
    public List<Article> getArticlesByType(int typeId) {
        return daoArticles.getArticles().stream()
                .filter(article -> article.getArticleType() == typeId)
                .toList();
    }

    @Override
    public List<ArticleType> getArticleTypes() {
        return daoArticleTypes.getArticleTypes();
    }

    @Override
    public Either<String, Boolean> saveArticle(Article article) {
        if (daoNewspapers.getNewspaperById(article.getNewspaperId()) != null) {
            if (daoArticleTypes.getArticleTypeById(article.getArticleType()) != null) {
                return daoArticles.saveArticle(article);
            } else {
                return Either.left("Article Type does not exist");
            }
        } else {
            return Either.left("Newspaper does not exist");
        }
    }

    @Override
    public Either<String, Boolean> saveArticleType(ArticleType articleType) {
        return daoArticleTypes.saveArticleType(articleType);
    }

    @Override
    public Either<String, Boolean> deleteArticle(Article article) {
        return daoArticles.deleteArticle(article);
    }

    @Override
    public Either<String, Boolean> deleteArticleByNewspaperId(int newspaperId) {
        List<Article> articles = daoArticles.getArticles().stream()
                .filter(article -> article.getNewspaperId() == newspaperId)
                .toList();
        if (articles.isEmpty()) {
            return Either.left("No articles found for Newspaper Id");
        } else {
            for (Article article : articles) {
                daoArticles.deleteArticle(article);
            }
            return Either.right(true);
        }
    }
}
