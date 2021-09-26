package com.fluig.api.classes;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.afford;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.Valid;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class VeiculoController {

	private final VeiculoRepository repository;

	VeiculoController(VeiculoRepository repository) {
		this.repository = repository;
	}

	@GetMapping("/veiculos")
	public ResponseEntity<CollectionModel<EntityModel<Veiculo>>> findAll() {

		List<EntityModel<Veiculo>> veiculoReferencia = StreamSupport.stream(repository.findAll().spliterator(), false)
				.map(veiculo -> EntityModel.of(veiculo,
						linkTo(methodOn(VeiculoController.class).findOne(veiculo.getId())).withSelfRel()
							.andAffordance(afford(methodOn(VeiculoController.class).updateVeiculo(null, veiculo.getId())))
							.andAffordance(afford(methodOn(VeiculoController.class).deleteVeiculo(veiculo.getId()))),
						linkTo(methodOn(VeiculoController.class).findAll()).withRel("veiculos")))
				.collect(Collectors.toList());
		
		return ResponseEntity.ok(CollectionModel.of( //
				veiculoReferencia, //
				linkTo(methodOn(VeiculoController.class).findAll()).withSelfRel()
						.andAffordance(afford(methodOn(VeiculoController.class).newVeiculo(null)))));
	}

	@PostMapping("/veiculos")
	public ResponseEntity<?> newVeiculo(@RequestBody @Valid Veiculo veiculo) {

		Veiculo veiculoSalvo = repository.save(veiculo);

		return EntityModel.of(veiculoSalvo,
				linkTo(methodOn(VeiculoController.class).findOne(veiculoSalvo.getId())).withSelfRel()
						.andAffordance(afford(methodOn(VeiculoController.class).updateVeiculo(null, veiculoSalvo.getId())))
						.andAffordance(afford(methodOn(VeiculoController.class).deleteVeiculo(veiculoSalvo.getId()))),
				linkTo(methodOn(VeiculoController.class).findAll()).withRel("veiculos")).getLink(IanaLinkRelations.SELF)
						.map(Link::getHref) //
						.map(href -> {
							try {
								return new URI(href);
							} catch (URISyntaxException e) {
								throw new RuntimeException(e);
							}
						}) //
						.map(uri -> ResponseEntity.noContent().location(uri).build())
						.orElse(ResponseEntity.badRequest().body("Unable to create " + veiculo));
	}
	
	@GetMapping("/veiculos/{id}")
	public ResponseEntity<EntityModel<Veiculo>> findOne(@PathVariable long id) {

		return repository.findById(id) //
				.map(veiculo -> EntityModel.of(veiculo, //
						linkTo(methodOn(VeiculoController.class).findOne(veiculo.getId())).withSelfRel()
						.andAffordance(afford(methodOn(VeiculoController.class).updateVeiculo(null, veiculo.getId())))
						.andAffordance(afford(methodOn(VeiculoController.class).deleteVeiculo(veiculo.getId()))),
						linkTo(methodOn(VeiculoController.class).findAll()).withRel("veiculos"))) //
				.map(ResponseEntity::ok) //
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PutMapping("/veiculos/{id}")
	public ResponseEntity<?> updateVeiculo(@RequestBody Veiculo veiculo, @PathVariable long id) {

		Veiculo veiculoAtualizando = veiculo;
		veiculoAtualizando.setId(id);

		Veiculo veiculoAtualizado = repository.save(veiculoAtualizando);

		return EntityModel.of(veiculoAtualizado,
				linkTo(methodOn(VeiculoController.class).findOne(veiculoAtualizado.getId())).withSelfRel()
						.andAffordance(afford(methodOn(VeiculoController.class).updateVeiculo(null, veiculoAtualizado.getId())))
						.andAffordance(afford(methodOn(VeiculoController.class).deleteVeiculo(veiculoAtualizado.getId()))),
				linkTo(methodOn(VeiculoController.class).findAll()).withRel("veiculos")).getLink(IanaLinkRelations.SELF)
						.map(Link::getHref).map(href -> {
							try {
								return new URI(href);
							} catch (URISyntaxException e) {
								throw new RuntimeException(e);
							}
						}) //
						.map(uri -> ResponseEntity.noContent().location(uri).build()) //
						.orElse(ResponseEntity.badRequest().body("Unable to update " + veiculoAtualizando));
	}
	
	@DeleteMapping("/veiculos/{id}")
	public ResponseEntity<?> deleteVeiculo(@PathVariable long id) {

		repository.deleteById(id);

		return ResponseEntity.noContent().build();
	}
		
}