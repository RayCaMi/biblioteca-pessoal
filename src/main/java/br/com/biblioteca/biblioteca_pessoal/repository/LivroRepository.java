package br.com.biblioteca.biblioteca_pessoal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.biblioteca.biblioteca_pessoal.model.Livro;

// Certifique-se de que esta linha está escrita exatamente desta forma
public interface LivroRepository extends JpaRepository<Livro, Long> {
}