package com.example.testcapstone3.Service;

import com.example.testcapstone3.ApiResponse.APIException;
import com.example.testcapstone3.DTO.AppealDTO;
import com.example.testcapstone3.Model.*;
import com.example.testcapstone3.Repoistory.AppealRepository;
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
    private final AppealRepository appealRepository;
    @Autowired
    private final AppealService appealService;
    @Autowired
    private final UserService userService;



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
    //=========================================EXTRA======================================================

    //======================================Assign Many Case to One Client ======================
    //extra 18
    //Sarah
    public void assignClientToCases(Integer clientId, Integer caseId) {
        Client client = clientRepository.findClientById(clientId);
        Casse aCase = caseRepository.findCasseById(caseId);

        if (client == null || aCase == null)
            throw new APIException("Cannot assign client to case");

        aCase.setClients(client);
        caseRepository.save(aCase);
    }
//======================================Assign Many Case to One Lawyer as user ======================


    //extra 19
    //Abduallrahman
    public void assignUserToCases(Integer userId, Integer caseId) {
        User user = userRepository.findUserById(userId);
        Casse aCase = caseRepository.findCasseById(caseId);
        if (user == null || aCase == null)
        {throw new APIException("Cannot assign Lawyer to case");}

        aCase.setUsser(user);
        caseRepository.save(aCase);
    }





    //--------------------------------------------------Extra 1-------------------
    //extra 20
    //Ghaliah
    public void AppeaThecase(AppealDTO appealDTO) {
        Casse casse = caseRepository.findCasseById(appealDTO.getCase_Id());
        long totalDays = 0;
        if (casse != null) {
            if (casse.getStatus().equalsIgnoreCase("closed")) {
                if (casse.getDescription().equalsIgnoreCase("lose")){
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
                                casse.setAppeal(appeal);
                        
                                appealRepository.save(appeal);
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
                            appealRepository.save(appeal);
                            //appealService.addAppeal(appeal);
                            //  break;
                            // }
                        }

                        else{
                            throw new APIException("Cannot appeal cases older than 60 days");

                        }
                    } else {

                        throw new APIException("Cannot appeal cases because case type: "+casse.getTypeOflawsuits());

                    }
                } else {//this reason why not can be appeal case

                    throw new APIException("This Case has already appeal case");
                }

                }else {
                    throw new APIException("The result of case not Lose to appeal it ");
                }
            } else {
                throw new APIException("status case is :"+casse.getStatus()+"so can't appeal case till close it");

            }
        } else {
            throw new APIException("Not found Case with ID " + appealDTO.getCase_Id());
        }
//=====================================================================================================
    }

    //extra 21
    private long calculateDays(LocalDate startDate, LocalDate endDate) {
        return startDate.until(endDate, ChronoUnit.DAYS);
    }
    //--------------------------------------------------Extra 2-------------------
//2
    //extra 22
    public void closeCase(Integer caseID, Integer userID, String result) {
        Casse casse = caseRepository.findCasseById(caseID);
        User user = userRepository.findUserById(userID);
        if (casse == null)
        { throw new APIException("Case not found");}
        else if (user == null)
        { throw new APIException("lawyer not found");}
        else if (casse.getUsser().getId()==userID) {
            if (casse.getStatus().equalsIgnoreCase("taken")) {
                    casse.setStatus("closed");
                    casse.setResult(result);
                    caseRepository.save(casse);
                    if(result.equalsIgnoreCase("win")){
                        user.setCount(user.getCount()+1);
                    userRepository.save(user);}
                }
            }
        else {
            throw new APIException(" Only the lawyer of the case can close case with ID." + caseID);


        }


    }
    //3 Done
    //extra 23
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

    /////////////////////////   4
    //extra 24
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
        userService.assignClientToUser(casse.getClients().getId(),lawyerId);

    }else{
        throw new APIException(" This case didn't send to you");

    }
}

    //===========================================================================
    public List<Casse> getCasesByTypeOfLawsuits(String typeOfLawsuits) {
        return caseRepository.findCassesByTypeOflawsuits(typeOfLawsuits);
    }
    //===========================================================================
    //===========================================================================

    //extra 25
    public Casse searchCaseByClientId(Integer clientId,Integer casseId) {
        Client client = clientRepository.findClientById(clientId);
        Casse casse = caseRepository.findCasseById(casseId);
        if (client == null ) {
            throw new APIException("Client not found ");
        }
        if (casse == null) {
            throw new APIException("case not found");
        }
        if (! (casse.getClients().getId()==client.getId())){
            throw new APIException("Client id not match with this case");
        }
        return casse;
    }
    //------------------------------------------------------------------------------------
    //git list of cases by client id
    //extra 26
    public List<Casse> getCasesByClientId(Integer clientId) {
        List<Casse> list=caseRepository.findCassesByClientsId(clientId);
        if (list.isEmpty()) {
            throw new APIException(" not found Cases ");
        }
        return list;
    }


    //extra 27
    public List<Casse> getCasseByStatus(String status) {
        return caseRepository.findCassesByTypeOflawsuits(status);
    }


//==============================================================

    //28 and 29 in appeal service !
    //extra 30
    public void changeStatus(Integer caseId){
        Casse casse=caseRepository.findCasseById(caseId);
        if (casse==null){
            throw new APIException("Case not found");
        }
        //investigation|trial
        if (casse.getStatusLawsuit().equalsIgnoreCase("investigation")){
            casse.setStatusLawsuit("trial");
            caseRepository.save(casse);
        }
        else throw new APIException("the satate of Law suit already trail "+casse.getStatusLawsuit());

    }

    public Casse getOneCasse(Integer caseId){
        Casse casse=caseRepository.findCasseById(caseId);
        if (casse==null){
            throw new APIException("Case not found");
        }
        return casse;
    }


}








