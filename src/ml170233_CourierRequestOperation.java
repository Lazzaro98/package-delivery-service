/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.etf.sab.operations.CourierRequestOperation;

/**
 *
 * @author laki
 */
public class ml170233_CourierRequestOperation implements CourierRequestOperation{

    
    Connection conn = DB.getInstance().getConnection();

    
    @Override
    public boolean insertCourierRequest(String string, String string1) {
        
        try {
            String query = "insert into request (username, RegNumber) values (?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, string);
            ps.setString(2, string1);
            int x = ps.executeUpdate();
            if (x>0) return true;
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            Logger.getLogger(ml170233_CourierRequestOperation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean deleteCourierRequest(String string) {
        try {
            String query = "delete from request where username = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, string);
            int x = ps.executeUpdate();
            if(x>0) return true;
        } catch (SQLException ex) {
            Logger.getLogger(ml170233_CourierRequestOperation.class.getName()).log(Level.SEVERE, null, ex);
        }
    return false;
}

    @Override
    public boolean changeVehicleInCourierRequest(String string, String string1) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        String query = "update request set RegNumber = ? where username = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, string1);
            ps.setString(2, string);
            int x = ps.executeUpdate();
            if(x>0) return true;
        } catch (SQLException ex) {
            Logger.getLogger(ml170233_CourierRequestOperation.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }

    @Override
    public List<String> getAllCourierRequests() {
        try {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            String query = "select username from request";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            List<String> lista = new ArrayList<String>();
            while(rs.next())
                lista.add(rs.getString(1));
            return lista;
            
        } catch (SQLException ex) {
            Logger.getLogger(ml170233_CourierRequestOperation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public boolean grantRequest(String string) {
        
        try {
            String query = "{call [dbo].[SPGrantCourierRequest](?, ?)}";
            CallableStatement cs = conn.prepareCall(query);
            cs.setString(2, string);
            cs.registerOutParameter(1, Types.INTEGER);
            boolean execute = cs.execute();
            int x = cs.getInt(1);
            if (x>0) return true;
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(ml170233_CourierRequestOperation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
}
