package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import models.BankingSystem;
import models.Customer;
import models.Transaction;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.io.IOException;

public class TransactionHistoryController {

    @FXML private TableView<Transaction> tblTransactions;
    @FXML private TableColumn<Transaction, String> colDate;
    @FXML private TableColumn<Transaction, String> colType;
    @FXML private TableColumn<Transaction, Double> colAmount;
    @FXML private TableColumn<Transaction, String> colAccount;

    private Customer customer;
    private BankingSystem bankingSystem;

    // ---------------------------
    // Receive customer & system 
    // ---------------------------
    public void setCustomer(Customer customer) {
        this.customer = customer;
        loadTransactions();
    }

    public void setBankingSystem(BankingSystem bankingSystem) {
        this.bankingSystem = bankingSystem;
    }

    // ---------------------------
    // Initialize columns
    // ---------------------------
    @FXML
    private void initialize() {

        colDate.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getDateTime()));

        colType.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getType()));

        colAmount.setCellValueFactory(data ->
                new SimpleDoubleProperty(data.getValue().getAmount()).asObject());

        colAccount.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getAccountNumber()));
    }

    // ---------------------------
    // Load transaction history
    // ---------------------------
    private void loadTransactions() {
        if (customer == null) return;

        ObservableList<Transaction> list =
                FXCollections.observableArrayList(customer.getTransactions());

        tblTransactions.setItems(list);
    }

    // ---------------------------
    // Back to Dashboard
    // ---------------------------
    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/Dashboard.fxml"));
            Parent root = loader.load();

            DashboardController controller = loader.getController();
            controller.setCustomer(customer);
            controller.setBankingSystem(bankingSystem);

            Stage stage = (Stage) tblTransactions.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Dashboard - Orange Bank of Botswana");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
