/**
 * Copyright (C),2019-09-04,Galaxis
 * Date:2019/9/4 0004 14:39
 * Author:LELE
 * Description:
 */

package galaxis.lee.junit;


import galaxis.lee.cc.Position;
import galaxis.lee.utils.ConnectionUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)   // 单元测试
public class JunitTest {
    Connection con;
    PreparedStatement preparedStatement;

    @Test
    public void insert() {
        con = ConnectionUtil.conDB();

        List<Position> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Position pt = new Position();
            pt.setLevel(i);
            pt.setDirection2(i);
            pt.setDistance2(i);
            pt.setJunction2(i);
            pt.setType2(i);

            list.add(pt);
        }
        putPosition(list);
        System.out.println("Success");


    }

    public void putPosition(List<Position> list) {
        int i = 0;
        System.out.println("-->插入数据：" + list.size() + "条");
        try {
            String sql = "insert into ga_position(level,pos,junction,direction,distance,type,lift_area,junction2,direction2,distance2,type2) " +
                    "VALUES(?,?,?,?,?,?,?,?,?,?,?);";
            preparedStatement = (PreparedStatement) con.prepareStatement(sql);
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
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void t500W() throws SQLException {
        long startTime = System.currentTimeMillis();

        Connection conn = null;
        try {
            conn = ConnectionUtil.conDB();
            conn.setAutoCommit(false);

            List<Position> list = new ArrayList<>();
            for (int i = 0; i < 100000; i++) {
                Position pt = new Position();
                pt.setLevel(i);
                pt.setDirection2(i);
                pt.setDistance2(i);
                pt.setJunction2(i);
                pt.setType2(i);

                list.add(pt);
            }

            PreparedStatement stmt = conn.prepareStatement("insert into ga_position(level,pos,junction,direction,distance,type,lift_area,junction2,direction2,distance2,type2) VALUES(?,?,?,?,?,?,?,?,?,?,?);");
            System.out.println("数据大小：" + list.size());        //1000000

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
                //注意: 每5万，提交一次;这里不能一次提交过多的数据,我测试了一下，6万5000是极限，6万6000就会出问题，插入的数据量不对。
                if (num > 50000) {
                    stmt.executeBatch();
                    conn.commit();
                    num = 0;
                }
            }
            stmt.executeBatch();
            conn.commit();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
            long endTime = System.currentTimeMillis();
            System.out.println("方法执行时间：" + (endTime - startTime) + "ms");
        }
    }
}
