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
	
	public List<TareaEntity> findAll() {
		return this.tareaRepository.findAll();
	}
	
	public TareaEntity findById(long idTarea) {
		if(!this.tareaRepository.existsById(idTarea)) {
			throw new TareaNotFoundException("No se encuentra la tarea con id: " + idTarea);
		}
		
		return this.tareaRepository.findById(idTarea).get();
	}
	
	public TareaEntity create(TareaEntity t) {
		if(t.getFechaCreacion() != null) {
			throw new TareaException("No se puede modificar la fecha de creación de una tarea. ");
		}
		if(t.getEstado() != null) {
			throw new TareaException("No se puede modificar el estado de una tarea. ");
		}
		if(t.getFechaVencimiento().isBefore(LocalDate.now())) {
			throw new TareaException("La fecha de vencimiento no puede ser anterior a la fecha actual. ");
		}
		
		t.setId(0);
		t.setFechaCreacion(LocalDate.now());
		t.setEstado(Estado.PENDIENTE);
		
		return this.tareaRepository.save(t);
	}
	
	
	
	
	public TareaEntity iniciarTarea(long idTarea) {		
		TareaEntity t = this.findById(idTarea);
		
		if(!t.getEstado().equals(Estado.PENDIENTE)) {
			throw new TareaException("No se puede iniciar una tarea que ya ha sido iniciada. ");
		}
		
		t.setEstado(Estado.EN_PROGRESO);
		
		return this.tareaRepository.save(t);
	}
	
	

	
}
