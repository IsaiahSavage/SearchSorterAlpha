module com.searchsorteralpha {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jsoup;
    requires java.desktop;

    opens com.searchsorteralpha to javafx.fxml;
    exports com.searchsorteralpha;

}
