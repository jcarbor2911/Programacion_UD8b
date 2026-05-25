package com.daw.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.persistence.entities.TareaEntity;
import com.daw.persistence.entities.enums.Estado;
import com.daw.persistence.repositories.TareaRepository;
import com.daw.services.exceptions.TareaException;
import com.daw.services.exceptions.TareaNotFoundException;

@Service
public class TareaService {

	@Autowired
	private TareaRepository tareaRepository;

	// CRUD básico

	public List<TareaEntity> findAll() {
		return this.tareaRepository.findAll();
	}

	public TareaEntity findById(long idTarea) {
		if (!this.tareaRepository.existsById(idTarea)) {
			throw new TareaNotFoundException("No se encuentra la tarea con id: " + idTarea);
		}
		return this.tareaRepository.findById(idTarea).get();
	}

	public TareaEntity create(TareaEntity t) {
		if (t.getFechaCreacion() != null) {
			throw new TareaException("No se puede establecer la fecha de creación manualmente.");
		}
		if (t.getEstado() != null) {
			throw new TareaException("No se puede establecer el estado manualmente al crear una tarea.");
		}
		if (t.getFechaVencimiento() == null) {
			throw new TareaException("La fecha de vencimiento es obligatoria.");
		}
		if (!t.getFechaVencimiento().isAfter(LocalDate.now())) {
			throw new TareaException("La fecha de vencimiento debe ser posterior a la fecha de creación (hoy).");
		}

		t.setId(0);
		t.setFechaCreacion(LocalDate.now());
		t.setEstado(Estado.PENDIENTE);

		return this.tareaRepository.save(t);
	}

	public TareaEntity update(long idTarea, TareaEntity t) {
		TareaEntity tareaExistente = this.findById(idTarea);

		if (t.getEstado() != null) {
			throw new TareaException("No se puede modificar el estado de la tarea desde este endpoint.");
		}
		if (t.getFechaCreacion() != null) {
			throw new TareaException("No se puede modificar la fecha de creación de la tarea.");
		}
		if (t.getFechaVencimiento() == null) {
			throw new TareaException("La fecha de vencimiento es obligatoria.");
		}
		if (!t.getFechaVencimiento().isAfter(tareaExistente.getFechaCreacion())) {
			throw new TareaException("La fecha de vencimiento debe ser posterior a la fecha de creación.");
		}

		tareaExistente.setTitulo(t.getTitulo());
		tareaExistente.setDescripcion(t.getDescripcion());
		tareaExistente.setFechaVencimiento(t.getFechaVencimiento());

		return this.tareaRepository.save(tareaExistente);
	}

	public void delete(long idTarea) {
		if (!this.tareaRepository.existsById(idTarea)) {
			throw new TareaNotFoundException("No se encuentra la tarea con id: " + idTarea);
		}
		this.tareaRepository.deleteById(idTarea);
	}

	// Cambios de estado

	public TareaEntity iniciarTarea(long idTarea) {
		TareaEntity t = this.findById(idTarea);

		if (!t.getEstado().equals(Estado.PENDIENTE)) {
			throw new TareaException("Solo se pueden iniciar tareas en estado PENDIENTE.");
		}

		t.setEstado(Estado.EN_PROGRESO);
		return this.tareaRepository.save(t);
	}

	public TareaEntity completarTarea(long idTarea) {
		TareaEntity t = this.findById(idTarea);

		if (!t.getEstado().equals(Estado.EN_PROGRESO)) {
			throw new TareaException("Solo se pueden completar tareas en estado EN_PROGRESO.");
		}

		t.setEstado(Estado.COMPLETADA);
		return this.tareaRepository.save(t);
	}

	// Filtros 

	public List<TareaEntity> findPendientes() {
		return this.tareaRepository.findByEstado(Estado.PENDIENTE);
	}

	public List<TareaEntity> findEnProgreso() {
		return this.tareaRepository.findByEstado(Estado.EN_PROGRESO);
	}

	public List<TareaEntity> findCompletadas() {
		return this.tareaRepository.findByEstado(Estado.COMPLETADA);
	}

	public List<TareaEntity> findVencidas() {
		return this.tareaRepository.findByFechaVencimientoBefore(LocalDate.now());
	}

	public List<TareaEntity> findNoVencidas() {
		return this.tareaRepository.findByFechaVencimientoAfter(LocalDate.now());
	}

	public List<TareaEntity> findByTitulo(String titulo) {
		return this.tareaRepository.findByTituloContaining(titulo);
	}

}