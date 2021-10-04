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
import rs.etf.sab.operations.DistrictOperations;

/**
 *
 * @author laki
 */
public class ml170233_DistrictOperations implements DistrictOperations {

    
    Connection conn = DB.getInstance().getConnection();

    /*
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
    
    */
    @Override
    public int insertDistrict(String string, int i, int i1, int i2) {
        try {
            if(this.districtExists(string))return -1;
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            String query = "insert into district (Name, IdCity, X_coord, Y_coord ) values (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, string);
            ps.setInt(2, i);
            ps.setInt(3, i1);
            ps.setInt(4, i2);
            int s = ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            boolean x = rs.next();
           if(x) return rs.getInt(1);
           //if(s>0) return 0;
        } catch (SQLException ex) {
            Logger.getLogger(ml170233_DistrictOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    @Override
    public int deleteDistricts(String... districts) {
       String query = "delete from district where name = ?";
        int ret = 0;
        try (PreparedStatement ps = conn.prepareStatement(query)){
             for(String district:districts){
                  ps.setString(1, district);
                  ret+=ps.executeUpdate();
              }
        } catch (SQLException ex) {
            Logger.getLogger(ml170233_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteDistrict(int i) {
         try {
            String query = "delete from District where IdDistrict = ?";
            
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, i);
            
            int x = ps.executeUpdate();
            if(x == 1) {return true;}
        } catch (SQLException ex) {
            Logger.getLogger(ml170233_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
        
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int deleteAllDistrictsFromCity(String string) {
        try {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            String query_get_id_from_name = "select IdCity from city where name = ?";
            PreparedStatement ps1 = conn.prepareStatement(query_get_id_from_name);
            int city_id = 0;
            ps1.setString(1, string);
            ResultSet rs = ps1.executeQuery();
            if(rs.next())
               city_id = rs.getInt(1);
            String query_delete = "delete from district where IdCity = ?";
            PreparedStatement ps2 = conn.prepareStatement(query_delete);
            ps2.setInt(1, city_id);
            int x = ps2.executeUpdate();
            if(x>0)return x;
                    
            return 0;
        } catch (SQLException ex) {
            Logger.getLogger(ml170233_DistrictOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    
     private boolean districtExists(String naziv){
        try {
            String query = "select IdDistrict from district where Name = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, naziv);
            ResultSet rs = ps.executeQuery();
            if(rs.next())return true;
        } catch (SQLException ex) {
            Logger.getLogger(ml170233_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    @Override
    public List<Integer> getAllDistrictsFromCity(int i) {
        try {
            List districts = new ArrayList<Integer>();
            String query = "select IdDistrict from district where IdCity = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, i);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
                districts.add(rs.getInt(1));                
            return districts;
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            Logger.getLogger(ml170233_DistrictOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Integer> getAllDistricts() {
        try {
            String query = "select name from district";
            PreparedStatement ps = conn.prepareStatement(query);
            List districts = new ArrayList<String>();
            ResultSet rs = ps.executeQuery();
            while(rs.next())
                districts.add(rs.getString(1));
            return districts;
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            Logger.getLogger(ml170233_DistrictOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
