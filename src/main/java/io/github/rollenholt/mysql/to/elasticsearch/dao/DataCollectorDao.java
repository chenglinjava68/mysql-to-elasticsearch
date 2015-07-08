package io.github.rollenholt.mysql.to.elasticsearch.dao;

import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author rollenholt
 */
@Repository
public class DataCollectorDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    private final Logger logger = LoggerFactory.getLogger(DataCollectorDao.class);

    public <T> Stream<T> queryAll(String sql, RowMapper<T> rowMapper) {
        try {
            final List<T> list = jdbcTemplate.query(sql, rowMapper);
            return list.stream();
        } catch (DataAccessException e) {
            logger.error("an error happend when queryAll: {}", e.getMessage(), e);
            throw Throwables.propagate(e);
        }
    }
}
