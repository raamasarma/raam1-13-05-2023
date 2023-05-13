package com.raithanna.dairy.RaithannaDairy.ServiceImpl.Utility;

import com.raithanna.dairy.RaithannaDairy.models.DownloadSuperBean;
import com.raithanna.dairy.RaithannaDairy.models.purchaseOrder;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class DownloadCsvReport {

    public static void getCsvReportDownload(HttpServletResponse response, String[] header, List<purchaseOrder> list, String fileName){

        try{
            System.out.println("in csv report helper class");

            response.setContentType("text/csv");
            String headerKey ="Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", fileName);
            response.setHeader(headerKey,headerValue);


            ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
            csvWriter.writeHeader(header);

            for(purchaseOrder superList : list){
                csvWriter.write(superList, header);
            }

            csvWriter.close();

        }catch(Exception e){

        }

    }
}
