package com.raithanna.dairy.RaithannaDairy.repositories;

import com.raithanna.dairy.RaithannaDairy.models.vehicle;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VehicleRepository extends CrudRepository<vehicle, Integer> {
    List<vehicle> findByOrderByIdDesc();
}
