module com.example.tienda_mejorada {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.tienda_mejorada to javafx.fxml;
    exports com.example.tienda_mejorada;
}