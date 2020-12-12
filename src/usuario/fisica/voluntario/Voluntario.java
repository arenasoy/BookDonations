package usuario.fisica.voluntario;

import java.util.List;

import cidade.Cidade;
import endereco.Endereco;
import livro.Livro;
import usuario.Tipo;
import usuario.fisica.PessoaFisica;

public class Voluntario extends PessoaFisica {

	private double pontuacao;
	private List<Livro> livros;

	public Voluntario(String email, String nome, String cpf, String rg, String telefone) throws Exception {
		super(email, nome, cpf, rg, telefone);
		// TODO Auto-generated constructor stub
	}

	public Voluntario(String email, double pontuacao) {
		this.email = email;
		this.pontuacao = pontuacao;
	}

	public void print() {
		super.print();
		System.out.println("Pontuação: " + pontuacao);

		if (livros != null && livros.size() > 0) {
			System.out.println("Livros: ");
			for (Livro livro : livros) {
				livro.print();
			}
		}
	}

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
