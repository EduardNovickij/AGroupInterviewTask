package com.AGroupInterviewTask.services;

import com.AGroupInterviewTask.repositories.LegalIdTypesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service("legalIdTypesService")
public class LegalIdTypesService implements ILegalIdTypesService {

    @Autowired
    LegalIdTypesRepository legalIdTypesRepository;

    @Override
    public void findOne(String idType) throws Exception {
        try{
            legalIdTypesRepository.findOne(idType);
        }
        catch (EmptyResultDataAccessException exception){
            throw new Exception("This idType is invalid. (" + idType + ")");
        }
    }
}
