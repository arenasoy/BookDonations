package emprestimo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import bibliotecario.Bibliotecario;
import livro.Livro;
import usuario.fisica.donatario.Donatario;

public class Emprestimo {

	private Donatario donatario;
	private Livro livro;
	private LocalDate dataRetirada;
	private LocalDate dataDevolucao;
	private Bibliotecario bibliotecario;
	
	public void print() {
		
		if (donatario != null) {
			System.out.println("Donatário: ");
			donatario.print();
		}
		
		if (livro != null) {
			System.out.println("Livro: ");
			livro.print();
		}
		
		if (bibliotecario != null) {
			System.out.println("Bibliotecário: ");
			bibliotecario.print();
		}
		
		System.out.print("Data retirada: " + dataRetirada.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " Data devolução: ");
		if (dataDevolucao == null) {
			System.out.println(" - ");
		} else {
			System.out.println(dataDevolucao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		}
	}
	
	public Emprestimo(Donatario donatario, Livro livro, LocalDate dataRetirada, LocalDate dataDevolucao,
			Bibliotecario bibliotecario) throws Exception {
		
		if (donatario == null) {
			throw new Exception("Donatário é obrigatório");
		}
		
		if (livro == null) {
			throw new Exception("Livro é obrigatório");
		}
		
		if (bibliotecario == null) {
			throw new Exception("Bibliotecário é obrigatório");
		}
		
		if (dataRetirada == null) {
			throw new Exception("Data de retirada é obrigatória");
		}
		
		this.donatario = donatario;
		this.livro = livro;
		this.dataRetirada = dataRetirada;
		this.dataDevolucao = dataDevolucao;
		this.bibliotecario = bibliotecario;
	}

	public Emprestimo(LocalDate dataRetirada, LocalDate dataDevolucao) {
		this.dataRetirada = dataRetirada;
		this.dataDevolucao = dataDevolucao;
		this.donatario = null;
		this.bibliotecario = null;
		this.livro = null;
	}

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

	public LocalDate getDataRetirada() {
		return dataRetirada;
	}

	public void setDataRetirada(LocalDate dataRetirada) {
		this.dataRetirada = dataRetirada;
	}

	public LocalDate getDataDevolucao() {
		return dataDevolucao;
	}

	public void setDataDevolucao(LocalDate dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}

	public Bibliotecario getBibliotecario() {
		return bibliotecario;
	}

	public void setBibliotecario(Bibliotecario bibliotecario) {
		this.bibliotecario = bibliotecario;
	}

}
