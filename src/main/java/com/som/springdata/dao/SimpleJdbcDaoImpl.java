package com.som.springdata.dao;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class SimpleJdbcDaoImpl extends JdbcDaoSupport {

    public int getCircleCount(){
        String sql = "SELECT COUNT(*) FROM ADMIN.CIRCLE";
        return this.getJdbcTemplate().queryForObject(sql, Integer.class);
    }
}
