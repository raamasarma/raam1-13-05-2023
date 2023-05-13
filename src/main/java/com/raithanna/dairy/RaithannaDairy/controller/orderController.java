
package com.raithanna.dairy.RaithannaDairy.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.raithanna.dairy.RaithannaDairy.models.customer;
import com.raithanna.dairy.RaithannaDairy.models.dailySales;
import com.raithanna.dairy.RaithannaDairy.models.productMaster;
import com.raithanna.dairy.RaithannaDairy.models.saleOrder;
import com.raithanna.dairy.RaithannaDairy.repositories.CustomerRepository;
import com.raithanna.dairy.RaithannaDairy.repositories.DailySalesRepository;
import com.raithanna.dairy.RaithannaDairy.repositories.ProductMasterRepository;
import com.raithanna.dairy.RaithannaDairy.repositories.SaleOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
@Controller
public class orderController {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductMasterRepository productMasterRepository;
    @Autowired
    private DailySalesRepository dailySalesRepository;
    @Autowired
    private SaleOrderRepository saleOrderRepository;
    @RequestMapping("/createOrder")
    public String createOrder_html(Model model, HttpSession session) {
        if (session.getAttribute("loggedIn").equals("yes")) {
            saleOrder saleOrder = saleOrderRepository.findTopByOrderByOrderNoDesc();
            Integer orderNo;
            if (saleOrder == null) {
                orderNo = 1;
            } else {
                orderNo = saleOrder.getOrderNo() + 1;
            }
            // all customer list
            List<customer> Customers = customerRepository.findByOrderByIdDesc();
            List<productMaster> Products = productMasterRepository.findAllByOrderByPCodeAsc();
            System.out.println("total products count --$$$$-----" + Products.size());
          List<productMaster> products1 = productMasterRepository.findAllByOrderByPCodeAsc();
          for (productMaster pm : products1) {
              if (products1.contains(pm.getPCode())) {
              products1.remove(pm);
          }
        }
          System.out.println("total products count after delete --$$$$-----" + products1.size());
            model.addAttribute("customers", Customers);
            model.addAttribute("products", Products);
            model.addAttribute("orderNo", orderNo);
            model.addAttribute("dateTime", LocalDate.now());
            return "createOrder";
        }
        List messages = new ArrayList<>();
        messages.add("Login First");
        model.addAttribute("messages", messages);
        return "redirect:/loginPage";
    }
    // getproductValues Start
    @PostMapping("/getproductValues")
    public ResponseEntity<?> getproductRatesByCustcode(@RequestParam Map<String, String> body, Model model) {
        System.out.println(body);
        System.out.println("-------------------");
        System.out.println(body.get("pcode"));
        System.out.println(body.get("unitRate"));
        System.out.println("-------------------");
        Double d = Double.valueOf(60);
        productMaster product = productMasterRepository.findByPCodeAndUnitRate(body.get("pcode"), Double.parseDouble(body.get("unitRate")));
        System.out.println("db product value- " + product);
        model.addAttribute("products", product);
        Map<String, String> respBody = new HashMap<>();
        respBody.putIfAbsent("specialValue", String.valueOf(product.getUnitRate() - Integer.parseInt(body.get("disc"))));
        respBody.putIfAbsent("commonValue", String.valueOf(product.getUnitRate()));
        respBody.putIfAbsent("amount", String.valueOf((product.getUnitRate() * Integer.parseInt(body.get("qty")))));
        respBody.putIfAbsent("ttlValue", String.valueOf((product.getUnitRate() - Integer.parseInt(body.get("disc"))) * Integer.parseInt(body.get("qty"))));
        return ResponseEntity.ok(respBody);
    }
    //getproductValues End
    @PostMapping("/createOrder")
    public String createOrder(Model model, @ModelAttribute dailySales order, @RequestParam Map<String, String> orderDetails, HttpServletRequest request, HttpSession session) throws InterruptedException {
        List<String> messages = new ArrayList<>();
        System.out.println(orderDetails);
        try {
            // daily sales table save
            order.setOrderNo(Integer.parseInt(orderDetails.get("orderDetails[orderNo]")));
            order.setCustCode(orderDetails.get("orderDetails[custCode]"));
            order.setName(orderDetails.get("orderDetails[Name]"));
            order.setDisc(Double.parseDouble(orderDetails.get("orderDetails[disc]")));
            order.setNetAmount(Double.parseDouble(orderDetails.get("orderDetails[netAmount]")));
            order.setAmount(Double.parseDouble(orderDetails.get("orderDetails[amount]")));
            order.setProdCode(orderDetails.get("orderDetails[prodCode]"));
            order.setQuantity(Double.parseDouble(orderDetails.get("orderDetails[quantity]")));
            order.setUnitRate(Double.parseDouble(orderDetails.get("orderDetails[unitRate]")));
            dailySalesRepository.save(order);
        } catch (Exception handlerException) {
            messages.add("Error Creating the order pls retry");
            model.addAttribute("messages", messages);
            return "/createOrder";
        }
        model.addAttribute("messages", messages);
        return "redirect:/";
    }
    //Sale Order Start
    @PostMapping("/createSaleOrder")
    public String createSaleOrder(@RequestParam String orderNo) {
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + orderNo);
        List<dailySales> orderProducts = dailySalesRepository.findByOrderNo(Integer.parseInt(orderNo));
        while (true) {
            if (orderProducts.isEmpty()) {
                orderProducts = dailySalesRepository.findByOrderNo(Integer.parseInt(orderNo));
            } else {
                break;
            }
        }
        System.out.println(orderProducts.size());
        System.out.println("$$$$$$$$$$$$$$$");
        System.out.println(orderProducts.size());
        System.out.println("$$$$$$$$$$$$$$$");
        double amt = 0;
        double netamt = 0;
        double discamt = 0;
        String custCode = "";
        for (dailySales product : orderProducts) {
            amt = amt + product.getAmount();
            netamt = netamt + product.getNetAmount();
            System.out.println("amt" + amt);
            System.out.println("netamt" + netamt);
        }
        saleOrder saleorder = new saleOrder();
        saleorder.setOrderNo(Integer.parseInt(orderNo));
        saleorder.setAmount(amt);
        saleorder.setNetAmount(netamt);
        saleorder.setDisc(amt - netamt);
        saleorder.setCustCode(custCode);
        // saleorder.setName(name);
        System.out.println(saleorder);
        saleOrderRepository.save(saleorder);
        return "redirect:/";
    }
    //Sale Order End
    //Get Order Start
    @GetMapping("/getOrder")
    public String getOrder(@RequestParam(name = "orderNo", defaultValue = "1") Integer orderNo, Model model) {
        List<dailySales> orderProducts = dailySalesRepository.findByOrderNo(orderNo);
        saleOrder sale_order = saleOrderRepository.findByOrderNo(orderNo);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = sale_order.getDate().format(dateTimeFormatter);
        sale_order.setRemarks(formattedDate);
        NumberFormat formatter = new DecimalFormat("##.00");
        String amount = formatter.format(sale_order.getAmount()).toString();
        System.out.println(amount);
        String disc = formatter.format(sale_order.getDisc()).toString();
        System.out.println(disc);
        String netAmount = formatter.format(sale_order.getNetAmount()).toString();
        System.out.println(netAmount);
        model.addAttribute("orderProducts", orderProducts);
        model.addAttribute("sale_order", sale_order);
        model.addAttribute("one", amount);
        System.out.println("amount:" + amount);
        model.addAttribute("two", disc);
        System.out.println("discount:" + disc);
        model.addAttribute("three", netAmount);
        System.out.println("netAmount:" + netAmount);
        return "orderDisplay";
    }
    //Get Order End
    @RequestMapping("/viewOrder")
    public String viewOrder(Model model, HttpSession session) {
        if (session.getAttribute("loggedIn").equals("yes")) {
            return "viewOrder";
        }
        List messages = new ArrayList<>();
        messages.add("Session Expired");
        model.addAttribute("messages", messages);
        return "redirect:/loginPage";
    }
    //view order start
    @GetMapping("/viewOrder/order/view/{orderNo}")
    public String viewOrderForm(Model model, @PathVariable(name = "orderNo") Integer orderNo, HttpSession session) {
        if (session.getAttribute("loggedIn").equals("yes")) {
            List<customer> Customers = customerRepository.findByOrderByIdDesc();
            // order - product details
            List<dailySales> orderedProducts = dailySalesRepository.findByOrderNo(orderNo);
            if (orderedProducts.size() > 0) {
                System.out.println("total ordered products count --" + orderedProducts.size());
                // all - product details
                List<productMaster> products = productMasterRepository.findAllByOrderByPCodeAsc();
                System.out.println("total products count --" + products.size());
                List<productMaster> products1 = productMasterRepository.findAllByOrderByPCodeAsc();
                // SortedSet<productMaster> ss = new TreeSet<>();
                for (dailySales ds : orderedProducts) {
                    for (productMaster pm : products) {
                        if (ds.getProdCode().equalsIgnoreCase(pm.getPCode())) {
                            // pm.setFlag("disabled");
                            // products1.add(pm);
                            products1.remove(pm);
                        } else {
                            // pm.setFlag("-----");
                            //products1.add(pm);
                        }
                    }
                }
                System.out.println("total products after deletion --" + products1.size());
                saleOrder sales = saleOrderRepository.findByOrderNo(orderNo);
                NumberFormat formatter = new DecimalFormat("##.00");
                String amount = formatter.format(sales.getAmount()).toString();
                String disc = formatter.format(sales.getDisc()).toString();
                String netAmount = formatter.format(sales.getNetAmount()).toString();
                model.addAttribute("one", amount);
                System.out.println("amount:" + amount);
                model.addAttribute("two", disc);
                System.out.println("discount:" + disc);
                model.addAttribute("three", netAmount);
                System.out.println("netAmount:" + netAmount);
                model.addAttribute("products", products1);
                model.addAttribute("orders", orderedProducts);
                model.addAttribute("sales", sales);
                return "view_order";
            } else {
                model.addAttribute("msg", "requested order not exists");
                return "viewOrder";
            }
        }
        List messages = new ArrayList<>();
        messages.add("Login First");
        model.addAttribute("messages", messages);
        return "redirect:/loginPage";
    }
    //view order End
    //update order start
    @PostMapping(value = "/updateOrder")
    public ModelAndView updateOrder(Model model, @RequestBody List<dailySales> orderList, HttpSession session) {
        if (session.getAttribute("loggedIn").equals("yes")) {
            System.out.println("log info -- dailySalesList " + orderList.size());
            for (dailySales list : orderList) {
                System.out.println(list.getQuantity() + " --- " + list.getDisc() + " --- " + list.getAmount() + " --- " + list.getNetAmount() + " --- " + list.getId() + "---" + list.getTotDisc() + "---" + list.getTotAmount() + "---" + list.getTotNetAmount());
                dailySales ds = dailySalesRepository.findByid(list.getId());
                System.out.println("hello");
                ds.setQuantity(list.getQuantity());
                ds.setDisc(list.getDisc());
                ds.setAmount(list.getAmount());
                ds.setNetAmount(list.getNetAmount());
                ds.setTotDisc(list.getTotDisc());
                ds.setTotAmount(list.getTotAmount());
                ds.setTotNetAmount(list.getTotNetAmount());
                ds.setDate(list.getDate());
                ds.setProdCode(list.getProdCode());
                ds.setUnitRate(list.getUnitRate());
                dailySalesRepository.save(ds);
            }
            saleOrder so = saleOrderRepository.findByOrderNo(orderList.get(0).getOrderNo());
            so.setDisc(orderList.get(0).getTotDisc());
            so.setAmount(orderList.get(0).getTotAmount());
            so.setNetAmount(orderList.get(0).getTotNetAmount());
            saleOrderRepository.save(so);
            return new ModelAndView("/loginPage");
        }
        List messages = new ArrayList<>();
        messages.add("Login First");
        model.addAttribute("messages", messages);
        //return "redirect:/loginPage";
        return new ModelAndView("/loginPage");
    }
    //update order End
    @PostMapping("/checkOrder")
    @ResponseBody
    public String checkOrder(Model model, HttpSession session, HttpServletRequest request) throws JsonProcessingException {
        String orderNo = request.getParameter("orderNo1");
        System.out.println("order Number ---- $$$ ---" + orderNo);
        List<dailySales> orderedProducts = dailySalesRepository.findByOrderNo(Integer.parseInt(orderNo));
        String result = "";
        if (orderedProducts.size() > 0) {
            result = "true";
        } else {
            // model.addAttribute("msg", "order not exist");
            result = "false";
        }
        ObjectMapper mapper = new ObjectMapper();
        String response = mapper.writeValueAsString(result);
        //return new ModelAndView("/viewOrder");
        return response;
    }
    @PostMapping(value = "/createOrderNew")
    public ModelAndView createOrderNew(Model model, @RequestBody List<dailySales> orderList, HttpSession session) {
        if (session.getAttribute("loggedIn").equals("yes")) {
            System.out.println("log info -- dailySalesList " + orderList.size());
            Iterable<customer> CustomersIterable = customerRepository.findAll();
            List<customer> Customers = new ArrayList<>();
            for (customer Customer : CustomersIterable) {
                Customers.add(Customer);
            }
            model.addAttribute("customers", Customers);
            Double totalAmount = 0.0, totalNetAmount = 0.0;
            for (dailySales list : orderList) {
                dailySales ds = new dailySales();
                ds.setAmount(list.getAmount());
                ds.setNetAmount(list.getNetAmount());
                totalAmount = totalAmount + list.getAmount();
                totalNetAmount = totalNetAmount + list.getNetAmount();
            }
            for (dailySales list : orderList) {
                dailySales ds = new dailySales();
                ds.setProdCode(list.getProdCode());
                ds.setName(list.getName());
                ds.setCustCode(list.getCustCode());
                ds.setName(list.getName());
                ds.setUnitRate(list.getUnitRate());
                ds.setQuantity(list.getQuantity());
                ds.setDisc(list.getDisc());
                ds.setAmount(list.getAmount());
                ds.setNetAmount(list.getNetAmount());
                ds.setOrderNo(list.getOrderNo());
                ds.setTotAmount(totalAmount);
                ds.setTotNetAmount(totalNetAmount);
                ds.setTotDisc(totalAmount - totalNetAmount);
                ds.setDate(list.getDate());
                System.out.println(list.getDate());
                dailySalesRepository.save(ds);
            }
            System.out.println("log info -- dailySalesList " + totalAmount);
            System.out.println("log info -- dailySalesList " + totalNetAmount);
            saleOrder so = new saleOrder();
            //so.setCustCode(orderList.get(0).getCustCode());
            so.setCustCode(orderList.get(0).getCustCode());
            so.setOrderNo(orderList.get(0).getOrderNo());
            Double d = totalAmount - totalNetAmount;
            so.setDisc(d);
            so.setAmount(totalAmount);
            so.setNetAmount(totalNetAmount);
            so.setDate(orderList.get(0).getDate());
            so.setName(orderList.get(0).getName());
            saleOrderRepository.save(so);
            return new ModelAndView("/loginPage");
        }
        List messages = new ArrayList<>();
        messages.add("Login First");
        model.addAttribute("messages", messages);
        return new ModelAndView("/loginPage");
    }
    @GetMapping("/orders/{id}")
    public String deleteOrder(@PathVariable Integer id, Model model, HttpSession session) {
        if (session.getAttribute("loggedIn").equals("yes")) {
            dailySalesRepository.deleteById(id);
            return "redirect:/";
        }
        List messages = new ArrayList<>();
        messages.add("Login First");
        model.addAttribute("messages", messages);
        return "redirect:/loginPage";
    }

}