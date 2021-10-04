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
import rs.etf.sab.operations.CourierOperations;

/**
 *
 * @author laki
 */
public class ml170233_CourierOperations implements CourierOperations{

        Connection conn = DB.getInstance().getConnection();

    
    @Override
    public boolean insertCourier(String username, String regNumber) {
            try {
                String query_insert = "insert into courir (Username, RegNumber, Number_of_deliever_packets, Profit, Status) values (?,?,0,0,0)";
                String courier_list = "select username from courir where RegNumber = ?";
                
                PreparedStatement ps = conn.prepareStatement(courier_list);
                ps.setString(1, regNumber);
                ResultSet rs = ps.executeQuery();
                List<String> ls = new ArrayList<String>();
                while(rs.next())
                    ls.add(rs.getString(1));
                if(ls.isEmpty()){
                    PreparedStatement ps2 = conn.prepareStatement(query_insert);
                    ps2.setString(1, username);
                    ps2.setString(2, regNumber);
                    int x = ps2.executeUpdate();
                    if(x>0) return true;
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(ml170233_CourierOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
    }

    @Override
    public boolean deleteCourier(String string) {
            try {
                String query = "delete from courir where username = ?";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, string);
                int x = ps.executeUpdate();
                if(x>0) return true;
                
                
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            } catch (SQLException ex) {
                Logger.getLogger(ml170233_CourierOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
    }

    @Override
    public List<String> getCouriersWithStatus(int i) {
            try {
                String query = "select username from courir where status = ?";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setInt(1, i);
                ResultSet res = ps.executeQuery();
                List<String> ret = new ArrayList<>();
                while(res.next())
                    ret.add(res.getString(1));
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            } catch (SQLException ex) {
                Logger.getLogger(ml170233_CourierOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        return new ArrayList<String>();
    }

    @Override
    public List<String> getAllCouriers() {
            try {
                String query = "select username from courir";
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery();
                List ret = new ArrayList<String>();
                while(rs.next())
                    ret.add(rs.getString(1));
                return ret;
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            } catch (SQLException ex) {
                Logger.getLogger(ml170233_CourierOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
    }

    @Override
    public BigDecimal getAverageCourierProfit(int i) {
            try {
                String query = "select profit from courir where Number_of_deliever_packets>?";
                Integer ukupno = 0,br=0;
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setInt(1, i);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    ukupno+= rs.getInt(1);
                    br++;
                }
                return new BigDecimal((float)ukupno/br);
               // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            } catch (SQLException ex) {
                Logger.getLogger(ml170233_CourierOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
    }   
}
