/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import java.math.BigDecimal;
import rs.etf.sab.operations.PackageOperations;

/**
 *
 * @author laki
 */
public class ml170233_OfferPair implements PackageOperations.Pair<Integer, BigDecimal>{

    private Integer idPackage;
    private BigDecimal percentage;


    public ml170233_OfferPair(Integer idPackage, BigDecimal percentage){
        this.idPackage = idPackage;
        this.percentage = percentage;
    }
    @Override
    public Integer getFirstParam() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return this.idPackage;
    }

    @Override
    public BigDecimal getSecondParam() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return this.percentage;
    }
    
}
