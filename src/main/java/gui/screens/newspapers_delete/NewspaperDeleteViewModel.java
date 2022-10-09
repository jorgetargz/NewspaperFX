package gui.screens.newspapers_delete;

import domain.modelo.Newspaper;
import domain.services.ServicesArticles;
import domain.services.ServicesNewspapers;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class NewspaperDeleteViewModel {

    private final ServicesNewspapers servicesNewspapers;
    private final ServicesArticles servicesArticles;
    private final ObjectProperty<NewspaperDeleteState> state;
    private final ObservableList<Newspaper> observableNewspapers;

    @Inject
    public NewspaperDeleteViewModel(ServicesNewspapers servicesNewspapers, ServicesArticles servicesArticles) {
        this.servicesNewspapers = servicesNewspapers;
        this.servicesArticles = servicesArticles;
        state = new SimpleObjectProperty<>(new NewspaperDeleteState(null, false));
        observableNewspapers = FXCollections.observableArrayList();
    }

    public ReadOnlyObjectProperty<NewspaperDeleteState> getState() {
        return state;
    }

    public ObservableList<Newspaper> getObservableNewspapers() {
        return FXCollections.unmodifiableObservableList(observableNewspapers);
    }

    public void loadNewspapers() {
        List<Newspaper> newspapers = servicesNewspapers.getNewspapers();
        if (newspapers.isEmpty()) {
            state.set(new NewspaperDeleteState("There are no newspapers", false));
        } else {
            observableNewspapers.clear();
            observableNewspapers.setAll(newspapers);
        }
    }

    public void deleteNewspaper(Newspaper newspaper) {
        if (newspaper == null) {
            state.set(new NewspaperDeleteState("Select a newspaper", false));
        } else {
            if (servicesNewspapers.hasArticles(newspaper)) {
                state.set(new NewspaperDeleteState(null, true));
            } else {
                Either<String, Boolean> result = servicesNewspapers.deleteNewspaper(newspaper);
                if (result.isRight()) {
                    loadNewspapers();
                } else {
                    state.set(new NewspaperDeleteState(result.getLeft(), false));
                }
            }
        }
    }

    public void deleteNewspaperConfirm(Newspaper newspaper) {
        Either<String, Boolean> resultArticlesDelete = servicesArticles.deleteArticleByNewspaperId(newspaper.getId());
        Either<String, Boolean> resultNewspaperDelete = servicesNewspapers.deleteNewspaper(newspaper);
        if (resultArticlesDelete.isRight()) {
            if (resultNewspaperDelete.isRight()) {
                loadNewspapers();
            } else {
                state.set(new NewspaperDeleteState(resultNewspaperDelete.getLeft(), false));
            }
        } else {
            state.set(new NewspaperDeleteState(resultArticlesDelete.getLeft(), false));
        }
    }

    public void clearState() {
        state.set(new NewspaperDeleteState(null, false));
    }
}
