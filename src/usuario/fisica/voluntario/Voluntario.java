package usuario.fisica.voluntario;

import java.util.List;

import livro.Livro;
import usuario.fisica.PessoaFisica;

public class Voluntario extends PessoaFisica {

	private double pontuacao;
	private List<Livro> livros;

	public double getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(double pontuacao) {
		this.pontuacao = pontuacao;
	}

	public List<Livro> getLivros() {
		return livros;
	}

	public void setLivros(List<Livro> livros) {
		this.livros = livros;
	}

}
