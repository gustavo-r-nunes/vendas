package io.github.gustavornunes.controller;

import io.github.gustavornunes.model.Pedido;
import io.github.gustavornunes.model.Produto;
import io.github.gustavornunes.repository.PedidoRepository;
import io.github.gustavornunes.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository repository;

    @GetMapping("/{id}")
    public Produto getProdutoById(@PathVariable Integer id){
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));



    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto save(@RequestBody Produto produto){
        return repository.save(produto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id){
         repository.findById(id)
                 .map(produto -> {repository.delete(produto);
                     return produto;
                 })
                 .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "produto não encontrado"));

    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Produto update(@PathVariable Integer id,@RequestBody Produto produto){
        return repository
                .findById(id)
                .map(produtoExistente -> {
                     produto.setId(produtoExistente.getId());
                     repository.save(produto);
                     return produtoExistente;
                    }).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "produto não encontrado"));
    }
    @GetMapping
    public List<Produto> find(Produto filtro){
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filtro, matcher);
        List<Produto> produtos = repository.findAll(example);

        return produtos;
    }


}
