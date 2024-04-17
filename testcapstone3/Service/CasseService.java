package com.example.testcapstone3.Service;

import com.example.testcapstone3.ApiResponse.APIException;
import com.example.testcapstone3.DTO.AppealDTO;
import com.example.testcapstone3.Model.Appeal;
import com.example.testcapstone3.Model.Casse;
import com.example.testcapstone3.Model.Client;
import com.example.testcapstone3.Model.User;
import com.example.testcapstone3.Repoistory.CasseRepository;
import com.example.testcapstone3.Repoistory.ClientRepository;
import com.example.testcapstone3.Repoistory.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RequiredArgsConstructor
@Service

public class CasseService {


    private final CasseRepository caseRepository;
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    @Autowired
    private final AppealService appealService;

    public List<Casse> getall() {
        if (caseRepository.findAll().isEmpty()) {
            throw new APIException("Empty Case");
        } else {
            return caseRepository.findAll();
        }
    }

    public void add(Integer clientId, Casse casse) {
        Client client = clientRepository.findClientById(clientId);

        if (client == null) {
            throw new APIException("Not found Client with ID" + clientId);

        } else {
            casse.setClients(client);
            casse.setIsAppeal(true);
            casse.setStatus("untaken");
            caseRepository.save(casse);
            assignClientToCases(clientId, casse.getId());
        }
    }

    public void upDate(Casse casse, Integer id) {
        Casse cas = caseRepository.findCasseById(id);
        if (cas == null) {
            throw new APIException("Not found Case with ID" + id);
        } else {
            cas.setStartDate(casse.getStartDate());
            cas.setIsAppeal(casse.getIsAppeal());
            cas.setAppeal(casse.getAppeal());
            cas.setTypeOflawsuits(casse.getTypeOflawsuits());
            caseRepository.save(cas);//update
        }
    }

    public void delete(Integer id) {
        Casse cas = caseRepository.findCasseById(id);
        if (cas == null) {
            throw new APIException("Not found Case with ID " + id);
        } else {
            caseRepository.delete(cas);
        }
    }

    //======================================Assign Many Case to One Client ======================
    public void assignClientToCases(Integer clientId, Integer caseId) {
        Client client = clientRepository.findClientById(clientId);
        Casse aCase = caseRepository.findCasseById(caseId);

        if (client == null || aCase == null)
            throw new APIException("Cannot assign client to case");

        aCase.setClients(client);
        caseRepository.save(aCase);
    }
//======================================Assign Many Case to One Lawyer as user ======================


    public void assignUserToCases(Integer userId, Integer caseId) {
        User user = userRepository.findUserById(userId);
        Casse aCase = caseRepository.findCasseById(caseId);
        if (user == null || aCase == null)
        {throw new APIException("Cannot assign Lawyer to case");}

        aCase.setUsser(user);
        caseRepository.save(aCase);
    }




    //--------------------------------------------------Extra 1-------------------
    public void AppeaThecase(AppealDTO appealDTO) {
        Casse casse = caseRepository.findCasseById(appealDTO.getCase_Id());
        long totalDays = 0;
        if (casse != null) {
            if (casse.getStatus().equalsIgnoreCase("Closed")) {
                if (casse.getAppeal() == null) {
                    //make sure case without appeal object
                    if (casse.getIsAppeal()) {//make sure case can be appeal
                        totalDays = calculateDays(casse.getStartDate(), LocalDate.now());
                        if (casse.getTypeOflawsuits().equalsIgnoreCase("criminal")) {
                            if (totalDays <= 15 && totalDays > 10) {//make sure case can be appeal within 15 days
                              //  Appeal appeal = new Appeal(null, appealDTO.getTitle(), appealDTO.getDescription(), appealDTO.getStartDate(), casse, false, null);
                                Appeal appeal=new Appeal();
                                appeal.setId(null);
                                appeal.setCasse(casse);
                                appeal.setTitle(appealDTO.getTitle());
                                appeal.setDescription(appealDTO.getDescription());
                                appeal.setStatus("intake");
                                appeal.setEvidenceList(null);
                                appeal.setStartDate(LocalDate.now());
                                appeal.setClosed(false);
                                appealService.addAppeal(appeal);
                                casse.setAppeal(appeal);
                                appealService.addAppeal(appeal);
                            } else {
                                throw new IllegalStateException("Cannot appeal cases older than 15 days");
                            }
                        } else if (totalDays > 15 && totalDays <= 60) {
//
                            Appeal appeal=new Appeal();
                            appeal.setId(null);
                            appeal.setCasse(casse);
                            appeal.setTitle(appealDTO.getTitle());
                            appeal.setDescription(appealDTO.getDescription());
                            appeal.setStatus("intake");
                            appeal.setEvidenceList(null);
                            appeal.setStartDate(LocalDate.now());
                            appeal.setClosed(false);
                            appealService.addAppeal(appeal);
                            casse.setAppeal(appeal);
                            appealService.addAppeal(appeal);
                            //  break;
                            // }
                        }else{
                            throw new APIException("Cannot appeal cases older than 60 days");

                        }
                    } else {

                        throw new APIException("Cannot appeal cases because case type: "+casse.getTypeOflawsuits());

                    }
                } else {//this reason why not can be appeal case

                    throw new APIException("This Case has already appeal case");
                }
            } else {
                throw new APIException("status case is :"+casse.getStatus()+"so can't appeal case till close it");

            }
        } else {
            throw new APIException("Not found Case with ID " + appealDTO.getCase_Id());
        }
//======================================================================================================

    }

    private long calculateDays(LocalDate startDate, LocalDate endDate) {
        return startDate.until(endDate, ChronoUnit.DAYS);
    }
    //--------------------------------------------------Extra 2-------------------
//2
    public void closeCase(Integer caseID, Integer userID, String result) {
        Casse casse = caseRepository.findCasseById(caseID);
        User user = userRepository.findUserById(userID);
        if (casse == null)
            throw new APIException("Case not found");
        else if (user == null)
            throw new APIException("lawyer not found");
        else if (casse.getUsser().getId() == userID)
        {
            if(!casse.getStatus().equalsIgnoreCase("Closed")) {
            casse.setStatus("Closed");
            casse.setResult(result);
            caseRepository.save(casse);}
            else{
                throw  new APIException("case is already closed");
        }

        } else {
            throw new APIException(" Only the lawyer of the case can close case with ID." + caseID);
        }
    }

    //3
public void startCase(Integer caseId,Integer lawyerId){
    Casse casse = caseRepository.findCasseById(caseId);
    User user = userRepository.findUserById(lawyerId);
    if (casse == null)
        throw new APIException("Case not found");
    else if (user == null)
        throw new APIException("lawyer not found");
    else if (casse.getUsser().getId()==lawyerId){
    {
        if (casse.getStatus().equalsIgnoreCase("taken")){

        casse.setStartDate(LocalDate.now());
        casse.setStatusLawsuit("investigation");
        caseRepository.save(casse);}
        else{
        throw new APIException("Lawyer with ID"+lawyerId +"Don't take this case");

    }

    }

}else {
        throw new APIException(" Only the lawyer of the case can close case with ID." + caseId);
    }

    }
public void acceptClientRequest(Integer caseId,Integer lawyerId){

    Casse casse = caseRepository.findCasseById(caseId);
    User user = userRepository.findUserById(lawyerId);
    if (casse == null)
        throw new APIException("Case not found");
    else if (user == null)
        throw new APIException("lawyer not found");
    else if (casse.getUsser().getId()==lawyerId){
        casse.setStatus("taken");
        caseRepository.save(casse);
    }else{
        throw new APIException(" This case didn't send to you");

    }
}


}




//===================================  2 ===========================
//    public void sendReslut(Integer caseId, String result){
//        Casse casse = caseRepository.findCasseById(caseId);
//        if(casse==null){
//            throw new APIException("Case not found with ID: " + caseId);
//        }
//        casse.setResult(result);
//        caseRepository.save(casse);
//    }



//getStatusofCaseFrom Task from lawyer



