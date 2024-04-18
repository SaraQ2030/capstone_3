package com.example.testcapstone3.Service;

import com.example.testcapstone3.ApiResponse.APIException;
import com.example.testcapstone3.Model.Appeal;
import com.example.testcapstone3.Model.Casse;
import com.example.testcapstone3.Model.User;

import com.example.testcapstone3.Repoistory.AppealRepository;
import com.example.testcapstone3.Repoistory.CasseRepository;
import com.example.testcapstone3.Repoistory.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AppealService {

    private final AppealRepository appealRepository;
    private final CasseRepository caseRepository;
    private final UserRepository userRepository;

    public List<Appeal> getall() {
        if (appealRepository.findAll().isEmpty()) {
            throw new APIException("Empty Appeal");
        } else {

            return appealRepository.findAll();

        }
    }
    //---------------------------------------------


    public void addAppeal(Appeal appeal) {
        //AppeaThecase in this method in CaseService already setCase in appeal so no need here set
        Casse cas = caseRepository.findCasseById(appeal.getCasse().getId());

        if (cas == null) {
            throw new APIException("Cant add Appeal without Case ");
        } else {

            appeal.setStartDate(LocalDate.now());
            appealRepository.save(appeal);
        }
    }

    public void upDateAppeal(Appeal appeal, Integer id) {
        Casse cas = caseRepository.findCasseById(appeal.getCasse().getId());

        if (cas == null) {
            throw new APIException("Cant update Appeal without Case ");
        } else {
            Appeal appeal1 = appealRepository.findAppealByCasseId(cas.getId());
            if (appeal1 == null) {
                throw new APIException("Not found Appeal");
            } else {

                appeal1.setStartDate(appeal.getStartDate());
                appeal1.setCasse(cas);
                appeal1.setTitle(appeal.getTitle());
                appeal1.setDescription(appeal.getDescription());
                appealRepository.save(appeal);
            }
        }
    }

    public void deleteAppeal(Integer id) {

        if (appealRepository.findAppealByCasseId(id) != null) {
            Appeal appeal = appealRepository.findAppealByCasseId(id);
            appealRepository.delete(appeal);

        } else {
            throw new APIException("Not found Appeal");
        }
    }

    //--------------------------------------------------Extra 5-------------------
    //extra 28
    public void approve(Integer caseId, Integer userId,String result) {
        User user = userRepository.findUserById(userId);//lawyer
        Appeal appeal = appealRepository.findAppealByCasseId(caseId);
        Casse casse = caseRepository.findCasseById(caseId);
        if (appeal != null) {

            if (user != null) {
//                if (casse.getUsser().getId()==userId) {
//                    throw new APIException(" Only the lawyer of the case can approve Appeal case with ID." + caseId);
//                }
                if (appeal.getCasse().getStatus().equalsIgnoreCase("intake")){
                    appeal.setResult(result);
                appealRepository.save(appeal);
                closedAppeal(caseId, userId);}
                else{
                    throw new APIException("lawyer not taken appeal");
                }
            } else {
                throw new APIException("lawyer not found");
            }

        } else {
            throw new APIException("appeal not found");
        }

    }


    ///=================================  6
    //extra 29
    public void closedAppeal(Integer caseId, Integer userId) {
        User user = userRepository.findUserById(userId);//lawyer
        Appeal appeal = appealRepository.findAppealByCasseId(caseId);
        if (appeal != null && user != null) {
            if (!appeal.getCasse().getAppeal().getStatus().equalsIgnoreCase("closed")) {
                Casse casse = appeal.getCasse();
                casse.setIsAppeal(false);
                caseRepository.save(casse);//save update
                appeal.setCasse(casse);
                //Approved|NOT_Approved
                appeal.setClosed(true);
                appeal.setStatus("closed");
                appealRepository.save(appeal);//save update
            } else {
                throw new APIException("Appeal is already closed");
            }
        } else {
            throw new APIException("Not Found Appeal with ID " + caseId);
        }
    }
}



//|| user==null){






