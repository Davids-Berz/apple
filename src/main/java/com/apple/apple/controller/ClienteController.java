package com.apple.apple.controller;

import com.apple.apple.models.entity.Cliente;
import com.apple.apple.service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ClienteController {

    @Autowired
    private IClienteService clienteService;

    @GetMapping("/clientes")
    public List<Cliente> list() {
        return clienteService.findAll();
    }

    @GetMapping("/clientes/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        Cliente cliente;

        Map<String, Object> resp = new HashMap<>();

        try {
            cliente = clienteService.findById(id);

        } catch (DataAccessException e) {
            resp.put("mensaje", "Error en la base de datos");
            resp.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
        }
        if (cliente == null) {
            resp.put("mensaje", "El cliente id: " + id.toString() + " no existe");
            return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
    }

    @PostMapping("/clientes")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?>  create(@RequestBody Cliente cliente) {
        cliente.setCreateAt(new Date());
        Cliente newClient;
        Map<String, Object> resp = new HashMap<>();
        try {
            newClient = clienteService.save(cliente);
        }catch (Exception e) {
            resp.put("mensaje", "Error en la base de datos al realizar el insert");
            resp.put("error", e.getLocalizedMessage());
            return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
        }

        resp.put("mensaje", "El cliente ah sido creado con exito");
        resp.put("cliente", newClient);

        return new ResponseEntity<>(resp, HttpStatus.CREATED);
    }

    @PutMapping("/clientes/{id}")
    public ResponseEntity<?>  update(@RequestBody Cliente cliente, @PathVariable Long id) {
        Cliente updateClient;

        Cliente currentClient = clienteService.findById(id);
        Map<String, Object> resp = new HashMap<>();

        if (currentClient == null) {
            resp.put("mensaje", "El cliente id: " + id.toString() + " no existe");
            return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
        }

        try {
            currentClient.setNombre(cliente.getNombre());
            currentClient.setApellido(cliente.getNombre());
            currentClient.setEmail(cliente.getEmail());
            currentClient.setCreateAt(cliente.getCreateAt());
            updateClient = clienteService.save(currentClient);

        }catch (Exception e) {
            resp.put("mensaje", "Error en la base de datos al realizar el update");
            resp.put("error", e.getCause());
            return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
        }

        resp.put("mensaje", "El cliente ah sido acualizado con exito");
        resp.put("cliente", updateClient);

        return new ResponseEntity<>(resp, HttpStatus.CREATED);
    }

    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {

        Map<String, Object> resp = new HashMap<>();

        try {
            clienteService.deleteById(id);
        }catch (DataAccessException e) {
            resp.put("mensaje", "Error en la base de datos al realizar el delete");
            resp.put("error", e.getCause());
            return new ResponseEntity<>(resp,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        resp.put("mensaje", "Cliente eliminado con exito");
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

}
