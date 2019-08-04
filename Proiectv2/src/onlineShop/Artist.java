package onlineShop;

import java.awt.Color;
import java.util.List;
import java.util.Objects;

public class Artist 
{
     final private String name;
     final private String info;
     final private String profilePhotoPath;
     final private String iconPhotoPath;
     final private List<Album> albumList;   //as prefera un artist - un album si incep sa cred ca editArtist nu e atat de necesar
     final private String genre;

     public Artist(String name, String info, String profilePhotoPath, 
                  String iconPhotoPath,
                  List<Album> album, String genre) 
    {
        this.name = name;
        this.info = info;
        this.profilePhotoPath = profilePhotoPath;
        this.albumList = album;
        this.genre = genre;
        this.iconPhotoPath = iconPhotoPath;
    }

    public String getName() 
    {
        return name;
    }

    public String getInfo() 
    {
        return info;
    }

    public String getProfilePhotoPath() 
    {
        return profilePhotoPath;
    }

    public String getIconPhotoPath() 
    {
        return iconPhotoPath;
    }

    public List<Album> getAlbumList() 
    {
        return albumList;
    }

    public String getGenre() 
    {
        return genre;
    }

    @Override
    public String toString() 
    {
       String firstPart = name + ": " + info + "; (";
       StringBuffer secondPart = new StringBuffer();
       albumList.forEach(s -> secondPart.append(s).append(" ;"));
       String lastPart = ") ";
       return firstPart + secondPart.toString() + lastPart;
    }

    @Override
    public int hashCode() 
    {
        int sum = 0;
        for(Album s : albumList)
            sum += s.hashCode();
        
        return Objects.hashCode(name) + Objects.hashCode(info)
                + Objects.hashCode(profilePhotoPath)
                + sum;
    }

    @Override
    public boolean equals(Object obj) 
    {
        if (this == obj)
            return true;
        
        if (obj == null) 
            return false;
        
        if (this.getClass() != obj.getClass())
            return false;
        
        final Artist other = (Artist) obj;
        
        if (! name.equals(other.name)) 
            return false;
        
        if (! info.equals(other.info)) 
            return false;
        
        if (! profilePhotoPath.equals(profilePhotoPath)) 
            return false;
        
        if(albumList.size() == other.albumList.size())
        {
            for(int i = 0; i < albumList.size(); i ++)
            {
                if(! albumList.get(i).equals(other.albumList.get(i)))
                   return false;
            }
            
        }
        else return false;   
        
        return true;
    }   
}
