// Pacote corrigido para 'biblioteca_pessoal'
package br.com.biblioteca.biblioteca_pessoal.controller;

// Importações corrigidas para 'biblioteca_pessoal'
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.biblioteca.biblioteca_pessoal.model.Livro;
import br.com.biblioteca.biblioteca_pessoal.repository.LivroRepository;

@RestController
@RequestMapping("/api/livros")
public class LivroController {

    @Autowired
    private LivroRepository livroRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Livro criarLivro(@RequestBody Livro livro) {
        return livroRepository.save(livro);
    }

    @GetMapping
    public List<Livro> lerTodosOsLivros() {
        return livroRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livro> lerLivroPorId(@PathVariable Long id) {
        Optional<Livro> livroOptional = livroRepository.findById(id);
        return livroOptional.map(ResponseEntity::ok)
                              .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Livro> atualizarLivro(@PathVariable Long id, @RequestBody Livro livroDetalhes) {
        return livroRepository.findById(id)
                .map(livroExistente -> {
                    livroExistente.setTitulo(livroDetalhes.getTitulo());
                    livroExistente.setAutor(livroDetalhes.getAutor());
                    livroExistente.setAnoPublicacao(livroDetalhes.getAnoPublicacao());
                    livroExistente.setLido(livroDetalhes.isLido());
                    Livro livroAtualizado = livroRepository.save(livroExistente);
                    return ResponseEntity.ok(livroAtualizado);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarLivro(@PathVariable Long id) {
        return livroRepository.findById(id)
                .map(livro -> {
                    livroRepository.delete(livro);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}