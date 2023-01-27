package ibf.app.ssf.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.UUID;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Order implements Serializable{

  //validate attributes
  private String orderId;
  
  @NotNull(message = "Name is mandatory")
  @Size(min = 3, message = "Name must be minimum 3 characters")
  private String name;

  @NotEmpty(message = "Address is mandatory")
  private String address;

  @Size(min=8, max=8, message = "Phone number must be 8 digits")
  private String phone;

  private Boolean rush = false;

  private String comments;

  @NotNull(message = "must select one of the 5 types of pizza: bella, margherita, marinara, spianatacalabrese or trioformaggio")
  private String pizza;

  @NotNull(message = "must select a pizza size: sm, md or lg")
  private String size;

  @Min(value = 1, message = "must order at least 1 pizza")
  @Max(value = 10, message = "can only order maximum 10 pizzas")
  private int quantity;
  private double total;
  private double pizzaCost;

  //getters & setters
  public String getOrderId() {
    return orderId;
  }
  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getAddress() {
    return address;
  }
  public void setAddress(String address) {
    this.address = address;
  }
  public String getPhone() {
    return phone;
  }
  public void setPhone(String phone) {
    this.phone = phone;
  }
  public Boolean getRush() {
    return rush;
  }
  public void setRush(Boolean rush) {
    this.rush = rush;
  }
  public String getComments() {
    return comments;
  }
  public void setComments(String comments) {
    this.comments = comments;
  }
  public String getPizza() {
    return pizza;
  }
  public void setPizza(String pizza) {
    this.pizza = pizza;
  }
  public String getSize() {
    return size;
  }
  public void setSize(String size) {
    this.size = size;
  }
  public int getQuantity() {
    return quantity;
  }
  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
  public double getTotal() {
    return total;
  }
  public void setTotal(double total) {
    this.total = total;
  }
  public double getPizzaCost() {
    return pizzaCost;
  }
  public void setPizzaCost(double pizzaCost) {
    this.pizzaCost = pizzaCost;
  } 

  //constructors
  public Order(){
    this.orderId = generateId(8);
  }
  
  public Order(String pizza, String size, int quantity){
    this.orderId = generateId(8);
    this.pizza = pizza;
    this.size=size;
    this.quantity = quantity;
    this.pizzaCost = calculatePizzaCost(pizza, size, quantity);
  }
  public Order(String orderId, String name, String address, String phone, Boolean rush, String comments, String pizza,
      String size, int quantity, int total) {
    this.orderId = orderId;
    this.name = name;
    this.address = address;
    this.phone = phone;
    this.rush = rush;
    this.comments = comments;
    this.pizza = pizza;
    this.size = size;
    this.quantity = quantity;
    this.pizzaCost = calculatePizzaCost(pizza, size, quantity);
    if(rush==true){
      total=total+2;
    } else this.total = total;
  }

  //generate random 8 digit ids using UUID jdk class
  public String generateId(int numChars){
    UUID id = UUID.randomUUID();
    String idString = id.toString().substring(0,numChars);
    return this.orderId=idString;
    
  }

  //calculate pizza cost by multiplying quantity of pizza with multiplier (size) and type of pizza selected
  public double calculatePizzaCost(String pizza, String size, Integer quantity){

    double costOfPizza = 0;
    double totalCostOfPizza = 0;
    // double totalCost;
    double multiplier = 0;
    if (pizza.equals("bella") || pizza.equals("marinara")|| pizza.equals("spianatacalabrese")){
      costOfPizza = 30;
    } else if (pizza.equals("margherita")){
      costOfPizza = 22;
    } else if (pizza.equals("trioformaggio")){
      costOfPizza = 25;
    }

    if(size.equals("sm")){
      multiplier = 1;
    } else if (size.equals("md")){
      multiplier = 1.2;
    } else if (size.equals("lg")){
      multiplier = 1.5;
    }

    totalCostOfPizza = costOfPizza * multiplier * quantity;

    // if (rush==true){
    //   totalCost = totalCostOfPizza + 2;
    // } else totalCost = totalCostOfPizza;

    return Double.parseDouble(String.format("%.2f", totalCostOfPizza));
  }


  //create json object and set the order object attributes
  public static Order createJson(String json) throws IOException{
      Order o = new Order();
      try(InputStream is = new ByteArrayInputStream(json.getBytes())){
        JsonReader r = Json.createReader(is);
        JsonObject obj = r.readObject();

        o.setOrderId(obj.getString("orderId"));
        o.setName(obj.getString("name"));
        o.setAddress(obj.getString("address"));
        o.setPhone(obj.getString("phone"));
        o.setRush(obj.getBoolean("rush"));
        o.setComments(obj.getString("comments"));
        o.setPizza(obj.getString("pizza"));
        o.setSize(obj.getString("size"));
        o.setQuantity(Integer.parseInt(obj.getString("quantity")));
        o.setTotal(Double.parseDouble(obj.getString("total")));

        System.out.println("======= Order ID: "+ o.getOrderId());
        System.out.println("======= Name: "+ o.getName());
        System.out.println("======= Address: "+ o.getAddress());
        System.out.println("======= Phone: "+ o.getPhone());
        System.out.println("======= Rush: "+ o.getRush());
        System.out.println("======= Comments: "+ o.getComments());
        System.out.println("======= Pizza: "+ o.getPizza());
        System.out.println("======= Size: "+ o.getSize());
        System.out.println("======= Quantity: "+ o.getQuantity());
        System.out.println("======= Total: "+ o.getTotal());

      }
      return o;


    }

    //create method toJson
    public JsonObject toJSON(){
      return Json.createObjectBuilder()
        .add("orderId", this.getOrderId())
        .add("name", this.getName())
        .add("address", this.getAddress())
        .add("phone", this.getPhone())
        .add("rush", this.getRush())
        .add("comments", this.getComments())
        .add("pizza", this.getPizza())
        .add("size", this.getSize())
        .add("quantity", this.getQuantity())
        .add("total", this.getTotal())
        .build();
    }

  

  
}
