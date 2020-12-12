package temporada;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Temporada {

	private LocalDate dataInicial;
	private int duracao;

	public Temporada(LocalDate dataInicial, int duracao) throws Exception {

		if (dataInicial == null) {
			throw new Exception("Data inicial é obrigatória");
		}

		if (duracao <= 0) {
			throw new Exception("Duração é obrigatória");
		}

		this.dataInicial = dataInicial;
		this.duracao = duracao;
	}

	public Temporada(LocalDate dataInicial, int duracao, boolean fromDatabase) {
		if (fromDatabase) {
			this.dataInicial = dataInicial;
			this.duracao = duracao;
		}
	}

	public void print() {
		System.out.println(
				"Início: " + dataInicial.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " Duração: " + duracao);
	}

	public LocalDate getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(LocalDate dataInicial) {
		this.dataInicial = dataInicial;
	}

	public int getDuracao() {
		return duracao;
	}

	public void setDuracao(int duracao) {
		this.duracao = duracao;
	}

}
