package usuario.fisica.doador;

import java.util.List;

import cidade.Cidade;
import endereco.Endereco;
import livro.Livro;
import usuario.Tipo;
import usuario.fisica.Perfil;
import usuario.fisica.PessoaFisica;

public class Doador extends PessoaFisica {

	private double pontuacao;
	private List<Livro> livros;
	
	public Doador(String email, String nome, String cpf, String rg, String telefone) throws Exception {
		super(email, nome, cpf, rg, telefone);
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

	public Doador(String email, double pontuacao) {
		this.email = email;
		this.pontuacao = pontuacao;
	}

	public Doador(String email, String senha, Cidade c, Endereco e, Tipo tipo, String nome, String cpf, String rg,
			String telefone, List<Perfil> perfis, double pontuacao) throws Exception {
		super(email, senha, c, e, tipo, nome, cpf, rg, telefone, perfis);
		this.pontuacao = pontuacao;
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
