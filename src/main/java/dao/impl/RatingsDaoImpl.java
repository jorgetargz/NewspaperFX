package dao.impl;

import configuration.DataPathsXML;
import dao.RatingsDao;
import domain.modelo.ArticleRating;
import domain.modelo.Readers;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

@Log4j2
public class RatingsDaoImpl implements RatingsDao {

    private final DataPathsXML dataPathsXML;

    @Inject
    public RatingsDaoImpl(DataPathsXML dataPathsXML) {
        this.dataPathsXML = dataPathsXML;
    }

    @Override
    public List<ArticleRating> getRatings() {
        Path readersFile = Paths.get(dataPathsXML.getReadersPath());
        try {
            JAXBContext context = JAXBContext.newInstance(Readers.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Readers readersList = (Readers) unmarshaller.unmarshal(Files.newInputStream(readersFile));
            return readersList.getReaders().stream()
                    .flatMap(reader -> reader.getArticleRatings().stream())
                    .toList();
        } catch (JAXBException | IOException e) {
            log.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    @Override
    public ArticleRating getRatingById(ArticleRating articleRating) {
        return getRatings().stream()
                .filter(articleRating1 -> articleRating1.getId() == articleRating.getId())
                .findFirst()
                .orElse(null);
    }

    @Override
    public Either<String, Boolean> saveRating(ArticleRating articleRating) {
        if (getRatingById(articleRating) == null) {
            Path readersFile = Paths.get(dataPathsXML.getReadersPath());
            try {
                JAXBContext context = JAXBContext.newInstance(Readers.class);
                Marshaller marshaller = context.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                Readers readersList = (Readers) unmarshaller.unmarshal(Files.newInputStream(readersFile));
                readersList.getReaders().stream()
                        .filter(reader -> reader.getId() == articleRating.getIdReader())
                        .findFirst()
                        .ifPresent(reader -> reader.getArticleRatings().add(articleRating));
                marshaller.marshal(readersList, Files.newOutputStream(readersFile));
                return Either.right(true);
            } catch (JAXBException | IOException e) {
                log.error(e.getMessage(), e);
                return Either.left(e.getMessage());
            }
        } else {
            return Either.left("Rating already exists");
        }
    }

    @Override
    public Either<String, Boolean> deleteRating(ArticleRating articleRating) {
        if (getRatingById(articleRating) != null) {
            Path readersFile = Paths.get(dataPathsXML.getReadersPath());
            try {
                JAXBContext context = JAXBContext.newInstance(Readers.class);
                Marshaller marshaller = context.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                Readers readersList = (Readers) unmarshaller.unmarshal(Files.newInputStream(readersFile));
                readersList.getReaders().stream()
                        .filter(reader -> reader.getId() == articleRating.getIdReader())
                        .findFirst()
                        .ifPresent(reader -> reader.getArticleRatings().remove(articleRating));
                marshaller.marshal(readersList, Files.newOutputStream(readersFile));
                return Either.right(true);
            } catch (JAXBException | IOException e) {
                log.error(e.getMessage(), e);
                return Either.left(e.getMessage());
            }
        } else {
            return Either.left("Rating does not exist");
        }
    }
}
