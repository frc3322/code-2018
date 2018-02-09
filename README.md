# code-2018
Code for FRC POWER UP
Code review for auton commands.
auton/DriveDistance.java: 
Presently the drive-distance command only allows distance.  This isn't a problem, but later it may be useful
to provide an overloaded constructor with a desired angle.
It may be useful to provide 'known' x/y coordinates, and to update them as the robot moves.  I don't know the best way to do this, perhaps
as inputs to the command, by reference so that they can be updated?  I'm thinking that if the command is interrupted, it will be useful to know how
far the robot travelled.  Also, can you add code to utilize the encoder reading to get the approximate position?  I don't know which is 
going to be more accurate, navx position or encoder.  I think experimenting with each will give you an answer.
The default use is to start from 0mph, and to end at 0mph.  But the command executes at a fixed speed.  It can't stop on a dime.  
and with max speed upon starting from scratch, it may skid upon startup (i.e. will the distance measurement be correct?)
I think that it will be beneficial to have a rampup and rampdown distance, based on experiment and speed, and adjust speed accordingly. 
- or maybe use some control for this. 
Also, I'm concerned that speed of "1" will be too fast for auton, we might want to max out the speed at something less than that...
but that can be determined from experiment.
I think that we should get Ken's input on how to approach this- I don't know the best approach.
IsFinished should also take into account the speed, otherwise you could end it but still be moving very fast.
auton/DriveAngle.java:
This function calls driveAngle, the same function that DriveDistance calls.
But in this case, the speed is 0, and the angle is non-zero.  
I see that there is some p-d control for angle control, which is probably good. 
IsFinished might need to take into account both an angle tolerance, and an angular speed.  Otherwise, you could overshoot.
