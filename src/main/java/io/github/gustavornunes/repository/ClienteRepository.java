package io.github.gustavornunes.repository;

import io.github.gustavornunes.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    /*
   equivalente a :
   hql:
   @Query (value = "select c from cliente c where c.nome like :nome")
   sql nativo:
   @Query (value = "select * from cliente c where c.nome like '%:nome%' ", nativeQuery = true)
   List<Cliente> encontrarPorNome(@Param("nome") String nome);
* */
    List<Cliente> findByNomeLike(String nome);
    boolean existsByNome(String nome);

    @Modifying
    void deleteByNome(String nome);

    @Query(" select c from Cliente c left join fetch c.pedidos where c.id = :id ")
    Cliente findClienteFetchPedido(@Param("id") Integer id);


}
