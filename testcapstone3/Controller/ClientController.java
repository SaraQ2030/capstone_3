package com.example.testcapstone3.Controller;

import com.example.testcapstone3.ApiResponse.APIResponse;
import com.example.testcapstone3.Model.Client;
import com.example.testcapstone3.Service.ClientService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/client")
public class ClientController {
    private final ClientService clientService;
    private final Logger logger = LoggerFactory.getLogger(ClientController.class);

    @GetMapping("/get")
    public ResponseEntity getAllClient() {
        logger.info("inside get all client");
        return ResponseEntity.status(200).body(clientService.getAllCliet());
    }

    @PostMapping("/add")
    public ResponseEntity addClient(@RequestBody @Valid Client client) {
        logger.info("inside add client");
        clientService.addClient(client);
        return ResponseEntity.status(200).body(new APIResponse("Client added"));

    }


    @PutMapping("/update/{id}")
    public ResponseEntity updateClient(@PathVariable Integer id, @RequestBody @Valid Client client) {
        logger.info("inside update client");
        clientService.updateClient(id, client);
        return ResponseEntity.status(200).body(new APIResponse("Client updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteClient(@PathVariable Integer id) {
        logger.info("inside delete client");
        clientService.deleteClient(id);
        return ResponseEntity.status(200).body(new APIResponse("Client deleted"));
    }

}
