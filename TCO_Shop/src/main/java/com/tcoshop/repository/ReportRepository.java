package com.tcoshop.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.tcoshop.entity.OrderStatusReport;
import com.tcoshop.entity.SaleReport;
import com.tcoshop.entity.TurnoverReport;

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
}
