package nutricionalappfx.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nutricionalappfx.NutricionalAppFX;
import nutricionalappfx.dominio.AutenticacionDM;
import nutricionalappfx.dominio.pojo.Autenticacion;
import nutricionalappfx.dominio.pojo.Colaborador;
import nutricionalappfx.modelo.dao.AutenticacionDAO;
import nutricionalappfx.utilidades.Utilidades;

public class FXMLInicioSesionController implements Initializable {

    @FXML
    private VBox vbCredenciales;
    @FXML
    private TextField txtNoPersonal;
    @FXML
    private Label lbErrorUsername;
    @FXML
    private PasswordField textContrasenia;
    @FXML
    private Label lbErrorContrasenia;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void handleBtnIngresar(ActionEvent event) {
        String noPersonal = txtNoPersonal.getText().trim();
        String password = textContrasenia.getText();
        if (validarCampos()) {
            validarCredenciales(noPersonal, password);
        }
    }

    private void validarCredenciales(String noPersonal, String password) {
        AutenticacionDM autenticacionDM = new AutenticacionDM(new AutenticacionDAO());

        try {
            Autenticacion respuesta = autenticacionDM.verificarCredencialesColaborador(noPersonal, password);
            if (respuesta.isCredencialesCorrectas() && respuesta.getActivo()) {
                Utilidades.mostrarAlertaSimple("Credenciales Correctas", respuesta.getMensaje(), Alert.AlertType.INFORMATION);
                irPantallaPrincipal(respuesta.getColaborador());
            } else {
                Utilidades.mostrarAlertaSimple("Lo sentimos", respuesta.getMensaje(), Alert.AlertType.WARNING);
            }
        } catch (RuntimeException e) {
            Utilidades.mostrarAlertaSimple("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private boolean validarCampos() {
        boolean esValido = true;
        lbErrorUsername.setText("");
        lbErrorUsername.setVisible(false);
        lbErrorContrasenia.setText("");
        lbErrorContrasenia.setVisible(false);

        if (txtNoPersonal.getText().trim().isEmpty()) {
            lbErrorUsername.setText("El número de personal es obligatorio");
            lbErrorUsername.setVisible(true);
            esValido = false;
        }
        if (textContrasenia.getText().isEmpty()) {
            lbErrorContrasenia.setText("La contraseña es obligatoria");
            lbErrorContrasenia.setVisible(true);
            esValido = false;
        }
        return esValido;
    }
    
    private void irPantallaPrincipal(Colaborador colaborador){
        try {
            Stage escenario = (Stage) txtNoPersonal.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(NutricionalAppFX.class.getResource("vista/FXMLPrincipal.fxml"));
            Parent vista = loader.load();
            FXMLPrincipalController controlador = loader.getController();
            controlador.inicializarInformacion(colaborador);
            Scene escena = new Scene(vista);
            escenario.setScene(escena);
            escenario.setTitle("Control Nutricional");
            escenario.show();
        } catch (IOException ex){
            ex.printStackTrace();
        }
        
        
        
    }
}
