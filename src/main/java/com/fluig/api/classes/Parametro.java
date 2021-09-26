package com.fluig.api.classes;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import lombok.Data;

@Data
public class Parametro {
		
	@NotNull
	@PositiveOrZero
	private double preco;
	
	@NotNull
	@PositiveOrZero
	private double totalCidade;
	
	@NotNull
	@PositiveOrZero
	private double totalRodovia;
	
	Parametro(double preco, double totalCidade, double totalRodovia) {
		this.preco = preco;
		this.totalCidade = totalCidade;
		this.totalRodovia = totalRodovia;
	}
	
}
