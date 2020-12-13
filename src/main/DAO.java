package main;

import administrador.AdministradorDAO;
import bibliotecario.BibliotecarioDAO;
import cidade.CidadeDAO;
import doacao.DoacaoDAO;
import emprestimo.EmprestimoDAO;
import endereco.EnderecoDAO;
import grupo.GrupoDAO;
import grupo.PessoaGrupoTemporadaDAO;
import livro.LivroDAO;
import questao.QuestaoDAO;
import temporada.TemporadaDAO;
import usuario.UsuarioDAO;
import usuario.fisica.PerfilDAO;
import usuario.fisica.PessoaFisicaDAO;
import usuario.fisica.doador.DoadorDAO;
import usuario.fisica.donatario.DonatarioDAO;
import usuario.fisica.voluntario.VoluntarioDAO;
import usuario.juridica.PessoaJuridicaDAO;

/**
 * 
 * Classe DAO
 * Essa classe implementa todos os DAO
 * Para evitar a criacao de muitos objetos diferentes
 *
 */
public class DAO {

	private AdministradorDAO administradorDAO;
	private BibliotecarioDAO bibliotecarioDAO;
	private CidadeDAO cidadeDAO;
	private DoacaoDAO doacaoDAO;
	private EmprestimoDAO emprestimoDAO;
	private EnderecoDAO enderecoDAO;
	private GrupoDAO grupoDAO;
	private LivroDAO livroDAO;
	private QuestaoDAO questaoDAO;
	private TemporadaDAO temporadaDAO;
	private UsuarioDAO usuarioDAO;
	private PessoaFisicaDAO pessoaFisicaDAO;
	private DoadorDAO doadorDAO;
	private DonatarioDAO donatarioDAO;
	private VoluntarioDAO voluntarioDAO;
	private PessoaJuridicaDAO pessoaJuridicaDAO;
	private PerfilDAO perfilDAO;
	private PessoaGrupoTemporadaDAO pessoaGrupoTemporadaDAO;
	private ConsultaDAO consultaDAO;

	public DAO() {
		administradorDAO = new AdministradorDAO();
		bibliotecarioDAO = new BibliotecarioDAO();
		cidadeDAO = new CidadeDAO();
		doacaoDAO = new DoacaoDAO();
		emprestimoDAO = new EmprestimoDAO();
		enderecoDAO = new EnderecoDAO();
		grupoDAO = new GrupoDAO();
		livroDAO = new LivroDAO();
		questaoDAO = new QuestaoDAO();
		temporadaDAO = new TemporadaDAO();
		usuarioDAO = new UsuarioDAO();
		pessoaFisicaDAO = new PessoaFisicaDAO();
		doadorDAO = new DoadorDAO();
		donatarioDAO = new DonatarioDAO();
		voluntarioDAO = new VoluntarioDAO();
		pessoaJuridicaDAO = new PessoaJuridicaDAO();
		perfilDAO = new PerfilDAO();
		pessoaGrupoTemporadaDAO = new PessoaGrupoTemporadaDAO();
		consultaDAO = new ConsultaDAO();
	}

	public AdministradorDAO getAdministradorDAO() {
		return administradorDAO;
	}

	public void setAdministradorDAO(AdministradorDAO administradorDAO) {
		this.administradorDAO = administradorDAO;
	}

	public BibliotecarioDAO getBibliotecarioDAO() {
		return bibliotecarioDAO;
	}

	public void setBibliotecarioDAO(BibliotecarioDAO bibliotecarioDAO) {
		this.bibliotecarioDAO = bibliotecarioDAO;
	}

	public CidadeDAO getCidadeDAO() {
		return cidadeDAO;
	}

	public void setCidadeDAO(CidadeDAO cidadeDAO) {
		this.cidadeDAO = cidadeDAO;
	}

	public DoacaoDAO getDoacaoDAO() {
		return doacaoDAO;
	}

	public void setDoacaoDAO(DoacaoDAO doacaoDAO) {
		this.doacaoDAO = doacaoDAO;
	}

	public EmprestimoDAO getEmprestimoDAO() {
		return emprestimoDAO;
	}

	public void setEmprestimoDAO(EmprestimoDAO emprestimoDAO) {
		this.emprestimoDAO = emprestimoDAO;
	}

	public EnderecoDAO getEnderecoDAO() {
		return enderecoDAO;
	}

	public void setEnderecoDAO(EnderecoDAO enderecoDAO) {
		this.enderecoDAO = enderecoDAO;
	}

	public GrupoDAO getGrupoDAO() {
		return grupoDAO;
	}

	public void setGrupoDAO(GrupoDAO grupoDAO) {
		this.grupoDAO = grupoDAO;
	}

	public LivroDAO getLivroDAO() {
		return livroDAO;
	}

	public void setLivroDAO(LivroDAO livroDAO) {
		this.livroDAO = livroDAO;
	}

	public QuestaoDAO getQuestaoDAO() {
		return questaoDAO;
	}

	public void setQuestaoDAO(QuestaoDAO questaoDAO) {
		this.questaoDAO = questaoDAO;
	}

	public TemporadaDAO getTemporadaDAO() {
		return temporadaDAO;
	}

	public void setTemporadaDAO(TemporadaDAO temporadaDAO) {
		this.temporadaDAO = temporadaDAO;
	}

	public UsuarioDAO getUsuarioDAO() {
		return usuarioDAO;
	}

	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	public PessoaFisicaDAO getPessoaFisicaDAO() {
		return pessoaFisicaDAO;
	}

	public void setPessoaFisicaDAO(PessoaFisicaDAO pessoaFisicaDAO) {
		this.pessoaFisicaDAO = pessoaFisicaDAO;
	}

	public DoadorDAO getDoadorDAO() {
		return doadorDAO;
	}

	public void setDoadorDAO(DoadorDAO doadorDAO) {
		this.doadorDAO = doadorDAO;
	}

	public DonatarioDAO getDonatarioDAO() {
		return donatarioDAO;
	}

	public void setDonatarioDAO(DonatarioDAO donatarioDAO) {
		this.donatarioDAO = donatarioDAO;
	}

	public VoluntarioDAO getVoluntarioDAO() {
		return voluntarioDAO;
	}

	public void setVoluntarioDAO(VoluntarioDAO voluntarioDAO) {
		this.voluntarioDAO = voluntarioDAO;
	}

	public PessoaJuridicaDAO getPessoaJuridicaDAO() {
		return pessoaJuridicaDAO;
	}

	public void setPessoaJuridicaDAO(PessoaJuridicaDAO pessoaJuridicaDAO) {
		this.pessoaJuridicaDAO = pessoaJuridicaDAO;
	}

	public PerfilDAO getPerfilDAO() {
		return perfilDAO;
	}

	public void setPerfilDAO(PerfilDAO perfilDAO) {
		this.perfilDAO = perfilDAO;
	}

	public PessoaGrupoTemporadaDAO getPessoaGrupoTemporadaDAO() {
		return pessoaGrupoTemporadaDAO;
	}

	public void setPessoaGrupoTemporadaDAO(PessoaGrupoTemporadaDAO pessoaGrupoTemporadaDAO) {
		this.pessoaGrupoTemporadaDAO = pessoaGrupoTemporadaDAO;
	}

	public ConsultaDAO getConsultaDAO() {
		return consultaDAO;
	}

	public void setConsultaDAO(ConsultaDAO consultaDAO) {
		this.consultaDAO = consultaDAO;
	}

}
