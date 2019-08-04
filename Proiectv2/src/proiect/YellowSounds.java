package proiect;

import java.sql.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;
import onlineShop.*;
/**
 *
 * @author Andi
 */

public class YellowSounds extends javax.swing.JFrame 
{

    public YellowSounds() 
    {
        try
        {
            this.albumToShow = new Album("", new ArrayList<Track>(), "", 0, 0);
            this.artistToShow = new Artist("", "", "", "", 
                                           new ArrayList<>(), "");
            audio = AudioSystem.getClip();
            panelList = new ArrayList<>();
            labelList = new ArrayList<>();
            titles = new ArrayList<>();
            albumsInCart = new ArrayList<>();
            cartContent = new ArrayList<>();
            customer = new Customer("", "", "", "", "");
            customerCardType = false;
            newCustomerCardType = false;
            con = DBConnection.getInstance().getConnection();
            
            createFirstPage();
            
            initComponents();
            jPanel55 = new javax.swing.JPanel();
            ScrollPaneAlbumList = new javax.swing.JScrollPane();
            
        }
        catch(LineUnavailableException ex)
        {
             System.out.println(ex.getMessage());
        }
    }

    
    private Artist createArtist(String artist)
    {
        Artist auxArtist = null;
        try 
        {
            boolean ok = false;
            String sql = "SELECT * FROM artist WHERE name = '"  
                        + artist + "'";
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            List<Album> albumList = new ArrayList<>();
            String artistName = "";
            String infoArtist = "";
            String artistProfilePhoto = "";
            String artistIconPhoto = "";
            String genre = "";
            boolean firstRow = true;
            while(rs.next())
            {
                if(firstRow)
                {
                    artistName = rs.getString(1);
                    infoArtist = rs.getString(2);
                    artistProfilePhoto = rs.getString(3);
                    artistIconPhoto  = rs.getString(4);
                    genre = rs.getString(6);
                    firstRow = false;
                }
                
                Album a = createAlbum(rs.getString(5));
                if(a != null)
                    albumList.add(a);
            }
            
            if(! firstRow)
            {
                auxArtist = new Artist(artistName, infoArtist, 
                                       artistProfilePhoto, artistIconPhoto, 
                                       albumList, genre);
            }
            
        } 
        catch (SQLException ex) 
        {
            System.out.println(ex.getMessage());
        }
        
        return auxArtist;
    }
    
   private void createFirstPage()
   {
       try
       {
            st = con.createStatement();
            sql1 = "SELECT name, album FROM artist WHERE album IN "
                    + "(SELECT DISTINCT name FROM album WHERE quantity = "
                    + "(SELECT MIN(quantity) FROM album))";
            st = con.createStatement();
            rez1 = st.executeQuery(sql1);
            sql1 = "SELECT COUNT(a.name) FROM (SELECT DISTINCT name "
                    + "FROM artist) a";
            st = con.createStatement();
            rezCountArtists = st.executeQuery(sql1);
            sql1 = "SELECT DISTINCT e.iconPhotoPath, e.name FROM artist e, "
                    + "album a WHERE e.album = a.name "
                    + "ORDER BY a.quantity ASC";
            st = con.createStatement();
            rezArtistIconPath = st.executeQuery(sql1);
            if(jScrollPane1 != null)
                jScrollPane1.getViewport().setViewPosition(new java.awt.Point(0, 0));
       }
       catch(SQLException e)
       {
           System.out.println(e.getMessage());
       }
   }
   
   private void createArtistPage(Artist a)
    {
        jLabel24.setIcon(new javax.swing.ImageIcon(a.getProfilePhotoPath()));
        jLabel25.setText(a.getName());
        jTextPane1.setText(a.getInfo());
        albumListOf(a.getName());
    }
    
    private Album createAlbum(String album)
    {
        Album auxAlbum = null;
        try 
        {
            String sql = "SELECT * FROM album WHERE name = '"
                        + album + "'";
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            List<Track> trackList1 = new ArrayList<>();
            boolean ok = false;
            String str1 = "";
            String str2 = "";
            int int1 = 0;
            int int2 = 0;
            while(rs.next())
            {
                ok = true;
                str1 = rs.getString(1);
                str2 = rs.getString(3);
                int1 = rs.getInt(4);
                int2 = rs.getInt(5);
                st = con.createStatement();
                ResultSet columnSet = st.executeQuery("SELECT * FROM"
                                       + " track WHERE name = '" 
                                       + rs.getString(2) + "'");
                if(columnSet.next())
                    trackList1.add(new Track(columnSet.getString(1),
                                             columnSet.getInt(2),
                                             columnSet.getString(3)));
            }
            
            if(ok)
            {
                auxAlbum = new Album(str1,
                                        trackList1,
                                        str2,
                                        int1,
                                        int2);
            }
        } 
        catch (SQLException ex) 
        {
            System.out.println(ex.getMessage());
        } 
        
        return auxAlbum;
    }
    
    private void createAlbumPage(Album a)
    {
        try
        {
            jLabel21.setIcon(new javax.swing.ImageIcon(a.getCoverPath()));
            jLabel22.setText(a.getName());
            st = con.createStatement();
            String auxs = "SELECT name FROM artist "
                           + "WHERE album = '" + a.getName() + "'";  
            ResultSet rss = st.executeQuery(auxs);
            if(rss.next())
            {
                jLabel23.setText(rss.getString(1));
            }
            jButton6.setText("Buy for " + a.getPrice() + " RON");
            List<String> trackList = new ArrayList<>();
            a.getPlayList().forEach((t) -> {
                trackList.add(t.getName());
            });
            jList2.setListData(trackList.toArray(new String[trackList.size()]));
            
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        
    }
    
    private void albumListOf(String name)
    {
        try
        {
            
            st = con.createStatement();
            String sql = "SELECT DISTINCT al.name, al.coverPath"
                        + " FROM artist ar, album al" 
                        + " WHERE ar.album = al.name AND "
                        + "ar.name = '" + name + "'";
            ResultSet ress = st.executeQuery(sql);
            
            GridBagConstraints gridBagConstraints;
            gridBagConstraints = new java.awt.GridBagConstraints();
            
            jPanel51.remove(ScrollPaneAlbumList);
            jPanel55 = new javax.swing.JPanel();
            ScrollPaneAlbumList = new javax.swing.JScrollPane();
            
            //jPanel55 = new javax.swing.JPanel();
            jPanel55.setBackground(new java.awt.Color(23, 17, 17));
            jPanel55.setLayout(new java.awt.GridBagLayout());
            
            panelList = new ArrayList<>();
            labelList = new ArrayList<>();
            titles = new ArrayList<>();
            
            javax.swing.JPanel auxPanel1 = new javax.swing.JPanel();
            auxPanel1.setPreferredSize(new Dimension(50, 1));
            auxPanel1.setMinimumSize(new Dimension(50, 1));
            auxPanel1.setBackground(new Color(23,17,17));
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel55.add(auxPanel1, gridBagConstraints);
            
            
            int i = 0;
            while(ress.next())
            {
                javax.swing.JPanel pan = new javax.swing.JPanel();
                javax.swing.JLabel lab = new javax.swing.JLabel();
                javax.swing.JLabel tit = new javax.swing.JLabel();
                
                lab.setText("");
                lab.setIcon(new javax.swing.ImageIcon(ress.getString(2)));
                lab.addMouseListener(new java.awt.event.MouseAdapter() 
                {
                    private final String name = ress.getString(1);
                    
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt)
                    {
                        Album a; 
                        a = createAlbum(name);
                        if(a != null)
                        {
                            jList2.setListData(new String[0]);
                            albumToShow = a;
                            createAlbumPage(albumToShow);
                            jPanel25.removeAll();
                            jPanel25.add(jPanel42);
                            if(jScrollPane4 != null)
                                jScrollPane4.getViewport().setViewPosition
                                                    (new java.awt.Point(0, 0));
                            if(jScrollPane6 != null)
                                jScrollPane6.getViewport().setViewPosition
                                                    (new java.awt.Point(0, 0));
                            jPanel25.repaint();
                            jPanel25.validate();
                        }
                    }
                });
                
                gridBagConstraints.gridx = i * 2 + 1;
                gridBagConstraints.gridy = 0;
                gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                labelList.add(lab);
                jPanel55.add(labelList.get(i), gridBagConstraints);

                tit.setText(ress.getString(1));
                tit.setFont(new java.awt.Font("Century Gothic", 1, 18));
                tit.setForeground(Color.WHITE);
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = i * 2 + 1;
                gridBagConstraints.gridy = 1;
                gridBagConstraints.fill = java.awt.GridBagConstraints.NONE;
                titles.add(tit);
                jPanel55.add(titles.get(i), gridBagConstraints);

               
                pan.setBackground(new java.awt.Color(23, 17, 17));
                pan.setForeground(new java.awt.Color(255, 255, 255));
                pan.setPreferredSize(new java.awt.Dimension(50, 1));
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 2 * (i + 1);
                gridBagConstraints.gridy = 0;
                panelList.add(pan);
                jPanel55.add(panelList.get(i), gridBagConstraints);
                i ++;
            }
            
            jPanel55.setMinimumSize(new java.awt.Dimension(1300, 350));
            if(i > 3)
                jPanel55.setPreferredSize(new java.awt.Dimension
                                                    (1300 + (i - 3) * 400, 350));
            else jPanel55.setPreferredSize(new java.awt.Dimension(1300, 350));
       
            ScrollPaneAlbumList.setBorder(null);
            ScrollPaneAlbumList.setPreferredSize(new Dimension(1330, 350));
            ScrollPaneAlbumList.setMinimumSize(new Dimension(1330, 350));
            ScrollPaneAlbumList.setViewportView(jPanel55);

          
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 5;
            jPanel51.add(ScrollPaneAlbumList, gridBagConstraints);
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel65 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jPanel23 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel18 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel34 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel26 = new javax.swing.JPanel();
        jPanel36 = new javax.swing.JPanel();
        jPanel27 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        searchLabel = new javax.swing.JLabel();
        jPanel28 = new javax.swing.JPanel();
        jPanel29 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel30 = new javax.swing.JPanel();
        noList = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        ListPanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jPanel32 = new javax.swing.JPanel();
        jPanel31 = new javax.swing.JPanel();
        jPanel33 = new javax.swing.JPanel();
        jPanel35 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jPanel39 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jPanel37 = new javax.swing.JPanel();
        jPanel41 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jPanel40 = new javax.swing.JPanel();
        jPanel42 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jPanel43 = new javax.swing.JPanel();
        jPanel44 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jPanel45 = new javax.swing.JPanel();
        jPanel46 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jPanel47 = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        jPanel48 = new javax.swing.JPanel();
        nowPlayingLabel = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList<>();
        jPanel49 = new javax.swing.JPanel();
        jPanel50 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jPanel51 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jPanel52 = new javax.swing.JPanel();
        jPanel56 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jPanel58 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jPanel57 = new javax.swing.JPanel();
        jPanel54 = new javax.swing.JPanel();
        jPanel53 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jPanel59 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jPanel60 = new javax.swing.JPanel();
        albumsToBuy = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        jList3 = new javax.swing.JList<>();
        jPanel71 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jPanel61 = new javax.swing.JPanel();
        jPanel62 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        subTotalLabel = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jPanel66 = new javax.swing.JPanel();
        jPanel64 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        totalLabel = new javax.swing.JLabel();
        jPanel67 = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jPanel68 = new javax.swing.JPanel();
        jPanel63 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jPanel65 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jPanel69 = new javax.swing.JPanel();
        jPanel70 = new javax.swing.JPanel();
        jButton8 = new javax.swing.JButton();
        jPanel72 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        jPanel73 = new javax.swing.JPanel();
        jPanel74 = new javax.swing.JPanel();
        jPanel76 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jPanel79 = new javax.swing.JPanel();
        jPanel80 = new javax.swing.JPanel();
        jTextField2 = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        jPanel82 = new javax.swing.JPanel();
        jPanel81 = new javax.swing.JPanel();
        jTextField3 = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        jPanel83 = new javax.swing.JPanel();
        jPanel84 = new javax.swing.JPanel();
        jTextField4 = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        jPanel85 = new javax.swing.JPanel();
        jPanel86 = new javax.swing.JPanel();
        jTextField5 = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        jPanel87 = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        jPanel88 = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jPanel89 = new javax.swing.JPanel();
        jPanel90 = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        cardTypeLabel = new javax.swing.JLabel();
        jPanel91 = new javax.swing.JPanel();
        jLabel46 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jPanel92 = new javax.swing.JPanel();
        jLabel47 = new javax.swing.JLabel();
        jPasswordField1 = new javax.swing.JPasswordField();
        jPanel93 = new javax.swing.JPanel();
        jPanel94 = new javax.swing.JPanel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jComboBox3 = new javax.swing.JComboBox<>();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel48 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        jPanel125 = new javax.swing.JPanel();
        jPanel95 = new javax.swing.JPanel();
        jPanel96 = new javax.swing.JPanel();
        jPanel97 = new javax.swing.JPanel();
        jPanel98 = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        jPasswordField2 = new javax.swing.JPasswordField();
        jButton9 = new javax.swing.JButton();
        jPanel99 = new javax.swing.JPanel();
        jPanel77 = new javax.swing.JPanel();
        jPanel78 = new javax.swing.JPanel();
        jLabel51 = new javax.swing.JLabel();
        jPanel100 = new javax.swing.JPanel();
        jTextField8 = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        jPanel101 = new javax.swing.JPanel();
        jPanel102 = new javax.swing.JPanel();
        jPanel103 = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        jPasswordField3 = new javax.swing.JPasswordField();
        jPanel104 = new javax.swing.JPanel();
        jButton10 = new javax.swing.JButton();
        jPanel127 = new javax.swing.JPanel();
        jLabel64 = new javax.swing.JLabel();
        jPanel75 = new javax.swing.JPanel();
        jLabel54 = new javax.swing.JLabel();
        jPanel105 = new javax.swing.JPanel();
        jLabel55 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jPanel110 = new javax.swing.JPanel();
        jLabel56 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jPanel111 = new javax.swing.JPanel();
        jLabel57 = new javax.swing.JLabel();
        jTextField11 = new javax.swing.JTextField();
        jPanel112 = new javax.swing.JPanel();
        jLabel58 = new javax.swing.JLabel();
        jTextField12 = new javax.swing.JTextField();
        jPanel109 = new javax.swing.JPanel();
        jPanel113 = new javax.swing.JPanel();
        jPanel116 = new javax.swing.JPanel();
        jPanel106 = new javax.swing.JPanel();
        jPanel107 = new javax.swing.JPanel();
        jPanel114 = new javax.swing.JPanel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jTextField14 = new javax.swing.JTextField();
        jPanel117 = new javax.swing.JPanel();
        jLabel61 = new javax.swing.JLabel();
        jTextField15 = new javax.swing.JTextField();
        jPanel118 = new javax.swing.JPanel();
        jLabel62 = new javax.swing.JLabel();
        jTextField16 = new javax.swing.JTextField();
        jPanel119 = new javax.swing.JPanel();
        jPanel120 = new javax.swing.JPanel();
        jPanel121 = new javax.swing.JPanel();
        userCardType = new javax.swing.JLabel();
        jPanel122 = new javax.swing.JPanel();
        jLabel63 = new javax.swing.JLabel();
        jPanel123 = new javax.swing.JPanel();
        jTextField17 = new javax.swing.JTextField();
        jPanel108 = new javax.swing.JPanel();
        jButton11 = new javax.swing.JButton();
        jPanel115 = new javax.swing.JPanel();
        jPanel124 = new javax.swing.JPanel();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jPanel126 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));
        jPanel1.setMinimumSize(new java.awt.Dimension(0, 40));
        jPanel1.setPreferredSize(new java.awt.Dimension(400, 40));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jPanel2.setBackground(new java.awt.Color(51, 51, 51));
        jPanel2.setToolTipText("");
        jPanel2.setMinimumSize(new java.awt.Dimension(0, 40));
        jPanel2.setPreferredSize(new java.awt.Dimension(100, 40));
        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel2MouseClicked(evt);
            }
        });
        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel65.setIcon(new javax.swing.ImageIcon("C:\\JavaProiect\\Proiectv2\\OtherPhotos\\YellowSounds4.png")); // NOI18N
        jPanel2.add(jLabel65, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel1.add(jPanel2, gridBagConstraints);

        jPanel3.setBackground(new java.awt.Color(51, 51, 51));
        jPanel3.setMinimumSize(new java.awt.Dimension(0, 40));
        jPanel3.setPreferredSize(new java.awt.Dimension(100, 40));
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel3MouseExited(evt);
            }
        });
        jPanel3.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Artists");
        jPanel3.add(jLabel1, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel1.add(jPanel3, gridBagConstraints);

        jPanel4.setBackground(new java.awt.Color(51, 51, 51));
        jPanel4.setMinimumSize(new java.awt.Dimension(0, 40));
        jPanel4.setPreferredSize(new java.awt.Dimension(100, 40));
        jPanel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel4MouseExited(evt);
            }
        });
        jPanel4.setLayout(new java.awt.GridBagLayout());

        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Shopping Cart");
        jPanel4.add(jLabel2, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel1.add(jPanel4, gridBagConstraints);

        jPanel5.setBackground(new java.awt.Color(51, 51, 51));
        jPanel5.setMinimumSize(new java.awt.Dimension(0, 40));
        jPanel5.setPreferredSize(new java.awt.Dimension(100, 40));
        jPanel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel5MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel5MouseExited(evt);
            }
        });
        jPanel5.setLayout(new java.awt.GridBagLayout());

        jLabel3.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("My account");
        jPanel5.add(jLabel3, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        jPanel1.add(jPanel5, gridBagConstraints);

        getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);

        jPanel25.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanel25.setPreferredSize(new java.awt.Dimension(640, 480));
        jPanel25.setLayout(new java.awt.CardLayout());

        jPanel6.setMinimumSize(new java.awt.Dimension(640, 480));
        jPanel6.setPreferredSize(new java.awt.Dimension(640, 480));
        jPanel6.setLayout(new java.awt.BorderLayout());

        jPanel7.setMinimumSize(new java.awt.Dimension(620, 400));
        jPanel7.setPreferredSize(new java.awt.Dimension(620, 400));
        jPanel7.setLayout(new java.awt.CardLayout());

        jPanel10.setBackground(new java.awt.Color(0, 0, 0));
        jPanel10.setMinimumSize(new java.awt.Dimension(100, 400));
        jPanel10.setPreferredSize(new java.awt.Dimension(620, 400));
        jPanel10.setLayout(new java.awt.GridBagLayout());

        jPanel11.setBackground(new java.awt.Color(0, 0, 0));
        jPanel11.setMinimumSize(new java.awt.Dimension(0, 150));
        jPanel11.setPreferredSize(new java.awt.Dimension(200, 150));
        jPanel11.setLayout(new java.awt.GridBagLayout());

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Shawn Mendes");
        jPanel11.add(jLabel4, new java.awt.GridBagConstraints());

        jLabel5.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Handwritten");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel11.add(jLabel5, gridBagConstraints);

        jPanel12.setBackground(new java.awt.Color(0, 0, 0));
        jPanel12.setMinimumSize(new java.awt.Dimension(0, 20));
        jPanel12.setPreferredSize(new java.awt.Dimension(0, 20));

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 175, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel11.add(jPanel12, gridBagConstraints);

        jPanel13.setBackground(new java.awt.Color(0, 0, 0));
        jPanel13.setMinimumSize(new java.awt.Dimension(0, 30));
        jPanel13.setPreferredSize(new java.awt.Dimension(0, 30));
        jPanel13.setLayout(new java.awt.GridBagLayout());

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("Century Gothic", 1, 11)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 0, 51));
        jButton1.setText("Buy right now!");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel13.add(jButton1, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel11.add(jPanel13, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel10.add(jPanel11, gridBagConstraints);

        jPanel14.setBackground(new java.awt.Color(0, 0, 0));
        jPanel14.setInheritsPopupMenu(true);
        jPanel14.setMinimumSize(new java.awt.Dimension(100, 150));
        jPanel14.setPreferredSize(new java.awt.Dimension(100, 150));

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel10.add(jPanel14, gridBagConstraints);

        jLabel6.setIcon(new javax.swing.ImageIcon("C:\\JavaProiect\\Proiectv2\\FirstWebPage\\Shawn_Mendes_-_Handwritten.png")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel10.add(jLabel6, gridBagConstraints);

        jPanel7.add(jPanel10, "card4");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 640, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );

        jPanel7.add(jPanel9, "card3");

        jPanel8.setBackground(new java.awt.Color(0, 153, 153));
        jPanel8.setLayout(new java.awt.GridBagLayout());

        jPanel22.setBackground(new java.awt.Color(0, 153, 153));
        jPanel22.setMinimumSize(new java.awt.Dimension(0, 150));
        jPanel22.setPreferredSize(new java.awt.Dimension(220, 150));
        jPanel22.setLayout(new java.awt.GridBagLayout());

        jLabel12.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Childish Gambino");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel22.add(jLabel12, gridBagConstraints);

        jLabel13.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Awaken, My Love!");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        jPanel22.add(jLabel13, gridBagConstraints);

        jButton4.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(0, 102, 102));
        jButton4.setText("Buy Right Now!");
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton4MouseClicked(evt);
            }
        });
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        jPanel22.add(jButton4, gridBagConstraints);

        jPanel23.setBackground(new java.awt.Color(0, 153, 153));
        jPanel23.setMinimumSize(new java.awt.Dimension(0, 20));
        jPanel23.setPreferredSize(new java.awt.Dimension(100, 20));

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 209, Short.MAX_VALUE)
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel22.add(jPanel23, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel8.add(jPanel22, gridBagConstraints);

        jPanel24.setBackground(new java.awt.Color(0, 153, 153));
        jPanel24.setMinimumSize(new java.awt.Dimension(100, 150));

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 316, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel8.add(jPanel24, gridBagConstraints);

        jLabel14.setIcon(new javax.swing.ImageIcon("C:\\JavaProiect\\Proiectv2\\Converted Audio\\Awaken, my love! -- childish gambino\\Childish_Gambino_-_Awaken,_My_Love!.png")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel8.add(jLabel14, gridBagConstraints);

        jPanel7.add(jPanel8, "card2");

        jPanel6.add(jPanel7, java.awt.BorderLayout.PAGE_START);

        jPanel17.setBackground(new java.awt.Color(255, 204, 0));
        jPanel17.setMinimumSize(new java.awt.Dimension(640, 20));
        jPanel17.setPreferredSize(new java.awt.Dimension(640, 20));
        jPanel17.setLayout(new java.awt.BorderLayout());

        jPanel15.setBackground(new java.awt.Color(255, 204, 0));
        jPanel15.setMinimumSize(new java.awt.Dimension(2147483647, 20));
        jPanel15.setPreferredSize(new java.awt.Dimension(640, 20));
        jPanel15.setLayout(new java.awt.BorderLayout());

        jPanel16.setBackground(new java.awt.Color(255, 204, 0));
        jPanel16.setMinimumSize(new java.awt.Dimension(100, 20));
        jPanel16.setPreferredSize(new java.awt.Dimension(100, 20));
        jPanel16.setLayout(new java.awt.GridBagLayout());

        jButton2.setBackground(new java.awt.Color(0, 0, 0));
        jButton2.setFont(new java.awt.Font("Century Gothic", 1, 10)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("1");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel16.add(jButton2, new java.awt.GridBagConstraints());

        jButton3.setBackground(new java.awt.Color(0, 0, 0));
        jButton3.setFont(new java.awt.Font("Century Gothic", 1, 10)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("2");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        jPanel16.add(jButton3, gridBagConstraints);

        jPanel15.add(jPanel16, java.awt.BorderLayout.PAGE_START);

        jScrollPane1.setBorder(null);
        jScrollPane1.setMinimumSize(new java.awt.Dimension(1000, 0));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(1000, 300));

        jPanel18.setBackground(new java.awt.Color(255, 204, 0));
        jPanel18.setMinimumSize(new java.awt.Dimension(1300, 350));
        jPanel18.setPreferredSize(new java.awt.Dimension(1300, 350));
        jPanel18.setLayout(new java.awt.GridBagLayout());

        jLabel7.setBackground(new java.awt.Color(255, 204, 0));
        jLabel7.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel7.setText("Most Wanted This Week");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel18.add(jLabel7, gridBagConstraints);

        try
        {
            if(rez1.next()){
                jLabel8.setBackground(new java.awt.Color(102, 102, 0));
                jLabel8.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
                jLabel8.setForeground(new java.awt.Color(51, 51, 51));
                jLabel8.setText(rez1.getString(2));
            }
        }
        catch(SQLException e)
        {
            jLabel8.setText("NULL");
        }
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel18.add(jLabel8, gridBagConstraints);

        try
        {
            jLabel9.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
            jLabel9.setText(rez1.getString(1));
        }
        catch(SQLException e)
        {
            jLabel9.setText("Null");
        }
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel18.add(jLabel9, gridBagConstraints);

        jPanel19.setBackground(new java.awt.Color(255, 204, 0));
        jPanel19.setMinimumSize(new java.awt.Dimension(1366, 20));
        jPanel19.setPreferredSize(new java.awt.Dimension(1366, 20));

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1366, Short.MAX_VALUE)
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel18.add(jPanel19, gridBagConstraints);

        jLabel10.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel10.setText("You Might Like...");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel18.add(jLabel10, gridBagConstraints);

        jPanel20.setBackground(new java.awt.Color(23, 17, 17));
        jPanel20.setMinimumSize(new java.awt.Dimension(1366, 200));
        jPanel20.setPreferredSize(new java.awt.Dimension(1366, 200));
        jPanel20.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel18.add(jPanel20, gridBagConstraints);
        try
        {
            rezCountArtists.next();
            nrJLabels = rezCountArtists.getInt(1);
            jLabeli = new javax.swing.JLabel[nrJLabels];
            jPaneli = new javax.swing.JPanel[nrJLabels];
            jNames = new javax.swing.JLabel[nrJLabels];

            javax.swing.JPanel auxPanel1 = new javax.swing.JPanel();
            auxPanel1.setPreferredSize(new Dimension(100, 10));
            auxPanel1.setMinimumSize(new Dimension(100, 10));
            auxPanel1.setBackground(new Color(23,17,17));
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel20.add(auxPanel1, gridBagConstraints);

            for(int i = 0; i < ((nrJLabels < 5) ? nrJLabels : 5); i ++)
            {
                rezArtistIconPath.next();

                jLabeli[i] = new javax.swing.JLabel();
                jLabeli[i].setIcon(new javax.swing.
                    ImageIcon(rezArtistIconPath.getString(1)));
                jLabeli[i].addMouseListener(new java.awt.event.MouseAdapter()
                    {
                        final String name = rezArtistIconPath.getString(2);
                        public void mouseClicked(java.awt.event.MouseEvent evt)
                        {
                            Artist a;
                            a = createArtist(name);
                            if(a != null)
                            {
                                artistToShow = a;
                                createArtistPage(a);
                                jPanel25.removeAll();
                                jPanel25.add(jPanel50);
                                if(jScrollPane5 != null)
                                jScrollPane5.getViewport().setViewPosition(new java.awt.Point(0, 0));
                                if(jScrollPane7 != null)
                                jScrollPane7.getViewport().setViewPosition(new java.awt.Point(0, 0));
                                jPanel25.repaint();
                                jPanel25.validate();
                            }
                        }
                    });
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = i * 2 + 1;
                    gridBagConstraints.gridy = 0;
                    jPanel20.add(jLabeli[i], gridBagConstraints);

                    jPaneli[i] = new javax.swing.JPanel();

                    jPaneli[i].setBackground(new java.awt.Color(23,17,17));
                    jPaneli[i].setMinimumSize(new java.awt.Dimension(80, 150));
                    jPaneli[i].setPreferredSize(new java.awt.Dimension(80, 150));

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 2 * (i + 1);
                    gridBagConstraints.gridy = 0;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel20.add(jPaneli[i], gridBagConstraints);

                    jNames[i] = new javax.swing.JLabel();
                    jNames[i].setForeground(Color.WHITE);
                    jNames[i].setFont(new Font("Century Gothic", 1, 12));
                    jNames[i].setText(rezArtistIconPath.getString(2));
                    gridBagConstraints.gridx = i * 2 + 1;
                    gridBagConstraints.gridy = 1;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.NONE;
                    jPanel20.add(jNames[i], gridBagConstraints);

                }
            }
            catch(SQLException e)
            {
                System.out.println(e.getMessage());
            }

            /*jLabel11 = new javax.swing.JLabel();

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            jPanel20.add(jLabel11, gridBagConstraints);
            */

            jPanel21.setBackground(new java.awt.Color(255, 204, 0));
            jPanel21.setMinimumSize(new java.awt.Dimension(0, 20));
            jPanel21.setPreferredSize(new java.awt.Dimension(0, 20));

            javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
            jPanel21.setLayout(jPanel21Layout);
            jPanel21Layout.setHorizontalGroup(
                jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 1333, Short.MAX_VALUE)
            );
            jPanel21Layout.setVerticalGroup(
                jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 20, Short.MAX_VALUE)
            );

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 6;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel18.add(jPanel21, gridBagConstraints);

            jLabel11.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
            jLabel11.setText("©Yellow Sounds. All Rights Reserved");
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 7;
            jPanel18.add(jLabel11, gridBagConstraints);

            jScrollPane1.setViewportView(jPanel18);

            jPanel15.add(jScrollPane1, java.awt.BorderLayout.CENTER);

            jPanel17.add(jPanel15, java.awt.BorderLayout.CENTER);

            jPanel6.add(jPanel17, java.awt.BorderLayout.CENTER);

            jPanel25.add(jPanel6, "card2");

            jPanel34.setBackground(new java.awt.Color(255, 204, 0));
            jPanel34.setMinimumSize(new java.awt.Dimension(640, 480));
            jPanel34.setPreferredSize(new java.awt.Dimension(640, 480));
            jPanel34.setLayout(new java.awt.BorderLayout());

            jScrollPane2.setBorder(null);
            jScrollPane2.setMinimumSize(new java.awt.Dimension(1366, 738));
            jScrollPane2.setPreferredSize(new java.awt.Dimension(1366, 738));

            jPanel26.setBackground(new java.awt.Color(255, 204, 0));
            jPanel26.setMinimumSize(new java.awt.Dimension(800, 800));
            jPanel26.setPreferredSize(new java.awt.Dimension(800, 800));
            jPanel26.setLayout(new java.awt.GridBagLayout());

            jPanel36.setBackground(new java.awt.Color(255, 204, 0));
            jPanel36.setLayout(new java.awt.GridBagLayout());

            jPanel27.setBackground(new java.awt.Color(255, 204, 0));
            jPanel27.setMinimumSize(new java.awt.Dimension(1366, 100));
            jPanel27.setLayout(new java.awt.GridBagLayout());

            jTextField1.setBackground(new java.awt.Color(51, 51, 51));
            jTextField1.setForeground(new java.awt.Color(255, 255, 255));
            jTextField1.setMinimumSize(new java.awt.Dimension(30, 30));
            jTextField1.setPreferredSize(new java.awt.Dimension(350, 30));
            jTextField1.setSelectionColor(new java.awt.Color(102, 102, 102));
            jTextField1.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jTextField1ActionPerformed(evt);
                }
            });
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 2;
            gridBagConstraints.gridy = 0;
            jPanel27.add(jTextField1, gridBagConstraints);

            searchLabel.setIcon(new javax.swing.ImageIcon("C:\\JavaProiect\\Proiectv2\\SecondWebPage\\searchPhoto.png")); // NOI18N
            searchLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    searchLabelMouseClicked(evt);
                }
            });
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            jPanel27.add(searchLabel, gridBagConstraints);

            jPanel28.setBackground(new java.awt.Color(255, 204, 0));
            jPanel28.setMinimumSize(new java.awt.Dimension(5, 1));
            jPanel28.setPreferredSize(new java.awt.Dimension(15, 1));

            javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
            jPanel28.setLayout(jPanel28Layout);
            jPanel28Layout.setHorizontalGroup(
                jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 15, Short.MAX_VALUE)
            );
            jPanel28Layout.setVerticalGroup(
                jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 1, Short.MAX_VALUE)
            );

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 0;
            jPanel27.add(jPanel28, gridBagConstraints);

            jPanel29.setBackground(new java.awt.Color(255, 204, 0));
            jPanel29.setMinimumSize(new java.awt.Dimension(5, 1));
            jPanel29.setPreferredSize(new java.awt.Dimension(15, 1));

            javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
            jPanel29.setLayout(jPanel29Layout);
            jPanel29Layout.setHorizontalGroup(
                jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 15, Short.MAX_VALUE)
            );
            jPanel29Layout.setVerticalGroup(
                jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 1, Short.MAX_VALUE)
            );

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 3;
            gridBagConstraints.gridy = 0;
            jPanel27.add(jPanel29, gridBagConstraints);

            jComboBox1.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
            jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "artist", "album", "track" }));
            jComboBox1.setMinimumSize(new java.awt.Dimension(5, 22));
            jComboBox1.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jComboBox1ActionPerformed(evt);
                }
            });
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 4;
            gridBagConstraints.gridy = 0;
            jPanel27.add(jComboBox1, gridBagConstraints);

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            jPanel36.add(jPanel27, gridBagConstraints);

            jPanel30.setMaximumSize(new java.awt.Dimension(474, 150));
            jPanel30.setMinimumSize(new java.awt.Dimension(474, 150));
            jPanel30.setPreferredSize(new java.awt.Dimension(474, 150));
            jPanel30.setLayout(new java.awt.CardLayout());

            noList.setBackground(new java.awt.Color(255, 204, 0));
            noList.setLayout(new java.awt.GridBagLayout());

            jLabel19.setBackground(new java.awt.Color(204, 102, 0));
            jLabel19.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
            jLabel19.setForeground(new java.awt.Color(204, 0, 0));
            jLabel19.setText("Search for your favourite music!");
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            noList.add(jLabel19, gridBagConstraints);

            jLabel20.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
            jLabel20.setText("Choose the criteria of selection");
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 1;
            noList.add(jLabel20, gridBagConstraints);

            jPanel30.add(noList, "card2");

            ListPanel.setBackground(new java.awt.Color(255, 204, 0));
            ListPanel.setLayout(new java.awt.GridBagLayout());

            jScrollPane3.setMinimumSize(new java.awt.Dimension(230, 120));
            jScrollPane3.setPreferredSize(new java.awt.Dimension(230, 120));

            jList1.setBackground(new java.awt.Color(51, 51, 51));
            jList1.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
            jList1.setForeground(new java.awt.Color(255, 255, 255));
            jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
            jList1.setMaximumSize(new java.awt.Dimension(230, 300));
            jList1.setSelectionBackground(new java.awt.Color(255, 204, 0));
            jList1.setSelectionForeground(new java.awt.Color(102, 102, 102));
            jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
                public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                    jList1ValueChanged(evt);
                }
            });
            jScrollPane3.setViewportView(jList1);

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 1;
            ListPanel.add(jScrollPane3, gridBagConstraints);

            jPanel32.setBackground(new java.awt.Color(255, 204, 0));
            jPanel32.setMaximumSize(new java.awt.Dimension(0, 10));
            jPanel32.setMinimumSize(new java.awt.Dimension(0, 10));

            javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
            jPanel32.setLayout(jPanel32Layout);
            jPanel32Layout.setHorizontalGroup(
                jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 230, Short.MAX_VALUE)
            );
            jPanel32Layout.setVerticalGroup(
                jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 10, Short.MAX_VALUE)
            );

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            ListPanel.add(jPanel32, gridBagConstraints);

            jPanel30.add(ListPanel, "card3");

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 1;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel36.add(jPanel30, gridBagConstraints);

            jPanel31.setLayout(new java.awt.CardLayout());

            jPanel33.setBackground(new java.awt.Color(255, 204, 0));
            jPanel33.setMinimumSize(new java.awt.Dimension(474, 100));
            jPanel33.setPreferredSize(new java.awt.Dimension(474, 100));

            javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
            jPanel33.setLayout(jPanel33Layout);
            jPanel33Layout.setHorizontalGroup(
                jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 474, Short.MAX_VALUE)
            );
            jPanel33Layout.setVerticalGroup(
                jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 100, Short.MAX_VALUE)
            );

            jPanel31.add(jPanel33, "card2");

            jPanel35.setBackground(new java.awt.Color(255, 204, 0));
            jPanel35.setMinimumSize(new java.awt.Dimension(474, 100));
            jPanel35.setPreferredSize(new java.awt.Dimension(474, 100));
            jPanel35.setLayout(new java.awt.GridBagLayout());

            jLabel15.setBackground(new java.awt.Color(204, 51, 0));
            jLabel15.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
            jLabel15.setForeground(new java.awt.Color(204, 51, 0));
            jLabel15.setText("Artist"
            );
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            jPanel35.add(jLabel15, gridBagConstraints);

            jLabel16.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
            jLabel16.setText("Genre");
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 1;
            jPanel35.add(jLabel16, gridBagConstraints);

            jButton5.setBackground(new java.awt.Color(0, 0, 0));
            jButton5.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
            jButton5.setForeground(new java.awt.Color(255, 255, 255));
            jButton5.setText("");
            jButton5.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    jButton5MouseClicked(evt);
                }
            });
            jButton5.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton5ActionPerformed(evt);
                }
            });
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 2;
            jPanel35.add(jButton5, gridBagConstraints);

            jPanel31.add(jPanel35, "card3");

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 2;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel36.add(jPanel31, gridBagConstraints);

            jPanel26.add(jPanel36, new java.awt.GridBagConstraints());

            jPanel39.setBackground(new java.awt.Color(51, 0, 51));
            jPanel39.setMinimumSize(new java.awt.Dimension(1366, 30));
            jPanel39.setPreferredSize(new java.awt.Dimension(1366, 30));
            jPanel39.setLayout(new java.awt.GridBagLayout());

            jLabel17.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
            jLabel17.setForeground(new java.awt.Color(255, 255, 255));
            jLabel17.setText("Pop");
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
            jPanel39.add(jLabel17, gridBagConstraints);

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 1;
            gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
            jPanel26.add(jPanel39, gridBagConstraints);

            jPanel37.setBackground(new java.awt.Color(51, 0, 51));
            jPanel37.setMinimumSize(new java.awt.Dimension(1366, 190));
            jPanel37.setPreferredSize(new java.awt.Dimension(1366, 190));
            jPanel37.setLayout(new java.awt.GridBagLayout());
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 2;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel26.add(jPanel37, gridBagConstraints);
            try
            {
                st = con.createStatement();

                String ss = "SELECT COUNT(a.name) FROM (SELECT DISTINCT name FROM artist "
                + "WHERE genre = 'Pop') a";
                ResultSet rs = st.executeQuery(ss);
                int x = 0;
                if(rs.next())
                {
                    x  = rs.getInt(1);
                }

                popArtists = new javax.swing.JLabel[x];
                popArtistsNames = new javax.swing.JLabel[x];
                popSpaceBetween = new javax.swing.JPanel[x];

                st = con.createStatement();
                ss = "SELECT DISTINCT a.name, a.iconPhotoPath FROM artist a, album b "
                + "WHERE a.album = b.name AND genre = 'Pop' ORDER BY quantity "
                + "ASC";

                javax.swing.JPanel auxPanel = new javax.swing.JPanel();
                auxPanel.setPreferredSize(new Dimension(100, 10));
                auxPanel.setMinimumSize(new Dimension(100, 10));
                auxPanel.setBackground(new Color(51, 0, 51));
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 0;
                gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                jPanel37.add(auxPanel, gridBagConstraints);

                ResultSet out = st.executeQuery(ss);
                for(int i = 0; i < ((x < 5)? x : 5); i ++)
                {
                    out.next();
                    popArtists[i] = new javax.swing.JLabel();
                    popArtists[i].setIcon(new javax.swing.ImageIcon(out.getString(2)));
                    popArtists[i].addMouseListener(new java.awt.event.MouseAdapter()
                        {
                            final String name = out.getString(1);
                            public void mouseClicked(java.awt.event.MouseEvent evt)
                            {
                                Artist a;
                                a = createArtist(name);
                                if(a != null)
                                {
                                    artistToShow = a;
                                    createArtistPage(a);
                                    jPanel25.removeAll();
                                    jPanel25.add(jPanel50);
                                    if(jScrollPane5 != null)
                                    jScrollPane5.getViewport().setViewPosition(new java.awt.Point(0, 0));
                                    if(jScrollPane7 != null)
                                    jScrollPane7.getViewport().setViewPosition(new java.awt.Point(0, 0));
                                    jPanel25.repaint();
                                    jPanel25.validate();
                                }
                            }
                        });
                        gridBagConstraints = new java.awt.GridBagConstraints();
                        gridBagConstraints.gridx = i * 2 + 1;
                        gridBagConstraints.gridy = 0;
                        jPanel37.add(popArtists[i], gridBagConstraints);

                        popSpaceBetween[i] = new javax.swing.JPanel();
                        popSpaceBetween[i].setMinimumSize(new java.awt.Dimension(100, 10));
                        popSpaceBetween[i].setPreferredSize(new java.awt.Dimension(100, 10));
                        popSpaceBetween[i].setBackground(new java.awt.Color(51, 0, 51));
                        gridBagConstraints = new java.awt.GridBagConstraints();
                        gridBagConstraints.gridx = (i + 1) * 2;
                        gridBagConstraints.gridy = 0;
                        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                        jPanel37.add(popSpaceBetween[i], gridBagConstraints);

                        popArtistsNames[i] = new javax.swing.JLabel();
                        popArtistsNames[i].setText(out.getString(1));
                        popArtistsNames[i].setForeground(Color.WHITE);
                        popArtistsNames[i].setFont(new Font("Century Gothic", 1, 12));
                        gridBagConstraints = new java.awt.GridBagConstraints();
                        gridBagConstraints.gridx = i * 2 + 1;
                        gridBagConstraints.gridy = 1;
                        jPanel37.add(popArtistsNames[i], gridBagConstraints);
                    }
                }
                catch(SQLException e)
                {
                    System.out.println(e.getMessage());
                }

                jPanel41.setBackground(new java.awt.Color(0, 51, 51));
                jPanel41.setMinimumSize(new java.awt.Dimension(1366, 30));
                jPanel41.setPreferredSize(new java.awt.Dimension(1366, 30));
                jPanel41.setLayout(new java.awt.GridBagLayout());

                jLabel18.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
                jLabel18.setForeground(new java.awt.Color(255, 255, 255));
                jLabel18.setText("R&B/Soul");
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                jPanel41.add(jLabel18, gridBagConstraints);

                /*

                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 1;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
                jPanel26.add(jPanel41, gridBagConstraints);
                */

                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 3;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
                jPanel26.add(jPanel41, gridBagConstraints);

                jPanel40.setBackground(new java.awt.Color(0, 51, 51));
                jPanel40.setMinimumSize(new java.awt.Dimension(1366, 190));
                jPanel40.setPreferredSize(new java.awt.Dimension(1366, 190));
                jPanel40.setLayout(new java.awt.GridBagLayout());

                /*

                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 2;
                gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                jPanel26.add(jPanel40, gridBagConstraints);
                */

                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 4;
                gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                jPanel26.add(jPanel40, gridBagConstraints);

                try
                {
                    st = con.createStatement();

                    String ss1 = "SELECT COUNT(a.name) FROM (SELECT DISTINCT name FROM artist "
                    + "WHERE genre = 'R&B/Soul') a";
                    ResultSet rs1 = st.executeQuery(ss1);
                    int x1 = 0;
                    if(rs1.next())
                    {
                        x1  = rs1.getInt(1);
                    }

                    rbArtists = new javax.swing.JLabel[x1];
                    rbArtistsNames = new javax.swing.JLabel[x1];
                    rbSpaceBetween = new javax.swing.JPanel[x1];

                    st = con.createStatement();
                    ss1 = "SELECT DISTINCT a.name, a.iconPhotoPath FROM artist a, album b "
                    + "WHERE a.album = b.name AND genre = 'R&B/Soul' ORDER BY quantity "
                    + "ASC";

                    javax.swing.JPanel auxPanel = new javax.swing.JPanel();
                    auxPanel.setPreferredSize(new Dimension(100, 10));
                    auxPanel.setMinimumSize(new Dimension(100, 10));
                    auxPanel.setBackground(new Color(0, 51, 51));
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 0;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel40.add(auxPanel, gridBagConstraints);

                    ResultSet out1 = st.executeQuery(ss1);
                    for(int i = 0; i < ((x1 < 5)? x1 : 5); i ++)
                    {
                        out1.next();
                        rbArtists[i] = new javax.swing.JLabel();
                        rbArtists[i].setIcon(new javax.swing.ImageIcon(out1.getString(2)));
                        rbArtists[i].addMouseListener(new java.awt.event.MouseAdapter()
                            {
                                final String name = out1.getString(1);
                                public void mouseClicked(java.awt.event.MouseEvent evt)
                                {
                                    Artist a;
                                    a = createArtist(name);
                                    if(a != null)
                                    {
                                        artistToShow = a;
                                        createArtistPage(a);
                                        jPanel25.removeAll();
                                        jPanel25.add(jPanel50);
                                        if(jScrollPane5 != null)
                                        jScrollPane5.getViewport().setViewPosition(new java.awt.Point(0, 0));
                                        if(jScrollPane7 != null)
                                        jScrollPane7.getViewport().setViewPosition(new java.awt.Point(0, 0));
                                        jPanel25.repaint();
                                        jPanel25.validate();
                                    }
                                }
                            });
                            gridBagConstraints = new java.awt.GridBagConstraints();
                            gridBagConstraints.gridx = i * 2 + 1;
                            gridBagConstraints.gridy = 0;
                            jPanel40.add(rbArtists[i], gridBagConstraints);

                            rbSpaceBetween[i] = new javax.swing.JPanel();
                            rbSpaceBetween[i].setMinimumSize(new java.awt.Dimension(100, 10));
                            rbSpaceBetween[i].setPreferredSize(new java.awt.Dimension(100, 10));
                            rbSpaceBetween[i].setBackground(new java.awt.Color(0, 51, 51));
                            gridBagConstraints = new java.awt.GridBagConstraints();
                            gridBagConstraints.gridx = (i + 1) * 2;
                            gridBagConstraints.gridy = 0;
                            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                            jPanel40.add(rbSpaceBetween[i], gridBagConstraints);

                            rbArtistsNames[i] = new javax.swing.JLabel();
                            rbArtistsNames[i].setText(out1.getString(1));
                            rbArtistsNames[i].setForeground(Color.WHITE);
                            rbArtistsNames[i].setFont(new Font("Century Gothic", 1, 12));
                            gridBagConstraints = new java.awt.GridBagConstraints();
                            gridBagConstraints.gridx = i * 2 + 1;
                            gridBagConstraints.gridy = 1;
                            jPanel40.add(rbArtistsNames[i], gridBagConstraints);
                        }
                    }
                    catch(SQLException e)
                    {
                        System.out.println(e.getMessage());
                    }

                    jScrollPane2.setViewportView(jPanel26);

                    jPanel34.add(jScrollPane2, java.awt.BorderLayout.CENTER);

                    jPanel25.add(jPanel34, "card4");

                    jPanel42.setLayout(new java.awt.BorderLayout());

                    jScrollPane4.setBorder(null);
                    jScrollPane4.setMinimumSize(new java.awt.Dimension(100, 700));
                    jScrollPane4.setPreferredSize(new java.awt.Dimension(100, 700));

                    jPanel43.setBackground(new java.awt.Color(255, 204, 0));
                    jPanel43.setMinimumSize(new java.awt.Dimension(1300, 700));
                    jPanel43.setPreferredSize(new java.awt.Dimension(1300, 700));
                    jPanel43.setRequestFocusEnabled(false);
                    jPanel43.setLayout(new java.awt.GridBagLayout());

                    jPanel44.setBackground(new java.awt.Color(23, 17, 17));
                    jPanel44.setMinimumSize(new java.awt.Dimension(1300, 400));
                    jPanel44.setPreferredSize(new java.awt.Dimension(1300, 400));
                    jPanel44.setLayout(new java.awt.GridBagLayout());
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 0;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel44.add(jLabel21, gridBagConstraints);

                    jPanel45.setBackground(new java.awt.Color(23, 17, 17));
                    jPanel45.setMinimumSize(new java.awt.Dimension(20, 0));
                    jPanel45.setPreferredSize(new java.awt.Dimension(20, 300));

                    javax.swing.GroupLayout jPanel45Layout = new javax.swing.GroupLayout(jPanel45);
                    jPanel45.setLayout(jPanel45Layout);
                    jPanel45Layout.setHorizontalGroup(
                        jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 20, Short.MAX_VALUE)
                    );
                    jPanel45Layout.setVerticalGroup(
                        jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 300, Short.MAX_VALUE)
                    );

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 1;
                    gridBagConstraints.gridy = 0;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel44.add(jPanel45, gridBagConstraints);

                    jPanel46.setBackground(new java.awt.Color(23, 17, 17));
                    jPanel46.setMinimumSize(new java.awt.Dimension(500, 300));
                    jPanel46.setName(""); // NOI18N
                    jPanel46.setPreferredSize(new java.awt.Dimension(500, 300));
                    jPanel46.setLayout(new java.awt.GridBagLayout());

                    jLabel22.setBackground(new java.awt.Color(255, 102, 0));
                    jLabel22.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
                    jLabel22.setForeground(new java.awt.Color(255, 255, 255));
                    jLabel22.setText("jLabel22");
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 0;
                    jPanel46.add(jLabel22, gridBagConstraints);

                    jLabel23.setBackground(new java.awt.Color(255, 255, 255));
                    jLabel23.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
                    jLabel23.setForeground(new java.awt.Color(255, 255, 255));
                    jLabel23.setText("jLabel23");
                    jLabel23.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                            jLabel23MouseClicked(evt);
                        }
                        public void mouseEntered(java.awt.event.MouseEvent evt) {
                            jLabel23MouseEntered(evt);
                        }
                        public void mouseExited(java.awt.event.MouseEvent evt) {
                            jLabel23MouseExited(evt);
                        }
                    });
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 1;
                    jPanel46.add(jLabel23, gridBagConstraints);

                    jPanel47.setBackground(new java.awt.Color(23, 17, 17));

                    javax.swing.GroupLayout jPanel47Layout = new javax.swing.GroupLayout(jPanel47);
                    jPanel47.setLayout(jPanel47Layout);
                    jPanel47Layout.setHorizontalGroup(
                        jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 145, Short.MAX_VALUE)
                    );
                    jPanel47Layout.setVerticalGroup(
                        jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 100, Short.MAX_VALUE)
                    );

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 2;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel46.add(jPanel47, gridBagConstraints);

                    jButton6.setBackground(new java.awt.Color(255, 255, 255));
                    jButton6.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
                    jButton6.setText("jButton6");
                    jButton6.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                            jButton6MouseClicked(evt);
                        }
                    });
                    jButton6.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            jButton6ActionPerformed(evt);
                        }
                    });
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 3;
                    jPanel46.add(jButton6, gridBagConstraints);

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 2;
                    gridBagConstraints.gridy = 0;
                    jPanel44.add(jPanel46, gridBagConstraints);

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 0;
                    jPanel43.add(jPanel44, gridBagConstraints);

                    jPanel48.setBackground(new java.awt.Color(255, 255, 255));
                    jPanel48.setMinimumSize(new java.awt.Dimension(100, 30));
                    jPanel48.setPreferredSize(new java.awt.Dimension(1300, 30));
                    jPanel48.setLayout(new java.awt.GridBagLayout());

                    nowPlayingLabel.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
                    nowPlayingLabel.setForeground(new java.awt.Color(23, 17, 17));
                    nowPlayingLabel.setText("");
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 0;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel48.add(nowPlayingLabel, gridBagConstraints);

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 1;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel43.add(jPanel48, gridBagConstraints);

                    jScrollPane6.setBorder(null);
                    jScrollPane6.setMinimumSize(new java.awt.Dimension(23, 200));
                    jScrollPane6.setName(""); // NOI18N
                    jScrollPane6.setPreferredSize(new java.awt.Dimension(47, 200));

                    jList2.setBackground(new java.awt.Color(23, 17, 17));
                    jList2.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
                    jList2.setForeground(new java.awt.Color(255, 255, 255));
                    jList2.setModel(new javax.swing.AbstractListModel<String>() {
                        String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
                        public int getSize() { return strings.length; }
                        public String getElementAt(int i) { return strings[i]; }
                    });
                    jList2.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
                    jList2.setSelectionBackground(new java.awt.Color(255, 204, 0));
                    jList2.setSelectionForeground(new java.awt.Color(0, 0, 0));
                    jList2.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
                        public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                            jList2ValueChanged(evt);
                        }
                    });
                    jScrollPane6.setViewportView(jList2);

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 2;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel43.add(jScrollPane6, gridBagConstraints);

                    jPanel49.setBackground(new java.awt.Color(255, 204, 0));
                    jPanel49.setMinimumSize(new java.awt.Dimension(0, 20));
                    jPanel49.setPreferredSize(new java.awt.Dimension(1300, 20));

                    javax.swing.GroupLayout jPanel49Layout = new javax.swing.GroupLayout(jPanel49);
                    jPanel49.setLayout(jPanel49Layout);
                    jPanel49Layout.setHorizontalGroup(
                        jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 1300, Short.MAX_VALUE)
                    );
                    jPanel49Layout.setVerticalGroup(
                        jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 20, Short.MAX_VALUE)
                    );

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 3;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel43.add(jPanel49, gridBagConstraints);

                    jScrollPane4.setViewportView(jPanel43);

                    jPanel42.add(jScrollPane4, java.awt.BorderLayout.CENTER);

                    jPanel25.add(jPanel42, "card4");

                    jPanel50.setLayout(new java.awt.BorderLayout());

                    jScrollPane5.setBackground(new java.awt.Color(255, 204, 0));
                    jScrollPane5.setBorder(null);
                    jScrollPane5.setMinimumSize(new java.awt.Dimension(100, 700));
                    jScrollPane5.setPreferredSize(new java.awt.Dimension(100, 700));

                    jPanel51.setBackground(new java.awt.Color(255, 204, 0));
                    jPanel51.setMinimumSize(new java.awt.Dimension(1300, 1100));
                    jPanel51.setPreferredSize(new java.awt.Dimension(1300, 1100));
                    jPanel51.setLayout(new java.awt.GridBagLayout());
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 0;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel51.add(jLabel24, gridBagConstraints);

                    jPanel52.setBackground(new java.awt.Color(255, 255, 255));
                    jPanel52.setLayout(new java.awt.GridBagLayout());

                    jPanel56.setBackground(new java.awt.Color(255, 255, 255));
                    jPanel56.setMinimumSize(new java.awt.Dimension(0, 20));
                    jPanel56.setPreferredSize(new java.awt.Dimension(100, 20));

                    javax.swing.GroupLayout jPanel56Layout = new javax.swing.GroupLayout(jPanel56);
                    jPanel56.setLayout(jPanel56Layout);
                    jPanel56Layout.setHorizontalGroup(
                        jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 100, Short.MAX_VALUE)
                    );
                    jPanel56Layout.setVerticalGroup(
                        jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 20, Short.MAX_VALUE)
                    );

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 4;
                    jPanel52.add(jPanel56, gridBagConstraints);

                    jLabel25.setFont(new java.awt.Font("Century Gothic", 1, 45)); // NOI18N
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 1;
                    jPanel52.add(jLabel25, gridBagConstraints);

                    jPanel58.setBackground(new java.awt.Color(255, 255, 255));
                    jPanel58.setMinimumSize(new java.awt.Dimension(0, 20));
                    jPanel58.setPreferredSize(new java.awt.Dimension(100, 20));

                    javax.swing.GroupLayout jPanel58Layout = new javax.swing.GroupLayout(jPanel58);
                    jPanel58.setLayout(jPanel58Layout);
                    jPanel58Layout.setHorizontalGroup(
                        jPanel58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 100, Short.MAX_VALUE)
                    );
                    jPanel58Layout.setVerticalGroup(
                        jPanel58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 20, Short.MAX_VALUE)
                    );

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 2;
                    jPanel52.add(jPanel58, gridBagConstraints);

                    jScrollPane7.setBackground(new java.awt.Color(255, 102, 0));
                    jScrollPane7.setBorder(null);
                    jScrollPane7.setMinimumSize(new java.awt.Dimension(1300, 150));
                    jScrollPane7.setPreferredSize(new java.awt.Dimension(1300, 150));

                    jTextPane1.setEditable(false);
                    /*Color backgroundColor = new Color(255, 102, 0);
                    SimpleAttributeSet background = new SimpleAttributeSet();
                    StyleConstants.setBackground(background, backgroundColor);
                    jTextPane1.getStyledDocument().setParagraphAttributes(0,
                        jTextPane1.getDocument().getLength(), background, false);*/
                    jTextPane1.setBorder(null);
                    /*jTextPane1.setBackground(new java.awt.Color(255, 102, 0));*/
                    jTextPane1.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
                    jTextPane1.setForeground(new java.awt.Color(102, 102, 102));
                    jTextPane1.setMinimumSize(new java.awt.Dimension(0, 0));
                    jTextPane1.setPreferredSize(new java.awt.Dimension(0, 0));
                    jScrollPane7.setViewportView(jTextPane1);

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 3;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel52.add(jScrollPane7, gridBagConstraints);

                    jPanel57.setBackground(new java.awt.Color(255, 255, 255));
                    jPanel57.setPreferredSize(new java.awt.Dimension(1300, 10));

                    javax.swing.GroupLayout jPanel57Layout = new javax.swing.GroupLayout(jPanel57);
                    jPanel57.setLayout(jPanel57Layout);
                    jPanel57Layout.setHorizontalGroup(
                        jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 1300, Short.MAX_VALUE)
                    );
                    jPanel57Layout.setVerticalGroup(
                        jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 10, Short.MAX_VALUE)
                    );

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 0;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel52.add(jPanel57, gridBagConstraints);

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 2;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel51.add(jPanel52, gridBagConstraints);

                    jPanel54.setBackground(new java.awt.Color(255, 204, 0));

                    javax.swing.GroupLayout jPanel54Layout = new javax.swing.GroupLayout(jPanel54);
                    jPanel54.setLayout(jPanel54Layout);
                    jPanel54Layout.setHorizontalGroup(
                        jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
                    );
                    jPanel54Layout.setVerticalGroup(
                        jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
                    );

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 3;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel51.add(jPanel54, gridBagConstraints);

                    jPanel53.setBackground(new java.awt.Color(23, 17, 17));
                    jPanel53.setMinimumSize(new java.awt.Dimension(1300, 50));
                    jPanel53.setPreferredSize(new java.awt.Dimension(1300, 50));
                    jPanel53.setLayout(new java.awt.GridBagLayout());

                    jLabel27.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
                    jLabel27.setForeground(new java.awt.Color(255, 255, 255));
                    jLabel27.setText("Albums");
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 0;
                    jPanel53.add(jLabel27, gridBagConstraints);

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 4;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
                    jPanel51.add(jPanel53, gridBagConstraints);

                    jScrollPane5.setViewportView(jPanel51);

                    jPanel50.add(jScrollPane5, java.awt.BorderLayout.CENTER);

                    jPanel25.add(jPanel50, "card5");

                    jPanel59.setBackground(new java.awt.Color(255, 204, 0));
                    jPanel59.setMinimumSize(new java.awt.Dimension(640, 480));
                    jPanel59.setPreferredSize(new java.awt.Dimension(640, 480));
                    jPanel59.setLayout(new java.awt.BorderLayout());

                    jScrollPane8.setBorder(null);
                    jScrollPane8.setMinimumSize(new java.awt.Dimension(1000, 738));
                    jScrollPane8.setPreferredSize(new java.awt.Dimension(1000, 738));

                    jPanel60.setBackground(new java.awt.Color(255, 204, 0));
                    jPanel60.setMinimumSize(new java.awt.Dimension(1100, 600));
                    jPanel60.setPreferredSize(new java.awt.Dimension(1100, 600));
                    jPanel60.setLayout(new java.awt.GridBagLayout());

                    albumsToBuy.setBackground(new java.awt.Color(0, 0, 0));
                    albumsToBuy.setMaximumSize(new java.awt.Dimension(619, 380));
                    albumsToBuy.setMinimumSize(new java.awt.Dimension(619, 380));
                    albumsToBuy.setPreferredSize(new java.awt.Dimension(619, 380));
                    albumsToBuy.setLayout(new java.awt.GridBagLayout());

                    jScrollPane10.setBorder(null);
                    jScrollPane10.setMaximumSize(new java.awt.Dimension(619, 350));
                    jScrollPane10.setMinimumSize(new java.awt.Dimension(619, 350));
                    jScrollPane10.setPreferredSize(new java.awt.Dimension(619, 350));

                    jList3.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
                    jList3.setModel(new javax.swing.AbstractListModel<String>() {
                        String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
                        public int getSize() { return strings.length; }
                        public String getElementAt(int i) { return strings[i]; }
                    });
                    jList3.setMaximumSize(new java.awt.Dimension(600, 400));
                    jList3.setMinimumSize(new java.awt.Dimension(600, 400));
                    jList3.setName(""); // NOI18N
                    jList3.setPreferredSize(new java.awt.Dimension(600, 400));
                    jList3.setSelectionBackground(new java.awt.Color(255, 204, 0));
                    jScrollPane10.setViewportView(jList3);

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 2;
                    gridBagConstraints.gridy = 2;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    albumsToBuy.add(jScrollPane10, gridBagConstraints);

                    jPanel71.setBackground(new java.awt.Color(0, 0, 0));
                    jPanel71.setMaximumSize(new java.awt.Dimension(619, 30));
                    jPanel71.setMinimumSize(new java.awt.Dimension(619, 30));
                    jPanel71.setPreferredSize(new java.awt.Dimension(619, 30));
                    jPanel71.setRequestFocusEnabled(false);
                    jPanel71.setLayout(new java.awt.GridBagLayout());

                    jLabel26.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
                    jLabel26.setForeground(new java.awt.Color(255, 204, 0));
                    jLabel26.setText("You have selected these items:");
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 0;
                    jPanel71.add(jLabel26, gridBagConstraints);

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 2;
                    gridBagConstraints.gridy = 1;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    albumsToBuy.add(jPanel71, gridBagConstraints);

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 2;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel60.add(albumsToBuy, gridBagConstraints);

                    jPanel61.setBackground(new java.awt.Color(255, 204, 0));
                    jPanel61.setMinimumSize(new java.awt.Dimension(100, 294));

                    javax.swing.GroupLayout jPanel61Layout = new javax.swing.GroupLayout(jPanel61);
                    jPanel61.setLayout(jPanel61Layout);
                    jPanel61Layout.setHorizontalGroup(
                        jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 100, Short.MAX_VALUE)
                    );
                    jPanel61Layout.setVerticalGroup(
                        jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 380, Short.MAX_VALUE)
                    );

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 1;
                    gridBagConstraints.gridy = 2;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel60.add(jPanel61, gridBagConstraints);

                    jPanel62.setBackground(new java.awt.Color(255, 255, 255));
                    jPanel62.setMinimumSize(new java.awt.Dimension(300, 50));
                    jPanel62.setPreferredSize(new java.awt.Dimension(300, 50));
                    jPanel62.setRequestFocusEnabled(false);
                    jPanel62.setLayout(new java.awt.GridBagLayout());

                    jLabel30.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
                    jLabel30.setText("Sub total");
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 1;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel62.add(jLabel30, gridBagConstraints);

                    jLabel31.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
                    jLabel31.setText("Shipping");
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 3;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel62.add(jLabel31, gridBagConstraints);

                    subTotalLabel.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
                    subTotalLabel.setText("0.0 RON");
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 1;
                    gridBagConstraints.gridy = 1;
                    jPanel62.add(subTotalLabel, gridBagConstraints);

                    jLabel33.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
                    jLabel33.setText("20.0 RON");
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 1;
                    gridBagConstraints.gridy = 3;
                    jPanel62.add(jLabel33, gridBagConstraints);

                    jPanel66.setBackground(new java.awt.Color(255, 255, 255));
                    jPanel66.setMinimumSize(new java.awt.Dimension(100, 15));
                    jPanel66.setName(""); // NOI18N

                    javax.swing.GroupLayout jPanel66Layout = new javax.swing.GroupLayout(jPanel66);
                    jPanel66.setLayout(jPanel66Layout);
                    jPanel66Layout.setHorizontalGroup(
                        jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 100, Short.MAX_VALUE)
                    );
                    jPanel66Layout.setVerticalGroup(
                        jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 15, Short.MAX_VALUE)
                    );

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 4;
                    jPanel62.add(jPanel66, gridBagConstraints);

                    jPanel64.setBackground(new java.awt.Color(255, 255, 255));
                    jPanel64.setMinimumSize(new java.awt.Dimension(100, 15));
                    jPanel64.setName(""); // NOI18N
                    jPanel64.setPreferredSize(new java.awt.Dimension(100, 15));

                    javax.swing.GroupLayout jPanel64Layout = new javax.swing.GroupLayout(jPanel64);
                    jPanel64.setLayout(jPanel64Layout);
                    jPanel64Layout.setHorizontalGroup(
                        jPanel64Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 100, Short.MAX_VALUE)
                    );
                    jPanel64Layout.setVerticalGroup(
                        jPanel64Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 15, Short.MAX_VALUE)
                    );

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 2;
                    jPanel62.add(jPanel64, gridBagConstraints);

                    jLabel34.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
                    jLabel34.setText("Total");
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 5;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel62.add(jLabel34, gridBagConstraints);

                    totalLabel.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 1;
                    gridBagConstraints.gridy = 5;
                    jPanel62.add(totalLabel, gridBagConstraints);

                    jPanel67.setBackground(new java.awt.Color(255, 255, 255));
                    jPanel67.setMinimumSize(new java.awt.Dimension(100, 15));
                    jPanel67.setName(""); // NOI18N
                    jPanel67.setPreferredSize(new java.awt.Dimension(100, 35));

                    javax.swing.GroupLayout jPanel67Layout = new javax.swing.GroupLayout(jPanel67);
                    jPanel67.setLayout(jPanel67Layout);
                    jPanel67Layout.setHorizontalGroup(
                        jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 100, Short.MAX_VALUE)
                    );
                    jPanel67Layout.setVerticalGroup(
                        jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 35, Short.MAX_VALUE)
                    );

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 6;
                    jPanel62.add(jPanel67, gridBagConstraints);

                    jLabel36.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
                    jLabel36.setText("Payment");
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 7;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel62.add(jLabel36, gridBagConstraints);
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 9;
                    jPanel62.add(jLabel37, gridBagConstraints);

                    jButton7.setBackground(new java.awt.Color(255, 204, 0));
                    jButton7.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
                    jButton7.setText("Place Order");
                    jButton7.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                            jButton7MouseClicked(evt);
                        }
                    });
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 12;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel62.add(jButton7, gridBagConstraints);

                    jPanel68.setBackground(new java.awt.Color(255, 255, 255));
                    jPanel68.setMinimumSize(new java.awt.Dimension(100, 15));
                    jPanel68.setName(""); // NOI18N

                    javax.swing.GroupLayout jPanel68Layout = new javax.swing.GroupLayout(jPanel68);
                    jPanel68.setLayout(jPanel68Layout);
                    jPanel68Layout.setHorizontalGroup(
                        jPanel68Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 100, Short.MAX_VALUE)
                    );
                    jPanel68Layout.setVerticalGroup(
                        jPanel68Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 15, Short.MAX_VALUE)
                    );

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 10;
                    jPanel62.add(jPanel68, gridBagConstraints);

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 2;
                    gridBagConstraints.gridy = 2;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel60.add(jPanel62, gridBagConstraints);

                    jPanel63.setBackground(new java.awt.Color(255, 255, 255));
                    jPanel63.setMinimumSize(new java.awt.Dimension(300, 70));
                    jPanel63.setPreferredSize(new java.awt.Dimension(300, 70));
                    jPanel63.setLayout(new java.awt.BorderLayout());

                    jLabel29.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
                    jLabel29.setText("  Cart summary");
                    jPanel63.add(jLabel29, java.awt.BorderLayout.CENTER);

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 2;
                    gridBagConstraints.gridy = 1;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel60.add(jPanel63, gridBagConstraints);

                    jPanel65.setBackground(new java.awt.Color(255, 204, 0));
                    jPanel65.setMinimumSize(new java.awt.Dimension(619, 100));
                    jPanel65.setLayout(new java.awt.GridBagLayout());

                    jLabel28.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
                    jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
                    jLabel28.setText("Shopping Cart");
                    jLabel28.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 0;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel65.add(jLabel28, gridBagConstraints);

                    jPanel69.setBackground(new java.awt.Color(255, 204, 0));
                    jPanel69.setMinimumSize(new java.awt.Dimension(250, 31));
                    jPanel69.setPreferredSize(new java.awt.Dimension(250, 31));

                    javax.swing.GroupLayout jPanel69Layout = new javax.swing.GroupLayout(jPanel69);
                    jPanel69.setLayout(jPanel69Layout);
                    jPanel69Layout.setHorizontalGroup(
                        jPanel69Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 250, Short.MAX_VALUE)
                    );
                    jPanel69Layout.setVerticalGroup(
                        jPanel69Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 31, Short.MAX_VALUE)
                    );

                    jPanel65.add(jPanel69, new java.awt.GridBagConstraints());

                    jPanel70.setBackground(new java.awt.Color(255, 204, 0));
                    jPanel70.setMinimumSize(new java.awt.Dimension(100, 40));
                    jPanel70.setPreferredSize(new java.awt.Dimension(100, 40));
                    jPanel70.setLayout(new java.awt.GridBagLayout());

                    jButton8.setBackground(new java.awt.Color(0, 0, 0));
                    jButton8.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
                    jButton8.setForeground(new java.awt.Color(255, 204, 0));
                    jButton8.setText("Remove!");
                    jButton8.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                            jButton8MouseClicked(evt);
                        }
                    });
                    jButton8.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            jButton8ActionPerformed(evt);
                        }
                    });
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 0;
                    jPanel70.add(jButton8, gridBagConstraints);

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 3;
                    gridBagConstraints.gridy = 0;
                    jPanel65.add(jPanel70, gridBagConstraints);

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 1;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel60.add(jPanel65, gridBagConstraints);

                    jScrollPane8.setViewportView(jPanel60);

                    jPanel59.add(jScrollPane8, java.awt.BorderLayout.CENTER);

                    jPanel25.add(jPanel59, "card4");

                    jPanel72.setBackground(new java.awt.Color(255, 204, 0));
                    jPanel72.setMinimumSize(new java.awt.Dimension(640, 800));
                    jPanel72.setPreferredSize(new java.awt.Dimension(640, 800));
                    jPanel72.setLayout(new java.awt.BorderLayout());

                    jScrollPane9.setBorder(null);
                    jScrollPane9.setMinimumSize(new java.awt.Dimension(1200, 900));
                    jScrollPane9.setPreferredSize(new java.awt.Dimension(1200, 900));

                    jPanel73.setBackground(new java.awt.Color(255, 204, 0));
                    jPanel73.setMinimumSize(new java.awt.Dimension(1300, 900));
                    jPanel73.setPreferredSize(new java.awt.Dimension(1300, 900));
                    jPanel73.setLayout(new java.awt.CardLayout());

                    jPanel74.setBackground(new java.awt.Color(255, 204, 0));
                    jPanel74.setMinimumSize(new java.awt.Dimension(1200, 700));
                    jPanel74.setPreferredSize(new java.awt.Dimension(1300, 700));
                    jPanel74.setLayout(new java.awt.GridBagLayout());

                    jPanel76.setBackground(new java.awt.Color(255, 255, 255));
                    jPanel76.setMinimumSize(new java.awt.Dimension(600, 630));
                    jPanel76.setPreferredSize(new java.awt.Dimension(600, 630));
                    jPanel76.setLayout(new java.awt.GridBagLayout());

                    jLabel32.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
                    jLabel32.setText("Create A New Account");
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 0;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel76.add(jLabel32, gridBagConstraints);

                    jLabel35.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
                    jLabel35.setText("It's free!");
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 1;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel76.add(jLabel35, gridBagConstraints);

                    jPanel79.setBackground(new java.awt.Color(255, 255, 255));
                    jPanel79.setPreferredSize(new java.awt.Dimension(550, 20));

                    javax.swing.GroupLayout jPanel79Layout = new javax.swing.GroupLayout(jPanel79);
                    jPanel79.setLayout(jPanel79Layout);
                    jPanel79Layout.setHorizontalGroup(
                        jPanel79Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 550, Short.MAX_VALUE)
                    );
                    jPanel79Layout.setVerticalGroup(
                        jPanel79Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 20, Short.MAX_VALUE)
                    );

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 2;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel76.add(jPanel79, gridBagConstraints);

                    jPanel80.setBackground(new java.awt.Color(255, 255, 255));
                    jPanel80.setMinimumSize(new java.awt.Dimension(550, 100));
                    jPanel80.setLayout(new java.awt.GridBagLayout());

                    jTextField2.setFont(new java.awt.Font("Century Gothic", 2, 12)); // NOI18N
                    jTextField2.setText("Name *");
                    jTextField2.setBorder(null);
                    jTextField2.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            jTextField2ActionPerformed(evt);
                        }
                    });
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 0;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel80.add(jTextField2, gridBagConstraints);

                    jLabel38.setIcon(new javax.swing.ImageIcon("C:\\JavaProiect\\Proiectv2\\OtherPhotos\\Horizontal-Line-Transparent-Background.png"));
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 1;
                    jPanel80.add(jLabel38, gridBagConstraints);

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 4;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel76.add(jPanel80, gridBagConstraints);

                    jPanel82.setBackground(new java.awt.Color(255, 255, 255));
                    jPanel82.setPreferredSize(new java.awt.Dimension(550, 20));

                    javax.swing.GroupLayout jPanel82Layout = new javax.swing.GroupLayout(jPanel82);
                    jPanel82.setLayout(jPanel82Layout);
                    jPanel82Layout.setHorizontalGroup(
                        jPanel82Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 550, Short.MAX_VALUE)
                    );
                    jPanel82Layout.setVerticalGroup(
                        jPanel82Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 20, Short.MAX_VALUE)
                    );

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 5;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel76.add(jPanel82, gridBagConstraints);

                    jPanel81.setBackground(new java.awt.Color(255, 255, 255));
                    jPanel81.setMinimumSize(new java.awt.Dimension(550, 100));
                    jPanel81.setLayout(new java.awt.GridBagLayout());

                    jTextField3.setFont(new java.awt.Font("Century Gothic", 2, 12)); // NOI18N
                    jTextField3.setText("Username *");
                    jTextField3.setBorder(null);
                    jTextField3.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            jTextField3ActionPerformed(evt);
                        }
                    });
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 0;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel81.add(jTextField3, gridBagConstraints);

                    jLabel39.setIcon(new javax.swing.ImageIcon("C:\\JavaProiect\\Proiectv2\\OtherPhotos\\Horizontal-Line-Transparent-Background.png"));
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 1;
                    jPanel81.add(jLabel39, gridBagConstraints);

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 6;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel76.add(jPanel81, gridBagConstraints);

                    jPanel83.setBackground(new java.awt.Color(255, 255, 255));
                    jPanel83.setPreferredSize(new java.awt.Dimension(550, 20));

                    javax.swing.GroupLayout jPanel83Layout = new javax.swing.GroupLayout(jPanel83);
                    jPanel83.setLayout(jPanel83Layout);
                    jPanel83Layout.setHorizontalGroup(
                        jPanel83Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 550, Short.MAX_VALUE)
                    );
                    jPanel83Layout.setVerticalGroup(
                        jPanel83Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 20, Short.MAX_VALUE)
                    );

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 7;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel76.add(jPanel83, gridBagConstraints);

                    jPanel84.setBackground(new java.awt.Color(255, 255, 255));
                    jPanel84.setMinimumSize(new java.awt.Dimension(550, 100));
                    jPanel84.setLayout(new java.awt.GridBagLayout());

                    jTextField4.setFont(new java.awt.Font("Century Gothic", 2, 12)); // NOI18N
                    jTextField4.setText("Address *");
                    jTextField4.setBorder(null);
                    jTextField4.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            jTextField4ActionPerformed(evt);
                        }
                    });
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 0;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel84.add(jTextField4, gridBagConstraints);

                    jLabel40.setIcon(new javax.swing.ImageIcon("C:\\JavaProiect\\Proiectv2\\OtherPhotos\\Horizontal-Line-Transparent-Background.png"));
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 1;
                    jPanel84.add(jLabel40, gridBagConstraints);

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 8;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel76.add(jPanel84, gridBagConstraints);

                    jPanel85.setBackground(new java.awt.Color(255, 255, 255));
                    jPanel85.setPreferredSize(new java.awt.Dimension(550, 20));

                    javax.swing.GroupLayout jPanel85Layout = new javax.swing.GroupLayout(jPanel85);
                    jPanel85.setLayout(jPanel85Layout);
                    jPanel85Layout.setHorizontalGroup(
                        jPanel85Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 550, Short.MAX_VALUE)
                    );
                    jPanel85Layout.setVerticalGroup(
                        jPanel85Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 20, Short.MAX_VALUE)
                    );

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 9;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel76.add(jPanel85, gridBagConstraints);

                    jPanel86.setBackground(new java.awt.Color(255, 255, 255));
                    jPanel86.setMinimumSize(new java.awt.Dimension(550, 100));
                    jPanel86.setLayout(new java.awt.GridBagLayout());

                    jTextField5.setFont(new java.awt.Font("Century Gothic", 2, 12)); // NOI18N
                    jTextField5.setText("Telephone *");
                    jTextField5.setBorder(null);
                    jTextField5.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            jTextField5ActionPerformed(evt);
                        }
                    });
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 0;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel86.add(jTextField5, gridBagConstraints);

                    jLabel41.setIcon(new javax.swing.ImageIcon("C:\\JavaProiect\\Proiectv2\\OtherPhotos\\Horizontal-Line-Transparent-Background.png"));
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 1;
                    jPanel86.add(jLabel41, gridBagConstraints);

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 10;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel76.add(jPanel86, gridBagConstraints);

                    jPanel87.setBackground(new java.awt.Color(255, 255, 255));
                    jPanel87.setPreferredSize(new java.awt.Dimension(550, 20));

                    javax.swing.GroupLayout jPanel87Layout = new javax.swing.GroupLayout(jPanel87);
                    jPanel87.setLayout(jPanel87Layout);
                    jPanel87Layout.setHorizontalGroup(
                        jPanel87Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 550, Short.MAX_VALUE)
                    );
                    jPanel87Layout.setVerticalGroup(
                        jPanel87Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 20, Short.MAX_VALUE)
                    );

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 11;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel76.add(jPanel87, gridBagConstraints);

                    jLabel42.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
                    jLabel42.setText("Payment");
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 12;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel76.add(jLabel42, gridBagConstraints);

                    jPanel88.setBackground(new java.awt.Color(255, 255, 255));
                    jPanel88.setPreferredSize(new java.awt.Dimension(550, 60));
                    jPanel88.setLayout(new java.awt.GridBagLayout());

                    jLabel43.setIcon(new javax.swing.ImageIcon("C:\\JavaProiect\\Proiectv2\\OtherPhotos\\MasterCard_Logo.svg.png")); // NOI18N
                    jLabel43.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                            jLabel43MouseClicked(evt);
                        }
                    });
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 2;
                    gridBagConstraints.gridy = 0;
                    jPanel88.add(jLabel43, gridBagConstraints);

                    jLabel44.setIcon(new javax.swing.ImageIcon("C:\\JavaProiect\\Proiectv2\\OtherPhotos\\visa.jpg")); // NOI18N
                    jLabel44.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                            jLabel44MouseClicked(evt);
                        }
                    });
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 3;
                    gridBagConstraints.gridy = 0;
                    jPanel88.add(jLabel44, gridBagConstraints);

                    jPanel89.setBackground(new java.awt.Color(255, 255, 255));
                    jPanel89.setMinimumSize(new java.awt.Dimension(210, 10));
                    jPanel89.setPreferredSize(new java.awt.Dimension(210, 10));

                    javax.swing.GroupLayout jPanel89Layout = new javax.swing.GroupLayout(jPanel89);
                    jPanel89.setLayout(jPanel89Layout);
                    jPanel89Layout.setHorizontalGroup(
                        jPanel89Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 210, Short.MAX_VALUE)
                    );
                    jPanel89Layout.setVerticalGroup(
                        jPanel89Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 10, Short.MAX_VALUE)
                    );

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 1;
                    gridBagConstraints.gridy = 0;
                    jPanel88.add(jPanel89, gridBagConstraints);

                    jPanel90.setBackground(new java.awt.Color(255, 255, 255));
                    jPanel90.setMinimumSize(new java.awt.Dimension(170, 30));
                    jPanel90.setPreferredSize(new java.awt.Dimension(170, 30));
                    jPanel90.setLayout(new java.awt.GridBagLayout());

                    jLabel45.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
                    jLabel45.setText("Card type ");
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 0;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel90.add(jLabel45, gridBagConstraints);

                    cardTypeLabel.setText("Choose the type of your card");
                    cardTypeLabel.setMaximumSize(new java.awt.Dimension(170, 14));
                    cardTypeLabel.setMinimumSize(new java.awt.Dimension(0, 14));
                    cardTypeLabel.setName(""); // NOI18N
                    cardTypeLabel.setPreferredSize(new java.awt.Dimension(170, 14));
                    cardTypeLabel.setRequestFocusEnabled(false);
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 1;
                    jPanel90.add(cardTypeLabel, gridBagConstraints);

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 0;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel88.add(jPanel90, gridBagConstraints);

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 13;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel76.add(jPanel88, gridBagConstraints);

                    jPanel91.setBackground(new java.awt.Color(255, 255, 255));
                    jPanel91.setMinimumSize(new java.awt.Dimension(550, 100));
                    jPanel91.setLayout(new java.awt.GridBagLayout());

                    jLabel46.setFont(new java.awt.Font("Century Gothic", 2, 12)); // NOI18N
                    jLabel46.setText("Card Number  ");
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 2;
                    jPanel91.add(jLabel46, gridBagConstraints);

                    jTextField6.setMinimumSize(new java.awt.Dimension(150, 25));
                    jTextField6.setPreferredSize(new java.awt.Dimension(150, 25));
                    jTextField6.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            jTextField6ActionPerformed(evt);
                        }
                    });
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 1;
                    gridBagConstraints.gridy = 2;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel91.add(jTextField6, gridBagConstraints);

                    jPanel92.setBackground(new java.awt.Color(255, 255, 255));
                    jPanel92.setPreferredSize(new java.awt.Dimension(100, 10));

                    javax.swing.GroupLayout jPanel92Layout = new javax.swing.GroupLayout(jPanel92);
                    jPanel92.setLayout(jPanel92Layout);
                    jPanel92Layout.setHorizontalGroup(
                        jPanel92Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 100, Short.MAX_VALUE)
                    );
                    jPanel92Layout.setVerticalGroup(
                        jPanel92Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 10, Short.MAX_VALUE)
                    );

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 3;
                    jPanel91.add(jPanel92, gridBagConstraints);

                    jLabel47.setFont(new java.awt.Font("Century Gothic", 2, 11)); // NOI18N
                    jLabel47.setText("SecurityCode");
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 4;
                    jPanel91.add(jLabel47, gridBagConstraints);

                    jPasswordField1.setText("123");
                    jPasswordField1.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            jPasswordField1ActionPerformed(evt);
                        }
                    });
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 1;
                    gridBagConstraints.gridy = 4;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel91.add(jPasswordField1, gridBagConstraints);

                    jPanel93.setBackground(new java.awt.Color(255, 255, 255));
                    jPanel93.setPreferredSize(new java.awt.Dimension(100, 10));

                    javax.swing.GroupLayout jPanel93Layout = new javax.swing.GroupLayout(jPanel93);
                    jPanel93.setLayout(jPanel93Layout);
                    jPanel93Layout.setHorizontalGroup(
                        jPanel93Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 100, Short.MAX_VALUE)
                    );
                    jPanel93Layout.setVerticalGroup(
                        jPanel93Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 10, Short.MAX_VALUE)
                    );

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 5;
                    jPanel91.add(jPanel93, gridBagConstraints);

                    jPanel94.setBackground(new java.awt.Color(255, 255, 255));
                    jPanel94.setPreferredSize(new java.awt.Dimension(210, 40));
                    jPanel94.setLayout(new java.awt.GridBagLayout());

                    jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
                    jComboBox2.setMinimumSize(new java.awt.Dimension(70, 20));
                    jComboBox2.setPreferredSize(new java.awt.Dimension(70, 20));
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 0;
                    jPanel94.add(jComboBox2, gridBagConstraints);

                    jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
                    jComboBox3.setMinimumSize(new java.awt.Dimension(70, 20));
                    jComboBox3.setPreferredSize(new java.awt.Dimension(70, 20));
                    jComboBox3.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            jComboBox3ActionPerformed(evt);
                        }
                    });
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 1;
                    gridBagConstraints.gridy = 0;
                    jPanel94.add(jComboBox3, gridBagConstraints);

                    jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2019", "2020", "2021", "2022", "2023", "2024" }));
                    jComboBox4.setMinimumSize(new java.awt.Dimension(70, 20));
                    jComboBox4.setPreferredSize(new java.awt.Dimension(70, 20));
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 2;
                    gridBagConstraints.gridy = 0;
                    jPanel94.add(jComboBox4, gridBagConstraints);

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 1;
                    gridBagConstraints.gridy = 6;
                    jPanel91.add(jPanel94, gridBagConstraints);

                    jLabel48.setFont(new java.awt.Font("Century Gothic", 2, 11)); // NOI18N
                    jLabel48.setText("Expiration Date");
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 6;
                    jPanel91.add(jLabel48, gridBagConstraints);

                    jTextField7.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            jTextField7ActionPerformed(evt);
                        }
                    });
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 1;
                    gridBagConstraints.gridy = 0;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel91.add(jTextField7, gridBagConstraints);

                    jLabel49.setFont(new java.awt.Font("Century Gothic", 2, 12)); // NOI18N
                    jLabel49.setText("Bank");
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 0;
                    jPanel91.add(jLabel49, gridBagConstraints);

                    jPanel125.setBackground(new java.awt.Color(255, 255, 255));
                    jPanel125.setPreferredSize(new java.awt.Dimension(0, 10));

                    javax.swing.GroupLayout jPanel125Layout = new javax.swing.GroupLayout(jPanel125);
                    jPanel125.setLayout(jPanel125Layout);
                    jPanel125Layout.setHorizontalGroup(
                        jPanel125Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 100, Short.MAX_VALUE)
                    );
                    jPanel125Layout.setVerticalGroup(
                        jPanel125Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 10, Short.MAX_VALUE)
                    );

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 1;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel91.add(jPanel125, gridBagConstraints);

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 14;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel76.add(jPanel91, gridBagConstraints);

                    jPanel95.setBackground(new java.awt.Color(255, 255, 255));
                    jPanel95.setPreferredSize(new java.awt.Dimension(550, 20));

                    javax.swing.GroupLayout jPanel95Layout = new javax.swing.GroupLayout(jPanel95);
                    jPanel95.setLayout(jPanel95Layout);
                    jPanel95Layout.setHorizontalGroup(
                        jPanel95Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 550, Short.MAX_VALUE)
                    );
                    jPanel95Layout.setVerticalGroup(
                        jPanel95Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 20, Short.MAX_VALUE)
                    );

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 15;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel76.add(jPanel95, gridBagConstraints);

                    jPanel96.setBackground(new java.awt.Color(255, 255, 255));
                    jPanel96.setMinimumSize(new java.awt.Dimension(550, 100));
                    jPanel96.setLayout(new java.awt.GridBagLayout());
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 16;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel76.add(jPanel96, gridBagConstraints);

                    jPanel97.setBackground(new java.awt.Color(255, 255, 255));
                    jPanel97.setPreferredSize(new java.awt.Dimension(550, 20));

                    javax.swing.GroupLayout jPanel97Layout = new javax.swing.GroupLayout(jPanel97);
                    jPanel97.setLayout(jPanel97Layout);
                    jPanel97Layout.setHorizontalGroup(
                        jPanel97Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 550, Short.MAX_VALUE)
                    );
                    jPanel97Layout.setVerticalGroup(
                        jPanel97Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 20, Short.MAX_VALUE)
                    );

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 19;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel76.add(jPanel97, gridBagConstraints);

                    jPanel98.setBackground(new java.awt.Color(255, 255, 255));
                    jPanel98.setMinimumSize(new java.awt.Dimension(550, 100));
                    jPanel98.setLayout(new java.awt.GridBagLayout());

                    jLabel50.setIcon(new javax.swing.ImageIcon("C:\\JavaProiect\\Proiectv2\\OtherPhotos\\Horizontal-Line-Transparent-Background.png"));
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 1;
                    jPanel98.add(jLabel50, gridBagConstraints);

                    jPasswordField2.setFont(new java.awt.Font("Century Gothic", 2, 12)); // NOI18N
                    jPasswordField2.setText("Password");
                    jPasswordField2.setBorder(null);
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 0;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel98.add(jPasswordField2, gridBagConstraints);

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 18;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel76.add(jPanel98, gridBagConstraints);

                    jButton9.setBackground(new java.awt.Color(255, 204, 0));
                    jButton9.setFont(new java.awt.Font("Century Gothic", 1, 11)); // NOI18N
                    jButton9.setText("Sign up!");
                    jButton9.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                            jButton9MouseClicked(evt);
                        }
                    });
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 20;
                    jPanel76.add(jButton9, gridBagConstraints);

                    jPanel99.setBackground(new java.awt.Color(255, 255, 255));
                    jPanel99.setMinimumSize(new java.awt.Dimension(0, 20));
                    jPanel99.setPreferredSize(new java.awt.Dimension(0, 20));

                    javax.swing.GroupLayout jPanel99Layout = new javax.swing.GroupLayout(jPanel99);
                    jPanel99.setLayout(jPanel99Layout);
                    jPanel99Layout.setHorizontalGroup(
                        jPanel99Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
                    );
                    jPanel99Layout.setVerticalGroup(
                        jPanel99Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 20, Short.MAX_VALUE)
                    );

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 17;
                    jPanel76.add(jPanel99, gridBagConstraints);

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 2;
                    jPanel74.add(jPanel76, gridBagConstraints);

                    jPanel77.setBackground(new java.awt.Color(255, 204, 0));
                    jPanel77.setMinimumSize(new java.awt.Dimension(100, 0));
                    jPanel77.setPreferredSize(new java.awt.Dimension(100, 0));

                    javax.swing.GroupLayout jPanel77Layout = new javax.swing.GroupLayout(jPanel77);
                    jPanel77.setLayout(jPanel77Layout);
                    jPanel77Layout.setHorizontalGroup(
                        jPanel77Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 100, Short.MAX_VALUE)
                    );
                    jPanel77Layout.setVerticalGroup(
                        jPanel77Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 630, Short.MAX_VALUE)
                    );

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 1;
                    gridBagConstraints.gridy = 2;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel74.add(jPanel77, gridBagConstraints);

                    jPanel78.setBackground(new java.awt.Color(255, 255, 255));
                    jPanel78.setMinimumSize(new java.awt.Dimension(600, 580));
                    jPanel78.setPreferredSize(new java.awt.Dimension(600, 580));
                    jPanel78.setLayout(new java.awt.GridBagLayout());

                    jLabel51.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
                    jLabel51.setText("Already Have An Account?");
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 0;
                    jPanel78.add(jLabel51, gridBagConstraints);

                    jPanel100.setBackground(new java.awt.Color(255, 255, 255));
                    jPanel100.setMinimumSize(new java.awt.Dimension(550, 100));
                    jPanel100.setLayout(new java.awt.GridBagLayout());

                    jTextField8.setFont(new java.awt.Font("Century Gothic", 2, 14)); // NOI18N
                    jTextField8.setText("Username");
                    jTextField8.setBorder(null);
                    jTextField8.setMinimumSize(new java.awt.Dimension(0, 25));
                    jTextField8.setPreferredSize(new java.awt.Dimension(69, 25));
                    jTextField8.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            jTextField8ActionPerformed(evt);
                        }
                    });
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 0;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel100.add(jTextField8, gridBagConstraints);

                    jLabel52.setIcon(new javax.swing.ImageIcon("C:\\JavaProiect\\Proiectv2\\OtherPhotos\\Horizontal-Line-Transparent-Background2.png")); // NOI18N
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 1;
                    jPanel100.add(jLabel52, gridBagConstraints);

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 2;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel78.add(jPanel100, gridBagConstraints);

                    jPanel101.setBackground(new java.awt.Color(255, 255, 255));
                    jPanel101.setPreferredSize(new java.awt.Dimension(550, 30));

                    javax.swing.GroupLayout jPanel101Layout = new javax.swing.GroupLayout(jPanel101);
                    jPanel101.setLayout(jPanel101Layout);
                    jPanel101Layout.setHorizontalGroup(
                        jPanel101Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 550, Short.MAX_VALUE)
                    );
                    jPanel101Layout.setVerticalGroup(
                        jPanel101Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 30, Short.MAX_VALUE)
                    );

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 1;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel78.add(jPanel101, gridBagConstraints);

                    jPanel102.setBackground(new java.awt.Color(255, 255, 255));
                    jPanel102.setPreferredSize(new java.awt.Dimension(550, 40));

                    javax.swing.GroupLayout jPanel102Layout = new javax.swing.GroupLayout(jPanel102);
                    jPanel102.setLayout(jPanel102Layout);
                    jPanel102Layout.setHorizontalGroup(
                        jPanel102Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 550, Short.MAX_VALUE)
                    );
                    jPanel102Layout.setVerticalGroup(
                        jPanel102Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 40, Short.MAX_VALUE)
                    );

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 3;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel78.add(jPanel102, gridBagConstraints);

                    jPanel103.setBackground(new java.awt.Color(255, 255, 255));
                    jPanel103.setMinimumSize(new java.awt.Dimension(550, 100));
                    jPanel103.setLayout(new java.awt.GridBagLayout());

                    jLabel53.setIcon(new javax.swing.ImageIcon("C:\\JavaProiect\\Proiectv2\\OtherPhotos\\Horizontal-Line-Transparent-Background2.png")); // NOI18N
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 1;
                    jPanel103.add(jLabel53, gridBagConstraints);

                    jPasswordField3.setFont(new java.awt.Font("Century Gothic", 2, 14)); // NOI18N
                    jPasswordField3.setText("Password");
                    jPasswordField3.setBorder(null);
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 0;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel103.add(jPasswordField3, gridBagConstraints);

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 4;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel78.add(jPanel103, gridBagConstraints);

                    jPanel104.setBackground(new java.awt.Color(255, 255, 255));
                    jPanel104.setPreferredSize(new java.awt.Dimension(550, 20));

                    javax.swing.GroupLayout jPanel104Layout = new javax.swing.GroupLayout(jPanel104);
                    jPanel104.setLayout(jPanel104Layout);
                    jPanel104Layout.setHorizontalGroup(
                        jPanel104Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 550, Short.MAX_VALUE)
                    );
                    jPanel104Layout.setVerticalGroup(
                        jPanel104Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 20, Short.MAX_VALUE)
                    );

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 5;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel78.add(jPanel104, gridBagConstraints);

                    jButton10.setBackground(new java.awt.Color(255, 204, 0));
                    jButton10.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
                    jButton10.setText("Sign in");
                    jButton10.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                            jButton10MouseClicked(evt);
                        }
                    });
                    jButton10.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            jButton10ActionPerformed(evt);
                        }
                    });
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 6;
                    jPanel78.add(jButton10, gridBagConstraints);

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 2;
                    gridBagConstraints.gridy = 2;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel74.add(jPanel78, gridBagConstraints);

                    jPanel127.setBackground(new java.awt.Color(255, 204, 0));
                    jPanel127.setMinimumSize(new java.awt.Dimension(600, 50));
                    jPanel127.setPreferredSize(new java.awt.Dimension(600, 50));

                    javax.swing.GroupLayout jPanel127Layout = new javax.swing.GroupLayout(jPanel127);
                    jPanel127.setLayout(jPanel127Layout);
                    jPanel127Layout.setHorizontalGroup(
                        jPanel127Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 600, Short.MAX_VALUE)
                    );
                    jPanel127Layout.setVerticalGroup(
                        jPanel127Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 50, Short.MAX_VALUE)
                    );

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 1;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel74.add(jPanel127, gridBagConstraints);

                    jLabel64.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
                    jLabel64.setText("All your favourite music in one place!");
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 0;
                    gridBagConstraints.gridwidth = 3;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel74.add(jLabel64, gridBagConstraints);

                    jPanel73.add(jPanel74, "card2");

                    jPanel75.setBackground(new java.awt.Color(255, 204, 0));
                    jPanel75.setMaximumSize(new java.awt.Dimension(600, 900));
                    jPanel75.setMinimumSize(new java.awt.Dimension(600, 900));
                    jPanel75.setPreferredSize(new java.awt.Dimension(600, 900));
                    jPanel75.setLayout(new java.awt.GridBagLayout());

                    jLabel54.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
                    jLabel54.setText("User");
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 0;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel75.add(jLabel54, gridBagConstraints);

                    jPanel105.setBackground(new java.awt.Color(0, 0, 0));
                    jPanel105.setMinimumSize(new java.awt.Dimension(900, 300));
                    jPanel105.setPreferredSize(new java.awt.Dimension(900, 300));
                    jPanel105.setLayout(new java.awt.GridBagLayout());

                    jLabel55.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
                    jLabel55.setForeground(new java.awt.Color(255, 204, 0));
                    jLabel55.setText("Username");
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 0;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel105.add(jLabel55, gridBagConstraints);

                    jTextField9.setEditable(false);
                    jTextField9.setBackground(new java.awt.Color(0, 0, 0));
                    jTextField9.setFont(new java.awt.Font("Century Gothic", 2, 14)); // NOI18N
                    jTextField9.setForeground(new java.awt.Color(255, 255, 255));
                    jTextField9.setText("username");
                    jTextField9.setBorder(null);
                    jTextField9.setCaretColor(new java.awt.Color(255, 255, 255));
                    jTextField9.setMinimumSize(new java.awt.Dimension(850, 30));
                    jTextField9.setPreferredSize(new java.awt.Dimension(850, 30));
                    jTextField9.setSelectedTextColor(new java.awt.Color(0, 0, 0));
                    jTextField9.setSelectionColor(new java.awt.Color(255, 255, 255));
                    jTextField9.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            jTextField9ActionPerformed(evt);
                        }
                    });
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 1;
                    jPanel105.add(jTextField9, gridBagConstraints);

                    jPanel110.setBackground(new java.awt.Color(0, 0, 0));
                    jPanel110.setPreferredSize(new java.awt.Dimension(100, 20));

                    javax.swing.GroupLayout jPanel110Layout = new javax.swing.GroupLayout(jPanel110);
                    jPanel110.setLayout(jPanel110Layout);
                    jPanel110Layout.setHorizontalGroup(
                        jPanel110Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 100, Short.MAX_VALUE)
                    );
                    jPanel110Layout.setVerticalGroup(
                        jPanel110Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 20, Short.MAX_VALUE)
                    );

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 1;
                    jPanel105.add(jPanel110, gridBagConstraints);

                    jLabel56.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
                    jLabel56.setForeground(new java.awt.Color(255, 204, 0));
                    jLabel56.setText("Password");
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 3;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel105.add(jLabel56, gridBagConstraints);

                    jTextField10.setEditable(false);
                    jTextField10.setBackground(new java.awt.Color(0, 0, 0));
                    jTextField10.setFont(new java.awt.Font("Century Gothic", 2, 14)); // NOI18N
                    jTextField10.setForeground(new java.awt.Color(255, 255, 255));
                    jTextField10.setText("password");
                    jTextField10.setBorder(null);
                    jTextField10.setCaretColor(new java.awt.Color(255, 255, 255));
                    jTextField10.setMinimumSize(new java.awt.Dimension(0, 30));
                    jTextField10.setPreferredSize(new java.awt.Dimension(65, 30));
                    jTextField10.setSelectedTextColor(new java.awt.Color(0, 0, 0));
                    jTextField10.setSelectionColor(new java.awt.Color(255, 255, 255));
                    jTextField10.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            jTextField10ActionPerformed(evt);
                        }
                    });
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 4;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel105.add(jTextField10, gridBagConstraints);

                    jPanel111.setBackground(new java.awt.Color(0, 0, 0));
                    jPanel111.setPreferredSize(new java.awt.Dimension(100, 20));

                    javax.swing.GroupLayout jPanel111Layout = new javax.swing.GroupLayout(jPanel111);
                    jPanel111.setLayout(jPanel111Layout);
                    jPanel111Layout.setHorizontalGroup(
                        jPanel111Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 100, Short.MAX_VALUE)
                    );
                    jPanel111Layout.setVerticalGroup(
                        jPanel111Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 20, Short.MAX_VALUE)
                    );

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 4;
                    jPanel105.add(jPanel111, gridBagConstraints);

                    jLabel57.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
                    jLabel57.setForeground(new java.awt.Color(255, 204, 0));
                    jLabel57.setText("Address");
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 6;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel105.add(jLabel57, gridBagConstraints);

                    jTextField11.setEditable(false);
                    jTextField11.setBackground(new java.awt.Color(0, 0, 0));
                    jTextField11.setFont(new java.awt.Font("Century Gothic", 2, 14)); // NOI18N
                    jTextField11.setForeground(new java.awt.Color(255, 255, 255));
                    jTextField11.setText("Address");
                    jTextField11.setBorder(null);
                    jTextField11.setCaretColor(new java.awt.Color(255, 255, 255));
                    jTextField11.setMinimumSize(new java.awt.Dimension(0, 30));
                    jTextField11.setPreferredSize(new java.awt.Dimension(53, 30));
                    jTextField11.setSelectedTextColor(new java.awt.Color(0, 0, 0));
                    jTextField11.setSelectionColor(new java.awt.Color(255, 255, 255));
                    jTextField11.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            jTextField11ActionPerformed(evt);
                        }
                    });
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 7;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel105.add(jTextField11, gridBagConstraints);

                    jPanel112.setBackground(new java.awt.Color(0, 0, 0));
                    jPanel112.setPreferredSize(new java.awt.Dimension(100, 20));

                    javax.swing.GroupLayout jPanel112Layout = new javax.swing.GroupLayout(jPanel112);
                    jPanel112.setLayout(jPanel112Layout);
                    jPanel112Layout.setHorizontalGroup(
                        jPanel112Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 100, Short.MAX_VALUE)
                    );
                    jPanel112Layout.setVerticalGroup(
                        jPanel112Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 20, Short.MAX_VALUE)
                    );

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 6;
                    jPanel105.add(jPanel112, gridBagConstraints);

                    jLabel58.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
                    jLabel58.setForeground(new java.awt.Color(255, 204, 0));
                    jLabel58.setText("Phone Number");
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 9;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel105.add(jLabel58, gridBagConstraints);

                    jTextField12.setEditable(false);
                    jTextField12.setBackground(new java.awt.Color(0, 0, 0));
                    jTextField12.setFont(new java.awt.Font("Century Gothic", 2, 14)); // NOI18N
                    jTextField12.setForeground(new java.awt.Color(255, 255, 255));
                    jTextField12.setText("Phone Number");
                    jTextField12.setBorder(null);
                    jTextField12.setCaretColor(new java.awt.Color(255, 255, 255));
                    jTextField12.setPreferredSize(new java.awt.Dimension(103, 30));
                    jTextField12.setSelectedTextColor(new java.awt.Color(0, 0, 0));
                    jTextField12.setSelectionColor(new java.awt.Color(255, 255, 255));
                    jTextField12.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            jTextField12ActionPerformed(evt);
                        }
                    });
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 10;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel105.add(jTextField12, gridBagConstraints);

                    jPanel109.setBackground(new java.awt.Color(0, 0, 0));
                    jPanel109.setMinimumSize(new java.awt.Dimension(0, 10));
                    jPanel109.setPreferredSize(new java.awt.Dimension(0, 10));

                    javax.swing.GroupLayout jPanel109Layout = new javax.swing.GroupLayout(jPanel109);
                    jPanel109.setLayout(jPanel109Layout);
                    jPanel109Layout.setHorizontalGroup(
                        jPanel109Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 850, Short.MAX_VALUE)
                    );
                    jPanel109Layout.setVerticalGroup(
                        jPanel109Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 10, Short.MAX_VALUE)
                    );

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 2;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel105.add(jPanel109, gridBagConstraints);

                    jPanel113.setBackground(new java.awt.Color(0, 0, 0));
                    jPanel113.setMinimumSize(new java.awt.Dimension(0, 10));
                    jPanel113.setPreferredSize(new java.awt.Dimension(0, 10));

                    javax.swing.GroupLayout jPanel113Layout = new javax.swing.GroupLayout(jPanel113);
                    jPanel113.setLayout(jPanel113Layout);
                    jPanel113Layout.setHorizontalGroup(
                        jPanel113Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 850, Short.MAX_VALUE)
                    );
                    jPanel113Layout.setVerticalGroup(
                        jPanel113Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 10, Short.MAX_VALUE)
                    );

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 5;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel105.add(jPanel113, gridBagConstraints);

                    jPanel116.setBackground(new java.awt.Color(0, 0, 0));
                    jPanel116.setMinimumSize(new java.awt.Dimension(0, 10));
                    jPanel116.setPreferredSize(new java.awt.Dimension(0, 10));

                    javax.swing.GroupLayout jPanel116Layout = new javax.swing.GroupLayout(jPanel116);
                    jPanel116.setLayout(jPanel116Layout);
                    jPanel116Layout.setHorizontalGroup(
                        jPanel116Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 850, Short.MAX_VALUE)
                    );
                    jPanel116Layout.setVerticalGroup(
                        jPanel116Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 10, Short.MAX_VALUE)
                    );

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 8;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel105.add(jPanel116, gridBagConstraints);

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 2;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel75.add(jPanel105, gridBagConstraints);

                    jPanel106.setBackground(new java.awt.Color(255, 204, 0));
                    jPanel106.setMinimumSize(new java.awt.Dimension(700, 30));
                    jPanel106.setPreferredSize(new java.awt.Dimension(700, 30));

                    javax.swing.GroupLayout jPanel106Layout = new javax.swing.GroupLayout(jPanel106);
                    jPanel106.setLayout(jPanel106Layout);
                    jPanel106Layout.setHorizontalGroup(
                        jPanel106Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 900, Short.MAX_VALUE)
                    );
                    jPanel106Layout.setVerticalGroup(
                        jPanel106Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 30, Short.MAX_VALUE)
                    );

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 1;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel75.add(jPanel106, gridBagConstraints);

                    jPanel107.setBackground(new java.awt.Color(255, 204, 0));
                    jPanel107.setMinimumSize(new java.awt.Dimension(900, 50));
                    jPanel107.setPreferredSize(new java.awt.Dimension(900, 50));

                    javax.swing.GroupLayout jPanel107Layout = new javax.swing.GroupLayout(jPanel107);
                    jPanel107.setLayout(jPanel107Layout);
                    jPanel107Layout.setHorizontalGroup(
                        jPanel107Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 900, Short.MAX_VALUE)
                    );
                    jPanel107Layout.setVerticalGroup(
                        jPanel107Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 50, Short.MAX_VALUE)
                    );

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 3;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel75.add(jPanel107, gridBagConstraints);

                    jPanel114.setBackground(new java.awt.Color(0, 0, 0));
                    jPanel114.setMinimumSize(new java.awt.Dimension(900, 320));
                    jPanel114.setPreferredSize(new java.awt.Dimension(900, 320));
                    jPanel114.setLayout(new java.awt.GridBagLayout());

                    jLabel59.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
                    jLabel59.setForeground(new java.awt.Color(255, 204, 0));
                    jLabel59.setText("Card");
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 0;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel114.add(jLabel59, gridBagConstraints);

                    jLabel60.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
                    jLabel60.setForeground(new java.awt.Color(255, 204, 0));
                    jLabel60.setText("Number");
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 3;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel114.add(jLabel60, gridBagConstraints);

                    jTextField14.setEditable(false);
                    jTextField14.setBackground(new java.awt.Color(0, 0, 0));
                    jTextField14.setFont(new java.awt.Font("Century Gothic", 2, 14)); // NOI18N
                    jTextField14.setForeground(new java.awt.Color(255, 255, 255));
                    jTextField14.setText("Card Number");
                    jTextField14.setBorder(null);
                    jTextField14.setCaretColor(new java.awt.Color(255, 255, 255));
                    jTextField14.setMinimumSize(new java.awt.Dimension(0, 30));
                    jTextField14.setPreferredSize(new java.awt.Dimension(850, 30));
                    jTextField14.setSelectedTextColor(new java.awt.Color(0, 0, 0));
                    jTextField14.setSelectionColor(new java.awt.Color(255, 255, 255));
                    jTextField14.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            jTextField14ActionPerformed(evt);
                        }
                    });
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 4;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel114.add(jTextField14, gridBagConstraints);

                    jPanel117.setBackground(new java.awt.Color(0, 0, 0));
                    jPanel117.setPreferredSize(new java.awt.Dimension(100, 20));

                    javax.swing.GroupLayout jPanel117Layout = new javax.swing.GroupLayout(jPanel117);
                    jPanel117.setLayout(jPanel117Layout);
                    jPanel117Layout.setHorizontalGroup(
                        jPanel117Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 100, Short.MAX_VALUE)
                    );
                    jPanel117Layout.setVerticalGroup(
                        jPanel117Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 20, Short.MAX_VALUE)
                    );

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 4;
                    jPanel114.add(jPanel117, gridBagConstraints);

                    jLabel61.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
                    jLabel61.setForeground(new java.awt.Color(255, 204, 0));
                    jLabel61.setText("Bank");
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 6;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel114.add(jLabel61, gridBagConstraints);

                    jTextField15.setEditable(false);
                    jTextField15.setBackground(new java.awt.Color(0, 0, 0));
                    jTextField15.setFont(new java.awt.Font("Century Gothic", 2, 14)); // NOI18N
                    jTextField15.setForeground(new java.awt.Color(255, 255, 255));
                    jTextField15.setText("Bank");
                    jTextField15.setBorder(null);
                    jTextField15.setCaretColor(new java.awt.Color(255, 255, 255));
                    jTextField15.setMinimumSize(new java.awt.Dimension(0, 30));
                    jTextField15.setPreferredSize(new java.awt.Dimension(53, 30));
                    jTextField15.setSelectedTextColor(new java.awt.Color(0, 0, 0));
                    jTextField15.setSelectionColor(new java.awt.Color(255, 255, 255));
                    jTextField15.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            jTextField15ActionPerformed(evt);
                        }
                    });
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 7;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel114.add(jTextField15, gridBagConstraints);

                    jPanel118.setBackground(new java.awt.Color(0, 0, 0));
                    jPanel118.setPreferredSize(new java.awt.Dimension(100, 20));

                    javax.swing.GroupLayout jPanel118Layout = new javax.swing.GroupLayout(jPanel118);
                    jPanel118.setLayout(jPanel118Layout);
                    jPanel118Layout.setHorizontalGroup(
                        jPanel118Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 100, Short.MAX_VALUE)
                    );
                    jPanel118Layout.setVerticalGroup(
                        jPanel118Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 20, Short.MAX_VALUE)
                    );

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 6;
                    jPanel114.add(jPanel118, gridBagConstraints);

                    jLabel62.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
                    jLabel62.setForeground(new java.awt.Color(255, 204, 0));
                    jLabel62.setText("Expiration Date");
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 9;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel114.add(jLabel62, gridBagConstraints);

                    jTextField16.setEditable(false);
                    jTextField16.setBackground(new java.awt.Color(0, 0, 0));
                    jTextField16.setFont(new java.awt.Font("Century Gothic", 2, 14)); // NOI18N
                    jTextField16.setForeground(new java.awt.Color(255, 255, 255));
                    jTextField16.setText("Date");
                    jTextField16.setBorder(null);
                    jTextField16.setCaretColor(new java.awt.Color(255, 255, 255));
                    jTextField16.setPreferredSize(new java.awt.Dimension(103, 30));
                    jTextField16.setSelectedTextColor(new java.awt.Color(0, 0, 0));
                    jTextField16.setSelectionColor(new java.awt.Color(255, 255, 255));
                    jTextField16.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            jTextField16ActionPerformed(evt);
                        }
                    });
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 10;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel114.add(jTextField16, gridBagConstraints);

                    jPanel119.setBackground(new java.awt.Color(0, 0, 0));
                    jPanel119.setMinimumSize(new java.awt.Dimension(0, 10));
                    jPanel119.setPreferredSize(new java.awt.Dimension(0, 10));

                    javax.swing.GroupLayout jPanel119Layout = new javax.swing.GroupLayout(jPanel119);
                    jPanel119.setLayout(jPanel119Layout);
                    jPanel119Layout.setHorizontalGroup(
                        jPanel119Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 850, Short.MAX_VALUE)
                    );
                    jPanel119Layout.setVerticalGroup(
                        jPanel119Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 10, Short.MAX_VALUE)
                    );

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 2;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel114.add(jPanel119, gridBagConstraints);

                    jPanel120.setBackground(new java.awt.Color(0, 0, 0));
                    jPanel120.setMinimumSize(new java.awt.Dimension(0, 10));
                    jPanel120.setPreferredSize(new java.awt.Dimension(0, 10));

                    javax.swing.GroupLayout jPanel120Layout = new javax.swing.GroupLayout(jPanel120);
                    jPanel120.setLayout(jPanel120Layout);
                    jPanel120Layout.setHorizontalGroup(
                        jPanel120Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 850, Short.MAX_VALUE)
                    );
                    jPanel120Layout.setVerticalGroup(
                        jPanel120Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 10, Short.MAX_VALUE)
                    );

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 5;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel114.add(jPanel120, gridBagConstraints);

                    jPanel121.setBackground(new java.awt.Color(0, 0, 0));
                    jPanel121.setMinimumSize(new java.awt.Dimension(0, 10));
                    jPanel121.setPreferredSize(new java.awt.Dimension(0, 10));

                    javax.swing.GroupLayout jPanel121Layout = new javax.swing.GroupLayout(jPanel121);
                    jPanel121.setLayout(jPanel121Layout);
                    jPanel121Layout.setHorizontalGroup(
                        jPanel121Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 850, Short.MAX_VALUE)
                    );
                    jPanel121Layout.setVerticalGroup(
                        jPanel121Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 10, Short.MAX_VALUE)
                    );

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 8;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel114.add(jPanel121, gridBagConstraints);

                    userCardType.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                            userCardTypeMouseClicked(evt);
                        }
                    });
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 1;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel114.add(userCardType, gridBagConstraints);

                    jPanel122.setBackground(new java.awt.Color(0, 0, 0));
                    jPanel122.setMinimumSize(new java.awt.Dimension(0, 10));
                    jPanel122.setPreferredSize(new java.awt.Dimension(0, 10));

                    javax.swing.GroupLayout jPanel122Layout = new javax.swing.GroupLayout(jPanel122);
                    jPanel122.setLayout(jPanel122Layout);
                    jPanel122Layout.setHorizontalGroup(
                        jPanel122Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 850, Short.MAX_VALUE)
                    );
                    jPanel122Layout.setVerticalGroup(
                        jPanel122Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 10, Short.MAX_VALUE)
                    );

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 11;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel114.add(jPanel122, gridBagConstraints);

                    jLabel63.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
                    jLabel63.setForeground(new java.awt.Color(255, 204, 0));
                    jLabel63.setText("Security Code");
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 12;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel114.add(jLabel63, gridBagConstraints);

                    jPanel123.setBackground(new java.awt.Color(0, 0, 0));
                    jPanel123.setMinimumSize(new java.awt.Dimension(0, 10));

                    javax.swing.GroupLayout jPanel123Layout = new javax.swing.GroupLayout(jPanel123);
                    jPanel123.setLayout(jPanel123Layout);
                    jPanel123Layout.setHorizontalGroup(
                        jPanel123Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 850, Short.MAX_VALUE)
                    );
                    jPanel123Layout.setVerticalGroup(
                        jPanel123Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 10, Short.MAX_VALUE)
                    );

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 8;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel114.add(jPanel123, gridBagConstraints);

                    jTextField17.setEditable(false);
                    jTextField17.setBackground(new java.awt.Color(0, 0, 0));
                    jTextField17.setFont(new java.awt.Font("Century Gothic", 2, 14)); // NOI18N
                    jTextField17.setForeground(new java.awt.Color(255, 255, 255));
                    jTextField17.setText("Card Number");
                    jTextField17.setBorder(null);
                    jTextField17.setCaretColor(new java.awt.Color(255, 255, 255));
                    jTextField17.setMinimumSize(new java.awt.Dimension(0, 30));
                    jTextField17.setPreferredSize(new java.awt.Dimension(850, 30));
                    jTextField17.setSelectedTextColor(new java.awt.Color(0, 0, 0));
                    jTextField17.setSelectionColor(new java.awt.Color(255, 255, 255));
                    jTextField17.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            jTextField17ActionPerformed(evt);
                        }
                    });
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 13;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel114.add(jTextField17, gridBagConstraints);

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 4;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel75.add(jPanel114, gridBagConstraints);

                    jPanel108.setBackground(new java.awt.Color(255, 204, 0));
                    jPanel108.setMinimumSize(new java.awt.Dimension(1900, 120));
                    jPanel108.setPreferredSize(new java.awt.Dimension(900, 120));
                    jPanel108.setRequestFocusEnabled(false);
                    jPanel108.setLayout(new java.awt.GridBagLayout());

                    jButton11.setBackground(new java.awt.Color(0, 0, 0));
                    jButton11.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
                    jButton11.setForeground(new java.awt.Color(255, 204, 0));
                    jButton11.setText("Edit");
                    jButton11.setMaximumSize(new java.awt.Dimension(60, 27));
                    jButton11.setMinimumSize(new java.awt.Dimension(60, 27));
                    jButton11.setPreferredSize(new java.awt.Dimension(60, 27));
                    jButton11.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                            jButton11MouseClicked(evt);
                        }
                    });
                    jButton11.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            jButton11ActionPerformed(evt);
                        }
                    });
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 0;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel108.add(jButton11, gridBagConstraints);

                    jPanel115.setMinimumSize(new java.awt.Dimension(0, 10));

                    javax.swing.GroupLayout jPanel115Layout = new javax.swing.GroupLayout(jPanel115);
                    jPanel115.setLayout(jPanel115Layout);
                    jPanel115Layout.setHorizontalGroup(
                        jPanel115Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
                    );
                    jPanel115Layout.setVerticalGroup(
                        jPanel115Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 10, Short.MAX_VALUE)
                    );

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 1;
                    jPanel108.add(jPanel115, gridBagConstraints);

                    jPanel124.setMinimumSize(new java.awt.Dimension(700, 0));
                    jPanel124.setName(""); // NOI18N

                    javax.swing.GroupLayout jPanel124Layout = new javax.swing.GroupLayout(jPanel124);
                    jPanel124.setLayout(jPanel124Layout);
                    jPanel124Layout.setHorizontalGroup(
                        jPanel124Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 700, Short.MAX_VALUE)
                    );
                    jPanel124Layout.setVerticalGroup(
                        jPanel124Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
                    );

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 1;
                    gridBagConstraints.gridy = 0;
                    jPanel108.add(jPanel124, gridBagConstraints);

                    jButton12.setBackground(new java.awt.Color(0, 0, 0));
                    jButton12.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
                    jButton12.setForeground(new java.awt.Color(255, 204, 0));
                    jButton12.setText("Exit the App");
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 4;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel108.add(jButton12, gridBagConstraints);

                    jButton13.setBackground(new java.awt.Color(0, 0, 0));
                    jButton13.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
                    jButton13.setForeground(new java.awt.Color(255, 204, 0));
                    jButton13.setText("Log out");
                    jButton13.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                            jButton13MouseClicked(evt);
                        }
                    });
                    jButton13.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            jButton13ActionPerformed(evt);
                        }
                    });
                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 2;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel108.add(jButton13, gridBagConstraints);

                    jPanel126.setMinimumSize(new java.awt.Dimension(0, 10));
                    jPanel126.setPreferredSize(new java.awt.Dimension(0, 10));

                    javax.swing.GroupLayout jPanel126Layout = new javax.swing.GroupLayout(jPanel126);
                    jPanel126.setLayout(jPanel126Layout);
                    jPanel126Layout.setHorizontalGroup(
                        jPanel126Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
                    );
                    jPanel126Layout.setVerticalGroup(
                        jPanel126Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 10, Short.MAX_VALUE)
                    );

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 3;
                    jPanel108.add(jPanel126, gridBagConstraints);

                    gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = 5;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
                    jPanel75.add(jPanel108, gridBagConstraints);

                    jPanel73.add(jPanel75, "card3");

                    jScrollPane9.setViewportView(jPanel73);

                    jPanel72.add(jScrollPane9, java.awt.BorderLayout.CENTER);

                    jPanel25.add(jPanel72, "card4");

                    getContentPane().add(jPanel25, java.awt.BorderLayout.CENTER);

                    pack();
                }// </editor-fold>//GEN-END:initComponents

    private void jPanel3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseEntered
        jPanel3.setBackground(new Color(255, 204, 0));
        jLabel1.setForeground(Color.DARK_GRAY);
    }//GEN-LAST:event_jPanel3MouseEntered

    private void jPanel4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseEntered
        jPanel4.setBackground(new Color(255, 204, 0));
        jLabel2.setForeground(Color.DARK_GRAY);
    }//GEN-LAST:event_jPanel4MouseEntered

    private void jPanel5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseEntered
        jPanel5.setBackground(new Color(255, 204, 0));
        jLabel3.setForeground(Color.DARK_GRAY);
    }//GEN-LAST:event_jPanel5MouseEntered

    private void jPanel3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseExited
        jPanel3.setBackground(new Color(51, 51, 51));
        jLabel1.setForeground(Color.WHITE);
    }//GEN-LAST:event_jPanel3MouseExited

    private void jPanel4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseExited
        jPanel4.setBackground(new Color(51, 51, 51));
        jLabel2.setForeground(Color.WHITE);
    }//GEN-LAST:event_jPanel4MouseExited

    private void jPanel5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseExited
        jPanel5.setBackground(new Color(51, 51, 51));
        jLabel3.setForeground(Color.WHITE);
    }//GEN-LAST:event_jPanel5MouseExited

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        jPanel7.removeAll();
        jPanel7.add(jPanel10);
        jPanel7.repaint();
        jPanel7.validate();
    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        jPanel7.removeAll();
        jPanel7.add(jPanel8);
        jPanel7.repaint();
        jPanel7.validate();
    }//GEN-LAST:event_jButton3MouseClicked

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jPanel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseClicked
        audio.close();
        try {
            audio = AudioSystem.getClip();
        } catch (LineUnavailableException ex) {
            Logger.getLogger(YellowSounds.class.getName()).log(Level.SEVERE, null, ex);
        }
        jPanel25.removeAll();
        if(jScrollPane2 != null)
            jScrollPane2.getViewport().setViewPosition(new java.awt.Point(0, 0));
        jPanel25.add(jPanel34);
        jPanel25.repaint();
        jPanel1.repaint();
        jPanel25.validate();
    }//GEN-LAST:event_jPanel3MouseClicked

    private void searchLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchLabelMouseClicked
        jList1.setListData(new String[0]);
        jPanel31.removeAll();
        jPanel31.add(jPanel33);
        jPanel31.repaint();
        jPanel31.validate();
        jPanel30.removeAll();
        jPanel30.add(ListPanel);
        jPanel30.repaint();
        jPanel30.validate();
        List<String> dataList = new ArrayList<>();
        if(!"".equals(jTextField1.getText()))
        try
        {
            String aux = (String)jComboBox1.getSelectedItem();
            st = con.createStatement();
            
            String sqlAlbum = "SELECT * FROM album WHERE name LIKE '%"
                    + jTextField1.getText()
                    + "%'";
            
            String sqlArtist = "SELECT * FROM artist"
                               + " WHERE name LIKE '%" 
                               + jTextField1.getText() + "%'";
            
            String sqlTrack = "SELECT * FROM track"
                               + " WHERE name LIKE '%" 
                               + jTextField1.getText() + "%'";
           
            st = con.createStatement();
            String sql = "";
            if(aux.equals("album"))
                sql = sqlAlbum;
            if(aux.equals("artist"))
                sql = sqlArtist;
            if(aux.equals("track"))
                sql = sqlTrack;
            ResultSet rezz = st.executeQuery(sql);
            String prev = "";
            while(rezz.next())
            {
                if(! prev.equals(rezz.getString(1)))
                {
                    dataList.add(rezz.getString(1));
                    prev = rezz.getString(1);
                }

            }
            jList1.setListData
                   (dataList.toArray(new String[dataList.size()]));
            
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_searchLabelMouseClicked

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        jList1.setListData(new String[0]);
        jPanel31.removeAll();
        jPanel31.add(jPanel33);
        jPanel31.repaint();
        jPanel31.validate();
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged
        jPanel31.removeAll();
        jPanel31.add(jPanel35);
        jPanel31.repaint();
        jPanel31.validate();
        jLabel15.setText(jList1.getSelectedValue());
        try
        {   
            String txt = "";
            String comboItem = (String)jComboBox1.getSelectedItem();
            st = con.createStatement();
            String sgl = "";
            if(comboItem.equals("album"))
            {
                sgl = "SELECT DISTINCT name FROM artist WHERE album = '"
                          + jList1.getSelectedValue() + "'";
                jButton5.setText("Open the album page");
            }
            else if(comboItem.equals("artist"))
            {
                sgl = "SELECT DISTINCT genre FROM artist WHERE name = '"
                          + jList1.getSelectedValue() + "'";
                jButton5.setText("Open the artist page");
                
            }
            else if(comboItem.equals("track"))
            {
                sgl = "SELECT b.name, a.name FROM album a, artist b "
                        + "WHERE a.name = b.album AND track = '" 
                        + jList1.getSelectedValue() + "'";
                jButton5.setText("Open the album page");
            }
            ResultSet result = st.executeQuery(sgl);
            if(result.next())
            {
                txt = result.getString(1);
                if(comboItem.equals("track"))
                    txt = txt + " - " + result.getString(2);
            }
            
            jLabel16.setText(txt);
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_jList1ValueChanged

    private void jButton5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton5MouseClicked
        try
        {
            String comboItem = (String)jComboBox1.getSelectedItem();
        
            if(comboItem.equals("album") || comboItem.equals("track"))
            {
                String sql = "";
                if(comboItem.equals("album"))
                {
                    sql = "SELECT * FROM album WHERE name = '"
                            + jLabel15.getText() + "'";
                }
                else sql = "SELECT * FROM album WHERE name = '"
                            + jLabel16.getText().substring
                             (jLabel16.getText().lastIndexOf("-") + 2
                             ,jLabel16.getText().length()) + "'";
               
                st = con.createStatement();
                ResultSet rs = st.executeQuery(sql);
                List<Track> trackList = new ArrayList<>();
                boolean ok = false;
                String str1 = "";
                String str2 = "";
                int int1 = 0;
                int int2 = 0;
                while(rs.next())
                {
                    ok = true;
                    str1 = rs.getString(1);
                    str2 = rs.getString(3);
                    int1 = rs.getInt(4);
                    int2 = rs.getInt(5);
                    st = con.createStatement();
                    ResultSet columnSet = st.executeQuery("SELECT * FROM"
                            + " track WHERE name = '" + rs.getString(2) + "'");
                    if(columnSet.next())
                        trackList.add(new Track(columnSet.getString(1),
                                                columnSet.getInt(2),
                                                columnSet.getString(3)));
                }
                
                if(ok)
                {
                    albumToShow = new Album(str1,
                                            trackList,
                                            str2,
                                            int1,
                                            int2);
                    createAlbumPage(albumToShow);
                    jPanel25.removeAll();
                    jPanel25.add(jPanel42);
                    if(jScrollPane4 != null)
                        jScrollPane4.getViewport().setViewPosition
                                                (new java.awt.Point(0, 0));
                    if(jScrollPane6 != null)
                        jScrollPane6.getViewport().setViewPosition
                                                (new java.awt.Point(0, 0));
                    jPanel25.repaint();
                    jPanel25.validate();
                }
            }
            else if(comboItem.equals("artist"))
            {
                jLabel23.setForeground(Color.WHITE);
                Artist a;
                a = createArtist(jLabel15.getText());
                if(a != null)
                {
                    artistToShow = a;
                    createArtistPage(a);
                    jPanel25.removeAll();
                    jPanel25.add(jPanel50);
                    if(jScrollPane5 != null)
                        jScrollPane5.getViewport().setViewPosition
                                                (new java.awt.Point(0, 0));
                    if(jScrollPane7 != null)
                        jScrollPane7.getViewport().setViewPosition
                                                (new java.awt.Point(0, 0));
                    jPanel25.repaint();
                    jPanel25.validate();
                }
            }
            
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        
        
    }//GEN-LAST:event_jButton5MouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jList2ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList2ValueChanged
        audio.close();
        AudioInputStream ais = null;
        try 
        {
            if(jList2.getSelectedIndex() != -1)
            {
                Track found = new Track("", 0, "");
                for(Track t : albumToShow.getPlayList())
                {
                    if(t.getName().equals(jList2.getSelectedValue()))
                    {
                        found = t;
                    }
                }   
                ais = AudioSystem.getAudioInputStream(new File(found.getPath()));
                audio.open(ais);
                audio.setMicrosecondPosition(found.getStartMicroseconds());
                audio.start();
                nowPlayingLabel.setText("Now Playing : " + 
                                        jList2.getSelectedValue());
            }
            else nowPlayingLabel.setText("");
            
        }
        catch (UnsupportedAudioFileException | IOException | 
               LineUnavailableException ex) 
        {
            Logger.getLogger(YellowSounds.class.getName()).log(Level.SEVERE, null, 
                             ex);
        }
    }//GEN-LAST:event_jList2ValueChanged

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        
        Album a;
        a = createAlbum("Handwritten");
        if(a != null)
        {
            jList2.setListData(new String[0]);
            albumToShow = a;
            createAlbumPage(albumToShow);
            jPanel25.removeAll();
            jPanel25.add(jPanel42);
            if(jScrollPane4 != null)
                jScrollPane4.getViewport().setViewPosition(new java.awt.Point(0, 0));
            if(jScrollPane6 != null)
                jScrollPane6.getViewport().setViewPosition(new java.awt.Point(0, 0));
            jPanel25.repaint();
            jPanel25.validate();
        }
        
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseClicked
        
        Album a;
        a = createAlbum("Awaken, My Love!");
        if(a != null)
        {
            jList2.setListData(new String[0]);
            albumToShow = a;
            createAlbumPage(albumToShow);
            jPanel25.removeAll();
            jPanel25.add(jPanel42);
            if(jScrollPane4 != null)
                jScrollPane4.getViewport().setViewPosition(new java.awt.Point(0, 0));
            if(jScrollPane6 != null)
                jScrollPane6.getViewport().setViewPosition(new java.awt.Point(0, 0));
            jPanel25.repaint();
            jPanel25.validate();
        }
        
    }//GEN-LAST:event_jButton4MouseClicked

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
        
        Album a;
        a = createAlbum(jLabel8.getText());
        if(a != null)
        {
            jList2.setListData(new String[0]);
            albumToShow = a;
            createAlbumPage(albumToShow);
            jPanel25.removeAll();
            jPanel25.add(jPanel42);
            if(jScrollPane4 != null)
                jScrollPane4.getViewport().setViewPosition(new java.awt.Point(0, 0));
            if(jScrollPane6 != null)
                jScrollPane6.getViewport().setViewPosition(new java.awt.Point(0, 0));
            jPanel25.repaint();
            jPanel25.validate();
        }
        
    }//GEN-LAST:event_jLabel8MouseClicked

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
       
        Album a;
        a = createAlbum(jLabel8.getText());
        if(a != null)
        {
            jList2.setListData(new String[0]);
            albumToShow = a;
            createAlbumPage(albumToShow);
            jPanel25.removeAll();
            jPanel25.add(jPanel42);
            if(jScrollPane4 != null)
                jScrollPane4.getViewport().setViewPosition(new java.awt.Point(0, 0));
            if(jScrollPane6 != null)
                jScrollPane6.getViewport().setViewPosition(new java.awt.Point(0, 0));
            jPanel25.repaint();
            jPanel25.validate();
        }
        
    }//GEN-LAST:event_jLabel9MouseClicked

    private void jPanel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseClicked
        audio.close();
        try 
        {
            audio = AudioSystem.getClip();
        } 
        catch (LineUnavailableException ex) 
        {
            Logger.getLogger(YellowSounds.class.getName()).log(Level.SEVERE, 
                                                          null, ex);
        }
        jPanel25.removeAll();
        createFirstPage();
        jPanel25.add(jPanel6);
        jPanel25.repaint();
        jPanel25.validate();
    }//GEN-LAST:event_jPanel2MouseClicked

    private void jLabel23MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel23MouseClicked
        audio.close();
        try 
        {
            audio = AudioSystem.getClip();
        } 
        catch (LineUnavailableException ex) 
        {
            Logger.getLogger(YellowSounds.class.getName()).log(Level.SEVERE, 
                                                          null, ex);
        }

        jLabel23.setForeground(Color.WHITE);
        Artist a;
        a = createArtist(jLabel23.getText());
        if(a != null)
        {
            artistToShow = a;
            createArtistPage(a);
            jPanel25.removeAll();
            jPanel25.add(jPanel50);
            if(jScrollPane5 != null)
                jScrollPane5.getViewport().setViewPosition(new java.awt.Point(0, 0));
            if(jScrollPane7 != null)
                jScrollPane7.getViewport().setViewPosition(new java.awt.Point(0, 0));
            jPanel25.repaint();
            jPanel25.validate();
        }
        
    }//GEN-LAST:event_jLabel23MouseClicked

    private void jLabel23MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel23MouseEntered
        jLabel23.setForeground(new Color(255, 204, 0));
    }//GEN-LAST:event_jLabel23MouseEntered

    private void jLabel23MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel23MouseExited
        jLabel23.setForeground(Color.WHITE);
    }//GEN-LAST:event_jLabel23MouseExited

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jPanel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseClicked
        audio.close();
        try 
        {
            audio = AudioSystem.getClip();
        } 
        catch (LineUnavailableException ex) 
        {
            Logger.getLogger(YellowSounds.class.getName()).log(Level.SEVERE, 
                                                          null, ex);
        }
        if(jScrollPane10 != null)
           jScrollPane10.getViewport().setViewPosition(new java.awt.Point(0, 0));
        cartContent = new ArrayList<>();
        double sum = 0;
        for(Album aa : albumsInCart)
        {
            cartContent.add(aa.getName() + "; " + aa.getPrice() + " RON");
            sum += aa.getPrice();
        }
        
        subTotalLabel.setText(sum + " RON");
        totalLabel.setText((sum + 20.0) + " RON");
        String p[] = new String[cartContent.size()];
        Arrays.sort(cartContent.toArray(p),
                    (str1, str2) -> str1.compareTo(str2));
        
        jList3.setListData(p);
        albumsInCart.sort((a, b) -> a.getName().compareTo(b.getName()));
        
        jPanel25.removeAll();
        jPanel25.add(jPanel59);
        jPanel25.repaint();
        jPanel25.validate();
    }//GEN-LAST:event_jPanel4MouseClicked

    private void jButton6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton6MouseClicked
        if(! customer.equals(new Customer("", "", "", "", "")))
        {
            synchronized(Buffer.getInstance())
            {
                boolean b = Buffer.getInstance().sellAlbum(albumToShow);
                if(b)
                {
                    albumsInCart.add(new Album(albumToShow.getName() 
                                       + "; " + jLabel23.getText(), 
                                       albumToShow.getPlayList(), 
                                       albumToShow.getCoverPath(), 
                                       albumToShow.getPrice(), 
                                       albumToShow.getQuantity()));


                    JOptionPane.showMessageDialog(null, "The Album was succesfully added "
                                          + "to Shopping Cart", 
                                          albumToShow.getName(), 
                                          JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
        else JOptionPane.showMessageDialog(null, 
                                      "You must be logged in to buy the album!", 
                                      "Error", JOptionPane.ERROR_MESSAGE);
        
    }//GEN-LAST:event_jButton6MouseClicked

    private void jButton8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton8MouseClicked
        if(jList3.getSelectedIndex() != -1)
        {
            Album aux = albumsInCart.get(jList3.getSelectedIndex());
            aux.setName(aux.getName().substring(0, aux.getName().indexOf(';')));
            Buffer.getInstance().addAlbum(aux);
            
            albumsInCart.remove(jList3.getSelectedIndex());
            
            cartContent = new ArrayList<>();
            double sum = 0;
            for(Album aa : albumsInCart)
            {
                cartContent.add(aa.getName() + "; " + aa.getPrice() + " RON");
                sum += aa.getPrice();
            }
            subTotalLabel.setText(sum + " RON");
            totalLabel.setText((sum + 20.0) + " RON");
            String p[] = new String[cartContent.size()];
            Arrays.sort(cartContent.toArray(p),
                        (str1, str2) -> str1.compareTo(str2));
            jList3.setListData(p);
        }
    }//GEN-LAST:event_jButton8MouseClicked

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6ActionPerformed

    private void jPasswordField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPasswordField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jPasswordField1ActionPerformed

    private void jTextField8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField8ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jTextField11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField11ActionPerformed

    private void jTextField12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField12ActionPerformed

    private void jTextField10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField10ActionPerformed

    private void jTextField9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField9ActionPerformed

    private void jTextField14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField14ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField14ActionPerformed

    private void jTextField15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField15ActionPerformed

    private void jTextField16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField16ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField16ActionPerformed

    private void jTextField17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField17ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField17ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton11MouseClicked
        boolean OK = true;
        if(jButton11.getText().equals("Edit!"))
       {
           jTextField10.setEditable(true);
           jTextField11.setEditable(true);
           jTextField12.setEditable(true);
           jButton11.setText("Save!");
       }
       else
       {
           try
           {
               st = con.createStatement();
               String telephoneAux = jTextField12.getText();
               if(telephoneAux.length() == 10)
               {
                   int i = 0;
                   for(; i < 10; i ++)
                        if(Character.isLetter(telephoneAux.charAt(i)))
                                break;
                   if(i < 10)
                   {
                       JOptionPane.showMessageDialog(null, 
                                      "Invalid Phone Number!", 
                                      "Error", JOptionPane.ERROR_MESSAGE);
                       OK = false;
                   }
                   
               }
               else 
               {
                   JOptionPane.showMessageDialog(null, 
                                      "Invalid Phone Number!", 
                                      "Error", JOptionPane.ERROR_MESSAGE);
                   OK = false;
               }
      
               
               if(OK)
               {
                   st = con.createStatement();
                   String sql = "UPDATE customer SET address = '" + 
                                 jTextField11.getText() + "', telephone = '" + 
                                 jTextField12.getText() + "', password = '" + 
                                 jTextField10.getText() + "' WHERE name = '" +
                                 jLabel54.getText() + "'";
                   st.executeUpdate(sql);
                   customer.setAddress(jTextField11.getText());
                   customer.setTelephone(jTextField12.getText());
                   customer.setPassword(jTextField10.getText());
               }
                
           }
           catch(SQLException e)
           {
               System.out.println(e.getMessage());
           }
           if(OK)
           {
               jTextField10.setEditable(false);
               jTextField11.setEditable(false);
               jTextField12.setEditable(false);
               jButton11.setText("Edit!");
           }
       }
       
    }//GEN-LAST:event_jButton11MouseClicked

    private void userCardTypeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userCardTypeMouseClicked

    }//GEN-LAST:event_userCardTypeMouseClicked

    private void jLabel43MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel43MouseClicked
         cardTypeLabel.setText("Mastercard");
         newCustomerCardType = false;
    }//GEN-LAST:event_jLabel43MouseClicked

    private void jLabel44MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel44MouseClicked
        cardTypeLabel.setText("VISA");
        newCustomerCardType = true;
    }//GEN-LAST:event_jLabel44MouseClicked

    private void jButton9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton9MouseClicked
        try
        {
            if(jTextField2.getText().equals("Name *") ||
               jTextField3.getText().equals("Username *") ||
               jTextField4.getText().equals("Address *") ||
               jTextField5.getText().equals("Telephone *") ||
               jTextField7.getText().equals("") ||
               jTextField6.getText().equals("") ||
               jPasswordField1.getText().equals("123") ||
               jPasswordField2.getText().equals("Password") ||
               cardTypeLabel.getText().equals("Choose the type of your card!"))
                    JOptionPane.showMessageDialog
                                (null, "All fields must be completed", 
                                 "Fatal Error", JOptionPane.ERROR_MESSAGE);
            else
            {
                st = con.createStatement();
                String usernameAux = jTextField3.getText();
                String sq = "SELECT username FROM customer";
                ResultSet rss = st.executeQuery(sq);
                boolean OK = true;
                while(rss.next())
                {
                    if(rss.getString(1).equals(usernameAux))
                    {
                        JOptionPane.showMessageDialog
                                     (null, "The username already exists"
                                      + " in our database!", 
                                       "Fatal Error", JOptionPane.ERROR_MESSAGE);
                        OK = false;
                        break;
                    }
                }
                if(OK)
                {
                    String telephone = jTextField5.getText();
                    int i = 0;
                    if(telephone.length() == 10)
                        JOptionPane.showMessageDialog
                                     (null, "Invalid phone number!", 
                                       "Fatal Error", JOptionPane.ERROR_MESSAGE);
                    else
                    {    
                        for(; i < telephone.length(); i ++)
                            if(Character.isLetter(telephone.charAt(i)))
                                   break;
                        if(i < telephone.length())
                            JOptionPane.showMessageDialog
                                         (null, "Invalid phone number!", 
                                           "Fatal Error", JOptionPane.ERROR_MESSAGE);
                        else
                        {
                            st = con.createStatement();
                            String numberAux = jTextField6.getText();
                            int j = 0;
                            for(; j < numberAux.length(); j ++)
                                if(Character.isLetter(numberAux.charAt(j)))
                                      break;
                            if(j < numberAux.length())
                               JOptionPane.showMessageDialog
                                         (null, "Invalid card number!", 
                                           "Fatal Error", JOptionPane.ERROR_MESSAGE);
                            else
                            {
                                String sqq = "SELECT number FROM card";
                                ResultSet rsss = st.executeQuery(sqq);
                                boolean OK2 = true;
                                while(rsss.next())
                                {
                                    if(rsss.getString(1).equals(numberAux))
                                    {
                                        JOptionPane.showMessageDialog
                                                     (null, "The card already exists"
                                                      + " in our database!", 
                                                      "Fatal Error", 
                                                      JOptionPane.ERROR_MESSAGE);
                                        OK2 = false;
                                        break;
                                    }
                                }

                                if(OK2)
                                {

                                     String date = jComboBox4.getSelectedItem() + "-" +
                                                   jComboBox3.getSelectedItem() + "-" +
                                                   jComboBox2.getSelectedItem();
                                     String insert = "INSERT INTO card VALUES ('" 
                                                     + jTextField7.getText() + "', '" 
                                                     + jTextField6.getText() + "', '" 
                                                     + date + "', '"
                                                     + ((newCustomerCardType) ? "VISA" : "MasterCard")
                                                     + "', '" + jPasswordField1.getText()
                                                     + "')";
                                     String insert2 = "INSERT INTO customer VALUES ('"
                                                      + jTextField3.getText() + "', '"
                                                      + jPasswordField2.getText() + "', '" 
                                                      + jTextField2.getText() + "', '"
                                                      + jTextField5.getText() + "', '"
                                                      + jTextField4.getText() + "', '"
                                                      + jTextField6.getText() + "')";
                                     st = con.createStatement();
                                     st.executeUpdate(insert);
                                     st = con.createStatement();
                                     st.executeUpdate(insert2);
                                     JOptionPane.showMessageDialog
                                             (null, "Account created!", 
                                               "Info", JOptionPane.INFORMATION_MESSAGE);
                                }
                            }
                        }
                    
                    }
                }
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_jButton9MouseClicked

    private void jTextField7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField7ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jButton10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton10MouseClicked
         try
         {
            st = con.createStatement();
            String sl = "SELECT * FROM customer WHERE username = '"
                        + jTextField8.getText() + "' AND password = '" 
                        + jPasswordField3.getText() + "'";
            ResultSet rsrs = st.executeQuery(sl);
            if(rsrs.next())
            {
                customer = new Customer(rsrs.getString(1), rsrs.getString(2),
                                        rsrs.getString(3), rsrs.getString(4),
                                        rsrs.getString(5));
                st = con.createStatement();
                String sqlSelect = "SELECT * FROM card WHERE number = '"
                                   + rsrs.getString(6) + "'";
                ResultSet rr = st.executeQuery(sqlSelect);
                if(rr.next())
                {
                    customer.createCard(rr.getString(1), rr.getString(2), 
                                        rr.getDate(3), rr.getString(4), 
                                        rr.getString(5));
                    customerCardType = (customer.getCard().getType().equals("VISA"));
                }
                
                jPanel73.removeAll();
                jPanel73.add(jPanel75);
                jPanel73.repaint();
                jPanel73.validate();
                jPanel25.removeAll();
                jPanel25.add(jPanel6);
                jPanel25.repaint();
                jPanel25.validate();
                jLabel54.setText(customer.getName());
                jTextField9.setText(customer.getUsername());
                jTextField10.setText(customer.getPassword());
                jTextField11.setText(customer.getAddress());
                jTextField12.setText(customer.getTelephone());
                if(customer.getUsername().equals(""))
                    jLabel37.setIcon(new javax.swing.ImageIcon());
                if(customer.getCard() != null)
                {
                    if(customerCardType)
                    {                   
                        userCardType.setIcon(new javax.swing.ImageIcon
                            ("C:\\JavaProiect\\Proiectv2\\OtherPhotos\\visa.jpg"));
                        jLabel37.setIcon(new javax.swing.ImageIcon
                            ("C:\\JavaProiect\\Proiectv2\\OtherPhotos\\visa.jpg"));
                    }
                    else 
                    {
                        userCardType.setIcon(new javax.swing.ImageIcon
           ("C:\\JavaProiect\\Proiectv2\\OtherPhotos\\MasterCard_Logo.svg.png"));
                        jLabel37.setIcon(new javax.swing.ImageIcon
           ("C:\\JavaProiect\\Proiectv2\\OtherPhotos\\MasterCard_Logo.svg.png"));
                        
                    }
                    jTextField15.setText(customer.getCard().getBank());
                    jTextField16.setText(customer.getCard().getExpirationDate().toString());
                    jTextField14.setText(customer.getCard().getNumber());
                    jTextField17.setText(customer.getCard().getSecurityCode());
                }
                else jLabel37.setIcon(new javax.swing.ImageIcon());
            }
            else JOptionPane.showMessageDialog
                                 (null, "Password or Username is incorrect!", 
                                   "Error", JOptionPane.ERROR_MESSAGE);
                
         }
         catch(SQLException e)
         {
             System.out.println(e.getMessage());
         }
    }//GEN-LAST:event_jButton10MouseClicked

    private void jPanel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseClicked
        audio.close();
        try {
            audio = AudioSystem.getClip();
        } catch (LineUnavailableException ex) {
            Logger.getLogger(YellowSounds.class.getName()).log(Level.SEVERE, null, ex);
        }
        jTextField2.setText("Name *");
        jTextField3.setText("Username *");
        jTextField4.setText("Address *");
        jTextField5.setText("Telephone *");
        jTextField7.setText("");
        jTextField6.setText("");
        jPasswordField1.setText("123");
        jComboBox1.setSelectedIndex(0);
        jComboBox2.setSelectedIndex(0);
        jComboBox3.setSelectedIndex(0);
        jPasswordField2.setText("password");
        jTextField8.setText("Username");
        jPasswordField3.setText("password");
        jTextField10.setText(customer.getPassword());
        jTextField11.setText(customer.getAddress());
        jTextField12.setText(customer.getTelephone());
        jTextField10.setEditable(false);
        jTextField11.setEditable(false);
        jTextField12.setEditable(false);
        jButton11.setText("Edit!");
        cardTypeLabel.setText("Choose the type of your card");
        jPanel25.removeAll();
        jPanel25.add(jPanel72);
        if(jScrollPane9 != null)
           jScrollPane9.getViewport().setViewPosition(new java.awt.Point(0, 0));
        jPanel25.repaint();
        jPanel25.validate();
    }//GEN-LAST:event_jPanel5MouseClicked

    private void jButton13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton13MouseClicked
        jPanel73.removeAll();
        jPanel73.add(jPanel74);
        if(jScrollPane9 != null)
           jScrollPane9.getViewport().setViewPosition(new java.awt.Point(0, 0));
        jPanel73.repaint();
        jPanel73.validate();
        jPanel72.validate();
        customer = new Customer("", "", "", "", "");
        subTotalLabel.setText("0.0 RON");
        totalLabel.setText("20.0");
        
        for(Album aux : albumsInCart)
        {
            aux.setName(aux.getName().substring(0, aux.getName().indexOf(';')));
            Buffer.getInstance().addAlbum(aux);
        }
        
        albumsInCart.clear();
        cartContent.clear();
        jList3.setListData(new String[0]);
        
        jLabel37.setIcon(new javax.swing.ImageIcon());
        jPanel25.removeAll();
        jPanel25.add(jPanel6);
        if(jScrollPane1 != null)
           jScrollPane1.getViewport().setViewPosition(new java.awt.Point(0, 0));
        jPanel25.repaint();
        jPanel25.validate();
    }//GEN-LAST:event_jButton13MouseClicked

    private void jButton7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton7MouseClicked
        if(customer.equals(new Customer("", "", "", "", "")))
            JOptionPane.showMessageDialog(null, 
                                      "You must be logged in to buy something!", 
                                      "Sorry", JOptionPane.ERROR_MESSAGE);
        else 
        {
            if(albumsInCart.isEmpty())
                JOptionPane.showMessageDialog(null, 
                                      "Your cart is empty!", 
                                      "Hmm..", JOptionPane.WARNING_MESSAGE);
            else
            {
                albumsInCart.forEach((aux) -> 
                {
                    aux.setName(aux.getName().substring(0, aux.getName().indexOf(';')));
                });
                
                synchronized(OrdersBuffer.getInstance())
                {
                    OrdersBuffer.getInstance().addOrder(new Order(customer, albumsInCart));
                }
                albumsInCart.clear();
                cartContent.clear();
                subTotalLabel.setText("0.0 RON");
                totalLabel.setText("20.0 RON");
                jList3.setListData(new String[0]);
                JOptionPane.showMessageDialog(null, 
                                      "Order succesfully placed!", 
                                      "Done", JOptionPane.INFORMATION_MESSAGE);
            }
            
        }
        
    }//GEN-LAST:event_jButton7MouseClicked

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton13ActionPerformed

    
    
    
    //private List<Order> ordersList;
    boolean newCustomerCardType;
    private Customer customer;
    boolean customerCardType;
    private javax.swing.JPanel jPanel55;
    private javax.swing.JScrollPane ScrollPaneAlbumList;
    //private javax.swing.JPanel ContainerOfAlbumList;
    private Statement st;
    private PreparedStatement pst;
    private Connection con;
    private ResultSet rez1;
    private String sql1;
    private List<Album> albumsInCart;
    private List<String> cartContent;
    List<javax.swing.JPanel> panelList;
    List<javax.swing.JLabel> labelList;
    List<javax.swing.JLabel> titles;
    javax.swing.JLabel [] jLabeli;
    javax.swing.JLabel [] jNames;
    javax.swing.JPanel [] jPaneli;
    javax.swing.JLabel [] popArtists;
    javax.swing.JLabel [] popArtistsNames;
    javax.swing.JPanel [] popSpaceBetween;
    javax.swing.JLabel [] rbArtists;
    javax.swing.JLabel [] rbArtistsNames;
    javax.swing.JPanel [] rbSpaceBetween;
    int nrJLabels;
    private Album albumToShow;
    private Artist artistToShow;
    private ResultSet rezArtistIconPath;
    private ResultSet rezCountArtists;
    private javax.swing.JPanel jPanel38;
    private Clip audio;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ListPanel;
    private javax.swing.JPanel albumsToBuy;
    private javax.swing.JLabel cardTypeLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JList<String> jList2;
    private javax.swing.JList<String> jList3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel100;
    private javax.swing.JPanel jPanel101;
    private javax.swing.JPanel jPanel102;
    private javax.swing.JPanel jPanel103;
    private javax.swing.JPanel jPanel104;
    private javax.swing.JPanel jPanel105;
    private javax.swing.JPanel jPanel106;
    private javax.swing.JPanel jPanel107;
    private javax.swing.JPanel jPanel108;
    private javax.swing.JPanel jPanel109;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel110;
    private javax.swing.JPanel jPanel111;
    private javax.swing.JPanel jPanel112;
    private javax.swing.JPanel jPanel113;
    private javax.swing.JPanel jPanel114;
    private javax.swing.JPanel jPanel115;
    private javax.swing.JPanel jPanel116;
    private javax.swing.JPanel jPanel117;
    private javax.swing.JPanel jPanel118;
    private javax.swing.JPanel jPanel119;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel120;
    private javax.swing.JPanel jPanel121;
    private javax.swing.JPanel jPanel122;
    private javax.swing.JPanel jPanel123;
    private javax.swing.JPanel jPanel124;
    private javax.swing.JPanel jPanel125;
    private javax.swing.JPanel jPanel126;
    private javax.swing.JPanel jPanel127;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel48;
    private javax.swing.JPanel jPanel49;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel50;
    private javax.swing.JPanel jPanel51;
    private javax.swing.JPanel jPanel52;
    private javax.swing.JPanel jPanel53;
    private javax.swing.JPanel jPanel54;
    private javax.swing.JPanel jPanel56;
    private javax.swing.JPanel jPanel57;
    private javax.swing.JPanel jPanel58;
    private javax.swing.JPanel jPanel59;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel60;
    private javax.swing.JPanel jPanel61;
    private javax.swing.JPanel jPanel62;
    private javax.swing.JPanel jPanel63;
    private javax.swing.JPanel jPanel64;
    private javax.swing.JPanel jPanel65;
    private javax.swing.JPanel jPanel66;
    private javax.swing.JPanel jPanel67;
    private javax.swing.JPanel jPanel68;
    private javax.swing.JPanel jPanel69;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel70;
    private javax.swing.JPanel jPanel71;
    private javax.swing.JPanel jPanel72;
    private javax.swing.JPanel jPanel73;
    private javax.swing.JPanel jPanel74;
    private javax.swing.JPanel jPanel75;
    private javax.swing.JPanel jPanel76;
    private javax.swing.JPanel jPanel77;
    private javax.swing.JPanel jPanel78;
    private javax.swing.JPanel jPanel79;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel80;
    private javax.swing.JPanel jPanel81;
    private javax.swing.JPanel jPanel82;
    private javax.swing.JPanel jPanel83;
    private javax.swing.JPanel jPanel84;
    private javax.swing.JPanel jPanel85;
    private javax.swing.JPanel jPanel86;
    private javax.swing.JPanel jPanel87;
    private javax.swing.JPanel jPanel88;
    private javax.swing.JPanel jPanel89;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanel90;
    private javax.swing.JPanel jPanel91;
    private javax.swing.JPanel jPanel92;
    private javax.swing.JPanel jPanel93;
    private javax.swing.JPanel jPanel94;
    private javax.swing.JPanel jPanel95;
    private javax.swing.JPanel jPanel96;
    private javax.swing.JPanel jPanel97;
    private javax.swing.JPanel jPanel98;
    private javax.swing.JPanel jPanel99;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JPasswordField jPasswordField2;
    private javax.swing.JPasswordField jPasswordField3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JPanel noList;
    private javax.swing.JLabel nowPlayingLabel;
    private javax.swing.JLabel searchLabel;
    private javax.swing.JLabel subTotalLabel;
    private javax.swing.JLabel totalLabel;
    private javax.swing.JLabel userCardType;
    // End of variables declaration//GEN-END:variables
}
