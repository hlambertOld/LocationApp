package cac.location;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import context.arch.comm.DataObject;
import context.arch.discoverer.ComponentDescription;
import context.arch.discoverer.componentDescription.ClassnameElement;
import context.arch.discoverer.querySystem.AbstractQueryItem;
import context.arch.discoverer.querySystem.QueryItem;
import context.arch.service.Services;
import context.arch.storage.Attribute;
import context.arch.storage.Attributes;
import context.arch.subscriber.Callbacks;
import context.arch.widget.Widget;
import context.widgets.WPersonNamePresence2;



public class LocationWidget extends Widget{
    
    private static final String CLASSNAME = "LocationWidget";
    private static final String RETURN_BUILDING = "returnBuilding";
    private ComponentDescription comp;

    public LocationWidget(int port) {
        super(port, CLASSNAME);
        setVersion(VERSION_NUMBER);
        findDiscoverer(true);
        System.out.println("hej");
        subscribe();
    }
    
    private void subscribe(){
        AbstractQueryItem qForWPNP2 = new QueryItem(new ClassnameElement("context.widgets.WPersonNamePresence2"));
        Vector res = discovererQuery (qForWPNP2);
        System.out.println(res);
        if (res != null && !res.isEmpty ()){
          comp = (ComponentDescription) res.firstElement();
        }
        getBuilding();
    }
    
    protected Attributes initAttributes() {
        return new Attributes();
    }


    protected Callbacks initCallbacks() {
        Callbacks result = new Callbacks();
        result.addCallback(RETURN_BUILDING, initAttributes());
        return result;
    }


    protected Attributes initConstantAttributes() {
        return new Attributes();
    }

    @Override
    protected Services initServices() {
        return new Services();
    }

    @Override
    protected Attributes queryGenerator() {
        // TODO Auto-generated method stub
        return null;
    }
    
    private void getBuilding(){
        Attributes request = new Attributes();
        Attributes macs = new Attributes();
        macs.addAll(getAddresses());
        System.out.println(comp.hostname +" " + comp.port +" " + comp.id);
        DataObject response = askInterpreter(comp.hostname, comp.port, comp.id, macs);
        Attributes newData = new Attributes(response);
        System.out.println("new data is: "+newData);
    }
    
    private List<String> getAddresses(){
        ArrayList<String> result = new ArrayList<String>();
        result.add("00:19:07:05:3d:51");
        return result;
    }
    
    public static void main(String[] args){
        new LocationWidget(5002);
    }
}
