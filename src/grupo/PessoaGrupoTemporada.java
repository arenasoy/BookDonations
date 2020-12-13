package grupo;

import temporada.Temporada;
import usuario.fisica.PessoaFisica;

public class PessoaGrupoTemporada {

	private Grupo grupo;
	private Temporada temporada;
	private PessoaFisica pessoaFisica;
	
	public PessoaGrupoTemporada(Grupo grupo, Temporada temporada, PessoaFisica pessoaFisica) {
		this.grupo = grupo;
		this.temporada = temporada;
		this.pessoaFisica = pessoaFisica;
	}
	
	public void print() {
		
		grupo.print();
		temporada.print();
		pessoaFisica.print();
		
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Temporada getTemporada() {
		return temporada;
	}

	public void setTemporada(Temporada temporada) {
		this.temporada = temporada;
	}

	public PessoaFisica getPessoaFisica() {
		return pessoaFisica;
	}

	public void setPessoaFisica(PessoaFisica pessoaFisica) {
		this.pessoaFisica = pessoaFisica;
	}

}
