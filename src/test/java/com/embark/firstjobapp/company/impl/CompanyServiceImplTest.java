package com.embark.firstjobapp.company.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.embark.firstjobapp.company.Company;
import com.embark.firstjobapp.company.CompanyRepository;

@ExtendWith(MockitoExtension.class)
class CompanyServiceImplTest {

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyServiceImpl companyService;

    @Test
    void shouldReturnAllCompanies() {
        List<Company> companies = List.of(new Company(), new Company());

        when(companyRepository.findAll()).thenReturn(companies);

        List<Company> result = companyService.getAllCompanies();

        assertEquals(2, result.size());
        verify(companyRepository).findAll();
    }

    @Test
    void shouldCreateCompany() {
        Company company = new Company();

        companyService.createCompany(company);

        verify(companyRepository).save(company);
    }

    @Test
    void shouldReturnCompanyWhenFoundById() {
        Long companyId = 1L;
        Company company = new Company();
        company.setId(companyId);

        when(companyRepository.findById(companyId)).thenReturn(Optional.of(company));

        Company result = companyService.findById(companyId);

        assertNotNull(result);
        assertEquals(companyId, result.getId());
    }

    @Test
    void shouldReturnNullWhenCompanyNotFoundById() {
        when(companyRepository.findById(1L)).thenReturn(Optional.empty());

        Company result = companyService.findById(1L);

        assertNull(result);
    }

    @Test
    void shouldUpdateCompanySuccessfully() {
        Long companyId = 1L;

        Company existingCompany = new Company();
        existingCompany.setId(companyId);

        Company updatedCompany = new Company();
        updatedCompany.setName("Updated Name");
        updatedCompany.setDescription("Updated Description");

        when(companyRepository.findById(companyId))
                .thenReturn(Optional.of(existingCompany));

        Boolean result = companyService.updateCompany(companyId, updatedCompany);

        assertTrue(result);
        assertEquals("Updated Name", existingCompany.getName());
        assertEquals("Updated Description", existingCompany.getDescription());
        verify(companyRepository).save(existingCompany);
    }

    @Test
    void shouldFailToUpdateWhenCompanyNotFound() {
        when(companyRepository.findById(1L)).thenReturn(Optional.empty());

        Boolean result = companyService.updateCompany(1L, new Company());

        assertFalse(result);
        verify(companyRepository, never()).save(any());
    }

    @Test
    void shouldDeleteCompanyWhenExists() {
        Long companyId = 1L;

        when(companyRepository.findById(companyId))
                .thenReturn(Optional.of(new Company()));

        companyService.deleteCompany(companyId);

        verify(companyRepository).deleteById(companyId);
    }

    @Test
    void shouldNotDeleteCompanyWhenNotExists() {
        when(companyRepository.findById(1L))
                .thenReturn(Optional.empty());

        companyService.deleteCompany(1L);

        verify(companyRepository, never()).deleteById(any());
    }
}
