package frc.team3322;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PIDController {
    private String subsystem_name;
    private double kp, decay, ki, kd, lastError;
    private double i_term;
    private double target;

    public PIDController(String subsystem_name, double kp, double decay, double ki, double kd) {
        SmartDashboard.putNumber(subsystem_name + " kp", kp);
        SmartDashboard.putNumber(subsystem_name + " decay", decay);
        SmartDashboard.putNumber(subsystem_name + " ki", ki);
        SmartDashboard.putNumber(subsystem_name + " kd", kd);
        this.subsystem_name = subsystem_name;
        this.kp = kp;
        this.decay = decay;
        this.ki = ki;
        this.kd = kd;
    }

    private void updateConstants() {
        SmartDashboard.getNumber(subsystem_name + " kp", kp);
        SmartDashboard.getNumber(subsystem_name + " decay", decay);
        SmartDashboard.getNumber(subsystem_name + " ki", ki);
        SmartDashboard.getNumber(subsystem_name + " kd", kd);
    }

    public void initialize(double target, double input) {
        this.target = target;
        i_term = 0;
        lastError = target - input;
    }

    public double output(double input) {
        updateConstants();

        double error = target - input;

        double p_term = error * kp;
        i_term = decay * i_term + error * ki;
        double d_term = (error - lastError) * kd;

        lastError = error;

        return p_term + i_term + d_term;
    }
}
