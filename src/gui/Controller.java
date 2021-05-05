package gui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class Controller {

	// cadastro de alunos.

	private String url = "jdbc:mysql://{IP DO BANCO DE DADOS MYSQL}/academia";
	private String usuarioSQL = "{USUÁRIO DO BANCO DE DADOS}";
	private String senhaSQL = "{SENHA DO SEU BANCO DE DADOS}";

	Connection c = null;
	Statement s = null;
	PreparedStatement ps = null;

	@FXML
	private TextField nomeTextFieldCadastro;

	@FXML
	private TextField pesoTextFieldCadastro;

	@FXML
	private TextField alturaTextFieldCadastro;

	@FXML
	private TextField tipoDeAulaTextFieldCadastro;

	@FXML
	private Button cadastrarButtonCadastro;

	@FXML
	private Text statusTextCadastro;

	@FXML
	private TextField identificacaoTextFieldPesquisa;

	@FXML
	private TextArea textAreaPesquisa;

	@FXML
	private Button PesquisarButtonPesquisa;

	@FXML
	public void cadastrarButtonCadastroAction() {
		conectar();

		try {
			ps = c.prepareStatement("INSERT INTO Alunos (id, nome, peso, altura, tipo_aula) VALUES (?, ?, ?, ?, ?)");

			Random random = new Random();

			Integer posicaoIdenticacao = random.nextInt(99);

			String id = String.valueOf(posicaoIdenticacao);
			String nome = nomeTextFieldCadastro.getText();
			String peso = pesoTextFieldCadastro.getText();
			String altura = alturaTextFieldCadastro.getText();
			String tipoAula = tipoDeAulaTextFieldCadastro.getText();

			ps.setString(1, id);
			ps.setString(2, nome);
			ps.setString(3, peso);
			ps.setString(4, altura);
			ps.setString(5, tipoAula);

			ps.executeUpdate();

			statusTextCadastro.setText("Aluno(a) cadastrado(a)! A identificação de " + nome + " É " + id + "!");
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			statusTextCadastro.setText("Aluno(a) não cadastrado(a)! devido a algum erro no sistema!");
		}
	}

	@FXML
	public void PesquisarButtonPesquisaAction() throws Exception {
		conectar();

		textAreaPesquisa.clear();

		String id = identificacaoTextFieldPesquisa.getText();

		s = c.createStatement();
		ResultSet rs = s.executeQuery("SELECT * FROM Alunos WHERE id = " + id + "");

		while (rs.next()) {

			String dadosAlunosBancoDeDados = "ID: " + rs.getString("id") + "\n" + "NOME: " + rs.getString("nome") + "\n"
					+ "PESO: " + rs.getString("peso") + "\n" + "ALTURA: " + rs.getString("altura") + "\n"
					+ "MODALIDADE: " + rs.getString("tipo_aula");

			textAreaPesquisa.setEditable(false);
			textAreaPesquisa.appendText(dadosAlunosBancoDeDados);

		}
	}

	public void conectar() {

		try {
			c = DriverManager.getConnection(url, usuarioSQL, senhaSQL);
		} catch (SQLException e) {
			e.getMessage();
		}
	}

}
