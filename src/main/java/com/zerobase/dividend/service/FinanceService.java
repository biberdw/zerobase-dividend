package com.zerobase.dividend.service;

import com.zerobase.dividend.exception.impl.NoCompanyException;
import com.zerobase.dividend.model.Company;
import com.zerobase.dividend.model.Dividend;
import com.zerobase.dividend.model.ScrapedResult;
import com.zerobase.dividend.model.constants.CacheKey;
import com.zerobase.dividend.persist.CompanyRepository;
import com.zerobase.dividend.persist.DividendRepository;
import com.zerobase.dividend.persist.entity.CompanyEntity;
import com.zerobase.dividend.persist.entity.DividendEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.zerobase.dividend.model.constants.CacheKey.*;

@Service
@AllArgsConstructor
@Slf4j
public class FinanceService {

    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;

    @Cacheable(key = "#companyName", value = KEY_FINANCE)//ex -> finance::3M Company (value : prefix)
    public ScrapedResult getDividendByCompanyName(String companyName){
        log.info("search company -> " + companyName);

        // 1. 회사명을 기준으로 회사 정보를 조회
        CompanyEntity company = companyRepository.findByName(companyName)
                .orElseThrow(NoCompanyException::new);

        // 2. 조회된 회사 ID 로 배당금 정보 조회
        List<DividendEntity> dividendEntities = dividendRepository.findAllByCompanyId(company.getId());

        // 3. 결과 조합 후 반환
        List<Dividend> dividends = dividendEntities.stream()
                .map(e -> Dividend.builder()
                        .date(e.getDate())
                        .dividend(e.getDividend())
                        .build())
                .collect(Collectors.toList());

        return new ScrapedResult(Company.builder()
                .ticker(company.getTicker())
                .name(company.getName())
                .build(), dividends
        );
    }
}
