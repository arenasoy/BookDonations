package bibliotecario;

import cidade.Cidade;
import endereco.Endereco;

public class Bibliotecario {

	private Integer cib;
	private String senha;
	private String nome;
	private Cidade cidade;
	private Endereco endereco;
	
	public Bibliotecario (Integer cib, String senha, String nome, Cidade cidade, Endereco endereco) throws Exception {
		
		if (cib == null) {
			throw new Exception("CIB � obrigat�rio");
		}
		
		if (senha == null || senha.length() == 0 || senha.length() > 30) {
			throw new Exception("Senha � obrigat�ria e deve ter at� 30 caracteres");
		}
		
		if (nome == null || nome.length() == 0 || nome.length() > 50) {
			throw new Exception("Nome � obrigat�rio e deve ter at� 50 caracteres");
		}
		
		this.cib = cib;
		this.senha = senha;
		this.nome = nome;
		this.cidade = cidade;
		this.endereco = endereco;
		
	}

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
