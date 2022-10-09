package dao;

import domain.modelo.Newspaper;
import io.vavr.control.Either;

import java.util.List;

public interface NewspapersDao {
    List<Newspaper> getNewspapers();

    Newspaper getNewspaperById(int id);

    Either<String, Boolean> saveNewspaper(Newspaper newspaper);

    Either<String, Boolean> deleteNewspaper(Newspaper newspaper);
}
