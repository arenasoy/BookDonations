package usuario.fisica.voluntario;

import java.util.List;

import cidade.Cidade;
import endereco.Endereco;
import livro.Livro;
import usuario.Tipo;
import usuario.fisica.PessoaFisica;

public class Voluntario extends PessoaFisica {

	public Voluntario(String email, String senha, Cidade cidade, Endereco endereco, Tipo tipo) throws Exception {
		super(email, senha, cidade, endereco, tipo);
		// TODO Auto-generated constructor stub
	}

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
