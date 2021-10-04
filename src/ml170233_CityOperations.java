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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.etf.sab.operations.CityOperations;

/**
 *
 * @author laki
 */
public class ml170233_CityOperations implements CityOperations{

    Connection conn = DB.getInstance().getConnection();
    
    private boolean cityExists(String naziv, String postanskiBroj){
        try {
            String query = "select IdCity from city where Name = ? or PostNumber = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, naziv);
            ps.setString(2, postanskiBroj);
            ResultSet rs = ps.executeQuery();
            if(rs.next())return true;
        } catch (SQLException ex) {
            Logger.getLogger(ml170233_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    @Override
    public int insertCity(String naziv, String postanskiBroj) {
        if(this.cityExists(naziv, postanskiBroj))return -1;
        String query = "insert into city (Name, PostNumber) values (?, ?)";
        int ret = -1;
        try (PreparedStatement ps = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)){
            ps.setString(1, naziv);
            ps.setString(2, postanskiBroj);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()) ret = rs.getInt(1);
        } catch (SQLException ex) {
            Logger.getLogger(ml170233_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int deleteCity(String... cities) {
        String query = "delete from city where name = ?";
        int ret = 0;
        try (PreparedStatement ps = conn.prepareStatement(query)){
             for(String city:cities){
                  ps.setString(1, city);
                  int x = ps.executeUpdate();
                  if(x>0)ret++;
              }
        } catch (SQLException ex) {
            Logger.getLogger(ml170233_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteCity(int i) {
        try {
            String query = "delete from city where IdCity = ?";
            
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, i);
            
            int x = ps.executeUpdate();
            if(x > 0) {return true;}
        } catch (SQLException ex) {
            Logger.getLogger(ml170233_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public List<Integer> getAllCities() {
        try {
            String query = "select IdCity from city";
            List<Integer> r = new ArrayList<>();
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while(rs.next())
                r.add(rs.getInt(1));
            return r;
        } catch (SQLException ex) {
            Logger.getLogger(ml170233_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
