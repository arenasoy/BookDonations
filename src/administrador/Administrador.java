package administrador;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import livro.Livro;

public class Administrador {

	private String email;
	private String senha;
	private String nome;
	private LocalDate dataRegistro;
	private List<Livro> livros;

	public Administrador(String email, String senha, String nome) throws Exception {

		if (email == null || email.length() == 0 || email.length() > 100) {
			throw new Exception("E-mail é obrigatório e deve ter até 100 caracteres");
		}

		Pattern pattern = Pattern.compile("^.+@.+\\..+$");
		Matcher matcher = pattern.matcher(email);

		if (!matcher.matches()) {
			throw new Exception("E-mail inválido");
		}

		if (senha == null || senha.length() == 0 || senha.length() > 20) {
			throw new Exception("Senha é obrigatória e deve ter até 20 caracteres");
		}

		if (nome == null || nome.length() == 0 || nome.length() > 50) {
			throw new Exception("Nome é obrigatório e deve ter até 50 caracteres");
		}

		this.email = email;
		this.senha = senha;
		this.nome = nome;
		this.dataRegistro = LocalDate.now();
		this.livros = new ArrayList<Livro>();
	}

	public Administrador(String email, String senha, String nome, LocalDate dataRegistro) {

		this.email = email;
		this.senha = senha;
		this.nome = nome;
		this.dataRegistro = dataRegistro;

	}

	public void print() {

		System.out.println("E-mail: " + this.email + "\nNome: " + this.nome + "\nData de registro: "
				+ this.dataRegistro.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		
		if (this.livros != null && this.livros.size() > 0) {
			System.out.println("Lista de livros:");
			for (Livro livro : livros) {
				livro.print();
			}
		}

	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getDataRegistro() {
		return dataRegistro;
	}

	public void setDataRegistro(LocalDate dataRegistro) {
		this.dataRegistro = dataRegistro;
	}

	public List<Livro> getLivros() {
		return livros;
	}

	public void setLivros(List<Livro> livros) {
		this.livros = livros;
	}

}
