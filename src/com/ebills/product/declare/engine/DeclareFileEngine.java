/**
 * @Title: DeclareFileEngine.java
 * @Package com.gjjs.intf
 * @Description: TODO
 * @author xiaoguocan
 * @date 2014-06-16 下午5:01:19
 * @version V1.0
 */
package com.ebills.product.declare.engine;

import com.ebills.intf.engine.BaseEngine;
import com.ebills.product.declare.manage.FileBuildManage;
import com.ebills.util.EbillsException;
import com.ebills.util.EbillsLog;
import com.ebills.util.db.ConnectionManager;
import com.ebills.util.db.ConnectionManagerFactory;

/**
 * @Description: 定时接收MQ消息并调用指定逻辑组件的引擎
 *
 * @author wangXinlin
 * @date 2013-11-4 下午5:01:19
 *
 */
public class DeclareFileEngine extends BaseEngine {
	/**
	 * @Fields className : 记录日志时使用的分类名
	 */
	private String className = DeclareFileEngine.class.getName();
	private EbillsLog log = new EbillsLog(className);
	
	/**
	 * Description: 构造函数
	 *
	 * @throws EbillsException
	 */
	public DeclareFileEngine()throws EbillsException{
		super();
	}

	/**
	 * Description: 调用申报接收发送线程
	 *
	 * @throws EbillsException
	 *
	 * @see com.gjjs.intf.engine.BaseEngine#execute()
	 */
	@Override
	public void execute() throws EbillsException {
		try {
			log.debug(1, new String[]{this.getFlowId(), this.getOpId()});
			//处理申报反馈文件
			System.out.println("处理申报反馈文件..............................");
			ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
			try {
				FileBuildManage.parseDeclareErrorFile();
			} catch (EbillsException e) {
				cm.setRollbackOnly();
				throw e;
			} finally{
				cm.commit();
			}
		} catch (Exception e) {
			log.debug(2, new String[]{this.getFlowId(), this.getOpId(), e.getMessage()});
		}
	}

}
