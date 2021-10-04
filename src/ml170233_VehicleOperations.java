/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.html.HTMLDocument;
import rs.etf.sab.operations.VehicleOperations;

/**
 *
 * @author laki
 */
public class ml170233_VehicleOperations implements VehicleOperations{

    
            Connection conn = DB.getInstance().getConnection();

    @Override
    public boolean insertVehicle(String regnumber, int fueltype, BigDecimal waste) {
                try {
                    String query = "insert into vehicle (RegNumber, FuelType, Waste) values (?,?,?)";
                    PreparedStatement ps = conn.prepareStatement(query);
                    ps.setString(1, regnumber);
                    ps.setInt(2, fueltype);
                    ps.setBigDecimal(3, waste);
                    int x = ps.executeUpdate();
                    if(x>0)return true;
                    // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                } catch (SQLException ex) {
                    Logger.getLogger(ml170233_VehicleOperations.class.getName()).log(Level.SEVERE, null, ex);
                }
                
        return false;
    }

   private boolean deleteVehicle(String vehicleid){
            try {
                String query = "delete from vehicle where RegNumber = ?";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, vehicleid);
                int x = ps.executeUpdate();
                if(x>0) return true;
            } catch (SQLException ex) {
                Logger.getLogger(ml170233_UserOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        return false;
    }

    
    @Override
    public int deleteVehicles(String... strings) {
        int br = 0;
        for(String vehicle:strings){
            boolean x = this.deleteVehicle(vehicle);
            if(x)br++;
        }
        return br;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getAllVehichles() {
                try {
                    List vehicles = new ArrayList<String>();
                    String query = "select RegNumber from vehicle";
                    PreparedStatement ps = conn.prepareStatement(query);
                    ResultSet rs = ps.executeQuery();
                    while(rs.next())
                        vehicles.add(rs.getString(1));
                    return vehicles;
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                } catch (SQLException ex) {
                    Logger.getLogger(ml170233_VehicleOperations.class.getName()).log(Level.SEVERE, null, ex);
                }
        return null;
    }

    @Override
    public boolean changeFuelType(String string, int i) {
                try {
                    String query = "update vehicle set FuelType = ? where RegNumber = ?";
                    PreparedStatement ps = conn.prepareStatement(query);
                    ps.setInt(1, i);
                    ps.setString(2, string);
                    int x = ps.executeUpdate();
                    if(x>0)return true;
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                } catch (SQLException ex) {
                    Logger.getLogger(ml170233_VehicleOperations.class.getName()).log(Level.SEVERE, null, ex);
                }
                return false;
    }

    @Override
    public boolean changeConsumption(String string, BigDecimal bd) {
                try {
                    // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    String query = "update vehicle set Waste = ? where RegNumber = ?";
                    PreparedStatement ps = conn.prepareStatement(query);
                    ps.setBigDecimal(1, bd);
                    ps.setString(2, string);
                    int x = ps.executeUpdate();
                    if(x>0)return true;
                } catch (SQLException ex) {
                    Logger.getLogger(ml170233_VehicleOperations.class.getName()).log(Level.SEVERE, null, ex);
                }
       return false;
    }
    
}
