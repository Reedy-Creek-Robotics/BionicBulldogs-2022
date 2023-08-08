package org.firstinspires.ftc.teamcode.modules.parallel;

import com.qualcomm.robotcore.util.ElapsedTime;

public class TaskListGeneral {
    public static class Print extends TaskBase{
        String stg;
        Thread thread;
        public void start(Thread t) {
            thread = t;
            thread.opmode.telemetry.addLine(stg);
            thread.opmode.telemetry.update();
        }

        public boolean evaluate(){
            return true;
        }
        public Print(String s){
            stg = s;

        }
    }
    public static class Wait extends TaskBase{
        Thread thread;
        double delay;
        ElapsedTime e;
        public void start(Thread t){
            thread = t;
            e = new ElapsedTime();
            e.reset();
        }
        public boolean evaluate(){
            return e.seconds() >= delay;
        }
        public Wait(double d){
            delay = d;
        }
    }
    public static class WaitForLabel extends TaskBase{
        Thread thread;
        Thread targetThread;
        String label;
        public void start(Thread t){
            thread = t;
        }
        public boolean evaluate() {
            return targetThread.label.equals(label);
        }
        public WaitForLabel(Thread t, String stg){
            targetThread = t;
            label = stg;
        }
    }
    public static class SetLabel extends TaskBase{
        Thread thread;
        String label;
        public void start(Thread t){
            thread = t;
            thread.label = label;
        }
        public boolean evaluate(){
            return true;
        }
        public SetLabel(String s){
            label = s;
        }
    }
}
