package com.som.springdata;

import com.som.springdata.dao.JdbcDAOImpl;
import com.som.springdata.dao.SimpleJdbcDaoImpl;
import com.som.springdata.model.Circle;
import org.apache.derby.impl.sql.catalog.SYSCOLUMNSRowFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
public class SpringdataApplication {

    public static void main(String[] args) {

        //ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
        ApplicationContext ctx = SpringApplication.run(SpringdataApplication.class, args);
        JdbcDAOImpl jdbcDAO = ctx.getBean(JdbcDAOImpl.class);
        SimpleJdbcDaoImpl simpleJdbcDao = ctx.getBean(SimpleJdbcDaoImpl.class);

        //Circle circle = jdbcDAO.getCircle(1);
        //System.out.println(circle.getName());
        //System.out.println(jdbcDAO.getCircleName(1));
        //Circle circle = jdbcDAO.getCircleForId(1);
        //System.out.println(circle.getId());
        //System.out.println(circle.getName());

        //Circle circle = new Circle(5,"name5");
        //System.out.println(jdbcDAO.insertCircle(circle));
        //System.out.println(jdbcDAO.getAllCircles().size());
        //jdbcDAO.createTriangleTable();

        System.out.println(simpleJdbcDao.getCircleCount());
    }
}
