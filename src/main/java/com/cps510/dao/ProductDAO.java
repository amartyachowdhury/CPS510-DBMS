package com.cps510.dao;

import com.cps510.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProductDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final class ProductRowMapper implements RowMapper<Product> {
        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            Product product = new Product();
            product.setProductId(rs.getLong("product_id"));
            product.setProductName(rs.getString("product_name"));
            product.setProductSize(rs.getString("product_size"));
            product.setProductColour(rs.getString("product_colour"));
            product.setProductBrand(rs.getString("product_brand"));
            product.setProductPrice(rs.getBigDecimal("product_price"));
            product.setProductStockQty(rs.getInt("product_stock_qty"));
            product.setCategoryId(rs.getLong("category_id"));
            try {
                product.setCategoryName(rs.getString("category_name"));
            } catch (SQLException e) {
                // category_name may not be present in all queries
            }
            return product;
        }
    }

    public List<Product> findAll() {
        String sql = "SELECT p.product_id, p.product_name, p.product_size, p.product_colour, p.product_brand, " +
                     "p.product_price, p.product_stock_qty, p.category_id, c.category_name " +
                     "FROM Product p JOIN Category_ c ON p.category_id = c.category_id " +
                     "ORDER BY p.product_name";
        return jdbcTemplate.query(sql, new ProductRowMapper());
    }

    public Product findById(Long productId) {
        String sql = "SELECT p.product_id, p.product_name, p.product_size, p.product_colour, p.product_brand, " +
                     "p.product_price, p.product_stock_qty, p.category_id, c.category_name " +
                     "FROM Product p JOIN Category_ c ON p.category_id = c.category_id " +
                     "WHERE p.product_id = ?";
        List<Product> products = jdbcTemplate.query(sql, new ProductRowMapper(), productId);
        return products.isEmpty() ? null : products.get(0);
    }

    public List<Product> findByCategory(Long categoryId) {
        String sql = "SELECT p.product_id, p.product_name, p.product_size, p.product_colour, p.product_brand, " +
                     "p.product_price, p.product_stock_qty, p.category_id, c.category_name " +
                     "FROM Product p JOIN Category_ c ON p.category_id = c.category_id " +
                     "WHERE p.category_id = ? ORDER BY p.product_name";
        return jdbcTemplate.query(sql, new ProductRowMapper(), categoryId);
    }

    public Long insert(Product product) {
        String sql = "INSERT INTO Product (product_name, product_size, product_colour, product_brand, " +
                     "product_price, product_stock_qty, category_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, product.getProductName(), product.getProductSize(), product.getProductColour(),
                           product.getProductBrand(), product.getProductPrice(), product.getProductStockQty(),
                           product.getCategoryId());
        
        String seqSql = "SELECT PRODUCT_SEQ.CURRVAL FROM dual";
        try {
            return jdbcTemplate.queryForObject(seqSql, Long.class);
        } catch (Exception e) {
            String maxSql = "SELECT NVL(MAX(product_id), 0) FROM Product";
            return jdbcTemplate.queryForObject(maxSql, Long.class);
        }
    }

    public int update(Product product) {
        String sql = "UPDATE Product SET product_name = ?, product_size = ?, product_colour = ?, " +
                     "product_brand = ?, product_price = ?, product_stock_qty = ?, category_id = ? " +
                     "WHERE product_id = ?";
        return jdbcTemplate.update(sql, product.getProductName(), product.getProductSize(), 
                                   product.getProductColour(), product.getProductBrand(), 
                                   product.getProductPrice(), product.getProductStockQty(),
                                   product.getCategoryId(), product.getProductId());
    }

    public int delete(Long productId) {
        String sql = "DELETE FROM Product WHERE product_id = ?";
        return jdbcTemplate.update(sql, productId);
    }
}

