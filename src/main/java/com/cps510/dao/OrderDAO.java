package com.cps510.dao;

import com.cps510.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class OrderDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final class OrderRowMapper implements RowMapper<Order> {
        @Override
        public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
            Order order = new Order();
            order.setOrderId(rs.getLong("order_id"));
            order.setOrderDate(rs.getTimestamp("order_date"));
            order.setTotalAmount(rs.getBigDecimal("total_amount"));
            order.setOrderStatus(rs.getString("order_status"));
            // Note: V_ORDERS_SUMMARY view doesn't include customer_id/employee_id, only names
            try {
                order.setCustomerId(rs.getLong("customer_id"));
            } catch (SQLException e) {
                // Not in view, will be null
            }
            try {
                order.setEmployeeId(rs.getLong("employee_id"));
            } catch (SQLException e) {
                // Not in view, will be null
            }
            try {
                order.setCustomerName(rs.getString("customer_name"));
            } catch (SQLException e) {
                // May not be present in all queries
            }
            try {
                order.setEmployeeName(rs.getString("employee_name"));
            } catch (SQLException e) {
                // May not be present in all queries
            }
            return order;
        }
    }

    public List<Order> findAll() {
        String sql = "SELECT * FROM V_ORDERS_SUMMARY ORDER BY order_date DESC";
        return jdbcTemplate.query(sql, new OrderRowMapper());
    }

    public Order findById(Long orderId) {
        // Use direct table query to get IDs when needed
        String sql = "SELECT o.order_id, o.order_date, o.total_amount, o.order_status, " +
                     "o.customer_id, o.employee_id, c.customer_name, e.employee_name " +
                     "FROM Order_ o JOIN Customer c ON o.customer_id = c.customer_id " +
                     "JOIN Employee e ON o.employee_id = e.employee_id " +
                     "WHERE o.order_id = ?";
        List<Order> orders = jdbcTemplate.query(sql, new OrderRowMapperWithIds(), orderId);
        return orders.isEmpty() ? null : orders.get(0);
    }

    public List<Order> findByCustomer(Long customerId) {
        String sql = "SELECT o.order_id, o.order_date, o.total_amount, o.order_status, " +
                     "o.customer_id, o.employee_id, c.customer_name, e.employee_name " +
                     "FROM Order_ o JOIN Customer c ON o.customer_id = c.customer_id " +
                     "JOIN Employee e ON o.employee_id = e.employee_id " +
                     "WHERE o.customer_id = ? ORDER BY o.order_date DESC";
        return jdbcTemplate.query(sql, new OrderRowMapperWithIds(), customerId);
    }

    private static final class OrderRowMapperWithIds implements RowMapper<Order> {
        @Override
        public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
            Order order = new Order();
            order.setOrderId(rs.getLong("order_id"));
            order.setOrderDate(rs.getTimestamp("order_date"));
            order.setTotalAmount(rs.getBigDecimal("total_amount"));
            order.setOrderStatus(rs.getString("order_status"));
            order.setCustomerId(rs.getLong("customer_id"));
            order.setEmployeeId(rs.getLong("employee_id"));
            try {
                order.setCustomerName(rs.getString("customer_name"));
            } catch (SQLException e) {
                // May not be present in all queries
            }
            try {
                order.setEmployeeName(rs.getString("employee_name"));
            } catch (SQLException e) {
                // May not be present in all queries
            }
            return order;
        }
    }

    public Long insert(Order order) {
        String sql = "INSERT INTO Order_ (order_date, total_amount, order_status, customer_id, employee_id) " +
                     "VALUES (?, ?, ?, ?, ?)";
        Date orderDate = order.getOrderDate() != null ? order.getOrderDate() : new Date();
        jdbcTemplate.update(sql, orderDate, order.getTotalAmount(), order.getOrderStatus(),
                           order.getCustomerId(), order.getEmployeeId());
        
        String seqSql = "SELECT ORDER_SEQ.CURRVAL FROM dual";
        try {
            return jdbcTemplate.queryForObject(seqSql, Long.class);
        } catch (Exception e) {
            String maxSql = "SELECT NVL(MAX(order_id), 0) FROM Order_";
            return jdbcTemplate.queryForObject(maxSql, Long.class);
        }
    }

    public int update(Order order) {
        String sql = "UPDATE Order_ SET order_date = ?, total_amount = ?, order_status = ?, " +
                     "customer_id = ?, employee_id = ? WHERE order_id = ?";
        return jdbcTemplate.update(sql, order.getOrderDate(), order.getTotalAmount(), 
                                   order.getOrderStatus(), order.getCustomerId(), 
                                   order.getEmployeeId(), order.getOrderId());
    }

    public int delete(Long orderId) {
        String sql = "DELETE FROM Order_ WHERE order_id = ?";
        return jdbcTemplate.update(sql, orderId);
    }

    public int updateTotalAmount(Long orderId, BigDecimal totalAmount) {
        String sql = "UPDATE Order_ SET total_amount = ? WHERE order_id = ?";
        return jdbcTemplate.update(sql, totalAmount, orderId);
    }

    public int updateOrderStatus(Long orderId, String status) {
        String sql = "UPDATE Order_ SET order_status = ? WHERE order_id = ?";
        return jdbcTemplate.update(sql, status, orderId);
    }

    public BigDecimal getOrderTotalAmount(Long orderId) {
        String sql = "SELECT total_amount FROM Order_ WHERE order_id = ?";
        try {
            BigDecimal total = jdbcTemplate.queryForObject(sql, BigDecimal.class, orderId);
            return total != null ? total : BigDecimal.ZERO;
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }
}

