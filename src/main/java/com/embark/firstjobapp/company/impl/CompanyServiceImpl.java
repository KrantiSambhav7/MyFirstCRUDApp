package com.embark.firstjobapp.company.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.embark.firstjobapp.company.Company;
import com.embark.firstjobapp.company.CompanyRepository;
import com.embark.firstjobapp.company.CompanyService;

@Service
public class CompanyServiceImpl implements CompanyService {
    private CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Boolean updateCompany(Long id, Company company) {
        Company existingCompany = companyRepository.findById(id).orElse(null);
        if(existingCompany != null){
            existingCompany.setName(company.getName());
            existingCompany.setDescription(company.getDescription());
            companyRepository.save(existingCompany);
            return true;
        }
        return false;
    }

    @Override
    public void createCompany(Company company) {
        companyRepository.save(company);
    }

    @Override
    public void deleteCompany(Long id) {
        if(companyRepository.findById(id).isPresent()){
            companyRepository.deleteById(id);
        }
    }

    @Override
    public Company findById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }
}
