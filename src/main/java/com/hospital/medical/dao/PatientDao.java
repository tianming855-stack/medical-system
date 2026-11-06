package com.hospital.medical.dao;

import com.hospital.medical.entity.Patient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

/**
 * 患者数据访问层，使用 JdbcTemplate 直接访问 MySQL。
 */
@Repository
public class PatientDao {

    private static final RowMapper<Patient> ROW_MAPPER = (rs, rowNum) -> {
        Date birth = rs.getDate("birth_date");
        return new Patient(
                rs.getLong("id"),
                rs.getString("patient_no"),
                rs.getString("name"),
                rs.getInt("gender"),
                birth == null ? null : birth.toLocalDate(),
                rs.getString("phone"));
    };

    private final JdbcTemplate jdbcTemplate;

    public PatientDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Patient> findAll(String keyword) {
        String base = "SELECT id, patient_no, name, gender, birth_date, phone FROM patient";
        if (keyword == null || keyword.isBlank()) {
            return jdbcTemplate.query(base + " ORDER BY id", ROW_MAPPER);
        }
        String like = "%" + keyword.trim() + "%";
        return jdbcTemplate.query(base + " WHERE name LIKE ? OR patient_no LIKE ? ORDER BY id",
                ROW_MAPPER, like, like);
    }

    public int count() {
        Integer total = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM patient", Integer.class);
        return total == null ? 0 : total;
    }

    public String databaseInfo() {
        return jdbcTemplate.queryForObject("SELECT CONCAT(VERSION(), ' / ', DATABASE())", String.class);
    }
}
