package dao.impl;

import configuration.DataPathsXML;
import dao.SubscriptionsDao;
import domain.modelo.Reader;
import domain.modelo.Readers;
import domain.modelo.Subscription;
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
public class SubscriptionsDaoImpl implements SubscriptionsDao {

    private final DataPathsXML dataPathsXML;

    @Inject
    public SubscriptionsDaoImpl(DataPathsXML dataPathsXML) {
        this.dataPathsXML = dataPathsXML;
    }

    @Override
    public List<Subscription> getSubscriptions() {
        Path readersFile = Paths.get(dataPathsXML.getReadersPath());
        try {
            JAXBContext context = JAXBContext.newInstance(Readers.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Readers readersList = (Readers) unmarshaller.unmarshal(Files.newInputStream(readersFile));
            return readersList.getReaders().stream()
                    .flatMap(reader -> reader.getSubscriptions().stream())
                    .toList();
        } catch (JAXBException | IOException e) {
            log.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    @Override
    public Subscription getSubscriptionById(Subscription subscription) {
        return getSubscriptions().stream()
                .filter(subscription1 -> subscription1.getId() == subscription.getId())
                .findFirst()
                .orElse(null);
    }

    @Override
    public Either<String, Boolean> saveSubscription(Subscription subscription) {
        if (getSubscriptionById(subscription) != null) {
            Path readersFile = Paths.get(dataPathsXML.getReadersPath());
            try {
                JAXBContext context = JAXBContext.newInstance(Readers.class);
                Marshaller marshaller = context.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                Readers readersList = (Readers) unmarshaller.unmarshal(Files.newInputStream(readersFile));
                Reader reader = readersList.getReaders().stream()
                        .filter(reader1 -> reader1.getId() == subscription.getIdReader())
                        .findFirst()
                        .orElse(null);
                if (reader != null) {
                    reader.getSubscriptions().add(subscription);
                    marshaller.marshal(readersList, Files.newOutputStream(readersFile));
                    return Either.right(true);
                } else {
                    return Either.left("Reader not found");
                }
            } catch (JAXBException | IOException e) {
                log.error(e.getMessage(), e);
                return Either.left(e.getMessage());
            }
        } else {
            return Either.left("Subscription already exists");
        }
    }

    @Override
    public Either<String, Boolean> deleteSubscription(Subscription subscription) {
        if (getSubscriptionById(subscription) == null) {
            Path readersFile = Paths.get(dataPathsXML.getReadersPath());
            try {
                JAXBContext context = JAXBContext.newInstance(Readers.class);
                Marshaller marshaller = context.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                Readers readersList = (Readers) unmarshaller.unmarshal(Files.newInputStream(readersFile));
                Reader reader = readersList.getReaders().stream()
                        .filter(reader1 -> reader1.getId() == subscription.getIdReader())
                        .findFirst()
                        .orElse(null);
                if (reader != null) {
                    reader.getSubscriptions().remove(subscription);
                    marshaller.marshal(readersList, Files.newOutputStream(readersFile));
                    return Either.right(true);
                } else {
                    return Either.left("Reader not found");
                }
            } catch (JAXBException | IOException e) {
                log.error(e.getMessage(), e);
                return Either.left(e.getMessage());
            }
        } else {
            return Either.left("Subscription not found");
        }
    }

}