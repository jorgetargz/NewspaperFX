package dao.impl;

import configuration.DataPaths;
import dao.ArticleTypesDao;
import domain.modelo.ArticleType;
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
public class ArticleTypesDaoImpl implements ArticleTypesDao {

    private final DataPaths dataPaths;

    @Inject
    public ArticleTypesDaoImpl(DataPaths dataPaths) {
        this.dataPaths = dataPaths;
    }

    @Override
    public List<ArticleType> getArticleTypes() {
        Path articleTypesFile = Paths.get(dataPaths.getProperty("articleTypesPath"));
        ArrayList<ArticleType> articleTypes = new ArrayList<>();
        try {
            List<String> fileContent = Files.readAllLines(articleTypesFile);
            fileContent.forEach(line -> {
                articleTypes.add(new ArticleType(line));
            });
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return articleTypes;
    }

    @Override
    public ArticleType getArticleTypeById(int id) {
        return getArticleTypes().stream()
                .filter(articleType -> articleType.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Either<String, Boolean> saveArticleType(ArticleType articleType) {
        if (getArticleTypeById(articleType.getId()) == null) {
            String articleTypeData = articleType.toFileLine();
            Path articleTypesFile = Paths.get(dataPaths.getProperty("articleTypesPath"));
            try {
                Files.write(articleTypesFile, articleTypeData.getBytes(), StandardOpenOption.APPEND);
                return Either.right(true);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
                return Either.left(e.getMessage());
            }
        } else {
            return Either.left("Article Type Id already exists");
        }
    }

    @Override
    public Either<String, Boolean> deleteArticleType(ArticleType articleType) {
        if (getArticleTypeById(articleType.getId()) != null) {
            String articleTypeData = articleType.toFileLine();
            Path articleTypesFile = Paths.get(dataPaths.getProperty("articleTypesPath"));
            try {
                List<String> fileContent = Files.readAllLines(articleTypesFile);
                fileContent.remove(articleTypeData);
                Files.write(articleTypesFile, fileContent);
                return Either.right(true);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
                return Either.left(e.getMessage());
            }
        } else {
            return Either.left("Article Type Id does not exist");
        }
    }
}
