package model.entities;

import java.io.Serializable;

//criando a classe department, q implementa a INTERFACE SERIALIZABLE
public class Department implements Serializable{

	//implementando o SERIAZABLE... para transformar os OBJETOS em sequencia
	//de BITS... isso e para ser guardado em arquivo/ ou trafegado em rede, etc...
	private static final long serialVersionUID = 1L;
	
	//declarando os atributos/variaveis da classe
	private Integer id;
	private String name;
	
	
	//construindo o construtor SEM argumentos
	public Department() {
	}


	//construindo o construtor com argumentos
	public Department(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	//implementando os GET e SET para podermos alterar os valores das
	//variaeis/atributos ID e NAME
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}


	
	//gerando o HASHCODE para podermos COMPARAR o valor dos OBJETOS pelo CONTEUDO
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Department other = (Department) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	//gerando o TOSTRING para podermos imprimir de FORMA LEGIVEL
	@Override
	public String toString() {
		return "Department [id=" + id + ", name=" + name + "]";
	}

	
}
