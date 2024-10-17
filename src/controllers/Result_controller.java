package controllers;

import javafx.fxml.FXML;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import static components.GameValue.*;

public class Result_controller {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private AnchorPane result;
    
    //public static boolean IsWin = false, IsPass = false, IsLose = false;

    @FXML
    private ImageView result_bag;

    @FXML
    private ImageView result_boar;

    @FXML
    private ImageView result_book;

    @FXML
    private ImageView result_cloth;

    @FXML
    private Text result_intro;

    @FXML
    private ImageView result_tarzan;
    
    //public static boolean IsBag = false, IsBook = false, IsCloth = false;
    
    void OnShow() {
    	if(IsWin == true)
    	{
    		result_bag.getClass().getResourceAsStream("/images/bag.png");
    		result_book.getClass().getResourceAsStream("/images/book.png");
    		result_cloth.getClass().getResourceAsStream("/images/cloth.png");
    		result_tarzan.getClass().getResourceAsStream("/images/tarzan_win.png");
    		result_boar.getClass().getResourceAsStream("/images/boar_win.png");
    		result_intro.setText("YOU WIN");
    	}
    	
    	else if(IsPass == true) {
    		if(IsBag == true)
    		{
    			result_bag.getClass().getResourceAsStream("/images/bag.png");
    		}
    		else if(IsBag != true){
    			result_bag.getClass().getResourceAsStream("/images/lose_bag.png");
    		}
    		if(IsBook == true)
    		{
    			result_bag.getClass().getResourceAsStream("/images/book.png");
    		}
    		else if(IsBook != true){
    			result_bag.getClass().getResourceAsStream("/images/lose_book.png");
    		}
    		if(IsCloth == true)
    		{
    			result_bag.getClass().getResourceAsStream("/images/cloth.png");
    		}
    		else if(IsCloth != true){
    			result_bag.getClass().getResourceAsStream("/images/lose_cloth.png");
    		}
    		result_tarzan.getClass().getResourceAsStream("/images/tarzan_pass.png");
    		result_boar.getClass().getResourceAsStream("/images/boar_pass.png");
    		result_intro.setText("YOU PASS");   		
    	}
    	
    	else if(IsLose == true) {
    		result_bag.getClass().getResourceAsStream("/images/lose_bag.png");
    		result_book.getClass().getResourceAsStream("/images/lose_book.png");
    		result_cloth.getClass().getResourceAsStream("/images/lose_cloth.png");
    		result_tarzan.getClass().getResourceAsStream("/images/tarzan_lose.png");
    		result_boar.getClass().getResourceAsStream("/images/boar_lose.png");
    		result_intro.setText("YOU LOSE");   		
    	}
    	else {
    		System.out.println("bug in result_controller.OnShow");
    		return;
    	}
    }

}
