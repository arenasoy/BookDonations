package bibliotecario;

import cidade.Cidade;
import endereco.Endereco;

public class Bibliotecario {

	private String cib;
	private String nome;
	private Cidade cidade;
	private Endereco endereco;

	public String getCib() {
		return cib;
	}

	public void setCib(String cib) {
		this.cib = cib;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

}
