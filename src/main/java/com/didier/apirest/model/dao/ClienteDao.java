package com.didier.apirest.model.dao;

import com.didier.apirest.model.entity.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface ClienteDao extends CrudRepository<Cliente, Integer>{
}
