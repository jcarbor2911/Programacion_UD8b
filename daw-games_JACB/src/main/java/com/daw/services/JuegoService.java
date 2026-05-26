package com.daw.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.persistence.entities.JuegoEntity;
import com.daw.persistence.entities.enums.Tipo;
import com.daw.persistence.repositories.JuegoRepository;
import com.daw.services.exceptions.JuegoException;
import com.daw.services.exceptions.JuegoNotFoundException;

@Service
public class JuegoService {

	@Autowired
	private JuegoRepository juegoRepository;

	// CRUD básico

	public List<JuegoEntity> findAll() {
		return this.juegoRepository.findAll();
	}

	public JuegoEntity findById(long idJuego) {
		if (!this.juegoRepository.existsById(idJuego)) {
			throw new JuegoNotFoundException("No se encuentra el juego con id: " + idJuego);
		}
		return this.juegoRepository.findById(idJuego).get();
	}

	public JuegoEntity create(JuegoEntity j) {
		if (j.getCompletado() != null) {
			throw new JuegoException("No se puede establecer el atributo completado manualmente al crear un juego.");
		}

		// Si no se indica fecha de lanzamiento, se establece la de hoy
		if (j.getFechaLanzamiento() == null) {
			j.setFechaLanzamiento(LocalDate.now());
		}

		j.setId(0);
		j.setCompletado(false);

		return this.juegoRepository.save(j);
	}

	public JuegoEntity update(long idJuego, JuegoEntity j) {
		JuegoEntity juegoExistente = this.findById(idJuego);

		if (j.getCompletado() != null) {
			throw new JuegoException("No se puede modificar el atributo completado desde este endpoint.");
		}

		juegoExistente.setNombre(j.getNombre());
		juegoExistente.setGenero(j.getGenero());
		juegoExistente.setPlataformas(j.getPlataformas());
		juegoExistente.setPrecio(j.getPrecio());
		juegoExistente.setDescargas(j.getDescargas());
		juegoExistente.setFechaLanzamiento(j.getFechaLanzamiento());
		juegoExistente.setTipo(j.getTipo());

		return this.juegoRepository.save(juegoExistente);
	}

	public void delete(long idJuego) {
		if (!this.juegoRepository.existsById(idJuego)) {
			throw new JuegoNotFoundException("No se encuentra el juego con id: " + idJuego);
		}
		this.juegoRepository.deleteById(idJuego);
	}

	// Filtros

	public List<JuegoEntity> findByGenero(String genero) {
		return this.juegoRepository.findByGenero(genero);
	}

	public List<JuegoEntity> findByNombre(String nombre) {
		return this.juegoRepository.findByNombreContainingIgnoreCase(nombre);
	}

	public List<JuegoEntity> findByPlataforma(String plataforma) {
		return this.juegoRepository.findByPlataformasContainingIgnoreCase(plataforma);
	}

	public List<JuegoEntity> findExpansiones() {
		return this.juegoRepository.findByTipo(Tipo.EXPANSION);
	}

	public List<JuegoEntity> findDlc() {
		return this.juegoRepository.findByTipo(Tipo.DLC);
	}

	public List<JuegoEntity> findBase() {
		return this.juegoRepository.findByTipo(Tipo.BASE);
	}

	public List<JuegoEntity> findByRangoPrecio(Double min, Double max) {
		return this.juegoRepository.findByPrecioBetween(min, max);
	}

	public List<JuegoEntity> findTop5Exitos() {
		return this.juegoRepository.findTop5ByOrderByDescargasDesc();
	}

	// Reto

	public JuegoEntity toggleCompletado(long idJuego) {
		JuegoEntity j = this.findById(idJuego);

		// Actúa como interruptor, si está completado lo desmarca, sino lo marca
		j.setCompletado(!j.getCompletado());

		return this.juegoRepository.save(j);
	}

	public List<JuegoEntity> aplicarDescuento(String genero, Double porcentaje) {
		if (porcentaje < 0 || porcentaje > 1) {
			throw new JuegoException("El porcentaje debe estar entre 0 y 1 (en tanto por 1).");
		}

		List<JuegoEntity> juegos = this.juegoRepository.findByGenero(genero);

		if (juegos.isEmpty()) {
			throw new JuegoNotFoundException("No se encuentran juegos del género: " + genero);
		}

		for (JuegoEntity j : juegos) {
			double nuevoPrecio = j.getPrecio() * (1 - porcentaje);
			// Redondeamos a 2 decimales para respetar el tipo DECIMAL(5,2) de la BD
			j.setPrecio(Math.round(nuevoPrecio * 100.0) / 100.0);
		}

		return this.juegoRepository.saveAll(juegos);
	}

}