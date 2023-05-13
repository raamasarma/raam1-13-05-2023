package com.raithanna.dairy.RaithannaDairy.repositories;
import com.azure.spring.data.cosmos.repository.Query;
import com.raithanna.dairy.RaithannaDairy.models.userLog;
import com.raithanna.dairy.RaithannaDairy.models.userModel;
import org.springframework.data.repository.CrudRepository;

public interface  UserLogRepository extends CrudRepository<userLog, Integer> {
    @Query("select * from user_model where mobile=?1")
    userModel findByMobile(String mobile);
}
