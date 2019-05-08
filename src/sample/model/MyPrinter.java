package sample.model;

import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.control.Alert;

public class MyPrinter {

    public void Print(Node node){
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);


        Printer printer = Printer.getDefaultPrinter(); //gets the default printer.

        PageLayout pageLayout = printer.createPageLayout(
                Paper.A3, PageOrientation.PORTRAIT, 20,20,15,12);  //setting pagelayout

        PrinterJob job = PrinterJob.createPrinterJob(printer); //create a job for a specified printer

        if((printer!=null || job!=null) && job.showPrintDialog(node.getScene().getWindow())) {

            System.out.println(printer.getName());
            System.out.println(job.jobStatusProperty().asString());

                boolean success = job.printPage(pageLayout,node); // Prints the specified node eg. anchorPane
                if (success) {
                    job.endJob();
                    alert.setContentText("Successfully sent to Printer");
                    alert.showAndWait();
                }else{
                    alert.setContentText("Print Failed");
                    alert.showAndWait();
                }
            }
        else{
            System.out.println("failed print");
        }


    }
}
