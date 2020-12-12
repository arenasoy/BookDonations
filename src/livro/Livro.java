package livro;

public class Livro {

	private Integer codigoBarras;
	private String autor;
	private String titulo;
	private Integer isbn;
	private String edicao;
	private int condicao;
	private Origem origem;

	public void print() {

		// TODO condicao
		System.out.println("C�digo de barras: " + codigoBarras + "\nTitulo: " + titulo + "\nAutor: " + autor
				+ "\nEdi��o: " + edicao + " ISBN: " + isbn + "\nOrigem: " + origem.toString());
		System.out.print("Condi��o: ");
		
		if (condicao == 1) System.out.println("Nova");
		if (condicao == 2) System.out.println("Semi nova");
		if (condicao == 3) System.out.println("Usada");
		if (condicao == 4) System.out.println("Poucos desgastes");
	}

	public Livro(Integer codigoBarras, String autor, String titulo, Integer isbn, String edicao, int condicao,
			Origem origem) throws Exception {
		
		if (codigoBarras == null || codigoBarras == 0) {
			throw new Exception("C�digo de barras � obrigat�rio");
		}
		
		if (autor == null || autor.length() == 0 || autor.length() > 50) {
			throw new Exception("Autor � obrigat�rio e deve ter at� 50 caracteres");
		}
		
		if (titulo == null || titulo.length() == 0 || titulo.length() > 50) {
			throw new Exception("T�tulo � obrigat�rio e deve ter at� 50 caracateres");
		}
		
		if (isbn == null || isbn == 0) {
			throw new Exception("ISBN � obrigat�rio");
		}
		
		if (edicao == null || edicao.length() == 0 || edicao.length() > 30) {
			throw new Exception("Edi��o � obrigat�ria e deve ter at� 30 caracteres");
		}
		
		if (condicao > 4 || condicao < 1) {
			throw new Exception("Condi��o � obrigat�ria e deve ter valores entre 1 e 4");
		}
		
		if (origem == null) {
			throw new Exception("Origem � obrigat�ria");
		}
		
		this.codigoBarras = codigoBarras;
		this.autor = autor;
		this.titulo = titulo;
		this.isbn = isbn;
		this.edicao = edicao;
		this.condicao = condicao;
		this.origem = origem;
	}

	public Livro(Integer codigoBarras, String autor, String titulo, Integer isbn, String edicao, int condicao,
			Origem origem, boolean fromDatabase) {
		if (fromDatabase) {
			this.codigoBarras = codigoBarras;
			this.autor = autor;
			this.titulo = titulo;
			this.isbn = isbn;
			this.edicao = edicao;
			this.condicao = condicao;
			this.origem = origem;
		}
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

	public Origem getOrigem() {
		return origem;
	}

	public void setOrigem(Origem origem) {
		this.origem = origem;
	}

}
