package controle;

import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.StringConverter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Emprestimo;
import modelo.Exemplar;
import modelo.Usuario;
import persistencia.EmprestimoDAO;
import persistencia.ExemplarDAO;
import persistencia.UsuarioDAO;

public class ControladorEmprestimo implements Initializable {
	private UsuarioDAO usuarioDAO = new UsuarioDAO();
	
	private ExemplarDAO exemplarDAO = new ExemplarDAO();
	
	private EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
	
	private ObservableList<Usuario> usuarios = FXCollections.observableArrayList();
	
	private ObservableList<Exemplar> exemplares = FXCollections.observableArrayList();
	
	private ObservableList<Emprestimo> emprestimos = FXCollections.observableArrayList();
	
	@FXML
	private JFXComboBox<Usuario> cbUsuario;
	
	@FXML
	private JFXComboBox<Exemplar> cbExemplar;
	
	@FXML
	private JFXDatePicker dtEmprestimo, dtDevolucao;
	
	@FXML
	private TableView<Emprestimo> tabela;
	
	@FXML 
	private TableColumn<Emprestimo, String> colunaUsuario;
	
	@FXML 
	private TableColumn<Emprestimo, String> colunaExemplar;
	
	@FXML 
	private TableColumn<Emprestimo, String> colunaDataEmprestimo;
	
	@FXML 
	private TableColumn<Emprestimo, String> colunaDataDevolucao;
	
	
	@FXML
	private JFXButton btBuscar, btAdd, btCancelar, btRemover;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		usuarios.addAll(usuarioDAO.listUsuario());
		cbUsuario.setItems(usuarios);
		new ComboBoxAutoComplete<Usuario>(cbUsuario);
		
		exemplares.addAll(exemplarDAO.listExemplar(null, null));
		cbExemplar.setItems(exemplares);
		new ComboBoxAutoComplete<Exemplar>(cbExemplar);

		emprestimos.addAll(emprestimoDAO.listEmprestimo());
		tabela.setItems(emprestimos);

		colunaUsuario.setCellValueFactory(new PropertyValueFactory<Emprestimo, String>("nomeUsuario"));
		colunaExemplar.setCellValueFactory(new PropertyValueFactory<Emprestimo, String>("codExemplar"));
		colunaDataEmprestimo.setCellValueFactory(new PropertyValueFactory<Emprestimo, String>("dtEmprestimo"));
		colunaDataDevolucao.setCellValueFactory(new PropertyValueFactory<Emprestimo, String>("dtDevolucao"));
		tabela.getSortOrder().add(colunaDataDevolucao);

		dtEmprestimo.setConverter(data());
		dtDevolucao.setConverter(data());
	}
	
	private StringConverter<LocalDate> data() {
		String pattern = "dd/MM/yyyy";
		StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

			@Override
			public String toString(LocalDate date) {
				if (date != null) {
					return dateFormatter.format(date);
				} else {
					return "";
				}
			}

			@Override
			public LocalDate fromString(String string) {
				if (string != null && !string.isEmpty()) {
					return LocalDate.parse(string, dateFormatter);
				} else {
					return null;
				}
			}
		};
		return converter;
	}
	
	
	private void refreshEmprestimo() {
		emprestimos.clear();
		emprestimos.addAll(emprestimoDAO.listEmprestimo());
		tabela.setItems(emprestimos);	
		tabela.getSortOrder().add(colunaDataDevolucao);
	}
	
	private void refreshEmprestimo(ArrayList<Emprestimo> lista) {
		emprestimos.clear();
		emprestimos.addAll(lista);
		tabela.setItems(emprestimos);	
		tabela.getSortOrder().add(colunaDataDevolucao);
	}
	
	@FXML
	public void buscarEmprestimo() {
		ArrayList<Emprestimo> lista;
		if (!cbUsuario.getSelectionModel().isEmpty()) {
			int idUsuario = cbUsuario.getSelectionModel().getSelectedItem().getIdUsuario();
			lista = emprestimoDAO.listEmprestimoUsuario(idUsuario);
		} else if (!cbExemplar.getSelectionModel().isEmpty()) {
			int idExemplar = exemplarDAO.idExemplar(cbExemplar.getSelectionModel().getSelectedItem().getCodExemplar());
			lista = emprestimoDAO.listEmprestimoExemplar(idExemplar);
		} else if (dtEmprestimo.getValue() != null) {
			Date dataEmprestimo = Date.valueOf(dtEmprestimo.getValue());
			lista = emprestimoDAO.listEmprestimoDataEmp(dataEmprestimo);
		} else if (dtDevolucao.getValue() != null) {
			Date dataDevolucao = Date.valueOf(dtDevolucao.getValue());
			lista = emprestimoDAO.listEmprestimoDataDev(dataDevolucao);
		}else {
			refreshEmprestimo();
			return;
		}
		refreshEmprestimo(lista);
	}
	
	@FXML
	public void addEmprestimo() {
		if (cbUsuario.getSelectionModel().isEmpty() || cbExemplar.getSelectionModel().isEmpty()
				|| dtEmprestimo.getValue() == null || dtDevolucao.getValue() == null) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Atenção");
			alert.setHeaderText("Campos em branco");
			alert.setContentText("Verifique se algum campo está em branco");
			alert.showAndWait();
		}else if(validaEmprestimo()){
			int idUsuario = cbUsuario.getSelectionModel().getSelectedItem().getIdUsuario();
			int idExemplar = exemplarDAO.idExemplar(cbExemplar.getSelectionModel().getSelectedItem().getCodExemplar());
			Date dataEmprestimo = Date.valueOf(dtEmprestimo.getValue());
			Date dataDevolucao = Date.valueOf(dtDevolucao.getValue());
			if(idExemplar == 0) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Atenção");
				alert.setHeaderText("Exemplar não identificado");
				alert.setContentText("Verifique se o código do exemplar está correto");
				alert.showAndWait();
			}else {
				Emprestimo e = new Emprestimo(idUsuario, idExemplar, dataEmprestimo, dataDevolucao);
				emprestimoDAO.insertEmprestimo(e);
				limparCampos();
				refreshEmprestimo();
			}
		}
		
	}
	
	private boolean validaEmprestimo() {
		boolean valida = true;
		for(Emprestimo e: emprestimos) {
			if(e.getNomeUsuario().equals(cbUsuario.getSelectionModel().getSelectedItem().getNomeUsuario())) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Atenção");
				alert.setHeaderText("Emprétimo não permitido");
				alert.setContentText("O usuário já possui um empréstimo ativo");
				alert.showAndWait();
				return false;
			}
			if(e.getCodExemplar().equals(cbExemplar.getSelectionModel().getSelectedItem().getCodExemplar().toUpperCase())){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Atenção");
				alert.setHeaderText("Emprétimo não permitido");
				alert.setContentText("O exemplar já está emprestado");
				alert.showAndWait();
				return false;
			}
		}
		return valida;
	}
	
	@FXML
	public void limparCampos() {
		cbUsuario.getSelectionModel().clearSelection();
		cbExemplar.getSelectionModel().clearSelection();
		dtDevolucao.setValue(null);
		dtDevolucao.getEditor().clear();
		dtEmprestimo.setValue(null);
		dtEmprestimo.getEditor().clear();
	}
	
	@FXML
	public void removerEmprestimo() {
		if(tabela.getSelectionModel().isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Atenção");
			alert.setHeaderText("Empréstimo não selecionado");
			alert.setContentText("Escolha um empréstimo para remover");
			alert.showAndWait();
		} else {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmação");
			alert.setHeaderText("Confirmar exclusão de cadastro");
			alert.setContentText("Tem certeza que deseja excluir o cadastro?");
			
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				emprestimoDAO.deleteEmprestimo(tabela.getSelectionModel().getSelectedItem());
				int indice = tabela.getSelectionModel().getSelectedIndex();
				tabela.getItems().remove(indice);
			}
		}
	}	

}
