package com.ebills.product.dg.intf;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.bussprocess.context.Context;

import com.ebills.intf.spi.InterfaceManager;

public class ClientProcess {

	    
	    public void sendAfter(Context context){
	 	   Map  map=(Map) context.get(InterfaceManager.RESULT_KEY);
	 	  List list=new ArrayList();
	 	  list.add(map);
	 	  context.put("data", list);

	    }

}
