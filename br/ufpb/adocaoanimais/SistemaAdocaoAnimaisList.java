package br.ufpb.adocaoanimais;

import br.ufpb.adocaoanimais.exceptions.*;

import java.util.ArrayList;
import java.util.List;

public class SistemaAdocaoAnimaisList implements SistemaAdocaoAnimais {
	private List<Animal> animais;
	private List<Usuario> usuarios;
	private List<Animal> animaisAdotados;

	public SistemaAdocaoAnimaisList() {
		this.animais = new ArrayList<Animal>();
		this.usuarios = new ArrayList<Usuario>();
		this.animaisAdotados = new ArrayList<Animal>();
	}

	@Override
	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	@Override
	public List<Animal> getAnimais() {
		return this.animais;
	}

	@Override
	public List<Animal> getAnimaisAdotados() {
		return this.animaisAdotados;
	}

	/**
	 * Pesquisa no sistema o usuário com um dado CPF
	 * 
	 * @param cpf O CPF a pesquisar
	 * @return O usuário do sistema que tem o mesmo CPF
	 * @throws UsuarioNaoExisteException Caso o CPF não esteja cadastrado
	 */
	@Override
	public Usuario pesquisaUsuario(String cpf) throws UsuarioNaoExisteException {
		for (Usuario u : this.usuarios) {
			if (u.getCpf().equalsIgnoreCase(cpf)) {
				return u;
			}
		}
		throw new UsuarioNaoExisteException("Usuário de CPF/CNPJ " + cpf + " não encontrado");
	}

	/**
	 * Pesquisa os usuários cujo nome contenha um certo prefixo
	 * 
	 * @param nome O prefixo a pesquisar
	 * @return A lista dos usuários cujo nome possui o parâmetro {@code nome} dentro
	 */
	@Override
	public List<Usuario> pesquisaUsuariosComNome(String nome) {
		nome = nome.toLowerCase();
		ArrayList<Usuario> userListReturn = new ArrayList<>(0);

		for (Usuario u : this.usuarios) {
			if (u.getNome().toLowerCase().contains(nome)) {
				userListReturn.add(u);
			}
		}
		return userListReturn;
	}

	/**
	 * Pesquisa os animais do tipo passado como parâmetro
	 * 
	 * @param tipo O tipo de animal a pesquisar
	 * @return a lista dos animais do tipo pesquisado.
	 */
	@Override
	public List<Animal> pesquisaAnimaisDoTipo(String tipo) {
		ArrayList<Animal> animalListReturn = new ArrayList<>(0);

		this.animais.stream().filter(k -> k.getTipo().equalsIgnoreCase(tipo)).forEach(k -> animalListReturn.add(k));

		return animalListReturn;
	}

	@Override
	public Animal pesquisaAnimal(String codigo) throws AnimalNaoExisteException {
		for (Animal a : this.animais) {
			if (a.getCodigo().equalsIgnoreCase(codigo)) {
				return a;
			}
		}
		throw new AnimalNaoExisteException("Animal com o código \"" + codigo + "\" não encontrado.");
	}

	/**
	 * Cadastra um Usuario no sistema
	 * 
	 * @param user O usuário a ser cadastrado
	 * @return true se o usuário for cadastrado com sucesso e false caso contrário
	 */
	@Override
	public boolean cadastraUsuario(Usuario user) {
		if (this.existeUsuario(user)) {
			return false;
		} else {
			this.usuarios.add(user);
			return true;
		}
	}

	@Override
	public boolean existeUsuario(Usuario user) {
		for (Usuario u : this.usuarios) {
			if (u.equals(user)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean cadastraAnimal(Animal a) {
		if (a == null || this.existeAnimal(a)) {
			return false;
		} else {
			this.animais.add(a);
			return true;
		}
	}

	@Override
	public boolean existeAnimal(Animal animal) {
		for (Animal a : this.animais) {
			if (a.equals(animal)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void descadastrarAnimal(Animal animal) throws AnimalNaoExisteException {

		if (!this.animais.remove(animal)) {
			throw new AnimalNaoExisteException("Houve um erro no descadastramento do animal.");
		}
	}

	@Override
	public void adotarAnimal(Animal animal, Usuario dono) throws AnimalNaoExisteException {
		boolean deuRuim = false;
		try {
			this.descadastrarAnimal(animal);
		} catch (AnimalNaoExisteException e) {
			deuRuim = true;
			throw e;
		}
		if (!deuRuim) {
			animal.setDono(dono);
			this.animaisAdotados.add(animal);
		}
	}

	@Override
	public List<String> compararRequisitos(Animal animal, Usuario usuario) {
		List<String> erroRequisito = new ArrayList<>();

		if (!usuario.getRequisitos().isEmpty()) {

			for (Requisito r : usuario.getRequisitos()) {

				String nomeRequisito = r.getNomeRequisito();
				String valorRequisito = r.getValorEsperadoRequisito();

				if (nomeRequisito.equals("Tipo") && !valorRequisito.equals(animal.getTipo())) {
					erroRequisito.add("O usuário deseja um \"" + valorRequisito + "\", mas o animal é um \""
							+ animal.getTipo() + "\".");

				} else if (nomeRequisito.equals("Sexo") && !valorRequisito.equals(animal.getSexo())) {
					erroRequisito.add("O usuário deseja um animal \"" + valorRequisito + "\", mas o animal é \""
							+ animal.getSexo() + "\".");

				} else if (nomeRequisito.equals("Tamanho") && !valorRequisito.equals(animal.getTamanho())) {
					erroRequisito.add("O usuário deseja um animal \"" + valorRequisito + "\", mas o animal é \""
							+ animal.getTamanho() + "\".");

				} else if (nomeRequisito.equals("Barulho")) {
					String barulho = animal.isEhBarulhento() ? "Barulhento" : "Silencioso";

					if (!valorRequisito.equals(barulho)) {
						erroRequisito.add("O usuário deseja um animal \"" + valorRequisito + "\", mas o animal é \""
								+ barulho + "\".");
					}

				} else if (nomeRequisito.equals("Manso")) {
					String manso = animal.isEhManso() ? "Manso" : "Bravo";

					if (!valorRequisito.equals(manso)) {
						erroRequisito.add("O usuário deseja um animal \"" + valorRequisito + "\", mas o animal é \""
								+ manso + "\".");
					}

				} else if (nomeRequisito.equals("Pedigree")) {
					String pedigree = animal.isTemPedigree() ? "Tem" : "Não tem";

					if (!valorRequisito.equalsIgnoreCase(pedigree)) {
						erroRequisito.add("O usuário deseja um animal que \"" + valorRequisito
								+ "\" pedigree, mas o animal \"" + pedigree + "\".");
					}

				} else if (nomeRequisito.equals("Castrado")) {
					String castrado = animal.isEstahCastrado() ? "Castrado" : "Não castrado";

					if (!valorRequisito.equalsIgnoreCase(castrado)) {
						erroRequisito.add("O usuário deseja um animal que seja \"" + valorRequisito
								+ "\", mas o animal é \"" + castrado + "\".");
					}

				} else if (nomeRequisito.equals("Vacinado")) {
					String vacina = animal.isEstahComVacinasEmDia() ? "Vacinado" : "Não vacinado";

					if (!valorRequisito.equalsIgnoreCase(vacina)) {
						erroRequisito.add("O usuário deseja um animal que seja \"" + valorRequisito
								+ "\", mas o animal é \"" + vacina + "\".");
					}
				}
			}
		}
		return erroRequisito;
	}

	@Override
	public boolean temUsuarios() {
		return !this.usuarios.isEmpty();
	}

	@Override
	public boolean temAnimais() {
		return !this.animais.isEmpty();
	}

	@Override
	public void descadastrarUsuario(Usuario usuario) throws UsuarioNaoExisteException {
		if (this.existeUsuario(usuario)) {
			this.usuarios.remove(usuario);
		} else {
			throw new UsuarioNaoExisteException("O usuário informado não está cadastrado.");
		}
	}

	@Override
	public void removeDuplicatas() {
		ArrayList<Usuario> usuariosCorrigidos = new ArrayList<>();
		ArrayList<Animal> animaisCorrigidos = new ArrayList<>();
		ArrayList<Animal> animaisAdotadosCorrigidos = new ArrayList<>();

		for (Usuario userSistema : this.usuarios) {
			boolean podeCadastrar = true;
			for (Usuario userCorrige : usuariosCorrigidos) {
				if (userSistema.equals(userCorrige)) {
					podeCadastrar = false;
					break;
				}
			}
			if (podeCadastrar) {
				usuariosCorrigidos.add(userSistema);
			}
		}
		for (Animal aniSistema : this.animaisAdotados) {
			boolean podeCadastrar = true;
			for (Animal aniCorrige : animaisAdotadosCorrigidos) {
				if (aniSistema.equals(aniCorrige)) {
					podeCadastrar = false;
					break;
				}
			}
			if (podeCadastrar) {
				animaisAdotadosCorrigidos.add(aniSistema);
			}
		}

		for (Animal aniSistema : this.animais) {
			boolean podeCadastrar = true;
			for (Animal aniCorrige : animaisCorrigidos) {
				if (aniSistema.equals(aniCorrige)) {
					podeCadastrar = false;
					break;
				}
			}
			if (podeCadastrar) {
				animaisCorrigidos.add(aniSistema);
			}
		}

		this.animais = animaisCorrigidos;
		this.usuarios = usuariosCorrigidos;
		this.animaisAdotados = animaisAdotadosCorrigidos;
	}
}