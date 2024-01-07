package lk.ijse.coir.dao;

import lk.ijse.coir.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory(){
    }
    public static DAOFactory getDaoFactory(){
        return (daoFactory==null)?daoFactory
                =new DAOFactory():daoFactory;
    }

    public enum DAOTypes{
        CUSTOMER,RAWMATERIAL,DELIVERY,Employee,ITEM,ORDER,ORDER_DETAIL,QUERY
    }
    public SuperDAO getDAO(DAOTypes daoTypes){
        switch (daoTypes){
            case CUSTOMER:
                return new CustomerDAOImpl();
            case RAWMATERIAL:
                 return new RawMaterialDAOImpl();
            case DELIVERY:
                return new DeliveryDAOImpl();
            case Employee:
                return new EmployeeDAOImpl();

            //case ITEM:
              //  return new ItemDAOImpl();
            //case ORDER:
              //  return new OrderDAOImpl();
            //case ORDER_DETAIL:
              //  return new OrderDetailsDAOImpl();
            //case QUERY:
              //  return new QuaryDAOImpl();
            default:
                return null;
        }
    }

}
