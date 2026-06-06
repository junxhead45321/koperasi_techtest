package com.example.koperasi.repositories;

import com.example.koperasi.model.entity.Saving;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface SavingRepository extends JpaRepository<Saving, UUID> {
    List<Saving> findByMemberId(UUID memberId);


    @Query(value = """
        SELECT COALESCE(SUM(amount), 0)
        FROM savings
        WHERE member_id = :memberId
    """, nativeQuery = true)
    BigDecimal getTotalSavingByMember(UUID memberId);
}