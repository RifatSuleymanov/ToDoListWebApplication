package com.suleymanov.controller;

import com.suleymanov.entity.Record;
import com.suleymanov.entity.RecordStatus;
import com.suleymanov.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class CommandController {

    private final RecordService recordService;

    @Autowired
    public CommandController(RecordService recordService) {
        this.recordService = recordService;
    }

    @RequestMapping("/home")
    public String getMainPage(Model model) {
        List<Record> records = recordService.findAllRecords();
        int numberOfDoneRecords = (int) records.stream().filter(record -> record.getStatus() == RecordStatus.DONE).count();
        int numberOfActiveRecords = (int) records.stream().filter(record -> record.getStatus() == RecordStatus.ACTIVE).count();
        model.addAttribute("numberOfDoneRecords", numberOfDoneRecords);
        model.addAttribute("numberOfActiveRecords", numberOfActiveRecords);
        return "main-page";
    }
}
