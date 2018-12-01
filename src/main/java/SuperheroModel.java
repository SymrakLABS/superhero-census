

public class SuperheroModel {
    private int id;
    private String name;
    private String universe;
    private int power;
    private String description;
    private boolean alive;


    public SuperheroModel(int id){
        this.id = id;
    }


    public SuperheroModel(String name, String universe, int power, String description, String alive){
        //setId(id);
        setName(name);
        setUniverse(universe);
        setPower(power);
        setDescription(description);
        setAlive(alive);
    }

    public SuperheroModel(int id, String name, String universe, int power, String description, String alive){
        setId(id);
        setName(name);
        setUniverse(universe);
        setPower(power);
        setDescription(description);
        setAlive(alive);
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("'" + name + "'" + ", ");
        stringBuffer.append("'" + universe + "'" + ", ");
        stringBuffer.append(power + ", ");
        stringBuffer.append("'" + description  + "'" + ", ");
        stringBuffer.append(this.alive);
        return stringBuffer.toString();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUniverse(String universe) {
        this.universe = universe;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAlive(String alive) {
        if(alive.equals("true") || alive.equals("1")){
            this.alive = true;
        } else if(alive.equals("false") || alive.equals("0")){
            this.alive = false;
        } else  {
            System.out.print(alive);
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUniverse() {
        return universe;
    }

    public int getPower() {
        return power;
    }

    public String getDescription() {
        return description;
    }

    public boolean isAlive() {
        return alive;
    }


}
