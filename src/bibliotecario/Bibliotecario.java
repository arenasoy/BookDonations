package bibliotecario;

import cidade.Cidade;
import endereco.Endereco;

public class Bibliotecario {

	private Integer cib;
	private String senha;
	private String nome;
	private Cidade cidade;
	private Endereco endereco;

	public Integer getCib() {
		return cib;
	}

	public void setCib(Integer cib) {
		this.cib = cib;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
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
