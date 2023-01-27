package ibf.app.ssf.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ibf.app.ssf.model.Order;
import ibf.app.ssf.service.OrderRedis;

@RestController
@RequestMapping(path="/pizza/order", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
public class PizzaRestController {
  @Autowired
  private OrderRedis orderRedisSvc;

  @GetMapping(path="{orderId}")
  public ResponseEntity<String> getOrder(@PathVariable String orderId) throws IOException{
    Order result = orderRedisSvc.findById(orderId);
    if(result==null){
      return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .contentType(MediaType.APPLICATION_JSON)
        .body("");
    }
    return ResponseEntity
      .status(HttpStatus.OK)
      .contentType(MediaType.APPLICATION_JSON)
      .body(result.toJSON().toString());
    
    
  }
}
