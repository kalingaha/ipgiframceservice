package lk.mobitel;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class McashApiApplication extends SpringBootServletInitializer {

    @Autowired
    DataSource dataSource;

    public static void main(String[] args) {
        SpringApplication.run(McashApiApplication.class, args);
    }

}
