package lk.blacky.pos.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.blacky.pos.db.Database;
import lk.blacky.pos.model.Customer;
import lk.blacky.pos.model.Item;
import lk.blacky.pos.view.tm.CustomerTm;
import lk.blacky.pos.view.tm.ItemTm;

import java.util.Optional;

public class ItemFormController {
    public AnchorPane itemFormContex;
    public TextField txtCode;
    public TextField txtDescription;
    public TextField txtUnitPrice;
    public TextField txtQtyOnHand;
    public JFXButton btnSaveItem;
    public TextField txtSearch;
    public TableView<ItemTm> tblItem;
    public TableColumn colCode;
    public TableColumn colDescription;
    public TableColumn colUnitPrice;
    public TableColumn colQtyOnHand;
    public TableColumn colOptions;
    private String searchText="";

    public void  initialize(){
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colOptions.setCellValueFactory(new PropertyValueFactory<>("btn"));


        searchItems(searchText);

        tblItem.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue!=null){

                setData(newValue);
            }


        });

        txtSearch.textProperty()
                .addListener((observable, oldValue, newValue) -> {
                    searchText=newValue;
                    searchItems(searchText);
                });



    }

    private void setData(ItemTm tm){

        txtCode.setText(tm.getCode());
        txtDescription.setText(tm.getDescription());
        txtUnitPrice.setText(String.valueOf(tm.getUnitPrice()));
        txtQtyOnHand.setText(String.valueOf(tm.getQtyOnHand()));
        btnSaveItem.setText("Update Item");

    }

    public void backToHomeOnAction(ActionEvent actionEvent) {
    }

    public void newItemOnAction(ActionEvent actionEvent) {
    }

    public void saveItemOnAction(ActionEvent actionEvent) {
        Item i1=new Item(txtCode.getText(),txtDescription.getText(),Double.parseDouble(txtUnitPrice.getText()),Integer.parseInt(txtQtyOnHand.getText()));
        //  Database.customerTable.add(c1);
        if (btnSaveItem.getText().equalsIgnoreCase("Save Item")){
            boolean isSaved= Database.itemTable.add(i1);
            if (isSaved){
                searchItems(searchText);
                clearFields();
                new Alert(Alert.AlertType.INFORMATION,"Item Saved!!").show();
            }else {
                new Alert(Alert.AlertType.WARNING,"Try Again !!!").show();
            }
        }else {
            for (int i=0;i<Database.itemTable.size();i++){
                if (txtCode.getText().equalsIgnoreCase(Database.itemTable.get(i).getCode())){
                    Database.itemTable.get(i).setDescription(txtDescription.getText());
                    Database.itemTable.get(i).setUnitPrice(Double.parseDouble(txtUnitPrice.getText()));
                    Database.itemTable.get(i).setQtyOnHand(Integer.parseInt(txtQtyOnHand.getText()));
                    searchItems(searchText);
                    new Alert(Alert.AlertType.INFORMATION,"Item Updated!!").show();
                    clearFields();
                }
            }

        }






    }

    private void clearFields() {
        txtCode.clear();
        txtDescription.clear();
        txtUnitPrice.clear();
        txtQtyOnHand.clear();

    }

    private void searchItems(String text){
        ObservableList<ItemTm> tmList= FXCollections.observableArrayList();
        for (Item i:Database.itemTable) {
            if (i.getDescription().contains(text) ) {


                Button btn = new Button("Delete");
                ItemTm tm = new ItemTm(i.getCode(),i.getDescription(),i.getUnitPrice(),i.getQtyOnHand(), btn);
                tmList.add(tm);


                btn.setOnAction(event -> {

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you Shuar delete this Item ? ",
                            ButtonType.YES, ButtonType.NO);

                    Optional<ButtonType> buttonType = alert.showAndWait();
                    if (buttonType.get() == ButtonType.YES) {
                        boolean isDeleted = Database.itemTable.remove(i);


                    if (isDeleted) {
                        searchItems(searchText);
                        new Alert(Alert.AlertType.INFORMATION, "Item Deleted!!").show();

                    } else {
                        new Alert(Alert.AlertType.WARNING, "Try Again !!!").show();
                    }
                    }

                });


            }
        }
        tblItem.setItems(tmList );

    }
}
