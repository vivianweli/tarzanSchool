package components;

import javax.sound.sampled.*;

import controllers.Game_controller;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class VoiceInput {
	private TargetDataLine microphone;
    private AudioFormat format;
    private Game_controller gamecontroller;
    private ImageView boar1;
    private ImageView boar2;
    private ImageView tarzan;
    private boolean running;

    //Add a constructor to receive the Game controller
    public VoiceInput(Game_controller gameController) {
        this.gamecontroller = gameController;  // Pass and save the Game controller instance
        this.boar1 = gameController.boar1;
        this.boar2 = gameController.boar2;
        this.tarzan = gameController.tarzan;
        format = new AudioFormat(16000, 16, 1, true, true);
        try {
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            microphone = (TargetDataLine) AudioSystem.getLine(info);
            microphone.open(format);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Activate the listening microphone input and calculate the sound bay
    public void start() {
        microphone.start();
        new Thread(() -> {
            byte[] buffer = new byte[1024];
            while (true) {
                int bytesRead = microphone.read(buffer, 0, buffer.length);
                double amplitude = calculateAmplitude(buffer, bytesRead);
                if (amplitude > 0) {
                    double decibel = 20 * Math.log10(amplitude);
                    if (decibel > 20) {
                        System.out.println("Decibel: " + decibel);
                        triggerBoarAction();  // When the sound bay value exceeds the threshold, the dodging action is performed
                    }
                }
            }
        }).start();
    }

    // Calculated amplitude (approximate computed sound bay value)
    private double calculateAmplitude(byte[] audioData, int bytesRead) {
        long sum = 0;
        for (int i = 0; i < bytesRead; i++) {
            sum += audioData[i] * audioData[i];
        }
        double average = sum / bytesRead;
        return Math.sqrt(average);
    }

    // Perform maneuvers to avoid enemies
    private void triggerBoarAction() {       
     // Determine if the boar is close to the player
        if (isBoarNearPlayer(boar1)) {
            frightenBoar(boar1);  
        }
        if (isBoarNearPlayer(boar2)) {
            frightenBoar(boar2); 
        }
    }

    private void frightenBoar(ImageView boar) {
        // stop the collision function
        gamecontroller.isCollisionPaused = false;
        // Set blinking effect: Toggle visibility every 0.3 seconds for 3 seconds
        Timeline flickerTimeline = new Timeline(
            new KeyFrame(Duration.seconds(0.0), e -> boar.setVisible(false)),
            new KeyFrame(Duration.seconds(0.3), e -> boar.setVisible(true)),
            new KeyFrame(Duration.seconds(0.6), e -> boar.setVisible(false)),
            new KeyFrame(Duration.seconds(0.9), e -> boar.setVisible(true)),
            new KeyFrame(Duration.seconds(1.2), e -> boar.setVisible(false)),
            new KeyFrame(Duration.seconds(1.5), e -> boar.setVisible(true)),
            new KeyFrame(Duration.seconds(1.8), e -> boar.setVisible(false)),
            new KeyFrame(Duration.seconds(2.1), e -> boar.setVisible(true)),
            new KeyFrame(Duration.seconds(2.4), e -> boar.setVisible(false)),
            new KeyFrame(Duration.seconds(2.7), e -> boar.setVisible(true)),
            new KeyFrame(Duration.seconds(3.0), e -> boar.setVisible(true)) 
        );

        // Starting flicker effect
        flickerTimeline.play();
        // Collision will resume in 3 seconds
        Timeline restoreCollisionTimeline = new Timeline(
            new KeyFrame(Duration.seconds(3), e -> {
                gamecontroller.isCollisionPaused = false;
                System.out.println("Boar can collide again.");
            })
        );
        restoreCollisionTimeline.play();

    }
    private boolean isBoarNearPlayer(ImageView boar) {
    	double playerX = tarzan.getLayoutX();
        double playerY = tarzan.getLayoutY();       
        // Get the location of Boar 1
        double boar1X = boar1.getLayoutX();
        double boar1Y = boar1.getLayoutY();        
        // Get the location of Boar 2
        double boar2X = boar2.getLayoutX();
        double boar2Y = boar2.getLayoutY();        
        // Set the distance threshold
        double distanceThreshold = 150.0;        
        // Calculate the distance between Boar 1 and the player
        double distanceToBoar1 = Math.sqrt(Math.pow(boar1X - playerX, 2) + Math.pow(boar1Y - playerY, 2));       
        // Calculate the distance between Boar 2 and the player
        double distanceToBoar2 = Math.sqrt(Math.pow(boar2X - playerX, 2) + Math.pow(boar2Y - playerY, 2));
        // Return true if the distance of boar 1 or boar 2 is less than the threshold
        return (distanceToBoar1 < distanceThreshold || distanceToBoar2 < distanceThreshold);
    }
}

    


