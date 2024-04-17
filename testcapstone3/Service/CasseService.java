package com.example.testcapstone3.Service;

import com.example.testcapstone3.ApiResponse.APIException;
import com.example.testcapstone3.DTO.AppealDTO;
import com.example.testcapstone3.Model.Appeal;
import com.example.testcapstone3.Model.Casse;
import com.example.testcapstone3.Model.Client;
import com.example.testcapstone3.Model.User;
import com.example.testcapstone3.Repoistory.CasseRepository;
import com.example.testcapstone3.Repoistory.ClientRepository;
import com.example.testcapstone3.Repoistory.TaskRepository;
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
        private final TaskRepository taskRepository;

        public List<Casse> getall() {
            if (caseRepository.findAll().isEmpty()) {
                throw new APIException("Empty Case");
            }
            else {
                return caseRepository.findAll();
            }
        }

        public void add(Casse casse) {
          //  if (casse.getClient().getId())
            casse.setStartDate(LocalDate.now());
            caseRepository.save(casse);
            assignClientToCases(casse.getClients().getId(), casse.getId());//auto assign
            assignUserToCases(casse.getUsser().getId(), casse.getId());
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
                throw new APIException("Cannot assign Lawyer to case");

            aCase.setUsser(user);
            caseRepository.save(aCase);
        }


        //--------------------------------------------------Extra 1-------------------
        public void AppeaThecase(AppealDTO appealDTO) {
            Casse casse = caseRepository.findCasseById(appealDTO.getCase_Id());
            long totalDays = 0;
            if (casse != null) {
                if (casse.getAppeal() == null) {//make sure case without appeal object
                    if (casse.getIsAppeal()) {//make sure case can be appeal
                        totalDays = calculateDays(casse.getStartDate(), LocalDate.now());
                        if (casse.getTypeOflawsuits().equalsIgnoreCase("criminal")) {
                            if (totalDays <= 15 && totalDays > 10) {//make sure case can be appeal within 15 days
                                Appeal appeal = new Appeal(null,appealDTO.getTitle(),appealDTO.getDescription(),appealDTO.getStartDate(),casse,null);
                                casse.setAppeal(appeal);
                                appealService.addAppeal(appeal);
                            } else {
                                throw new IllegalStateException("Cannot appeal cases older than 15 days");
                            }
                        } else if (totalDays > 15 && totalDays <= 60) {
//
                            Appeal appeal = new Appeal(null, appealDTO.getTitle(), appealDTO.getDescription(), LocalDate.now(),casse,null);
                            casse.setAppeal(appeal);
                            appealService.addAppeal(appeal);
                            //  break;
                            // }
                        } else {

                            throw new APIException("Cannot appeal cases older than 60 days");

                        }
                    } else {//this reason why not can be appeal case

                        throw new APIException("This Case can not appeal because its type: " + casse.getTypeOflawsuits());
                    }
                } else {
                    throw new APIException("Case already has appeal associated with it");

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


//getStatusofCaseFrom Task from lawyer
    }


