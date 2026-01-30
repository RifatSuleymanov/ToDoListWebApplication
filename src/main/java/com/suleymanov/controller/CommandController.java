package com.suleymanov.controller;

import com.suleymanov.entity.RecordStatus;
import com.suleymanov.entity.dto.QueryParameters;
import com.suleymanov.entity.dto.RecordsContainerDto;
import com.suleymanov.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class CommandController {

    private final RecordService recordService;

    @Autowired
    public CommandController(RecordService recordService) {
        this.recordService = recordService;
    }

    @RequestMapping("/")
    public String redirectToHomePage(){
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String getMainPage(Model model,@RequestParam(name = "filter", required = false) String filterMode) {
        RecordsContainerDto container = recordService.findAllRecords(filterMode);
        model.addAttribute("records", container.getRecords());
        model.addAttribute("numberOfDoneRecords", container.getNumberOfDoneRecords());
        model.addAttribute("numberOfActiveRecords", container.getNumberOfActiveRecords());
        return "main-page";
    }

    @RequestMapping(value = "/add-record", method = RequestMethod.POST)
    public String addRecord(@RequestParam String title){
        recordService.saveRecord(title);
        return "redirect:/home";
    }

    @RequestMapping(value = "/make-record-done", method = RequestMethod.POST)
    public String makeRecordDone(QueryParameters parameters){
        recordService.updateRecordStatus(parameters.getId(), RecordStatus.DONE);
        return "redirect:/home" + (parameters.getFilter() != null && !parameters.getFilter().isBlank() ? "?filter=" + parameters.getFilter() : "");
    }

    @RequestMapping(value = "/delete-record", method = RequestMethod.POST)
    public String deleteRecord(QueryParameters parameters){
        recordService.deleteRecord(parameters.getId());
        return "redirect:/home" + (parameters.getFilter() != null && !parameters.getFilter().isBlank() ? "?filter=" + parameters.getFilter() : "");
    }
}
