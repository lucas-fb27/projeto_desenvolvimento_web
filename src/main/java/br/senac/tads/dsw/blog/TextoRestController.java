package br.senac.tads.dsw.blog;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/textos")
@CrossOrigin(origins = "*")
public class TextoRestController {

    @Autowired
    private TextoService service;

    @GetMapping
    public List<Texto> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Texto findById(@PathVariable("id") Long id) {
        Texto resultado = service.findById(id);
        if (resultado == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Texto %d não encontrado".formatted(id));
        }
        return resultado;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> addNew(@RequestBody @Valid Texto texto) {
        try {
            Texto t = service.addNew(texto);
            
            Map<String, Object> response = new HashMap<>();
            response.put("mensagem", "Texto salvo com sucesso!");
            response.put("id", t.getId());
            response.put("titulo", t.getTitulo());
            
            URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(t.getId())
                .toUri();
            
            return ResponseEntity.created(location).body(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("erro", "Erro ao salvar o texto: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(
            @PathVariable Long id,
            @RequestBody @Valid Texto texto) {
        
        Texto t = service.findById(id);
        if (t == null) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("erro", "Texto não encontrado");
            return ResponseEntity.notFound().build();
        }
        
        try {
            t = service.update(id, texto);
            Map<String, Object> response = new HashMap<>();
            response.put("mensagem", "Texto atualizado com sucesso!");
            response.put("id", t.getId());
            response.put("titulo", t.getTitulo());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("erro", "Erro ao atualizar o texto: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            Map<String, Object> response = new HashMap<>();
            response.put("mensagem", "Texto excluído com sucesso!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("erro", "Erro ao excluir o texto: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
}
