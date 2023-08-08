package org.firstinspires.ftc.teamcode.modules.parallel;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.util.ArrayList;

public class Thread {
    ArrayList<TaskBase> taskList = new ArrayList<TaskBase>();
    public int taskIndex = 0;
    public String label = "";
    public LinearOpMode opmode;
    public void start(){
        taskList.get(0).start(this);
    }
    public void evaluate(){
        if(isDone()){return;}
        if(taskList.get(taskIndex).evaluate()){
            taskIndex++;
            taskList.get(taskIndex).start(this);
        }
    }
    public void addTask(TaskBase t){
        taskList.add(t);
    }
    public boolean isDone(){
        return taskIndex >= taskList.size() - 1;
    }
    public Thread(LinearOpMode op){
        opmode = op;
    }
}
