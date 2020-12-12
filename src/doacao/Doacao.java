package doacao;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import bibliotecario.Bibliotecario;
import livro.Livro;

public class Doacao {

	private Livro livro;
	private Instant dataHora;
	private double pontuacao;
	private Bibliotecario bibliotecario;

	public Doacao(Livro livro, Instant dataHora, double pontuacao, Bibliotecario bibliotecario) throws Exception {

		if (livro == null) {
			throw new Exception("Livro é obrigatório");
		}

		if (dataHora == null) {
			throw new Exception("Data é obrigatória");
		}

		if (pontuacao < 0) {
			throw new Exception("Pontuação é obrigatória");
		}

		this.livro = livro;
		this.dataHora = dataHora;
		this.pontuacao = pontuacao;
		this.bibliotecario = bibliotecario;
	}

	public Doacao(Instant dataHora, double pontuacao) {
		this.dataHora = dataHora;
		this.pontuacao = pontuacao;
		this.bibliotecario = null;
		this.livro = null;
	}

	public void print() {

		System.out.println("Data: "
				+ DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").withZone(ZoneId.systemDefault()).format(dataHora)
				+ " Pontuação: " + pontuacao);		
		if (livro != null) {
			System.out.println("Livro: ");
			livro.print();
		}
		
		if (bibliotecario != null) {
			System.out.println("Bibliotecário");
			bibliotecario.print();
		}
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public Instant getDataHora() {
		return dataHora;
	}

	public void setDataHora(Instant dataHora) {
		this.dataHora = dataHora;
	}

	public double getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(double pontuacao) {
		this.pontuacao = pontuacao;
	}

	public Bibliotecario getBibliotecario() {
		return bibliotecario;
	}

	public void setBibliotecario(Bibliotecario bibliotecario) {
		this.bibliotecario = bibliotecario;
	}

}
