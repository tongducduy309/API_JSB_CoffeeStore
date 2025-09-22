package com.api_coffee_store.api_coffee_store.repositories;

import com.api_coffee_store.api_coffee_store.models.InvoiceSequence;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceSequenceRepository extends JpaRepository<InvoiceSequence,String> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE invoice_sequence SET next_val = next_val + 1 WHERE period = :period", nativeQuery = true)
    int increment(@Param("period") String period);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO invoice_sequence(period, next_val) VALUES (:period, 1)", nativeQuery = true)
    void insertInitial(@Param("period") String period) throws DataIntegrityViolationException;

    @Query(value = "SELECT next_val FROM invoice_sequence WHERE period = :period", nativeQuery = true)
    Long findNextVal(@Param("period") String period);
}
