package io.github.gustavornunes.controller;

import io.github.gustavornunes.model.Cliente;
import io.github.gustavornunes.repository.ClienteRepository;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@Api("Api clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository repository;

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de cliente")
    @ApiResponses({ @ApiResponse(code = 200, message = "Cliente encontrado"),
                    @ApiResponse(code = 404, message = "cliente nao encontado para o id informado")
    })
    public Cliente getClienteById(@PathVariable @ApiParam("id do cliente") Integer id){
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));

    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("salvar cliente")
    @ApiResponses({ @ApiResponse(code = 201, message = "Cliente salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro de validacao")
    })
    public Cliente save(@RequestBody @Valid Cliente cliente){
        return repository.save(cliente);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id){
         repository.findById(id)
                 .map(cliente -> {repository.delete(cliente);
                     return cliente;
                 })
                 .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "cliente não encontrado"));

    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Cliente update(@PathVariable Integer id,@RequestBody @Valid Cliente cliente){
        return repository
                .findById(id)
                .map(clienteExistente -> {
                     cliente.setId(clienteExistente.getId());
                     repository.save(cliente);
                     return clienteExistente;
                    }).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "cliente não encontrado"));
    }
    @GetMapping
    public List<Cliente> find(Cliente filtro){
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filtro, matcher);
        List<Cliente> clientes = repository.findAll(example);

        return clientes;
    }


}
