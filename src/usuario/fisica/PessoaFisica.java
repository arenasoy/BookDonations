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

	public PessoaFisica(String email, String nome, String cpf, String rg, String telefone) {
		this.email = email;
		this.nome = nome;
		this.cpf = cpf;
		this.rg = rg;
		this.telefone = telefone;
		this.tipo = Tipo.PESSOA_FISICA;
	}
	
	public PessoaFisica() {
		
	}
	
	public PessoaFisica(String email, String senha, Cidade cidade, Endereco endereco, Tipo tipo, String nome,
			String cpf, String rg, String telefone, List<Perfil> perfis) throws Exception {
		super(email, senha, cidade, endereco, tipo);

		if (nome == null || nome.length() == 0 || nome.length() > 50) {
			throw new Exception("Nome é obrigatório e deve ter até 50 caracteres");
		}
		
		if (cpf == null || cpf.length() != 14) {
			throw new Exception("CPF é obrigatório e deve ser no formato '000.000.000-00'");
		}
		
		//TODO validar cpf
		
		if (rg != null && rg.length() > 9) {
			throw new Exception("RG deve ter até 9 caracteres");
		}
		
		if (telefone != null && telefone.length() > 15) {
			throw new Exception("O telefone deve ter até 15 caracteres");
		}
		
		this.nome = nome;
		this.cpf = cpf;
		this.rg = rg;
		this.telefone = telefone;
		this.perfis = perfis;
 	}
	
	public PessoaFisica(String email, String senha, Tipo tipo, String nome,
			String cpf, String rg, String telefone) {
		super(email, senha, tipo);
		
		this.nome = nome;
		this.cpf = cpf;
		this.rg = rg;
		this.telefone = telefone;
 	}

	public void print() {
		super.print();
		System.out.println("Nome: " + nome + "\nCPF: " + cpf + " RG: " + rg + "\nTelefone: " + telefone);
		if (perfis.size() > 0) {
			System.out.println("Perfis: ");
			for (Perfil perfil : perfis) {
				System.out.print(perfil + " ");
			}
		}
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
