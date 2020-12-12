package livro;

public class Livro {

	private Integer codigoBarras;
	private String autor;
	private String titulo;
	private Integer isbn;
	private String edicao;
	private int condicao;
	private String origem;
	
	public void print() {
		//TODO
	}
	

	public Livro(Integer codigoBarras, String autor, String titulo, Integer isbn, String edicao, int condicao,
			String origem) {
		this.codigoBarras = codigoBarras;
		this.autor = autor;
		this.titulo = titulo;
		this.isbn = isbn;
		this.edicao = edicao;
		this.condicao = condicao;
		this.origem = origem;
	}



	public Integer getCodigoBarras() {
		return codigoBarras;
	}

	public void setCodigoBarras(Integer codigoBarras) {
		this.codigoBarras = codigoBarras;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Integer getIsbn() {
		return isbn;
	}

	public void setIsbn(Integer isbn) {
		this.isbn = isbn;
	}

	public String getEdicao() {
		return edicao;
	}

	public void setEdicao(String edicao) {
		this.edicao = edicao;
	}

	public int getCondicao() {
		return condicao;
	}

	public void setCondicao(int condicao) {
		this.condicao = condicao;
	}

	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}

}
