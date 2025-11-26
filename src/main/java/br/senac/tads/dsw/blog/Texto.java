package br.senac.tads.dsw.blog;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class Texto {

    private Long id;

    @NotBlank
    @Size(min = 1, max = 200)
    private String titulo;

    @NotBlank
    @Size(min = 1, max = 100)
    private String autor;

    private LocalDate dataPublicacao;

    @NotBlank
    @Size(min = 10)
    private String texto;

    private boolean publicado;

    public Texto() {
        this.publicado = false;
    }

    public Texto(Long id, String titulo, String autor, LocalDate dataPublicacao, String texto) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.dataPublicacao = dataPublicacao;
        this.texto = texto;
        this.publicado = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public LocalDate getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(LocalDate dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public boolean isPublicado() {
        return publicado;
    }

    public void setPublicado(boolean publicado) {
        this.publicado = publicado;
    }

}
