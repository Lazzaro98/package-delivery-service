/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.etf.sab.operations.UserOperations;

/**
 *
 * @author laki
 */
public class ml170233_UserOperations implements UserOperations{

        Connection conn = DB.getInstance().getConnection();

    private boolean isPassValid(String password){
        Boolean validLength = false, hasOneLetter=false, hasOneNumber=false;
        if(password.length()>7)validLength=true;
        for(int i = 0;i<password.length();i++){
            if(Character.isLetter(password.charAt(i))) hasOneLetter= true;
            if(Character.isDigit(password.charAt(i)))hasOneNumber = true;
        }
        return validLength && hasOneLetter && hasOneNumber;
    }
    private boolean isFirstNameValid(String firstName){
        return Character.isUpperCase(firstName.charAt(0));
    }
    private boolean isLastNameValud(String lastName){
        return Character.isUpperCase(lastName.charAt(0));
    }
    private boolean isUsernameValid(String username){
        return !this.checkIfUserExists(username);
    }
    @Override
    public boolean insertUser(String username, String name, String surname, String password) {
         String query = "insert into [User] (Username, Name, Surname, Password, Number_of_sent_packets) values (?, ?, ?, ?, ?)";
        int ret = 0;
         
                if(!this.isFirstNameValid(name) 
                || !this.isLastNameValud(surname) 
                || !this.isPassValid(password))
            return false;
        try (PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1, username);
            ps.setString(2, name);
            ps.setString(3, surname);
            ps.setString(4, password);
            ps.setInt(5, 0);
            int x = ps.executeUpdate();
            if (x>0) return true;
        } catch (SQLException ex) {
            Logger.getLogger(ml170233_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
// throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    private int declare(String username){
            try {
                String query = "insert into Admin (username) values (?)";
                
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, username);
                if (stmt.executeUpdate() == 1)
                    return 0;
            } catch (SQLException ex) {
                Logger.getLogger(ml170233_UserOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
            return 3; 
    }
    
    private boolean checkIfUserExists(String username){
            try {
                String query= "select username from [User] where username=?";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, username);
                ResultSet rs = ps.executeQuery();
                if(rs.next())return true;
            } catch (SQLException ex) {
                Logger.getLogger(ml170233_UserOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
    }
    
    private boolean checkIfUserAdmin(String username){
            try {
                String query = "select username from Admin where username = ?";
                PreparedStatement ps = conn.prepareStatement(query);
                
                ps.setString(1, username);
                ResultSet rs = ps.executeQuery();
                if(rs.next())return true;
            } catch (SQLException ex) {
                Logger.getLogger(ml170233_UserOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        return false;
    }
    @Override
    public int declareAdmin(String string) {
        if(this.checkIfUserAdmin(string))return 1;
        else if (!this.checkIfUserExists(string)) return 2;
        return declare(string);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    private Integer getSentPacketsByUser(String user){
            try {
                String query = "select Number_of_sent_packets from [user] where username = ?";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, user);
                ResultSet rs = ps.executeQuery();
                if(rs.next())
                    return rs.getInt(1);
            } catch (SQLException ex) {
                Logger.getLogger(ml170233_UserOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        return 0;
    }
    @Override
    public Integer getSentPackages(String... users) {
        
        int ukupnan_broj_paketa = 0;
        for(String user:users){
            if(!this.checkIfUserExists(user))return null;
            ukupnan_broj_paketa += this.getSentPacketsByUser(user);
        }
        if(ukupnan_broj_paketa == 0) return 0;
        return ukupnan_broj_paketa;
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private boolean deleteUser(String user){
            try {
                String query = "delete from [user] where username = ?";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, user);
                int x = ps.executeUpdate();
                if(x>0) return true;
            } catch (SQLException ex) {
                Logger.getLogger(ml170233_UserOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        return false;
    }
    @Override
    public int deleteUsers(String... users) {
        int num = 0;
        for(String user:users){
            boolean x = this.deleteUser(user);
            if(x)num++;
        }
        return num;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getAllUsers() {
        List users = new ArrayList<String>();
        String query = "select username from [user]";
            try {
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery();
                while(rs.next())
                    users.add(rs.getString(1));
                return users;
                // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            } catch (SQLException ex) {
                Logger.getLogger(ml170233_UserOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
    }
}
