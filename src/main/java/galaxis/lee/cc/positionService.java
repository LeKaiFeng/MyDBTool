/**
 * Copyright (C),2019-09-03,Galaxis
 * Date:2019/9/3 0003 16:32
 * Author:LELE
 * Description:
 */

package galaxis.lee.cc;


import galaxis.lee.utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class positionService {

    // static String sql = null;
    // static DBHelper db1 = null;
    static ResultSet ret = null;
    public static Connection conn;
    static PreparedStatement preparedStatement;

    public void putPosition2(List<Position> list) {
        int i = 0;
        System.out.println("-->插入数据：" + list.size() + "条");
        try {
            String sql = "insert into ga_position(level,pos,junction,direction,distance,type,lift_area,junction2,direction2,distance2,type2) " +
                    "VALUES(?,?,?,?,?,?,?,?,?,?,?);";
            // String st = "INSERT INTO wip_users ( firstname, lastname, email, gender, dob) VALUES (?,?,?,?,?)";
            preparedStatement = (PreparedStatement) conn.prepareStatement(sql);
            for (Position position : list) {
                preparedStatement.setInt(1, position.getLevel());
                preparedStatement.setInt(2, position.getPos());
                preparedStatement.setInt(3, position.getJunction());
                preparedStatement.setInt(4, position.getDirection());
                preparedStatement.setInt(5, position.getDistance());
                preparedStatement.setInt(6, position.getType());
                preparedStatement.setString(7, position.getLift_area());
                preparedStatement.setInt(8, position.getJunction2());
                preparedStatement.setInt(9, position.getDirection2());
                preparedStatement.setInt(10, position.getDistance2());
                preparedStatement.setInt(11, position.getType2());

                preparedStatement.executeUpdate();
                i++;
                System.out.println(new Date() + "--> 第 [ " + i + " ] 条数据插入完成");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main1(String args[]) {
        conn = ConnectionUtil.conDB();
        //插入数据  zhuan
        // positionService ps = new positionService();
        // Parameter pt = new Parameter();
        // pt.setAiseCount(3);
        // pt.setLevelCount(3);
        // pt.setJunCount(2);
        // pt.setAiseSpace(2362);
        // pt.setFirstBoard_secoundBoard(19010);
        // Calculate cl = new Calculate();
        // ps.putPosition(cl.getJunctionBoard(pt));

        //删除数据
        // delLocationPosition();

//		positionService ps = new positionService();
//		Parameter pt = new Parameter();
//		pt.setAiseCount(10);
//		pt.setBox_count(50);
//		pt.setLevelCount(3);
//		pt.setStart_firstBoard(-9210);
//		pt.setJunCount(2);
//		pt.setLocationSpace(490);
//		pt.setFirstBoard_secoundBoard(19010);
//		Calculate cl = new Calculate();
//		ps.putPosition(cl.getLocationPosition(pt));

        // ps.getLocationPosition();

        //-->location
        positionService ps = new positionService();
        List<Position> list = ps.getPosition();
        if(list.size()<=0){
            System.out.println("position 表未找到相关数据");
            return;
        }
        ps.putLocation(new Calculate().putLocation(list));
    }

    static String sql = null;

    public List<Position> getPosition() {
        // TODO Auto-generated method stub
        sql = "select * from ga_position where type=4 and type2=4 ";
        // db1 = new DBHelper(sql);
        List list = new ArrayList<Position>();
        try {
            ret = conn.createStatement().executeQuery(sql);
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

    public void putLocation(List<Location> list) {
        // for(Location l : list){
        //     System.out.println("--> "+l.toString());
        // }
        // TODO Auto-generated method stub
        sql = "insert into ga_locations(level,location,pos,aisle,state,area,priority,box_number,weight,lift_area,type,Container_status)"
                + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?);";
        // db1 = new DBHelper(sql);
        try {
            conn.setAutoCommit(false);
            PreparedStatement stmt = conn.prepareStatement(sql);
            for (Location location : list) {
                stmt.setInt(1, location.getLevel());
                stmt.setInt(2,location.getLocation() );
                stmt.setInt(3, location.getPos());
                stmt.setInt(4, location.getAisle());
                stmt.setInt(5, location.getState());
                stmt.setString(6, location.getArea());
                stmt.setInt(7, location.getPriority());
                stmt.setString(8, location.getBox_number());
                stmt.setInt(9, location.getWeight() );
                stmt.setString(10, location.getLift_area());
                stmt.setInt(11, location.getType());
                stmt.setInt(12,location.getContainer_status() );
                stmt.addBatch();
                // db1.pst.executeUpdate();
            }
            stmt.executeBatch();
            conn.commit();
            // db1.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static void delLocationPosition() {
        try {
            String sql = "truncate ga_position";
            preparedStatement = (PreparedStatement) conn.prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // @Override
    // public void getLocationPosition() {
    //     // TODO Auto-generated method stub
    //     sql = "truncate ga_position";
    //     db1 = new DBHelper(sql);
    //
    //     try {
    //         db1.pst.executeUpdate();
    //     }catch(SQLException e){
    //
    //         e.printStackTrace();
    //     }
    // }

    // @Override
    // public void putPosition(List<Position> list) {
    //     // TODO Auto-generated method stub
    //     // TODO Auto-generated method stub
    //     sql = "insert into ga_position(level,pos,junction,direction,distance,type,lift_area,junction2,direction2,distance2,type2) "
    //             + "VALUES(?,?,?,?,?,?,?,?,?,?,?);";//SQL语句
    //     db1 = new DBHelper(sql);//创建DBHelper对象
    //
    //     try {
    //         for(Position position : list) {
    //             db1.pst.setInt(1, position.getLevel());
    //             db1.pst.setInt(2, position.getPos());
    //             db1.pst.setInt(3, position.getJunction());
    //             db1.pst.setInt(4, position.getDirection());
    //             db1.pst.setInt(5, position.getDistance());
    //             db1.pst.setInt(6, position.getType());
    //             db1.pst.setString(7, position.getLift_area());
    //             db1.pst.setInt(8, position.getJunction2());
    //             db1.pst.setInt(9,position.getDirection2());
    //             db1.pst.setInt(10, position.getDistance2());
    //             db1.pst.setInt(11, position.getType2());
    //             db1.pst.executeUpdate();//执行语句，得到结果集
    //         }
    //         //ret.close();
    //         db1.close();//关闭连接
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    // }
}
