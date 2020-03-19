package br.ufpb.adocaoanimais;

import br.ufpb.adocaoanimais.exceptions.*;

import java.util.List;

public interface SistemaAdocaoAnimais {

	public List<Usuario> getUsuarios();

	public List<Animal> getAnimais();

	public List<Animal> getAnimaisAdotados();

	/**
	 * Pesquisa no sistema o usuário com um dado CPF
	 * 
	 * @param cpf O CPF a pesquisar
	 * @return O usuário do sistema que tem o mesmo CPF
	 * @throws UsuarioNaoExisteException
	 */
	public Usuario pesquisaUsuario(String cpf) throws UsuarioNaoExisteException;

	/**
	 * Pesquisa os usuários cujo nome começa com um certo prefixo
	 * 
	 * @param prefixo O prefixo a pesquisar
	 * @return a lista dos usuários cujo nome começa com certo prefixo
	 */
	public List<Usuario> pesquisaUsuariosComNome(String prefixo);

	/**
	 * Pesquisa os animais do tipo passado como parâmetro
	 * 
	 * @param tipo O tipo de animal a pesquisar
	 * @return a lista dos animais do tipo pesquisado.
	 */
	public List<Animal> pesquisaAnimaisDoTipo(String tipo);

	/**
	 * Pesquisa o animal que possuir o código que foi passado como parâmetro
	 * 
	 * @param codigo O código do animal a pesquisar
	 * @return o animal com o código pesquisado
	 * @throws AnimalNaoExisteException se não houver animal com o código passado
	 */
	public Animal pesquisaAnimal(String codigo) throws AnimalNaoExisteException;

	public boolean cadastraUsuario(Usuario user);

	public boolean existeUsuario(Usuario user);

	public boolean cadastraAnimal(Animal a);

	public boolean existeAnimal(Animal animal);

	public void descadastrarAnimal(Animal animal) throws AnimalNaoExisteException;

	public void descadastrarUsuario(Usuario usuario) throws UsuarioNaoExisteException;

	public List<String> compararRequisitos(Animal animal, Usuario usuario);

	public boolean temUsuarios();

	public boolean temAnimais();

	public void adotarAnimal(Animal animal, Usuario dono) throws AnimalNaoExisteException;

	public void removeDuplicatas();

}