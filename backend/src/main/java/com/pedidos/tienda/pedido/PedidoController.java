package com.pedidos.tienda.pedido;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

	private final PedidoRepository repository;

	public PedidoController(PedidoRepository repository) {
		this.repository = repository;
	}

	@GetMapping
	public List<Pedido> listar() {
		return repository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Pedido> obtener(@PathVariable Long id) {
		return ResponseEntity.of(repository.findById(id));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Pedido crear(@RequestBody Pedido pedido) {
		pedido.setId(null);
		if (pedido.getEstado() == null) {
			pedido.setEstado("NUEVO");
		}
		return repository.save(pedido);
	}
}
