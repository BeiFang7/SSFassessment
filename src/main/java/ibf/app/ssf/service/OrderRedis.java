package ibf.app.ssf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import ibf.app.ssf.model.Order;

@Service
public class OrderRedis {
  private static final String ORDER_ENTITY ="order";

  @Autowired
  RedisTemplate<String, Object> redisTemplate;

  public void save(final Order order){
    redisTemplate.opsForValue().set(order.getOrderId(),order.toJSON().toString());
  }

  public Order findById(final String orderId){
    Order result = (Order) redisTemplate.opsForHash().get(ORDER_ENTITY+"_Map",orderId);
    return result;
  }
  
}
