package com.embark.firstjobapp.company;

import java.util.List;

public interface CompanyService {
    List<Company> getAllCompanies();
    void createCompany(Company company);
    Boolean updateCompany(Long id, Company company);
    void deleteCompany(Long id);
    Company findById(Long id);
} 