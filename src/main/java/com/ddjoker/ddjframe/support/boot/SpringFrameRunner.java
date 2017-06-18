package com.ddjoker.ddjframe.support.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

/**
 * Created by dong on 2017/6/18.
 */

@SpringBootApplication
@ComponentScan(basePackages = "com.ddjoker.ddjframe")
public class SpringFrameRunner {

  public static void main(String[] args) {
    SpringApplication.run(SpringFrameRunner.class);
  }
}
