package br.senac.tads.dsw.blog;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.senac.tads.dsw.blog.persistence.entities.TextoEntity;
import br.senac.tads.dsw.blog.persistence.repository.TextoRepository;

@Service
public class TextoService {

    @Autowired
    private TextoRepository textoRepository;

    private Texto toDto(TextoEntity entity) {
        Texto dto = new Texto();
        dto.setId(entity.getId());
        dto.setTitulo(entity.getTitulo());
        dto.setAutor(entity.getAutor());
        dto.setDataPublicacao(entity.getDataPublicacao());
        dto.setTexto(entity.getTexto());
        dto.setPublicado(entity.isPublicado());
        return dto;
    }

    private TextoEntity toEntity(Texto dto) {
        TextoEntity entity = new TextoEntity();
        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        entity.setTitulo(dto.getTitulo());
        entity.setAutor(dto.getAutor());
        entity.setDataPublicacao(dto.getDataPublicacao());
        entity.setTexto(dto.getTexto());
        entity.setPublicado(dto.isPublicado());
        return entity;
    }

    public List<Texto> findAll() {
        List<TextoEntity> entities = textoRepository.findAll();
        List<Texto> resultado = new ArrayList<>();
        for (TextoEntity entity : entities) {
            resultado.add(toDto(entity));
        }
        return resultado;
    }

    public Texto findById(Long id) {
        Optional<TextoEntity> optTexto = textoRepository.findById(id);
        if (optTexto.isEmpty()) {
            return null;
        }
        return toDto(optTexto.get());
    }

    public Texto addNew(Texto texto) {
        LocalDate hoje = LocalDate.now();
        boolean publicado = !texto.getDataPublicacao().isAfter(hoje);
        texto.setPublicado(publicado);

        TextoEntity entity = toEntity(texto);
        entity = textoRepository.save(entity);
        return toDto(entity);
    }

    public Texto update(Long id, Texto texto) {
        Optional<TextoEntity> optTexto = textoRepository.findById(id);
        if (optTexto.isEmpty()) {
            return null;
        }

        LocalDate hoje = LocalDate.now();
        boolean publicado = !texto.getDataPublicacao().isAfter(hoje);

        TextoEntity entity = optTexto.get();
        entity.setTitulo(texto.getTitulo());
        entity.setAutor(texto.getAutor());
        entity.setDataPublicacao(texto.getDataPublicacao());
        entity.setTexto(texto.getTexto());
        entity.setPublicado(publicado);

        entity = textoRepository.save(entity);
        return toDto(entity);
    }

    public void delete(Long id) {
        textoRepository.deleteById(id);
    }

}