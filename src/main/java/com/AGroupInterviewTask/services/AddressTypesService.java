package com.AGroupInterviewTask.services;

import com.AGroupInterviewTask.repositories.AddressTypesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service("addressTypesService")
public class AddressTypesService implements IAddressTypesService {

    @Autowired
    private AddressTypesRepository addressTypesRepository;

    @Override
    public void findOne(String addressType) throws Exception {
        try{
            addressTypesRepository.findOne(addressType);
        }
        catch (EmptyResultDataAccessException exception){
            throw new Exception("This addressType is invalid. (" + addressType + ")");
        }
    }

}
