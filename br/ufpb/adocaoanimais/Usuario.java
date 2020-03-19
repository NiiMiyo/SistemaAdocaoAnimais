package br.ufpb.adocaoanimais;

import java.util.List;

public class Usuario {

	private String nome;

	private String cpf;

	private String dataNascimento;

	private List<Requisito> requisitos;

	public Usuario(String nome, String cpf, String dataNascimento, List<Requisito> requisitos) {
		this.nome = nome;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
		this.requisitos = requisitos;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public List<Requisito> getRequisitos() {
		return requisitos;
	}

	public void adicionaRequisito(Requisito req) {
		this.requisitos.add(req);
	}

	public void setRequisitos(List<Requisito> requisitos) {
		this.requisitos = requisitos;
	}

	public String toString() {
		return this.nome + ", " + this.cpf;
	}

	public String getDados() {
		String strReturn = "• " + this.nome + "\nCPF ou CNPJ: " + this.cpf + "\nNascimento: " + this.dataNascimento;
		if (!this.requisitos.isEmpty()) {
			strReturn += "\n• Requisitos:";
			for (Requisito r : this.requisitos) {
				strReturn += "\n  ○ " + r.getNomeRequisito() + " - " + r.getValorEsperadoRequisito();
			}
		}
		return strReturn;
	}

	public String salvar(String separator, String reqSeparator, String reqInternalSeparator) {
		String save = this.nome + separator + this.cpf + separator + this.dataNascimento;
		String reqs = "";
		if (!this.requisitos.isEmpty()) {
			for (Requisito r : this.requisitos) {
				reqs += r.getNomeRequisito() + reqInternalSeparator + r.getValorEsperadoRequisito() + reqSeparator;
			}
			reqs = reqs.substring(0, reqs.length() - 2);
		} else {
			reqs = "NONE";
		}
		save += separator + reqs;
		return save;
	}
}