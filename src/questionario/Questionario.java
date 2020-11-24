package questionario;

import java.util.Date;

import livro.Livro;
import usuario.fisica.donatario.Donatario;

public class Questionario {

	private Donatario donatario;
	private Livro livro;
	private Date dataRetirada;
	private int numeroIdentificador;
	private String nivel;
	private String questao;
	private String resolucao;
	private double pontuacao;

	public Donatario getDonatario() {
		return donatario;
	}

	public void setDonatario(Donatario donatario) {
		this.donatario = donatario;
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public Date getDataRetirada() {
		return dataRetirada;
	}

	public void setDataRetirada(Date dataRetirada) {
		this.dataRetirada = dataRetirada;
	}

	public int getNumeroIdentificador() {
		return numeroIdentificador;
	}

	public void setNumeroIdentificador(int numeroIdentificador) {
		this.numeroIdentificador = numeroIdentificador;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	public String getQuestao() {
		return questao;
	}

	public void setQuestao(String questao) {
		this.questao = questao;
	}

	public String getResolucao() {
		return resolucao;
	}

	public void setResolucao(String resolucao) {
		this.resolucao = resolucao;
	}

	public double getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(double pontuacao) {
		this.pontuacao = pontuacao;
	}

}
