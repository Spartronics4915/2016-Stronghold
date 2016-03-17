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
     }
     
     public static boolean isLeftPortcullisAtTop(){
         return RobotMap.portcullisSwitchTop.get();
     }
     public static boolean isLeftPortcullisAtBottom(){
         return RobotMap.portcullisSwitchBottom.get();
     }
     public static boolean isRightPortcullisAtTop(){
         return RobotMap.portcullisSwitchTop.get();
     }
     public static boolean isRightPortcullisAtBottom(){
         return RobotMap.portcullisSwitchBottom.get();
     }
     
     public static void PortcullisMoveUp(){
         System.out.println("Executing move portcullis up"); 
         RobotMap.portcullisLeftMotor.set(PERCENT_POWER);
         RobotMap.portcullisRightMotor.set(PERCENT_POWER);       
       if (isRightPortcullisAtTop()){
         RobotMap.portcullisLeftMotor.disableControl();
         RobotMap.portcullisRightMotor.disableControl();
         System.out.println("Portcullis reached top");
       }
         
     }
     
     public static void PortcullisMoveDown(){
         System.out.println("Executing move portcullis down"); 
         RobotMap.portcullisLeftMotor.set(-PERCENT_POWER);
         RobotMap.portcullisRightMotor.set(-PERCENT_POWER);
         if (isRightPortcullisAtBottom()){
         RobotMap.portcullisLeftMotor.disableControl();
         RobotMap.portcullisRightMotor.disableControl();
         }
         System.out.println("Portcullis reached bottom");
 
     }
         
    }
         
 
 