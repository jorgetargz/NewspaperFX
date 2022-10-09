package domain.services;

import domain.modelo.Newspaper;
import domain.modelo.Subscription;
import io.vavr.control.Either;

import java.util.List;

public interface ServicesSubscriptions {
    List<Subscription> getSubscriptions();

    List<Subscription> getSubscriptionsByNewspaper(Newspaper newspaper);

    Subscription getSubscriptionById(Subscription subscription);

    Either<String, Boolean> addSubscription(Subscription subscription);

    Either<String, Boolean> deleteSubscription(Subscription subscription);
}
