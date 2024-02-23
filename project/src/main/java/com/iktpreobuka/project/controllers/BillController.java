package com.iktpreobuka.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.project.entities.BillEntity;
import com.iktpreobuka.project.services.BillService;

@RestController
@RequestMapping("/project/bills")
public class BillController {
	@Autowired
    private BillService billService;

    @GetMapping
    public Iterable<BillEntity> getAllBills() {
        return billService.findAllBills();
    }
}
