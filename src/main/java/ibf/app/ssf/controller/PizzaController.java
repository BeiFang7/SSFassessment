package ibf.app.ssf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import ibf.app.ssf.model.Order;
import ibf.app.ssf.service.OrderRedis;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
public class PizzaController {

  @Autowired
  private OrderRedis orderRedisSvc; 


  @GetMapping(path="/")
  public String orderForm(Model model){
    System.out.println("======= inside GetMapping / =======");
    model.addAttribute("order", new Order());
    return "index";
  }

  @PostMapping(path = "/pizza", consumes = "application/x-www-form-urlencoded", produces="text/html")
  public String saveOrder (@Valid Order order, BindingResult binding, Model model, HttpServletResponse response){
    System.out.println("======= inside PostMapping /pizza =======");
    System.out.println(binding.hasErrors());
    System.out.println("======= order: "+order);
    if (binding.hasErrors()){
      return "index";
    }
    
    orderRedisSvc.save(order);
    model.addAttribute("order",order);
    response.setStatus(HttpServletResponse.SC_CREATED);
    return "view1";

  }

  @GetMapping(path="/pizza/order")
  public String getOrder(Model model){
    System.out.println("======= inside getmapping /pizza/order ======= ");
    System.out.println("======= order: ");
    model.addAttribute("order", new Order());
    return "view1";
  }

  @PostMapping(path="/pizza/order", consumes = "application/x-www-form-urlencoded", produces="text/html")
  public String createOrder (@Valid Order order, BindingResult binding, Model model, HttpServletResponse response){
    System.out.println("======= inside postmapping /pizza/order =======");
    System.out.println(binding.hasErrors());
    
    if (binding.hasErrors()){
      return "view1";
    }
    System.out.println("======= order: "+order);
    orderRedisSvc.save(order);
    model.addAttribute("order",order);
    response.setStatus(HttpServletResponse.SC_CREATED);
    return "view2";
  }

  // @GetMapping(path="/pizza/order/{orderId}")
  // public String getOrderInfoById(Model model, @PathVariable(value="orderId") String orderId){
  //   Order o = orderRedisSvc.findById(orderId);
  //   o.setOrderId(orderId);
  //   model.addAttribute("order", o);
  //   return "view2";
    
  // }


}

