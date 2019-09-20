package galaxis.lee.controller;

import de.felixroske.jfxsupport.FXMLController;
import de.felixroske.jfxsupport.FXMLView;
import galaxis.lee.cc.Calculate;
import galaxis.lee.cc.Location;
import galaxis.lee.cc.Position;
import galaxis.lee.cc.positionService;
import galaxis.lee.junit.JunitTest;
import galaxis.lee.models.Param;
import galaxis.lee.utils.ConnectionUtil;
import galaxis.lee.utils.DialogBuilder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;

/**
 * @Author: Lee
 * @Date: Created in 12:55 2019/9/20
 * <p>
 * 双转弯板
 */
@FXMLView(value = "/fxml/double.fxml")
@FXMLController
public class DoubleController implements Initializable {

    private static final Logger log = LoggerFactory.getLogger(DoubleController.class);

    @FXML
    private TextField levelCount, aiseCount, junCount, posSpace, aiseSpace, posCount,
            beforeFirstBoard_posGroupCount, betweenFirstBoard_toSecondBoard_posCroupCount,
            afterSecondBoard_posGroupCount, boardCloser_posSpace, colWidth;

    public Connection connection;
    public DBController dbController;
    public HomeController homeController;


    public Map<String, TextField> putMap(){
        Map<String,TextField> map = new HashMap<>();
        map.put("levelCount",levelCount);
        map.put("aiseCount",aiseCount);
        map.put("junCount",junCount);
        map.put("posSpace",posSpace);
        map.put("aiseSpace",aiseSpace);
        map.put("posCount",posCount);
        map.put("beforeFirstBoard_posGroupCount",beforeFirstBoard_posGroupCount);
        map.put("betweenFirstBoard_toSecondBoard_posCroupCount",betweenFirstBoard_toSecondBoard_posCroupCount);
        map.put("afterSecondBoard_posGroupCount",afterSecondBoard_posGroupCount);
        map.put("boardCloser_posSpace",boardCloser_posSpace);
        map.put("colWidth",colWidth);
        return map;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connection = (Connection) ConnectionUtil.conDB();
        dbController = new DBController();
        homeController = new HomeController();
        homeController.setToolTip(putMap());
    }

    @FXML
    void Roadway() {     //巷道位置生成
        Map<String,TextField> map =putMap();
        if (homeController.checkTextField(map)) {
            connection = ConnectionUtil.conDB();
            Param pt = homeController.saveParameter(map);
            Calculate cl = new Calculate();
            List<Position> list = cl.addPostion(pt);
            if (list.size() > 0) {
                //删除 pos 长度为6 的数据
                dbController.delPOS6(connection);
                dbController.putPosition(connection,list);
                // putPosition(list);
                DialogBuilder.alert(levelCount, "巷道位置，执行成功");
            } else {
                DialogBuilder.alert(levelCount, "巷道位置生成失败，请检查输入数据 ！");
            }
        } else {
            DialogBuilder.alert(levelCount, "可以为空，但不能全部为空，且必须填数字");
            return;
        }
    }

    @FXML
    void TurningPlate() {    //转弯板生成
        Map<String,TextField> map =putMap();
        if (homeController.checkTextField(map)) {
            connection = ConnectionUtil.conDB();
            // positionService ps = new positionService();
            Param pt = homeController.saveParameter(map);

            Calculate cl = new Calculate();
            List<Position> list = cl.getJunctionBoard(pt);
            if (list.size() <= 0) {
                DialogBuilder.alert(levelCount, "转弯板生成失败，请检查输入数据");
                return;
            }
            //清除 pos 长度大于3且小于 6 的数据，否则会主键冲突
            dbController.delPOS3(connection);
            // delPOS3();
            dbController.putPosition(connection,list);
            // putPosition(list);
            DialogBuilder.alert(levelCount, "转弯半径，执行成功");
        } else {
            DialogBuilder.alert(levelCount, "可以为空，但不能全部为空，且必须填数字");
            return;
        }
    }

    @FXML
    void CleanPositions() {      //清空 position 表数据
        // connection = ConnectionUtil.conDB();
        if (dbController.selectDBCount(connection) <= 0) {
            DialogBuilder.alert(levelCount, "ga_position表中无数据,无需删除");
            // closeConn(connection);
            return;
        }
        try {

          dbController.delPosition(connection);
            // fetRowList();
            DialogBuilder.alert(levelCount, "ga_position表已清空");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void CleanLocations() {      //清空 location表数据
        if (dbController.selectLocationCount(connection) <= 0) {
            DialogBuilder.alert(levelCount, "ga_location表中无数据,无需删除");
            // closeConn(connection);
            return;
        }
        try {
            dbController.delLocation(connection);
            // fetRowList();
            DialogBuilder.alert(levelCount, "ga_locations表已清空");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void HandleLocation() {      //货位生成
        // connection = ConnectionUtil.conDB();
        //-->location
        // positionService ps = new positionService();
        List<Position> list = dbController.getPosition(connection);
        if (list.size() <= 0) {
            log.info("position表中找不到 type=4 && pos>10000 的数据");
            // System.out.println("position表中找不到 type=4 && pos>10000 的数据");
            DialogBuilder.alert(levelCount, "货位生成失败！");
            return;
        }
        int count = dbController.selectLocationCount(connection);
        if (count >= 1) {
            dbController.delLocation(connection);
            // delLocation();
            log.info( "ga_location 表已清空 " + count + " 条数据");
            // System.out.println("ga_location 表已清空 " + count + " 条数据");
            // DialogBuilder.alert(levelCount, "ga_locations表中有 "+selectLocationCount()+" 条数据，得先清空");
            // return;
        }
        dbController.putLocation(connection,new Calculate().putLocation(list));
        // putLocation(new Calculate().putLocation(list));
        DialogBuilder.alert(levelCount, "ga_locations表 货位生成成功");
        // closeConn(connection);
    }


}
