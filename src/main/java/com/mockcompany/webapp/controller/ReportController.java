package com.mockcompany.webapp.controller;

import com.mockcompany.webapp.api.SearchReportResponse;
import com.mockcompany.webapp.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;

/**
 * Management decided it is super important that we have lots of products that match the following terms.
 * So much so, that they would like a daily report of the number of products for each term along with the total
 * product count.
 */
@RestController
public class ReportController {

    /**
     * The people that wrote this code didn't know about JPA Spring Repository interfaces!
     */
    private final EntityManager entityManager;
    private final SearchService searchService;

    @Autowired
    public ReportController(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.searchService = new SearchService();
    }

    @GetMapping("/api/products/report")
    public SearchReportResponse runReport() {
        return this.searchService.report(this.entityManager);
    }
}
