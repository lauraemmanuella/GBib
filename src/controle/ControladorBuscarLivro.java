package controle;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Autor;
import modelo.Exemplar;
import modelo.Titulo;
import persistencia.AutorDAO;
import persistencia.ExemplarDAO;
import persistencia.TituloDAO;

public class ControladorBuscarLivro implements Initializable {
	private AutorDAO autorDAO = new AutorDAO();
	
	private TituloDAO tituloDAO = new TituloDAO();
	
	private ExemplarDAO exemplarDAO = new ExemplarDAO();
	
	private ObservableList<Autor> autores = FXCollections.observableArrayList();
	
	private ObservableList<Titulo> titulos = FXCollections.observableArrayList();
	
	private ObservableList<Exemplar> exemplares = FXCollections.observableArrayList();
	
	@FXML
	private JFXComboBox<Autor> cbAutor;
	
	@FXML
	private JFXComboBox<Titulo> cbTitulo;
	
	@FXML
	private TableView<Exemplar> tabela;
	
	@FXML 
	private TableColumn<Exemplar, String> colunaCodExemplar;
	
	@FXML 
	private TableColumn<Exemplar, String> colunaNomeTitulo;
	
	@FXML 
	private TableColumn<Exemplar, String> colunaNomeAutor;
	
	@FXML
	private JFXButton btBuscar, btCancelar, btDeletar;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		autores.addAll(autorDAO.listAutor());
		cbAutor.setItems(autores);
		new ComboBoxAutoComplete<Autor>(cbAutor);
		
		refreshTitulo(null);
		
		cbAutor.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				Autor a = cbAutor.getSelectionModel().getSelectedItem();
				refreshTitulo(a);
			}
			
		});
	}
	
	public void refreshTitulo(Autor a) {
		titulos.clear();
		titulos.addAll(tituloDAO.listTitulo(a));
		cbTitulo.setItems(titulos);
		new ComboBoxAutoComplete<Titulo>(cbTitulo);
	}
	
	@FXML
	public void buscarExemplar() {
		exemplares.clear();
		Autor a = cbAutor.getSelectionModel().getSelectedItem();
		Titulo t = cbTitulo.getSelectionModel().getSelectedItem();
		exemplares.addAll(exemplarDAO.listExemplar(a, t));
		tabela.setItems(exemplares);
		colunaCodExemplar.setCellValueFactory(new PropertyValueFactory<Exemplar, String>("codExemplar"));
		colunaNomeTitulo.setCellValueFactory(new PropertyValueFactory<Exemplar, String>("nomeTitulo"));
		colunaNomeAutor.setCellValueFactory(new PropertyValueFactory<Exemplar, String>("nomeAutor"));
		tabela.getSortOrder().add(colunaCodExemplar);
	}
	
	@FXML
	public void cancelarBusca() {
		cbAutor.getSelectionModel().clearSelection();
		cbTitulo.getSelectionModel().clearSelection();
		exemplares.clear();
	}
	
	@FXML
	public void deletarExemplar() {
		if(tabela.getSelectionModel().isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Atenção");
			alert.setHeaderText("Exemplar não escolhido");
			alert.setContentText("Escolha um exemplar para remover");
			alert.showAndWait();
		}else {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmação");
			alert.setHeaderText("Confirmar exclusão de cadastro");
			alert.setContentText("Tem certeza que deseja excluir o cadastro?");
			
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				exemplarDAO.deleteExemplar(tabela.getSelectionModel().getSelectedItem());
				int indice = tabela.getSelectionModel().getSelectedIndex();
				tabela.getItems().remove(indice);
			}
		}
	}
	

}
