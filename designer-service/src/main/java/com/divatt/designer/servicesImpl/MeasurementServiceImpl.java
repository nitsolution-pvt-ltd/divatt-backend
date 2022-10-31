package com.divatt.designer.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.divatt.designer.entity.MeasurementEntity;
import com.divatt.designer.exception.CustomException;
import com.divatt.designer.repo.MeasurementRepo;
import com.divatt.designer.response.GlobalResponce;
import com.divatt.designer.services.MeasurementService;

@Service
public class MeasurementServiceImpl implements MeasurementService {
    @Autowired
    private MeasurementRepo measurementRepo;
    @Override
    public GlobalResponce add(MeasurementEntity entity) {
        try {
        measurementRepo.save(entity);
        return new GlobalResponce("Sucess", "Data Added Sucessfully", 200);
        
    }catch (Exception e) {
       throw new CustomException(e.getMessage());
    }

}
}