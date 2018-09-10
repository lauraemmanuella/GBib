package controle;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Usuario;
import persistencia.UsuarioDAO;


public class ControladorUsuario implements Initializable{
	private Usuario usuarioEdit = new Usuario();
	
	private UsuarioDAO usuarioDAO = new UsuarioDAO();
	
	private ObservableList<Usuario> usuarios = FXCollections.observableArrayList();
	
	@FXML
	private TableView<Usuario> tabela;
	
	@FXML
	private TableColumn<Usuario, String> colunaNome;
	
	@FXML
	private TableColumn<Usuario, String> colunaTelefone;
	
	@FXML
	private TableColumn<Usuario, String> colunaEmail;
	
	@FXML
	private JFXTextField tfNome, tfTelefone, tfEmail;
	
	@FXML
	private JFXButton btAdd, btRemover, btEditar, btLimpar;
	
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		colunaNome.setCellValueFactory(new PropertyValueFactory<Usuario, String>("nomeUsuario"));
		colunaTelefone.setCellValueFactory(new PropertyValueFactory<Usuario, String>("telUsuario"));
		colunaEmail.setCellValueFactory(new PropertyValueFactory<Usuario, String>("emailUsuario"));
		
		refreshTabela();

		FilteredList<Usuario> filteredData = new FilteredList<>(usuarios, p -> true);
		
		tfNome.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(user -> {
				// If filter text is empty, display all users.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				if (user.getNomeUsuario().toUpperCase().contains(newValue.toUpperCase())) {
					return true;
				} 
				
				return false; 
			});
			
			tabela.setItems(filteredData);
		});
		
		tabela.setOnMouseClicked(event -> {
			usuarioEdit = tabela.getSelectionModel().getSelectedItem();
			tfNome.setText(usuarioEdit.getNomeUsuario());
			tfTelefone.setText(usuarioEdit.getTelUsuario());
			tfEmail.setText(usuarioEdit.getEmailUsuario());
		});
		
	}
	
	private void refreshTabela() {
		usuarios.clear();
		usuarios.addAll(usuarioDAO.listUsuario());
		tabela.setItems(usuarios);
	}
	
	private void limparTextFields() {
		tfNome.clear();
		tfTelefone.clear();
		tfEmail.clear();
	}
		
	@FXML
	private void addUsuario() {
		if(tfNome.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Atenção");
			alert.setHeaderText("Nome em branco");
			alert.setContentText("Informe o nome do usuário");
			alert.showAndWait();
		}else {
			Usuario u = new Usuario(tfNome.getText().toUpperCase(), tfTelefone.getText(), tfEmail.getText());
			if(!usuarios.contains(u)) {
				usuarioDAO.insertUsuario(u);
				refreshTabela();
			}else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Atenção");
				alert.setHeaderText("Nome repetido");
				alert.setContentText("Este nome de usuário já foi cadastrado");
				alert.showAndWait();
			}
			limparTextFields();
		}
	}
	
	
	@FXML
	private void removerUsuario() {
		if(tabela.getSelectionModel().isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Atenção");
			alert.setHeaderText("Usuário não escolhido");
			alert.setContentText("Escolha um usuário para remover");
			alert.showAndWait();
		} else {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmação");
			alert.setHeaderText("Confirmar exclusão de cadastro");
			alert.setContentText("Tem certeza que deseja excluir o cadastro?");
			
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				usuarioDAO.deleteUsuario(tabela.getSelectionModel().getSelectedItem().getIdUsuario());
				refreshTabela();
			} 
			limparTextFields();
		}
	}
	
	@FXML
	private void editarUsuario() {
		if(tfNome.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Atenção");
			alert.setHeaderText("Nome em branco");
			alert.setContentText("Informe o nome do usuário");
			alert.showAndWait();
		}else {
			usuarioEdit.setNomeUsuario(tfNome.getText());
			usuarioEdit.setTelUsuario(tfTelefone.getText());
			usuarioEdit.setEmailUsuario(tfEmail.getText());
			usuarioDAO.updateUsuario(usuarioEdit);
			refreshTabela();
			limparTextFields();
		}
	}
	
	@FXML
	private void limpar() {
		limparTextFields();
	}

}
