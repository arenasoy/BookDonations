package usuario.fisica;

import java.util.List;

import cidade.Cidade;
import endereco.Endereco;
import usuario.Tipo;
import usuario.Usuario;

public class PessoaFisica extends Usuario {

	private String nome;
	private String cpf;
	private String rg;
	private String telefone;
	private List<Perfil> perfis;
	
	public PessoaFisica(String email, String senha, Cidade cidade, Endereco endereco, Tipo tipo) throws Exception {
		super(email, senha, cidade, endereco, tipo);
		// TODO Auto-generated constructor stub
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public List<Perfil> getPerfis() {
		return perfis;
	}

	public void setPerfis(List<Perfil> perfis) {
		this.perfis = perfis;
	}

}
