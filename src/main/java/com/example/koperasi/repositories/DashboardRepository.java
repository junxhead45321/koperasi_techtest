package com.example.koperasi.repositories;

import com.example.koperasi.model.entity.Loan;
import com.example.koperasi.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DashboardRepository extends JpaRepository<Member, UUID> {

    // 1. FINANCIAL REPORT
    @Query(value = """
        SELECT 
            m.id,
            m.name,
            COALESCE(SUM(s.amount),0) AS total_saving,
            COALESCE(SUM(l.amount),0) AS total_loan,
            (COALESCE(SUM(s.amount),0) - COALESCE(SUM(l.amount),0)) AS net_balance
        FROM members m
        LEFT JOIN savings s ON m.id = s.member_id
        LEFT JOIN loans l ON m.id = l.member_id
        GROUP BY m.id, m.name
        ORDER BY net_balance DESC
    """, nativeQuery = true)
    List<Object[]> getMemberFinancialReport();

    // 2. LOAN APPROVAL RATE
    @Query(value = """
        SELECT 
            m.id,
            m.name,
            COUNT(l.id) AS total_loans,
            SUM(CASE WHEN l.status = 'APPROVED' THEN 1 ELSE 0 END) AS approved_loans,
            ROUND(
                (SUM(CASE WHEN l.status = 'APPROVED' THEN 1 ELSE 0 END) * 100.0)
                / NULLIF(COUNT(l.id),0),
            2) AS approval_rate
        FROM members m
        LEFT JOIN loans l ON m.id = l.member_id
        GROUP BY m.id, m.name
    """, nativeQuery = true)
    List<Object[]> getLoanApprovalRate();

    // 3. TOP ACTIVE MEMBERS
    @Query(value = """
        SELECT 
            m.id,
            m.name,
            COUNT(DISTINCT s.id) AS saving_count,
            COUNT(DISTINCT l.id) AS loan_count,
            (COUNT(DISTINCT s.id) + COUNT(DISTINCT l.id)) AS activity_score
        FROM members m
        LEFT JOIN savings s ON m.id = s.member_id
        LEFT JOIN loans l ON m.id = l.member_id
        GROUP BY m.id, m.name
        ORDER BY activity_score DESC
        LIMIT 10
    """, nativeQuery = true)
    List<Object[]> getTopActiveMembers();

    // 4. HIGH RISK MEMBERS
    @Query(value = """
        SELECT 
            m.id,
            m.name,
            COALESCE(SUM(s.amount),0) AS saving,
            COALESCE(SUM(l.amount),0) AS loan,
            (COALESCE(SUM(l.amount),0) - COALESCE(SUM(s.amount),0)) AS risk_gap
        FROM members m
        LEFT JOIN savings s ON m.id = s.member_id
        LEFT JOIN loans l ON m.id = l.member_id
        GROUP BY m.id, m.name
        HAVING COALESCE(SUM(l.amount),0) > COALESCE(SUM(s.amount),0)
        ORDER BY risk_gap DESC
    """, nativeQuery = true)
    List<Object[]> getHighRiskMembers();

    // 5. DASHBOARD SUMMARY (GLOBAL KPI)
    @Query(value = """
        SELECT 
            (SELECT COUNT(*) FROM members) AS total_members,
            (SELECT COALESCE(SUM(amount),0) FROM savings) AS total_savings,
            (SELECT COALESCE(SUM(amount),0) FROM loans) AS total_loans
    """, nativeQuery = true)
    Object getDashboardSummary();
}