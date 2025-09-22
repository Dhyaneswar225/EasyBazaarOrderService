package com.dazzledepot.order_service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "orders")
public class Order {
    @Id
    private String id;
    private String userId;
    private List<CartItem> items;
    private double total;
    private String address;
    private String status;
    private List<TrackingUpdate> trackingUpdates;

    public Order() {
        this.items = new ArrayList<>();
        this.trackingUpdates = new ArrayList<>();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    @JsonProperty("items")
    public List<CartItem> getItems() { return items; }
    public void setItems(List<CartItem> items) { this.items = items; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    @JsonProperty("trackingUpdates")
    public List<TrackingUpdate> getTrackingUpdates() { return trackingUpdates; }
    public void setTrackingUpdates(List<TrackingUpdate> trackingUpdates) { this.trackingUpdates = trackingUpdates; }

    public static class CartItem {
        private String productId;
        private int quantity;
        private double price;

        public String getProductId() { return productId; }
        public void setProductId(String productId) { this.productId = productId; }
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
        public double getPrice() { return price; }
        public void setPrice(double price) { this.price = price; }
    }

    public static class TrackingUpdate {
        private String status;
        private String timestamp;
        private String location;
        // Default constructor (needed for frameworks like Jackson)
        public TrackingUpdate() {}

        // Parameterized constructor (for convenience)
        public TrackingUpdate(String status, String timestamp, String location) {
            this.status = status;
            this.timestamp = timestamp;
            this.location = location;
        }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public String getTimestamp() { return timestamp; }
        public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
        public String getLocation() { return location; }
        public void setLocation(String location) { this.location = location; }
    }
}
