package domain.services.impl;

import dao.SubscriptionsDao;
import domain.modelo.Newspaper;
import domain.modelo.Subscription;
import domain.services.ServicesSubscriptions;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;

public class ServicesSubscriptionsImpl implements ServicesSubscriptions {

    private final SubscriptionsDao daoSubscriptions;

    @Inject
    public ServicesSubscriptionsImpl(SubscriptionsDao daoSubscriptions) {
        this.daoSubscriptions = daoSubscriptions;
    }

    @Override
    public List<Subscription> getSubscriptions() {
        return daoSubscriptions.getSubscriptions();
    }


    @Override
    public List<Subscription> getSubscriptionsByNewspaper(Newspaper newspaper) {
        return getSubscriptions().stream()
                .filter(subscription -> subscription.getIdNewspaper() == newspaper.getId())
                .toList();
    }

    @Override
    public Subscription getSubscriptionById(Subscription subscription) {
        return daoSubscriptions.getSubscriptionById(subscription);
    }

    @Override
    public Either<String, Boolean> addSubscription(Subscription subscription) {
        return daoSubscriptions.saveSubscription(subscription);
    }

    @Override
    public Either<String, Boolean> deleteSubscription(Subscription subscription) {
        return daoSubscriptions.deleteSubscription(subscription);
    }
}
