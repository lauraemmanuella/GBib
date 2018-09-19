package controle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import persistencia.AnotacaoDAO;
import persistencia.ExemplarDAO;

public class ControladorPrincipal implements Initializable {
	private AnotacaoDAO anotacaoDAO = new AnotacaoDAO();
	
	@FXML
	private JFXButton btInicio, btAddLivro, btBuscarLivros, btCatalogo, btUsuarios, btEmprestimos, btAjuda, btSair, btGravar, brApagar;
	
	@FXML
	private BorderPane borderPrincipal;
	
	@FXML
	private AnchorPane anchorInicial;
	
	@FXML
	private Label labelPrincipal;
	
	@FXML
	private JFXTextArea taAnotacoes;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	
		taAnotacoes.setText(anotacaoDAO.listAnotacao());
	}
	
	@FXML
	private void inicio() {
		borderPrincipal.setCenter(anchorInicial);
		labelPrincipal.setText("BIBLIOTECA");
	}
	
	@FXML
	private void addLivro() {
		try {
			Parent addLivro =  FXMLLoader.load(getClass().getResource("/visao/AddLivro.fxml"));
			borderPrincipal.setCenter(addLivro);
			labelPrincipal.setText("ADICIONAR LIVRO");
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	@FXML
	private void buscarLivros() {
		try {
			Parent buscarLivro = FXMLLoader.load(getClass().getResource("/visao/BuscarLivro.fxml"));
			borderPrincipal.setCenter(buscarLivro);
			labelPrincipal.setText("BUSCAR LIVRO");
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	@FXML
	private void gerenciarUsuarios() {
		try {
			Parent addLivro = FXMLLoader.load(getClass().getResource("/visao/Usuarios.fxml"));
			borderPrincipal.setCenter(addLivro);
			labelPrincipal.setText("GERENCIAR USUÁRIOS");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void gerenciarEmprestimos() {
		try {
			Parent emprestimo = FXMLLoader.load(getClass().getResource("/visao/Emprestimo.fxml"));
			borderPrincipal.setCenter(emprestimo);
			labelPrincipal.setText("GERENCIAR EMPRÉSTIMOS");
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	@FXML
	private void gerarCatalogo() throws FileNotFoundException, DocumentException {
		ExemplarDAO exemplarDAO = new ExemplarDAO();
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));     
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("PDF Files", "*.pdf"));
		File file = fileChooser.showSaveDialog(this.btCatalogo.getScene().getWindow());
		
		if(file != null) {
			Document document = new Document();
                        PdfWriter.getInstance(document, new FileOutputStream(file));
                        document.open();

                        LocalDate hoje = LocalDate.now();
                        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        String data = hoje.format(formatador); 

                        Paragraph p1 = new Paragraph("Catálogo Biblioteca\nGerado em "+ data+"\n\n");
                        p1.setAlignment(Element.ALIGN_CENTER);

                        document.add(p1);
                        document.add(exemplarDAO.geraTabela());

                        document.close();
                 }
	}

	@FXML
	private void ajuda() {
		try {
			Parent ajuda = FXMLLoader.load(getClass().getResource("/visao/Ajuda.fxml"));
			borderPrincipal.setCenter(ajuda);
			labelPrincipal.setText("AJUDA");
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	@FXML
	private void sair() {
		System.exit(0);
	}
	
	@FXML
	private void gravarAnotacao() {
		anotacaoDAO.insertAnotacao(taAnotacoes.getText());
	}
	
	@FXML
	private void apagarAnotacao() {
		anotacaoDAO.deleteAnotacao();
		taAnotacoes.clear();
	}
}
