package learn.scheduler.data.mapper;
import learn.scheduler.model.Services;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class ServiceMapper implements RowMapper<Services> {

    @Override
    public Services mapRow(ResultSet resultSet, int i) throws SQLException {

        Services service = new Services();
        service.setServiceId(resultSet.getInt("service_id"));
        service.setServiceName(resultSet.getString("service_name"));
        service.setServiceDescription(resultSet.getString("service_description"));
        service.setDuration(resultSet.getInt("duration"));
        service.setPrice(resultSet.getBigDecimal("price"));
    
        return service;
    }
}