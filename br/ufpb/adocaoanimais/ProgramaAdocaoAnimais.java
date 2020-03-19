package br.ufpb.adocaoanimais;

import br.ufpb.adocaoanimais.exceptions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

/**
 * Programa principal para o Sistema de Adoção de Animais
 * 
 * @author Douglas Sebastian
 * @version 1.0
 */
public class ProgramaAdocaoAnimais {
	/**
	 * Subdiretório em que serão guardados e carregados os dados do sistema pelos
	 * métodos {@code guardarDados(SistemaAdocaoAnimais)}, {@code lerUsuários()} e
	 * {@code lerAnimais()}.
	 */
	private static final String DATA_DIRECTORY = "data/";

	/**
	 * A {@code String} que dividirá os atributos dos {@code Usuarios} e dos
	 * {@code Animais} no arquivo dos dados do sistema.
	 */
	private static final String SEPARADOR = "//";

	/**
	 * A {@code String} que dividirá os {@code Requisitos} dos {@code Usuarios}.
	 */
	private static final String REQUISITO_SEPARADOR = "@@";

	/**
	 * A {@code String} que dividirá o {@code nome} e o {@code valor} dos requisitos
	 * dos {@code Usuarios}.
	 */
	private static final String REQUISITO_INTERNAL_SEPARADOR = "##";

	/**
	 * {@code Array} com os caracteres que não podem ser dados no método
	 * {@code pedirDados(String)}.
	 */
	private static final String[] CHARS_INVALIDOS = { "/", "@", "#", "\\" };

	/**
	 * {@code Array} com os possíveis tamanhos de animais.
	 */
	private static final String[] TAMANHO_ANIMAIS = { "Pequeno", "Médio", "Grande" };

	/**
	 * {@code Array} com os possíveis sexos de animais.
	 */
	private static final String[] SEXO_ANIMAIS = { "Macho", "Fêmea" };

	/**
	 * {@code Array} com os possíveis tipos de animais.
	 */
	private static final String[] TIPO_ANIMAIS = { "Gato", "Cachorro" };

	/**
	 * {@code Array} com as opcoes do menu principal
	 */
	private static final String[] OPCOES = { "Gerenciar usuários", "Gerenciar animais", "Adotar um animal",
			"Pesquisar animal para um usuário", "Sair" };

	public static void main(String[] args) {

		// Objeto do Sistema
		SistemaAdocaoAnimais sistema = new SistemaAdocaoAnimaisList();

		// boolean mensagemCriarArquivos = false;

		try {
			List<Usuario> leitura = lerUsuarios();
			leitura.forEach(user -> sistema.cadastraUsuario(user));
		} catch (IOException e) {
			// mensagemCriarArquivos = true;
			// JOptionPane.showMessageDialog(null, "Não foi possível recuperar os dos
			// usuários do Sistema.");
		}

		try {
			List<Animal> leitura = lerAnimais();
			// TODO: Separar os cadastrados
			for (Animal a : leitura) {
				sistema.cadastraAnimal(a);
				if (!a.getDono().getCpf().equals("NONE")) {
					try {
						sistema.adotarAnimal(a, a.getDono());
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, e.getMessage());
					}
				}
			}
		} catch (IOException e) {
			// mensagemCriarArquivos = true;
			// JOptionPane.showMessageDialog(null, "Não foi possível recuperar os dos
			// animais do Sistema.");
		}

		
		// if (mensagemCriarArquivos) {
		// 	try {
		// 		gravarDados(sistema);
		// 		JOptionPane.showMessageDialog(null, "Criando arquivos para salvamento dos dados.");
		// 	} catch (IOException e) {
		// 	}
		// }

		boolean sair = false;

		String nome = "", codigo = "", tipo = "", cpf = "";
		Usuario u;

		while (!sair) {
			try {
				gravarDados(sistema);
			} catch (IOException e) {
			}
			String escolhaString = null;
			try {
				escolhaString = pedirDado("Escolha uma opção", OPCOES).toString();
			} catch (OperacaoCanceladaException e) {
				sair = true;
				break;
			} catch (NullPointerException e) {
				sair = true;
				break;
			}
			if (escolhaString == null) {
				sair = true;
				break;
			}
			switch (escolhaString) {
				case "Gerenciar usuários":
					try {
						final String[] opcoesMenu = { "Cadastrar um usuário", "Descadastrar um usuário",
								"Exibir todos os usuários", "Pesquisar usuário pelo nome",
								"Pesquisar usuário pelo CPF ou CPNJ" };
						String escolha = pedirDado("Escolha uma opção.", opcoesMenu).toString();
						if (escolha.equals("Cadastrar um usuário")) {
							try {
								nome = pedirDado("Qual o nome do usuário?");
								cpf = pedirDado("Insira o CPF ou CNPJ de " + nome + ".");
								u = sistema.pesquisaUsuario(cpf);
								JOptionPane.showMessageDialog(null,
										"Já existe um usuário cadastrado com este CPF/CNPJ.");
								break;
							} catch (UsuarioNaoExisteException e) {
								try {
									String dataNascimento = pedirDado("Insira a data de nascimento de " + nome + ".");
									ArrayList<Requisito> requisitos = new ArrayList<>(0);

									// Início do pedido de requisitos
									if (pedirSimNao("Você possui algum requisito quanto ao tipo do animal?")) {
										String valorTipo = pedirDado("Escolha o tipo de animal que você deseja.",
												TIPO_ANIMAIS).toString();
										requisitos.add(new Requisito("Tipo", valorTipo));
									}
									if (pedirSimNao("Você possui algum requisito quanto ao tamanho do animal?")) {
										String valorTipo = pedirDado("Escolha o tamanho do animal que você deseja.",
												TAMANHO_ANIMAIS).toString();
										requisitos.add(new Requisito("Tamanho", valorTipo));
									}
									if (pedirSimNao("Você possui algum requisito quanto ao sexo do animal?")) {
										String valorTipo = pedirDado("Insira o sexo desejado.", SEXO_ANIMAIS)
												.toString();
										requisitos.add(new Requisito("Sexo", valorTipo));
									}
									if (pedirSimNao(
											"Você possui algum requisito quanto ao quão barulhento o animal é?")) {
										String[] opcoes = { "Barulhento", "Silencioso" };
										String valorTipo = pedirDado("Insira a opção desejada.", opcoes).toString();
										requisitos.add(new Requisito("Barulho", valorTipo));
									}
									if (pedirSimNao("Você possui algum requisito quanto ao quão manso o animal é?")) {
										String[] opcoes = { "Manso", "Bravo" };
										String valorTipo = pedirDado("Insira a opção desejada.", opcoes).toString();
										requisitos.add(new Requisito("Manso", valorTipo));
									}
									if (pedirSimNao("Você possui algum requisito quanto à pedigree do animal?")) {
										String[] opcoes = { "Tem", "Não tem" };
										String valorTipo = pedirDado("Insira a opção desejada.", opcoes).toString();
										requisitos.add(new Requisito("Pedigree", valorTipo));
									}
									if (pedirSimNao("Você possui algum requisito quanto à castração do animal?")) {
										String[] opcoes = { "Castrado", "Não castrado" };
										String valorTipo = pedirDado("Insira a opção desejada.", opcoes).toString();
										requisitos.add(new Requisito("Castrado", valorTipo));
									}
									if (pedirSimNao("Você possui algum requisito quanto à vacinação do animal?")) {
										String[] opcoes = { "Vacinado", "Não vacinado" };
										String valorTipo = pedirDado("Insira a opção desejada.", opcoes).toString();
										requisitos.add(new Requisito("Vacinado", valorTipo));
									}

									// Fim do pedido de requisitos

									u = new Usuario(nome, cpf, dataNascimento, requisitos);
									sistema.cadastraUsuario(u);
									JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso.");
								} catch (OperacaoCanceladaException e1) {
									JOptionPane.showMessageDialog(null, e.getMessage());
								}
							}
						} else if (escolha.equals("Descadastrar um usuário")) {
							if (!sistema.temUsuarios()) {
								JOptionPane.showMessageDialog(null, "Não há usuários cadastrados no sistema.");
								break;
							}
							try {
								Usuario usuarioDescadastrado = (Usuario) pedirDado(
										"Selecione um usuário para ser descadastrado.",
										sistema.getUsuarios().toArray());
								sistema.descadastrarUsuario(usuarioDescadastrado);
								JOptionPane.showMessageDialog(null,
										"\"" + usuarioDescadastrado.getNome() + "\" foi descadastrado com sucesso.");
							} catch (UsuarioNaoExisteException e) {
								JOptionPane.showMessageDialog(null, "Houve um erro no descadastramento do usuário.");
							}
						} else if (escolha.equals("Exibir todos os usuários")) {
							if (!sistema.temUsuarios()) {
								JOptionPane.showMessageDialog(null, "Não há usuários cadastrados no sistema.");
								break;
							}
							Object[] usuariosNoSistema = sistema.getUsuarios().toArray();
							try {
								while (true) {
									Usuario usuarioEscolhido = (Usuario) pedirDado("Escolha um usuário para ver.",
											usuariosNoSistema);
									JOptionPane.showMessageDialog(null, usuarioEscolhido.getDados());
								}
							} catch (OperacaoCanceladaException e) {
							}
						} else if (escolha.equals("Pesquisar usuário pelo nome")) {
							if (!sistema.temUsuarios()) {
								JOptionPane.showMessageDialog(null, "Não existem usuários cadastrados no sistema.");
								break;
							}

							ArrayList<Usuario> users = new ArrayList<>();
							try {
								nome = pedirDado("Qual o nome do usuário?");
							} catch (OperacaoCanceladaException e) {
								JOptionPane.showMessageDialog(null, e.getMessage());
							}
							sistema.pesquisaUsuariosComNome(nome).forEach(user -> users.add(user));
							if (users.isEmpty()) {
								JOptionPane.showMessageDialog(null, "Nenhum usuário encontrado.");
							} else {

								try {
									while (true) {
										Usuario usuarioEscolhido = (Usuario) pedirDado("Escolha um usuário para ver.",
												users.toArray());
										JOptionPane.showMessageDialog(null, usuarioEscolhido.getDados());
									}
								} catch (OperacaoCanceladaException e) {
								}
							}
						} else if (escolha.equals("Pesquisar usuário pelo CPF ou CPNJ")) {
							if (!sistema.temUsuarios()) {
								JOptionPane.showMessageDialog(null, "Não existem usuários cadastrados no sistema.");
								break;
							}
							try {
								cpf = pedirDado("Insira o CPF ou CNPJ do usuário.");
								u = sistema.pesquisaUsuario(cpf);
								JOptionPane.showMessageDialog(null, "Usuário encontrado:\n" + u.getDados());
							} catch (UsuarioNaoExisteException e) {
								JOptionPane.showMessageDialog(null,
										"Usuário de CPF ou CNPJ \"" + cpf + "\" não existente.");
							} catch (OperacaoCanceladaException e) {
								JOptionPane.showMessageDialog(null, e.getMessage());
							}
						}
					} catch (OperacaoCanceladaException e) {
						// JOptionPane.showMessageDialog(null, e.getMessage());
					}
					break;

				case "Gerenciar animais":
					try {
						final String[] opcoesMenu = { "Cadastrar animal", "Descadastrar um animal",
								"Exibir todos os animais", "Pesquisar animais de um tipo",
								"Pesquisar animais de um sexo", "Exibir animais já adotados" };
						String escolha = pedirDado("Escolha uma opção.", opcoesMenu).toString();

						if (escolha.equals("Cadastrar animal")) {
							try {
								nome = pedirDado("Qual o nome do animal?");
								tipo = pedirDado("Qual o tipo do animal", TIPO_ANIMAIS).toString();
								codigo = pedirDado("Qual o código do animal?");
								sistema.pesquisaAnimal(codigo);
								JOptionPane.showMessageDialog(null, "Um animal com esse código já foi cadastrado.");
							} catch (OperacaoCanceladaException e) {
								JOptionPane.showMessageDialog(null, e.getMessage());
							} catch (AnimalNaoExisteException e) {
								Animal a = null;

								String dataNascimento = "", tamanho = "", cor = "", sexo = "", tipoAlimentacao = "",
										detalhes = "";
								boolean ehBarulhento = false, ehManso = false, temPedigree = false,
										estahComVacinasEmDia = false, estahCastrado = false;

								try {
									sexo = pedirDado("Qual o sexo de " + nome + "?", SEXO_ANIMAIS).toString();
									dataNascimento = pedirDado("Insira a data de nascimento de " + nome + ".");
									tamanho = pedirDado("Insira o tamanho de " + nome + ".", TAMANHO_ANIMAIS)
											.toString();
									cor = pedirDado("Qual a cor de " + nome + "?");
									ehBarulhento = pedirSimNao(nome + " é barulhento?");
									ehManso = pedirSimNao(nome + " é manso?");
									temPedigree = pedirSimNao(nome + " tem pedigree?");
									estahComVacinasEmDia = pedirSimNao(nome + " está com as vacinas em dia?");
									estahCastrado = pedirSimNao(nome + " é castrado?");
									tipoAlimentacao = pedirDado("Como é o tipo de alimentação de " + nome + "?");
									detalhes = pedirDado("Dê detalhes sobre " + nome + ".");

									if (tipo.equals("Cachorro")) {
										a = new Cachorro(nome, codigo, dataNascimento, tamanho, ehBarulhento, ehManso,
												cor, sexo, temPedigree, estahComVacinasEmDia, estahCastrado,
												tipoAlimentacao, detalhes);
									} else if (tipo.equals("Gato")) {
										a = new Gato(nome, codigo, dataNascimento, tamanho, ehBarulhento, ehManso, cor,
												sexo, temPedigree, estahComVacinasEmDia, estahCastrado, tipoAlimentacao,
												detalhes);
									}

									boolean cadastrou = sistema.cadastraAnimal(a);
									if (cadastrou) {
										JOptionPane.showMessageDialog(null, "Animal cadastrado com sucesso");
									} else {
										JOptionPane.showMessageDialog(null, "Animal já estava cadastrado");
									}
								} catch (OperacaoCanceladaException e1) {
									JOptionPane.showMessageDialog(null, e.getMessage());
								}
							}
						} else if (escolha.equals("Descadastrar um animal")) {
							if (!sistema.temAnimais()) {
								JOptionPane.showMessageDialog(null, "Não há animais cadastrados no sistema.");
								break;
							}
							try {
								Animal animalDescadastrado = (Animal) pedirDado(
										"Selecione um animal para ser descadastrado.", sistema.getAnimais().toArray());
								sistema.descadastrarAnimal(animalDescadastrado);
								JOptionPane.showMessageDialog(null,
										"\"" + animalDescadastrado.getNome() + "\" foi descadastrado com sucesso.");
							} catch (OperacaoCanceladaException e) {
								JOptionPane.showMessageDialog(null, e.getMessage());
							} catch (AnimalNaoExisteException e) {
								JOptionPane.showMessageDialog(null, "Houve um erro no descadastramento do animal.");
							}
						} else if (escolha.equals("Exibir todos os animais")) {
							if (!sistema.temAnimais()) {
								JOptionPane.showMessageDialog(null, "Não há animais cadastrados no sistema.");
								break;
							}
							Object[] animaisNoSistema = sistema.getAnimais().toArray();
							try {
								while (true) {
									Animal animalEscolhido = (Animal) pedirDado("Escolha um animal para ver.",
											animaisNoSistema);
									JOptionPane.showMessageDialog(null, animalEscolhido.getDados());
								}
							} catch (OperacaoCanceladaException e) {
							}
						} else if (escolha.equals("Pesquisar animais de um tipo")) {
							if (!sistema.temAnimais()) {
								JOptionPane.showMessageDialog(null, "Não existem animais cadastrados no sistema.");
								break;
							}

							List<Animal> animais = null;
							String tipoPesq = "";
							try {
								tipoPesq = pedirDado("Qual o tipo do animal?", TIPO_ANIMAIS).toString();
							} catch (OperacaoCanceladaException e) {
								JOptionPane.showMessageDialog(null, e.getMessage());
							}

							animais = sistema.pesquisaAnimaisDoTipo(tipoPesq);
							if (!animais.isEmpty()) {
								try {
									while (true) {
										Animal animalEscolhido = (Animal) pedirDado("Escolha um animal para ver.",
												animais.toArray());
										JOptionPane.showMessageDialog(null, animalEscolhido.getDados());
									}
								} catch (OperacaoCanceladaException e) {
								}
							} else {
								JOptionPane.showMessageDialog(null, "Não foi encontrado nenhum " + tipoPesq + ".");
							}
						} else if (escolha.equals("Pesquisar animais de um sexo")) {
							if (!sistema.temAnimais()) {
								JOptionPane.showMessageDialog(null, "Não existem animais cadastrados no sistema.");
								break;
							}

							String sexoPesq = "";
							try {
								sexoPesq = pedirDado("Qual o tipo do animal?", SEXO_ANIMAIS).toString();
							} catch (OperacaoCanceladaException e) {
								JOptionPane.showMessageDialog(null, e.getMessage());
							}

							ArrayList<Animal> animais = new ArrayList<>();

							for (Animal a : sistema.getAnimais()) {
								if (a.getSexo().equals(sexoPesq)) {
									animais.add(a);
								}
							}

							if (!animais.isEmpty()) {
								try {
									while (true) {
										Animal animalEscolhido = (Animal) pedirDado("Escolha um animal para ver.",
												animais.toArray());
										JOptionPane.showMessageDialog(null, animalEscolhido.getDados());
									}
								} catch (OperacaoCanceladaException e) {
								}
							} else {
								JOptionPane.showMessageDialog(null, "Não foi encontrado nenhum \"" + sexoPesq + "\".");
							}
						} else if (escolha.equals("Exibir animais já adotados")) {
							if (sistema.getAnimaisAdotados().isEmpty()) {
								JOptionPane.showMessageDialog(null,
										"Nenhum animal foi adotado ainda!\nSeja o primeiro a adotar um!");
								break;
							}
							try {
								Object[] animaisAdotados = sistema.getAnimaisAdotados().toArray();
								while (true) {
									Animal animalEscolhido = (Animal) pedirDado("Escolha um animal para ver.",
											animaisAdotados);
									JOptionPane.showMessageDialog(null, animalEscolhido.getDados());
								}
							} catch (OperacaoCanceladaException e) {
							}
						}

					} catch (OperacaoCanceladaException e) {
						// JOptionPane.showMessageDialog(null, e.getMessage());
					}
					break;

				case "Adotar um animal":
					if (!sistema.temUsuarios()) {
						JOptionPane.showMessageDialog(null, "Não existe nenhum usuário cadastrado no sistema.");
						break;
					}
					if (!sistema.temAnimais()) {
						JOptionPane.showMessageDialog(null, "Não existe nenhum animal cadastrado no sistema.");
						break;
					}

					try {
						Usuario adotador = (Usuario) pedirDado("Escolha o usuário que irá adotar o animal.",
								sistema.getUsuarios().toArray());
						Animal animalAdotado = (Animal) pedirDado("Escolha o animal a ser adotado",
								sistema.getAnimais().toArray());

						List<String> errosRequisitos = sistema.compararRequisitos(animalAdotado, adotador);

						String mensagem = "";
						if (!errosRequisitos.isEmpty()) {
							mensagem = "Foram encontrados discrepâncias com os requisitos informados pelo usuário.\n";
							for (String s : errosRequisitos) {
								mensagem += "\n  • " + s;
							}
						}
						String mensagemFinal = mensagem + "\n\n" + adotador.getNome() + " deseja realmente adotar "
								+ animalAdotado.getNome() + "?";
						while (mensagemFinal.startsWith("\n")) {
							mensagemFinal = mensagemFinal.substring(1);
						}
						if (pedirSimNao(mensagemFinal)) {
							JOptionPane.showMessageDialog(null,
									"Parabéns para " + adotador.getNome() + " e " + animalAdotado.getNome()
											+ "!\n Com certeza vocês terãm uma vida bem mais feliz a partir de hoje!");
							sistema.adotarAnimal(animalAdotado, adotador);
						} else {
							JOptionPane.showMessageDialog(null, "Tudo bem. " + animalAdotado.getNome()
									+ " encontrará um lar mais cedo ou mais tarde!");
						}

					} catch (OperacaoCanceladaException e) {
						JOptionPane.showMessageDialog(null, e.getMessage());
					} catch (AnimalNaoExisteException e) {
						JOptionPane.showMessageDialog(null, "Ocorreu um erro na adoção. Tente novamente.");
					}
					break;
				case "Pesquisar animal para um usuário":
					if (!sistema.temUsuarios()) {
						JOptionPane.showMessageDialog(null, "Não existe nenhum usuário cadastrado no sistema.");
						break;
					}
					if (!sistema.temAnimais()) {
						JOptionPane.showMessageDialog(null, "Não existe nenhum animal cadastrado no sistema.");
						break;
					}
					List<Animal> animaisCompatíveis = new ArrayList<>(0);
					try {
						Usuario usuarioPesquisa = (Usuario) pedirDado("Escolha um usuário para procurar por animais.",
								sistema.getUsuarios().toArray());
						for (Animal a : sistema.getAnimais()) {
							if (sistema.compararRequisitos(a, usuarioPesquisa).isEmpty()) {
								animaisCompatíveis.add(a);
							}
						}
					} catch (OperacaoCanceladaException e) {
						JOptionPane.showMessageDialog(null, e.getMessage());
					}
					try {
						if (animaisCompatíveis.isEmpty()) {
							JOptionPane.showMessageDialog(null,
									"Não foi encontrado nenhum animal compatível com os requisitos do usuário.");
						} else {
							while (true) {
								Animal animalEscolhido = (Animal) pedirDado("Escolha um animal para ver.",
										animaisCompatíveis.toArray());
								JOptionPane.showMessageDialog(null, animalEscolhido.getDados());
							}
						}
						break;
					} catch (OperacaoCanceladaException e) {
						JOptionPane.showMessageDialog(null, e.getMessage());
					}
					break;
				case "Sair":
					sair = true;
					break;
			}
		}
		try

		{
			gravarDados(sistema);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Não foi possível salvar os dados.");
		} finally {
			JOptionPane.showMessageDialog(null, "FIM DO PROGRAMA");
		}
	}

	/**
	 * Método para gravar os dados dos {@code Usuarios} e dos {@code Animais} do
	 * {@code sistema}.
	 * 
	 * @param sistema Objeto da interface {@code SistemaAdocaoAnimais} que será
	 *                usado para guardar os dados.
	 * 
	 * @throws IOException Caso não seja possível gravar os dados do {@code sistema}
	 */
	private static void gravarDados(SistemaAdocaoAnimais sistema) throws IOException {
		List<Usuario> dataUsuario = sistema.getUsuarios();

		List<Animal> dataAnimais = sistema.getAnimais();
		sistema.getAnimaisAdotados().forEach(a -> dataAnimais.add(a));

		GravadorDeDados gravador = new GravadorDeDados();
		try {
			List<String> data = new ArrayList<>();
			for (Usuario u : dataUsuario) {
				String dado = u.salvar(SEPARADOR, REQUISITO_SEPARADOR, REQUISITO_INTERNAL_SEPARADOR);
				boolean podeSalvar = true;
				for (String linha : data) {
					if (linha.equals(dado)) {
						podeSalvar = false;
						break;
					}
				}
				if (podeSalvar) {
					data.add(dado);
				}
			}
			gravador.gravaTextoEmArquivo(data, DATA_DIRECTORY + "users.txt");

			data = new ArrayList<>();
			for (Animal a : dataAnimais) {
				String dado = a.salvar(SEPARADOR);
				boolean podeSalvar = true;
				for (String linha : data) {
					if (linha.equals(dado)) {
						podeSalvar = false;
						break;
					}
				}
				if (podeSalvar) {
					data.add(dado);
				}
			}
			gravador.gravaTextoEmArquivo(data, DATA_DIRECTORY + "animals.txt");
		} finally {
		}
	}

	/**
	 * Método para ler os {@code Usuarios} salvos pelo método
	 * {@code gravarDados(SistemaAdocaoAnimais)}.
	 * 
	 * @return {@code List<Usuario>} com todos os usuários lidos.
	 * 
	 * @throws IOException Caso não consiga ler os dados.
	 */
	private static List<Usuario> lerUsuarios() throws IOException {
		GravadorDeDados gravador = new GravadorDeDados();
		List<String> usersStringList = gravador.recuperaTextoEmArquivo(DATA_DIRECTORY + "users.txt");

		List<Usuario> userList = new ArrayList<>(0);
		for (String u : usersStringList) {
			List<Requisito> requisitos = new ArrayList<>(0);

			// Divide a String a usando a constante do separador
			String[] uSplit = u.split(SEPARADOR);
			if (!uSplit[3].equals("NONE")) {
				String[] userReqs = uSplit[3].split(REQUISITO_SEPARADOR);
				for (String reqsString : userReqs) {
					String[] reqUser = reqsString.split(REQUISITO_INTERNAL_SEPARADOR);
					requisitos.add(new Requisito(reqUser[0], reqUser[1]));
				}
			}
			userList.add(new Usuario(uSplit[0], uSplit[1], uSplit[2], requisitos));
		}
		return userList;
	}

	/**
	 * Método para ler os {@code Animais} salvos pelo método
	 * {@code gravarDados(SistemaAdocaoAnimais)}.
	 * 
	 * @return {@code List<Animal>} com todos os animais lidos.
	 * 
	 * @throws IOException Caso não consiga ler os dados.
	 */

	private static List<Animal> lerAnimais() throws IOException {
		GravadorDeDados gravador = new GravadorDeDados();
		List<String> animaisStringList = gravador.recuperaTextoEmArquivo(DATA_DIRECTORY + "animals.txt");

		List<Animal> animaisList = new ArrayList<>(0);
		for (String a : animaisStringList) {
			// Divide a String a usando a constante do separador
			String[] aSplit = a.split(SEPARADOR);

			// Atribui um valor aos atributos do Animal
			String tipoAnimal = aSplit[0];

			String nome = aSplit[1], codigo = aSplit[2], dataNascimento = aSplit[3], tamanho = aSplit[4],
					cor = aSplit[7], sexo = aSplit[8], tipoAlimentacao = aSplit[12], detalhes = aSplit[13];

			String[] stringsBArray = { aSplit[5], aSplit[6], aSplit[9], aSplit[10], aSplit[11] };
			boolean[] booleanArray = new boolean[stringsBArray.length];

			for (int i = 0; i < stringsBArray.length; i++) {
				booleanArray[i] = Boolean.parseBoolean(stringsBArray[1]);
			}
			boolean ehBarulhento = booleanArray[0], ehManso = booleanArray[1], temPedigree = booleanArray[2],
					estahComVacinasEmDia = booleanArray[3], estahCastrado = booleanArray[4];

			// Instancia o animal e adiciona na lista
			Animal a1 = null;
			if (tipoAnimal.equals("Gato")) {
				a1 = new Gato(nome, codigo, dataNascimento, tamanho, ehBarulhento, ehManso, cor, sexo, temPedigree,
						estahComVacinasEmDia, estahCastrado, tipoAlimentacao, detalhes);
			} else if (tipoAnimal.equals("Cachorro")) {
				a1 = new Cachorro(nome, codigo, dataNascimento, tamanho, ehBarulhento, ehManso, cor, sexo, temPedigree,
						estahComVacinasEmDia, estahCastrado, tipoAlimentacao, detalhes);
			}

			if (!aSplit[14].equals("NONE")) {
				a1.setDono(new Usuario(aSplit[14], aSplit[15], "", null));
			}

			animaisList.add(a1);
		}
		return animaisList;
	}

	/**
	 * Pede uma entrada do usuário pelo teclado
	 *
	 * @param mensagem A mensagem que aparece na tela
	 * 
	 * @return Uma {@code String} com a entrada do usuário
	 * 
	 * @throws OperacaoCanceladaException se o usuário pressionar "Cancel"
	 */
	private static String pedirDado(String mensagem) throws OperacaoCanceladaException {
		boolean primeiraTentativa = true;
		boolean dadoValido = false;
		boolean charInvalidoNoDado = false;
		boolean dadoVazio = true;
		boolean none = false;

		String dado = "";
		while (!dadoValido) {
			if (primeiraTentativa) {
				// Verifica se é a primeira vez que usuário informa o dado
				dado = JOptionPane.showInputDialog(mensagem);

				primeiraTentativa = false;

			} else {
				// Caso não seja, ele verifica qual erro aconteceu no preenchimento do dado
				String aviso = "";
				if (charInvalidoNoDado) {
					aviso = "Caractere inválido inserido: \"/\", \"@\", \"#\", \"\\\".";
				} else if (dadoVazio) {
					aviso = "Dado não inserido.";
				} else if (none) {
					aviso = "Entrada de informação não pode ser \"NONE\"";
				}

				dado = JOptionPane.showInputDialog(aviso + "\nTente novamente.\n\n" + mensagem);
			}
			// Verifica se o botão não "Cancel" foi pressionado
			if (dado != null) {
				// Verifica se foi inserido alguma coisa no dado
				dadoVazio = dado.replace(" ", "").isEmpty();

				// Remove espaços vazios no começo e no final do dado caso o dado não seja vazio
				if (!dadoVazio) {
					while (dado.charAt(0) == ' ') {
						dado = dado.substring(1);
					}
					while (dado.endsWith(" ")) {
						dado = dado.substring(0, dado.length() - 1);
					}
				}

				// Verifica se foi digitado "NONE" no dado
				none = dado.equalsIgnoreCase("none");

				// Verifica se foi inserido algum caractere invalido no dado
				ArrayList<String> charsInvalidos = new ArrayList<>(0);
				for (String invalid : CHARS_INVALIDOS) {
					if (dado.contains(invalid)) {
						charsInvalidos.add(invalid);
					}
				}

				if (!charsInvalidos.isEmpty()) {
					charInvalidoNoDado = true;
				} else {
					charInvalidoNoDado = false;
				}

				// Verifica se o dado é válido
				dadoValido = !(charInvalidoNoDado || dadoVazio || none);
			} else {
				throw new OperacaoCanceladaException("A operação foi cancelada pelo usuário.");
			}

		}
		return dado;
	}

	/**
	 * Pede que o usuário escolha uma opção dentre uma lista de opções
	 *
	 * @param mensagem A mensagem que aparece na tela
	 * @param opcoes   As opções que podem ser escolhidas pelo usuário
	 * 
	 * @return Um {@code Object} correspondente ao {@code Object} escolhido dentre
	 *         as {@code opcoes}
	 * 
	 * @throws OperacaoCanceladaException se o usuário pressionar "Cancel"
	 */
	private static Object pedirDado(String mensagem, Object[] opcoes) throws OperacaoCanceladaException {
		// O método agora não retorna uma String e sim um Object
		Object dado = null;

		// Vou perguntar qual a escolha da pessoa e agora não tem toString() porque não
		// quero mais uma String, deixa no Object mesmo
		dado = JOptionPane.showInputDialog(null, mensagem, "Sistema de Adoção de Animais", JOptionPane.PLAIN_MESSAGE,
				null, opcoes, opcoes[0]);

		// Checando se a pessoa apertou "Cancel", não precisa mais checar se está vazio
		// porque não tem como já que ela clicou na opção
		if (dado == null) {
			throw new OperacaoCanceladaException("A operação foi cancelada pelo usuário.");
		}
		// Se chegar aqui é porque não lançou o erro então o dado não é null então a
		// pessoa escolheu alguma coisa
		return dado;
	}

	/**
	 * Pede para que o usuário escolha entre "Sim" ou "Não"
	 *
	 * @param mensagem A mensagem que aparece na tela
	 * 
	 * @return {@code true} se o usuário pressionar "Sim" e {@code false} se o
	 *         usuário pressionar "Não".
	 * 
	 * @throws OperacaoCanceladaException se o usuário pressionar "Cancel"
	 */
	private static boolean pedirSimNao(String mensagem) throws OperacaoCanceladaException {
		String[] simNao = { "Sim", "Não", "Cancelar" };
		String resposta = simNao[JOptionPane.showOptionDialog(null, mensagem, "Sistema Adoção de Animais",
				JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, simNao, simNao[0])];
		switch (resposta) {
			case "Sim":
				return true;
			case "Não":
				return false;
			case "Cancelar":
				throw new OperacaoCanceladaException("A operação foi cancelada pelo usuário");
		}
		return false;
	}
}