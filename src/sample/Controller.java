package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.Group;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import modèles.Matrice;

import javax.imageio.ImageIO;
import java.io.File;

public class Controller {

    private Matrice matriceA = new Matrice();
    private Matrice matriceB = new Matrice();
    private Matrice matriceC = new Matrice();
    private Matrice[] matrice = new Matrice[3];

    @FXML
    private TextField multiplicationB, multiplicationA, ia, ja, ib, jb;

    @FXML
    GridPane aMatrice, bMatrice, cMatrice;

    public void CréeMatrice() {
        try{
            if ((Integer.parseInt(ia.getText()) <= 15) && (Integer.parseInt(ib.getText()) <= 15) && (Integer.parseInt(ja.getText()) <= 15) && (Integer.parseInt(jb.getText()) <= 15)) {
                aMatrice.getChildren().clear();
                bMatrice.getChildren().clear();
                for (int i = 0; i < Integer.parseInt(ia.getText()); i++) {
                    for (int j = 0; j < Integer.parseInt(ja.getText()); j++) {
                        TextField textfield = new TextField();
                        aMatrice.add(textfield, i, j);
                        textfield.setText("0");
                        textfield.setId("a" + i + j);
                        textfield.setMaxWidth(40);
                    }
                }
                for (int i = 0; i < Integer.parseInt(ib.getText()); i++) {
                    for (int j = 0; j < Integer.parseInt(jb.getText()); j++) {
                        TextField textfield = new TextField();
                        bMatrice.add(textfield, i, j);
                        textfield.setText("0");
                        textfield.setId("b" + i + j);
                        textfield.setMaxWidth(40);

                    }
                }
            } else {
                System.out.println("vos matrice ne doivent pas dépasser 15x15");
                Alert alerte = new Alert(Alert.AlertType.INFORMATION);
                alerte.setTitle("Bris de Matrice");
                alerte.setHeaderText("Prendre note...");
                alerte.setContentText(
                        "Vos matrice ne doivent pas dépasser 15x15");
                alerte.showAndWait();
            }
        }catch (Exception ex){
            Alert alerte = new Alert(Alert.AlertType.INFORMATION);
            alerte.setTitle("Bris de Matrice");
            alerte.setHeaderText("Prendre note...");
            alerte.setContentText(
                    "Vos élement de matrice rencontre un problème:" +
                            "\n      -> Écrire 0.5 au lieu de 1/2" +
                            "\n      -> Vérifier qu'il n'y as pas de lettre ou caractère speciaux " +
                            "\n      -> Vérifier que tous les champs sont remplis");
            alerte.showAndWait();
        }

    }

    public void Imprimer() {
        WritableImage image = cMatrice.snapshot(new SnapshotParameters(), null);
        ImageView result = new ImageView();
        File fileA = new File("chart.png");
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", fileA);

        } catch (Exception s) {
            System.out.println("image nn créé");
        }
        java.awt.Image image2[] = {null};
        result = new ImageView(new Image("file:chart.png"));
        try {
            image2[0] = ImageIO.read(fileA);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        cMatrice.getChildren().clear();
        cMatrice.add(result, 0, 0);
        // Basé sur : https://stackoverflow.com/questions/20676858/javafx-convert-writableimage-to-image
        try {
            PrinterJob job = PrinterJob.createPrinterJob();
            if (job != null && job.showPrintDialog(result.getScene().getWindow())) {
                boolean success = job.printPage(result);
                if (success) {
                    job.endJob();
                }
            }
        } catch (Exception ex) {
            System.out.println("print failled");
            Alert alerte = new Alert(Alert.AlertType.INFORMATION);
            alerte.setTitle("Bris de Matrice");
            alerte.setHeaderText("Prendre note...");
            alerte.setContentText(
                    "Impression de la matrice impossible");
            alerte.showAndWait();
        }

    }

    public Boolean assigner() { // un nombre indéfini de matrice...
        Boolean done = false;
        try {
            matriceA.Matrice(Integer.parseInt(ia.getText()), Integer.parseInt(ja.getText()));
            matriceB.Matrice(Integer.parseInt(ib.getText()), Integer.parseInt(jb.getText()));
            for (int i = 0; i < Integer.parseInt(ia.getText()); i++) {
                for (int j = 0; j < Integer.parseInt(ja.getText()); j++) {
                    matriceA.getMatrice()[i][j] = Double.parseDouble(((TextField) Main.getCurrentScene().lookup("#a" + i + j)).getText());
                }
            }
            for (int i = 0; i < Integer.parseInt(ib.getText()); i++) {
                for (int j = 0; j < Integer.parseInt(jb.getText()); j++) {
                    matriceB.getMatrice()[i][j] = Double.parseDouble(((TextField) Main.getCurrentScene().lookup("#b" + i + j)).getText());
                }
            }
            done = true;
        } catch (NullPointerException ex) {
            System.out.println("Vérifier que vous avez bien créé la matrice");
            Alert alerte = new Alert(Alert.AlertType.INFORMATION);
            alerte.setTitle("Bris de Matrice");
            alerte.setHeaderText("Prendre note...");
            alerte.setContentText(
                    "Erreur lors de l'asignement des matrice." +
                            "\n Assurez-vous que vous avez créé votre matrice après avoir changé le format");
            alerte.showAndWait();
            done = false;
        } catch (Exception ex) {
            System.out.println("vous devez entrer des élement valide dans votre matrice(uniquement des chiffre et nombre)" +
                    "\n astuce: 1/2 -> 0.5");
            Alert alerte = new Alert(Alert.AlertType.INFORMATION);
            alerte.setTitle("Bris de Matrice");
            alerte.setHeaderText("Prendre note...");
            alerte.setContentText(
                    "Vos élement de matrice rencontre un problème:" +
                            "\n      -> Écrire 0.5 au lieu de 1/2" +
                            "\n      -> Vérifier qu'il n'y as pas de lettre ou caractère speciaux " +
                            "\n      -> Vérifier que tous les champs sont remplis");
            alerte.showAndWait();
        }
        return done;
    }

    public void Addition(/* int a, int b*/) {
        if (assigner()) {
            if (Integer.parseInt(ia.getText()) == Integer.parseInt(ib.getText())) {
                if (Integer.parseInt(ja.getText()) == Integer.parseInt(jb.getText())) {
                    matriceC.Matrice(Integer.parseInt(ia.getText()), Integer.parseInt(ja.getText()));
            /*matrice[2].Matrice(Integer.parseInt(ia.getText()),Integer.parseInt(ja.getText()));*/
                    for (int i = 0; i < Integer.parseInt(ia.getText()); i++) {
            /*        for(int i=0;i<Integer.parseInt(   (  (TextField)Main.getCurrentScene().lookup("#"+iMatrice)).getText() )  .getText());i++)*/
                        for (int j = 0; j < Integer.parseInt(ja.getText()); j++) {
                            matriceC.getMatrice()[i][j] = matriceA.getMatrice()[i][j] + matriceB.getMatrice()[i][j];
                        }
                    }
                    AfficherC(Integer.parseInt(ia.getText()), Integer.parseInt(ja.getText()));
                } else {
                    System.out.println("le format des matrices doit être la même pour l'adition");
                    Alert alerte = new Alert(Alert.AlertType.INFORMATION);
                    alerte.setTitle("Bris de Matrice");
                    alerte.setHeaderText("Prendre note...");
                    alerte.setContentText(
                            "Vos matrice A et B doivent être du même format pour l'addition");
                    alerte.showAndWait();
                }
            } else {
                System.out.println("le format des matrices doit etre la même pour l'adition");
                Alert alerte = new Alert(Alert.AlertType.INFORMATION);
                alerte.setTitle("Bris de Matrice");
                alerte.setHeaderText("Prendre note...");
                alerte.setContentText(
                        "Vos matrice A et B doivent être du même format pour l'addition");
                alerte.showAndWait();
            }
        }
    }

    public void Soustraction() {
        if (assigner()) {
            if (Integer.parseInt(ia.getText()) == Integer.parseInt(ib.getText()) && Integer.parseInt(ja.getText()) == Integer.parseInt(jb.getText())) {
                matriceC.Matrice(Integer.parseInt(ia.getText()), Integer.parseInt(ja.getText()));
                for (int i = 0; i < Integer.parseInt(ia.getText()); i++) {
                    for (int j = 0; j < Integer.parseInt(ja.getText()); j++) {
                        matriceC.getMatrice()[i][j] = matriceA.getMatrice()[i][j] - matriceB.getMatrice()[i][j];
                    }
                }
                AfficherC(Integer.parseInt(ia.getText()), Integer.parseInt(ja.getText()));
            } else {
                System.out.println("le format des matrices doit etre la même pour la soustraction");
                Alert alerte = new Alert(Alert.AlertType.INFORMATION);
                alerte.setTitle("Bris de Matrice");
                alerte.setHeaderText("Prendre note...");
                alerte.setContentText(
                        "Vos matrice A et B doivent être du même format pour la soustraction");
                alerte.showAndWait();
            }
        }

    }

    public void PuissanceA() {
        if (assigner()) {
            double reponse = 0;
            matriceC.Matrice(Integer.parseInt(ia.getText()), Integer.parseInt(ja.getText()));
            for (int i = 0; i < Integer.parseInt(ia.getText()); i++) {
                for (int j = 0; j < Integer.parseInt(ja.getText()); j++) {
                    for (int k = 0; k < Integer.parseInt(ja.getText()); k++) {
                        reponse = reponse + Double.parseDouble(String.valueOf(matriceA.getMatrice()[i][k]));
                    }
                    matriceC.getMatrice()[i][j] = reponse;
                }
            }
            AfficherC(Integer.parseInt(ia.getText()), Integer.parseInt(ja.getText()));
        }
    }

    public void MultiplicationA() {
        if (assigner()) {
            matriceC.Matrice(Integer.parseInt(ia.getText()), Integer.parseInt(ja.getText()));
            for (int i = 0; i < Integer.parseInt(ia.getText()); i++) {
                for (int j = 0; j < Integer.parseInt(ja.getText()); j++) {
                    matriceC.getMatrice()[i][j] = matriceA.getMatrice()[i][j] * Double.parseDouble(multiplicationA.getText());
                }
            }
            AfficherC(Integer.parseInt(ia.getText()), Integer.parseInt(ja.getText()));
        }
    }

    public void Transposition() {
        if (assigner()) {
            matriceC.Matrice(Integer.parseInt(ja.getText()), Integer.parseInt(ia.getText()));
            for (int i = 0; i < Integer.parseInt(ja.getText()); i++) {
                for (int j = 0; j < Integer.parseInt(ia.getText()); j++) {
                    matriceC.getMatrice()[i][j] = matriceA.getMatrice()[j][i];
                }
            }
            AfficherC(Integer.parseInt(ja.getText()), Integer.parseInt(ia.getText()));
        }

    }

    public void ProduitMatriciel() {
        if (assigner()) {
            if (Integer.parseInt(ja.getText()) == Integer.parseInt(ib.getText())) {
                matriceC.Matrice(Integer.parseInt(ia.getText()), Integer.parseInt(jb
                        .getText()));
                double reponse = 0;
                for (int i = 0; i < Integer.parseInt(ia.getText()); i++) {
                    for (int j = 0; j < Integer.parseInt(jb.getText()); j++) {
                        for (int k = 0; k < Integer.parseInt(ja.getText()); k++) {
                            reponse = reponse + Double.parseDouble(String.valueOf(matriceA.getMatrice()[i][k] * matriceB.getMatrice()[k][j]));
                        }
                        matriceC.getMatrice()[i][j] = reponse;
                    }

                }
                AfficherC(Integer.parseInt(ia.getText()), Integer.parseInt(jb.getText()));
            } else {
                System.out.println("le format des matrices doit etre conforme à la multiplication");
                Alert alerte = new Alert(Alert.AlertType.INFORMATION);
                alerte.setTitle("Bris de Matrice");
                alerte.setHeaderText("Prendre note...");
                alerte.setContentText(
                        "Vos matrice A et B doivent etre conforme au format de la multiplication" +
                                "\n   ex: A(nxm) et B(mxp)");
                alerte.showAndWait();
            }
        }

    }

    public void ProduitVectoriel() {
        if (assigner()) {
                if (Integer.parseInt(ia.getText()) >= 3 && Integer.parseInt(ib.getText()) >= 3) {
                    matriceC.getMatrice()[0][0] = matriceA.getMatrice()[1][0] * matriceB.getMatrice()[2][0] - matriceA.getMatrice()[2][0] * matriceB.getMatrice()[1][0];
                    matriceC.getMatrice()[1][0] = -1 * (matriceA.getMatrice()[0][0] * matriceB.getMatrice()[2][0] - matriceA.getMatrice()[2][0] * matriceB.getMatrice()[0][0]);
                    matriceC.getMatrice()[2][0] = matriceA.getMatrice()[0][0] * matriceB.getMatrice()[1][0] - matriceA.getMatrice()[1][0] * matriceB.getMatrice()[0][0];
                }
                Double.parseDouble((String.valueOf(matriceA.getMatrice()[0][0] * matriceB.getMatrice()[1][0] - matriceA.getMatrice()[1][0] * matriceB.getMatrice()[0][0])));
                AfficherC(Integer.parseInt(ja.getText()), Integer.parseInt(ia.getText()));
            }
        }


    public void ProduitHadamard() {
        if (assigner()) {
            if (Integer.parseInt(ia.getText()) == Integer.parseInt(ib.getText()) && Integer.parseInt(ja.getText()) == Integer.parseInt(jb.getText())) {
                for (int i = 0; i < Integer.parseInt(ia.getText()); i++) {
                    for (int j = 0; j < Integer.parseInt(ja.getText()); j++) {
                        matriceC.getMatrice()[i][j] = matriceA.getMatrice()[i][j] * matriceB.getMatrice()[i][j];
                    }
                }
                AfficherC(Integer.parseInt(ia.getText()), Integer.parseInt(ja.getText()));
            } else {
                System.out.println("le format des matrices doit etre la même pour le produit d'Hadamard");
                Alert alerte = new Alert(Alert.AlertType.INFORMATION);
                alerte.setTitle("Bris de Matrice");
                alerte.setHeaderText("Prendre note...");
                alerte.setContentText(
                        "Vos matrice A et B doivent etre du même format pour le profuit d'Hadamard");
                alerte.showAndWait();
            }
        }

    }

    public void ProduitTensoriel() {
        if (assigner()) {

        }
    }

    public void AfficherC(int ic, int jc) {
        cMatrice.getChildren().clear();
        for (int i = 0; i < ic; i++) {
            for (int j = 0; j < jc; j++) {
                Label label = new Label();
                cMatrice.add(label, i, j);
                label.setText(Double.toString(matriceC.getMatrice()[i][j]));
                cMatrice.setHgap(8);
            }
        }
    }
}
