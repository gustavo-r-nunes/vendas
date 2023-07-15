package io.github.gustavornunes;

import io.github.gustavornunes.domain.entity.Cliente;
import io.github.gustavornunes.domain.repositorio.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
public class VendasApplication {
    @Bean
    public CommandLineRunner init(@Autowired ClienteRepository repository){
        return args -> {
            repository.salvar(new Cliente("Douglas"));
            repository.salvar(new Cliente("Gustavo"));

            List<Cliente> todosClientes = repository.obterTodos();
            todosClientes.forEach(System.out::println);

            todosClientes.forEach( c -> {c.setNome(c.getNome() + "atualizado");
                repository.atualizar(c);
            }
            );

            todosClientes = repository.obterTodos();
            todosClientes.forEach(System.out::println);

            repository.buscarPorNome("Cli").forEach(System.out::println);

            repository.obterTodos().forEach(c -> repository.deletar(c)
            );

            todosClientes = repository.obterTodos();
            todosClientes.forEach(System.out::println);

        };
    }

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
}
