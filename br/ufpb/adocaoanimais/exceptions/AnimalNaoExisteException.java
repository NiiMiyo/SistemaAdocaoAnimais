package br.ufpb.adocaoanimais.exceptions;

/**
 * AnimalNaoExisteException
 */
public class AnimalNaoExisteException extends Exception{
	private static final long serialVersionUID = 1L;

	public AnimalNaoExisteException(String mensagem){
		super(mensagem);
	}
}