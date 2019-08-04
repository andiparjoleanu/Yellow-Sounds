
package onlineShop;

import java.util.ArrayList;
import java.util.List;

public class OrdersBuffer 
{
    private final List<Order> ordersList;
    private static OrdersBuffer BUFFER;
    
    private OrdersBuffer()
    {
        ordersList = new ArrayList<>();
    }
    
    public static synchronized OrdersBuffer getInstance()
    {
        if(BUFFER == null)
            BUFFER = new OrdersBuffer();
            
        return BUFFER;
    }
    
    public List<Order> getOrdersList()
    {
        return ordersList;
    }
    
    public synchronized void addOrder(Order a)
    {
        ordersList.add(a);
    }
}
