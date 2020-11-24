package grupo;

import administrador.Administrador;

public class Grupo {

	private String nome;
	private String tipo;
	private double pontuacaoMinima;
	private Administrador administrador;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
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
