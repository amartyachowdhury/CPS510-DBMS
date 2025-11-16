package com.cps510.dao;

import com.cps510.model.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PaymentDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final class PaymentRowMapper implements RowMapper<Payment> {
        @Override
        public Payment mapRow(ResultSet rs, int rowNum) throws SQLException {
            Payment payment = new Payment();
            payment.setPaymentId(rs.getLong("payment_id"));
            payment.setOrderId(rs.getLong("order_id"));
            payment.setPaymentMethod(rs.getString("payment_method"));
            payment.setPaymentAmount(rs.getBigDecimal("payment_amount"));
            payment.setPaymentStatus(rs.getString("payment_status"));
            try {
                payment.setOrderDate(rs.getTimestamp("order_date"));
            } catch (SQLException e) {
                // May not be present in all queries
            }
            try {
                payment.setCustomerName(rs.getString("customer_name"));
            } catch (SQLException e) {
                // May not be present in all queries
            }
            return payment;
        }
    }

    public List<Payment> findAll() {
        String sql = "SELECT * FROM V_PAYMENTS ORDER BY payment_id DESC";
        return jdbcTemplate.query(sql, new PaymentRowMapper());
    }

    public Payment findById(Long paymentId) {
        String sql = "SELECT * FROM V_PAYMENTS WHERE payment_id = ?";
        List<Payment> payments = jdbcTemplate.query(sql, new PaymentRowMapper(), paymentId);
        return payments.isEmpty() ? null : payments.get(0);
    }

    public List<Payment> findByOrder(Long orderId) {
        String sql = "SELECT * FROM V_PAYMENTS WHERE order_id = ?";
        return jdbcTemplate.query(sql, new PaymentRowMapper(), orderId);
    }

    public Long insert(Payment payment) {
        String sql = "INSERT INTO Payment (order_id, payment_method, payment_amount, payment_status) " +
                     "VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, payment.getOrderId(), payment.getPaymentMethod(),
                           payment.getPaymentAmount(), payment.getPaymentStatus());
        
        String seqSql = "SELECT PAYMENT_SEQ.CURRVAL FROM dual";
        try {
            return jdbcTemplate.queryForObject(seqSql, Long.class);
        } catch (Exception e) {
            String maxSql = "SELECT NVL(MAX(payment_id), 0) FROM Payment";
            return jdbcTemplate.queryForObject(maxSql, Long.class);
        }
    }

    public int update(Payment payment) {
        String sql = "UPDATE Payment SET order_id = ?, payment_method = ?, payment_amount = ?, " +
                     "payment_status = ? WHERE payment_id = ?";
        return jdbcTemplate.update(sql, payment.getOrderId(), payment.getPaymentMethod(),
                                   payment.getPaymentAmount(), payment.getPaymentStatus(),
                                   payment.getPaymentId());
    }

    public int delete(Long paymentId) {
        String sql = "DELETE FROM Payment WHERE payment_id = ?";
        return jdbcTemplate.update(sql, paymentId);
    }
}

