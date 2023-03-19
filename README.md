# demo-dao-jdbc

## Curso: Java COMPLETO 2022 Programação Orientada a Objetos + Projetos | Udemy

### http://educandoweb.com.br<br>
### Prof. Dr. Nelio Alves<br>
### Capítulo: Acesso a banco de dados com JDBC <br>

<p>versão com comentarios 
<a href="https://github.com/rodrigojfagundes/Java-COMPLETO-2022-POO-Projetos-Comentados-/tree/main/Sessao%2021%20-%20demo-dao-jdbc/287%20-%20DepartmentDao%20implementation%20and%20test/demo-dao-jdbc">aqui</a></p>

<b>Objetivo geral:</b>

 Conhecer os principais recursos do JDBC na teoria e prática<br>
 Elaborar a estrutura básica de um projeto com JDBC (criar, recuperar, atualizar, deletar dados)<br>
 Implementar o padrão DAO manualmente com JDBC

### Visão geral do JDBC

 JDBC (Java Database Connectivity): API padrão do Java para acesso a dados<br>
 Páginas oficiais: <br>
o https://docs.oracle.com/javase/8/docs/technotes/guides/jdbc/<br>
o https://docs.oracle.com/javase/8/docs/api/java/sql/package-summary.html<br>
 Pacotes: java.sql e javax.sql (API suplementar para servidores)



### Padrão de projeto DAO (Data Access Object) <br>
<b>Referências:</b>
https://www.devmedia.com.br/dao-pattern-persistencia-de-dados-utilizando-o-padrao-dao/30999<br>
https://www.oracle.com/technetwork/java/dataaccessobject-138824.html<br>
<b>Ideia geral do padrão DAO:</b><br>
Para cada entidade, haverá um objeto responsável por fazer acesso a dados relacionado a esta 
entidade. Por exemplo:<br>
  Cliente: ClienteDao<br>
  Produto: ProdutoDao<br>
  Pedido: PedidoDao<br>
Cada DAO será definido por uma interface.<br>
A injeção de dependência pode ser feita por meio do padrão de projeto Factory<br>



<div align="center">
<img src="https://raw.githubusercontent.com/rodrigojfagundes/imagens_para_readme/main/Sessao_21_imagem_2.jpg" />
</div>


### Department entity class<br>
<b>Entity class checklist:</b><br>
Attributes<br>
Constructors<br>
Getters/Setters<br>
hashCode and equals<br>
toString<br>
implements Serializable

<div align="center">
<img src="https://raw.githubusercontent.com/rodrigojfagundes/imagens_para_readme/main/Sessao_21_imagem_3.jpg" />
</div>
