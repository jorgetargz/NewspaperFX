package dao.impl;

import configuration.DataPathsXML;
import dao.ReadersDao;
import domain.modelo.Reader;
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
public class ReadersDaoImpl implements ReadersDao {

    private final DataPathsXML dataPathsXML;

    @Inject
    public ReadersDaoImpl(DataPathsXML dataPathsXML) {
        this.dataPathsXML = dataPathsXML;
    }

    @Override
    public List<Reader> getReaders() {
        Path readersFile = Paths.get(dataPathsXML.getReadersPath());
        try {
            JAXBContext context = JAXBContext.newInstance(Readers.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Readers readersList = (Readers) unmarshaller.unmarshal(Files.newInputStream(readersFile));
            return readersList.getReaders();
        } catch (JAXBException | IOException e) {
            log.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    @Override
    public Reader getReaderById(int id) {
        return getReaders().stream()
                .filter(reader -> reader.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Either<String, Boolean> saveReader(Reader reader) {
        if (getReaderById(reader.getId()) == null) {
            Path readersFile = Paths.get(dataPathsXML.getReadersPath());
            try {
                JAXBContext context = JAXBContext.newInstance(Readers.class);
                Marshaller marshaller = context.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                Readers readersList = (Readers) unmarshaller.unmarshal(Files.newInputStream(readersFile));
                readersList.getReaders().add(reader);
                marshaller.marshal(readersList, Files.newOutputStream(readersFile));
                return Either.right(true);
            } catch (JAXBException | IOException e) {
                log.error(e.getMessage(), e);
                return Either.left(e.getMessage());
            }
        } else {
            return Either.left("Reader Id already exists");
        }
    }

    @Override
    public Either<String, Boolean> deleteReader(Reader reader) {
        if (getReaderById(reader.getId()) != null) {
            Path readersFile = Paths.get(dataPathsXML.getReadersPath());
            try {
                JAXBContext context = JAXBContext.newInstance(Readers.class);
                Marshaller marshaller = context.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                Readers readersList = (Readers) unmarshaller.unmarshal(Files.newInputStream(readersFile));
                readersList.getReaders().remove(reader);
                marshaller.marshal(readersList, Files.newOutputStream(readersFile));
                return Either.right(true);
            } catch (JAXBException | IOException e) {
                log.error(e.getMessage(), e);
                return Either.left(e.getMessage());
            }
        } else {
            return Either.left("Reader Id does not exist");
        }
    }
}
