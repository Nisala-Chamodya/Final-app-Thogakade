package lk.blacky.pos.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import lk.blacky.pos.db.Database;
import lk.blacky.pos.model.Customer;
import lk.blacky.pos.model.Item;
import lk.blacky.pos.view.tm.CartTm;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PlaceOrderFormController {
    public TextField txtDate;
    public ComboBox <String>cmbCustomerids;
    public ComboBox<String> cmbItemCodes;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtSalary;
    public TextField txtQtyOnHand;
    public TextField txtUnitPrice;
    public TextField txtDescription;
    public TextField txtQty;
    public TableView <CartTm>tblCart;
    public TableColumn colCode;
    public TableColumn colDescription;
    public TableColumn colUnitPrice;
    public TableColumn colQty;
    public TableColumn colTotal;
    public TableColumn colOption;

    public void initialize(){

        setDateAndOrderId();
        loadAllCustomerId();
        loadAllItemCodes();

        cmbCustomerids.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue!=null){
                setCustomerDetails();
            }

        });


        cmbItemCodes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue!=null){
                setItemDetails();
            }

        });




    }

    private void setItemDetails() {
        for (Item i: Database.itemTable ) {
            if (i.getCode().equals(cmbItemCodes.getValue())){
                txtDescription.setText(i.getDescription());
                txtUnitPrice.setText(String.valueOf(i.getUnitPrice()));
                txtQtyOnHand.setText(String.valueOf(String.valueOf(i.getQtyOnHand())));

            }

        }

    }

    private void setCustomerDetails() {
        for (Customer c: Database.customerTable ) {
            if (c.getId().equals(cmbCustomerids.getValue())){
                txtName.setText(c.getName());
                txtAddress.setText(c.getAddress());
                txtSalary.setText(String.valueOf(c.getSalary()));

            }

        }
    }

    private void loadAllItemCodes() {
        for (Item i :Database.itemTable   ) {
            cmbItemCodes.getItems().add(i.getCode());

        }

    }

    private void loadAllCustomerId() {
        for (Customer c :Database.customerTable   ) {
          cmbCustomerids.getItems().add(c.getId());
            
        }

        
    }

    private void setDateAndOrderId() {
        /*  Date date=new Date();
        SimpleDateFormat df=new SimpleDateFormat("YYYY-MM-dd");
        String d = df.format(date);
        txtDate.setText(d);*/

        txtDate.setText(new SimpleDateFormat("YYYY-MM-dd").format(new Date()));
    }


    public void addToCartToOnAction(ActionEvent actionEvent) {

        double unitPrice=Double.parseDouble(txtUnitPrice.getText());
        int qty=Integer.parseInt(txtQty.getText());
        double total= unitPrice*qty;
        Button btn=new Button("Delete");

        CartTm tm=new CartTm(cmbItemCodes.getValue(),txtDescription.getText(),unitPrice,qty,total,btn);

        ObservableList<CartTm> obList= FXCollections.observableArrayList();
        obList.add(tm);
        tblCart.setItems(obList);



    }
}
