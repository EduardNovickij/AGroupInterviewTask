package com.AGroupInterviewTask.services;

import com.AGroupInterviewTask.repositories.LegalIdTypesRepository;
import com.AGroupInterviewTask.services.interfaces.ILegalIdTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service("legalIdTypesService")
public class LegalIdTypesService implements ILegalIdTypesService {

    @Autowired
    LegalIdTypesRepository legalIdTypesRepository;

    /*Method for checking if provided idType is valid by checking if it exists in SQLite database.
     * Throws exception if provided idType does not exist and therefore is invalid.*/
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
