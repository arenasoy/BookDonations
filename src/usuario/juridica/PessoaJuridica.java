package usuario.juridica;

import java.util.List;

import cidade.Cidade;
import endereco.Endereco;
import livro.Livro;
import usuario.Tipo;
import usuario.Usuario;

public class PessoaJuridica extends Usuario {

	private String razaoSocial;
	private String nomeFantasia;
	private String cnpj;
	private String inscricaoEstadual;
	private List<Livro> livros;

	public PessoaJuridica(String email, String senha, Cidade cidade, Endereco endereco, Tipo tipo, String razaoSocial, String nomeFantasia, String cnpj, String inscricaoEstadual) throws Exception {
		super(email, senha, cidade, endereco, tipo);

		this.razaoSocial = razaoSocial;
		this.nomeFantasia = nomeFantasia;
		this.cnpj = cnpj;
		this.inscricaoEstadual = inscricaoEstadual;
	}

	public PessoaJuridica(String email, String razaoSocial, String nomeFantasia, String cnpj,
			String inscricaoEstadual) {
		this.email = email;
		this.razaoSocial = razaoSocial;
		this.nomeFantasia = nomeFantasia;
		this.cnpj = cnpj;
		this.inscricaoEstadual = inscricaoEstadual;
	}

	public void print() {
		super.print();
		System.out.println("Razão social: " + this.razaoSocial + "\nCNPJ: " + this.cnpj + " Inscrição estadual: "
				+ this.inscricaoEstadual + "\nNome fantasia: " + this.nomeFantasia);
		
		if (livros != null && this.livros.size() > 0) {
			System.out.println("Livros doados: ");
			for (Livro livro : livros) {
				livro.print();
			}
		}
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}

	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}

	public List<Livro> getLivros() {
		return livros;
	}

	public void setLivros(List<Livro> livros) {
		this.livros = livros;
	}

}
