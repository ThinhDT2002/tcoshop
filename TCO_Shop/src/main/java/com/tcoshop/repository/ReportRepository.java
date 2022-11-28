package com.tcoshop.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.tcoshop.entity.OrderStatusReport;
import com.tcoshop.entity.SaleReport;
import com.tcoshop.entity.TurnoverDetailReport;
import com.tcoshop.entity.TurnoverReport;
import com.tcoshop.entity.UserRegistryReport;

@Repository
public class ReportRepository {
    @PersistenceContext
    private EntityManager em;
    
    public List<SaleReport> getSaleReport(Integer year) {
        List<SaleReport> saleReport = em.createNamedQuery("findSaleReport", SaleReport.class)
                .setParameter("year", year).getResultList();
        return saleReport;
    }
    
    public List<OrderStatusReport> getOrderStatusReport(Integer year) {
        List<OrderStatusReport> report = em.createNamedQuery("findOrderStatusReport", OrderStatusReport.class)
                .setParameter("year", year).getResultList();
        return report;
    }
    
    public List<TurnoverReport> getTurnoverReport(Integer year) {
        List<TurnoverReport> report = em.createNamedQuery("findTurnoverReport", TurnoverReport.class)
                .setParameter("year", year).getResultList();
        return report;
    }
    
    public List<TurnoverDetailReport> getTurnoverDetailReport(Integer year, Integer month) {
        List<TurnoverDetailReport> report = em.createNamedQuery("findTurnoverDetailReport", TurnoverDetailReport.class)
                .setParameter("year", year).setParameter("month", month)
                .getResultList();
        return report;
    }
    
    public List<UserRegistryReport> getUserRegistryReport(int year) {
        List<UserRegistryReport> report = em.createNamedQuery("findUserRegistryReport", UserRegistryReport.class)
                .setParameter("year", year)
                .getResultList();
        return report;
    }    
}
