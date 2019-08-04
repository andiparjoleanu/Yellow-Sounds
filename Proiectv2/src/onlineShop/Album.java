package onlineShop;

import java.util.ArrayList;
import java.util.List;

public class Album implements Comparable<Album>
{
    private String name;
    private final List<Track> playList; 
    private final String coverPath;
    private final double price;
    private int quantity;
    
    public Album(String name, List<Track> playList, String coverPath, 
            double price, int quantity)
    {
        this.name = name;
        this.playList = new ArrayList<>();
        for(int i = 0; i < playList.size(); i ++)
        {
            Track aux = playList.get(i);
            Track t = new Track(aux.getName(), aux.getStartMicroseconds(), 
                                aux.getPath());
            this.playList.add(t);
        }
        this.coverPath = coverPath;
        this.price = price;
        this.quantity = quantity;
    }
    

    public String getName() 
    {
        return name;
    }

    public void setName(String name) 
    {
        this.name = name;
    }
    
    
    
    public List<Track> getPlayList() 
    {
        return playList;
    }

    public String getCoverPath() 
    {
        return coverPath;
    }
    
    public double getPrice() 
    {
        return price;
    }

    public synchronized int getQuantity() 
    {
        return quantity;
    }
    
    public synchronized void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }
    
    @Override
    public int compareTo(Album o) 
    {
        return this.name.compareTo(o.name);
    }
    
}
