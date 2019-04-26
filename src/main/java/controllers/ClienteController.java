package main.java.controllers;

import main.java.models.Cliente;
import main.java.repository.ClienteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/clientes")
    public @ResponseBody Iterable<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }

    @GetMapping("/cliente/{id}")
    public @ResponseBody Cliente getAllClientes( @PathVariable int id ) {
    		return clienteRepository.findById(id);
    }

    @PostMapping("/cliente")
    public @ResponseBody Cliente newCliente( @RequestBody Cliente cliente ) {
        return clienteRepository.save(cliente);
    }


    @PutMapping("/cliente/{id}")
    public @ResponseBody Cliente updateCliente( @PathVariable int id, @RequestBody Cliente cliente ) {
    	Cliente clienteToUpdate = clienteRepository.findById(id);	

    	clienteToUpdate.setNome(cliente.getNome());
    	clienteToUpdate.setCpf(cliente.getCpf());
    	clienteToUpdate.setDataNascimento(cliente.getDataNascimento());
        return clienteRepository.save(cliente);
    }

    @DeleteMapping("/cliente/{id}")
    public @ResponseBody void deleteCliente( @PathVariable int id) {
    	Cliente clienteToDelete = clienteRepository.findById(id);	
        clienteRepository.delete(clienteToDelete);
    }
}