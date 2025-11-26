package br.senac.tads.dsw.blog.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.senac.tads.dsw.blog.persistence.entities.TextoEntity;

public interface TextoRepository extends JpaRepository<TextoEntity, Long> {

}
