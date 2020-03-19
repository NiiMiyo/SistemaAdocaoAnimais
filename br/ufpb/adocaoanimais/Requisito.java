package br.ufpb.adocaoanimais;
public class Requisito {
	private String nomeRequisito;
	private String valorEsperadoRequisito;
	
	public Requisito(String nomeRequisito, String valorEsperadoRequisito) {
		super();
		this.nomeRequisito = nomeRequisito;
		this.valorEsperadoRequisito = valorEsperadoRequisito;
	}

	
	public String getNomeRequisito() {
		return nomeRequisito;
	}
	public void setNomeRequisito(String nomeRequisito) {
		this.nomeRequisito = nomeRequisito;
	}
	public String getValorEsperadoRequisito() {
		return valorEsperadoRequisito;
	}
	public void setValorEsperadoRequisito(String valorEsperadoRequisito) {
		this.valorEsperadoRequisito = valorEsperadoRequisito;
	}
	
	
}
