package timy.test.service;

import java.security.interfaces.RSAKey;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import timy.test.projo.Cement;
import timy.test.projo.Result;
import timy.test.util.JdbcTool;

public class ResultService {
	
	static String sql = null;
	public static void delet(int id) {
		sql = "delet from "+Result.getTabel()+"where id = "+id;
		try {
			JdbcTool.update(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static int update(Result result) {
		
		if (result.getId() > 0) {
			if (result.getRemark()==null) {
				result.setRemark("null");
			}
			sql = "update "+Result.getTabel()+" set ton = "+result.getTon()+",outtime = '"+result.getOuttime()+"',intime = '"+result.getIntime()
					+ "',port='"+result.getPort()+"',batchport='"+result.getBatchport()+"',cargoout="+result.getCargoout()+",cargoin="+result.getCargoin()
					+",cargotype = '"+result.getCargotype()+"',remark='"+result.getRemark()+"'"+",inid="+result.getInid()+",outid = "+result.getOutid()+",minutes="+result.getMinutes();
		}else if (result.getId() == 0) {
			if (result.getRemark()==null) {
				result.setRemark("null");
			}
//			StringBuilder builder = new StringBuilder("insert into "+Result.getTabel()+"(ton, outtime, intime, port, batchport, cargoout, cargoin, cargotype, remark, inid, outid) values ");
			sql = "insert into "+Result.getTabel()+"(ton, outtime, intime, port, batchport, cargoout, cargoin, cargotype, remark, inid, outid, minutes) values ("
					+result.getTon()+",'"+result.getOuttime()+"','"+result.getIntime()+"','"+result.getPort()+"','"+result.getBatchport()+"',"+
					result.getCargoout()+","+result.getCargoin()+",'"+result.getCargotype()+"','"+result.getRemark()+"',"+result.getInid()+","+result.getOutid()+","+result.getMinutes()+")";
//			builder.append("("+result.getTon()+",'"+result.getOuttime()+"','"+result.getIntime()+"','"+result.getPort()+"','"+result.getBatchport()+"',"+
//					result.getCargoout()+","+result.getCargoin()+",'"+result.getCargotype()+"','"+result.getRemark()+"',"+result.getInid()+","+result.getOutid());
		}
		try {
			JdbcTool.update(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		
		return 1;
		
	}
	
	/**
	 * 保存检索的结果属于第二步
	 * @param incement  进港的记录
	 * @param outcement   出港记录
	 */
	public static void saveResult(Cement incement, Cement outcement) {
		//*********写入检索到的result结果********
		Result result = null;
		result = new Result();
		result.setInid(incement.getId());
		result.setOutid(outcement.getId());
		result.setBatchport(incement.getBatchport());
		result.setCargoin(incement.getCargo());
		result.setCargoout(outcement.getCargo());
		result.setCargotype(outcement.getCargotype());
		result.setIntime(incement.getDealtime());
		result.setOuttime(outcement.getDealtime());
		result.setTon(incement.getTon());
		result.setRemark("non");
		ResultService.update(result);
//		CoalService.delet(sortcement.getId(), 1);
	}

	
	public static void  save_results(List<Cement>inList, List<Cement>outList, String table) {
	    List<Result> list = new ArrayList<Result>();
	    Result result = null;
	    for (int i = 0; i < inList.size(); i++) {
		result = new Result();
		result.setInid(inList.get(i).getId());
		result.setOutid(outList.get(i).getId());
		result.setBatchport(inList.get(i).getBatchport());
		result.setCargoin(inList.get(i).getCargo());
		result.setCargoout(outList.get(i).getCargo());
		result.setCargotype(outList.get(i).getCargotype());
		result.setIntime(inList.get(i).getDealtime());
		result.setOuttime(outList.get(i).getDealtime());
		result.setTon(inList.get(i).getTon());
		result.setRemark("non");
		list.add(result);
	    }
	    JdbcTool.savelist(list, table);
	}
	
	public static boolean  updateMinute(List<Double>list) throws Exception  {
	    
	    StringBuilder builder = new StringBuilder("update t_result set minutes = case id ");
	    StringBuilder endBuilder = new StringBuilder(" end where id in (0");
	   for (int i = 0; i < list.size(); i+=2) {
	    builder.append("when "+list.get(i)+" then "+list.get(i+1));
	    endBuilder.append(","+list.get(i));
	}
	   endBuilder.append(")");
	   builder.append(endBuilder);
	   JdbcTool.update(builder.toString());
	   SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   System.out.println("本次时间更新="+list.size()+",时间："+df.format(new Date()));
	    return true;
	    
	}
	
    public static List<Result> select(String select, Object... args) {
	Connection cnn = null;
	PreparedStatement preparedStatement = null;
	ResultSet rs = null;
	List<Result> list = null;
	try {
	    cnn = JdbcTool.getConnection();

	    preparedStatement = cnn.prepareStatement(select);
	    for (int i = 0; i < args.length; i++) {
		preparedStatement.setObject(i + 1, args[i]);
	    }
	    rs = preparedStatement.executeQuery();
	    list = new ArrayList<Result>();
	    Result result = null;
	    while (rs.next()) {
		result = new Result();
		result.setId(rs.getInt("id"));
		result.setBatchport(rs.getString("batchport"));
		result.setCargoin(rs.getFloat("cargoin"));
		result.setCargoout(rs.getFloat("cargoout"));
		result.setCargotype(rs.getString("cargotype"));
		result.setInid(rs.getLong("inid"));
		result.setIntime(rs.getString("intime"));
		// result.setMinutes(rs.getFloat("minutes"));
		result.setOutid(rs.getLong("outid"));
		result.setOuttime(rs.getString("outtime"));
		// result.setPort(rs.getString("port"));
		result.setTon(rs.getFloat("ton"));
		result.setMinutes(rs.getDouble("minutes"));

		list.add(result);
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {

	    JdbcTool.rease(rs, cnn, preparedStatement);
	}

	return list;
    }

}
