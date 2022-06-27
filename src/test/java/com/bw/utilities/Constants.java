package com.bw.utilities;

import java.util.ArrayList;

public class Constants {
	public static String inspectionName = "";
	public static String templateName = "";
	public static String driverID = "1010";
	public static String vehicleNum = "";
	public static String fleetName = "TestFleet";
	public static String IE = "ie";
	public static String CHROME = "chrome";
	public static String FF = "firefox";

	public static String ANDROID = "android";

	public static String DRIVER = "driver";
	public static String VEHICLE = "1.6_Vehicle";
	public static int genXport = 9999;
	public static String trackId = "TestDeviceRK";
	public static String serialNumber = "";
	public static String bySerialNumber = "com.itelematic.drive.dashboard:id/serialNumber";
	public static String byTrackId = "com.itelematic.drive.dashboard:id/deviceId";
	public static String deviceType = "Drive";
	public static String driverName = "Sanjay Demo 11";
	public static int wait_time = 15;
	public static int pooling_interval = 5;
	public static String driverGroup = "Test cigniti";
	public static String annotation_MarkReview_Unidentified = "";
	public static String annotation_AssignDriver_Unidentified = "";
	public static String configFolderPath = "src/test/resources/config/";
	public static String jsonFolderPath = "src/test/resources/api/";
	public static String vehicleName = "TestVehicle";
	public static String CarrierName="";
	public static String DivisionName="AutoDivision11516";
	public static int numberOfVehiclesToInstall = 2;
	public static int numberOfUsersToInstall = 2;
	public static String fleetStatus = "All Fleets";
	public static String alertplanName = "TestAlertPlan";
	public static String alertEventName = "Accident";
	public static String CompanyName="CoreHub ELD CloudQA Test Company";
    public static String vehicleRegistration = "RegNum";
    public static String vehicleModel = "TestModel";
    public static String vehicleColor = "Blue";
    public static String vehicleTransmission = "TestTransmission";
    public static String vehicleEngineMake = "TestEngineMake";
    public static String yearOfManufacture = "2020";
    public static String vehicleType = "Refrigerated Truck";
    public static String division = "Cigniti Division";
    public static String terminal = "Cigniti Terminal";
    public static int numberOfFleetsToInstall = 2;
    public static int shortWait = 2;
    public static int mediumWait = 5;
    public static int longWait = 10;
    public static String valueTest = "Test";
    public static String reportDescription = "";
    public static int explicitWaitTime = 30;
    public static String YEAR_OF_MANUFACTURE = "2010";
    public static String DataTranferRequest_driverName = "cigniti1 First";
    public static String DataTranferRequest_Window_Title = "Data Transfer Request";
    public static String driverAlertEventName = "Driver Log Off";
    public static String driverEditAlertEventName = "Driver Log On";
    public static ArrayList<String> preTemplateList=new ArrayList<String>();
    public static ArrayList<String> postTemplateList=new ArrayList<String>();
    public static ArrayList<String> dvirTemplateList=new ArrayList<String>();
    
    public enum VehicleStatus {
        ALL_Vehicles("All Vehicles"),
        Active_Vehicles("Active Vehicles"),
        Inactive_Vehicles("Inactive Vehicles"),
        Active("Active"),
        Inactive("Inactive");
    	private String displayName;

        VehicleStatus(final String displayName) {
           this.displayName = displayName;
        }
        
        public String displayName() { return displayName; }
        @Override 
        public String toString() { return displayName; }
    }
    
    public enum FleetStatus {
        ALL_Fleets("All Fleets"),
        Active_Fleets("Active Fleets"),
        Inactive_Fleets("Inactive Fleets"),
        Active("Active"),
        Inactive("Inactive");

        private String displayName;

        FleetStatus(final String displayName) {
            this.displayName = displayName;
        }

        public String displayName() { return displayName; }
        
        @Override 
        public String toString() { return displayName; }
    }
    
    public enum DriverStatus {
        ALL_Drivers("All Drivers"),
        Active_Drivers("Active Drivers"),
        Inactive_Drivers("Inactive Drivers"),
        Active("Active"),
        Inactive("Inactive");
    	private String displayName;

    	DriverStatus(final String displayName) {
           this.displayName = displayName;
        }
        
        public String displayName() { return displayName; }
        @Override 
        public String toString() { return displayName; }
    }
    
    public static String mobileDriverID = "1112";
    public static String mobileVehicleName = "first_vehicle";
    public static String mobileMsgTo = "360 CENTER";
    public static String mobileMsg = "Test";
    
    public enum AttributeType {
        Flag("Flag"),
        Text("Text");
    	private String displayName;

    	AttributeType(final String displayName) {
           this.displayName = displayName;
        }
        
        public String displayName() { return displayName; }
        @Override 
        public String toString() { return displayName; }
    }
    public enum AttributeFlagValue {
        Flag_TRUE("true"),
        Flag_FALSE("false");
    	private String displayName;

    	AttributeFlagValue(final String displayName) {
           this.displayName = displayName;
        }
        
        public String displayName() { return displayName; }
        @Override 
        public String toString() { return displayName; }
    }
    
    public static String STATUS = "Driving";
    public static String DVIRUserMail = "";
    public static String DVIRUserName = "test";
    public static String DVIRUserPassword = "Test@123";
    public static String DVIRCurrentUser = "Cigniti_User";
    public static String DVIRCompanyName = "CoreHub ELD CloudQA Test Company";
    public static String DVIRUserDivision = "Cigniti Division";
    public static String DVIRGroupDvirAdmin = "DVIR Administrator";
    public static String DVIRGroupUserAdmin = "User Administrator";
    public static String DVIRMenubarItemForUserAdmin = "Admin";
    
    public static String DVIRGlobalAccount = "ANZ Test Company";
    public static String DVIRGlobalDivision = "ANZNewDivision";
    public static String DVIRGroupSystemAdmin = "System Administrator";
    public static String DVIRMenubarItemForSystemAdmin = "Admin,Compliance,Diagnostics"; 
    public static String DVIRGroupDriver = "Driver";
    public static String DVIRMenubarItemForUserDriver = "Admin,Compliance";  
    public static String DVIRMenubarItemForDvirAdmin = "Compliance";
    
    public static String checklistName = "test";
    public static String checklistItemName = "air";
    
    public static String Web_AddServicePlan = "service every 1000 distance KM";
    public static String Web_ServiceDistance = "1000";
    public static String Web_ServiceFrequencyType = "Distance";
    public static String Web_ServiceVehicleName = "1.6_Vehicle";
    
    public static String Web_SearchProduct = "Auto";
    
    public static String Web_RfidTag = "test";
    public static String Web_ProductType = "Test1";
    public static String Web_ProductName = "test";
}
