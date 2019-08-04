package onlineShop;

public class Administrator 
{
     private final String name;
     private final String username;
     private final String password;
     private final String responsabilities;
     private final Buffer buffer;

    public Administrator(String name, String username, String password, 
                         String responsabilities) 
    {
        this.name = name;
        this.username = username;
        this.password = password;
        this.responsabilities = responsabilities;
        this.buffer = Buffer.getInstance();
    }

    public String getName() 
    {
        return name;
    }

    public String getUsername() 
    {
        return username;
    }

    public String getPassword() 
    {
        return password;
    }

    public String getResponsabilities() 
    {
        return responsabilities;
    } 
    
    public void addAlbum(Album a) throws InterruptedException
    {
        buffer.addAlbum(a);
    }
     
    public void sellAlbum(Album a)
    {
        buffer.sellAlbum(a);
    }
}
