package usuario.fisica.donatario;

import cidade.Cidade;
import endereco.Endereco;
import usuario.Tipo;
import usuario.fisica.PessoaFisica;

public class Donatario extends PessoaFisica {

	private double pontuacao;

	public Donatario(String email, String nome, String cpf, String rg, String telefone) throws Exception {
		super(email, nome, cpf, rg, telefone);
		// TODO Auto-generated constructor stub
	}

	public Donatario(String email, double pontuacao) {
		this.email = email;
		this.pontuacao = pontuacao;
	}

	public void print() {
		super.print();
		System.out.println("Pontua��o: " + pontuacao);
	}

	public double getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(double pontuacao) {
		this.pontuacao = pontuacao;
	}

}
