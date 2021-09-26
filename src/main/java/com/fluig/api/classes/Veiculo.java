package com.fluig.api.classes;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PositiveOrZero;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
class Veiculo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	private String marca;
	private String modelo;
	
	@NotNull
	@Past
	private LocalDate dataFabricacao;
	
	@NotNull
	@PositiveOrZero
	private double consumoCidade;
	
	@NotNull
	@PositiveOrZero
	private double consumoRodovia;
	
	
	Veiculo(String nome, String marca, String modelo, LocalDate dataFabricacao, double consumoCidade,
			double consumoRodovia) {
		this.nome = nome;
		this.marca = marca;
		this.modelo = modelo;
		this.dataFabricacao = dataFabricacao;
		this.consumoCidade = consumoCidade;
		this.consumoRodovia = consumoRodovia;
	}

}