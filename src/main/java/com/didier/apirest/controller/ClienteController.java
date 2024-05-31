package com.didier.apirest.controller;

import com.didier.apirest.model.dto.ClienteDto;
import com.didier.apirest.model.entity.Cliente;
import com.didier.apirest.model.payload.MensajeResponse;
import com.didier.apirest.service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ClienteController {

    @Autowired
    private IClienteService clienteService;

    @GetMapping("/clientes")
    public ResponseEntity<?> showAll(){
        List<Cliente> getList = clienteService.listAll();
        if(getList == null){
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("No hay registros")
                    .object(null)
                    .build()
                    ,HttpStatus.OK);
        }
        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("")
                        .object(getList)
                        .build()
                ,HttpStatus.OK);
    }

    @PostMapping("/cliente")
    public ResponseEntity<?> create(@RequestBody ClienteDto clienteDto){
        Cliente clienteSave = null;
        try{
            clienteSave = clienteService.save(clienteDto);
            return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("Se guardado el cliente con exito")
                        .object(ClienteDto.builder()
                                .id(clienteSave.getId())
                                .nombre(clienteSave.getNombre())
                                .apellido(clienteSave.getApellido())
                                .correo(clienteSave.getCorreo())
                                .fechaRegistro(clienteSave.getFechaRegistro())
                                .build())
                        .build()
                    ,HttpStatus.CREATED);
        }catch (DataAccessException exDT){
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje(exDT.getMessage())
                    .object(null)
                    .build()
                    ,HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @PutMapping("/cliente/{id}")
    public ResponseEntity<?> update(@RequestBody ClienteDto clienteDto,@PathVariable Integer id){
        Cliente clienteUpdate = null;
        try{
            if(clienteService.existsById(id)){
                clienteDto.setId(id);
                clienteUpdate = clienteService.save(clienteDto);
                return new ResponseEntity<>(
                        MensajeResponse.builder()
                                .mensaje("Se edito el cliente con exito")
                                .object(ClienteDto.builder()
                                        .id(clienteUpdate.getId())
                                        .nombre(clienteUpdate.getNombre())
                                        .apellido(clienteUpdate.getApellido())
                                        .correo(clienteUpdate.getCorreo())
                                        .fechaRegistro(clienteUpdate.getFechaRegistro())
                                        .build())
                                .build()
                        ,HttpStatus.CREATED);
            }else{
                return new ResponseEntity<>(MensajeResponse.builder()
                        .mensaje("El cliente que intenta actualizar no existe")
                        .object(null)
                        .build()
                        ,HttpStatus.NOT_FOUND);
            }
        }catch (DataAccessException exDT){
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje(exDT.getMessage())
                    .object(null)
                    .build()
                    ,HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @DeleteMapping("/cliente/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        try{
            Cliente clienteDelete = clienteService.findById(id);
            clienteService.delete(clienteDelete);
            return new ResponseEntity<>(clienteDelete, HttpStatus.NO_CONTENT);
        }catch (DataAccessException exDT){
            return new ResponseEntity<>(MensajeResponse.builder()
                        .mensaje(exDT.getMessage())
                        .object(null)
                        .build()
                    ,HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @GetMapping("/cliente/{id}")
    public ResponseEntity<?> showById(@PathVariable Integer id){
        Cliente clienteShow = clienteService.findById(id);
        if(clienteShow == null){
            return new ResponseEntity<>(MensajeResponse.builder()
                    .mensaje("El cliente que busca no existe en la base de datos")
                    .object(null)
                    .build()
                    ,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(MensajeResponse.builder()
                .mensaje("Cliente encontrado")
                .object(ClienteDto.builder()
                        .id(clienteShow.getId())
                        .nombre(clienteShow.getNombre())
                        .apellido(clienteShow.getApellido())
                        .correo(clienteShow.getCorreo())
                        .fechaRegistro(clienteShow.getFechaRegistro())
                        .build())
                .build()
                ,HttpStatus.OK);
    }
}
