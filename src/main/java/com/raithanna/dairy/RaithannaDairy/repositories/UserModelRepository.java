package com.raithanna.dairy.RaithannaDairy.repositories;

import com.azure.spring.data.cosmos.repository.Query;
import com.raithanna.dairy.RaithannaDairy.models.userModel;
import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public interface UserModelRepository extends CrudRepository<userModel, Integer> {
    @Query("select * from user_model where loginID=?1 and password=?2 ")
    userModel findByLoginIDAndPassword(String loginId, String password);

}
