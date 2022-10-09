package dao.impl;

import configuration.DataPaths;
import dao.ArticlesDao;
import domain.modelo.Article;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class ArticlesDaoImpl implements ArticlesDao {

    private final DataPaths dataPaths;

    @Inject
    public ArticlesDaoImpl(DataPaths dataPaths) {
        this.dataPaths = dataPaths;
    }

    @Override
    public List<Article> getArticles() {
        Path articlesFile = Paths.get(dataPaths.getProperty("articlesPath"));
        ArrayList<Article> articles = new ArrayList<>();
        try {
            List<String> fileContent = Files.readAllLines(articlesFile);
            fileContent.forEach(line -> articles.add(new Article(line)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return articles;
    }

    @Override
    public Article getArticleById(int id) {
        return getArticles().stream()
                .filter(article -> article.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Either<String, Boolean> saveArticle(Article article) {
        String articleData = article.toFileLine();
        if (getArticleById(article.getId()) == null) {
            Path articlesFile = Paths.get(dataPaths.getProperty("articlesPath"));
            try {
                Files.write(articlesFile, articleData.getBytes(), StandardOpenOption.APPEND);
                return Either.right(true);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
                return Either.left(e.getMessage());
            }
        } else {
            return Either.left("Article Id already exists");
        }
    }

    @Override
    public Either<String, Boolean> deleteArticle(Article article) {
        if (getArticleById(article.getId()) != null) {
            String articleData = article.toFileLine();
            Path articlesFile = Paths.get(dataPaths.getProperty("articlesPath"));
            try {
                List<String> fileContent = Files.readAllLines(articlesFile);
                fileContent.remove(articleData);
                Files.write(articlesFile, fileContent);
                return Either.right(true);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
                return Either.left(e.getMessage());
            }
        } else {
            return Either.left("Article Id does not exist");
        }
    }
}
