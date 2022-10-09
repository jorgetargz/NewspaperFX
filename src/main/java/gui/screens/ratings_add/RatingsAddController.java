package gui.screens.ratings_add;

import domain.modelo.ArticleRating;
import gui.screens.common.BaseScreenController;
import gui.screens.common.ScreenConstants;
import io.github.palexdev.materialfx.controls.MFXTextField;
import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class RatingsAddController extends BaseScreenController {

    @FXML
    private Label title;
    @FXML
    private TableView<ArticleRating> tableRatings;
    @FXML
    private TableColumn<ArticleRating, Integer> columnId;
    @FXML
    private TableColumn<ArticleRating, Integer> columnIdReader;
    @FXML
    private TableColumn<ArticleRating, Integer> columnIdArticle;
    @FXML
    private TableColumn<ArticleRating, Integer> columnRating;
    @FXML
    private MFXTextField id;
    @FXML
    private MFXTextField idReader;
    @FXML
    private MFXTextField idArticle;
    @FXML
    private MFXTextField rating;

    private final RatingsAddViewModel ratingsAddViewModel;

    @Inject
    public RatingsAddController(RatingsAddViewModel ratingsAddViewModel) {
        this.ratingsAddViewModel = ratingsAddViewModel;
    }

    public void initialize() {
        title.setText("Add a rating");

        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnIdReader.setCellValueFactory(new PropertyValueFactory<>("idReader"));
        columnIdArticle.setCellValueFactory(new PropertyValueFactory<>("idArticle"));
        columnRating.setCellValueFactory(new PropertyValueFactory<>("rating"));

        tableRatings.setItems(ratingsAddViewModel.getObservableRatings());

        ratingsAddViewModel.loadRatings();

        ratingsAddViewModel.getState().addListener((observableValue, oldState, newState) -> {
            if (newState.getError() != null) {
                this.getPrincipalController().showAlert(Alert.AlertType.ERROR, ScreenConstants.ERROR, newState.getError());
            }
            if (newState.isRatingAdded()) {
                this.getPrincipalController().showAlert(Alert.AlertType.INFORMATION, ScreenConstants.SUCCESS, "Rating added successfully");
            }
        });
    }

    @FXML
    private void addRating(ActionEvent actionEvent) {
        ratingsAddViewModel.addRating(id.getText(), idReader.getText(), idArticle.getText(), rating.getText());
    }
}

