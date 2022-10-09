package domain.services.impl;

import dao.ArticlesDao;
import dao.ReadersDao;
import dao.SubscriptionsDao;
import domain.modelo.Article;
import domain.modelo.ArticleType;
import domain.modelo.Newspaper;
import domain.modelo.Reader;
import domain.services.ServicesReaders;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

public class ServicesReadersImpl implements ServicesReaders {

    private final ReadersDao daoReaders;
    private final ArticlesDao daoArticles;
    private final SubscriptionsDao daoSubscriptions;

    @Inject
    public ServicesReadersImpl(ReadersDao daoReaders, ArticlesDao daoArticles, SubscriptionsDao daoSubscriptions) {
        this.daoReaders = daoReaders;
        this.daoArticles = daoArticles;
        this.daoSubscriptions = daoSubscriptions;
    }

    @Override
    public List<Reader> getReaders() {
        return daoReaders.getReaders();
    }

    @Override
    public List<Reader> getReadersByArticleType(ArticleType articleType) {
        List<Integer> articlesIds = daoArticles.getArticles().stream()
                .filter(article -> article.getArticleType() == articleType.getId())
                .map(Article::getId)
                .toList();
        return getReaders().stream()
                .filter(reader -> reader.getArticleRatings().stream()
                        .anyMatch(articleRating -> articlesIds.contains(articleRating.getIdArticle())))
                .collect(Collectors.toSet()).stream().toList();
    }

    @Override
    public Reader getReaderById(int id) {
        return daoReaders.getReaderById(id);
    }

    @Override
    public Either<String, Boolean> saveReader(Reader reader) {
        return daoReaders.saveReader(reader);
    }

    @Override
    public Either<String, Boolean> deleteReader(Reader reader) {
        return daoReaders.deleteReader(reader);
    }


    @Override
    public List<Reader> getReadersByNewspaper(Newspaper newspaper) {
        return daoSubscriptions.getSubscriptions().stream()
                .filter(subscription -> subscription.getIdNewspaper() == newspaper.getId())
                .map(subscription -> getReaderById(subscription.getIdReader()))
                .collect(Collectors.toSet()).stream().toList();
    }
}
