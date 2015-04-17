package test.mongo;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;


public class MongoDBInsertDataTest {
	private static final String HOST = "localhost";
	private static final int PORT = 27017;
	private static final String USER = "iwtxokhtd";
	private static final String PASSWORD = "123456";
	private static final String DB_NAME = "temp";
	private static final String COLLECTION = "insert_test";
	private static Mongo conn = null;
	private static DB myDB = null;
	private static DBCollection myCollection = null;

	static {
		try {
			conn = new Mongo(HOST, PORT);// 建立数据库连接
			myDB = conn.getDB(DB_NAME);// 使用test数据库
//			boolean loginSuccess = myDB.authenticate(USER,
//					PASSWORD.toCharArray());// 用户验证
//			if (loginSuccess) {
//				myCollection = myDB.getCollection(COLLECTION);
//			}
			
			myCollection = myDB.getCollection(COLLECTION);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 使用save()方法保存数据
	 * 
	 * @param collection
	 *            　“表”名
	 */
	private static void saveData(DBCollection collection) {
		DBObject saveData = new BasicDBObject();
		saveData.put("userName", "iwtxokhtd");
		saveData.put("age", "26");
		saveData.put("gender", "m");

		DBObject infoData = new BasicDBObject();
		infoData.put("height", 16.3);
		infoData.put("weight", 22);

		saveData.put("info", infoData);
		collection.save(saveData);
	}

	/**
	 * 使用insert()方法保存数据
	 * 
	 * @param collection
	 *            "表"名
	 */
	private static void insertData(DBCollection collection) {
		DBObject insertData = new BasicDBObject();
		insertData.put("name", "pig");
		insertData.put("headers", 2);
		insertData.put("legs", 4);
		// insert(DBObject obj)
		collection.insert(insertData);

		DBObject insertDataFox = new BasicDBObject();
		insertDataFox.put("name", "fox");
		insertDataFox.put("headers", 1);
		insertDataFox.put("legs", 4);

		DBObject insertDataTiger = new BasicDBObject();
		insertDataTiger.put("name", "tiger");
		insertDataTiger.put("headers", 1);
		insertDataTiger.put("legs", 3);

		List<DBObject> insertDataList = new ArrayList<DBObject>();
		insertDataList.add(insertDataFox);
		insertDataList.add(insertDataTiger);
		// insert(List<DBObject> list)
		collection.insert(insertDataList);
	}

	/**
	 * 取得查询结果集
	 * 
	 * @param collection
	 * @return
	 */
	private static DBCursor getResult(DBCollection collection) {
		return collection.find();
	}

	/**
	 * 打印结果数据
	 * 
	 * @param description
	 *            　结果数据相关描述
	 * @param recordResult
	 *            　结果集
	 */
	private static void printData(String description, DBCursor recordResult) {
		System.out.println(description);
		for (Iterator<DBObject> iter = recordResult.iterator(); iter.hasNext();) {
			System.out.println(iter.next());
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 插入数据前若有记录才先删除所有记录
		if (myCollection.find().count() > 0) {
			myCollection.remove(new BasicDBObject());
		}
		printData("插入数据前的所有结果记录为：", getResult(myCollection));
		// 再插入数据
		saveData(myCollection);
		insertData(myCollection);
		// 再查询数据
		printData("插入数据后的所有结果记录为：", getResult(myCollection));
		
		// 再删除指定的数据
		DBObject deletePig = new BasicDBObject();
		deletePig.put("name", "pig");
		myCollection.remove(deletePig);
		printData("删除pig后的所有结果记录为：", getResult(myCollection));

	}
}