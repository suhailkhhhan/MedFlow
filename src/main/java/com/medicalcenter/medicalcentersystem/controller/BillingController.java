package com.medicalcenter.medicalcentersystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.medicalcenter.medicalcentersystem.dao.BillRepository;
import com.medicalcenter.medicalcentersystem.model.Bill;
import com.medicalcenter.medicalcentersystem.service.BillingService;


import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;



import java.util.List;

@Controller
public class BillingController {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BillingService billingService;

    @GetMapping("/bills-list")
    public ModelAndView showBillsList() {
        ModelAndView mav = new ModelAndView("bills-list");
        List<Bill> bills = billRepository.findAll();
        mav.addObject("bills", bills);
        return mav;
    }

    @GetMapping("/generate-bill/{appointmentId}")
    public String generateBill(@PathVariable int appointmentId) {
        billingService.generateBillForAppointment(appointmentId);
        return "redirect:/bills-list";
    }


    // Inject the keys from application.properties
    @Value("${razorpay.key.id}")
    private String razorpayKeyId;

    @Value("${razorpay.key.secret}")
    private String razorpayKeySecret;

    // This is the new method to create a payment order
    @PostMapping("/create-order")
    @ResponseBody // This tells Spring to return the data as JSON
    public String createOrder(@RequestBody java.util.Map<String, Object> data) throws RazorpayException {
        int amount = Double.valueOf(data.get("amount").toString()).intValue();

        RazorpayClient razorpayClient = new RazorpayClient(razorpayKeyId, razorpayKeySecret);

        JSONObject orderRequest = new JSONObject();
        // Amount must be in the smallest currency unit (e.g., paise for INR)
        orderRequest.put("amount", amount * 100);
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", "txn_12345");

        // Create the order
        Order order = razorpayClient.orders.create(orderRequest);

        // Return the order details as a JSON string
        return order.toString();
    }
}