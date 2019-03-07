package cn.varfunc.restaurant.domain.repository;

import cn.varfunc.restaurant.domain.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Customer Repository
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
