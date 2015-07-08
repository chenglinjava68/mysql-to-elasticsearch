package io.github.rollenholt.mysql.to.elasticsearch.service;

import io.github.rollenholt.mysql.to.elasticsearch.dao.DataCollectorDao;
import io.github.rollenholt.mysql.to.elasticsearch.model.BasePayload;
import org.springframework.jdbc.core.RowMapper;

import javax.annotation.Resource;
import java.util.stream.Stream;

/**
 * @author rollenholt
 */
@Resource
public abstract class AbstractDataCollector {

    @Resource
    protected DataCollectorDao dataCollectorDao;

    public Stream<BasePayload> queryAll() {
        return dataCollectorDao.queryAll(fetchSql(), fetchRowMapper());
    }

    protected abstract String fetchSql();

    protected abstract <T extends BasePayload> RowMapper<T> fetchRowMapper();
}
