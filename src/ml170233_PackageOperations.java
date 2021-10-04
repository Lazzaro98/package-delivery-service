/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.etf.sab.operations.PackageOperations;

/**
 *
 * @author laki
 */
public class ml170233_PackageOperations implements PackageOperations{

    
    Connection conn = DB.getInstance().getConnection();
    @Override
    public int insertPackage(int IdDistrictFrom, int IdDistrictTo, String UsernameSender, int Type, BigDecimal Weight) {
        int id = -1;
        try {
            String query = "insert into package (IdDistrictFrom, IdDistrictTo,UsernameSender, Type, Weight, Status) values (?,?,?,?,?, 0)";
            
            PreparedStatement ps = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, IdDistrictFrom);
            ps.setInt(2, IdDistrictTo);
            ps.setString(3, UsernameSender);
            ps.setInt(4, Type);
            ps.setBigDecimal(5, Weight);
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next())
                id = rs.getInt(1);
            
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            Logger.getLogger(ml170233_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    
    private boolean doesCurierDrive(String courierUsername){
        try {
        //0 ne vozi, 1 vozi
        String query = "select username from Courir where username=? and status=0";
        PreparedStatement ps = conn.prepareStatement(query);
        
        ps.setString(1, courierUsername);
        
        ResultSet rs = ps.executeQuery();
        if(rs.next())return true;
        } catch (SQLException ex) {
            Logger.getLogger(ml170233_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
    return false;
    }
    
    private boolean isPackageReady(int id){
        try {
            String query = "select IdPackage from package where IdPackage = ? and status = 0";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next())return true;
        } catch (SQLException ex) {
            Logger.getLogger(ml170233_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    private int insertOffer(String username, int idPackage, BigDecimal percent){
        int id = -1;
        try {
            String query = "insert into Offer(Username, IdPackage, PercentP) values (?,?,?)";
            PreparedStatement ps = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, username);
            ps.setInt(2, idPackage);
            ps.setBigDecimal(3, percent);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next())
                id = rs.getInt(1);
        } catch (SQLException ex) {
            Logger.getLogger(ml170233_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }
    @Override
    public int insertTransportOffer(String username, int idPackage, BigDecimal percent) {
        if(!this.doesCurierDrive(username) && this.isPackageReady(idPackage)){
           return this.insertOffer(username, idPackage, percent);
        }
        return -1;
    }
    @Override
    public boolean acceptAnOffer(int i) {
        try {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            String query = "update offer set status = 1 where IdOffer = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, i);
            int x = ps.executeUpdate();
            if(x>0)return true;
        } catch (SQLException ex) {
            Logger.getLogger(ml170233_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public List<Integer> getAllOffers() {
        try {
            String query = "select id from offer";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            List listaPonuda = new ArrayList<Integer>();
            while(rs.next())
                listaPonuda.add(rs.getInt(1));
            return listaPonuda;
            
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            Logger.getLogger(ml170233_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Pair<Integer, BigDecimal>> getAllOffersForPackage(int packetid) {
        List<Pair<Integer, BigDecimal>> list = new ArrayList<Pair<Integer, BigDecimal>>();
        try {
            String query = "select idOffer, PercentP from offer where idPackage = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, packetid);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
                list.add(new ml170233_OfferPair(rs.getInt(1), rs.getBigDecimal(2)));
            return list;
            
//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            Logger.getLogger(ml170233_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public boolean deletePackage(int i) {
        try {
            String query = "delete from package where IdPackage = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, i);
            int x = ps.executeUpdate();
            if(x>0)return true;
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            Logger.getLogger(ml170233_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean changeWeight(int pid, BigDecimal w) {
        try {
            String query = "update Package set Weight = ? where IdPackage = ? and status = 0";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setBigDecimal(1, w);
            ps.setInt(2, pid);
            int x = ps.executeUpdate();
            if(x>0)return true;
//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            Logger.getLogger(ml170233_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean changeType(int pid, int type) {
        try {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            String query = "update package set type = ? where idPackage = ? and status = 0";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, type);
            ps.setInt(2, pid);
            int x = ps.executeUpdate();
            if(x>0)return true;
        } catch (SQLException ex) {
            Logger.getLogger(ml170233_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public Integer getDeliveryStatus(int pid) {
        try {
            String query = "select status from package where IdPackage = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, pid);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
                return rs.getInt(1);
            
//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            Logger.getLogger(ml170233_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public BigDecimal getPriceOfDelivery(int pid) {
        try {
            String query = "select price from package where IdPackage = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, pid);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
                return rs.getBigDecimal(1);
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            Logger.getLogger(ml170233_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Date getAcceptanceTime(int pid) {
        try {
            String query = "select Time from package where IdPackage = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, pid);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
                return rs.getDate(1);
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            Logger.getLogger(ml170233_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Integer> getAllPackagesWithSpecificType(int i) {
        try {
            List list = new ArrayList<Integer>();
            String query = "select IdPackage from package where type = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, i);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
                list.add(rs.getInt(1));
            return list;
                
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            Logger.getLogger(ml170233_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Integer> getAllPackages() {
        try {
            List packages = new ArrayList<Integer>();
            String query = "select IdPackage from package";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
                packages.add(rs.getInt(1));
            return packages;
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            Logger.getLogger(ml170233_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Integer> getDrive(String string) {
        try {
            List list = new ArrayList<Integer>();
            String query = "select IdPackage from package where UsernameCourir = ? and status = 2";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, string);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
                list.add(rs.getInt(1));
            return list;
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            Logger.getLogger(ml170233_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    private int getCurierStatus(String courierUsername){
        try {
            String query = "select status from courir where username = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, courierUsername);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
                return rs.getInt(1);
        } catch (SQLException ex) {
            Logger.getLogger(ml170233_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -2;
    }
    
    public boolean packageStatusUpdate(String username){
        try {
            String query = "update package set status = 2 where UsernameCourir = ? and status = 1";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username);
            int x = ps.executeUpdate();
            if(x>0) return true;
         
        } catch (SQLException ex) {
            Logger.getLogger(ml170233_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
           return false;
    }
     
    
    
    private void getNextPackage(String username, Integer packages1[],Integer from1[], Integer to1[]){
            Integer packages[] ={-1,-1};
            Integer from[]= {-1, -1};
            Integer to[] = {-1, -1};
        try {
           
            
            String query = "select top 2 IdPackage, IdDistrictFrom, IdDistrictTo from Package where UsernameCourir = ? and status = 2 order by Time";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                packages[0] = rs.getInt(1);
                from[0] = rs.getInt(2);
                to[0] = rs.getInt(3);
            }
            if(rs.next()){
                packages[1] = rs.getInt(1);
                from[0] = rs.getInt(2);
                to[0] = rs.getInt(3);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ml170233_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        packages1 = packages;
        from1 = from;
        to1 = to;
    }
    public int updateNextPackageStatus(int id){
        try {
            String query = "update package set status = 3 where IdPackage = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            int x = ps.executeUpdate();
            if(x>0)return x;
        } catch (SQLException ex) {
            Logger.getLogger(ml170233_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -2;
    }
    
    private void callSQLGasFunction(String cusername, int from ,int to){
        try {
            if(from==-1)return;
            String query = "{call GasBills(?, ?, ?)}";
            
            CallableStatement cs = conn.prepareCall(query);
            cs.setString(1, cusername);
            cs.setInt(3, from);
            cs.setInt(2, to);
            cs.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ml170233_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    @Override
    public int driveNextPackage(String string) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        int status = this.getCurierStatus(string);
        if(status == 0) this.packageStatusUpdate(string);
        Integer packages[]={-1,-1}, from[]={-1,-1}, to[]={-1,-1};
        this.getNextPackage(string, packages, from, to);
        int r = this.updateNextPackageStatus(packages[0]);
        this.callSQLGasFunction(string, from[0], to[0]);
        this.callSQLGasFunction(string, from[1], to[0]);
        return packages[0];
       
        
    }
    
}
