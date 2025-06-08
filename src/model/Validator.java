package model;

public class Validator {
    
    public static final boolean quantityValidatorSingleProduct(String text){

        if(text.matches("[1-9]\\d*") || text.equals("")){
            return true;
        }else{
            return false;
        }
       
    }
    
    public static final boolean checkPositivenumbers(String text){
    
        if(text.matches("^(0|[1-9]\\d*)$")){
            return true;
        }else{
            return false;
        }
        
    }
    
}
