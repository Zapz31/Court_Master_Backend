package com.swp391.Court_Master.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/test")
public class testAPI {
    @GetMapping("path")
    public String allAccess() {
        return "Public asscess content";
    }

    @GetMapping("/customer")
    @PreAuthorize("hasAuthority('USER_CUSTOMER')")
    public String getMethodName() {
        return " chi co ta moi la dang chi ton";
    }
    
    
}
