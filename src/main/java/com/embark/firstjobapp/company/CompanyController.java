package com.embark.firstjobapp.company;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);

    private CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies() {
        logger.info("Fetching all companies");
        List<Company> companies = companyService.getAllCompanies();
        logger.info("Total companies fetched: {}", companies.size());
        return new ResponseEntity<>(companies, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> findCompanyById(@PathVariable Long id) {
        logger.info("Fetching company with id: {}", id);
        Company company = companyService.findById(id);
        if (company != null) {
            logger.info("Company found: {}", company.getName());
            return new ResponseEntity<>(company, HttpStatus.OK);
        } else {
            logger.warn("Company not found with id: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<String> createCompany(@Valid @RequestBody Company company) {
        logger.info("Creating new company: {}", company.getName());
        companyService.createCompany(company);
        logger.info("Company created successfully: {}", company.getName());
        return new ResponseEntity<>("Company created successfully", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCompany(@PathVariable Long id, @RequestBody Company company) {
        logger.info("Updating company with id: {}", id);
        boolean success = companyService.updateCompany(id, company);
        if (success) {
            logger.info("Company updated successfully: {}", id);
            return new ResponseEntity<>("Company updated successfully", HttpStatus.OK);
        } else {
            logger.warn("Company update failed for id: {}", id);
            return new ResponseEntity<>("Company update failed", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable Long id) {
        logger.info("Deleting company with id: {}", id);
        companyService.deleteCompany(id);
        logger.info("Company deleted successfully: {}", id);
        return new ResponseEntity<>("Company deleted successfully", HttpStatus.OK);
    }
}
