package com.lasse.web;

import com.lasse.util.CallTomcat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Lance on 2018/4/8.
 */
@RestController
public class MyController {

    @Autowired
    private CallTomcat callTomcat;

    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public @ResponseBody
    void start(@RequestParam(value = "url", required = false) String url) {
        callTomcat.changeType(url);
    }

    @RequestMapping(value = "/close", method = RequestMethod.POST)
    public @ResponseBody
    String close(@RequestParam(value = "url", required = false) String url) {
        callTomcat.changeType(url);
        return "close successful";
    }
}
