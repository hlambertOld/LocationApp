package cac.location;

public class Building implements Comparable<Building>{
    
    private String name;
    
    public Building(String name){
        this.name = name;
    }
    
    public String getName(){
        return name;
    }

    @Override
    public int compareTo(Building that) {
        return this.name.compareTo(that.name);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Building other = (Building) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

}
