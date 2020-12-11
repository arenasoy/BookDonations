package emprestimo;

import java.time.LocalDate;

import bibliotecario.Bibliotecario;
import livro.Livro;
import usuario.fisica.donatario.Donatario;

public class Emprestimo {

	private Donatario donatario;
	private Livro livro;
	private LocalDate dataRetirada;
	private LocalDate dataDevolucao;
	private Bibliotecario bibliotecario;

	public Donatario getDonatario() {
		return donatario;
	}

	public void setDonatario(Donatario donatario) {
		this.donatario = donatario;
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public LocalDate getDataRetirada() {
		return dataRetirada;
	}

	public void setDataRetirada(LocalDate dataRetirada) {
		this.dataRetirada = dataRetirada;
	}

	public LocalDate getDataDevolucao() {
		return dataDevolucao;
	}

	public void setDataDevolucao(LocalDate dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}

	public Bibliotecario getBibliotecario() {
		return bibliotecario;
	}

	public void setBibliotecario(Bibliotecario bibliotecario) {
		this.bibliotecario = bibliotecario;
	}

}
