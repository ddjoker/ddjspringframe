package com.ddjoker.ddjframe.web.controller.error;

import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.MediaType;

/**
 * Created by dong on 2017/6/19.
 */
@Controller
public class BaseErrorController extends BasicErrorController {

  public BaseErrorController(
      ErrorAttributes errorAttributes,
      ErrorProperties errorProperties) {
    super(errorAttributes, errorProperties);
  }
  @RequestMapping(produces={MediaType.APPLICATION_JSON_VALUE})
  public String error1(){
    return  "error1";
  }
}
