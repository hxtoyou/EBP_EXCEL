package com.ebills.product.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.emory.mathcs.backport.java.util.Collections;

public class AuthHelpRang {
	private double minamt;
	private double maxamt;
	private String users;
	
	public AuthHelpRang(double aMinamt, double aMaxamt, String aUsers){
		this.minamt = aMinamt;
		this.maxamt = aMaxamt;
		this.users = aUsers;
	}

	public double getMinamt() {
		return minamt;
	}

	public void setMinamt(double minamt) {
		this.minamt = minamt;
	}

	public double getMaxamt() {
		return maxamt;
	}

	public void setMaxamt(double maxamt) {
		this.maxamt = maxamt;
	}

	public String getUsers() {
		return users;
	}

	public void setUsers(String users) {
		this.users = users;
	}
	
	public static void main(String[] args) {
		List<AuthHelpRang> rangeLst = new ArrayList<AuthHelpRang>();
		rangeLst.add( new AuthHelpRang(0, 80, "C@@D") );
		rangeLst.add( new AuthHelpRang(0, -1, "A@@B") );
		rangeLst.add( new AuthHelpRang(0, 10.00, "C@@D") );
		rangeLst.add( new AuthHelpRang(0, 13.000, "E@@F") );
		
		//System.out.println( AuthHelpRang.getRange(500, rangeLst, "A@@D").getUsers() );
		
		AuthHelpRang.sort(rangeLst);
		
		for( int i=0; i<rangeLst.size(); i++ ){
			System.out.println( "min:"+rangeLst.get(i).getMinamt()+";   max:"+rangeLst.get(i).getMaxamt() );
		}
	}
	
	/**
	 * 排序授权分级
	 * @param rangeLst
	 */
	public static void sort(List<AuthHelpRang> rangeLst){
		Collections.sort(rangeLst, new Comparator<AuthHelpRang>(){
			public int compare(AuthHelpRang o1, AuthHelpRang o2) {
				return (o1.getMinamt() - o2.getMinamt() > 0.0) ? 1 : -1 ;
			}
		});
		
		Collections.sort(rangeLst, new Comparator<AuthHelpRang>(){
			public int compare(AuthHelpRang o1, AuthHelpRang o2) {
				if( o1.getMaxamt() < 0 ) return 1;
				if( o2.getMaxamt() < 0 ) return -1;
				return (o1.getMaxamt() - o2.getMaxamt() > 0.0) ? 1 : -1 ;
			}
		});
	}
	
	/**
	 * @param amt
	 * @param rangeLst
	 * @param doneUsers
	 * @return
	 */
	public static AuthHelpRang getRange(double amt, List<AuthHelpRang> rangeLst, String doneUsers){
		AuthHelpRang retRang = null;
		
		if( doneUsers == null ){
			doneUsers = "";
		}
		Collections.sort(rangeLst, new Comparator<AuthHelpRang>(){
			public int compare(AuthHelpRang o1, AuthHelpRang o2) {
				return (o1.getMinamt() - o2.getMinamt() > 0.0) ? 1 : -1 ;
			}
		});
		
		Collections.sort(rangeLst, new Comparator<AuthHelpRang>(){
			public int compare(AuthHelpRang o1, AuthHelpRang o2) {
				if( o1.getMaxamt() < 0 ) return -1;
				if( o2.getMaxamt() < 0 ) return 1;
				return (o1.getMaxamt() - o2.getMaxamt() > 0.0) ? 1 : -1 ;
			}
		});
		
		boolean isEnd = false;
		for( AuthHelpRang rang : rangeLst ){
			if( isEnd ){
				break;
			}
			if( amt >= rang.getMinamt() ){
				if( rang.getMaxamt() <= 0.0 || rang.getMaxamt() >= amt ){
					isEnd = true;
				}
				if( allInArr(rang.getUsers().split("@@"), doneUsers.split("@@") ) ){
					continue;
				}else{
					retRang = rang;
				}
			}
		}
		
		return retRang;
	}
	
	/**
	 * 第一个数组的是否有元素都在第二个数组中出现过
	 * @param arr
	 * @param otherArr
	 * @return true-第一个数组的所有元素都在第二个数组中, false-否
	 */
	public static boolean allInArr(String[] arr, String[] otherArr){
		boolean is = false;
		Map<String, String> map = new HashMap<String, String>();
		if( otherArr != null ){
			for(String e : otherArr){
				map.put(e, e);
			}
		}
		
		if( arr != null ){
			for(String e : arr){
				if( map.containsKey(e) ){
					is = true;
					break;
				}
			}
		}
		
		return is;
	}
}
