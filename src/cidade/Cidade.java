package cidade;

public class Cidade {

	private Integer id;
	private String nome;
	private String uf;

	public Cidade(String nome, String uf) throws Exception {

		if (nome == null || nome.length() > 40 || nome.length() == 0) {
			throw new Exception("O nome da cidade é obrigatório e deve ter tamanho menor ou igual a 40");
		}

		if (uf == null || uf.length() != 2) {
			throw new Exception("O nome da UF deve ter tamanho igual a 2");
		}

		this.nome = nome;
		this.uf = uf;
		// TODO gerar id
		this.id = (int) (Math.random() * 100);
	}

	public Cidade(Integer id, String nome, String uf) {

		this.id = id;
		this.nome = nome;
		this.uf = uf;
	}

	public void print() {
		System.out.println("ID: " + this.id + "\n" + this.nome + " - " + this.uf);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

}
