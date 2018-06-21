package com.som.springdata.dao;

import com.som.springdata.model.Circle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

@EnableAutoConfiguration(exclude=DataSourceAutoConfiguration.class)
@ImportResource({ "classpath:spring.xml"})
@Component
public class JdbcDAOImpl {


    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        return namedParameterJdbcTemplate;
    }

    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    /*public Circle getCircle(int circleId){

        Connection conn = null;
        Circle circle = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //String driver = "org.apache.derby.jdbc.ClientDriver";
            //Class.forName(driver).newInstance();
            conn = dataSource.getConnection();
                   // DriverManager.getConnection("jdbc:derby://localhost:1527/somDerbyDB");
            ps = conn.prepareStatement("select * from ADMIN.CIRCLE where ID=?");
            ps.setInt(1, circleId);
            rs = ps.executeQuery();
            if (rs.next()) {
                circle = new Circle(circleId, rs.getString("name"));
            }
            rs.close();
            ps.close();
            conn.close();

        } catch (Exception ex){
            //throw new RuntimeException(ex);
            ex.printStackTrace();
        }
        return circle;
    }*/

    public int getCircleCount(){
        String sql = "SELECT count(*) from ADMIN.CIRCLE";
        //jdbcTemplate.setDataSource(getDataSource());
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public String getCircleName(int circleId){
        String sql = "SELECT NAME FROM ADMIN.CIRCLE where ID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[] {circleId}, String.class);
    }

    public Circle getCircleForId(int circleId) {
        String sql = "SELECT * FROM ADMIN.CIRCLE where ID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{circleId}, new CircleMapper());
    }

    public List<Circle> getAllCircles(){
        String sql = "SELECT * FROM ADMIN.CIRCLE";
        return jdbcTemplate.query(sql, new CircleMapper());
    }

    public int insertCircle(Circle circle){
        String sql = "INSERT INTO ADMIN.CIRCLE(ID, NAME) VALUES (:id, :name)";
        //return jdbcTemplate.update(sql, new Object[] {circle.getId(), circle.getName()});
        SqlParameterSource namedParameter = new MapSqlParameterSource();
        ((MapSqlParameterSource) namedParameter).addValue("id", circle.getId());
        ((MapSqlParameterSource) namedParameter).addValue("name", circle.getName());
        return namedParameterJdbcTemplate.update(sql, namedParameter);
    }

    public void createTriangleTable(){
        String sql = "CREATE TABLE ADMIN.TRIANGLE(ID INTEGER, NAME VARCHAR(50))";
        jdbcTemplate.execute(sql);
    }


    private static final class CircleMapper implements RowMapper<Circle> {

        @Override
        public Circle mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            Circle circle = new Circle();
            circle.setId (resultSet.getInt("ID"));
            circle.setName(resultSet.getString("NAME"));
            return circle;
        }
    }

}
