package org.charles.controller;

import org.charles.entity.Named;
import org.charles.service.NLPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NamedController {

    @Autowired
    private NLPService nlpService;

    @Async
    @PostMapping("queryNamedList")
    public List<Named> queryNamedList(@RequestParam String context) {
        nlpService.precess(context);
        return nlpService.NamedList(context);
    }

}
