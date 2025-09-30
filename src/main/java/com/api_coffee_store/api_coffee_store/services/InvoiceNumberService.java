package com.api_coffee_store.api_coffee_store.services;

import com.api_coffee_store.api_coffee_store.repositories.InvoiceSequenceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class InvoiceNumberService {

    private final InvoiceSequenceRepository repo;

    private static final DateTimeFormatter YM = DateTimeFormatter.ofPattern("yyyyMM");

    @Transactional
    public String nextInvoiceCode() {
        String period = LocalDate.now().format(YM);

        int updated = repo.increment(period);

        if (updated == 0) {
            try {
                repo.insertInitial(period);
            } catch (DataIntegrityViolationException e) {
                repo.increment(period);
            }
        }

        Long val = repo.findNextVal(period);
        String six = String.format("%06d", val);
        return period + six ;
    }
}

