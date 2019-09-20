/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package galaxis.lee.controller;


import de.felixroske.jfxsupport.FXMLController;
import de.felixroske.jfxsupport.FXMLView;
import galaxis.lee.LeeApplication;
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
import javafx.scene.control.Control;
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
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 * @Author: Lee
 * @Date: Created in 15:12 2019/9/20
 */
@FXMLView(value = "/fxml/OnBoard2.fxml")
@FXMLController
public class HomeController implements Initializable {
    private static final Logger logger = Logger.getLogger(HomeController.class.getName());
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    private String levelCount;
    private String aiseCount;
    private String junCount;
    private String posSpace;
    private String aiseSpace;
    private String posCount;
    private String beforeFirstBoard_posGroupCount;
    private String betweenFirstBoard_toSecondBoard_posCroupCount;
    private String afterSecondBoard_posGroupCount;
    private String boardCloser_posSpace;
    private String colWidth;
    /**
     * parameter 鼠标悬浮提示
     */
    public void setToolTip(Map<String,TextField> map) {
        map.get("levelCount").setTooltip(new Tooltip("立体库总层数"));
        map.get("aiseCount").setTooltip(new Tooltip("每一层的巷道数"));
        map.get("junCount").setTooltip(new Tooltip("转弯板数量"));
        map.get("posSpace").setTooltip(new Tooltip("定位点之间的间距"));
        map.get("aiseSpace").setTooltip(new Tooltip("巷道间距"));
        map.get("posCount").setTooltip(new Tooltip("每一组定位点的个数"));
        map.get("beforeFirstBoard_posGroupCount").setTooltip(new Tooltip("第一块转弯板前定位点组数"));
        map.get("betweenFirstBoard_toSecondBoard_posCroupCount").setTooltip(new Tooltip("两块转弯板之间定位点组数"));
        map.get("afterSecondBoard_posGroupCount").setTooltip(new Tooltip("第二块转弯板后定位点组数"));
        map.get("boardCloser_posSpace").setTooltip(new Tooltip("距离转弯板最近的定位点之间的间距"));
        map.get("colWidth").setTooltip(new Tooltip("定位点组之间的间距"));
    }

    public void getMap(Map<String,TextField> map){
        levelCount = map.get("levelCount").getText();
        aiseCount = map.get("aiseCount").getText();
        junCount = map.get("junCount").getText();
        posSpace = map.get("posSpace").getText();
        aiseSpace = map.get("aiseSpace").getText();
        posCount = map.get("posCount").getText();
        beforeFirstBoard_posGroupCount = map.get("beforeFirstBoard_posGroupCount").getText();
        betweenFirstBoard_toSecondBoard_posCroupCount = map.get("betweenFirstBoard_toSecondBoard_posCroupCount").getText();
        afterSecondBoard_posGroupCount = map.get("afterSecondBoard_posGroupCount").getText();
        boardCloser_posSpace = map.get("boardCloser_posSpace").getText();
        colWidth = map.get("colWidth").getText();
    }

    /**
     * 获取parameter 界面所有的数据
     *
     * @return
     */
    public Param saveParameter(Map<String,TextField> map) {
        Param pt = new Param();
        getMap(map);

        pt.setLevelCount(selfParse(levelCount));
        pt.setAiseCount(selfParse(aiseCount));
        pt.setJunCount(selfParse(junCount));
        pt.setPosSpace(selfParse(posSpace));
        pt.setAiseSpace(selfParse(aiseSpace));
        pt.setPosCount(selfParse(posCount));
        pt.setBeforeFirstBoard_posGroupCount(selfParse(beforeFirstBoard_posGroupCount));
        pt.setBetweenFirstBoard_toSecondBoard_posCroupCount(selfParse(betweenFirstBoard_toSecondBoard_posCroupCount));
        pt.setAfterSecondBoard_posGroupCount(selfParse(afterSecondBoard_posGroupCount));
        pt.setBoardCloser_posSpace(selfParse(boardCloser_posSpace));
        pt.setColWidth(selfParse(colWidth));
        return pt;
    }

    /*String 转 int*/
    public int selfParse(String str) {
        if (StringUtils.isEmpty(str)) {
            return 0;
        }
        return Integer.parseInt(str);
    }

    //检查 输入数据
    public boolean checkTextField(Map<String,TextField> map) {
        getMap(map);
        //如果全部为空，则不通过
        if (StringUtils.isEmpty(levelCount) && StringUtils.isEmpty(aiseCount)
                && StringUtils.isEmpty(junCount) && StringUtils.isEmpty(posSpace)
                && StringUtils.isEmpty(aiseSpace) && StringUtils.isEmpty(posCount)
                && StringUtils.isEmpty(beforeFirstBoard_posGroupCount) && StringUtils.isEmpty(betweenFirstBoard_toSecondBoard_posCroupCount)
                && StringUtils.isEmpty(afterSecondBoard_posGroupCount) && StringUtils.isEmpty(boardCloser_posSpace) && StringUtils.isEmpty(colWidth)) {
            return false;
        }
        //至少 有一个不为null 的数字
        if (isNullOrNumeric(levelCount) && isNullOrNumeric(aiseCount)
                && isNullOrNumeric(junCount) && isNullOrNumeric(posSpace)
                && isNullOrNumeric(aiseSpace) && isNullOrNumeric(posCount)
                && isNullOrNumeric(beforeFirstBoard_posGroupCount) && isNullOrNumeric(betweenFirstBoard_toSecondBoard_posCroupCount)
                && isNullOrNumeric(afterSecondBoard_posGroupCount) && isNullOrNumeric(boardCloser_posSpace) && isNullOrNumeric(colWidth)) {
            return true;
        }
        return false;
    }

    /*可以为null  但必须填数字，包括负数*/
    public boolean isNullOrNumeric(String str) {
        if (!StringUtils.isEmpty(str)) {
            // StringUtils.isNumeric(str) //该方法不能判断负数
            Boolean strResult = str.matches("^[-\\+]?([0-9]+\\.?)?[0-9]+$");
            if (strResult == true) {
                Double num = Double.parseDouble(str);
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    /*巷道位置生成*/
    public void RoadwayCommon(){

    }



}
