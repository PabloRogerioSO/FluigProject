package com.fluig.api.classes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
class PrevisaoDeGastosController {
	
	private final VeiculoRepository veiculoRepository;
	
	PrevisaoDeGastosController(VeiculoRepository veiculoRepository) {
		this.veiculoRepository = veiculoRepository;
	}
	
	@PostMapping("/previsaoDeGastos/response")
    @ResponseBody
    public List<PrevisaoDeGastos> previsaoDeGastosResponse(@RequestBody @Valid Parametro parametro) {
		
		List<Veiculo> veiculos = StreamSupport.stream(veiculoRepository.findAll().spliterator(), false)
				.collect(Collectors.toList());
		
		PrevisaoDeGastos[] previsao = new PrevisaoDeGastos[veiculos.size()];
		
		List<PrevisaoDeGastos> response = new ArrayList<>();
		
		Veiculo veiculo;
		
		for(int i = 0; i < veiculos.size(); i++) {
			
			veiculo = veiculos.get(i);
			previsao[i] = new PrevisaoDeGastos(null, null, null, 0, 0, 0);

			previsao[i].setNome(veiculo.getNome());
			previsao[i].setMarca(veiculo.getMarca());
			previsao[i].setModelo(veiculo.getModelo());
			previsao[i].setAno(veiculo.getDataFabricacao().getYear());
			previsao[i].setConsumoCombustivel(
					veiculo.getConsumoCidade() * parametro.getTotalCidade()
					+ veiculo.getConsumoRodovia() * parametro.getTotalRodovia());
			previsao[i].setValorTotal(previsao[i].getConsumoCombustivel() * parametro.getPreco());
		}
				
		for(int i = 0; i < previsao.length; i++) {
			response.add(previsao[i]);
		}
		
		return response.stream().sorted(Comparator.comparing(PrevisaoDeGastos::getValorTotal)).collect(Collectors.toList());
     }
}
