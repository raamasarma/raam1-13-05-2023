package com.raithanna.dairy.RaithannaDairy.repositories;

import com.raithanna.dairy.RaithannaDairy.models.bank;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BankRepository extends CrudRepository<bank,Integer> {
    List<bank> findByOrderByIdDesc();
}
