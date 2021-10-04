package student;

import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import rs.etf.sab.operations.CityOperations;
import rs.etf.sab.operations.CourierOperations;
import rs.etf.sab.operations.CourierRequestOperation;
import rs.etf.sab.operations.DistrictOperations;
import rs.etf.sab.operations.GeneralOperations;
import rs.etf.sab.operations.PackageOperations;
import rs.etf.sab.operations.UserOperations;
import rs.etf.sab.operations.VehicleOperations;
import rs.etf.sab.tests.TestRunner;
import rs.etf.sab.tests.TestHandler;



public class StudentMain {

    public static void main(String[] args) {
        CityOperations cityOperations = new ml170233_CityOperations(); // Change this to your implementation.
        DistrictOperations districtOperations = new ml170233_DistrictOperations(); // Do it for all classes.
        CourierOperations courierOperations = new ml170233_CourierOperations(); // e.g. = new MyDistrictOperations();
        CourierRequestOperation courierRequestOperation = new ml170233_CourierRequestOperation();
        GeneralOperations generalOperations = new ml170233_GeneralOperations();
        UserOperations userOperations = new ml170233_UserOperations();
        VehicleOperations vehicleOperations = new ml170233_VehicleOperations();
        PackageOperations packageOperations = new ml170233_PackageOperations();

      TestHandler.createInstance(
                cityOperations,
                courierOperations,
                courierRequestOperation,
                districtOperations,
                generalOperations,
                userOperations,
                vehicleOperations,
                packageOperations);

        TestRunner.runTests();
    
        generalOperations.eraseAll();
      
        final String courierLastName = "Ckalja8";
        final String courierFirstName = "Pero";
        final String courierUsername = "perkan1234543534";
        String password = "sabi2018";
        boolean x = userOperations.insertUser(courierUsername, courierFirstName, courierLastName, password);
        System.out.print(x);
        final String licencePlate = "BG323WE";
        final int fuelType = 0;
        final BigDecimal fuelConsumption = new BigDecimal(8.3);
        vehicleOperations.insertVehicle(licencePlate, fuelType, fuelConsumption);
        
        courierRequestOperation.insertCourierRequest(courierUsername, licencePlate);
        
        courierRequestOperation.grantRequest(courierUsername);
        if(courierOperations.getAllCouriers().contains(courierUsername)){
            System.out.print("Success 1");
        }
        
        final String senderUsername = "masa";
        final String senderFirstName = "Masana";
        final String senderLastName = "Leposava";
        password = "lepasampasta1";
        userOperations.insertUser(senderUsername, senderFirstName, senderLastName, password);
        final int cityId = cityOperations.insertCity("Novo Milosevo", "21234");
        final int cordXd1 = 10;
        final int cordYd1 = 2;
        final int districtIdOne = districtOperations.insertDistrict("Novo Milosevo", cityId, cordXd1, cordYd1);
        System.out.println("Id prvog distrikta je " + districtIdOne);
        final int cordXd2 = 2;
        final int cordYd2 = 10;
        final int districtIdTwo = districtOperations.insertDistrict("Vojinovica", cityId, cordXd2, cordYd2);
        System.out.println("Id drugog distrikta je "+districtIdTwo);
        final int type1 = 0;
        final BigDecimal weight1 = new BigDecimal(123);
        final int packageId1 = packageOperations.insertPackage(districtIdOne, districtIdTwo, courierUsername, type1, weight1);
        final BigDecimal packageOnePrice = Util.getPackagePrice(type1, weight1, Util.euclidean(cordXd1, cordYd1, cordXd2, cordYd2), new BigDecimal(5));
       int offerId = packageOperations.insertTransportOffer(courierUsername, packageId1, new BigDecimal(5));
        
        //System.err.println(offerId);
        packageOperations.acceptAnOffer(offerId);
        final int type2 = 1;
        final BigDecimal weight2 = new BigDecimal(321);
        final int packageId2 = packageOperations.insertPackage(districtIdTwo, districtIdOne, courierUsername, type2, weight2);
        final BigDecimal packageTwoPrice = Util.getPackagePrice(type2, weight2, Util.euclidean(cordXd1, cordYd1, cordXd2, cordYd2), new BigDecimal(5));
        offerId = packageOperations.insertTransportOffer(courierUsername, packageId2, new BigDecimal(5));
        packageOperations.acceptAnOffer(offerId);
        final int type3 = 1;
        final BigDecimal weight3 = new BigDecimal(222);
        final int packageId3 = packageOperations.insertPackage(districtIdTwo, districtIdOne, courierUsername, type3, weight3);
        final BigDecimal packageThreePrice = Util.getPackagePrice(type3, weight3, Util.euclidean(cordXd1, cordYd1, cordXd2, cordYd2), new BigDecimal(5));
        offerId = packageOperations.insertTransportOffer(courierUsername, packageId3, new BigDecimal(5));
        packageOperations.acceptAnOffer(offerId);
        
        //test1
        System.err.println(packageOperations.getDeliveryStatus(packageId1));
        if(1==packageOperations.getDeliveryStatus(packageId1))
        {
            System.out.print("uspesan test - 1");
        }
       int xr = packageOperations.driveNextPackage(courierUsername);
        System.out.println(xr);
        if(packageId1 ==  xr){
            System.out.println("uspesan test - 2");
        }

    }
}
