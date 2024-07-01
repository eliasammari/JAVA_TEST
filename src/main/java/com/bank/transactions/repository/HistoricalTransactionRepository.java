package com.bank.transactions.repository;

import com.bank.transactions.entity.HistoricalTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricalTransactionRepository extends JpaRepository<HistoricalTransaction, Long> {
}
