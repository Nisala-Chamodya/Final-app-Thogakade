package lk.blacky.pos.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.blacky.pos.db.Database;
import lk.blacky.pos.model.Order;
import lk.blacky.pos.view.tm.OrderTm;

import java.io.IOException;

public class OrderDetailFormContoller {
    public AnchorPane orderDetailsContext;
    public TableView<OrderTm > tblOrders;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colDate;
    public TableColumn colTotal;
    public TableColumn colOption;

    public void initialize(){
        colId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn "));

        loardOrders();
    }

    private void loardOrders() {
        ObservableList<OrderTm>tmList= FXCollections.observableArrayList();
        for (Order o: Database.orderTable  ) {
            Button btn=new Button("View More");
            OrderTm tm=new OrderTm(o.getOrderId(),o.getCustomer(),o.getDate(),o.getTotalCost(),btn);
            tmList.add(tm);

            btn.setOnAction(e->{

                try {

                    FXMLLoader loader=new FXMLLoader(getClass().getResource("../view/ItemDetailsForm.fxml"));
                    Parent parent = loader.load();
                    ItemDetailsFormController controller=  loader.getController();
                  controller.loardOrderDetails(tm.getOrderId());
                  Stage stage=new Stage();
                  stage.setScene(new Scene(parent));
                  stage.show();




                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });




        }
        tblOrders.setItems(tmList);
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage=(Stage) orderDetailsContext.getScene().getWindow();
        stage.setScene(new Scene(
                FXMLLoader.load(getClass().getResource("../view/DashboardForm.fxml"))
        ));


    }
}
