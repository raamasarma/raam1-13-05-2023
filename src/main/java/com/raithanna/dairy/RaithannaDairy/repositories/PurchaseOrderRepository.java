package com.raithanna.dairy.RaithannaDairy.repositories;

import com.azure.spring.data.cosmos.repository.Query;
import com.raithanna.dairy.RaithannaDairy.models.customer;
import com.raithanna.dairy.RaithannaDairy.models.purchaseOrder;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface PurchaseOrderRepository extends CrudRepository<purchaseOrder,Integer> {
    List<purchaseOrder> findByInvDate(String date);
    List<purchaseOrder> findByInvDateAndSuplCode(String date, String suplCOde);

    @Query("select * from  purchase_order where supl_code=?1 and  inv_date between ?2 and ?3")
    List<purchaseOrder> findBySuplCodeAndInvDateBetween(String suplCOde, String invDatefrom, String invDateto);
    @Query("select * from  purchase_order where inv_date between ?1 and ?2")
    List<purchaseOrder> findByInvDateBetween( String invDatefrom, String invDateto);
    purchaseOrder findByInvNo(String invNo);
    @Query("select * from  purchase_order where suplCode=?1 and inv_date ?2")
    List<purchaseOrder> findBySuplCodeAndInvDate(String suplCode, String date);

    // List<purchaseOrder> findByOrderBySlNo();
    // List<purchaseOrder> findByOrderByAmtDesc();
    //   @Query("select count(*) from  purchase_order where supplier=?1 and recDate=?2")
//   Integer findBySupplierAndRecDate(String supplier,LocalDate recDate);
    @Query("select count(*) from  purchase_order where supplier=?1 and invDate=?2")
    Integer countBySupplierAndInvDate(String supplier, LocalDate invDate);

//   @Query("select * from  purchase_order where supplier=?1 and recDate=?2")
//   purchaseOrder findBySupplierAndRecDate(String supplier,LocalDate recDate);


    @Query("select * from  purchase_order where supplier=?1 and invDate=?2")
    List<purchaseOrder> findBySupplierAndInvDate(String supplier,LocalDate recDate);


    //Integer countDistinctSupplierByInvDate(LocalDate invDate);
    // List<purchaseOrder> findByInvDate(LocalDate recDate);


    @Query("select * from  purchase_order where suplCode=?1 ")
    List<purchaseOrder> findBySuplCode(String suplCode);

    purchaseOrder findTopByOrderByOrderNoDesc();
    List<purchaseOrder>findByOrderNo(Integer orderNo);

}
