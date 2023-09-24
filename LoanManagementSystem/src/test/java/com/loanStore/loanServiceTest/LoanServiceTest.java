package com.loanStore.loanServiceTest;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.loanStore.CustomException.InvalidPaymentDateException;
import com.loanStore.CustomException.LoanNotFoundException;
import com.loanStore.dto.ResponseDto;
import com.loanStore.entity.Loan;
import com.loanStore.repository.LoanRepository;
import com.loanStore.service.LoanService;

@SpringBootTest
public class LoanServiceTest {

    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private LoanService loanService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddLoanWithValidPaymentDate() {
        Loan loan = new Loan();
        loan.setPaymentDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusDays(5));

        when(loanRepository.save(any(Loan.class))).thenReturn(loan);

        ResponseDto responseDto = loanService.addLoan(loan);

        assert(responseDto.getStatus().equals("success"));
        assert(responseDto.getStatusCode().equals("200"));
        assert(responseDto.getMessage().equals("Loan added successfully"));
        assert(responseDto.getResponse().equals(loan));
       // assert(!responseDto.isErrorResponse());
    }

    @Test
    public void testAddLoanWithInvalidPaymentDate() {
        Loan loan = new Loan();
        loan.setPaymentDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().minusDays(5));

        try {
            loanService.addLoan(loan);
        } catch (InvalidPaymentDateException ex) {
            assert(ex.getMessage().contains("Loan has been rejected"));
        }
    }

    @Test
    public void testGetLoanById() {
        String loanId = "123";
        Loan loan = new Loan();
        loan.setLoanId(loanId);

        when(loanRepository.findById(loanId)).thenReturn(Optional.of(loan));

        Loan resultLoan = loanService.getLoanById(loanId);

        assert(resultLoan != null);
        assert(resultLoan.getLoanId().equals(loanId));
    }

    @Test
    public void testGetLoanByIdNotFound() {
        String loanId = "123";

        when(loanRepository.findById(loanId)).thenReturn(Optional.empty());

        try {
            loanService.getLoanById(loanId);
        } catch (LoanNotFoundException ex) {
            assert(ex.getMessage().contains("Loan with ID"));
        }
    }

}
