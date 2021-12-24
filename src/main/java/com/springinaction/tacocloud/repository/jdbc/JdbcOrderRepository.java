package com.springinaction.tacocloud.repository.jdbc;

import com.springinaction.tacocloud.domain.Order;

public interface JdbcOrderRepository {
    Order save (Order order);
}
