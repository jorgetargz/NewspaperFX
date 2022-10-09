package gui.screens.ratings_add;

import domain.modelo.ArticleRating;
import domain.services.ServicesRatings;
import gui.screens.ratings_list.RatingsListState;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class RatingsAddViewModel {

    private final ServicesRatings servicesRatings;
    private final ObjectProperty<RatingsAddState> state;
    private final ObservableList<ArticleRating> observableRatings;

    @Inject
    public RatingsAddViewModel(ServicesRatings servicesRatings) {
        this.servicesRatings = servicesRatings;
        state = new SimpleObjectProperty<>(new RatingsAddState(null,false));
        observableRatings = FXCollections.observableArrayList();
    }

    public ReadOnlyObjectProperty<RatingsAddState> getState() {
        return state;
    }

    public ObservableList<ArticleRating> getObservableRatings() {
        return FXCollections.unmodifiableObservableList(observableRatings);
    }

    public void loadRatings() {
        List<ArticleRating> ratings = servicesRatings.getRatings();
        if (ratings.isEmpty()) {
            state.set(new RatingsAddState("There are no ratings",false));
        } else {
            observableRatings.clear();
            observableRatings.setAll(ratings);
        }
    }

    public void addRating(String idInput, String idReaderInput, String idArticleInput, String ratingInput) {
        try {
            int id = Integer.parseInt(idInput);
            int idReader = Integer.parseInt(idReaderInput);
            int idArticle = Integer.parseInt(idArticleInput);
            int rating = Integer.parseInt(ratingInput);
            ArticleRating articleRating = new ArticleRating(id, idReader, idArticle, rating);
            Either<String, Boolean> response = servicesRatings.saveRating(articleRating);
            if(response.isRight()){
                state.set(new RatingsAddState(null,true));
                loadRatings();
            }else{
                state.set(new RatingsAddState(response.getLeft(),false));
            }
        } catch ( NumberFormatException e) {
            state.set(new RatingsAddState("All fields must be simple numbers without commas",false));
        }
    }
}
