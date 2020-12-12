package usuario.fisica.donatario;

import cidade.Cidade;
import endereco.Endereco;
import usuario.Tipo;
import usuario.fisica.PessoaFisica;

public class Donatario extends PessoaFisica {

	public Donatario(String email, String senha, Cidade cidade, Endereco endereco, Tipo tipo) throws Exception {
		super(email, senha, cidade, endereco, tipo);
		// TODO Auto-generated constructor stub
	}

	private double pontuacao;

	public double getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(double pontuacao) {
		this.pontuacao = pontuacao;
	}

}
