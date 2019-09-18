/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package galaxis.lee.controller;


import de.felixroske.jfxsupport.FXMLController;
import de.felixroske.jfxsupport.FXMLView;
import galaxis.lee.cc.Calculate;
import galaxis.lee.cc.Location;
import galaxis.lee.cc.Position;
import galaxis.lee.cc.positionService;
import galaxis.lee.models.Param;
import galaxis.lee.utils.ConnectionUtil;
import galaxis.lee.utils.DialogBuilder;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import org.apache.commons.lang3.StringUtils;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author oXCToo
 */
@FXMLView(value = "/fxml/OnBoard.fxml")
@FXMLController
public class HomeController implements Initializable {

    @FXML
    private TextField levelCount, aiseCount, junCount, posSpace, aiseSpace,posCount,
            beforeFirstBoard_posGroupCount, betweenFirstBoard_toSecondBoard_posCroupCount,
            afterSecondBoard_posGroupCount,boardCloser_posSpace,colWidth;

    PreparedStatement preparedStatement;
    Connection connection;

    public HomeController() {
        connection = (Connection) ConnectionUtil.conDB();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setToolTip();
    }

    /**
     * 转弯半径生成
     * @param event
     */
    @FXML
    private void HandleInsertParameterEvents(MouseEvent event) {
        if (checkTextField()) {
            connection = ConnectionUtil.conDB();
            positionService ps = new positionService();
            Param pt = saveParameter();

            Calculate cl = new Calculate();
            List<Position> list =  cl.getJunctionBoard(pt);
            if(list.size()<=0){
                DialogBuilder.alert(levelCount, "生成转弯半径失败，请检查输入数据");
                return;
            }
            //清除 pos 长度大于3且小于 6 的数据，否则会主键冲突
            delPOS3();
            putPosition(list);
            DialogBuilder.alert(levelCount, "转弯半径，执行成功");
        } else {
            DialogBuilder.alert(levelCount, "可以为空，但不能全部为空，且必须填数字");
            return;
        }
    }
    // 删除 pos [100~99999]
    public void delPOS3(){
        String pos3 = " delete from ga_position where pos>=100 and pos<=99999";
        try {
            PreparedStatement stmt = connection.prepareStatement(pos3);
            int result = stmt.executeUpdate();
            System.out.println("delete ga_position pos>=100 && pos<=99999 data result: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*可以为null  但必须填数字*/
    private boolean isNullOrNumeric(String str) {
        if (!StringUtils.isEmpty(str)) {
            // StringUtils.isNumeric(str) //该方法不能判断负数
            Boolean strResult = str.matches("^[-\\+]?([0-9]+\\.?)?[0-9]+$");
            if (strResult == true ) {
                Double num = Double.parseDouble(str);
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    private boolean checkTextField() {
        //如果全部为空，则不通过
        if (StringUtils.isEmpty(levelCount.getText()) && StringUtils.isEmpty(aiseCount.getText())
                && StringUtils.isEmpty(junCount.getText()) && StringUtils.isEmpty(posSpace.getText())
                && StringUtils.isEmpty(aiseSpace.getText()) && StringUtils.isEmpty(posCount.getText())
                && StringUtils.isEmpty(beforeFirstBoard_posGroupCount.getText()) && StringUtils.isEmpty(betweenFirstBoard_toSecondBoard_posCroupCount.getText())
                && StringUtils.isEmpty(afterSecondBoard_posGroupCount.getText() ) && StringUtils.isEmpty(boardCloser_posSpace.getText())&&StringUtils.isEmpty(colWidth.getText())) {
            return false;
        }
        //有一个不为null
        if (isNullOrNumeric(levelCount.getText()) && isNullOrNumeric(aiseCount.getText())
                && isNullOrNumeric(junCount.getText()) && isNullOrNumeric(posSpace.getText())
                && isNullOrNumeric(aiseSpace.getText()) && isNullOrNumeric(posCount.getText())
                && isNullOrNumeric(beforeFirstBoard_posGroupCount.getText()) && isNullOrNumeric(betweenFirstBoard_toSecondBoard_posCroupCount.getText())
                && isNullOrNumeric(afterSecondBoard_posGroupCount.getText()) && isNullOrNumeric(boardCloser_posSpace.getText()) && isNullOrNumeric(colWidth.getText())) {
            return true;
        }
        return false;
    }
    /*巷道位置生成*/
    @FXML
    private void HandleParameterEvents(MouseEvent event) {
        if (checkTextField()) {
            connection = ConnectionUtil.conDB();
            Param pt = saveParameter();
            Calculate cl = new Calculate();
            List<Position> list = cl.addPostion(pt);
            if(list.size()>0){
                //删除 pos 长度为6 的数据
                delPOS6();
                putPosition(list);
                DialogBuilder.alert(levelCount, "巷道位置，执行成功");
            }else{
                DialogBuilder.alert(levelCount, "巷道位置生成失败，请检查输入数据 ！");
            }
        } else {
            DialogBuilder.alert(levelCount, "可以为空，但不能全部为空，且必须填数字");
            return;
        }
    }
    // 删除 pos (99999，~）
    public void delPOS6(){
        String pos6 = " delete from ga_position where pos>99999";
        try {
            PreparedStatement stmt = connection.prepareStatement(pos6);
            int result6 = stmt.executeUpdate();
            System.out.println("delete ga_position pos>99999 data result: " + result6);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //货位生成
    @FXML
    private void HandleLocation(MouseEvent event) {
        connection = ConnectionUtil.conDB();
        //-->location
        // positionService ps = new positionService();
        List<Position> list = getPosition();
        if(list.size()<=0){
            DialogBuilder.alert(levelCount,"货位生成失败！");
            return;
        }
        int count =selectLocationCount();
        if(count>=1){
            delLocation();
            System.out.println("ga_location 表已清空 "+count +" 条数据");
            // DialogBuilder.alert(levelCount, "ga_locations表中有 "+selectLocationCount()+" 条数据，得先清空");
            // return;
        }
        putLocation(new Calculate().putLocation(list));
        DialogBuilder.alert(levelCount, "ga_locations表 货位配置成功");
        closeConn(connection);
    }
    static  String sql = null;
    ResultSet ret = null;
    public List<Position> getPosition() {
        // TODO Auto-generated method stub
        sql = "select * from ga_position where type=4 and type2=4 ";
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

    public void putLocation(List<Location> list) {
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
            connection.commit();
            // db1.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /*删除错误数据*/
    @FXML
    private void HandleDelParameterEvents(MouseEvent event) {

        connection = ConnectionUtil.conDB();
        if (selectDBCount() <=0) {
            DialogBuilder.alert(levelCount, "表中无数据,无需删除");
            closeConn(connection);
            return;
        }
        try {
            String sql = "truncate ga_position";
            preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            // fetRowList();
            DialogBuilder.alert(levelCount, "成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    /*String 转 int*/
    public int selfParse(String str) {
        if (StringUtils.isEmpty(str) || !StringUtils.isNumeric(str)) {
            return 0;
        }
        return Integer.parseInt(str);
    }

    /**
     * 获取parameter 界面所有的数据
     *
     * @return
     */
    private Param saveParameter() {
        Param pt = new Param();

        pt.setLevelCount(selfParse(levelCount.getText()));
        pt.setAiseCount(selfParse(aiseCount.getText()));
        pt.setJunCount(selfParse(junCount.getText()));
        pt.setPosSpace(selfParse(posSpace.getText()));
        pt.setAiseSpace(selfParse(aiseSpace.getText()));
        pt.setPosCount(selfParse(posCount.getText()));
        pt.setBeforeFirstBoard_posGroupCount(selfParse(beforeFirstBoard_posGroupCount.getText()));
        pt.setBetweenFirstBoard_toSecondBoard_posCroupCount(selfParse(betweenFirstBoard_toSecondBoard_posCroupCount.getText()));
        pt.setAfterSecondBoard_posGroupCount(selfParse(afterSecondBoard_posGroupCount.getText()));
        pt.setBoardCloser_posSpace(selfParse(boardCloser_posSpace.getText()));
        pt.setColWidth(selfParse(colWidth.getText()));
        return pt;
    }

    /**
     * parameter 鼠标悬浮提示
     */
    private void setToolTip() {
        levelCount.setTooltip(new Tooltip("立体库总层数"));
        aiseCount.setTooltip(new Tooltip("每一层的巷道数"));
        junCount.setTooltip(new Tooltip("转弯板数量"));
        posSpace.setTooltip(new Tooltip("定位点之间的间距"));
        aiseSpace.setTooltip(new Tooltip("巷道间距"));
        posCount.setTooltip(new Tooltip("每一组定位点的个数"));
        beforeFirstBoard_posGroupCount.setTooltip(new Tooltip("第一块转弯板前定位点组数"));
        betweenFirstBoard_toSecondBoard_posCroupCount.setTooltip(new Tooltip("两块转弯板之间定位点组数"));
        afterSecondBoard_posGroupCount.setTooltip(new Tooltip("第二块转弯板后定位点组数"));
        boardCloser_posSpace.setTooltip(new Tooltip("距离转弯板最近的定位点之间的间距"));
        colWidth.setTooltip(new Tooltip("立柱宽度"));
    }



    private ObservableList<ObservableList> data;
    String SQL = "SELECT * from wip_users";
    // String SQL = "SELECT * from ga_position";


    /**
     * 将生成的position数据  插入数据库
     * @param list
     */
    public void putPosition(List<Position> list) {

        // for(Position p : list){
        //     System.out.println("-->  "+p.toString());
        // }
        long startTime = System.currentTimeMillis();
        try {
            connection.setAutoCommit(false);
            PreparedStatement stmt = connection.prepareStatement("insert into ga_position(level,pos,junction,direction,distance,type,lift_area,junction2,direction2,distance2,type2) VALUES(?,?,?,?,?,?,?,?,?,?,?);");
            System.out.println("生成数据大小：" + list.size());        //1000000

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
                // if (num > 50000) {
                //     stmt.executeBatch();
                //     connection.commit();
                //     num = 0;
                // }

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
            closeConn(connection);
            long endTime = System.currentTimeMillis();
            System.out.println("ga_position表插入"+list.size()+"条数据，执行时间：" + (endTime - startTime) + "ms");
        }
    }

    public int selectDBCount() {
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
    public int selectLocationCount() {
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
    public void delLocation(){
        try {
            String sqlc = "truncate ga_locations";
            preparedStatement = (PreparedStatement) connection.prepareStatement(sqlc);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeConn(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
