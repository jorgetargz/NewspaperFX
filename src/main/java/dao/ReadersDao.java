package dao;

import domain.modelo.Reader;
import io.vavr.control.Either;

import java.util.List;

public interface ReadersDao {
    List<Reader> getReaders();

    Reader getReaderById(int id);

    Either<String, Boolean> saveReader(Reader reader);

    Either<String, Boolean> deleteReader(Reader reader);
}
