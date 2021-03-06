package endereco;

public class Endereco {

	private Integer id;
	private String cep;
	private int numero;
	private String rua;
	private String bairro;
	private String complemento;

	public Endereco(String cep, int numero, String rua, String bairro, String complemento) throws Exception {

		if (cep == null || cep.length() != 9) {
			throw new Exception("O CEP � obrigat�rio e deve ser no formato 'XXXXX-XXX'");
		}

		if (rua == null || rua.length() == 0) {
			throw new Exception("A rua � obrigat�ria");
		}

		this.cep = cep;
		this.numero = numero;
		this.rua = rua;
		this.bairro = bairro;
		this.complemento = complemento;
		// TODO gerar id
		this.id = (int) (Math.random() * 100);

	}

	public Endereco(int id, String cep, int numero, String rua, String bairro, String complemento) {

		this.cep = cep;
		this.numero = numero;
		this.rua = rua;
		this.bairro = bairro;
		this.complemento = complemento;
		this.id = id;

	}

	public void print() {
		System.out.print("ID: " + this.id + "\nRua: " + this.rua);
		if (this.numero > 0)
			System.out.print(", " + this.numero); 
		if (this.complemento != null && this.complemento.length() > 0)
			System.out.print(" " + this.complemento);
		if (this.bairro != null && this.bairro.length() > 0)
			System.out.println("\nBairro: " + this.bairro);
		System.out.println("\nCEP: " + this.cep);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

}
