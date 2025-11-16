package com.cps510.dao;

import com.cps510.model.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class OrderItemDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final class OrderItemRowMapper implements RowMapper<OrderItem> {
        @Override
        public OrderItem mapRow(ResultSet rs, int rowNum) throws SQLException {
            OrderItem item = new OrderItem();
            item.setOrderId(rs.getLong("order_id"));
            item.setProductId(rs.getLong("product_id"));
            item.setItemQty(rs.getInt("item_qty"));
            item.setUnitPrice(rs.getBigDecimal("unit_price"));
            try {
                item.setProductName(rs.getString("product_name"));
            } catch (SQLException e) {
                // May not be present in all queries
            }
            try {
                item.setProductBrand(rs.getString("product_brand"));
            } catch (SQLException e) {
                // May not be present in all queries
            }
            try {
                item.setCategoryName(rs.getString("category_name"));
            } catch (SQLException e) {
                // May not be present in all queries
            }
            try {
                item.setLineTotal(rs.getBigDecimal("line_total"));
            } catch (SQLException e) {
                // Calculate if not present
            }
            return item;
        }
    }

    public List<OrderItem> findByOrder(Long orderId) {
        String sql = "SELECT * FROM V_ORDER_LINE_ITEMS WHERE order_id = ? ORDER BY product_name";
        return jdbcTemplate.query(sql, new OrderItemRowMapper(), orderId);
    }

    public OrderItem findByOrderAndProduct(Long orderId, Long productId) {
        String sql = "SELECT oi.order_id, oi.product_id, oi.item_qty, oi.unit_price, " +
                     "p.product_name, p.product_brand, c.category_name " +
                     "FROM OrderItem oi JOIN Product p ON oi.product_id = p.product_id " +
                     "JOIN Category_ c ON p.category_id = c.category_id " +
                     "WHERE oi.order_id = ? AND oi.product_id = ?";
        List<OrderItem> items = jdbcTemplate.query(sql, new OrderItemRowMapper(), orderId, productId);
        return items.isEmpty() ? null : items.get(0);
    }

    public int insert(OrderItem item) {
        String sql = "INSERT INTO OrderItem (order_id, product_id, item_qty, unit_price) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, item.getOrderId(), item.getProductId(), 
                                   item.getItemQty(), item.getUnitPrice());
    }

    public int update(OrderItem item) {
        String sql = "UPDATE OrderItem SET item_qty = ?, unit_price = ? WHERE order_id = ? AND product_id = ?";
        return jdbcTemplate.update(sql, item.getItemQty(), item.getUnitPrice(),
                                   item.getOrderId(), item.getProductId());
    }

    public int delete(Long orderId, Long productId) {
        String sql = "DELETE FROM OrderItem WHERE order_id = ? AND product_id = ?";
        return jdbcTemplate.update(sql, orderId, productId);
    }

    public int deleteByOrder(Long orderId) {
        String sql = "DELETE FROM OrderItem WHERE order_id = ?";
        return jdbcTemplate.update(sql, orderId);
    }

    public BigDecimal calculateOrderTotal(Long orderId) {
        String sql = "SELECT SUM(item_qty * unit_price) FROM OrderItem WHERE order_id = ?";
        BigDecimal total = jdbcTemplate.queryForObject(sql, BigDecimal.class, orderId);
        return total != null ? total : BigDecimal.ZERO;
    }
}

