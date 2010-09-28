package cac.location;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;

import context.arch.comm.DataObject;
import context.arch.discoverer.lease.Lease;
import context.arch.interpreter.Interpreter;
import context.arch.storage.Attribute;
import context.arch.storage.AttributeNameValue;
import context.arch.storage.Attributes;
import context.arch.util.ContextTypes;

public class MacToBuildingInterpreter extends Interpreter{
    
    public static final String XML_PATH = "D:/Dokumenter/WebTek/LocationApp/data.xml";
    public static final String CLASSNAME = "MacToBuildingInterpreter";
    public static final String BUILDING_NAME = "buildingName";
    public static final String MAC_ADDRESSES_NAME = "addresses";
    private Map<String, Building> mappings;
    private Map<String, Building> buildings;

    public MacToBuildingInterpreter(int port) {
        super(port);
        setId(CLASSNAME);
        mappings = new HashMap<String, Building>();
        buildings = new HashMap<String, Building>();
        loadMappings();
        findDiscoverer(true);
    }


    public Attributes interpretData(Attributes data) {
        Attribute request = data.getAttribute(MAC_ADDRESSES_NAME);
        Attributes addresses = request.getSubAttributes();
        ArrayList<String> temp = new ArrayList<String>();
        for(Object o : addresses){
            temp.add(o.toString());
        }
        Attributes result = new Attributes();
        result.addAttributeNameValue(BUILDING_NAME, getBuilding(temp).getName());
        return result;
    }

    @Override
    protected Attributes setInAttributes() {
        Attributes atts = new Attributes();
        atts.addAttribute(MAC_ADDRESSES_NAME, new Attributes());
        return atts;
    }

    @Override
    protected Attributes setOutAttributes() {
        Attributes atts = new Attributes();
        atts.addAttribute(BUILDING_NAME);
        return atts;
    }
    
    
    private Building getBulding(String name){
        Building result = buildings.get(name);
        if(result == null){
            result = new Building(name);
            buildings.put(name, result);
        }
        return result;
    }
    
    public void add(String macAddress, Building building){
        mappings.put(macAddress, building);
    }
    
    public void add(String macAddress, String building){
        mappings.put(macAddress, getBulding(building));
    }
    
  
    /**
     * This method will return the bulding where the addresses are located
     * @param macAddresses a list of macAddresses
     * @return The building that matches most of the addresses. 
     */
    
    private Building getBuilding(List<String> macAddresses){
        ArrayList<Building> temp = new ArrayList<Building>();
        for(String address : macAddresses){
            Building building = mappings.get(address);
            if(building != null){
                temp.add(building);
            }
        }
        Collections.sort(temp);
        int i = 0;
        int max = 0;
        Building otherBuilding = null;
        Building result = temp.get(0);
        for(Building building : temp){
            if(building.equals(otherBuilding)){
                i++;
                if(i > max){
                    max = i;
                    result = building;
                }
            } else {
                i = 0;
            }
            otherBuilding = building;
        }
        return result;
    }
    
    private void loadMappings(){
        try {
            FileInputStream inStream = new FileInputStream(XML_PATH);
            SAXBuilder builder = new SAXBuilder(); 
            Document inputDocument = builder.build(inStream);
            Element itemRoot = inputDocument.getRootElement();
            List<Element> elements = itemRoot.getChildren();
            for(Element element : elements){
                add(element.getAttributeValue("mac"), element.getAttributeValue("building"));
            }
            inStream.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JDOMException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
