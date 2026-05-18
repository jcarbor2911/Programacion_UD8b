package com.daw.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	
	@GetMapping
	public ResponseEntity<List<TareaEntity>> list(){
		return ResponseEntity.ok(this.tareaService.findAll());
	}
	
	@GetMapping("/{idTarea}")
	public ResponseEntity<?> findById(@PathVariable long idTarea){
		try {
			return ResponseEntity.ok(this.tareaService.findById(idTarea));
		}
		catch(TareaNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody TareaEntity tareaEntity){
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(this.tareaService.create(tareaEntity));
		}
		catch(TareaException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}

}
