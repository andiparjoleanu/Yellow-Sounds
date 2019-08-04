package proiect;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import onlineShop.*;
import java.util.ArrayList;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.table.DefaultTableModel;
import onlineShop.Customer.Card;

public class Settings extends JFrame {

    protected Connection con;
    protected List<Customer> customersList = new ArrayList<Customer>();
    protected String[] adminName = new String[2];
    protected String[] adminUsername = new String[2];
    // protected List<Order> ordersList = new ArrayList<Order>();
    // protected List<String> cardList = new ArrayList<String>();

    protected class Login {

        private JLabel jLabel1;
        private JLabel jLabel2;
        private JLabel jLabel5;
        private JLabel jLabel6;
        private JLabel jLabel7;
        private JButton login;
        private JPanel jPanel1;
        private JPanel jPanel2;
        private JPanel jPanel3;
        private JPanel jPanel4;
        private JTextField jTextField;
        private JPasswordField jPasswordField;
        private JLabel vinylPicture;
        protected Statement st;
        protected ResultSet rez1, rez2, rez3, rez4;
        protected String sql1, sql2, sql3, sql4;
        javax.swing.JLabel[] jLabeli;
        javax.swing.JLabel[] jNames;
        javax.swing.JPanel[] jPaneli;
        int nrJLabels;
        private ResultSet rezArtistIconPath;
        private ResultSet rezCountArtists;
        private String[] a = new String[2];
        private String[] b = new String[2];

        protected Login() {
            try {
                con = DBConnection.getInstance().getConnection();
                st = con.createStatement();
                sql1 = "SELECT username, password FROM administrator ";
                rez1 = st.executeQuery(sql1);
                int i = 0;
                while (rez1.next()) {
                    a[i] = rez1.getString(1);
                    b[i] = rez1.getString(2);
                    i++;
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        protected void removeAll() {
            Settings.this.remove(jLabel1);
            Settings.this.remove(jLabel2);
            Settings.this.remove(jLabel5);
            Settings.this.remove(jLabel6);
            Settings.this.remove(jLabel7);
            Settings.this.remove(jPanel1);
            Settings.this.remove(login);
            Settings.this.remove(jPanel2);
            Settings.this.remove(jPanel3);
            Settings.this.remove(jPanel4);
            Settings.this.remove(jTextField);
            Settings.this.remove(jPasswordField);
            Settings.this.remove(vinylPicture);
        }

        protected void show() {
            jPanel1 = new JPanel();
            jPanel2 = new JPanel();
            jPanel3 = new JPanel();
            jPanel4 = new JPanel();
            jLabel1 = new JLabel();
            jLabel2 = new JLabel();
            jLabel5 = new JLabel();
            jLabel6 = new JLabel();
            jLabel7 = new JLabel();
            jTextField = new JTextField();
            jPasswordField = new JPasswordField();
            login = new JButton();
            vinylPicture = new JLabel();
            GridBagConstraints gridBagConstraints;

            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setBackground(new Color(36, 159, 209));
            setMaximumSize(null);
            setSize(Settings.this.getSize());
            setPreferredSize(Settings.this.getSize());
            jPanel1.setBackground(new Color(255, 204, 0));
            jPanel1.setLayout(new BorderLayout());

            jPanel3.setBackground(new Color(0, 0, 0));
            jPanel3.setPreferredSize(new Dimension(639, 180));
            jPanel3.setLayout(new GridBagLayout());

            jPanel4.setBackground(new Color(0, 0, 0));
            jPanel4.setLayout(new GridBagLayout());

            jLabel1.setBackground(new Color(0, 0, 0));
            jLabel1.setFont(new Font("Century Gothic", 1, 14));
            jLabel1.setForeground(new Color(255, 255, 255));
            jLabel1.setText("Username:   ");
            jLabel1.setToolTipText("");
            gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 1;
            gridBagConstraints.anchor = GridBagConstraints.EAST;
            jPanel4.add(jLabel1, gridBagConstraints);

            jTextField.setBackground(new Color(0, 0, 0));
            jTextField.setFont(new Font("Century Gothic", 2, 12));
            jTextField.setForeground(new Color(255, 255, 255));
            jTextField.setText("example.123");
            jTextField.setToolTipText("");
            jTextField.setBorder(null);
            jTextField.setCaretColor(new Color(255, 255, 255));
            jTextField.setMaximumSize(new Dimension(120, 16));

            jTextField.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 3;
            gridBagConstraints.gridy = 1;
            gridBagConstraints.gridwidth = 3;
            gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
            jPanel4.add(jTextField, gridBagConstraints);

            jLabel2.setBackground(new Color(0, 0, 0));
            jLabel2.setFont(new Font("Century Gothic", 1, 14));
            jLabel2.setForeground(new Color(255, 255, 255));
            jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
            jLabel2.setText("Password:   ");
            gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 4;
            gridBagConstraints.anchor = GridBagConstraints.EAST;
            jPanel4.add(jLabel2, gridBagConstraints);

            jPasswordField.setBackground(new Color(0, 0, 0));
            jPasswordField.setForeground(new Color(255, 255, 255));
            jPasswordField.setText("jPassword");
            jPasswordField.setBorder(null);
            jPasswordField.setSelectedTextColor(new Color(0, 0, 0));
            jPasswordField.setSelectionColor(new Color(204, 204, 204));
            jPasswordField.setMaximumSize(new Dimension(120, 16));

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 3;
            gridBagConstraints.gridy = 4;
            gridBagConstraints.gridwidth = 3;
            gridBagConstraints.fill = GridBagConstraints.BOTH;
            jPanel4.add(jPasswordField, gridBagConstraints);

            jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
            jLabel5.setIcon(new javax.swing.ImageIcon("C:\\JavaProiect\\Proiectv2\\Photo\\line.png"));
            jLabel5.setToolTipText("");
            jLabel5.setMaximumSize(new java.awt.Dimension(120, 1));
            jLabel5.setMinimumSize(new java.awt.Dimension(0, 1));
            jLabel5.setPreferredSize(new java.awt.Dimension(120, 1));
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 3;
            gridBagConstraints.gridy = 2;
            gridBagConstraints.weighty = 0.2;
            jPanel4.add(jLabel5, gridBagConstraints);

            jLabel6.setIcon(new ImageIcon("C:\\JavaProiect\\Proiectv2\\Photo\\line.png")); // NOI18N
            jLabel6.setMaximumSize(new Dimension(120, 1));
            jLabel6.setMinimumSize(new Dimension(0, 1));
            jLabel6.setPreferredSize(new Dimension(120, 1));
            gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 3;
            gridBagConstraints.gridy = 5;
            jPanel4.add(jLabel6, gridBagConstraints);
            gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 3;
            gridBagConstraints.gridy = 3;
            gridBagConstraints.insets = new Insets(20, 0, 0, 0);
            jPanel4.add(jLabel7, gridBagConstraints);

            gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 4;
            gridBagConstraints.gridy = 1;
            jPanel3.add(jPanel4, gridBagConstraints);

            /*jPanel2.setBackground(new Color(0, 0, 0));
            jPanel2.setLayout(new GridBagLayout());

            gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 12;
            gridBagConstraints.gridy = 2;
            gridBagConstraints.insets = new Insets(0, 100, 0, 0);
            jPanel3.add(jPanel2, gridBagConstraints);*/

            login.setBackground(new Color(255, 255, 255));
            login.setFont(new Font("Century Gothic", 3, 14));
            login.setText("Login as Administrator");

            login.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    
                        loginActionPerformed(evt);
                    
                }

                private void loginActionPerformed(ActionEvent evt) {
                   
                    if (checkUsername() == false) {

                        JOptionPane.showMessageDialog(null,
                                "Username or Password is incorrect", "Error",
                                JOptionPane.WARNING_MESSAGE);
                    } else {
                        removeAll();
                        addArtist.show();
                    }

                }
            });

            gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 4;
            gridBagConstraints.gridy = 2;
            gridBagConstraints.insets = new Insets(20, 0, 0, 0);
            jPanel3.add(login, gridBagConstraints);

            jPanel1.add(jPanel3, BorderLayout.PAGE_END);

            vinylPicture.setIcon(new ImageIcon("C:\\JavaProiect\\Proiectv2\\Photo\\vinyl_start.png"));
            vinylPicture.setBackground(new Color(255, 255, 255));
            vinylPicture.setHorizontalAlignment(SwingConstants.CENTER);
            vinylPicture.setToolTipText("");
            vinylPicture.setAlignmentX(10.0F);
            vinylPicture.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            jPanel1.add(vinylPicture, BorderLayout.PAGE_START);

            getContentPane().add(jPanel1, BorderLayout.CENTER);

            getContentPane().addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    if (e.getSource() instanceof JPanel) {
                        jTextField.setText("example.123");
                        jPasswordField.setText("parola");
                    }
                }
            });

            pack();
        }

        private boolean checkUsername()
        {
            int i;
            String usr = new String(jTextField.getText());
            String pw = new String(jPasswordField.getText());
            for (i = 0; i < 2; i++) {
                if (usr.compareTo(a[i]) == 0 && pw.compareTo(b[i]) == 0) {
                    return true;
                }
            }
            return false;
        }

    }

    private class AddArtist {

        private List<Track> tracksList = new ArrayList<>();
        private javax.swing.JLabel addGenre;
        private javax.swing.JTextField addGenreTextField;
        private javax.swing.JPanel administratorsButton;
        private javax.swing.JLabel administratorsLabel;
        private javax.swing.JPanel artistsButton;
        private javax.swing.JLabel artistsLabel;
        private javax.swing.JPanel customersButton;
        private javax.swing.JLabel customersLabel;
        private javax.swing.JPanel editArtistButton;
        private javax.swing.JLabel editArtistLabel;
        private javax.swing.JLabel iconPhotoLabel;
        private javax.swing.JButton jButton6;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel jLabel10;
        private javax.swing.JLabel jLabel11;
        private javax.swing.JLabel jLabel13;
        private javax.swing.JLabel jLabel2;
        private javax.swing.JLabel jLabel3;
        private javax.swing.JLabel jLabel4;
        private javax.swing.JLabel jLabel5;
        private javax.swing.JLabel jLabel6;
        private javax.swing.JLabel jLabel7;
        private javax.swing.JLabel jLabel8;
        private javax.swing.JLabel jLabel9;
        private javax.swing.JPanel jPanel1;
        private javax.swing.JPanel jPanel10;
        private javax.swing.JPanel jPanel11;
        private javax.swing.JPanel jPanel12;
        private javax.swing.JPanel jPanel13;
        private javax.swing.JPanel jPanel14;
        private javax.swing.JPanel jPanel15;
        private javax.swing.JPanel jPanel16;
        private javax.swing.JPanel jPanel2;
        private javax.swing.JPanel jPanel3;
        private javax.swing.JPanel jPanel5;
        private javax.swing.JPanel jPanel6;
        private javax.swing.JPanel jPanel7;
        private javax.swing.JPanel jPanel8;
        private javax.swing.JPanel jPanel9;
        private javax.swing.JScrollPane jScrollPane1;
        private javax.swing.JScrollPane jScrollPane2;
        private javax.swing.JScrollPane jScrollPane3;
        private javax.swing.JTextArea jTextArea1;
        private javax.swing.JTextField jTextField1;
        private javax.swing.JTextField jTextField2;
        private javax.swing.JTextField jTextField3;
        private javax.swing.JTextField jTextField4;
        private javax.swing.JPanel logOutButton;
        private javax.swing.JLabel logOutLabel;
        private javax.swing.JPanel ordersButton;
        private javax.swing.JLabel ordersLabel;
        private javax.swing.JLabel profilePhotoLabel;
        private javax.swing.JList<String> tracksJList;
        private List<String> directoriesList = new ArrayList<>();
        private List<String> tracksName = new ArrayList<>();
        private String label8;
        private String profilePhoto;
        private String iconPhoto;

        protected void show() {
            java.awt.GridBagConstraints gridBagConstraints;

            jPanel1 = new javax.swing.JPanel();
            artistsButton = new javax.swing.JPanel();
            artistsLabel = new javax.swing.JLabel();
            customersButton = new javax.swing.JPanel();
            customersLabel = new javax.swing.JLabel();
            ordersButton = new javax.swing.JPanel();
            ordersLabel = new javax.swing.JLabel();
            administratorsButton = new javax.swing.JPanel();
            administratorsLabel = new javax.swing.JLabel();
            editArtistButton = new javax.swing.JPanel();
            editArtistLabel = new javax.swing.JLabel();
            logOutButton = new javax.swing.JPanel();
            logOutLabel = new javax.swing.JLabel();
            jScrollPane1 = new javax.swing.JScrollPane();
            jPanel2 = new javax.swing.JPanel();
            jLabel1 = new javax.swing.JLabel();
            jLabel2 = new javax.swing.JLabel();
            jTextField1 = new javax.swing.JTextField();
            jLabel3 = new javax.swing.JLabel();
            jScrollPane2 = new javax.swing.JScrollPane();
            jTextArea1 = new javax.swing.JTextArea();
            jPanel3 = new javax.swing.JPanel();
            jLabel4 = new javax.swing.JLabel();
            jPanel5 = new javax.swing.JPanel();
            jLabel5 = new javax.swing.JLabel();
            jPanel6 = new javax.swing.JPanel();
            jPanel7 = new javax.swing.JPanel();
            jScrollPane3 = new javax.swing.JScrollPane();
            tracksJList = new javax.swing.JList<>();
            jLabel6 = new javax.swing.JLabel();
            jTextField2 = new javax.swing.JTextField();
            jPanel8 = new javax.swing.JPanel();
            jLabel7 = new javax.swing.JLabel();
            jLabel8 = new javax.swing.JLabel();
            jPanel9 = new javax.swing.JPanel();
            jLabel9 = new javax.swing.JLabel();
            jTextField3 = new javax.swing.JTextField();
            jLabel10 = new javax.swing.JLabel();
            jTextField4 = new javax.swing.JTextField();
            jPanel10 = new javax.swing.JPanel();
            jPanel11 = new javax.swing.JPanel();
            jPanel12 = new javax.swing.JPanel();
            jButton6 = new javax.swing.JButton();
            jLabel11 = new javax.swing.JLabel();
            jPanel13 = new javax.swing.JPanel();
            profilePhotoLabel = new javax.swing.JLabel();
            jPanel14 = new javax.swing.JPanel();
            jLabel13 = new javax.swing.JLabel();
            jPanel15 = new javax.swing.JPanel();
            addGenre = new javax.swing.JLabel();
            addGenreTextField = new javax.swing.JTextField();
            jPanel16 = new javax.swing.JPanel();
            iconPhotoLabel = new javax.swing.JLabel();

            setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
            setBackground(new java.awt.Color(0, 0, 0));
            setFocusCycleRoot(false);
            setSize(Settings.this.getSize());
            setPreferredSize(Settings.this.getSize());
            jPanel1.setBackground(new java.awt.Color(51, 51, 51));
            jPanel1.setMinimumSize(new java.awt.Dimension(0, 0));
            jPanel1.setName(""); // NOI18N
            jPanel1.setPreferredSize(new java.awt.Dimension(200, 0));
            jPanel1.setLayout(new java.awt.GridBagLayout());

            artistsButton.setBackground(new java.awt.Color(255, 204, 0));
            artistsButton.setForeground(new java.awt.Color(51, 51, 51));
            artistsButton.setMinimumSize(new java.awt.Dimension(0, 10));
            artistsButton.setPreferredSize(new java.awt.Dimension(200, 30));

            artistsLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
            artistsLabel.setForeground(new java.awt.Color(51, 51, 51));
            artistsLabel.setText("Add new artist");
            artistsButton.add(artistsLabel);

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 3;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel1.add(artistsButton, gridBagConstraints);

            customersButton.setBackground(new java.awt.Color(51, 51, 51));
            customersButton.setMinimumSize(new java.awt.Dimension(200, 30));
            customersButton.setPreferredSize(new java.awt.Dimension(200, 30));
            customersButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    customersButtonMouseClicked(evt);
                }

                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    customersButtonMouseEntered(evt);
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    customersButtonMouseExited(evt);
                }
            });

            customersLabel.setBackground(new java.awt.Color(255, 255, 255));
            customersLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
            customersLabel.setForeground(new java.awt.Color(255, 255, 255));
            customersLabel.setText("Customers");
            customersButton.add(customersLabel);

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 5;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel1.add(customersButton, gridBagConstraints);

            ordersButton.setBackground(new java.awt.Color(51, 51, 51));
            ordersButton.setMinimumSize(new java.awt.Dimension(200, 30));
            ordersButton.setPreferredSize(new java.awt.Dimension(200, 30));
            ordersButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    ordersButtonMouseClicked(evt);
                }

                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    ordersButtonMouseEntered(evt);
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    ordersButtonMouseExited(evt);
                }
            });

            ordersLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
            ordersLabel.setForeground(new java.awt.Color(255, 255, 255));
            ordersLabel.setText("Orders");
            ordersButton.add(ordersLabel);

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 7;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel1.add(ordersButton, gridBagConstraints);

            administratorsButton.setBackground(new java.awt.Color(51, 51, 51));
            administratorsButton.setMinimumSize(new java.awt.Dimension(105, 30));
            administratorsButton.setPreferredSize(new java.awt.Dimension(105, 30));
            administratorsButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    administratorsButtonMouseClicked(evt);
                }

                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    administratorsButtonMouseEntered(evt);
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    administratorsButtonMouseExited(evt);
                }
            });

            administratorsLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
            administratorsLabel.setForeground(new java.awt.Color(255, 255, 255));
            administratorsLabel.setText("Administrators");
            administratorsButton.add(administratorsLabel);

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 6;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel1.add(administratorsButton, gridBagConstraints);

            editArtistButton.setBackground(new java.awt.Color(51, 51, 51));
            editArtistButton.setForeground(new java.awt.Color(51, 51, 51));
            editArtistButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    editArtistButtonMouseClicked(evt);
                }

                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    editArtistButtonMouseEntered(evt);
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    editArtistButtonMouseExited(evt);
                }
            });

            editArtistLabel.setBackground(new java.awt.Color(51, 51, 51));
            editArtistLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
            editArtistLabel.setForeground(new java.awt.Color(255, 255, 255));
            editArtistLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            editArtistLabel.setText("Stock");
            editArtistLabel.setToolTipText("");
            editArtistLabel.setMaximumSize(new java.awt.Dimension(83, 19));
            editArtistLabel.setMinimumSize(new java.awt.Dimension(83, 19));
            editArtistLabel.setPreferredSize(new java.awt.Dimension(83, 19));
            editArtistLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    editArtistLabelMouseEntered(evt);
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    editArtistLabelMouseExited(evt);
                }

                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    editArtistLabelMouseClicked(evt);
                }
            });
            editArtistButton.add(editArtistLabel);

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 4;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel1.add(editArtistButton, gridBagConstraints);

            logOutButton.setBackground(new java.awt.Color(51, 51, 51));
            logOutButton.setMaximumSize(new java.awt.Dimension(200, 30));
            logOutButton.setMinimumSize(new java.awt.Dimension(200, 30));
            logOutButton.setPreferredSize(new java.awt.Dimension(200, 30));
            logOutButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    logOutButtonMouseClicked(evt);
                }

                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    logOutButtonMouseEntered(evt);
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    logOutButtonMouseExited(evt);
                }
            });

            logOutLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
            logOutLabel.setForeground(new java.awt.Color(255, 255, 255));
            logOutLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            logOutLabel.setText("LOG OUT");
            logOutLabel.setMaximumSize(new java.awt.Dimension(95, 19));
            logOutLabel.setMinimumSize(new java.awt.Dimension(95, 19));
            logOutLabel.setPreferredSize(new java.awt.Dimension(95, 19));
            logOutButton.add(logOutLabel);
            logOutLabel.getAccessibleContext().setAccessibleParent(administratorsButton);

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 8;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel1.add(logOutButton, gridBagConstraints);

            getContentPane().add(jPanel1, java.awt.BorderLayout.LINE_START);

            jScrollPane1.setBackground(new java.awt.Color(102, 102, 102));
            jScrollPane1.setMaximumSize(new java.awt.Dimension(601, 550));
            jScrollPane1.setMinimumSize(new java.awt.Dimension(601, 550));
            jScrollPane1.setPreferredSize(new java.awt.Dimension(601, 550));

            jPanel2.setBackground(new java.awt.Color(102, 102, 102));
            jPanel2.setMaximumSize(new java.awt.Dimension(0, 5000));
            jPanel2.setMinimumSize(new java.awt.Dimension(0, 1000));
            jPanel2.setLayout(new java.awt.GridBagLayout());

            jLabel1.setBackground(new java.awt.Color(102, 102, 102));
            jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
            jLabel1.setForeground(new java.awt.Color(255, 204, 0));
            jLabel1.setText("New Artist");
            jLabel1.setMaximumSize(new java.awt.Dimension(116, 200));
            jLabel1.setMinimumSize(new java.awt.Dimension(116, 200));
            jLabel1.setPreferredSize(new java.awt.Dimension(116, 200));
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints.weightx = 0.2;
            gridBagConstraints.weighty = 0.2;
            jPanel2.add(jLabel1, gridBagConstraints);

            jLabel2.setBackground(new java.awt.Color(204, 204, 204));
            jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
            jLabel2.setForeground(new java.awt.Color(204, 204, 204));
            jLabel2.setText("Name");
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 1;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(jLabel2, gridBagConstraints);

            jTextField1.setBackground(new java.awt.Color(51, 51, 51));
            jTextField1.setForeground(new java.awt.Color(255, 255, 255));
            jTextField1.setText("Write the artist's name here...");
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 2;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(jTextField1, gridBagConstraints);

            jLabel3.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
            jLabel3.setForeground(new java.awt.Color(204, 204, 204));
            jLabel3.setText("Info");
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 4;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(jLabel3, gridBagConstraints);

            jScrollPane2.setMaximumSize(new java.awt.Dimension(104, 64));
            jScrollPane2.setMinimumSize(new java.awt.Dimension(104, 64));
            jScrollPane2.setPreferredSize(new java.awt.Dimension(104, 64));

            jTextArea1.setBackground(new java.awt.Color(51, 51, 51));
            jTextArea1.setColumns(20);
            jTextArea1.setForeground(new java.awt.Color(255, 255, 255));
            jTextArea1.setRows(5);
            jTextArea1.setMaximumSize(new java.awt.Dimension(104, 64));
            jTextArea1.setMinimumSize(new java.awt.Dimension(104, 64));
            jScrollPane2.setViewportView(jTextArea1);

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 5;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(jScrollPane2, gridBagConstraints);

            jPanel3.setBackground(new java.awt.Color(102, 102, 102));
            jPanel3.setMinimumSize(new java.awt.Dimension(10, 30));
            jPanel3.setPreferredSize(new java.awt.Dimension(10, 30));
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 3;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(jPanel3, gridBagConstraints);

            jLabel4.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
            jLabel4.setForeground(new java.awt.Color(204, 204, 204));
            jLabel4.setText("Add tracks");
            jLabel4.setToolTipText("");
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 28;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(jLabel4, gridBagConstraints);

            jPanel5.setBackground(new java.awt.Color(51, 51, 51));
            jPanel5.setMinimumSize(new java.awt.Dimension(80, 60));
            jPanel5.setPreferredSize(new java.awt.Dimension(80, 60));
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

            jLabel5.setBackground(new java.awt.Color(255, 204, 51));
            jLabel5.setFont(new java.awt.Font("Century Gothic", 1, 48)); // NOI18N
            jLabel5.setForeground(new java.awt.Color(255, 204, 51));
            jLabel5.setText("+");
            jPanel5.add(jLabel5, new java.awt.GridBagConstraints());

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 30;
            gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
            jPanel2.add(jPanel5, gridBagConstraints);

            jPanel6.setBackground(new java.awt.Color(102, 102, 102));
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 29;
            jPanel2.add(jPanel6, gridBagConstraints);

            jPanel7.setBackground(new java.awt.Color(102, 102, 102));
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 31;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(jPanel7, gridBagConstraints);

            jScrollPane3.setMinimumSize(new java.awt.Dimension(23, 130));

            tracksJList.setBackground(new java.awt.Color(51, 51, 51));
            tracksJList.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
            tracksJList.setForeground(new java.awt.Color(255, 255, 255));
            tracksJList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
            tracksJList.setToolTipText("");
            tracksJList.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            tracksJList.setMinimumSize(new java.awt.Dimension(0, 50));
            tracksJList.setPreferredSize(new java.awt.Dimension(0, 50));
            tracksJList.setSelectionBackground(new java.awt.Color(102, 102, 102));
            tracksJList.addListSelectionListener
            (new javax.swing.event.ListSelectionListener() {
                public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                    tracksJListValueChanged(evt);
                }
            });
            jScrollPane3.setViewportView(tracksJList);

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 32;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(jScrollPane3, gridBagConstraints);

            jLabel6.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
            jLabel6.setForeground(new java.awt.Color(204, 204, 204));
            jLabel6.setText("Create an album");
            jLabel6.setToolTipText("");
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 16;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(jLabel6, gridBagConstraints);

            jTextField2.setBackground(new java.awt.Color(51, 51, 51));
            jTextField2.setForeground(new java.awt.Color(255, 255, 255));
            jTextField2.setText("Write the title here....");
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 17;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(jTextField2, gridBagConstraints);

            jPanel8.setBackground(new java.awt.Color(102, 102, 102));
            jPanel8.setInheritsPopupMenu(true);
            jPanel8.setMinimumSize(new java.awt.Dimension(10, 30));
            jPanel8.setPreferredSize(new java.awt.Dimension(10, 30));
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 18;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(jPanel8, gridBagConstraints);

            jLabel7.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
            jLabel7.setForeground(new java.awt.Color(204, 204, 204));
            jLabel7.setText("Add a cover photo");
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 19;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(jLabel7, gridBagConstraints);

            jLabel8.setIcon(new javax.swing.ImageIcon("C:\\JavaProiect\\"
                    + "Proiectv2\\Photo\\music-record.png")); // NOI18N
            jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    jLabel8MouseClicked(evt);
                }
            });
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 20;
            jPanel2.add(jLabel8, gridBagConstraints);

            jPanel9.setBackground(new java.awt.Color(102, 102, 102));
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 21;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(jPanel9, gridBagConstraints);

            jLabel9.setBackground(new java.awt.Color(204, 204, 204));
            jLabel9.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
            jLabel9.setForeground(new java.awt.Color(204, 204, 204));
            jLabel9.setText("Set the price for the album");
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 22;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(jLabel9, gridBagConstraints);

            jTextField3.setBackground(new java.awt.Color(51, 51, 51));
            jTextField3.setForeground(new java.awt.Color(255, 255, 255));
            jTextField3.setText("0.00 RON");
            jTextField3.setToolTipText("");
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 23;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(jTextField3, gridBagConstraints);

            jLabel10.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
            jLabel10.setForeground(new java.awt.Color(204, 204, 204));
            jLabel10.setText("Set quantity");
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 25;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(jLabel10, gridBagConstraints);

            jTextField4.setBackground(new java.awt.Color(51, 51, 51));
            jTextField4.setForeground(new java.awt.Color(255, 255, 255));
            jTextField4.setText("0");
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 26;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(jTextField4, gridBagConstraints);

            jPanel10.setBackground(new java.awt.Color(102, 102, 102));
            jPanel10.setMinimumSize(new java.awt.Dimension(10, 30));
            jPanel10.setPreferredSize(new java.awt.Dimension(10, 30));
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 27;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(jPanel10, gridBagConstraints);

            jPanel11.setBackground(new java.awt.Color(102, 102, 102));
            jPanel11.setMinimumSize(new java.awt.Dimension(10, 30));
            jPanel11.setPreferredSize(new java.awt.Dimension(10, 30));
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 24;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(jPanel11, gridBagConstraints);

            jPanel12.setBackground(new java.awt.Color(102, 102, 102));
            jPanel12.setMinimumSize(new java.awt.Dimension(10, 20));
            jPanel12.setPreferredSize(new java.awt.Dimension(10, 20));
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 33;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(jPanel12, gridBagConstraints);

            jButton6.setBackground(new java.awt.Color(255, 204, 0));
            jButton6.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
            jButton6.setText("Save");
            jButton6.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton6ActionPerformed(evt);
                }
            });
            jButton6.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    jButton6MouseClicked(evt);
                }
            });
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 34;
            gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
            jPanel2.add(jButton6, gridBagConstraints);

            jLabel11.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
            jLabel11.setForeground(new java.awt.Color(204, 204, 204));
            jLabel11.setText("Add a profile photo");
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 7;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(jLabel11, gridBagConstraints);

            jPanel13.setBackground(new java.awt.Color(102, 102, 102));
            jPanel13.setMaximumSize(new java.awt.Dimension(10, 30));
            jPanel13.setMinimumSize(new java.awt.Dimension(10, 30));
            jPanel13.setPreferredSize(new java.awt.Dimension(10, 30));
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 6;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(jPanel13, gridBagConstraints);

            profilePhotoLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            profilePhotoLabel.setIcon(new javax.swing.ImageIcon("C:\\JavaProiect\\"
                    + "Proiectv2\\Photo\\music-record.png")); // NOI18N
            profilePhotoLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    profilePhotoLabelMouseClicked(evt);
                }
            });
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 8;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(profilePhotoLabel, gridBagConstraints);

            jPanel14.setBackground(new java.awt.Color(102, 102, 102));
            jPanel14.setMaximumSize(new java.awt.Dimension(10, 30));
            jPanel14.setMinimumSize(new java.awt.Dimension(10, 30));
            jPanel14.setPreferredSize(new java.awt.Dimension(10, 30));
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 9;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(jPanel14, gridBagConstraints);

            jLabel13.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
            jLabel13.setForeground(new java.awt.Color(204, 204, 204));
            jLabel13.setText("Add a icon photo");
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 10;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(jLabel13, gridBagConstraints);

            jPanel15.setBackground(new java.awt.Color(102, 102, 102));
            jPanel15.setMaximumSize(new java.awt.Dimension(10, 30));
            jPanel15.setMinimumSize(new java.awt.Dimension(10, 30));
            jPanel15.setPreferredSize(new java.awt.Dimension(10, 30));
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 12;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(jPanel15, gridBagConstraints);

            addGenre.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
            addGenre.setForeground(new java.awt.Color(204, 204, 204));
            addGenre.setText("Set genre");
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 13;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(addGenre, gridBagConstraints);

            addGenreTextField.setBackground(new java.awt.Color(51, 51, 51));
            addGenreTextField.setForeground(new java.awt.Color(255, 255, 255));
            addGenreTextField.setText("Pop/Rock/Hip-hop/..");
            addGenreTextField.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    addGenreTextFieldActionPerformed(evt);
                }
            });
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 14;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(addGenreTextField, gridBagConstraints);

            jPanel16.setBackground(new java.awt.Color(102, 102, 102));
            jPanel16.setMaximumSize(new java.awt.Dimension(10, 30));
            jPanel16.setMinimumSize(new java.awt.Dimension(10, 30));
            jPanel16.setPreferredSize(new java.awt.Dimension(10, 30));
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 15;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(jPanel16, gridBagConstraints);

            iconPhotoLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            iconPhotoLabel.setIcon(new javax.swing.ImageIcon("C:\\JavaProiect\\"
                                          + "Proiectv2\\Photo\\music-record.png")); // NOI18N
            iconPhotoLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    iconPhotoLabelMouseClicked(evt);
                }
            });
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 11;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(iconPhotoLabel, gridBagConstraints);

            jScrollPane1.setViewportView(jPanel2);

            getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

            pack();
        }

        private void jButton6MouseClicked(java.awt.event.MouseEvent evt) {
            PreparedStatement preparedStatement = null;
            String insertArtist = "insert into artist "
                    + "(name, info, profilePhotoPath, iconPhotoPath, album, genre) "
                    + "VALUES" + "(?,?,?,?,?,?)";
            try {
                preparedStatement = con.prepareStatement(insertArtist);
                preparedStatement.setString(1, jTextField1.getText());   //artist name
                preparedStatement.setString(2, jTextArea1.getText());    //info
                preparedStatement.setString(3, profilePhoto);
                preparedStatement.setString(4, iconPhoto);
                preparedStatement.setString(5, jTextField2.getText());   //album name
                preparedStatement.setString(6, addGenreTextField.getText());
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Changes saved!", "Succes",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        private void customersButtonMouseEntered(java.awt.event.MouseEvent evt) {
            customersButton.setBackground(new Color(255, 204, 0));
            customersLabel.setForeground(Color.DARK_GRAY);
        }

        private void editArtistButtonMouseEntered(java.awt.event.MouseEvent evt) {
            editArtistButton.setBackground(new Color(255, 204, 0));
            editArtistLabel.setForeground(Color.DARK_GRAY);
        }

        private void addGenreTextFieldActionPerformed(java.awt.event.ActionEvent evt) {
            // TODO add your handling code here:
        }

        private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {
            // TODO add your handling code here:
        }

        private void customersButtonMouseExited(java.awt.event.MouseEvent evt) {
            customersButton.setBackground(new Color(51, 51, 51));
            customersLabel.setForeground(Color.WHITE);
        }

        private void administratorsButtonMouseEntered(java.awt.event.MouseEvent evt) {
            administratorsButton.setBackground(new Color(255, 204, 0));
            administratorsLabel.setForeground(Color.DARK_GRAY);
        }

        private void administratorsButtonMouseExited(java.awt.event.MouseEvent evt) {
            administratorsButton.setBackground(new Color(51, 51, 51));
            administratorsLabel.setForeground(Color.WHITE);
        }

        private void ordersButtonMouseEntered(java.awt.event.MouseEvent evt) {
            ordersButton.setBackground(new Color(255, 204, 0));
            ordersLabel.setForeground(Color.DARK_GRAY);
        }

        private void ordersButtonMouseExited(java.awt.event.MouseEvent evt) {
            ordersButton.setBackground(new Color(51, 51, 51));
            ordersLabel.setForeground(Color.WHITE);
        }

        private void jPanel5MouseEntered(java.awt.event.MouseEvent evt) {
            jPanel5.setBackground(new Color(255, 204, 0));
            jLabel5.setForeground(Color.DARK_GRAY);

        }

        private void jPanel5MouseExited(java.awt.event.MouseEvent evt) {
            jPanel5.setBackground(new Color(51, 51, 51));
            jLabel5.setForeground(new Color(255, 204, 0));
        }

        private void jPanel5MouseClicked(java.awt.event.MouseEvent evt) {
            FileDialog fd = new FileDialog(new Frame(), "File Open");
            fd.setFile("*.wav");
            fd.show();
            directoriesList.add(fd.getDirectory() + fd.getFile());
            tracksName.add(fd.getFile());
            tracksJList.setListData(tracksName.toArray(new String[tracksName.size()]));

        }

        private void tracksJListValueChanged(javax.swing.event.ListSelectionEvent evt) {
            if (tracksJList.getSelectedIndex() != -1) {
                PreparedStatement preparedStatement = null;
                String insertTrack = "insert into track "
                        + "( name,startMicroseconds , path) VALUES" + "(?,?,?)";
                String insertAlbum = "insert into album "
                        + "( name, track, coverPath, price, quantity) VALUES"
                        + "(?,?,?,?,?)";
                String str = (String) JOptionPane.showInputDialog(null, "Set "
                        + "preview time " + 
                        tracksName.get(tracksJList.getSelectedIndex()),
                        "Track Settings", JOptionPane.PLAIN_MESSAGE, null, null,
                        "0 microseconds");
                long s = Integer.parseInt(str);

                Track t = new Track(tracksName.get(tracksJList.getSelectedIndex()),
                        s, directoriesList.get(tracksJList.getSelectedIndex()));
                tracksList.add(t);

                try {
                    preparedStatement = con.prepareStatement(insertTrack);
                    preparedStatement.setString(1, t.getName().substring(0, 
                            t.getName().length() - 4));
                    preparedStatement.setLong(2, t.getStartMicroseconds());
                    preparedStatement.setString(3, t.getPath());
                    preparedStatement.executeUpdate();

                    preparedStatement = con.prepareStatement(insertAlbum);
                    preparedStatement.setString(1, jTextField2.getText());   //album name
                    preparedStatement.setString(2, t.getName().substring(0, 
                                                   t.getName().length() - 4));     //track name
                    preparedStatement.setString(3, label8);   //coverpath
                    preparedStatement.setInt(4, Integer.parseInt(jTextField3.getText()));    //price
                    preparedStatement.setInt(5, Integer.parseInt(jTextField4.getText())); //quantity
                    preparedStatement.executeUpdate();

                } catch (MySQLIntegrityConstraintViolationException ex) {
                    Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            //  tracksJList.setListData(directoriesList.toArray(new String[directoriesList.size()]));
            tracksJList.setSelectedIndex(-1);
        }

        private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {
            FileDialog fd = new FileDialog(new Frame(), "File Open");
            fd.setFile("*.png");
            fd.show();
            if (fd.getFile() == null) {
                jLabel8.setIcon(new ImageIcon("C:\\Users\\Andi\\Desktop"
                                              + "\\music-record.png"));
            } else {
                jLabel8.setIcon(new ImageIcon(fd.getDirectory() + "\\" + fd.getFile()));
            }
            label8 = fd.getDirectory() + "\\" + fd.getFile();
            jLabel8.setSize(200, 200);
        }

        private void editArtistLabelMouseEntered(java.awt.event.MouseEvent evt) {
            editArtistButton.setBackground(new Color(255, 204, 0));
            editArtistLabel.setForeground(Color.DARK_GRAY);
        }

        private void editArtistButtonMouseClicked(java.awt.event.MouseEvent evt) {
            this.removeAll();
            editArtist.show();

        }

        private void editArtistLabelMouseClicked(java.awt.event.MouseEvent evt) {

            editArtistButtonMouseClicked(evt);
        }

        private void administratorsButtonMouseClicked(java.awt.event.MouseEvent evt) {
            this.removeAll();
            administrators.show();
        }

        private void administratorsLabelMouseClicked(java.awt.event.MouseEvent evt) {

            administratorsButtonMouseClicked(evt);
        }

        private void ordersButtonMouseClicked(java.awt.event.MouseEvent evt) {
            this.removeAll();
            orders.show();
        }

        private void ordersLabelMouseClicked(java.awt.event.MouseEvent evt) {

            ordersButtonMouseClicked(evt);
        }

        private void customersButtonMouseClicked(java.awt.event.MouseEvent evt) {
                this.removeAll();
                customers.show();
          
        }

        private void customersLabelMouseClicked(java.awt.event.MouseEvent evt) {

            customersButtonMouseClicked(evt);
        }

        private void editArtistLabelMouseExited(java.awt.event.MouseEvent evt) {
            editArtistButton.setBackground(new Color(51, 51, 51));
            editArtistLabel.setForeground(Color.WHITE);
        }

        private void logOutButtonMouseClicked(java.awt.event.MouseEvent evt) {
            removeAll();
            login.show();
        }

        private void logOutLabelMouseClicked(java.awt.event.MouseEvent evt) {
            logOutButtonMouseClicked(evt);
        }

        private void logOutButtonMouseEntered(java.awt.event.MouseEvent evt) {
            logOutButton.setBackground(new Color(255, 204, 0));
            logOutLabel.setForeground(Color.DARK_GRAY);
        }

        private void logOutButtonMouseExited(java.awt.event.MouseEvent evt) {
            logOutButton.setBackground(new Color(51, 51, 51));
            logOutLabel.setForeground(Color.WHITE);
        }

        private void logOutLabelMouseEntered(java.awt.event.MouseEvent evt) {
            logOutButton.setBackground(new Color(255, 204, 0));
            logOutLabel.setForeground(Color.DARK_GRAY);
        }

        private void logOutLabelMouseExited(java.awt.event.MouseEvent evt) {
            logOutButton.setBackground(new Color(51, 51, 51));
            logOutLabel.setForeground(Color.WHITE);
        }

        private void editArtistButtonMouseExited(java.awt.event.MouseEvent evt) {
            editArtistButton.setBackground(new Color(51, 51, 51));
            editArtistLabel.setForeground(Color.WHITE);
        }

        private void profilePhotoLabelMouseClicked(java.awt.event.MouseEvent evt) {
            FileDialog fd = new FileDialog(new Frame(), "File Open");
            fd.setFile("*.png");
            fd.show();
            if (fd.getFile() == null) {
                profilePhotoLabel.setIcon(new ImageIcon("C:\\JavaProiect\\"
                        + "Proiectv2\\Photo\\music-record.png"));
            } else {
                profilePhotoLabel.setIcon(new ImageIcon(fd.getDirectory() + "\\" 
                                                        + fd.getFile()));
            }
            profilePhotoLabel.setSize(200, 200);
            profilePhoto = fd.getDirectory() + "\\" + fd.getFile();

        }

        private void iconPhotoLabelMouseClicked(java.awt.event.MouseEvent evt) {
            FileDialog fd = new FileDialog(new Frame(), "File Open");
            fd.setFile("*.png");
            fd.show();
            if (fd.getFile() == null) {
                iconPhotoLabel.setIcon(new ImageIcon("C:\\JavaProiect\\"
                        + "Proiectv2\\Photo\\music-record.png"));
            } else {
                iconPhotoLabel.setIcon(new ImageIcon(fd.getDirectory() + "\\"
                                                     + fd.getFile()));
            }
            iconPhotoLabel.setSize(200, 200);
            iconPhoto = fd.getDirectory() + "\\" + fd.getFile();
        }

        protected void removeAll() {
            Settings.this.remove(artistsButton);
            Settings.this.remove(jPanel1);
            Settings.this.remove(artistsLabel);
            Settings.this.remove(customersButton);
            Settings.this.remove(customersLabel);
            Settings.this.remove(ordersButton);
            Settings.this.remove(ordersLabel);
            Settings.this.remove(administratorsLabel);
            Settings.this.remove(administratorsButton);
            Settings.this.remove(jScrollPane1);
            Settings.this.remove(jPanel2);
            Settings.this.remove(jLabel1);
            Settings.this.remove(jLabel2);
            Settings.this.remove(jTextField1);
            Settings.this.remove(jLabel3);
            Settings.this.remove(jScrollPane2);
            Settings.this.remove(jTextArea1);
            Settings.this.remove(jPanel3);
            Settings.this.remove(jLabel4);
            Settings.this.remove(jPanel5);
            Settings.this.remove(jLabel5);
            Settings.this.remove(jPanel6);
            Settings.this.remove(jPanel7);
            Settings.this.remove(jScrollPane3);
            Settings.this.remove(jLabel6);
            Settings.this.remove(jTextField2);
            Settings.this.remove(jPanel8);
            Settings.this.remove(jLabel7);
            Settings.this.remove(jLabel8);
            Settings.this.remove(jPanel9);
            Settings.this.remove(jLabel9);
            Settings.this.remove(jTextField3);
            Settings.this.remove(jLabel10);
            Settings.this.remove(jTextField4);
            Settings.this.remove(jPanel10);
            Settings.this.remove(jPanel11);
            Settings.this.remove(jPanel12);
            Settings.this.remove(jButton6);
            Settings.this.remove(editArtistButton);
            Settings.this.remove(editArtistLabel);
            Settings.this.remove(logOutButton);
            Settings.this.remove(logOutLabel);

        }

    }

    private class EditArtist {

        private void readStock() {
            try {
                String readSt = "Select DISTINCT name, quantity from album";
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(readSt);

                while (rs.next()) {
                    String a = rs.getString(1);
                    int b = rs.getInt(2);
                    Object[] row = {a, b};
                    DefaultTableModel model = (DefaultTableModel) albumStockTable.getModel();
                    model.addRow(row);
                }
            } catch (SQLException ex) {
                Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        private void readBestSells() {
            try {
                String readBS = "Select distinct art.genre , alb.name from album alb, "
                        + "artist art where art.album = alb.name "
                        + "AND  (art.genre,alb.quantity) in (Select art1.genre,"
                        + "min(alb1.quantity) from album alb1, artist art1 "
                        + "where art1.album = alb1.name group by art1.genre)";
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(readBS);

                while (rs.next()) {
                    String a = rs.getString(1);
                    String b = rs.getString(2);
                    Object[] row = {a, b};
                    DefaultTableModel model = (DefaultTableModel) genreAlbumTable.getModel();
                    model.addRow(row);
                }
            } catch (SQLException ex) {
                Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        protected void show() {
            java.awt.GridBagConstraints gridBagConstraints;

            jPanel1 = new javax.swing.JPanel();
            addArtistButton = new javax.swing.JPanel();
            addArtistLabel = new javax.swing.JLabel();
            customersButton = new javax.swing.JPanel();
            customersLabel = new javax.swing.JLabel();
            ordersButton = new javax.swing.JPanel();
            ordersLabel = new javax.swing.JLabel();
            administratorsButton = new javax.swing.JPanel();
            administratorsLabel = new javax.swing.JLabel();
            editArtistButton = new javax.swing.JPanel();
            editArtistLabel = new javax.swing.JLabel();
            logOutButton = new javax.swing.JPanel();
            logOutLabel = new javax.swing.JLabel();
            jScrollPane1 = new javax.swing.JScrollPane();
            jPanel2 = new javax.swing.JPanel();
            jPanel15 = new javax.swing.JPanel();
            jLabel3 = new javax.swing.JLabel();
            updatedAlbum = new javax.swing.JTextField();
            updateButton = new javax.swing.JButton();
            addedQuantity = new javax.swing.JTextField();
            jScrollPane4 = new javax.swing.JScrollPane();
            albumStockTable = new javax.swing.JTable();
            jPanel17 = new javax.swing.JPanel();
            jPanel18 = new javax.swing.JPanel();
            jPanel19 = new javax.swing.JPanel();
            jScrollPane5 = new javax.swing.JScrollPane();
            genreAlbumTable = new javax.swing.JTable();
            jPanel20 = new javax.swing.JPanel();
            jPanel3 = new javax.swing.JPanel();

            setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
            setBackground(new java.awt.Color(0, 0, 0));
            setFocusCycleRoot(false);
            setSize(Settings.this.getSize());
            setPreferredSize(Settings.this.getSize());
            jPanel1.setBackground(new java.awt.Color(51, 51, 51));
            jPanel1.setMinimumSize(new java.awt.Dimension(0, 0));
            jPanel1.setName(""); // NOI18N
            jPanel1.setPreferredSize(new java.awt.Dimension(200, 0));
            jPanel1.setLayout(new java.awt.GridBagLayout());

            addArtistButton.setBackground(new java.awt.Color(51, 51, 51));
            addArtistButton.setMinimumSize(new java.awt.Dimension(0, 10));
            addArtistButton.setPreferredSize(new java.awt.Dimension(200, 30));
            addArtistButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    addArtistButtonMouseClicked(evt);
                }

                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    addArtistButtonMouseEntered(evt);
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    addArtistButtonMouseExited(evt);
                }
            });

            addArtistLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
            addArtistLabel.setForeground(new java.awt.Color(255, 255, 255));
            addArtistLabel.setText("Add new artist");
            addArtistLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    addArtistLabelMouseClicked(evt);
                }
            });
            addArtistButton.add(addArtistLabel);

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 3;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel1.add(addArtistButton, gridBagConstraints);

            customersButton.setBackground(new java.awt.Color(51, 51, 51));
            customersButton.setMinimumSize(new java.awt.Dimension(200, 30));
            customersButton.setPreferredSize(new java.awt.Dimension(200, 30));
            customersButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    customersButtonMouseClicked(evt);
                }

                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    customersButtonMouseEntered(evt);
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    customersButtonMouseExited(evt);
                }
            });

            customersLabel.setBackground(new java.awt.Color(255, 255, 255));
            customersLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
            customersLabel.setForeground(new java.awt.Color(255, 255, 255));
            customersLabel.setText("Customers");
            customersLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    customersLabelMouseClicked(evt);
                }
            });
            customersButton.add(customersLabel);

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 5;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel1.add(customersButton, gridBagConstraints);

            ordersButton.setBackground(new java.awt.Color(51, 51, 51));
            ordersButton.setMinimumSize(new java.awt.Dimension(200, 30));
            ordersButton.setPreferredSize(new java.awt.Dimension(200, 30));
            ordersButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    ordersButtonMouseClicked(evt);
                }

                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    ordersButtonMouseEntered(evt);
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    ordersButtonMouseExited(evt);
                }
            });

            ordersLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
            ordersLabel.setForeground(new java.awt.Color(255, 255, 255));
            ordersLabel.setText("Orders");
            ordersLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    ordersLabelMouseClicked(evt);
                }
            });
            ordersButton.add(ordersLabel);

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 7;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel1.add(ordersButton, gridBagConstraints);

            administratorsButton.setBackground(new java.awt.Color(51, 51, 51));
            administratorsButton.setMinimumSize(new java.awt.Dimension(105, 30));
            administratorsButton.setPreferredSize(new java.awt.Dimension(105, 30));
            administratorsButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    administratorsButtonMouseClicked(evt);
                }

                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    administratorsButtonMouseEntered(evt);
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    administratorsButtonMouseExited(evt);
                }
            });

            administratorsLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
            administratorsLabel.setForeground(new java.awt.Color(255, 255, 255));
            administratorsLabel.setText("Administrators");
            administratorsLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    administratorsLabelMouseClicked(evt);
                }
            });
            administratorsButton.add(administratorsLabel);

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 6;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel1.add(administratorsButton, gridBagConstraints);

            editArtistButton.setBackground(new java.awt.Color(255, 204, 0));
            editArtistButton.setForeground(new java.awt.Color(51, 51, 51));

            editArtistLabel.setBackground(new java.awt.Color(255, 204, 0));
            editArtistLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
            editArtistLabel.setForeground(new java.awt.Color(51, 51, 51));
            editArtistLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            editArtistLabel.setText("Stock");
            editArtistLabel.setToolTipText("");
            editArtistLabel.setMaximumSize(new java.awt.Dimension(83, 19));
            editArtistLabel.setMinimumSize(new java.awt.Dimension(83, 19));
            editArtistLabel.setPreferredSize(new java.awt.Dimension(83, 19));
            editArtistLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    editArtistLabelMouseEntered(evt);
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    editArtistLabelMouseExited(evt);
                }
            });
            editArtistButton.add(editArtistLabel);

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 4;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel1.add(editArtistButton, gridBagConstraints);

            logOutButton.setBackground(new java.awt.Color(51, 51, 51));
            logOutButton.setMinimumSize(new java.awt.Dimension(200, 30));
            logOutButton.setPreferredSize(new java.awt.Dimension(200, 30));
            logOutButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    logOutButtonMouseClicked(evt);
                }

                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    logOutButtonMouseEntered(evt);
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    logOutButtonMouseExited(evt);
                }
            });

            logOutLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
            logOutLabel.setForeground(new java.awt.Color(255, 255, 255));
            logOutLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            logOutLabel.setText("LOG OUT");
            logOutLabel.setMaximumSize(new java.awt.Dimension(68, 19));
            logOutLabel.setMinimumSize(new java.awt.Dimension(68, 19));
            logOutLabel.setPreferredSize(new java.awt.Dimension(68, 19));
            logOutLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    logOutLabelMouseClicked(evt);
                }
            });
            logOutButton.add(logOutLabel);

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 8;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel1.add(logOutButton, gridBagConstraints);

            getContentPane().add(jPanel1, java.awt.BorderLayout.LINE_START);

            jScrollPane1.setBackground(new java.awt.Color(102, 102, 102));
            jScrollPane1.setMinimumSize(new java.awt.Dimension(1000, 480));
            jScrollPane1.setPreferredSize(new java.awt.Dimension(1000, 480));

            jPanel2.setBackground(new java.awt.Color(102, 102, 102));
            jPanel2.setMinimumSize(new java.awt.Dimension(1000, 800));
            jPanel2.setPreferredSize(new java.awt.Dimension(1000, 800));
            jPanel2.setLayout(new java.awt.GridBagLayout());

            jPanel15.setBackground(new java.awt.Color(102, 102, 102));
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 1;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(jPanel15, gridBagConstraints);

            jLabel3.setFont(new java.awt.Font("Century Gothic", 1, 30)); // NOI18N
            jLabel3.setForeground(new java.awt.Color(255, 204, 0));
            jLabel3.setText("Update an album's stock!");
            jLabel3.setAlignmentX(5.0F);
            jLabel3.setMaximumSize(new java.awt.Dimension(500, 30));
            jLabel3.setMinimumSize(new java.awt.Dimension(200, 30));
            jLabel3.setPreferredSize(new java.awt.Dimension(400, 30));
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 2;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(jLabel3, gridBagConstraints);

            updatedAlbum.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
            updatedAlbum.setText("  Enter the album's name");
            updatedAlbum.setAlignmentX(5.0F);
            updatedAlbum.setMaximumSize(new java.awt.Dimension(200, 30));
            updatedAlbum.setMinimumSize(new java.awt.Dimension(200, 30));
            updatedAlbum.setPreferredSize(new java.awt.Dimension(200, 30));
            updatedAlbum.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    updatedAlbumActionPerformed(evt);
                }
            });
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 4;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(updatedAlbum, gridBagConstraints);

            updateButton.setBackground(new java.awt.Color(255, 255, 204));
            updateButton.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
            updateButton.setText("Update!");
            updateButton.setMaximumSize(new java.awt.Dimension(100, 30));
            updateButton.setMinimumSize(new java.awt.Dimension(100, 30));
            updateButton.setPreferredSize(new java.awt.Dimension(100, 30));
            updateButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    updateButtonMouseClicked(evt);
                }
            });
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 2;
            gridBagConstraints.gridy = 2;
            gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
            gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
            jPanel2.add(updateButton, gridBagConstraints);

            addedQuantity.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
            addedQuantity.setText("  Quantity");
            addedQuantity.setAlignmentX(5.0F);
            addedQuantity.setMaximumSize(new java.awt.Dimension(100, 30));
            addedQuantity.setMinimumSize(new java.awt.Dimension(100, 30));
            addedQuantity.setPreferredSize(new java.awt.Dimension(100, 30));
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 2;
            gridBagConstraints.gridy = 4;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(addedQuantity, gridBagConstraints);

            jScrollPane4.setMinimumSize(new java.awt.Dimension(400, 100));
            jScrollPane4.setPreferredSize(new java.awt.Dimension(400, 100));
            jScrollPane4.setRequestFocusEnabled(false);

            albumStockTable.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
            albumStockTable.setModel(new javax.swing.table.DefaultTableModel(
                    new Object[][]{},
                    new String[]{
                        "Album", "Stock"
                    }
            ) {
                Class[] types = new Class[]{
                    java.lang.String.class, java.lang.Integer.class
                };

                public Class getColumnClass(int columnIndex) {
                    return types[columnIndex];
                }
            });
            albumStockTable.setMaximumSize(new java.awt.Dimension(1000, 100));
            albumStockTable.setMinimumSize(new java.awt.Dimension(700, 100));
            albumStockTable.setPreferredSize(new java.awt.Dimension(700, 100));
            albumStockTable.setSelectionBackground(new java.awt.Color(255, 204, 0));
            jScrollPane4.setViewportView(albumStockTable);

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 6;
            gridBagConstraints.gridwidth = 3;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(jScrollPane4, gridBagConstraints);

            jPanel17.setBackground(new java.awt.Color(102, 102, 102));
            jPanel17.setMinimumSize(new java.awt.Dimension(10, 30));
            jPanel17.setPreferredSize(new java.awt.Dimension(10, 30));
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 5;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(jPanel17, gridBagConstraints);

            jPanel18.setBackground(new java.awt.Color(102, 102, 102));
            jPanel18.setMinimumSize(new java.awt.Dimension(10, 30));
            jPanel18.setPreferredSize(new java.awt.Dimension(10, 30));
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 3;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(jPanel18, gridBagConstraints);

            jPanel19.setBackground(new java.awt.Color(102, 102, 102));
            jPanel19.setMinimumSize(new java.awt.Dimension(10, 30));
            jPanel19.setPreferredSize(new java.awt.Dimension(10, 30));
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 7;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(jPanel19, gridBagConstraints);

            jScrollPane5.setMinimumSize(new java.awt.Dimension(400, 100));
            jScrollPane5.setPreferredSize(new java.awt.Dimension(400, 100));
            jScrollPane5.setRequestFocusEnabled(false);

            genreAlbumTable.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
            genreAlbumTable.setModel(new javax.swing.table.DefaultTableModel(
                    new Object[][]{},
                    new String[]{
                        "Genre", "Most selled album"
                    }
            ) {
                Class[] types = new Class[]{
                    java.lang.String.class, java.lang.String.class
                };

                public Class getColumnClass(int columnIndex) {
                    return types[columnIndex];
                }
            });
            genreAlbumTable.setMaximumSize(new java.awt.Dimension(900, 400));
            genreAlbumTable.setMinimumSize(new java.awt.Dimension(900, 100));
            genreAlbumTable.setPreferredSize(new java.awt.Dimension(900, 100));
            genreAlbumTable.setSelectionBackground(new java.awt.Color(255, 204, 0));
            jScrollPane5.setViewportView(genreAlbumTable);

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 8;
            gridBagConstraints.gridwidth = 3;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(jScrollPane5, gridBagConstraints);

            jPanel20.setBackground(new java.awt.Color(102, 102, 102));
            jPanel20.setMinimumSize(new java.awt.Dimension(100, 50));
            jPanel20.setPreferredSize(new java.awt.Dimension(600, 50));
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 9;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(jPanel20, gridBagConstraints);

            jPanel3.setBackground(new java.awt.Color(102, 102, 102));
            jPanel3.setMinimumSize(new java.awt.Dimension(200, 10));
            jPanel3.setPreferredSize(new java.awt.Dimension(200, 10));
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 4;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(jPanel3, gridBagConstraints);

            jScrollPane1.setViewportView(jPanel2);

            getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);
            readStock();
            readBestSells();
            pack();
        }

        private void addArtistButtonMouseEntered(java.awt.event.MouseEvent evt) {
            addArtistButton.setBackground(new Color(255, 204, 0));
            addArtistLabel.setForeground(Color.DARK_GRAY);
        }

        private void addArtistButtonMouseExited(java.awt.event.MouseEvent evt) {
            addArtistButton.setBackground(new Color(51, 51, 51));
            addArtistLabel.setForeground(Color.WHITE);
        }

        private void customersButtonMouseEntered(java.awt.event.MouseEvent evt) {
            customersButton.setBackground(new Color(255, 204, 0));
            customersLabel.setForeground(Color.DARK_GRAY);
        }

        private void customersButtonMouseExited(java.awt.event.MouseEvent evt) {
            customersButton.setBackground(new Color(51, 51, 51));
            customersLabel.setForeground(Color.WHITE);
        }

        private void administratorsButtonMouseEntered(java.awt.event.MouseEvent evt) {
            administratorsButton.setBackground(new Color(255, 204, 0));
            administratorsLabel.setForeground(Color.DARK_GRAY);
        }

        private void administratorsButtonMouseExited(java.awt.event.MouseEvent evt) {
            administratorsButton.setBackground(new Color(51, 51, 51));
            administratorsLabel.setForeground(Color.WHITE);
        }

        private void ordersButtonMouseEntered(java.awt.event.MouseEvent evt) {
            ordersButton.setBackground(new Color(255, 204, 0));
            ordersLabel.setForeground(Color.DARK_GRAY);
        }

        private void ordersButtonMouseExited(java.awt.event.MouseEvent evt) {
            ordersButton.setBackground(new Color(51, 51, 51));
            ordersLabel.setForeground(Color.WHITE);
        }

        private void editArtistLabelMouseEntered(java.awt.event.MouseEvent evt) {

        }

        private void editArtistLabelMouseExited(java.awt.event.MouseEvent evt) {

        }

        private void updatedAlbumActionPerformed(java.awt.event.ActionEvent evt) {
        }

        private void updateButtonMouseClicked(java.awt.event.MouseEvent evt) {
            try {
                int newQuantity = 0;
                String takeQuantity = "Select distinct quantity from album "
                        + "where name ='" + updatedAlbum.getText() + "'";
                Statement st = con.createStatement();
                ResultSet rez = st.executeQuery(takeQuantity);
                if (rez.next()) {
                    newQuantity = rez.getInt(1) + Integer.parseInt(addedQuantity.getText());
                    String updateInfo = "UPDATE album SET quantity=" + newQuantity
                            + " where name ='" + updatedAlbum.getText() + "'";
                    Statement st2 = con.createStatement();
                    st2.executeUpdate(updateInfo);
                    JOptionPane.showMessageDialog(null, "Changes saved!",
                            "Succes", JOptionPane.INFORMATION_MESSAGE);

                } else {
                    JOptionPane.showMessageDialog(null, "Something went wrong.",
                            "ERROR", JOptionPane.ERROR_MESSAGE);
                }

            } catch (SQLException ex) {
                Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        private void addArtistButtonMouseClicked(java.awt.event.MouseEvent evt) {
            this.removeAll();
            addArtist.show();

        }

        private void addArtistLabelMouseClicked(java.awt.event.MouseEvent evt) {
            addArtistButtonMouseClicked(evt);

        }

        private void administratorsButtonMouseClicked(java.awt.event.MouseEvent evt) {
            this.removeAll();
            administrators.show();
        }

        private void administratorsLabelMouseClicked(java.awt.event.MouseEvent evt) {

            administratorsButtonMouseClicked(evt);
        }

        private void ordersButtonMouseClicked(java.awt.event.MouseEvent evt) {
            this.removeAll();
            orders.show();
        }

        private void ordersLabelMouseClicked(java.awt.event.MouseEvent evt) {

            ordersButtonMouseClicked(evt);
        }

        private void customersButtonMouseClicked(java.awt.event.MouseEvent evt) {
            
                this.removeAll();
                customers.show();
            
        }

        private void customersLabelMouseClicked(java.awt.event.MouseEvent evt) {

            customersButtonMouseClicked(evt);
        }

        private void logOutButtonMouseClicked(java.awt.event.MouseEvent evt) {
            removeAll();
            login.show();
        }

        private void logOutLabelMouseClicked(java.awt.event.MouseEvent evt) {
            logOutButtonMouseClicked(evt);
        }

        private void logOutButtonMouseEntered(java.awt.event.MouseEvent evt) {
            logOutButton.setBackground(new Color(255, 204, 0));
            logOutLabel.setForeground(Color.DARK_GRAY);
        }

        private void logOutButtonMouseExited(java.awt.event.MouseEvent evt) {
            logOutButton.setBackground(new Color(51, 51, 51));
            logOutLabel.setForeground(Color.WHITE);
        }

        private void logOutLabelMouseEntered(java.awt.event.MouseEvent evt) {
            logOutButton.setBackground(new Color(255, 204, 0));
            logOutLabel.setForeground(Color.DARK_GRAY);
        }

        private void logOutLabelMouseExited(java.awt.event.MouseEvent evt) {
            logOutButton.setBackground(new Color(51, 51, 51));
            logOutLabel.setForeground(Color.WHITE);
        }

        protected void removeAll() {
            jScrollPane1.removeAll();
            jPanel1.removeAll();
            Settings.this.remove(jScrollPane1);
            Settings.this.remove(jPanel1);
        }

        private javax.swing.JPanel addArtistButton;
        private javax.swing.JLabel addArtistLabel;
        private javax.swing.JTextField addedQuantity;
        private javax.swing.JPanel administratorsButton;
        private javax.swing.JLabel administratorsLabel;
        private javax.swing.JTable albumStockTable;
        private javax.swing.JPanel customersButton;
        private javax.swing.JLabel customersLabel;
        private javax.swing.JPanel editArtistButton;
        private javax.swing.JLabel editArtistLabel;
        private javax.swing.JTable genreAlbumTable;
        private javax.swing.JLabel jLabel3;
        private javax.swing.JPanel jPanel1;
        private javax.swing.JPanel jPanel15;
        private javax.swing.JPanel jPanel17;
        private javax.swing.JPanel jPanel18;
        private javax.swing.JPanel jPanel19;
        private javax.swing.JPanel jPanel2;
        private javax.swing.JPanel jPanel20;
        private javax.swing.JPanel jPanel3;
        private javax.swing.JScrollPane jScrollPane1;
        private javax.swing.JScrollPane jScrollPane4;
        private javax.swing.JScrollPane jScrollPane5;
        private javax.swing.JPanel logOutButton;
        private javax.swing.JLabel logOutLabel;
        private javax.swing.JPanel ordersButton;
        private javax.swing.JLabel ordersLabel;
        private javax.swing.JButton updateButton;
        private javax.swing.JTextField updatedAlbum;
        // End of variables declaration                   
        private List<String> directoriesList = new ArrayList<>();

    }

    private class Customers {

        protected void Customers() {
        }

        protected void removeAll() {
            Settings.this.remove(administratorsButton);
            Settings.this.remove(administratorsLabel);
            Settings.this.remove(ordersButton);
            Settings.this.remove(ordersLabel);
            Settings.this.remove(artistsButton);
            Settings.this.remove(artistsLabel);
            Settings.this.remove(customersButton);
            Settings.this.remove(customersLabel);
            Settings.this.remove(editArtistButton);
            Settings.this.remove(editArtistLabel);
            Settings.this.remove(jLabel1);
            Settings.this.remove(jLabel3);
            Settings.this.remove(jLabel8);
            Settings.this.remove(jPanel1);
            Settings.this.remove(jPanel2);
            Settings.this.remove(jPanel3);
            Settings.this.remove(jScrollPane1);
            Settings.this.remove(jScrollPane3);
            Settings.this.remove(jTable1);
            // Settings.this.remove(searchCustomerButton);
            //  Settings.this.remove(searchCustomersLabel);
            // Settings.this.remove(searchCustomersTextField);
            Settings.this.remove(logOutButton);
            Settings.this.remove(logOutLabel);
        }

        protected void show()
        {
            java.awt.GridBagConstraints gridBagConstraints;
            logOutButton = new javax.swing.JPanel();
            logOutLabel = new javax.swing.JLabel();
            jPanel1 = new javax.swing.JPanel();
            artistsButton = new javax.swing.JPanel();
            artistsLabel = new javax.swing.JLabel();
            customersButton = new javax.swing.JPanel();
            customersLabel = new javax.swing.JLabel();
            ordersButton = new javax.swing.JPanel();
            ordersLabel = new javax.swing.JLabel();
            administratorsButton = new javax.swing.JPanel();
            administratorsLabel = new javax.swing.JLabel();
            editArtistButton = new javax.swing.JPanel();
            editArtistLabel = new javax.swing.JLabel();
            jScrollPane1 = new javax.swing.JScrollPane();
            jPanel2 = new javax.swing.JPanel();
            jLabel1 = new javax.swing.JLabel();
            jLabel3 = new javax.swing.JLabel();
            jPanel3 = new javax.swing.JPanel();
            jLabel8 = new javax.swing.JLabel();
            jScrollPane3 = new javax.swing.JScrollPane();
            jTable1 = new javax.swing.JTable();

            setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
            setBackground(new java.awt.Color(0, 0, 0));
            setFocusCycleRoot(false);
            setSize(Settings.this.getSize());
            setPreferredSize(Settings.this.getSize());

            jPanel1.setBackground(new java.awt.Color(51, 51, 51));
            jPanel1.setMinimumSize(new java.awt.Dimension(0, 0));
            jPanel1.setName(""); // NOI18N
            jPanel1.setPreferredSize(new java.awt.Dimension(200, 0));
            jPanel1.setLayout(new java.awt.GridBagLayout());

            artistsButton.setBackground(new java.awt.Color(51, 51, 51));
            artistsButton.setForeground(new java.awt.Color(51, 51, 51));
            artistsButton.setMinimumSize(new java.awt.Dimension(0, 10));
            artistsButton.setPreferredSize(new java.awt.Dimension(200, 30));
            artistsButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    artistsButtonMouseEntered(evt);
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    artistsButtonMouseExited(evt);
                }

                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    artistsButtonMouseClicked(evt);
                }

            });

            artistsLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
            artistsLabel.setForeground(new java.awt.Color(255, 255, 255));
            artistsLabel.setText("Add new artist");
            artistsLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    artistsButtonMouseEntered(evt);
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    artistsButtonMouseExited(evt);
                }

                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    artistsButtonMouseClicked(evt);
                }

            });
            artistsButton.add(artistsLabel);

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 3;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel1.add(artistsButton, gridBagConstraints);

            customersButton.setBackground(new java.awt.Color(255, 204, 0));
            customersButton.setMinimumSize(new java.awt.Dimension(200, 30));
            customersButton.setPreferredSize(new java.awt.Dimension(200, 30));

            customersLabel.setBackground(new java.awt.Color(255, 255, 255));
            customersLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
            customersLabel.setForeground(new java.awt.Color(51, 51, 51));
            customersLabel.setText("Customers");

            customersButton.add(customersLabel);

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 5;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel1.add(customersButton, gridBagConstraints);

            ordersButton.setBackground(new java.awt.Color(51, 51, 51));
            ordersButton.setMinimumSize(new java.awt.Dimension(200, 30));
            ordersButton.setPreferredSize(new java.awt.Dimension(200, 30));
            ordersButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    ordersButtonMouseEntered(evt);
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    ordersButtonMouseExited(evt);
                }

                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    ordersButtonMouseClicked(evt);
                }
            });

            ordersLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
            ordersLabel.setForeground(new java.awt.Color(255, 255, 255));
            ordersLabel.setText("Orders");
            ordersButton.add(ordersLabel);

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 7;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel1.add(ordersButton, gridBagConstraints);

            administratorsButton.setBackground(new java.awt.Color(51, 51, 51));
            administratorsButton.setMinimumSize(new java.awt.Dimension(105, 30));
            administratorsButton.setPreferredSize(new java.awt.Dimension(105, 30));
            administratorsButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    administratorsButtonMouseEntered(evt);
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    administratorsButtonMouseExited(evt);
                }

                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    administratorsButtonMouseClicked(evt);
                }
            });

            administratorsLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
            administratorsLabel.setForeground(new java.awt.Color(255, 255, 255));
            administratorsLabel.setText("Administrators");
            administratorsButton.add(administratorsLabel);

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 6;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel1.add(administratorsButton, gridBagConstraints);

            editArtistButton.setBackground(new java.awt.Color(51, 51, 51));
            editArtistButton.setForeground(new java.awt.Color(51, 51, 51));

            editArtistLabel.setBackground(new java.awt.Color(51, 51, 51));
            editArtistLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
            editArtistLabel.setForeground(new java.awt.Color(255, 255, 255));
            editArtistLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            editArtistLabel.setText("Stock");
            editArtistLabel.setToolTipText("");
            editArtistLabel.setMaximumSize(new java.awt.Dimension(83, 19));
            editArtistLabel.setMinimumSize(new java.awt.Dimension(83, 19));
            editArtistLabel.setPreferredSize(new java.awt.Dimension(83, 19));
            editArtistLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    editArtistLabelMouseEntered(evt);
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    editArtistLabelMouseExited(evt);
                }

                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    editArtistLabelMouseClicked(evt);
                }
            });
            editArtistButton.add(editArtistLabel);
            editArtistButton.addMouseListener(new java.awt.event.MouseAdapter() {

                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    editArtistLabelMouseClicked(evt);
                }
            });
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 4;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel1.add(editArtistButton, gridBagConstraints);

            logOutButton.setBackground(new java.awt.Color(51, 51, 51));
            logOutButton.setMinimumSize(new java.awt.Dimension(200, 30));
            logOutButton.setPreferredSize(new java.awt.Dimension(200, 30));
            logOutButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    logOutButtonMouseClicked(evt);
                }

                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    logOutButtonMouseEntered(evt);
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    logOutButtonMouseExited(evt);
                }
            });

            logOutLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
            logOutLabel.setForeground(new java.awt.Color(255, 255, 255));
            logOutLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            logOutLabel.setText("LOG OUT");
            logOutLabel.setMaximumSize(new java.awt.Dimension(100, 19));
            logOutLabel.setMinimumSize(new java.awt.Dimension(100, 19));
            logOutLabel.setPreferredSize(new java.awt.Dimension(100, 19));
            logOutLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    logOutLabelMouseClicked(evt);
                }

                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    logOutLabelMouseEntered(evt);
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    logOutLabelMouseExited(evt);
                }
            });
            logOutButton.add(logOutLabel);

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 8;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel1.add(logOutButton, gridBagConstraints);
            getContentPane().add(jPanel1, java.awt.BorderLayout.LINE_START);

            jScrollPane1.setBackground(new java.awt.Color(102, 102, 102));
            jScrollPane1.setPreferredSize(new java.awt.Dimension(400, 400));
            jScrollPane1.setRequestFocusEnabled(false);

            jPanel2.setBackground(new java.awt.Color(102, 102, 102));
            jPanel2.setPreferredSize(new java.awt.Dimension(0, 400));
            jPanel2.setLayout(new java.awt.GridBagLayout());

            jLabel1.setBackground(new java.awt.Color(102, 102, 102));
            jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
            jLabel1.setForeground(new java.awt.Color(255, 204, 0));
            jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            jLabel1.setText("Customers");
            jLabel1.setMaximumSize(new java.awt.Dimension(116, 31));
            jLabel1.setMinimumSize(new java.awt.Dimension(116, 31));
            jLabel1.setOpaque(true);
            jLabel1.setPreferredSize(new java.awt.Dimension(116, 31));
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints.weightx = 0.2;
            gridBagConstraints.weighty = 0.2;
            jPanel2.add(jLabel1, gridBagConstraints);

            jLabel3.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
            jLabel3.setForeground(new java.awt.Color(204, 204, 204));
            jLabel3.setText("Results");
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 4;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(jLabel3, gridBagConstraints);

            jPanel3.setBackground(new java.awt.Color(102, 102, 102));
            jPanel3.setMinimumSize(new java.awt.Dimension(10, 30));
            jPanel3.setPreferredSize(new java.awt.Dimension(10, 30));
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 3;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(jPanel3, gridBagConstraints);

            jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    jLabel8MouseClicked(evt);
                }
            });
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 11;
            jPanel2.add(jLabel8, gridBagConstraints);

            jScrollPane3.setMaximumSize(new java.awt.Dimension(452, 200));
            jScrollPane3.setMinimumSize(new java.awt.Dimension(452, 200));
            jScrollPane3.setPreferredSize(new java.awt.Dimension(452, 200));

            jTable1.setBackground(new java.awt.Color(102, 102, 102));
            jTable1.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
            jTable1.setForeground(new java.awt.Color(255, 255, 255));
            jTable1.setModel(new javax.swing.table.DefaultTableModel(
                    new Object[][]{},
                    new String[]{
                        "Name", "Username", "Address"
                    }
            ) {
                Class[] types = new Class[]{
                    java.lang.String.class, java.lang.String.class, java.lang.String.class
                };
                boolean[] canEdit = new boolean[]{
                    false, false, false
                };

                public Class getColumnClass(int columnIndex) {
                    return types[columnIndex];
                }

                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit[columnIndex];
                }
            });

            jTable1.setToolTipText("");
            jTable1.setMaximumSize(new java.awt.Dimension(452, 200));
            jTable1.setMinimumSize(new java.awt.Dimension(452, 200));
            jTable1.setPreferredSize(new java.awt.Dimension(452, 200));
            jTable1.setRowHeight(25);
            jTable1.setSelectionBackground(new java.awt.Color(255, 204, 0));
            jTable1.setSelectionForeground(new java.awt.Color(0, 0, 0));
            jScrollPane3.setViewportView(jTable1);
            jTableAddValues();
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 6;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(jScrollPane3, gridBagConstraints);

            jScrollPane1.setViewportView(jPanel2);

            getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

            pack();
        }

        private void artistsButtonMouseEntered(java.awt.event.MouseEvent evt) {
            artistsButton.setBackground(new Color(255, 204, 0));
            artistsLabel.setForeground(Color.DARK_GRAY);
        }

        private void artistsButtonMouseExited(java.awt.event.MouseEvent evt) {
            artistsButton.setBackground(new Color(51, 51, 51));
            artistsLabel.setForeground(Color.WHITE);
        }

        private void administratorsButtonMouseEntered(java.awt.event.MouseEvent evt) {
            administratorsButton.setBackground(new Color(255, 204, 0));
            administratorsLabel.setForeground(Color.DARK_GRAY);
        }

        private void administratorsButtonMouseExited(java.awt.event.MouseEvent evt) {
            administratorsButton.setBackground(new Color(51, 51, 51));
            administratorsLabel.setForeground(Color.WHITE);
        }

        private void ordersButtonMouseEntered(java.awt.event.MouseEvent evt) {
            ordersButton.setBackground(new Color(255, 204, 0));
            ordersLabel.setForeground(Color.DARK_GRAY);
        }

        private void ordersButtonMouseExited(java.awt.event.MouseEvent evt) {
            ordersButton.setBackground(new Color(51, 51, 51));
            ordersLabel.setForeground(Color.WHITE);
        }

        private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {
            FileDialog fd = new FileDialog(new Frame(), "File Open");
            fd.setFile("*.png");
            fd.show();
            if (fd.getFile() == null) {
                jLabel8.setIcon(new ImageIcon("C:\\Users\\Andi\\Desktop\\music-record.png"));
            } else {
                jLabel8.setIcon(new ImageIcon(fd.getDirectory() + "\\" + fd.getFile()));
            }
        }

        private void editArtistLabelMouseEntered(java.awt.event.MouseEvent evt) {
            editArtistButton.setBackground(new Color(255, 204, 0));
            editArtistLabel.setForeground(Color.DARK_GRAY);
        }

        private void editArtistLabelMouseExited(java.awt.event.MouseEvent evt) {
            editArtistButton.setBackground(new Color(51, 51, 51));
            editArtistLabel.setForeground(Color.WHITE);
        }

        private void logOutButtonMouseClicked(java.awt.event.MouseEvent evt) {
            removeAll();
            login.show();
        }

        private void logOutLabelMouseClicked(java.awt.event.MouseEvent evt) {
            logOutButtonMouseClicked(evt);
        }

        private void logOutButtonMouseEntered(java.awt.event.MouseEvent evt) {
            logOutButton.setBackground(new Color(255, 204, 0));
            logOutLabel.setForeground(Color.DARK_GRAY);
        }

        private void logOutButtonMouseExited(java.awt.event.MouseEvent evt) {
            logOutButton.setBackground(new Color(51, 51, 51));
            logOutLabel.setForeground(Color.WHITE);
        }

        private void logOutLabelMouseEntered(java.awt.event.MouseEvent evt) {
            logOutButton.setBackground(new Color(255, 204, 0));
            logOutLabel.setForeground(Color.DARK_GRAY);
        }

        private void logOutLabelMouseExited(java.awt.event.MouseEvent evt) {
            logOutButton.setBackground(new Color(51, 51, 51));
            logOutLabel.setForeground(Color.WHITE);
        }

        private void jButton6MouseClicked(java.awt.event.MouseEvent evt) {

        }

        private javax.swing.JPanel administratorsButton;
        private javax.swing.JLabel administratorsLabel;
        private javax.swing.JPanel ordersButton;
        private javax.swing.JLabel ordersLabel;
        private javax.swing.JPanel artistsButton;
        private javax.swing.JLabel artistsLabel;
        private javax.swing.JPanel customersButton;
        private javax.swing.JLabel customersLabel;
        private javax.swing.JPanel editArtistButton;
        private javax.swing.JLabel editArtistLabel;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel jLabel3;
        private javax.swing.JLabel jLabel8;
        private javax.swing.JPanel jPanel1;
        private javax.swing.JPanel jPanel2;
        private javax.swing.JPanel jPanel3;
        private javax.swing.JScrollPane jScrollPane1;
        private javax.swing.JScrollPane jScrollPane3;
        private javax.swing.JTable jTable1;
        private javax.swing.JPanel logOutButton;
        private javax.swing.JLabel logOutLabel;
        private List<String> directoriesList = new ArrayList<>();

        private void artistsButtonMouseClicked(java.awt.event.MouseEvent evt) {
            this.removeAll();
            addArtist.show();
        }

        private void artistsLabelMouseClicked(java.awt.event.MouseEvent evt) {
            artistsButtonMouseClicked(evt);
        }

        private void ordersButtonMouseClicked(java.awt.event.MouseEvent evt) {
            this.removeAll();
            orders.show();
        }

        private void ordersLabelMouseClicked(java.awt.event.MouseEvent evt) {
            ordersButtonMouseClicked(evt);
        }

        private void editArtistButtonMouseClicked(java.awt.event.MouseEvent evt) {
            this.removeAll();
            editArtist.show();
        }

        private void editArtistLabelMouseClicked(java.awt.event.MouseEvent evt) {
            editArtistButtonMouseClicked(evt);
        }

        private void administratorsButtonMouseClicked(java.awt.event.MouseEvent evt) {
            this.removeAll();
            administrators.show();
        }

        private void administratorsLabelMouseClicked(java.awt.event.MouseEvent evt) {
            administratorsButtonMouseClicked(evt);
        }

        private void jTableAddValues() {
            try {
                String readCustomers = "Select name, username, address from customer";
                Statement statement = con.createStatement();
                ResultSet rs = statement.executeQuery(readCustomers);
                while (rs.next()) {
                    String a = null, b = null, c = null;
                    a = rs.getString("name");
                    b = rs.getString("username");
                    c = rs.getString("address");
                    Object[] row = {a, b, c};
                    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                    model.addRow(row);
                }
            } catch (SQLException ex) {
                Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private class Orders {

        List<Order> ordersList = new ArrayList<Order>();

        private int i = 0;
        
        private void readOrders()
        {
            List<Order> aux = OrdersBuffer.getInstance().getOrdersList();
            for(Order order : aux)
            {
                Long a = order.getOrderIndex();
                String b = order.getCustomer().getName(),
                       c = order.getCustomer().getAddress(),
                       d = order.getCustomer().getTelephone(),
                       e = order.getCustomer().getCard().getType();
                Object row [] = {a, b, c, d, e};
                DefaultTableModel model = (DefaultTableModel) ordersTabel.getModel();
                model.addRow(row);
            }
            
        }
        
        protected void show() {

            java.awt.GridBagConstraints gridBagConstraints;
            logOutButton = new javax.swing.JPanel();
            logOutLabel = new javax.swing.JLabel();
            jPanel1 = new javax.swing.JPanel();
            addArtistButton = new javax.swing.JPanel();
            addArtistLabel = new javax.swing.JLabel();
            customersButton = new javax.swing.JPanel();
            customersLabel = new javax.swing.JLabel();
            ordersButton = new javax.swing.JPanel();
            ordersLabel = new javax.swing.JLabel();
            administratorsButton = new javax.swing.JPanel();
            administratorsLabel = new javax.swing.JLabel();
            editArtistButton = new javax.swing.JPanel();
            editArtistLabel = new javax.swing.JLabel();
            jScrollPane1 = new javax.swing.JScrollPane();
            jPanel2 = new javax.swing.JPanel();
            orders = new javax.swing.JLabel();
            jScrollPane2 = new javax.swing.JScrollPane();
            ordersTabel = new javax.swing.JTable();
            jPanel3 = new javax.swing.JPanel();
            orderButton = new javax.swing.JButton();
            jPanel4 = new javax.swing.JPanel();
            jPanel5 = new javax.swing.JPanel();
            jPanel6 = new javax.swing.JPanel();
            clickOrderText = new javax.swing.JLabel();

            setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
            setBackground(new java.awt.Color(0, 0, 0));
            setFocusCycleRoot(false);
            setSize(Settings.this.getSize());
            setPreferredSize(Settings.this.getSize());

            jPanel1.setBackground(new java.awt.Color(51, 51, 51));
            jPanel1.setMinimumSize(new java.awt.Dimension(0, 0));
            jPanel1.setName(""); // NOI18N
            jPanel1.setPreferredSize(new java.awt.Dimension(200, 0));
            jPanel1.setLayout(new java.awt.GridBagLayout());

            addArtistButton.setBackground(new java.awt.Color(51, 51, 51));
            addArtistButton.setMinimumSize(new java.awt.Dimension(0, 10));
            addArtistButton.setPreferredSize(new java.awt.Dimension(200, 30));
            addArtistButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    addArtistButtonMouseEntered(evt);
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    addArtistButtonMouseExited(evt);
                }

                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    addArtistButtonMouseClicked(evt);
                }

            });

            addArtistLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
            addArtistLabel.setForeground(new java.awt.Color(255, 255, 255));
            addArtistLabel.setText("Add new artist");
            addArtistButton.add(addArtistLabel);
            addArtistLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    addArtistButtonMouseEntered(evt);
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    addArtistButtonMouseExited(evt);
                }

                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    addArtistButtonMouseClicked(evt);
                }

            });
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 3;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel1.add(addArtistButton, gridBagConstraints);

            customersButton.setBackground(new java.awt.Color(51, 51, 51));
            customersButton.setMinimumSize(new java.awt.Dimension(200, 30));
            customersButton.setPreferredSize(new java.awt.Dimension(200, 30));
            customersButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    customersButtonMouseEntered(evt);
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    customersButtonMouseExited(evt);
                }

                public void mouseCliked(java.awt.event.MouseEvent evt) {
                    
                        customersButtonMouseClicked(evt);
                    
                }
            });

            customersLabel.setBackground(new java.awt.Color(255, 255, 255));
            customersLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
            customersLabel.setForeground(new java.awt.Color(255, 255, 255));
            customersLabel.setText("Customers");
            customersButton.add(customersLabel);
            customersLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    customersLabelMouseClicked(evt);
                    
                }
            });

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 5;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel1.add(customersButton, gridBagConstraints);

            ordersButton.setBackground(new java.awt.Color(255, 204, 0));
            ordersButton.setMinimumSize(new java.awt.Dimension(200, 30));
            ordersButton.setPreferredSize(new java.awt.Dimension(200, 30));

            ordersLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
            ordersLabel.setForeground(new java.awt.Color(255, 255, 255));
            ordersLabel.setText("Orders");
            ordersButton.add(ordersLabel);

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 7;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel1.add(ordersButton, gridBagConstraints);

            administratorsButton.setBackground(new java.awt.Color(51, 51, 51));
            administratorsButton.setMinimumSize(new java.awt.Dimension(105, 30));
            administratorsButton.setPreferredSize(new java.awt.Dimension(105, 30));
            administratorsButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    administratorsButtonMouseEntered(evt);
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    administratorsButtonMouseExited(evt);
                }

                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    administratorsButtonMouseClicked(evt);
                }
            });

            administratorsLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
            administratorsLabel.setForeground(new java.awt.Color(255, 255, 255));
            administratorsLabel.setText("Administrators");
            administratorsButton.add(administratorsLabel);

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 6;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel1.add(administratorsButton, gridBagConstraints);
            logOutButton.setBackground(new java.awt.Color(51, 51, 51));
            logOutButton.setMinimumSize(new java.awt.Dimension(200, 30));
            logOutButton.setPreferredSize(new java.awt.Dimension(200, 30));
            logOutButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    logOutButtonMouseClicked(evt);
                }

                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    logOutButtonMouseEntered(evt);
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    logOutButtonMouseExited(evt);
                }
            });

            logOutLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
            logOutLabel.setForeground(new java.awt.Color(255, 255, 255));
            logOutLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            logOutLabel.setText("LOG OUT");
            logOutLabel.setMaximumSize(new java.awt.Dimension(100, 19));
            logOutLabel.setMinimumSize(new java.awt.Dimension(100, 19));
            logOutLabel.setPreferredSize(new java.awt.Dimension(100, 19));
            logOutLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    logOutLabelMouseClicked(evt);
                }

                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    logOutLabelMouseEntered(evt);
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    logOutLabelMouseExited(evt);
                }
            });
            logOutButton.add(logOutLabel);

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 8;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel1.add(logOutButton, gridBagConstraints);

            editArtistButton.setBackground(new java.awt.Color(51, 51, 51));
            editArtistButton.setForeground(new java.awt.Color(51, 51, 51));
            editArtistButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    editArtistButtonMouseEntered(evt);
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    editArtistButtonMouseExited(evt);
                }

                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    editArtistButtonMouseClicked(evt);
                }
            });

            editArtistLabel.setBackground(new java.awt.Color(255, 204, 0));
            editArtistLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
            editArtistLabel.setForeground(new java.awt.Color(255, 255, 255));
            editArtistLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            editArtistLabel.setText("Stock");
            editArtistLabel.setToolTipText("");
            editArtistLabel.setMaximumSize(new java.awt.Dimension(83, 19));
            editArtistLabel.setMinimumSize(new java.awt.Dimension(83, 19));
            editArtistLabel.setPreferredSize(new java.awt.Dimension(83, 19));
            editArtistButton.add(editArtistLabel);

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 4;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel1.add(editArtistButton, gridBagConstraints);

            getContentPane().add(jPanel1, java.awt.BorderLayout.LINE_START);

            jScrollPane1.setBackground(new java.awt.Color(102, 102, 102));
            jScrollPane1.setName(""); // NOI18N
            jScrollPane1.setPreferredSize(new java.awt.Dimension(480, 480));

            jPanel2.setBackground(new java.awt.Color(102, 102, 102));
            jPanel2.setMinimumSize(new java.awt.Dimension(258, 0));
            jPanel2.setPreferredSize(new java.awt.Dimension(0, 600));
            jPanel2.setLayout(new java.awt.GridBagLayout());

            orders.setBackground(new java.awt.Color(102, 102, 102));
            orders.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
            orders.setForeground(new java.awt.Color(255, 204, 0));
            orders.setText("Orders");
            orders.setMaximumSize(new java.awt.Dimension(78, 200));
            orders.setPreferredSize(new java.awt.Dimension(78, 200));
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 1;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints.weightx = 0.2;
            gridBagConstraints.weighty = 0.2;
            jPanel2.add(orders, gridBagConstraints);

            jScrollPane2.setBackground(new java.awt.Color(102, 102, 102));
            jScrollPane2.setMinimumSize(new java.awt.Dimension(452, 402));

            ordersTabel.setBackground(new java.awt.Color(102, 102, 102));
            ordersTabel.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
            ordersTabel.setForeground(new java.awt.Color(255, 255, 255));
            ordersTabel.setModel(new javax.swing.table.DefaultTableModel(
                    new Object[][]{},
                    new String[]{
                        "Order no.", "Customer", "Address", "Phone", "Card"
                    }
            ) {
                Class[] types = new Class[]{
                    java.lang.Long.class, java.lang.String.class,
                    java.lang.String.class, java.lang.String.class,
                    java.lang.String.class
                };
                boolean[] canEdit = new boolean[]{
                    false, false, false, false, false
                };

                @Override
                public Class getColumnClass(int columnIndex) {
                    return types[columnIndex];
                }

                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit[columnIndex];
                }
            });

            ordersTabel.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
            ordersTabel.setMinimumSize(new java.awt.Dimension(300, 300));
            ordersTabel.setPreferredSize(new java.awt.Dimension(300, 300));
            ordersTabel.setRowHeight(25);
            ordersTabel.setSelectionBackground(new java.awt.Color(255, 204, 0));
            ordersTabel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    ordersTabelMouseClicked(evt);
                }
            });
            jScrollPane2.setViewportView(ordersTabel);

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 5;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(jScrollPane2, gridBagConstraints);

            jPanel3.setBackground(new java.awt.Color(102, 102, 102));
            jPanel3.setLayout(new java.awt.BorderLayout());

            orderButton.setBackground(new java.awt.Color(255, 204, 0));
            orderButton.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
            orderButton.setForeground(new java.awt.Color(255, 255, 255));
            orderButton.setText("Send order!");
            orderButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    orderButtonMouseClicked(evt);
                }
            });
            jPanel3.add(orderButton, java.awt.BorderLayout.LINE_END);

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 3;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(jPanel3, gridBagConstraints);

            jPanel4.setBackground(new java.awt.Color(102, 102, 102));
            jPanel4.setPreferredSize(new java.awt.Dimension(10, 60));
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 4;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(jPanel4, gridBagConstraints);

            jPanel5.setBackground(new java.awt.Color(102, 102, 102));
            jPanel5.setPreferredSize(new java.awt.Dimension(10, 50));
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 2;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(jPanel5, gridBagConstraints);

            jPanel6.setBackground(new java.awt.Color(102, 102, 102));
            jPanel6.setPreferredSize(new java.awt.Dimension(10, 100));
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(jPanel6, gridBagConstraints);

            clickOrderText.setFont(new java.awt.Font("Century Gothic", 3, 18)); // NOI18N
            clickOrderText.setForeground(new java.awt.Color(255, 204, 0));
            clickOrderText.setText("Click on an order to see the ordered products");
            clickOrderText.setMaximumSize(new java.awt.Dimension(10, 60));
            clickOrderText.setMinimumSize(new java.awt.Dimension(10, 60));
            clickOrderText.setPreferredSize(new java.awt.Dimension(10, 60));
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 4;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(clickOrderText, gridBagConstraints);

            jScrollPane1.setViewportView(jPanel2);

            getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);
            jScrollPane1.getAccessibleContext().setAccessibleName("");
            readOrders();
            pack();
        }

        private void logOutButtonMouseClicked(java.awt.event.MouseEvent evt) {
            removeAll();
            login.show();
        }

        private void logOutLabelMouseClicked(java.awt.event.MouseEvent evt) {
            logOutButtonMouseClicked(evt);
        }

        private void logOutButtonMouseEntered(java.awt.event.MouseEvent evt) {
            logOutButton.setBackground(new Color(255, 204, 0));
            logOutLabel.setForeground(Color.DARK_GRAY);
        }

        private void logOutButtonMouseExited(java.awt.event.MouseEvent evt) {
            logOutButton.setBackground(new Color(51, 51, 51));
            logOutLabel.setForeground(Color.WHITE);
        }

        private void logOutLabelMouseEntered(java.awt.event.MouseEvent evt) {
            logOutButton.setBackground(new Color(255, 204, 0));
            logOutLabel.setForeground(Color.DARK_GRAY);
        }

        private void logOutLabelMouseExited(java.awt.event.MouseEvent evt) {
            logOutButton.setBackground(new Color(51, 51, 51));
            logOutLabel.setForeground(Color.WHITE);
        }

        private void addArtistButtonMouseEntered(java.awt.event.MouseEvent evt) {
            addArtistButton.setBackground(new Color(255, 204, 0));
            addArtistLabel.setForeground(Color.DARK_GRAY);
        }

        private void addArtistButtonMouseExited(java.awt.event.MouseEvent evt) {
            addArtistButton.setBackground(new Color(51, 51, 51));
            addArtistLabel.setForeground(Color.WHITE);
        }

        private void customersButtonMouseEntered(java.awt.event.MouseEvent evt) {
            customersButton.setBackground(new Color(255, 204, 0));
            customersLabel.setForeground(Color.DARK_GRAY);
        }

        private void customersButtonMouseExited(java.awt.event.MouseEvent evt) {
            customersButton.setBackground(new Color(51, 51, 51));
            customersLabel.setForeground(Color.WHITE);
        }

        private void administratorsButtonMouseEntered(java.awt.event.MouseEvent evt) {
            administratorsButton.setBackground(new Color(255, 204, 0));
            administratorsLabel.setForeground(Color.DARK_GRAY);
        }

        private void administratorsButtonMouseExited(java.awt.event.MouseEvent evt) {
            administratorsButton.setBackground(new Color(51, 51, 51));
            administratorsLabel.setForeground(Color.WHITE);
        }

        private void editArtistButtonMouseExited(java.awt.event.MouseEvent evt) {
            editArtistButton.setBackground(new Color(51, 51, 51));
            editArtistLabel.setForeground(Color.WHITE);
        }

        private void editArtistButtonMouseEntered(java.awt.event.MouseEvent evt) {
            editArtistButton.setBackground(new Color(255, 204, 0));
            editArtistLabel.setForeground(Color.DARK_GRAY);
        }

        private void addArtistButtonMouseClicked(java.awt.event.MouseEvent evt) {
            this.removeAll();
            addArtist.show();
        }

        private void addArtistLabelMouseClicked(java.awt.event.MouseEvent evt) {
            addArtistButtonMouseClicked(evt);
        }

        private void administratorsButtonMouseClicked(java.awt.event.MouseEvent evt) {
            this.removeAll();
            administrators.show();
        }

        private void administratorsLabelMouseClicked(java.awt.event.MouseEvent evt) {
            administratorsButtonMouseClicked(evt);
        }

        private void customersButtonMouseClicked(java.awt.event.MouseEvent evt)
        {
            this.removeAll();
            customers.show();
        }

        private void customersLabelMouseClicked(java.awt.event.MouseEvent evt)
        {
            customersButtonMouseClicked(evt);
        }

        private void editArtistButtonMouseClicked(java.awt.event.MouseEvent evt) {
            this.removeAll();
            editArtist.show();
        }

        private void editArtistLabelMouseClicked(java.awt.event.MouseEvent evt) {
            editArtistButtonMouseClicked(evt);
        }

        private void ordersTabelMouseClicked(java.awt.event.MouseEvent evt) {
            int index = ordersTabel.getSelectedRow();
            if(index != -1)
            {
                Order o = OrdersBuffer.getInstance().getOrdersList().get(index);
                albumsList albumsList = new albumsList(o);
                albumsList.setLocationRelativeTo(null);
                albumsList.setVisible(true);
            }
            //de adaugat lista cu albume din comanda
        }

        private void orderButtonMouseClicked(java.awt.event.MouseEvent evt) {
            DefaultTableModel model = (DefaultTableModel) ordersTabel.getModel();

            if (ordersTabel.getSelectedRow() >= 0) {
                OrdersBuffer.getInstance().getOrdersList().remove(ordersTabel.getSelectedRow());
                model.removeRow(ordersTabel.getSelectedRow());
                
            }
        }
        private boolean selected = false;
        private javax.swing.JPanel addArtistButton;
        private javax.swing.JLabel addArtistLabel;
        private javax.swing.JPanel administratorsButton;
        private javax.swing.JLabel administratorsLabel;
        private javax.swing.JPanel customersButton;
        private javax.swing.JLabel customersLabel;
        private javax.swing.JPanel editArtistButton;
        private javax.swing.JLabel editArtistLabel;
        private javax.swing.JPanel jPanel1;
        private javax.swing.JPanel jPanel2;
        private javax.swing.JPanel jPanel3;
        private javax.swing.JPanel jPanel4;
        private javax.swing.JPanel jPanel5;
        private javax.swing.JPanel jPanel6;
        private javax.swing.JScrollPane jScrollPane1;
        private javax.swing.JScrollPane jScrollPane2;
        private javax.swing.JButton orderButton;
        private javax.swing.JLabel orders;
        private javax.swing.JPanel ordersButton;
        private javax.swing.JLabel ordersLabel;
        private javax.swing.JTable ordersTabel;
        private javax.swing.JPanel logOutButton;
        private javax.swing.JLabel logOutLabel;
        private javax.swing.JLabel clickOrderText;
        private List<String> directoriesList = new ArrayList<>();

        protected void removeAll() {

            Settings.this.remove(addArtistButton);
            Settings.this.remove(addArtistLabel);
            Settings.this.remove(administratorsButton);
            Settings.this.remove(administratorsLabel);
            Settings.this.remove(customersButton);
            Settings.this.remove(customersLabel);
            Settings.this.remove(editArtistButton);
            Settings.this.remove(editArtistLabel);
            Settings.this.remove(jPanel1);
            Settings.this.remove(jPanel2);
            Settings.this.remove(jPanel3);
            Settings.this.remove(jPanel4);
            Settings.this.remove(jPanel5);
            Settings.this.remove(jPanel6);
            Settings.this.remove(jScrollPane1);
            Settings.this.remove(jScrollPane2);
            Settings.this.remove(orderButton);
            Settings.this.remove(orders);
            Settings.this.remove(ordersButton);
            Settings.this.remove(ordersLabel);
            Settings.this.remove(ordersTabel);
            Settings.this.remove(logOutButton);
            Settings.this.remove(logOutLabel);
        }
    }

    private class Administrators {

        private javax.swing.JPanel addArtistButton;
        private javax.swing.JLabel addArtistLabel;
        private javax.swing.JPanel administratorsButton;
        private javax.swing.JLabel administratorsLabel;
        private javax.swing.JPanel customersButton;
        private javax.swing.JLabel customersLabel;
        private javax.swing.JPanel editArtistButton;
        private javax.swing.JLabel editArtistLabel;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel jLabel10;
        private javax.swing.JLabel jLabel2;
        private javax.swing.JLabel jLabel3;
        private javax.swing.JLabel jLabel4;
        private javax.swing.JLabel jLabel5;
        private javax.swing.JLabel jLabel6;
        private javax.swing.JLabel jLabel7;
        private javax.swing.JLabel jLabel9;
        private javax.swing.JPanel jPanel1;
        private javax.swing.JPanel jPanel10;
        private javax.swing.JPanel jPanel11;
        private javax.swing.JPanel jPanel12;
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
        private javax.swing.JPanel jPanel3;
        private javax.swing.JPanel jPanel4;
        private javax.swing.JPanel jPanel5;
        private javax.swing.JPanel jPanel6;
        private javax.swing.JPanel jPanel7;
        private javax.swing.JPanel jPanel8;
        private javax.swing.JPanel jPanel9;
        private javax.swing.JScrollPane jScrollPane1;
        private javax.swing.JScrollPane jScrollPane2;
        private javax.swing.JTextPane jTextPane1;
        private javax.swing.JTextPane jTextPane2;
        private javax.swing.JPanel ordersButton;
        private javax.swing.JLabel ordersLabel;
        private javax.swing.JPanel logOutButton;
        private javax.swing.JLabel logOutLabel;
        private List<String> directoriesList = new ArrayList<>();

        public Administrators() {

        }

        protected void show() {

            Statement st = null;
            try {
                st = con.createStatement();
            } catch (SQLException ex) {
                Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
            }
            String sql3 = "SELECT name, username FROM administrator";
            ResultSet rez3 = null;
            try {
                rez3 = st.executeQuery(sql3);
            } catch (SQLException ex) {
                Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
            }
            int i = 0;
            try {
                while (rez3.next()) {
                    adminName[i] = rez3.getString(1);
                    adminUsername[i] = rez3.getString(2);
                    i++;
                }
            } catch (SQLException ex) {
                Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
            }
            java.awt.GridBagConstraints gridBagConstraints;
            logOutButton = new javax.swing.JPanel();
            logOutLabel = new javax.swing.JLabel();
            jPanel1 = new javax.swing.JPanel();
            addArtistButton = new javax.swing.JPanel();
            addArtistLabel = new javax.swing.JLabel();
            customersButton = new javax.swing.JPanel();
            customersLabel = new javax.swing.JLabel();
            ordersButton = new javax.swing.JPanel();
            ordersLabel = new javax.swing.JLabel();
            administratorsButton = new javax.swing.JPanel();
            administratorsLabel = new javax.swing.JLabel();
            editArtistButton = new javax.swing.JPanel();
            editArtistLabel = new javax.swing.JLabel();
            jPanel3 = new javax.swing.JPanel();
            jPanel5 = new javax.swing.JPanel();
            jPanel6 = new javax.swing.JPanel();
            jScrollPane1 = new javax.swing.JScrollPane();
            jTextPane1 = new javax.swing.JTextPane();
            jPanel10 = new javax.swing.JPanel();
            jPanel11 = new javax.swing.JPanel();
            jPanel12 = new javax.swing.JPanel();
            jPanel7 = new javax.swing.JPanel();
            jLabel9 = new javax.swing.JLabel();
            jLabel3 = new javax.swing.JLabel();
            jPanel13 = new javax.swing.JPanel();
            jLabel2 = new javax.swing.JLabel();
            jLabel10 = new javax.swing.JLabel();
            jLabel1 = new javax.swing.JLabel();
            jPanel15 = new javax.swing.JPanel();
            jPanel16 = new javax.swing.JPanel();
            jPanel2 = new javax.swing.JPanel();
            jPanel8 = new javax.swing.JPanel();
            jPanel9 = new javax.swing.JPanel();
            jScrollPane2 = new javax.swing.JScrollPane();
            jTextPane2 = new javax.swing.JTextPane();
            jPanel17 = new javax.swing.JPanel();
            jPanel18 = new javax.swing.JPanel();
            jPanel19 = new javax.swing.JPanel();
            jLabel5 = new javax.swing.JLabel();
            jLabel6 = new javax.swing.JLabel();
            jPanel20 = new javax.swing.JPanel();
            jPanel21 = new javax.swing.JPanel();
            jLabel4 = new javax.swing.JLabel();
            jPanel14 = new javax.swing.JPanel();
            jLabel7 = new javax.swing.JLabel();
            jPanel4 = new javax.swing.JPanel();

            setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
            setBackground(new java.awt.Color(0, 0, 0));
            setFocusCycleRoot(false);
            setSize(Settings.this.getSize());
            setPreferredSize(Settings.this.getSize());

            jPanel1.setBackground(new java.awt.Color(51, 51, 51));
            jPanel1.setMinimumSize(new java.awt.Dimension(0, 0));
            jPanel1.setName(""); // NOI18N
            jPanel1.setPreferredSize(new java.awt.Dimension(200, 600));
            jPanel1.setLayout(new java.awt.GridBagLayout());

            addArtistButton.setBackground(new java.awt.Color(51, 51, 51));
            addArtistButton.setMinimumSize(new java.awt.Dimension(0, 10));
            addArtistButton.setPreferredSize(new java.awt.Dimension(200, 30));
            addArtistButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    addArtistButtonMouseClicked(evt);
                }

                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    addArtistButtonMouseEntered(evt);
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    addArtistButtonMouseExited(evt);
                }
            });

            addArtistLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
            addArtistLabel.setForeground(new java.awt.Color(255, 255, 255));
            addArtistLabel.setText("Add new artist");
            addArtistButton.add(addArtistLabel);

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 3;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel1.add(addArtistButton, gridBagConstraints);

            customersButton.setBackground(new java.awt.Color(51, 51, 51));
            customersButton.setMinimumSize(new java.awt.Dimension(200, 30));
            customersButton.setPreferredSize(new java.awt.Dimension(200, 30));
            customersButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                 
                        customersButtonMouseClicked(evt);
                    
                }

                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    customersButtonMouseEntered(evt);
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    customersButtonMouseExited(evt);
                }
            });

            customersLabel.setBackground(new java.awt.Color(255, 255, 255));
            customersLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
            customersLabel.setForeground(new java.awt.Color(255, 255, 255));
            customersLabel.setText("Customers");
            customersLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    
                        customersLabelMouseClicked(evt);
                    
                }
            });
            customersButton.add(customersLabel);
            logOutButton.setBackground(new java.awt.Color(51, 51, 51));
            logOutButton.setMinimumSize(new java.awt.Dimension(200, 30));
            logOutButton.setPreferredSize(new java.awt.Dimension(200, 30));
            logOutButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    logOutButtonMouseClicked(evt);
                }

                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    logOutButtonMouseEntered(evt);
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    logOutButtonMouseExited(evt);
                }
            });

            logOutLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
            logOutLabel.setForeground(new java.awt.Color(255, 255, 255));
            logOutLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            logOutLabel.setText("LOG OUT");
            logOutLabel.setMaximumSize(new java.awt.Dimension(100, 19));
            logOutLabel.setMinimumSize(new java.awt.Dimension(100, 19));
            logOutLabel.setPreferredSize(new java.awt.Dimension(100, 19));
            logOutLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    logOutLabelMouseClicked(evt);
                }

                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    logOutLabelMouseEntered(evt);
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    logOutLabelMouseExited(evt);
                }
            });
            logOutButton.add(logOutLabel);

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 8;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel1.add(logOutButton, gridBagConstraints);

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 5;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel1.add(customersButton, gridBagConstraints);

            ordersButton.setBackground(new java.awt.Color(51, 51, 51));
            ordersButton.setMinimumSize(new java.awt.Dimension(200, 30));
            ordersButton.setPreferredSize(new java.awt.Dimension(200, 30));
            ordersButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    ordersButtonMouseClicked(evt);
                }

                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    ordersButtonMouseEntered(evt);
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    ordersButtonMouseExited(evt);
                }
            });

            ordersLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
            ordersLabel.setForeground(new java.awt.Color(255, 255, 255));
            ordersLabel.setText("Orders");
            ordersButton.add(ordersLabel);

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 7;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel1.add(ordersButton, gridBagConstraints);

            administratorsButton.setBackground(new java.awt.Color(255, 204, 0));
            administratorsButton.setMinimumSize(new java.awt.Dimension(105, 30));
            administratorsButton.setPreferredSize(new java.awt.Dimension(105, 30));

            administratorsLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
            administratorsLabel.setForeground(new java.awt.Color(51, 51, 51));
            administratorsLabel.setText("Administrators");
            administratorsButton.add(administratorsLabel);

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 6;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel1.add(administratorsButton, gridBagConstraints);

            editArtistButton.setBackground(new java.awt.Color(51, 51, 51));
            editArtistButton.setForeground(new java.awt.Color(51, 51, 51));
            editArtistButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    editArtistButtonMouseClicked(evt);
                }

                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    editArtistButtonMouseEntered(evt);
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    editArtistButtonMouseExited(evt);
                }
            });

            editArtistLabel.setBackground(new java.awt.Color(255, 204, 0));
            editArtistLabel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
            editArtistLabel.setForeground(new java.awt.Color(255, 255, 255));
            editArtistLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            editArtistLabel.setText("Stock");
            editArtistLabel.setToolTipText("");
            editArtistLabel.setMaximumSize(new java.awt.Dimension(83, 19));
            editArtistLabel.setMinimumSize(new java.awt.Dimension(83, 19));
            editArtistLabel.setPreferredSize(new java.awt.Dimension(83, 19));
            editArtistLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    editArtistLabelMouseEntered(evt);
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    editArtistLabelMouseExited(evt);
                }

                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    editArtistLabelMouseClicked(evt);
                }
            });
            editArtistButton.add(editArtistLabel);

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 4;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel1.add(editArtistButton, gridBagConstraints);

            getContentPane().add(jPanel1, java.awt.BorderLayout.LINE_START);

            jPanel3.setBackground(new java.awt.Color(102, 102, 102));
            jPanel3.setLayout(new java.awt.GridBagLayout());

            jPanel5.setBackground(new java.awt.Color(255, 204, 0));
            jPanel5.setMinimumSize(new java.awt.Dimension(320, 300));
            jPanel5.setPreferredSize(new java.awt.Dimension(320, 300));
            jPanel5.setLayout(new java.awt.GridBagLayout());

            jPanel6.setBackground(new java.awt.Color(255, 204, 0));
            jPanel6.setMaximumSize(new java.awt.Dimension(320, 50));
            jPanel6.setMinimumSize(new java.awt.Dimension(320, 50));
            jPanel6.setPreferredSize(new java.awt.Dimension(320, 50));
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.gridwidth = 5;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel5.add(jPanel6, gridBagConstraints);

            jScrollPane1.setBackground(new java.awt.Color(255, 204, 0));
            jScrollPane1.setBorder(null);
            jScrollPane1.setMaximumSize(new java.awt.Dimension(100, 100));
            jScrollPane1.setMinimumSize(new java.awt.Dimension(100, 100));
            jScrollPane1.setPreferredSize(new java.awt.Dimension(120, 100));

            jTextPane1.setEditable(false);
            jTextPane1.setBorder(null);
            jTextPane1.setFont(new java.awt.Font("Century Gothic", 1, 11)); // NOI18N
            jTextPane1.setText("Responsabilities\nsellings\nmanagement\nadvertising\n");
            jTextPane1.setToolTipText("");
            jTextPane1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
            jTextPane1.setMaximumSize(new java.awt.Dimension(100, 100));
            jTextPane1.setMinimumSize(new java.awt.Dimension(100, 100));
            jTextPane1.setPreferredSize(new java.awt.Dimension(100, 100));
            jTextPane1.setRequestFocusEnabled(false);
            jTextPane1.setSelectedTextColor(new java.awt.Color(51, 51, 51));
            jTextPane1.setSelectionColor(new java.awt.Color(255, 204, 0));
            jScrollPane1.setViewportView(jTextPane1);

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 3;
            gridBagConstraints.gridy = 3;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel5.add(jScrollPane1, gridBagConstraints);

            jPanel10.setBackground(new java.awt.Color(255, 204, 0));
            jPanel10.setMinimumSize(new java.awt.Dimension(50, 300));
            jPanel10.setPreferredSize(new java.awt.Dimension(50, 300));
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.gridheight = 5;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel5.add(jPanel10, gridBagConstraints);

            jPanel11.setBackground(new java.awt.Color(255, 204, 0));
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 4;
            gridBagConstraints.gridwidth = 5;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel5.add(jPanel11, gridBagConstraints);

            jPanel12.setBackground(new java.awt.Color(255, 204, 0));
            jPanel12.setMaximumSize(new java.awt.Dimension(50, 300));
            jPanel12.setMinimumSize(new java.awt.Dimension(50, 300));
            jPanel12.setPreferredSize(new java.awt.Dimension(50, 300));
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 4;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.gridheight = 5;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel5.add(jPanel12, gridBagConstraints);

            jPanel7.setBackground(new java.awt.Color(255, 255, 255));
            jPanel7.setMaximumSize(new java.awt.Dimension(100, 100));
            jPanel7.setMinimumSize(new java.awt.Dimension(100, 100));
            jPanel7.setPreferredSize(new java.awt.Dimension(100, 100));
            jPanel7.setRequestFocusEnabled(false);
            jPanel7.setLayout(new java.awt.GridBagLayout());

            jLabel9.setBackground(new java.awt.Color(255, 255, 255));
            jLabel9.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
            jLabel9.setText("Username:");
            jLabel9.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
            jLabel9.setMaximumSize(new java.awt.Dimension(100, 50));
            jLabel9.setMinimumSize(new java.awt.Dimension(100, 50));
            jLabel9.setPreferredSize(new java.awt.Dimension(100, 50));
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 2;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel7.add(jLabel9, gridBagConstraints);

            jLabel3.setBackground(new java.awt.Color(255, 255, 255));
            jLabel3.setFont(new java.awt.Font("Century Gothic", 3, 11)); // NOI18N
            jLabel3.setText(adminUsername[1]);
            jLabel3.setVerticalAlignment(javax.swing.SwingConstants.TOP);
            jLabel3.setMaximumSize(new java.awt.Dimension(100, 50));
            jLabel3.setMinimumSize(new java.awt.Dimension(100, 50));
            jLabel3.setPreferredSize(new java.awt.Dimension(100, 50));
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 2;
            gridBagConstraints.gridy = 1;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel7.add(jLabel3, gridBagConstraints);

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 3;
            gridBagConstraints.gridy = 1;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel5.add(jPanel7, gridBagConstraints);

            jPanel13.setBackground(new java.awt.Color(255, 255, 255));
            jPanel13.setMaximumSize(new java.awt.Dimension(100, 100));
            jPanel13.setMinimumSize(new java.awt.Dimension(100, 100));
            jPanel13.setPreferredSize(new java.awt.Dimension(100, 100));
            jPanel13.setRequestFocusEnabled(false);

            jLabel2.setBackground(new java.awt.Color(255, 204, 0));
            jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
            jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            jLabel2.setText(adminName[1]);
            jLabel2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
            jLabel2.setMaximumSize(new java.awt.Dimension(100, 50));
            jLabel2.setMinimumSize(new java.awt.Dimension(100, 50));
            jLabel2.setPreferredSize(new java.awt.Dimension(100, 50));
            jPanel13.add(jLabel2);

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 3;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel5.add(jPanel13, gridBagConstraints);

            jLabel10.setText("jLabel10");
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 3;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel5.add(jLabel10, gridBagConstraints);

            jLabel1.setBackground(new java.awt.Color(255, 255, 255));
            jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            jLabel1.setIcon(new javax.swing.ImageIcon("C:\\JavaProiect\\"
                                            + "Proiectv2\\Photo\\diana.jpg")); // NOI18N
            jLabel1.setText("jLabel1");
            jLabel1.setMaximumSize(new java.awt.Dimension(100, 100));
            jLabel1.setMinimumSize(new java.awt.Dimension(100, 100));
            jLabel1.setPreferredSize(new java.awt.Dimension(100, 100));
            jLabel1.setRequestFocusEnabled(false);
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 1;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel5.add(jLabel1, gridBagConstraints);

            jPanel15.setBackground(new java.awt.Color(255, 204, 0));
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 2;
            gridBagConstraints.gridy = 1;
            gridBagConstraints.gridheight = 3;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel5.add(jPanel15, gridBagConstraints);

            jPanel16.setBackground(new java.awt.Color(255, 204, 0));
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 2;
            gridBagConstraints.gridwidth = 3;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel5.add(jPanel16, gridBagConstraints);

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 2;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel3.add(jPanel5, gridBagConstraints);

            jPanel2.setBackground(new java.awt.Color(255, 255, 255));
            jPanel2.setMaximumSize(new java.awt.Dimension(320, 300));
            jPanel2.setMinimumSize(new java.awt.Dimension(320, 300));
            jPanel2.setPreferredSize(new java.awt.Dimension(320, 300));
            jPanel2.setLayout(new java.awt.GridBagLayout());

            jPanel8.setBackground(new java.awt.Color(255, 204, 0));
            jPanel8.setMaximumSize(new java.awt.Dimension(320, 50));
            jPanel8.setMinimumSize(new java.awt.Dimension(320, 50));
            jPanel8.setPreferredSize(new java.awt.Dimension(320, 50));
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.gridwidth = 5;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(jPanel8, gridBagConstraints);

            jPanel9.setBackground(new java.awt.Color(255, 204, 0));
            jPanel9.setMaximumSize(new java.awt.Dimension(50, 300));
            jPanel9.setMinimumSize(new java.awt.Dimension(50, 300));
            jPanel9.setPreferredSize(new java.awt.Dimension(50, 300));
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.gridheight = 5;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(jPanel9, gridBagConstraints);

            jScrollPane2.setBorder(null);
            jScrollPane2.setMaximumSize(new java.awt.Dimension(100, 100));
            jScrollPane2.setMinimumSize(new java.awt.Dimension(100, 100));
            jScrollPane2.setPreferredSize(new java.awt.Dimension(100, 100));

            jTextPane2.setEditable(false);
            jTextPane2.setBorder(null);
            jTextPane2.setFont(new java.awt.Font("Century Gothic", 1, 11)); // NOI18N
            jTextPane2.setText("Responsabilities\nmarketing\nmanagement\n IT\n");
            jTextPane2.setDisabledTextColor(new java.awt.Color(0, 0, 0));
            jTextPane2.setMaximumSize(new java.awt.Dimension(100, 100));
            jTextPane2.setMinimumSize(new java.awt.Dimension(100, 100));
            jTextPane2.setPreferredSize(new java.awt.Dimension(100, 100));
            jTextPane2.setRequestFocusEnabled(false);
            jTextPane2.setSelectedTextColor(new java.awt.Color(51, 51, 51));
            jTextPane2.setSelectionColor(new java.awt.Color(255, 204, 0));
            jScrollPane2.setViewportView(jTextPane2);

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 3;
            gridBagConstraints.gridy = 3;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(jScrollPane2, gridBagConstraints);

            jPanel17.setBackground(new java.awt.Color(255, 204, 0));
            jPanel17.setMaximumSize(new java.awt.Dimension(320, 50));
            jPanel17.setMinimumSize(new java.awt.Dimension(320, 50));
            jPanel17.setPreferredSize(new java.awt.Dimension(320, 50));
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 4;
            gridBagConstraints.gridwidth = 5;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(jPanel17, gridBagConstraints);

            jPanel18.setBackground(new java.awt.Color(255, 204, 0));
            jPanel18.setMaximumSize(new java.awt.Dimension(50, 300));
            jPanel18.setMinimumSize(new java.awt.Dimension(50, 300));
            jPanel18.setPreferredSize(new java.awt.Dimension(50, 300));
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 4;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.gridheight = 5;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(jPanel18, gridBagConstraints);

            jPanel19.setBackground(new java.awt.Color(255, 255, 255));
            jPanel19.setMaximumSize(new java.awt.Dimension(100, 100));
            jPanel19.setMinimumSize(new java.awt.Dimension(100, 100));
            jPanel19.setPreferredSize(new java.awt.Dimension(100, 100));
            jPanel19.setLayout(new java.awt.GridBagLayout());

            jLabel5.setFont(new java.awt.Font("Century Gothic", 1, 11)); // NOI18N
            jLabel5.setText("Username");
            jLabel5.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
            jLabel5.setMaximumSize(new java.awt.Dimension(100, 50));
            jLabel5.setMinimumSize(new java.awt.Dimension(100, 50));
            jLabel5.setPreferredSize(new java.awt.Dimension(100, 50));
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel19.add(jLabel5, gridBagConstraints);

            jLabel6.setFont(new java.awt.Font("Century Gothic", 1, 11)); // NOI18N
            jLabel6.setText(adminUsername[0]);
            jLabel6.setVerticalAlignment(javax.swing.SwingConstants.TOP);
            jLabel6.setMaximumSize(new java.awt.Dimension(100, 50));
            jLabel6.setMinimumSize(new java.awt.Dimension(100, 50));
            jLabel6.setPreferredSize(new java.awt.Dimension(100, 50));
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 1;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel19.add(jLabel6, gridBagConstraints);

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 3;
            gridBagConstraints.gridy = 1;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(jPanel19, gridBagConstraints);

            jPanel20.setBackground(new java.awt.Color(255, 204, 0));
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 2;
            gridBagConstraints.gridy = 1;
            gridBagConstraints.gridheight = 3;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(jPanel20, gridBagConstraints);

            jPanel21.setBackground(new java.awt.Color(255, 204, 0));
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 2;
            gridBagConstraints.gridwidth = 3;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(jPanel21, gridBagConstraints);

            jLabel4.setBackground(new java.awt.Color(255, 255, 255));
            jLabel4.setIcon(new javax.swing.ImageIcon("C:\\JavaProiect\\"
                                            + "Proiectv2\\Photo\\andi.jpg")); // NOI18N
            jLabel4.setText("jLabel4");
            jLabel4.setMaximumSize(new java.awt.Dimension(100, 100));
            jLabel4.setMinimumSize(new java.awt.Dimension(100, 100));
            jLabel4.setPreferredSize(new java.awt.Dimension(100, 100));
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 1;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(jLabel4, gridBagConstraints);

            jPanel14.setBackground(new java.awt.Color(255, 255, 255));
            jPanel14.setMaximumSize(new java.awt.Dimension(100, 100));
            jPanel14.setMinimumSize(new java.awt.Dimension(100, 100));
            jPanel14.setPreferredSize(new java.awt.Dimension(100, 100));
            jPanel14.setLayout(new java.awt.GridBagLayout());

            jLabel7.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
            jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            jLabel7.setText(adminName[0]);
            jLabel7.setVerticalAlignment(javax.swing.SwingConstants.TOP);
            jLabel7.setMaximumSize(new java.awt.Dimension(100, 100));
            jLabel7.setMinimumSize(new java.awt.Dimension(100, 100));
            jLabel7.setPreferredSize(new java.awt.Dimension(100, 100));
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel14.add(jLabel7, gridBagConstraints);

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 3;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel2.add(jPanel14, gridBagConstraints);

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 2;
            gridBagConstraints.gridy = 0;
            jPanel3.add(jPanel2, gridBagConstraints);

            jPanel4.setBackground(new java.awt.Color(102, 102, 102));
            jPanel4.setPreferredSize(new java.awt.Dimension(70, 10));
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.gridheight = 3;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            jPanel3.add(jPanel4, gridBagConstraints);

            getContentPane().add(jPanel3, java.awt.BorderLayout.CENTER);

            pack();
        }

        protected void removeAll() {
            Settings.this.remove(jPanel1);
            Settings.this.remove(addArtistButton);
            Settings.this.remove(addArtistLabel);
            Settings.this.remove(customersButton);
            Settings.this.remove(customersLabel);
            Settings.this.remove(ordersButton);
            Settings.this.remove(ordersLabel);
            Settings.this.remove(administratorsButton);
            Settings.this.remove(administratorsLabel);
            Settings.this.remove(editArtistButton);
            Settings.this.remove(editArtistLabel);
            Settings.this.remove(jPanel3);
            Settings.this.remove(jPanel5);
            Settings.this.remove(jPanel6);
            Settings.this.remove(jScrollPane1);
            Settings.this.remove(jTextPane1);
            Settings.this.remove(jPanel10);
            Settings.this.remove(jPanel11);
            Settings.this.remove(jPanel1);
            Settings.this.remove(jPanel7);
            Settings.this.remove(jLabel9);
            Settings.this.remove(jLabel3);
            Settings.this.remove(jPanel13);
            Settings.this.remove(jLabel2);
            Settings.this.remove(jLabel10);
            Settings.this.remove(jLabel1);
            Settings.this.remove(jPanel15);
            Settings.this.remove(jPanel16);
            Settings.this.remove(jPanel2);
            Settings.this.remove(jPanel8);
            Settings.this.remove(jPanel9);
            Settings.this.remove(jScrollPane2);
            Settings.this.remove(jTextPane2);
            Settings.this.remove(jPanel17);
            Settings.this.remove(jPanel18);
            Settings.this.remove(jPanel19);
            Settings.this.remove(jLabel5);
            Settings.this.remove(jLabel6);
            Settings.this.remove(jPanel20);
            Settings.this.remove(jPanel21);
            Settings.this.remove(jLabel4);
            Settings.this.remove(jPanel14);
            Settings.this.remove(jLabel7);
            Settings.this.remove(jPanel4);
            Settings.this.remove(logOutButton);
            Settings.this.remove(logOutLabel);

        }

        private void addArtistButtonMouseEntered(java.awt.event.MouseEvent evt) {
            addArtistButton.setBackground(new Color(255, 204, 0));
            addArtistLabel.setForeground(Color.DARK_GRAY);
        }

        private void addArtistButtonMouseExited(java.awt.event.MouseEvent evt) {
            addArtistButton.setBackground(new Color(51, 51, 51));
            addArtistLabel.setForeground(Color.WHITE);
        }

        private void customersButtonMouseEntered(java.awt.event.MouseEvent evt) {
            customersButton.setBackground(new Color(255, 204, 0));
            customersLabel.setForeground(Color.DARK_GRAY);
        }

        private void customersButtonMouseExited(java.awt.event.MouseEvent evt) {
            customersButton.setBackground(new Color(51, 51, 51));
            customersLabel.setForeground(Color.WHITE);
        }

        private void ordersButtonMouseEntered(java.awt.event.MouseEvent evt) {
            ordersButton.setBackground(new Color(255, 204, 0));
            ordersLabel.setForeground(Color.DARK_GRAY);
        }

        private void ordersButtonMouseExited(java.awt.event.MouseEvent evt) {
            ordersButton.setBackground(new Color(51, 51, 51));
            ordersLabel.setForeground(Color.WHITE);
        }

        private void editArtistButtonMouseExited(java.awt.event.MouseEvent evt) {
            editArtistButton.setBackground(new Color(51, 51, 51));
            editArtistLabel.setForeground(Color.WHITE);
        }

        private void editArtistButtonMouseEntered(java.awt.event.MouseEvent evt) {
            editArtistButton.setBackground(new Color(255, 204, 0));
            editArtistLabel.setForeground(Color.DARK_GRAY);
        }

        private void editArtistLabelMouseEntered(java.awt.event.MouseEvent evt) {
            editArtistButton.setBackground(new Color(255, 204, 0));
            editArtistLabel.setForeground(Color.DARK_GRAY);
        }

        private void editArtistLabelMouseExited(java.awt.event.MouseEvent evt) {
            editArtistButton.setBackground(new Color(51, 51, 51));
            editArtistLabel.setForeground(Color.WHITE);
        }

        private void addArtistButtonMouseClicked(java.awt.event.MouseEvent evt) {
            this.removeAll();
            addArtist.show();
        }

        private void addArtistLabelMouseClicked(java.awt.event.MouseEvent evt) {
            addArtistButtonMouseClicked(evt);
        }

        private void ordersButtonMouseClicked(java.awt.event.MouseEvent evt) {
            this.removeAll();
            orders.show();
        }

        private void ordersLabelMouseClicked(java.awt.event.MouseEvent evt) {
            ordersButtonMouseClicked(evt);
        }

        private void customersButtonMouseClicked(java.awt.event.MouseEvent evt)
        {
            this.removeAll();
            customers.show();
        }

        private void customersLabelMouseClicked(java.awt.event.MouseEvent evt)
        {
            customersButtonMouseClicked(evt);
        }

        private void editArtistButtonMouseClicked(java.awt.event.MouseEvent evt) {
            this.removeAll();
            editArtist.show();
        }

        private void editArtistLabelMouseClicked(java.awt.event.MouseEvent evt) {
            editArtistButtonMouseClicked(evt);
        }

        private void logOutButtonMouseClicked(java.awt.event.MouseEvent evt) {
            removeAll();
            login.show();
        }

        private void logOutLabelMouseClicked(java.awt.event.MouseEvent evt) {
            logOutButtonMouseClicked(evt);
        }

        private void logOutButtonMouseEntered(java.awt.event.MouseEvent evt) {
            logOutButton.setBackground(new Color(255, 204, 0));
            logOutLabel.setForeground(Color.DARK_GRAY);
        }

        private void logOutButtonMouseExited(java.awt.event.MouseEvent evt) {
            logOutButton.setBackground(new Color(51, 51, 51));
            logOutLabel.setForeground(Color.WHITE);
        }

        private void logOutLabelMouseEntered(java.awt.event.MouseEvent evt) {
            logOutButton.setBackground(new Color(255, 204, 0));
            logOutLabel.setForeground(Color.DARK_GRAY);
        }

        private void logOutLabelMouseExited(java.awt.event.MouseEvent evt) {
            logOutButton.setBackground(new Color(51, 51, 51));
            logOutLabel.setForeground(Color.WHITE);
        }

    }
    final Login login = new Login();
    final AddArtist addArtist = new AddArtist();
    final EditArtist editArtist = new EditArtist();
    final Customers customers = new Customers();
    final Orders orders = new Orders();
    final Administrators administrators = new Administrators();

    public Settings() {
        super();

        login.show();
    }

}
