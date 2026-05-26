package com.daw.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daw.persistence.entities.JuegoEntity;
import com.daw.services.JuegoService;
import com.daw.services.exceptions.JuegoException;
import com.daw.services.exceptions.JuegoNotFoundException;

@RestController
@RequestMapping("/juegos")
public class JuegoController {

	@Autowired
	private JuegoService juegoService;

	// GET

	@GetMapping
	public ResponseEntity<List<JuegoEntity>> list() {
		return ResponseEntity.ok(this.juegoService.findAll());
	}

	@GetMapping("/{idJuego}")
	public ResponseEntity<?> findById(@PathVariable long idJuego) {
		try {
			return ResponseEntity.ok(this.juegoService.findById(idJuego));
		} catch (JuegoNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}

	@GetMapping("/genero")
	public ResponseEntity<List<JuegoEntity>> findByGenero(@RequestParam String genero) {
		return ResponseEntity.ok(this.juegoService.findByGenero(genero));
	}

	@GetMapping("/nombre")
	public ResponseEntity<List<JuegoEntity>> findByNombre(@RequestParam String nombre) {
		return ResponseEntity.ok(this.juegoService.findByNombre(nombre));
	}

	@GetMapping("/plataforma")
	public ResponseEntity<List<JuegoEntity>> findByPlataforma(@RequestParam String plataforma) {
		return ResponseEntity.ok(this.juegoService.findByPlataforma(plataforma));
	}

	@GetMapping("/expansiones")
	public ResponseEntity<List<JuegoEntity>> findExpansiones() {
		return ResponseEntity.ok(this.juegoService.findExpansiones());
	}

	@GetMapping("/dlc")
	public ResponseEntity<List<JuegoEntity>> findDlc() {
		return ResponseEntity.ok(this.juegoService.findDlc());
	}

	@GetMapping("/base")
	public ResponseEntity<List<JuegoEntity>> findBase() {
		return ResponseEntity.ok(this.juegoService.findBase());
	}

	@GetMapping("/precio")
	public ResponseEntity<List<JuegoEntity>> findByRangoPrecio(@RequestParam Double min, @RequestParam Double max) {
		return ResponseEntity.ok(this.juegoService.findByRangoPrecio(min, max));
	}

	@GetMapping("/exitos")
	public ResponseEntity<List<JuegoEntity>> findTop5Exitos() {
		return ResponseEntity.ok(this.juegoService.findTop5Exitos());
	}

	// POST

	@PostMapping
	public ResponseEntity<?> create(@RequestBody JuegoEntity juegoEntity) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(this.juegoService.create(juegoEntity));
		} catch (JuegoException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}

	// PUT

	@PutMapping("/{idJuego}")
	public ResponseEntity<?> update(@PathVariable long idJuego, @RequestBody JuegoEntity juegoEntity) {
		try {
			return ResponseEntity.ok(this.juegoService.update(idJuego, juegoEntity));
		} catch (JuegoNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		} catch (JuegoException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}

	@PutMapping("/{idJuego}/completar")
	public ResponseEntity<?> toggleCompletado(@PathVariable long idJuego) {
		try {
			return ResponseEntity.ok(this.juegoService.toggleCompletado(idJuego));
		} catch (JuegoNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}

	@PutMapping("/descuento")
	public ResponseEntity<?> aplicarDescuento(@RequestParam String genero, @RequestParam Double porcentaje) {
		try {
			return ResponseEntity.ok(this.juegoService.aplicarDescuento(genero, porcentaje));
		} catch (JuegoNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		} catch (JuegoException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}

	// DELETE

	@DeleteMapping("/{idJuego}")
	public ResponseEntity<?> delete(@PathVariable long idJuego) {
		try {
			this.juegoService.delete(idJuego);
			return ResponseEntity.noContent().build();
		} catch (JuegoNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}

}