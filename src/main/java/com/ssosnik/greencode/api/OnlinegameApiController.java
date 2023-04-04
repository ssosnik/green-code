package com.ssosnik.greencode.api;

import java.util.Optional;

import javax.annotation.Generated;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;

@Generated(value = "com.ssosnik.greencode.codegen.languages.SpringCodegen", date = "2023-04-01T16:14:58.930237600+02:00[Europe/Warsaw]")
@Controller
@RequestMapping("${openapi.onlineGameTask.base-path:}")
public class OnlinegameApiController implements OnlinegameApi {

    private final NativeWebRequest request;

    @Autowired
    public OnlinegameApiController(NativeWebRequest request) {
        this.request = request;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

}