package com.example.testcapstone3.Service;
import com.example.testcapstone3.ApiResponse.APIException;
import com.example.testcapstone3.Model.Appeal;
import com.example.testcapstone3.Model.Casse;
import com.example.testcapstone3.Model.Client;
import com.example.testcapstone3.Model.Evidence;
import com.example.testcapstone3.Repoistory.AppealRepository;
import com.example.testcapstone3.Repoistory.CasseRepository;
import com.example.testcapstone3.Repoistory.DocumentRepository;
import com.example.testcapstone3.Repoistory.EvidenceRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EvidenceService {

    private final EvidenceRepository evidenceRepository;
    private final AppealRepository appealRepository;

    public List<Evidence> getAll(){
        if (evidenceRepository.findAll().isEmpty())
            throw new APIException("Empty evidence List");
        return evidenceRepository.findAll();
    }


    public void add(Integer appealID, Evidence evidence){
       Appeal appeal=appealRepository.findAppealByCasseId(appealID);
       evidence.setSubmissionDate(LocalDate.now());
       if (appeal==null){throw new APIException(" Not found appeal");}
       evidenceRepository.save(evidence);
        assignEvidenceToAppeal(evidence.getId(),appealID);
    }

    public void update(Evidence evidence,Integer id){
        Appeal appeal=appealRepository.findAppealByCasseId(evidence.getAppeal().getCasse().getId());
        if (appeal==null)throw new APIException("Fail addition because Not found appeal");
      Evidence retrEvidence=evidenceRepository.findEvidenceById(id);
      if (retrEvidence!=null){
          retrEvidence.setAppeal(evidence.getAppeal());
          retrEvidence.setTitle(evidence.getTitle());
          retrEvidence.setDescription(evidence.getDescription());
          retrEvidence.setSubmissionDate(evidence.getSubmissionDate());
        evidenceRepository.save(retrEvidence);}

      else{
          throw new APIException("Not found Evidence with ID"+id);
      }
    }

    public void delete(Integer id){

        Evidence retrEvidence=evidenceRepository.findEvidenceById(id);
        if (retrEvidence!=null){
            evidenceRepository.delete(retrEvidence);
        }else{
            throw new APIException("Not found Evidence with ID"+id);
        }
    }

    //============================EXTRA======================================
    //extra 13
    public void assignEvidenceToAppeal(Integer eviID, Integer appealId) {
        Appeal appeal = appealRepository.findAppealByCasseId(appealId);
        Evidence evidence = evidenceRepository.findEvidenceById(eviID);

        if (appeal == null || evidence == null)
            throw new APIException("Cannot evidence client to appeal");

        evidence.setAppeal(appeal);
        evidenceRepository.save(evidence);
    }

    //extra 14
   public List<Evidence> getAllevidenceByAppeal(Integer appealId){
       Appeal appeal = appealRepository.findAppealByCasseId(appealId);
if (appeal==null)
           throw new APIException("appeal not found");
else{
    List<Evidence>evidenceList=evidenceRepository.findAllByAppeal_Id(appealId);
    if(evidenceList.isEmpty()){
        throw new APIException("No evidence found");
    }
    return evidenceList;
}
   }



    }

