package com.daw.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daw.persistence.entities.JuegoEntity;
import com.daw.persistence.entities.enums.Tipo;

public interface JuegoRepository extends JpaRepository<JuegoEntity, Long> {

	List<JuegoEntity> findByGenero(String genero);

	List<JuegoEntity> findByNombreContainingIgnoreCase(String nombre);

	List<JuegoEntity> findByPlataformasContainingIgnoreCase(String plataforma);

	List<JuegoEntity> findByTipo(Tipo tipo);

	List<JuegoEntity> findByPrecioBetween(Double min, Double max);

	List<JuegoEntity> findTop5ByOrderByDescargasDesc();

}
