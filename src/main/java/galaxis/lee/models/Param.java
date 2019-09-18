package galaxis.lee.models;

/**
 * @Author: Lee
 * @Date: Created in 14:44 2019/9/17
 */
public class Param {

    public int levelCount;//立体库总层数

    public int aiseCount;//每一层的巷道数

    public int junCount;//转弯板数量

    public int posSpace;//定位点直接的间距

    public int aiseSpace;//巷道间距

    public int  posCount;//每一组定位点的个数

    public int beforeFirstBoard_posGroupCount;//第一块转弯板前定位点组数

    public int betweenFirstBoard_toSecondBoard_posCroupCount;//两块转弯板直接定位点组数

    public int afterSecondBoard_posGroupCount;//第二块转弯板后定位点组数

    public int boardCloser_posSpace;//距离转弯板最近的定位点直接的间距

    public int colWidth;//立柱宽度

    public int getLevelCount() {
        return levelCount;
    }

    public void setLevelCount(int levelCount) {
        this.levelCount = levelCount;
    }

    public int getAiseCount() {
        return aiseCount;
    }

    public void setAiseCount(int aiseCount) {
        this.aiseCount = aiseCount;
    }

    public int getJunCount() {
        return junCount;
    }

    public void setJunCount(int junCount) {
        this.junCount = junCount;
    }

    public int getPosSpace() {
        return posSpace;
    }

    public void setPosSpace(int posSpace) {
        this.posSpace = posSpace;
    }

    public int getAiseSpace() {
        return aiseSpace;
    }

    public void setAiseSpace(int aiseSpace) {
        this.aiseSpace = aiseSpace;
    }

    public int getPosCount() {
        return posCount;
    }

    public void setPosCount(int posCount) {
        this.posCount = posCount;
    }

    public int getBeforeFirstBoard_posGroupCount() {
        return beforeFirstBoard_posGroupCount;
    }

    public void setBeforeFirstBoard_posGroupCount(int beforeFirstBoard_posGroupCount) {
        this.beforeFirstBoard_posGroupCount = beforeFirstBoard_posGroupCount;
    }

    public int getBetweenFirstBoard_toSecondBoard_posCroupCount() {
        return betweenFirstBoard_toSecondBoard_posCroupCount;
    }

    public void setBetweenFirstBoard_toSecondBoard_posCroupCount(int betweenFirstBoard_toSecondBoard_posCroupCount) {
        this.betweenFirstBoard_toSecondBoard_posCroupCount = betweenFirstBoard_toSecondBoard_posCroupCount;
    }

    public int getAfterSecondBoard_posGroupCount() {
        return afterSecondBoard_posGroupCount;
    }

    public void setAfterSecondBoard_posGroupCount(int afterSecondBoard_posGroupCount) {
        this.afterSecondBoard_posGroupCount = afterSecondBoard_posGroupCount;
    }

    public int getBoardCloser_posSpace() {
        return boardCloser_posSpace;
    }

    public void setBoardCloser_posSpace(int boardCloser_posSpace) {
        this.boardCloser_posSpace = boardCloser_posSpace;
    }

    public int getColWidth() {
        return colWidth;
    }

    public void setColWidth(int colWidth) {
        this.colWidth = colWidth;
    }
}
