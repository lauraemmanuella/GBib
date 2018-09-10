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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Tooltip;
import modelo.Autor;
import modelo.Titulo;
import persistencia.AutorDAO;
import persistencia.ExemplarDAO;
import persistencia.TituloDAO;

public class ControladorAddLivro implements Initializable {
	private AutorDAO autorDAO = new AutorDAO();
	
	private TituloDAO tituloDAO = new TituloDAO();
	
	private ExemplarDAO exemplarDAO = new ExemplarDAO();
	
	private ObservableList<Autor> autores = FXCollections.observableArrayList();
	
	private ObservableList<Titulo> titulos = FXCollections.observableArrayList();

	@FXML
	private JFXComboBox<Autor> cbAutor;
	
	@FXML
	private JFXComboBox<Titulo> cbTitulo;
	
	@FXML
	private JFXButton btAddAutor, btAddTitulo, btEditAutor,btEditTitulo, btDelAutor, btDelTitulo, btAddExemplar, btCancelarAdd;
	
	@FXML
	private Label labelConfirmacao;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		refreshAutor();
		refreshTitulo(null);
		
		cbAutor.setOnAction(new EventHandler<ActionEvent>() { 

			@Override
			public void handle(ActionEvent arg0) {
				Autor a = cbAutor.getSelectionModel().getSelectedItem();
				refreshTitulo(a);
			}
			
		});
		
		btAddAutor.setTooltip(new Tooltip("Adicionar Autor"));
		btEditAutor.setTooltip(new Tooltip("Atualizar Autor"));
		btDelAutor.setTooltip(new Tooltip("Remover Autor"));
		
		btAddTitulo.setTooltip(new Tooltip("Adicionar Título"));
		btEditTitulo.setTooltip(new Tooltip("Atualizar Título"));
		btDelTitulo.setTooltip(new Tooltip("Remover Título"));
		
		btAddExemplar.setTooltip(new Tooltip("Adicionar Exemplar"));
		btCancelarAdd.setTooltip(new Tooltip("Limpar Campos"));
	}
	
	public void refreshAutor() {
		autores.clear();
		autores.addAll(autorDAO.listAutor());
		cbAutor.setItems(autores);
		new ComboBoxAutoComplete<Autor>(cbAutor);
	}
	
	public void refreshTitulo(Autor a) {
		titulos.clear();
		titulos.addAll(tituloDAO.listTitulo(a));
		cbTitulo.setItems(titulos);
		new ComboBoxAutoComplete<Titulo>(cbTitulo);
	}

	@FXML
	public void addAutor(){
		TextInputDialog novoAutor = new TextInputDialog();
		novoAutor.setTitle("Cadastrar Autor");
		novoAutor.setHeaderText("Cadastro de autor");
		novoAutor.setContentText("Informe o nome do autor");
		Optional<String> resultado = novoAutor.showAndWait();
		if (resultado.isPresent() && !novoAutor.getResult().isEmpty()) {
			if(autorDAO.validaNomeAutor(novoAutor.getResult())) {
				char letraAutor = novoAutor.getResult().charAt(0);
				letraAutor = Character.toUpperCase(letraAutor);
				String ultimoCodigo = autorDAO.codAutor(letraAutor);
				int num = 0;
				if(ultimoCodigo != null) {
					String[] numAutor = ultimoCodigo.split("[A-Z]");
					num = Integer.parseInt(numAutor[1]);
				}
				String codAutor = letraAutor + Integer.toString((num + 1));
				Autor a = new Autor(novoAutor.getResult(), codAutor);
				autorDAO.insertAutor(a);
				refreshAutor();
			}else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Atenção");
				alert.setHeaderText("Nome de autor repetido");
				alert.setContentText("Esse autor já está cadastrado");

				alert.showAndWait();
			}
		}
	}
	
	@FXML
	public void addTitulo() {
		if(cbAutor.getSelectionModel().isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Atenção");
			alert.setHeaderText("Informe o autor do título");
			alert.setContentText("Antes de informar o título do livro, informe o autor do título");

			alert.showAndWait();
		} else {
			TextInputDialog novoTitulo = new TextInputDialog();
			novoTitulo.setTitle("Cadastrar Titulo");
			novoTitulo.setHeaderText("Cadastro de título");
			novoTitulo.setContentText("Informe o nome do título");
			Optional<String> resultado = novoTitulo.showAndWait();
			if (resultado.isPresent() && !novoTitulo.getResult().isEmpty()) {
				if(tituloDAO.validaNomeTitulo(novoTitulo.getResult(), cbAutor.getSelectionModel().getSelectedItem().getIdAutor())) {
					String nomeTitulo = novoTitulo.getResult();
					Autor a = cbAutor.getSelectionModel().getSelectedItem();	
					String ultimoCodigo = tituloDAO.codTitulo(a.getNomeAutor());
					int num = 0;
					if(ultimoCodigo != null) {
						String[] numTitulo = ultimoCodigo.split("-");
						num = Integer.parseInt(numTitulo[1]);
					}
					String codTitulo = a.getCodAutor() + "-" + Integer.toString(num + 1);
					Titulo t = new Titulo(nomeTitulo, codTitulo, a);
					tituloDAO.insertTitulo(t);
					refreshTitulo(a);
				}else {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Atenção");
					alert.setHeaderText("Nome de título repetido");
					alert.setContentText("Esse título já está cadastrado para esse autor");

					alert.showAndWait();
				}
			}
		}
	}
	
	@FXML
	public void addExemplar() {
		if(cbTitulo.getSelectionModel().isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Atenção");
			alert.setHeaderText("Informe o título do exemplar a ser cadastrado");
			alert.setContentText("Informe o título do exemplar a ser cadastrado");

			alert.showAndWait();
		} else {
			Titulo t = cbTitulo.getSelectionModel().getSelectedItem();
			String ultimoCodigo = exemplarDAO.codExemplar(t.getIdTitulo());
			int num = 0;
			if(ultimoCodigo != null) {
				String[] numExemplar = ultimoCodigo.split("-");
				num = Integer.parseInt(numExemplar[2]);
			}
			String codExemplar = t.getCodTitulo() + "-" + (num+1);
			exemplarDAO.insertExemplar(codExemplar, t.getIdTitulo());
			labelConfirmacao.setText("EXEMPLAR "+ codExemplar + " CADASTRADO COM SUCESSO");
			labelConfirmacao.setVisible(true);
		}
		
	}
	
	@FXML
	public void editAutor() {
		if(cbAutor.getSelectionModel().isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Atenção");
			alert.setHeaderText("Editar autor");
			alert.setContentText("Informe qual autor deseja editar");
			alert.showAndWait();
		} else {
			TextInputDialog editarAutor = new TextInputDialog(cbAutor.getSelectionModel().getSelectedItem().getNomeAutor());
			editarAutor.setTitle("Editar autor");
			editarAutor.setHeaderText("Atualizar nome de autor");
			editarAutor.setContentText("Nome do autor");
			Optional<String> resultado = editarAutor.showAndWait();
			if (resultado.isPresent() && !editarAutor.getResult().isEmpty()) {
				String novoNome = editarAutor.getResult();
				autorDAO.updateAutor(novoNome, cbAutor.getSelectionModel().getSelectedItem());
				refreshAutor();
			}
		}
	}
	
	@FXML
	public void editTitulo() {
		if(cbTitulo.getSelectionModel().isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Atenção");
			alert.setHeaderText("Editar título");
			alert.setContentText("Informe qual título deseja editar");
			alert.showAndWait();
		} else {
			TextInputDialog editarTitulo = new TextInputDialog(cbTitulo.getSelectionModel().getSelectedItem().getNomeTitulo());
			editarTitulo.setTitle("Editar título");
			editarTitulo.setHeaderText("Atualizar nome de título");
			editarTitulo.setContentText("Nome do título");
			Optional<String> resultado = editarTitulo.showAndWait();
			if (resultado.isPresent() && !editarTitulo.getResult().isEmpty()) {
				String novoNome = editarTitulo.getResult();
				tituloDAO.updateTitulo(novoNome, cbTitulo.getSelectionModel().getSelectedItem());
				Autor a = cbAutor.getSelectionModel().getSelectedItem();
				refreshTitulo(a);
			}
		}
	}
	
	@FXML
	public void delAutor() {
		if (cbAutor.getSelectionModel().isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Atenção");
			alert.setHeaderText("Remover autor");
			alert.setContentText("Informe qual autor deseja remover");
			alert.showAndWait();
		} else {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmação");
			alert.setHeaderText("Confirmar exclusão de cadastro");
			alert.setContentText("Tem certeza que deseja excluir o cadastro?");
			
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				autorDAO.deleteAutor(cbAutor.getSelectionModel().getSelectedItem());
				refreshAutor();
			}
		}
	}
	
	@FXML
	public void delTitulo() {
		if(cbTitulo.getSelectionModel().isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Atenção");
			alert.setHeaderText("Remover título");
			alert.setContentText("Informe qual título deseja remover");
			alert.showAndWait();
		} else {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmação");
			alert.setHeaderText("Confirmar exclusão de cadastro");
			alert.setContentText("Tem certeza que deseja excluir o cadastro?");
			
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				tituloDAO.deleteTitulo(cbTitulo.getSelectionModel().getSelectedItem());
				Autor a = cbAutor.getSelectionModel().getSelectedItem();
				refreshTitulo(a);
			}
		}
	}
	
	@FXML
	public void cancelarAdd() {
		cbAutor.getSelectionModel().clearSelection();
		cbTitulo.getSelectionModel().clearSelection();
		labelConfirmacao.setVisible(false);
	}
}
