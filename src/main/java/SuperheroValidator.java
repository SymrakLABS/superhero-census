import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SuperheroValidator {

    int power;
    String name;

    public void checkSuperhero (String name, String universe, int power, String description, String alive)
            throws NotValidNameException, NotValidPowerException, NotValidUniverseException,
                NotValidAliveException, NotValidDescriptionException {

        String universeLowerCase = universe.toLowerCase();
        String aliveLowerCase =  alive.toLowerCase();

        String regx = "[a-zA-Z]+\\.?";
        Pattern pattern = Pattern.compile(regx,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(name);

        if(name.length() < 3 || name.length() > 100 && matcher.find()){
            throw new NotValidNameException();
        }
        if(!universeLowerCase.equals("marvel")){
            if(!universeLowerCase.equals("dc")){
                throw new NotValidUniverseException();
            }
        }
        if(description.equals("")){
            throw new NotValidDescriptionException();
        }
        if(power < 0 || power > 100){
            throw new NotValidPowerException();
        }
        if(!aliveLowerCase.equals("true")){
            if(!aliveLowerCase.equals("false")){
                throw new NotValidAliveException();
            }
        }
    }


    public Boolean check(){
        return true;
    }



}
