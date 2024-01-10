package lk.ijse.coir.bo;

import lk.ijse.coir.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){

    }
    public static BOFactory getBoFactory(){
        return (boFactory==null)?boFactory=
                new BOFactory():boFactory;

    }
    public enum BOTypes{
        CUSTOMER,DELIVERY,ITEM,EMPLOYEE,LOGIN,MATERIAL_STOCK,PLACE_ORDER,RAW_MATERIAL,SUPPLIER
    }
    public SuperBO getBO(BOTypes boTypes){
        switch (boTypes){
            case CUSTOMER:
                return new CustomerBOImpl();
            case DELIVERY:
                return new DeliveryBOImpl();
            case ITEM:
                return new ItemBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case LOGIN:
                return new LoginBOImpl();
            case MATERIAL_STOCK:
                return new MaterialStockBOImpl();
            case PLACE_ORDER:
                return new PlaceOrderBOImpl();
            case RAW_MATERIAL:
                return new RawMaterialBOImpl();
            case SUPPLIER:
                return new SupplierBOImpl();
            default:
                return null;
        }
    }
}
