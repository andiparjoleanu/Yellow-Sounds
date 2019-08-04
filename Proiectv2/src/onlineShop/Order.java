package onlineShop;
import java.util.ArrayList;
import java.util.List;

public class Order 
{
     static public long NUMBER_OF_ORDERS;
     private final long orderIndex;
     private final Customer customer;
     private final List<Album> shoppingList;
     
     static
     {
         NUMBER_OF_ORDERS = 0;
     }

    public Order(Customer customer, List<Album> shoppingList) 
    {
        NUMBER_OF_ORDERS += 1;
        this.orderIndex = NUMBER_OF_ORDERS;
        this.customer = customer;
        this.shoppingList = new ArrayList<>();
        for(int i = 0; i < shoppingList.size(); i ++)
        {
            List<Track> trackList = new ArrayList<>();
            for(Track t : shoppingList.get(i).getPlayList())
            {
                trackList.add(new Track(t.getName(), t.getStartMicroseconds(), 
                        t.getPath()));
            }
            this.shoppingList.add(new Album(shoppingList.get(i).getName(), 
                                    trackList, shoppingList.get(i).getCoverPath(), 
                                    shoppingList.get(i).getPrice(), 
                                    shoppingList.get(i).getQuantity()));
        }
    }
   

    public long getOrderIndex() 
    {
        return orderIndex;
    }

    public Customer getCustomer() 
    {
        return customer;
    }

    public List<Album> getShoppingList() 
    {
        return shoppingList;
    } 
     
}
