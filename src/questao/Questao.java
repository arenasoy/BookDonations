package questao;

import emprestimo.Emprestimo;

public class Questao {

	private Emprestimo emprestimo;
	private int numeroIdentificador;
	private int nivel;
	private String pergunta;
	private String solucao;
	private double pontuacao;

	public Questao(Emprestimo emprestimo, int numeroIdentificador, int nivel, String pergunta, String solucao,
			double pontuacao) throws Exception {
		
		if (emprestimo == null) {
			throw new Exception("Empr�stimo � obrigat�rio");
		}
		
		if (numeroIdentificador <= 0) {
			throw new Exception("N�mero identificador � obrigat�rio");
		}
		
		if (nivel <= 0) {
			throw new Exception("N�vel � obrigat�rio");
		}
		
		if (pergunta == null || pergunta.length() == 0 || pergunta.length() > 100) {
			throw new Exception("Pergunta � obrigat�ria e deve ter at� 100 caracteres");
		}
		
		if (solucao == null || solucao.length() == 0 || solucao.length() > 150) {
			throw new Exception("Solu��o � obrigat�ria e deve ter at� 150 caracteres");
		}
		
		if (pontuacao < 0) {
			throw new Exception("Pontua��o � obrigat�ria");
		}
		
		this.emprestimo = emprestimo;
		this.numeroIdentificador = numeroIdentificador;
		this.nivel = nivel;
		this.pergunta = pergunta;
		this.solucao = solucao;
		this.pontuacao = pontuacao;
	}

	
	public Questao(int numeroIdentificador, int nivel, String pergunta, String solucao, double pontuacao) {
		this.numeroIdentificador = numeroIdentificador;
		this.nivel = nivel;
		this.pergunta = pergunta;
		this.solucao = solucao;
		this.pontuacao = pontuacao;
	}



	public void print() {

		System.out.println("N�mero identificador: " + numeroIdentificador + "\nNivel: " + nivel + " Pergunta: "
				+ pergunta + "\nSolu��o: " + solucao + " Pontua��o: " + pontuacao);

		if (emprestimo != null) {
			System.out.println("Empr�stimo");
			emprestimo.print();
		}
	}

	public Emprestimo getEmprestimo() {
		return emprestimo;
	}

	public void setEmprestimo(Emprestimo emprestimo) {
		this.emprestimo = emprestimo;
	}

	public int getNumeroIdentificador() {
		return numeroIdentificador;
	}

	public void setNumeroIdentificador(int numeroIdentificador) {
		this.numeroIdentificador = numeroIdentificador;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public String getPergunta() {
		return pergunta;
	}

	public void setPergunta(String pergunta) {
		this.pergunta = pergunta;
	}

	public String getSolucao() {
		return solucao;
	}

	public void setSolucao(String solucao) {
		this.solucao = solucao;
	}

	public double getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(double pontuacao) {
		this.pontuacao = pontuacao;
	}

}
