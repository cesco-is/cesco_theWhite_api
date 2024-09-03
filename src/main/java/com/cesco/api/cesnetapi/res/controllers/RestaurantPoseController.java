package com.cesco.api.cesnetapi.res.controllers;

import com.cesco.api.cesnetapi.cm.models.CesResponse;
import com.cesco.api.cesnetapi.cm.models.CesResponseHeader;
import com.cesco.api.cesnetapi.res.services.RestaurantService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/res")
public class RestaurantPoseController {

    @Autowired
    private RestaurantService restaurantService;
    
    @PostMapping("/dailysales")
    @ResponseBody
    public ResponseEntity<CesResponse> setDailySales() throws Exception 
    {
        CesResponse rc = new CesResponse(new CesResponseHeader(), restaurantService.setDailySalesList());
        return new ResponseEntity<CesResponse>(rc, HttpStatus.OK);
    }

    @PostMapping("/franchises")
    @ResponseBody
    public ResponseEntity<CesResponse> setFranchises() throws Exception 
    {
        CesResponse rc = new CesResponse(new CesResponseHeader(), restaurantService.setFranchisesList());
        return new ResponseEntity<CesResponse>(rc, HttpStatus.OK);
    }

    @PostMapping("/salesdtl")
    @ResponseBody
    public ResponseEntity<CesResponse> setSalesDtl() throws Exception 
    {
        CesResponse rc = new CesResponse(new CesResponseHeader(), restaurantService.setSalesDtlList());
        return new ResponseEntity<CesResponse>(rc, HttpStatus.OK);
    }

    @PostMapping("/itemlist")
    @ResponseBody
    public ResponseEntity<CesResponse> setItemlist() throws Exception 
    {
        CesResponse rc = new CesResponse(new CesResponseHeader(), restaurantService.setItemListList());
        return new ResponseEntity<CesResponse>(rc, HttpStatus.OK);
    }
    
    @PostMapping("/salesreceipt")
    @ResponseBody
    public ResponseEntity<CesResponse> setSalesreceipt() throws Exception 
    {
        CesResponse rc = new CesResponse(new CesResponseHeader(), restaurantService.setSalesreceiptList());
        return new ResponseEntity<CesResponse>(rc, HttpStatus.OK);
    }

    @PostMapping("/salesreceiptdtl")
    @ResponseBody
    public ResponseEntity<CesResponse> setSalesreceiptDtlList() throws Exception 
    {
        CesResponse rc = new CesResponse(new CesResponseHeader(), restaurantService.setSalesreceiptDtlList());
        return new ResponseEntity<CesResponse>(rc, HttpStatus.OK);
    }
    
    @PostMapping("/prepaidcardhead")
    @ResponseBody
    public ResponseEntity<CesResponse> setPrepaidCardSale() throws Exception 
    {
        CesResponse rc = new CesResponse(new CesResponseHeader(), restaurantService.setPrepaidCardSale());
        return new ResponseEntity<CesResponse>(rc, HttpStatus.OK);
    }
    
    @PostMapping("/prepaidcarddtl")
    @ResponseBody
    public ResponseEntity<CesResponse> setPrepaidCardSaleDtl() throws Exception 
    {
        CesResponse rc = new CesResponse(new CesResponseHeader(), restaurantService.setPrepaidCardSaleDtl());
        return new ResponseEntity<CesResponse>(rc, HttpStatus.OK);
    }
}
