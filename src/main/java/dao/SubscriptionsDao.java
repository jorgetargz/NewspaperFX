package dao;

import domain.modelo.Subscription;
import io.vavr.control.Either;

import java.util.List;

public interface SubscriptionsDao {
    List<Subscription> getSubscriptions();

    Subscription getSubscriptionById(Subscription subscription);

    Either<String, Boolean> saveSubscription(Subscription subscription);

    Either<String, Boolean> deleteSubscription(Subscription subscription);
}
