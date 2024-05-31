package com.didier.apirest.service;

import com.didier.apirest.model.dto.ClienteDto;
import com.didier.apirest.model.entity.Cliente;

import java.util.List;

public interface IClienteService {
    List<Cliente> listAll();
    Cliente save(ClienteDto cliente);
    Cliente findById(Integer id);
    void delete(Cliente cliente);
    boolean existsById(Integer id);
}
