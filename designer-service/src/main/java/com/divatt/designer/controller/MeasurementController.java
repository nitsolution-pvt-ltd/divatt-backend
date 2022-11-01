package com.divatt.designer.controller;

import javax.ws.rs.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.divatt.designer.entity.MeasurementEntity;
import com.divatt.designer.exception.CustomException;
import com.divatt.designer.response.GlobalResponce;
import com.divatt.designer.services.MeasurementService;

@RestController
@RequestMapping("/designerMeasurement")
public class MeasurementController {
    
    @Autowired
    private MeasurementService measurementService;

    @PostMapping("/addMeasurement")
    public GlobalResponce addMeasurementAPI(@RequestBody MeasurementEntity measurementEntity) {
        try {
          return measurementService.add(measurementEntity);
        }catch(Exception e) {
            throw new CustomException(e.getMessage());
        }
    }
}
