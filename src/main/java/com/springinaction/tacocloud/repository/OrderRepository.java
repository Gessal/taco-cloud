package com.springinaction.tacocloud.repository;

import com.springinaction.tacocloud.domain.Order;
import com.springinaction.tacocloud.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findByZip(String zip);
    List<Order> readOrderByZipAndPlacedAtBetween(String zip, Date startDate, Date endDate);
    List<Order> findByStreetIgnoreCaseAndCityIgnoreCase(String street, String city);
    List<Order> findByCityOrderByStreet(String city);
    @Query("select o from Order o where o.city='Seattle'")
    List<Order> readOrdersDeliveredInSeattle();

    List<Order> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);
}
