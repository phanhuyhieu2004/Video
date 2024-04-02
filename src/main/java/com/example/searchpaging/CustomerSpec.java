package com.example.searchpaging;



import com.example.searchpaging.model.Customer;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
//Interface này là một phần của Spring Data JPA và được sử dụng để xây dựng các điều kiện tìm kiếm động cho các truy vấn JPA.
public class CustomerSpec implements Specification<Customer> {

    private CustomerRequest customerRequest;
//    Đây là khai báo của biến customerRequest để lưu trữ yêu cầu tìm kiếm của khách hàng.
    public CustomerSpec(CustomerRequest customerRequest) {
        this.customerRequest = customerRequest;
    }

    @Override
    public Predicate toPredicate(Root<Customer> root,
                                 CriteriaQuery<?> query,
                                 CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates = new ArrayList<>();

        if (customerRequest.getFirstName() != null) {
            predicates.add(criteriaBuilder.like(root.get("firstName"),
                    "%" + customerRequest.getFirstName() + "%"));
        }

        if (customerRequest.getLastName() != null) {
            predicates.add(criteriaBuilder.like(root.get("lastName"),
                    "%" + customerRequest.getLastName() + "%"));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
