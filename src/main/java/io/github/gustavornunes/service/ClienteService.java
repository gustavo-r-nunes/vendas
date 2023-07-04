package io.github.gustavornunes.service;

import io.github.gustavornunes.model.Cliente;
import io.github.gustavornunes.repository.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClientesRepository repository;
    public void salvarCliente(Cliente cliente){
        validarCliente(cliente);
        repository.persistir(cliente);
    }

    public void validarCliente(Cliente cliente){
        //aplica validaçoes

    }
}
