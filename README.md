![banner](https://user-images.githubusercontent.com/121516171/211205818-3d938699-7b96-426e-9f32-bbe552eef757.png)
<p align="center">
<img src="https://img.shields.io/badge/Testes-40%20sucessos%2C%200%20falhas-green?style=for-the-badge&logo=appveyor">
<img src="https://img.shields.io/badge/Build-Sucesso-yellow?style=for-the-badge&logo=appveyor">
<img src="https://img.shields.io/badge/Licen%C3%A7a-MIT-yellowgreen?style=for-the-badge&logo=appveyor">
<img src="https://img.shields.io/badge/Vers%C3%A3o-1.0.0-blue?style=for-the-badge&logo=appveyor">
</p>

<a name="ancora"></a>
# Índice
- [Descricao](#descricao)
- [Obtenção](#obtencao)
	- [Maven](#maven)
	- [Jar](#jar)
- [Funcionalidades](#funcionalidades)
- [Como utilizar](#utilizar)
- [Exemplo de utilização](#exemplos)
	- [Dependência de classes concretas](#exemplos-classes)
	- [Dependência de interfaces](#exemplos-interfaces)
- [Tecnologias utilizadas](#tecnologias)
- [Licença](#licenca)
- [Autor](#autor)

<a name="descricao"></a>
## Descrição
A biblioteca é responsável por promover a funcionalidade de inversão de controle e injeção de dependências de qualquer objeto que desejar.

<a name="obtencao"></a>
## Obtenção

<a name="maven"></a>
### Dependência Maven
~~~xml
<dependency>
  <groupId>com.github.emanoelfigueredo</groupId>
  <artifactId>container-ioc</artifactId>
  <version>1.0.0</version>
</dependency>
~~~

<a name="jar"></a>
### Download Jar
<a href="https://github.com/emanoelfigueredo/container-ioc/raw/main/Container%20IoC.jar">Container IoC.jar</a>

<a name="funcionalidades"></a>
## Funcionalidades
- Injetar as depêndencias no construtor de um objeto. Podendo ser classes concetras, ou intefaces, desde que estejam configuradas numa classe de configuração.

<a name="utilizar"></a>
## Como utilizar
Para utilizar a funcionalidade em construtores, utilize a anotação __@Injeção__ no construtor desejado.

> OBS: Se alguma das dependências for uma interface, é necessário configurá-la numa classes de configuração.

Para obter os objetos, utilize o objeto __ContainerIoc__. O método responsável é __getIntancia()__. Na instânciação do objeto __ContainerIoc__, insira o pacote raiz do projeto. No caso será [br.com.efigueredo.container].

> OBS: Caso insira um pacote de menor hierarquia, corre o risco de nem todas as classes de seu projeto serem visualizadas pelo container ioc. Portanto, existe a possibilidade de alguma classe de configuração de dependências não ser encontrada.

~~~java
import br.com.efigueredo.container.ContainerIoc;
import br.com.efigueredo.container.exception.ClasseIlegalParaIntanciaException;
import br.com.efigueredo.container.exception.InversaoDeControleInvalidaException;
import br.com.efigueredo.project_loader.projeto.exception.PacoteInexistenteException;

public class Main {

	public static void main(String[] args) throws PacoteInexistenteException, InversaoDeControleInvalidaException, ClasseIlegalParaIntanciaException {

		ContainerIoc container = new ContainerIoc("br.com.efigueredo.container");
		Class<?> classeObjeto;
		Object instancia = container.getIntancia(classeObjeto);
		
	}

}
~~~

<a name="exemplos"></a>
## Exemplo de utilização
Utilize a biblioteca quando for necessário resolver as dependências na intânciação de objetos.

<a name="exemplos-classes"></a>
### Dependência de classes concretas

> Exemplo: 
> Classe que será intânciada necessita de um objeto da classe concreta UsuarioDao.
~~~java
import br.com.efigueredo.container.anotacao.Injecao;

public class UsuarioService {

	private UsuarioDao usuarioDao;

	@Injecao
	public UsuarioService(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;	
	}

	public UsuarioDao getUsuarioDao() {
		return this.usuarioDao;
	}
	
}
~~~

O ContainerIoC pode instânciar o objeto UsuarioDao de duas maneiras
- __Construtor padrão__<br>
Se a classe não tiver um construtor anotado com __@Injecao__, então irá instânciar pelo construtor padrão.
> OBS: Se não houver construtor padrão nem anotado com __@Injeção__, uma exceção será lançada.
~~~java
public class UsuarioDao implements Dao {

	public UsuarioDao() {
		
	}
	
}
~~~
- __Construtor anotado com @Injecao__<br>
Se houver um construtor anotado, ele será o escolhido,
> OBS: Se houver mais de um construtor anotado com __@Injeção__, uma exceção será lançada.
~~~java
import br.com.efigueredo.container.anotacao.Injecao;

public class UsuarioDao implements Dao {

	private ConexaoComBancoDeDados conexaoDB;

	@Injecao
	public UsuarioDao(ConexaoComBancoDeDados conexaoDB) {
		this.conexaoDB = conexaoDB;
	}
	
}
~~~
As depêndências de todos os objetos seguintes serão resolvidos consecutivamente.

#### Resultado

Utilizando o objeto __ContainerIoc__, podemos obter uma intância de qualquer classes pelo método __getIntancia()__.
~~~java
import br.com.efigueredo.container.ContainerIoc;
import br.com.efigueredo.container.exception.ClasseIlegalParaIntanciaException;
import br.com.efigueredo.container.exception.InversaoDeControleInvalidaException;
import br.com.efigueredo.project_loader.projeto.exception.PacoteInexistenteException;

public class Main {

	public static void main(String[] args) throws PacoteInexistenteException, InversaoDeControleInvalidaException, ClasseIlegalParaIntanciaException {
		ContainerIoc container = new ContainerIoc();
		UsuarioService intanciaUsuarioService = (UsuarioService) container.getIntancia(UsuarioService.class);
		UsuarioDao usuarioDao = intanciaUsuarioService.getUsuarioDao();

		System.out.println(usuarioDao instanceof UsuarioDao);		
	}
  
}
~~~
> ___Saída:__<br> 
true

<a name="exemplos-interfaces"></a>
### Dependência de interfaces
> Exemplo: 
> A classe UsuarioService necessita de um objeto da inteface Dao.
~~~java
import br.com.efigueredo.container.anotacao.Injecao;

public class UsuarioService {

	private Dao dao;

	@Injecao
	public UsuarioService(Dao dao) {
		this.dao = dao;	
	}

	public Dao getDao() {
		return this.dao;
	}
	
}
~~~
~~~java
public interface Dao {

}
~~~

Não é possível instânciar intefaces. Portanto, é necessário que exista uma configuração para qual classe concreta a interface Dao deve apontar.
Para isso, é necessário criar uma classe de configuração de dependências. 

#### Classe de configuração de dependências
Deve estar anotada com __@ConfiguracaoDependencia__ e __extender a classes abstrata ConfiguracaoDependenciaIoC__.
~~~java
import br.com.efigueredo.container.anotacao.ConfiguracaoDependencia;
import br.com.efigueredo.container.configuracao.ConfiguracaoDependenciaIoC;
import br.com.efigueredo.container.configuracao.InterfaceConfiguracaoIoCBuilder;
import br.com.efigueredo.container.exception.ConfiguracaoDependenciaInvalidaException;

@ConfiguracaoDependencia
public class Configuracao extends ConfiguracaoDependenciaIoC {

	@Override
	public void configuracao(InterfaceConfiguracaoIoCBuilder icb) throws ConfiguracaoDependenciaInvalidaException {
		
		
	}

}
~~~
O objeto __InterfaceConfiguracaoIoCBuilder__ será disponibilizado para que você possa configurar para qual classe concreta uma interface irá apontar.
Através do método __adicionarConfiguracao(ClasseChave, ClasseValor)__.

~~~java
import br.com.efigueredo.container.anotacao.ConfiguracaoDependencia;
import br.com.efigueredo.container.configuracao.ConfiguracaoDependenciaIoC;
import br.com.efigueredo.container.configuracao.InterfaceConfiguracaoIoCBuilder;
import br.com.efigueredo.container.exception.ConfiguracaoDependenciaInvalidaException;

@ConfiguracaoDependencia
public class Configuracao extends ConfiguracaoDependenciaIoC {

	@Override
	public void configuracao(InterfaceConfiguracaoIoCBuilder icb) throws ConfiguracaoDependenciaInvalidaException {
		icb.adicionarConfiguracao(Dao.class, UsuarioDao.class);
	}

}
~~~
Note que, agora a inteface Dao, aponta para a classe concretaa UsuarioDao. Dessa forma, todas as vezes que a dependência for a inteface Dao, será instânciado um objeto UsuarioDao e injetado no construtor<br>

> OBS: A classe concreta deve ser uma implementação da interface. Caso contrário, uma exceção será lançada.

#### Resultado

Utilizando o objeto __ContainerIoc__, podemos obter uma intância de qualquer classes pelo método __getIntancia()__.
~~~java
import java.util.Arrays;

import br.com.efigueredo.container.ContainerIoc;
import br.com.efigueredo.container.exception.ClasseIlegalParaIntanciaException;
import br.com.efigueredo.container.exception.InversaoDeControleInvalidaException;
import br.com.efigueredo.project_loader.projeto.exception.PacoteInexistenteException;

public class Main {

	public static void main(String[] args) throws PacoteInexistenteException, InversaoDeControleInvalidaException, ClasseIlegalParaIntanciaException {
		
		ContainerIoc container = new ContainerIoc();
		UsuarioService instanciaUsuarioService = (UsuarioService) container.getIntancia(UsuarioService.class);
		
		Dao dao = instanciaUsuarioService.getDao();
		
		// Objeto de referência dao é instância da inteface UsuarioDao
		System.out.println(dao instanceof UsuarioDao);
		
		// Objeto de referência dao é um objeto do tipo Dao
		System.out.println(dao instanceof Dao);
		
		// Classe do objeto de referência dao
		System.out.println(dao.getClass());
		
		// A lista de todas as intefaces do objeto de referência dao possui a inteface de classe Dao.class
		System.out.println(Arrays.asList(dao.getClass().getInterfaces()).contains(Dao.class));
		
	}

}
~~~
> ___Saída:___<br> 
true<br>
true<br>
class t.UsuarioDao<br>
true

<a id="tecnologias"></a>
# Tecnologias utilizadas

- Java 1.8
    - Reflection API

<a id="licenca"></a>
# Licença

Essa biblioteca é licenciada pelo MIT.

<a id="autor"></a>
# Autor
<a href="https://github.com/emanoelfigueredo"><img src="https://avatars.githubusercontent.com/u/121516171?s=400&u=5a20eb0276bef0e61432f40e90b64a56df953ec9&v=4" width=115><br><sub>Emanoel Figueredo</sub></a>
