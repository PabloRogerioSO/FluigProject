package com.fluig.api.classes;

import lombok.Data;

@Data
class PrevisaoDeGastos {
	
	private String nome;
	private String marca;
	private String modelo;
	private int ano;
	private double consumoCombustivel;
	private double valorTotal;
	
	PrevisaoDeGastos(String nome, String marca, String modelo, int ano, double consumoCombustivel,
			double valorTotal) {
		this.nome = nome;
		this.marca = marca;
		this.modelo = modelo;
		this.ano = ano;
		this.consumoCombustivel = consumoCombustivel;
		this.valorTotal = valorTotal;
	}

	PrevisaoDeGastos() {
	}
	
}
