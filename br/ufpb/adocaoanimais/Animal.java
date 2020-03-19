package br.ufpb.adocaoanimais;

public abstract class Animal {

	private String nome;

	private String codigo;

	private String dataNascimento;

	private String tamanho;

	private boolean ehBarulhento;

	private boolean ehManso;

	private String cor;

	private String sexo;

	private boolean temPedigree;

	private boolean estahComVacinasEmDia;

	private boolean estahCastrado;

	private String detalhes;

	private String tipoAlimentacao;

	private Usuario dono;

	public Animal(String nome, String codigo, String dataNascimento, String tamanho, boolean ehBarulhento,
			boolean ehManso, String cor, String sexo, boolean temPedigree, boolean estahComVacinasEmDia,
			boolean estahCastrado, String tipoAlimentacao, String detalhes) {
		super();
		this.nome = nome;
		this.codigo = codigo;
		this.dataNascimento = dataNascimento;
		this.tamanho = tamanho;
		this.ehBarulhento = ehBarulhento;
		this.ehManso = ehManso;
		this.cor = cor;
		this.sexo = sexo;
		this.temPedigree = temPedigree;
		this.estahComVacinasEmDia = estahComVacinasEmDia;
		this.estahCastrado = estahCastrado;
		this.setTipoAlimentacao(tipoAlimentacao);
		this.detalhes = detalhes;
		this.dono = new Usuario("NONE", "NONE", "", null);
	}

	public Animal() {
		this("", "", "", "", false, false, "", "", false, false, false, "", "");
	}

	@Override
	public String toString() {
		String strReturn = String.format("%s (%s)", this.nome, this.codigo);
		return strReturn;

	}

	public String getDados() {
		String barulhento = this.ehBarulhento ? "Barulhento" : "Silencioso";
		String manso = this.ehManso ? "Manso" : "Não é Manso";
		String pedigree = this.temPedigree ? "Tem Pedigree" : "Não tem Pedigree";
		String vacinado = this.estahComVacinasEmDia ? "Está vacinado" : "Não está vacinado";
		String castrado = this.estahCastrado ? "É castrado" : "Não é castrado";

		String strReturn = "• " + this.nome + ", " + this.getTipo() + " (" + this.codigo + ")";
		strReturn += "\n" + "Nascimento: " + this.dataNascimento + ", Sexo: " + this.sexo;
		strReturn += "\n" + "Tamanho:" + this.tamanho;
		strReturn += "\n" + "Cor: " + this.cor;
		strReturn += "\n" + barulhento + ", " + manso;
		strReturn += "\n" + pedigree + ", " + vacinado + ", " + castrado;
		strReturn += "\n• Alimentação:\n  " + this.tipoAlimentacao;
		strReturn += "\n• Detalhes:\n  " + this.detalhes;

		if (!this.dono.getCpf().equals("NONE")){
			strReturn += "\n\nAdotado por: " + dono.toString();
		}

		return strReturn;
	}

	public abstract String getTipo();

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getTamanho() {
		return tamanho;
	}

	public void setTamanho(String tamanho) {
		this.tamanho = tamanho;
	}

	public boolean isEhBarulhento() {
		return ehBarulhento;
	}

	public void setEhBarulhento(boolean ehBarulhento) {
		this.ehBarulhento = ehBarulhento;
	}

	public boolean isEhManso() {
		return ehManso;
	}

	public void setEhManso(boolean ehManso) {
		this.ehManso = ehManso;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public boolean isTemPedigree() {
		return temPedigree;
	}

	public void setTemPedigree(boolean temPedigree) {
		this.temPedigree = temPedigree;
	}

	public boolean isEstahComVacinasEmDia() {
		return estahComVacinasEmDia;
	}

	public void setEstahComVacinasEmDia(boolean estahComVacinasEmDia) {
		this.estahComVacinasEmDia = estahComVacinasEmDia;
	}

	public boolean isEstahCastrado() {
		return estahCastrado;
	}

	public void setEstahCastrado(boolean estahCastrado) {
		this.estahCastrado = estahCastrado;
	}

	public String getDetalhes() {
		return detalhes;
	}

	public void setDetalhes(String detalhes) {
		this.detalhes = detalhes;
	}

	public String getTipoAlimentacao() {
		return tipoAlimentacao;
	}

	public void setTipoAlimentacao(String tipoAlimentacao) {
		this.tipoAlimentacao = tipoAlimentacao;
	}

	public Usuario getDono() {
		return this.dono;
	}

	public void setDono(Usuario dono) {
		this.dono = dono;
	}

	public String salvar(String separator) {
		return (this.getTipo() + separator + this.nome + separator + this.codigo + separator + this.dataNascimento
				+ separator + this.tamanho + separator + this.ehBarulhento + separator + this.ehManso + separator
				+ this.cor + separator + this.sexo + separator + this.temPedigree + separator
				+ this.estahComVacinasEmDia + separator + this.estahCastrado + separator + this.tipoAlimentacao
				+ separator + this.detalhes + separator + dono.getNome() + separator + dono.getCpf());
	}
}
