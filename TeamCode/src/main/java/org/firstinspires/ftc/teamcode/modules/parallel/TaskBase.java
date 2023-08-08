package org.firstinspires.ftc.teamcode.modules.parallel;

public abstract class TaskBase {
    public abstract void start(Thread t);
    public abstract boolean evaluate();
}
