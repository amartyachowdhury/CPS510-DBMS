package com.cps510.dao;

import com.cps510.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CategoryDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final class CategoryRowMapper implements RowMapper<Category> {
        @Override
        public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
            Category category = new Category();
            category.setCategoryId(rs.getLong("category_id"));
            category.setCategoryName(rs.getString("category_name"));
            return category;
        }
    }

    public List<Category> findAll() {
        String sql = "SELECT category_id, category_name FROM Category_ ORDER BY category_name";
        return jdbcTemplate.query(sql, new CategoryRowMapper());
    }

    public Category findById(Long categoryId) {
        String sql = "SELECT category_id, category_name FROM Category_ WHERE category_id = ?";
        List<Category> categories = jdbcTemplate.query(sql, new CategoryRowMapper(), categoryId);
        return categories.isEmpty() ? null : categories.get(0);
    }

    public int insert(Category category) {
        String sql = "INSERT INTO Category_ (category_id, category_name) VALUES (?, ?)";
        return jdbcTemplate.update(sql, category.getCategoryId(), category.getCategoryName());
    }

    public int update(Category category) {
        String sql = "UPDATE Category_ SET category_name = ? WHERE category_id = ?";
        return jdbcTemplate.update(sql, category.getCategoryName(), category.getCategoryId());
    }

    public int delete(Long categoryId) {
        String sql = "DELETE FROM Category_ WHERE category_id = ?";
        return jdbcTemplate.update(sql, categoryId);
    }
}

