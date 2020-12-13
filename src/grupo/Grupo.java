package grupo;

import administrador.Administrador;

public class Grupo {

	private String nome;
	private Tipo tipo;
	private double pontuacaoMinima;
	private Administrador administrador;

	public Grupo(String nome, Tipo tipo, double pontuacaoMinima) {
		this.nome = nome;
		this.tipo = tipo;
		this.pontuacaoMinima = pontuacaoMinima;
		this.administrador = null;
	}

	public Grupo(String nome, Tipo tipo) {
		this.nome = nome;
		this.tipo = tipo;
	}

	public Grupo(String nome, Tipo tipo, double pontuacaoMinima, Administrador administrador) throws Exception {

		if (nome == null || nome.length() == 0 || nome.length() > 20) {
			throw new Exception("Nome � obrigat�rio e deve ter at� 20 caracteres");
		}

		if (tipo == null) {
			throw new Exception("Tipo � obrigat�rio");
		}

		if (pontuacaoMinima < 0) {
			throw new Exception("Pontua��o m�nima � obrigat�ria");
		}

		if (administrador == null) {
			throw new Exception("Administrador � obrigat�rio");
		}

		this.nome = nome;
		this.tipo = tipo;
		this.pontuacaoMinima = pontuacaoMinima;
		this.administrador = administrador;
	}

	public void print() {

		System.out.println(
				"Nome: " + nome + "\nTipo: " + tipo.toString() + " Pontua��o m�nima: " + pontuacaoMinima);
		if (administrador != null) {
			System.out.println("Administrador:");
			administrador.print();
		}
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public double getPontuacaoMinima() {
		return pontuacaoMinima;
	}

	public void setPontuacaoMinima(double pontuacaoMinima) {
		this.pontuacaoMinima = pontuacaoMinima;
	}

	public Administrador getAdministrador() {
		return administrador;
	}

	public void setAdministrador(Administrador administrador) {
		this.administrador = administrador;
	}

}
