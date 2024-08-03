package com.ryofac.livbook.livbook.Controllers;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class SweaggerController {

  @GetMapping("/")
  void handleRedirectToSweaggerUI(HttpServletResponse response) throws IOException {
    response.sendRedirect("/api/docs/");
  }
}
