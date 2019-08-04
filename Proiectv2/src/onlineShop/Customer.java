package onlineShop;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Customer 
{
    public class Card
    {
        private final String bank;
        private final String number;
        private final Date expirationDate;
        private final String type;
        private final String securityCode;

        public Card(String bank, String number, Date expirationDate, 
                    String type, String securityCode) 
        {
            this.bank = bank;
            this.number = number;
            this.expirationDate = expirationDate;
            this.type = type;
            this.securityCode = securityCode;
        }
        
        public String getBank() 
        {
            return bank;
        }

        public String getNumber() 
        {
            return number;
        }

        public Date getExpirationDate() 
        {
            return expirationDate;
        }

        public String getType() 
        {
            return type;
        }
        
        public String getSecurityCode() 
        {
            return securityCode;
        }
        
        @Override
        public String toString()
        {
            return bank + ": " + number + ", " + expirationDate.toString() 
                    + ", " + type + ", " + securityCode;
        }
    }
    
    private final String username;
    private String password;
    private final String name;
    private String telephone;
    private String address;
    private Card card;

    public Customer(String username, String password, String name, 
                    String telephone, String address) 
    {
        this.username = username;
        this.password = password;
        this.name = name;
        this.telephone = telephone;
        this.address = address;
        card = null;
    }
    
    public void createCard(String bank, String number, Date expirationDate, 
                           String type, String securityCode)
    {
        
        if(card == null || expirationDate.after(Date.from(Instant.now())))
            card = new Card(bank, number, expirationDate, type, 
                                  securityCode);
    }
    
    

    public String getUsername() 
    {
        return username;
    }

    public String getPassword() 
    {
        return password;
    }

    public String getName() 
    {
        return name;
    }

    public String getTelephone() 
    {
        return telephone;
    }

    
    
    public String getAddress() 
    {
        return address;
    }

    public Card getCard() 
    {
        return card;
    }    

    public void setPassword(String password) 
    {
        this.password = password;
    }

    @Override
    public int hashCode() 
    {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.username);
        hash = 41 * hash + Objects.hashCode(this.password);
        hash = 41 * hash + Objects.hashCode(this.name);
        hash = 41 * hash + Objects.hashCode(this.telephone);
        hash = 41 * hash + Objects.hashCode(this.address);
        hash = 41 * hash + Objects.hashCode(this.card);
        return hash;
    }

    @Override
    public boolean equals(Object obj) 
    {
        if (this == obj) 
        {
            return true;
        }
        if (obj == null) 
        {
            return false;
        }
        if (getClass() != obj.getClass()) 
        {
            return false;
        }
        final Customer other = (Customer) obj;
        if (!Objects.equals(this.username, other.username)) 
        {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) 
        {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) 
        {
            return false;
        }
        if (!Objects.equals(this.telephone, other.telephone)) 
        {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) 
        {
            return false;
        }
        if (!Objects.equals(this.card, other.card)) 
        {
            return false;
        }
        return true;
    }
    
    

    public void setTelephone(String telephone) 
    {
        this.telephone = telephone;
    }

    public void setAddress(String address) 
    {
        this.address = address;
    }
 
    
    
}