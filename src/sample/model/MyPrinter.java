package sample.model;

import javafx.print.*;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MyPrinter {

    public void Print(Node node){
        Printer printer = Printer.getDefaultPrinter();

        PageLayout pageLayout = printer.createPageLayout(
                Paper.A2, PageOrientation.PORTRAIT, Printer.MarginType.EQUAL_OPPOSITES);
        Stage dialogStage = new Stage(StageStyle.DECORATED);
        PrinterJob job = PrinterJob.createPrinterJob(printer);
        if(printer!=null || job!=null) {
            System.out.println(printer.getName());
            System.out.println(job.jobStatusProperty().asString());
            boolean printed=job.printPage(pageLayout,node);
            boolean showDialog = job.showPageSetupDialog(dialogStage);
            if (showDialog) {
                node.setScaleX(0.60);
                node.setScaleY(0.60);
                node.setTranslateX(-220);
                node.setTranslateY(-70);
                boolean success = job.printPage(node);
                if (success) {
                    job.endJob();
                }
                node.setTranslateX(0);
                node.setTranslateY(0);
                node.setScaleX(1.0);
                node.setScaleY(1.0);
            }
        }
        else{
            System.out.println("failed print");
        }


    }
}
