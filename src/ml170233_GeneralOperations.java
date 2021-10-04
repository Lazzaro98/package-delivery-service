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
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.etf.sab.operations.GeneralOperations;

/**
 *
 * @author laki
 */
public class ml170233_GeneralOperations implements GeneralOperations{
        Connection conn = DB.getInstance().getConnection();

   /* query= "DELETE FROM [dbo].[City]\n";
    query+= "DELETE FROM [dbo].[District]\n";
    query+= "DELETE FROM [dbo].[Offer]\n";
    query+= "DELETE FROM [dbo].[Package]\n";
    query+= "DELETE FROM [dbo].[Request]\n";
    query+= "DELETE FROM [dbo].[User]\n";
    query+= "DELETE FROM [dbo].[Vehicle]\n";
    query+= "DELETE FROM [dbo].[Courir]";*/

        
        private boolean deleteAdmin(){
            try {
                String queryDelete = "DELETE FROM [Admin]";
                PreparedStatement ps = conn.prepareStatement(queryDelete);
                int x = ps.executeUpdate();
                if(x>0) return true;
            } catch (SQLException ex) {
                Logger.getLogger(ml170233_GeneralOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
        }
        private boolean deleteOffer(){
            try {
                String queryDelete = "DELETE FROM [Offer]";
                PreparedStatement ps = conn.prepareStatement(queryDelete);
                int x = ps.executeUpdate();
                if(x>0) return true;
            } catch (SQLException ex) {
                Logger.getLogger(ml170233_GeneralOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
        }
         private boolean deleteRequest(){
            try {
                String queryDelete = "DELETE FROM [Request]";
                PreparedStatement ps = conn.prepareStatement(queryDelete);
                int x = ps.executeUpdate();
                if(x>0) return true;
            } catch (SQLException ex) {
                Logger.getLogger(ml170233_GeneralOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
        }
          private boolean deletePackage(){
            try {
                String queryDelete = "DELETE FROM [Package]";
                PreparedStatement ps = conn.prepareStatement(queryDelete);
                int x = ps.executeUpdate();
                if(x>0) return true;
            } catch (SQLException ex) {
                Logger.getLogger(ml170233_GeneralOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
        }
          
           private boolean deleteDistrict(){
            try {
                String queryDelete = "DELETE FROM [District]";
                PreparedStatement ps = conn.prepareStatement(queryDelete);
                int x = ps.executeUpdate();
                if(x>0) return true;
            } catch (SQLException ex) {
                Logger.getLogger(ml170233_GeneralOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
        }
        private boolean deleteCity(){
            try {
                String queryDeleteCity = "DELETE FROM [City]";
                PreparedStatement ps = conn.prepareStatement(queryDeleteCity);
                int x = ps.executeUpdate();
                if(x>0) return true;
            } catch (SQLException ex) {
                Logger.getLogger(ml170233_GeneralOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
        }
         private boolean deleteCourir(){
            try {
                String queryDelete = "DELETE FROM [Courir]";
                PreparedStatement ps = conn.prepareStatement(queryDelete);
                int x = ps.executeUpdate();
                if(x>0) return true;
            } catch (SQLException ex) {
                Logger.getLogger(ml170233_GeneralOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
        }
         private boolean deleteVehicle(){
            try {
                String queryDelete = "DELETE FROM [Vehicle]";
                PreparedStatement ps = conn.prepareStatement(queryDelete);
                int x = ps.executeUpdate();
                if(x>0) return true;
            } catch (SQLException ex) {
                Logger.getLogger(ml170233_GeneralOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
        }
          private boolean deleteUser(){
            try {
                String queryDelete = "DELETE FROM [User]";
                PreparedStatement ps = conn.prepareStatement(queryDelete);
                int x = ps.executeUpdate();
                if(x>0) return true;
            } catch (SQLException ex) {
                Logger.getLogger(ml170233_GeneralOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
        }
       
        
    @Override
    public void eraseAll() {
            this.deleteAdmin();
            this.deleteOffer();
            this.deleteRequest();
            this.deletePackage();
            this.deleteDistrict();
            this.deleteCity();
            this.deleteCourir();
            this.deleteVehicle();
            this.deleteUser();
    }
    
}
