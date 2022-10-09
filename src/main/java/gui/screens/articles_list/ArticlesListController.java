package gui.screens.articles_list;

import domain.modelo.Article;
import domain.modelo.ArticleType;
import gui.screens.common.BaseScreenController;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ArticlesListController extends BaseScreenController {

    @FXML
    private Label title;
    @FXML
    private TableView<Article> tableArticles;
    @FXML
    private TableColumn<Article, Integer> columnId;
    @FXML
    private TableColumn<Article, String> columnName;
    @FXML
    private TableColumn<Article, Integer> columnArticleType;
    @FXML
    private TableColumn<Article, Integer> columnNewspaperId;
    @FXML
    private MFXComboBox<ArticleType> cbArticleType;
    @FXML
    private MFXButton btnFilter;

    private final ArticlesListViewModel articlesListViewModel;

    @Inject
    public ArticlesListController(ArticlesListViewModel articlesListViewModel) {
        this.articlesListViewModel = articlesListViewModel;
    }

    public void initialize() {
        title.setText("List all Articles");

        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnArticleType.setCellValueFactory(new PropertyValueFactory<>("articleType"));
        columnNewspaperId.setCellValueFactory(new PropertyValueFactory<>("newspaperId"));
        tableArticles.setItems(articlesListViewModel.getObservableArticles());
        cbArticleType.setItems(articlesListViewModel.getObservableArticleTypes());

        articlesListViewModel.loadArticles();
        articlesListViewModel.loadArticleTypes();

        articlesListViewModel.getState().addListener((observableValue, oldState, newState) -> {
            if (newState.getError() != null) {
                getPrincipalController().showAlert(Alert.AlertType.ERROR, "Error", newState.getError());
            }
        });
    }

    @FXML
    private void filter(ActionEvent actionEvent) {
        articlesListViewModel.filter(cbArticleType.getValue());
        articlesListViewModel.clearState();
    }

}
