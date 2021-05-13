/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package triplependulumsimulation;

import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.animation.AnimationTimer;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

/**
 *
 * @author nick
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Line arm1;
    @FXML
    private Line arm2;
    @FXML
    private Button btnStart;
    @FXML
    private Button btnStop;
    @FXML
    private Circle bob1;
    @FXML
    private Circle bob2;
    @FXML
    private Circle origin;
    @FXML
    private TextField txtA1;
    @FXML
    private TextField txtA2;
    @FXML
    private TextField txtM1;
    @FXML
    private TextField txtM2;
    @FXML
    private TextField txtArm1;
    @FXML
    private TextField txtArm2;
    @FXML
    private TextField txtGrav;
    @FXML
    private Label lblA1;
    @FXML
    private Label lblA2;
    @FXML
    private Label lblM1;
    @FXML
    private Label lblM2;
    @FXML
    private Label lblR1;
    @FXML
    private Label lblR2;
    @FXML
    private Label lblGrav;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private CheckBox checkTrace;
    @FXML
    private ComboBox<String> cmbDiffSolver;
    
    public double r1 = 100;
    public double r2 = 100;
    public double m1 = 10;
    public double m2 = 10;
    public double a1 = 0;
    public double a2 = 0;
    public double a1_v = 0;
    public double a2_v = 0;
    public double a1_a = 0;
    public double a2_a = 0;
    public double x0 = 300;
    public double y0 = 175;
    public double x1 = 0;
    public double x2 = 0;
    public double y1 = 0;
    public double y2 = 0;
    public double x2prev = -1;
    public double y2prev = -1;
    public double g = 1;
    public double a1i = 0;
    public double a2i = 0;
    public double dt = 1;
    public String solver = "";
    public DecimalFormat dec = new DecimalFormat("0.0");
    
    private void draw() {
        arm1.setStartX(x0);
        arm1.setStartY(y0);
        arm1.setEndX(x1);
        arm1.setEndY(y1);
        arm2.setStartX(x1);
        arm2.setStartY(y1);
        arm2.setEndX(x2);
        arm2.setEndY(y2);
        bob1.setCenterX(x1);
        bob1.setCenterY(y1);
        bob2.setCenterX(x2);
        bob2.setCenterY(y2);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        x1 = r1 * sin(a1) + x0;
        x2 = r2 * sin(a2) + x1;
        y1 = r1 * cos(a1) + y0;
        y2 = r2 * cos(a2) + y1;
        bob1.setRadius(m1);
        bob2.setRadius(m2);
        origin.setCenterX(x0);
        origin.setCenterY(y0);
        lblA1.setText(dec.format(a1/Math.PI) + " pi radians");
        lblA2.setText(dec.format(a2/Math.PI) + " pi radians");
        lblM1.setText("" + m1);
        lblM2.setText("" + m2);
        lblR1.setText("" + r1);
        lblR2.setText("" + r2);
        lblGrav.setText("" + g);
        draw();
        cmbDiffSolver.getItems().add("Euler's Method");
        cmbDiffSolver.getItems().add("Runge-Kutte");
        
        
    }    

    @FXML
    private void handleA1(ActionEvent event) {
        a1 = Double.parseDouble(txtA1.getText()) * Math.PI;
        a1i = a1;
        x1 = r1 * sin(a1) + x0;
        x2 = r2 * sin(a2) + x1;
        y1 = r1 * cos(a1) + y0;
        y2 = r2 * cos(a2) + y1;
        lblA1.setText(dec.format(a1/Math.PI) + " pi radians");
        txtA1.clear();
        draw();
    }

    @FXML
    private void handleA2(ActionEvent event) {
        a2 = Double.parseDouble(txtA2.getText()) * Math.PI;
        a2i = a2;
        x1 = r1 * sin(a1) + x0;
        x2 = r2 * sin(a2) + x1;
        y1 = r1 * cos(a1) + y0;
        y2 = r2 * cos(a2) + y1;
        lblA2.setText(dec.format(a2/Math.PI) + " pi radians");
        txtA2.clear();
        draw();
    }

    @FXML
    private void HandleM1(ActionEvent event) {
        m1 = Double.parseDouble(txtM1.getText());
        bob1.setRadius(m1);
        lblM1.setText(txtM1.getText());
        txtM1.clear();
    }

    @FXML
    private void handleM2(ActionEvent event) {
        m2 = Double.parseDouble(txtM2.getText());
        bob2.setRadius(m2);
        lblM2.setText(txtM2.getText());
        txtM2.clear();
    }

    @FXML
    private void handleArm1(ActionEvent event) {
        r1 = Double.parseDouble(txtArm1.getText());
        x1 = r1 * sin(a1) + x0;
        x2 = r2 * sin(a2) + x1;
        y1 = r1 * cos(a1) + y0;
        y2 = r2 * cos(a2) + y1;
        lblR1.setText(txtArm1.getText());
        txtArm1.clear();
        draw();
    }

    @FXML
    private void handleArm2(ActionEvent event) {
        r2 = Double.parseDouble(txtArm2.getText());
        x1 = r1 * sin(a1) + x0;
        x2 = r2 * sin(a2) + x1;
        y1 = r1 * cos(a1) + y0;
        y2 = r2 * cos(a2) + y1;
        lblR2.setText(txtArm2.getText());
        txtArm2.clear();
        draw();
    }
    
    
    @FXML
    private void handleGrav(ActionEvent event) {
        g = Double.parseDouble(txtGrav.getText());
        lblGrav.setText(txtGrav.getText());
        txtGrav.clear();
    }
    private double calcPendulum1(double a1, double a1_v, double a2, double a2_v) {
        double result = 0;
        double num1 = -g*(2*m1+m2)*sin(a1);
        double num2 = -m2*g*sin(a1-2*a2);
        double num3 = -2*sin(a1-a2)*m2*(pow(a2_v, 2)*r2 + pow(a1_v, 2)*r1*cos(a1-a2));
        double denom1 = r1*(2*m1 + m2 - m2*cos(2*a1 - 2*a2));
        
        result = (num1 + num2 + num3)/denom1;
        return result;
    }
    
    private double calcPendulum2(double a1, double a1_v, double a2, double a2_v) {
        double result = 0;
        double num4 = 2*sin(a1-a2);
        double num5 = pow(a1_v, 2)*r1*(m1+m2);
        double num6 = g*(m1+m2)*cos(a1);
        double num7 = pow(a2_v, 2)*r1*m2*cos(a1-a2);
        double denom2 = r2*(2*m1+m2-m2*cos(2*a1-2*a2));
        
        result = num4*(num5+num6+num7)/denom2;
        return result;
    }
    private class PendulumTimer extends AnimationTimer {

        @Override
        public void handle(long now) {
            doHandle();
        }

        private void doHandle() {
            
            
            
            switch(solver) {
                case "Euler's Method" :
                    a1_a = calcPendulum1(a1, a1_v, a2, a2_v);
                    a2_a = calcPendulum2(a1, a1_v, a2, a2_v);

                    a1_v += a1_a*dt;
                    a2_v += a2_a*dt;
                    a1 += a1_v*dt;
                    a2 += a2_v*dt;
                    break;
                    
                case "Runge-Kutte" :
                    double ka1_1;
                    double ka1_2;
                    double ka1_3;
                    double ka1_4;
                    double ka1_v_1;
                    double ka1_v_2;
                    double ka1_v_3;
                    double ka1_v_4;
                    double ka2_1;
                    double ka2_2;
                    double ka2_3;
                    double ka2_4;
                    double ka2_v_1;
                    double ka2_v_2;
                    double ka2_v_3;
                    double ka2_v_4;
                    
                    ka1_1 = a1_v;
                    ka2_1 = a2_v;
                    
                    ka1_v_1 = calcPendulum1(a1, a1_v, a2, a2_v);
                    ka2_v_1 = calcPendulum2(a1, a1_v, a2, a2_v);
                    
                    ka1_2 = a1_v + ka1_v_1 / 2;
                    ka2_2 = a2_v + ka2_v_1 / 2;
                    
                    ka1_v_2 = calcPendulum1(a1 + dt*ka1_1/2, a1_v + dt*ka1_v_1/2, a2 + dt*ka2_1/2, a2_v + dt*ka2_v_1/2);
                    ka2_v_2 = calcPendulum2(a1 + dt*ka1_1/2, a1_v + dt*ka1_v_1/2, a2 + dt*ka2_1/2, a2_v + dt*ka2_v_1/2);
                    
                    ka1_3 = a1_v + ka1_v_2/2;
                    ka2_3 = a2_v + ka2_v_2/2;
                    
                    ka1_v_3 = calcPendulum1(a1 + dt*ka1_2/2, a1_v + dt*ka1_v_1/2, a2 + dt*ka2_2/2, a2_v + dt*ka2_v_1/2);
                    ka2_v_3 = calcPendulum2(a1 + dt*ka1_2/2, a1_v + dt*ka1_v_1/2, a2 + dt*ka2_2/2, a2_v + dt*ka2_v_1/2);
                    
                    ka1_4 = a1_v + ka1_v_3;
                    ka2_4 = a2_v + ka2_v_3;
                    
                    ka1_v_4 = calcPendulum1(a1 + dt*ka1_3, a1_v + dt*ka1_v_1, a2 + dt*ka2_3, a2_v + dt*ka2_v_1);
                    ka2_v_4 = calcPendulum2(a1 + dt*ka1_3, a1_v + dt*ka1_v_1, a2 + dt*ka2_3, a2_v + dt*ka2_v_1);
                    
                    double dv1 = (ka1_v_1 + 2*ka1_v_2 + 2*ka1_v_3 + ka1_v_4)*dt/6;
                    double dv2 = (ka2_v_1 + 2*ka2_v_2 + 2*ka2_v_3 + ka2_v_4)*dt/6;
                    double da1 = (ka1_1 + 2*ka1_2 + 2*ka1_3 + ka1_4)*dt/6;
                    double da2 = (ka2_1 + 2*ka2_2 + 2*ka2_3 + ka2_4)*dt/6;
                    
                    a1_v += dv1;
                    a2_v += dv2;
                            
                    a1 += da1;
                    a2 += da2;
                    break;
                    
            }
            x1 = r1 * sin(a1) + x0;
            x2 = r2 * sin(a2) + x1;
            y1 = r1 * cos(a1) + y0;
            y2 = r2 * cos(a2) + y1;
            
            lblA1.setText(dec.format(a1/Math.PI) + " pi radians");
            lblA2.setText(dec.format(a2/Math.PI) + " pi radians");
            draw();
            
            if(x2prev != -1 && checkTrace.isSelected()) {
                Line traceLine = new Line(x2prev, y2prev, x2, y2);
                traceLine.setStroke(Color.RED);
                mainPane.getChildren().add(0,traceLine);
            }
            
            x2prev = x2;
            y2prev = y2;
            
        }
    }
    
    AnimationTimer timer = new PendulumTimer();
    
    @FXML
    private void handleStart(ActionEvent event) {
        a1_v = 0;
        a2_v = 0;
        solver = cmbDiffSolver.getValue();
        timer.start();
    }

    @FXML
    private void handleStop(ActionEvent event) {
        timer.stop();
    }
    
}
