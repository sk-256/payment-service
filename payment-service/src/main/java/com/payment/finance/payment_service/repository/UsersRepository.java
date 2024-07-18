package com.payment.finance.payment_service.repository;

import com.payment.finance.payment_service.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface UsersRepository extends JpaRepository<Users,Integer>{

	   Optional<Users>  findByUserName(String userName);

	  Optional<Users>  findByEmailId(String emailId);
}
