package lk.blacky.pos.db;

import lk.blacky.pos.model.Customer;
import lk.blacky.pos.model.Item;

import java.util.ArrayList;

public class Database {
   public static ArrayList<Customer> customerTable=new ArrayList<Customer>();
   public static ArrayList<Item> itemTable=new ArrayList<Item>();

   static {
      customerTable.add(new Customer("1","Nisala","Kaluthara",52000));
      customerTable.add(new Customer("2","Chamodya","Bandaragama",65223));
      customerTable.add(new Customer("3","Pasan","Panadura",85525));
      customerTable.add(new Customer("4","Sandeepa","Waadduwa",65236));
      customerTable.add(new Customer("5","Tharani","Angoda",100));




      itemTable.add(new Item("I-001","Keeri samba",25,20));
      itemTable.add(new Item("I-002","Sudu kekulu",50,30));
      itemTable.add(new Item("I-003","Naadu",65,40));
      itemTable.add(new Item("I-004","Daaal",245,50));
      itemTable.add(new Item("I-005","Biscuit",100,75));


   }

}
