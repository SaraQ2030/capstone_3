package com.example.testcapstone3.Service;
import com.example.testcapstone3.ApiResponse.APIException;
import com.example.testcapstone3.Model.Casse;
import com.example.testcapstone3.Model.Client;
import com.example.testcapstone3.Model.User;
import com.example.testcapstone3.Repoistory.CasseRepository;
import com.example.testcapstone3.Repoistory.ClientRepository;
import com.example.testcapstone3.Repoistory.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
private final CasseRepository casseRepository;
private final UserRepository userRepository;
@Autowired
private final CasseService casseService;
    public List<Client> getAllCliet(){
        return clientRepository.findAll();
    }
    public void addClient(Client client){
        clientRepository.save(client);
    }

    public void updateClient(Integer id_client,Client client){
        Client c=clientRepository.findClientById(id_client);
        if (c==null){
            throw new APIException("the client not exist");
        }
        c.setAge(client.getAge());
        c.setName(client.getName());
        c.setEmail(client.getEmail());
        c.setPhoneNumber(client.getPhoneNumber());
        clientRepository.save(c);
    }
    public void deleteClient(Integer id_client){
        Client c=clientRepository.findClientById(id_client);
        if (c==null){
            throw new APIException("the client not exist");
        }
        clientRepository.delete(c);
    }


    //======================= request lawyer==================

public void sendRequestToLawyer(Integer caseID,Integer clientID,Integer lawyerID){
    Casse casse=casseRepository.findCasseById(caseID);
    Client client=clientRepository.findClientById(clientID);
    User user=userRepository.findUserById(lawyerID);

    if (casse==null){
        throw new APIException("Case Not found");
    } if (client==null){
        throw new APIException("Client Not found");
    } if (user==null){
        throw new APIException("lawyer Not found");
    }

    casse.setStatus("untaken");
    casseRepository.save(casse);
    casseService.assignUserToCases(lawyerID,caseID);


}
}