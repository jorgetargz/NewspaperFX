package domain.services.impl;

import dao.ArticlesDao;
import dao.NewspapersDao;
import domain.modelo.Newspaper;
import domain.services.ServicesNewspapers;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;

public class ServicesNewspapersImpl implements ServicesNewspapers {

    private final NewspapersDao daoNewspapers;
    private final ArticlesDao daoArticles;

    @Inject
    public ServicesNewspapersImpl(NewspapersDao daoNewspapers, ArticlesDao daoArticles) {
        this.daoNewspapers = daoNewspapers;
        this.daoArticles = daoArticles;
    }

    @Override
    public List<Newspaper> getNewspapers() {
        return daoNewspapers.getNewspapers();
    }

    @Override
    public Either<String, Boolean> saveNewspaper(Newspaper newspaper) {
        return daoNewspapers.saveNewspaper(newspaper);
    }

    @Override
    public Either<String, Boolean> deleteNewspaper(Newspaper newspaper) {
        return daoNewspapers.deleteNewspaper(newspaper);
    }

    @Override
    public boolean hasArticles(Newspaper newspaper) {
        return daoArticles.getArticles().stream()
                .anyMatch(article -> article.getNewspaperId() == newspaper.getId());
    }

}
