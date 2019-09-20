package galaxis.lee.controller;

import galaxis.lee.cc.Location;
import galaxis.lee.cc.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Lee
 * @Date: Created in 15:12 2019/9/20
 */
public class DBController {

    private static final Logger log = LoggerFactory.getLogger(DBController.class);

    static String sql = null;
    ResultSet ret = null;
    PreparedStatement preparedStatement;

    /*查询 Location 表数据条数 */
    public  int selectLocationCount(Connection connection) {
        ResultSet rs;
        int count = 0;
        String sql = "select count(1) from ga_locations";
        try {
            rs = connection.createStatement().executeQuery(sql);
            if (rs.next()) {
                count = rs.getInt(1);
            }
            // System.out.println("-->> count:"+count);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }


    public List<Position> getPosition(Connection connection) {
        // TODO Auto-generated method stub
        // sql = "select * from ga_position where type=4 and type2=4 ";
        sql = "select * from ga_position where type=4 and pos>100000  ";
        // db1 = new DBHelper(sql);
        List list = new ArrayList<Position>();
        try {
            ret = connection.createStatement().executeQuery(sql);
            // ret = db1.pst.executeQuery();
            while (ret.next()) {
                Position position = new Position();
                position.setLevel(ret.getInt(1));
                position.setPos(ret.getInt(2));
                position.setJunction(ret.getInt(3));
                position.setLift_area(ret.getString(7));
                list.add(position);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return list;
    }
    public void putLocation(Connection connection,List<Location> list) {
        // for(Location l : list){
        //     System.out.println("--> "+l.toString());
        // }
        // TODO Auto-generated method stub
        sql = "insert into ga_locations(level,location,pos,aisle,state,area,priority,box_number,weight,lift_area,type,Container_status)"
                + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?);";
        // db1 = new DBHelper(sql);
        try {
            connection.setAutoCommit(false);
            PreparedStatement stmt = connection.prepareStatement(sql);
            for (Location location : list) {
                stmt.setInt(1, location.getLevel());
                stmt.setInt(2, location.getLocation());
                stmt.setInt(3, location.getPos());
                stmt.setInt(4, location.getAisle());
                stmt.setInt(5, location.getState());
                stmt.setString(6, location.getArea());
                stmt.setInt(7, location.getPriority());
                stmt.setString(8, location.getBox_number());
                stmt.setInt(9, location.getWeight());
                stmt.setString(10, location.getLift_area());
                stmt.setInt(11, location.getType());
                stmt.setInt(12, location.getContainer_status());
                stmt.addBatch();
                // db1.pst.executeUpdate();
            }
            stmt.executeBatch();
            connection.commit();
            // db1.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
    /**
     * 将生成的position数据  插入数据库
     *
     * @param list
     */
    public void putPosition(Connection connection,List<Position> list) {
        long startTime = System.currentTimeMillis();
        try {
            connection.setAutoCommit(false);
            PreparedStatement stmt = connection.prepareStatement("insert into ga_position(level,pos,junction,direction,distance,type,lift_area,junction2,direction2,distance2,type2) VALUES(?,?,?,?,?,?,?,?,?,?,?);");
            // System.out.println("生成数据大小：" + list.size());        //1000000
            int num = 0;
            for (Position position : list) {
                num++;
                stmt.setInt(1, position.getLevel());
                stmt.setInt(2, position.getPos());
                stmt.setInt(3, position.getJunction());
                stmt.setInt(4, position.getDirection());
                stmt.setInt(5, position.getDistance());
                stmt.setInt(6, position.getType());
                stmt.setString(7, position.getLift_area());
                stmt.setInt(8, position.getJunction2());
                stmt.setInt(9, position.getDirection2());
                stmt.setInt(10, position.getDistance2());
                stmt.setInt(11, position.getType2());
                stmt.addBatch();
                // System.out.println(new Date() + "--> 第 [ " + num + " ] 条数据插入");
            }
            stmt.executeBatch();
            connection.commit();
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            long endTime = System.currentTimeMillis();
            log.info("ga_position表插入" + list.size() + "条数据，执行时间：" + (endTime - startTime) + "ms");
            // System.out.println("ga_position表插入" + list.size() + "条数据，执行时间：" + (endTime - startTime) + "ms");
        }
    }
    public int selectDBCount(Connection connection) {
        ResultSet rs;
        int count = 0;
        String sql = "select count(1) from ga_position";
        try {
            rs = connection.createStatement().executeQuery(sql);
            if (rs.next()) {
                count = rs.getInt(1);
            }
            // System.out.println("-->> count:"+count);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;

    }



    public void delLocation(Connection connection) {
        try {
            String sqlc = "truncate ga_locations";
            preparedStatement = (PreparedStatement) connection.prepareStatement(sqlc);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void delPosition(Connection connection) {
        try {
            String sql = "truncate ga_position";
            preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // 删除 pos (99999，~）
    public void delPOS6(Connection connection) {
        String pos6 = " delete from ga_position where pos>99999";
        try {
            PreparedStatement stmt = connection.prepareStatement(pos6);
            int result6 = stmt.executeUpdate();
            // log.info( "delete ga_position pos>99999 data result: " + result6);
            log.info("delete ga_position pos>99999 data result: " + result6);
            // System.out.println("delete ga_position pos>99999 data result: " + result6);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // 删除 pos [100~99999]
    public void delPOS3(Connection connection) {
        String pos3 = " delete from ga_position where pos>=100 and pos<=99999";
        try {
            PreparedStatement stmt = connection.prepareStatement(pos3);
            int result = stmt.executeUpdate();
            log.info("delete ga_position pos>=100 && pos<=99999 data result: " + result);
            // System.out.println("delete ga_position pos>=100 && pos<=99999 data result: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
