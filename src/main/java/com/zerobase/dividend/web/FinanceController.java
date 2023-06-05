package com.zerobase.dividend.web;

import com.zerobase.dividend.model.ScrapedResult;
import com.zerobase.dividend.service.FinanceService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/finance")
@AllArgsConstructor
public class FinanceController {

    private final FinanceService financeService;

    @PreAuthorize("hasRole('READ')")
    @GetMapping("/dividend/{companyName}")
    public ResponseEntity<?> searchFinance(@PathVariable String companyName){
        ScrapedResult result = financeService.getDividendByCompanyName(companyName);
        return ResponseEntity.ok(result);
    }
}
