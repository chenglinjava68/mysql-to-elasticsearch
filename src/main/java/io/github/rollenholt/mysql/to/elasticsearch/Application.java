package io.github.rollenholt.mysql.to.elasticsearch;

import com.google.common.base.Throwables;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.beans.PropertyVetoException;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * @author rollenholt
 */
@SpringBootApplication
@PropertySource({"classpath:config.properties"})
public class Application {

    @Value("${jdbcDriver}")
    private String jdbcDriver;

    @Value("${jdbcUrl}")
    private String jdbcUrl;

    @Value("${jdbcUsername}")
    private String jdbcUsername;

    @Value("${jdbcPassword}")
    private String jdbcPassword;

    @Bean(name="jdbcTemplate")
    public JdbcTemplate jdbcTemplate(){
        checkArgument(!isNullOrEmpty(jdbcDriver));
        checkArgument(!isNullOrEmpty(jdbcUrl));
        checkArgument(!isNullOrEmpty(jdbcUsername));
        checkArgument(!isNullOrEmpty(jdbcPassword));

        ComboPooledDataSource datasource = new ComboPooledDataSource();
        try {
            datasource.setDriverClass(jdbcDriver);
        } catch (PropertyVetoException e) {
            throw Throwables.propagate(e);
        }
        datasource.setJdbcUrl(jdbcUrl);
        datasource.setUser(jdbcUsername);
        datasource.setPassword(jdbcPassword);
        datasource.setInitialPoolSize(5);
        datasource.setMaxPoolSize(10);
        datasource.setAutoCommitOnClose(true);
        return new JdbcTemplate(datasource);
    }


    public static void main(String[] args) {
        Object[] objects = {Application.class};
        SpringApplication.run(objects, args);
    }
}
