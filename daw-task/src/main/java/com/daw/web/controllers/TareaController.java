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

import com.daw.persistence.entities.TareaEntity;
import com.daw.services.TareaService;
import com.daw.services.exceptions.TareaException;
import com.daw.services.exceptions.TareaNotFoundException;

@RestController
@RequestMapping("/tareas")
public class TareaController {

	@Autowired
	private TareaService tareaService;

	// GET

	@GetMapping
	public ResponseEntity<List<TareaEntity>> list() {
		return ResponseEntity.ok(this.tareaService.findAll());
	}

	@GetMapping("/{idTarea}")
	public ResponseEntity<?> findById(@PathVariable long idTarea) {
		try {
			return ResponseEntity.ok(this.tareaService.findById(idTarea));
		} catch (TareaNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}

	@GetMapping("/pendientes")
	public ResponseEntity<List<TareaEntity>> findPendientes() {
		return ResponseEntity.ok(this.tareaService.findPendientes());
	}

	@GetMapping("/en_progreso")
	public ResponseEntity<List<TareaEntity>> findEnProgreso() {
		return ResponseEntity.ok(this.tareaService.findEnProgreso());
	}

	@GetMapping("/completadas")
	public ResponseEntity<List<TareaEntity>> findCompletadas() {
		return ResponseEntity.ok(this.tareaService.findCompletadas());
	}

	@GetMapping("/vencidas")
	public ResponseEntity<List<TareaEntity>> findVencidas() {
		return ResponseEntity.ok(this.tareaService.findVencidas());
	}

	@GetMapping("/no_vencidas")
	public ResponseEntity<List<TareaEntity>> findNoVencidas() {
		return ResponseEntity.ok(this.tareaService.findNoVencidas());
	}

	@GetMapping("/titulo")
	public ResponseEntity<List<TareaEntity>> findByTitulo(@RequestParam String titulo) {
		return ResponseEntity.ok(this.tareaService.findByTitulo(titulo));
	}

	// POST

	@PostMapping
	public ResponseEntity<?> create(@RequestBody TareaEntity tareaEntity) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(this.tareaService.create(tareaEntity));
		} catch (TareaException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}

	// PUT

	@PutMapping("/{idTarea}")
	public ResponseEntity<?> update(@PathVariable long idTarea, @RequestBody TareaEntity tareaEntity) {
		try {
			return ResponseEntity.ok(this.tareaService.update(idTarea, tareaEntity));
		} catch (TareaNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		} catch (TareaException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}

	@PutMapping("/{idTarea}/iniciar")
	public ResponseEntity<?> iniciar(@PathVariable long idTarea) {
		try {
			return ResponseEntity.ok(this.tareaService.iniciarTarea(idTarea));
		} catch (TareaNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		} catch (TareaException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}

	@PutMapping("/{idTarea}/completar")
	public ResponseEntity<?> completar(@PathVariable long idTarea) {
		try {
			return ResponseEntity.ok(this.tareaService.completarTarea(idTarea));
		} catch (TareaNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		} catch (TareaException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}

	// DELETE

	@DeleteMapping("/{idTarea}")
	public ResponseEntity<?> delete(@PathVariable long idTarea) {
		try {
			this.tareaService.delete(idTarea);
			return ResponseEntity.noContent().build();
		} catch (TareaNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}

}