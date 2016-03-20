package org.usfirst.frc.team4915.stronghold.subsystems;
 
 import org.usfirst.frc.team4915.stronghold.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;
 
 
 public class Portcullis extends Subsystem {
     public static final double PERCENT_POWER = 0.6;

     // Put methods for controlling this subsystem
     // here. Call these from Commands.
 
     public void initDefaultCommand() {
         // Set the default command for a subsystem here.
         //setDefaultCommand(new MySpecialCommand());
         RobotMap.portcullisLeftMotor.setSafetyEnabled(false);
         RobotMap.portcullisRightMotor.setSafetyEnabled(false);

     }
     
     public static boolean isLeftPortcullisAtTop(){
         return RobotMap.portcullisLeftMotor.isFwdLimitSwitchClosed();
     }
     public static boolean isLeftPortcullisAtBottom(){
         return RobotMap.portcullisLeftMotor.isRevLimitSwitchClosed();
     }
     public static boolean isRightPortcullisAtTop(){
         return RobotMap.portcullisRightMotor.isFwdLimitSwitchClosed();
     }
     public static boolean isRightPortcullisAtBottom(){
         return RobotMap.portcullisRightMotor.isRevLimitSwitchClosed();
     }
     
     public static void PortcullisMoveUp(boolean isRight){ //right is true and left is false
         if (isRight){ 
             System.out.println("Executing move portcullis up: right"); 
             RobotMap.portcullisRightMotor.set(PERCENT_POWER);    
             if (isRightPortcullisAtTop()){
                 RobotMap.portcullisRightMotor.set(0);
                 System.out.println("Right Portcullis reached top");
             }
         }
         if (!isRight){
             System.out.println("Executing move portcullis up: left"); 
             RobotMap.portcullisLeftMotor.set(PERCENT_POWER);    
             }
             if (isLeftPortcullisAtTop()){
                 RobotMap.portcullisLeftMotor.set(0);
                 System.out.println("Left Portcullis reached top");
         }
       }
        
     
     public static void PortcullisMoveDown(boolean isRight){
         if (isRight){
             System.out.println("Executing move portcullis down: right"); 
             RobotMap.portcullisRightMotor.set(-PERCENT_POWER);    
             if (isRightPortcullisAtBottom()){
                 RobotMap.portcullisRightMotor.set(0);
                 System.out.println("Right Portcullis reached bottom");
             }
         }
         if (!isRight){
             System.out.println("Executing move portcullis down: left"); 
             RobotMap.portcullisLeftMotor.set(-PERCENT_POWER);    
             }
             if (isLeftPortcullisAtBottom()){
                 RobotMap.portcullisLeftMotor.set(0);
                 System.out.println("Left Portcullis reached bottom");
         }
       }
         
 }
 