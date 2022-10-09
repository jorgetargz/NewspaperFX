package dao.impl;

import configuration.DataPaths;
import dao.NewspapersDao;
import domain.modelo.Newspaper;
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
public class NewspapersDaoImpl implements NewspapersDao {

    private final DataPaths dataPaths;

    @Inject
    public NewspapersDaoImpl(DataPaths dataPaths) {
        this.dataPaths = dataPaths;
    }

    @Override
    public List<Newspaper> getNewspapers() {
        Path newspapersFile = Paths.get(dataPaths.getProperty("newspapersPath"));
        ArrayList<Newspaper> newspapers = new ArrayList<>();
        try {
            List<String> fileContent = Files.readAllLines(newspapersFile);
            fileContent.forEach(line -> newspapers.add(new Newspaper(line)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return newspapers;
    }

    @Override
    public Newspaper getNewspaperById(int id) {
        return getNewspapers().stream()
                .filter(newspaper -> newspaper.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Either<String, Boolean> saveNewspaper(Newspaper newspaper) {
        if (getNewspaperById(newspaper.getId()) == null) {
            String newspaperData = newspaper.toFileLine();
            Path newspapersFile = Paths.get(dataPaths.getProperty("newspapersPath"));
            try {
                Files.write(newspapersFile, newspaperData.getBytes(), StandardOpenOption.APPEND);
                return Either.right(true);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
                return Either.left(e.getMessage());
            }
        } else {
            return Either.left("Newspaper Id already exists");
        }
    }

    @Override
    public Either<String, Boolean> deleteNewspaper(Newspaper newspaper) {
        if (getNewspaperById(newspaper.getId()) != null) {
            String newspaperData = newspaper.toFileLine();
            Path newspapersFile = Paths.get(dataPaths.getProperty("newspapersPath"));
            try {
                List<String> fileContent = Files.readAllLines(newspapersFile);
                fileContent.remove(newspaperData);
                Files.write(newspapersFile, fileContent);
                return Either.right(true);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
                return Either.left(e.getMessage());
            }
        } else {
            return Either.left("Newspaper Id does not exist");
        }
    }
}
