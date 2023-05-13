package com.raithanna.dairy.RaithannaDairy.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.text.ParseException;
import java.util.Map;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data
public class purchaseOrder {
    @Id
    @GeneratedValue
    private Integer id;
   //private Integer slNo;
    private String invDate;
   private Integer orderNo;/// found redundant - need to remove- as this is part of invno
    private String invNo;//VA-120523-001/01 S1-260423-001/2
    private String supplier;//need to remove
    private String suplCode;//supplier.suppliercode
    private String vehNumber;//vehicle.vehicleNo
    private String milkType;//milktype.code
    private Double quantity;//as per unit
    private Double fatP;//decimal 2
    private Double snfP;//decimal 2
    private Double tsRate;//decimal 2
    private Double ltrRate;//Unit Rate <Ltr rate or KG Rate - depends on unit>private Double amt;// Item wise Total
    private Double amt;
    private String InvType;//Purchase Invoice or Tax(Sale) Invoice
    private String Tmode;// Road or Others
    private String Unit;// KG / Ltr
    private String PoRefNo;// NA
    private String PoRefDate;//NA
    private Double TotAmtRound;

    private String paymentStatus;
    private String bankIfsc;
    private String bankrefno;
    private String recDate;

    private Double recAmount;



    public String[] getListValues(){
        String values[] = {this.invDate.toString(), this.invNo,this.supplier, this.vehNumber, this.milkType,this.quantity.toString(), this.fatP.toString(), this.snfP.toString(), this.tsRate.toString(), this.ltrRate.toString(), this.paymentStatus, this.bankIfsc,this.bankrefno,this.recDate.toString()};
        return values;
    }

   public void mapToVariables(@NotNull Map purchase) throws ParseException {
       this.supplier = purchase.get("supplier").toString();
   }

}
