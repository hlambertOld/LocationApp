import java.util.ArrayList;

import context.arch.storage.Attributes;
import cac.location.MacToBuildingInterpreter;


public class Tester {
    public static void main(String[] args){
        MacToBuildingInterpreter mtbi = new MacToBuildingInterpreter(5001);
        Attributes attr = new Attributes();
        Attributes addresses = new Attributes();
        ArrayList<String> list = new ArrayList<String>();
        list.add("00:19:07:05:3d:51");
        list.add("123213");
        list.add("ADAWDWAD");
        list.add("HANS");
        addresses.addAll(list);
        attr.addAttribute("addresses", addresses, Attributes.ATTRIBUTES);
        Attributes result = mtbi.interpretData(attr);
        
        System.out.println(result.getAttributeNameValue("buildingName").getValue());
    }

}
