package com.iiitb.customebook.repository;

import com.iiitb.customebook.bean.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Invoice,Integer> {
}
