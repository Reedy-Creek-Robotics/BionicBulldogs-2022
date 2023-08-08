package org.firstinspires.ftc.teamcode.modules.parallel;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.opmode.auto.AutoBase;

@Autonomous
public class ThreadTest extends AutoBase {
    Thread t1;
    Thread t2;
    @Override
    public void initalize(){
        t1 = new Thread(this);
        t2 = new Thread(this);

        t1.addTask(new TaskListGeneral.Print("a1"));
        t1.addTask(new TaskListGeneral.Wait(5));
        t1.addTask(new TaskListGeneral.Print("b1"));
        t1.addTask(new TaskListGeneral.SetLabel("a"));

        t2.addTask(new TaskListGeneral.Print("a2"));
        t2.addTask(new TaskListGeneral.Wait(3));
        t2.addTask(new TaskListGeneral.Print("b2"));
        t2.addTask(new TaskListGeneral.WaitForLabel(t1, "a"));
        t2.addTask(new TaskListGeneral.Print("c2"));
    }
    public void move(){
        t1.start();
        t2.start();
        while((!t1.isDone() || !t2.isDone()) && opModeIsActive()){
            t1.evaluate();
            t2.evaluate();
        }
    }
}
