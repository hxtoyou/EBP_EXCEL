package com.ebills.product.action.excel;

import org.apache.commons.bussprocess.context.Context;
import org.apache.commons.bussprocess.exception.BPException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eap.flow.EAPAction;
import com.ebills.product.component.ReportsGenerator;
import com.ebills.product.component.ReportsGeneratorService;
import com.ebills.utils.OutPutBean;

/**
 * @author Xiao He E-mail:hxtoyou@163.com
 * @version 创建时间：2015年6月9日 下午3:42:08
 * 
 */
public class ExcelConverJsonAction extends EAPAction{
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	private String path;
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String execute(Context context) throws BPException{
		ReportsGenerator generator = new ReportsGeneratorService();
		System.out.println("BPE");
		context = (Context)generator.generateReport(context);
		OutPutBean outPut = null;
		outPut = new OutPutBean(context.getValue("output").toString());
		outPut.writeOutPut(context);
		return null;
	}
}