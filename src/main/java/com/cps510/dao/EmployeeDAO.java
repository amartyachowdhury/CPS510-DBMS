package com.cps510.dao;

import com.cps510.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class EmployeeDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final class EmployeeRowMapper implements RowMapper<Employee> {
        @Override
        public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
            Employee employee = new Employee();
            employee.setEmployeeId(rs.getLong("employee_id"));
            employee.setEmployeeName(rs.getString("employee_name"));
            employee.setEmployeeEmail(rs.getString("employee_email"));
            employee.setEmployeePhone(rs.getString("employee_phone"));
            employee.setEmployeeRole(rs.getString("employee_role"));
            return employee;
        }
    }

    public List<Employee> findAll() {
        String sql = "SELECT employee_id, employee_name, employee_email, employee_phone, employee_role FROM Employee ORDER BY employee_name";
        return jdbcTemplate.query(sql, new EmployeeRowMapper());
    }

    public Employee findById(Long employeeId) {
        String sql = "SELECT employee_id, employee_name, employee_email, employee_phone, employee_role FROM Employee WHERE employee_id = ?";
        List<Employee> employees = jdbcTemplate.query(sql, new EmployeeRowMapper(), employeeId);
        return employees.isEmpty() ? null : employees.get(0);
    }

    public Long insert(Employee employee) {
        String sql = "INSERT INTO Employee (employee_name, employee_email, employee_phone, employee_role) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, employee.getEmployeeName(), employee.getEmployeeEmail(), 
                           employee.getEmployeePhone(), employee.getEmployeeRole());
        
        // Get the generated ID from sequence
        String seqSql = "SELECT EMPLOYEE_SEQ.CURRVAL FROM dual";
        try {
            return jdbcTemplate.queryForObject(seqSql, Long.class);
        } catch (Exception e) {
            String maxSql = "SELECT NVL(MAX(employee_id), 0) FROM Employee";
            return jdbcTemplate.queryForObject(maxSql, Long.class);
        }
    }

    public int update(Employee employee) {
        String sql = "UPDATE Employee SET employee_name = ?, employee_email = ?, employee_phone = ?, employee_role = ? WHERE employee_id = ?";
        return jdbcTemplate.update(sql, employee.getEmployeeName(), employee.getEmployeeEmail(), 
                                   employee.getEmployeePhone(), employee.getEmployeeRole(), employee.getEmployeeId());
    }

    public int delete(Long employeeId) {
        String sql = "DELETE FROM Employee WHERE employee_id = ?";
        return jdbcTemplate.update(sql, employeeId);
    }
}

