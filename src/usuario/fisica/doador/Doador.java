package usuario.fisica.doador;

import java.util.List;

import cidade.Cidade;
import endereco.Endereco;
import livro.Livro;
import usuario.Tipo;
import usuario.fisica.PessoaFisica;

public class Doador extends PessoaFisica {

	public Doador(String email, String nome, String cpf, String rg, String telefone) throws Exception {
		super(email, nome, cpf, rg, telefone);
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
