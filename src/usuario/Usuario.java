package usuario;

import cidade.Cidade;
import endereco.Endereco;

public class Usuario {

	protected String email;
	protected String senha;
	protected Cidade cidade;
	protected Endereco endereco;
	protected Tipo tipo;
	
	public Usuario(String email, String senha, Cidade cidade, Endereco endereco, Tipo tipo) throws Exception {
		
		if (email == null || email.length() == 0 || email.length() > 100) {
			throw new Exception("E-mail é obrigatório e deve ter até 100 caracteres");
		}
		
		if (senha == null || senha.length() == 0 || senha.length() > 20) {
			throw new Exception("Senha é obrigatória e deve ter até 20 caracteres");
		}
		
		if (tipo == null) {
			throw new Exception("Tipo é obrigatório");
		}
		
		this.email = email;
		this.senha = senha;
		this.cidade = cidade;
		this.endereco = endereco;
		this.tipo = tipo;
		
	}
	
	public Usuario(String email, String senha, Tipo tipo) {
		
		this.email = email;
		this.senha = senha;
		this.tipo = tipo;
		
	}
	
	public void print() {
		System.out.println("E-mail: " + this.email + "\nTipo: " + this.tipo);
		
		if (this.cidade != null) {
			System.out.println("Cidade:");
			this.cidade.print();
		}
		
		if (this.endereco != null) {
			System.out.println("Endereço:");
			this.endereco.print();
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
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

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
}
