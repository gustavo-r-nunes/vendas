package io.github.gustavornunes.repository;

import io.github.gustavornunes.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
}
