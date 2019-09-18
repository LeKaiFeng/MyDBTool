package galaxis.lee.cc;

import galaxis.lee.models.Param;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Lee
 * @Date: Created in 14:47 2019/9/17
 */
public class Calculate {
    //定位点生成2.0
    public  List<Position>  addPostion(Param param){
        System.out.println(("定位点生成数据："+(2*param.getBoardCloser_posSpace()+(param.getBeforeFirstBoard_posGroupCount()-1)*param.getColWidth()+(param.getPosCount()*param.getAfterSecondBoard_posGroupCount()-1)*param.getPosSpace())));

        List<Position> list = new ArrayList<Position>();
        for(int i=1;i<=param.getLevelCount();i++) {
            for(int j=1;j<=param.getAiseCount();j++) {
                for(int m1=0;m1<param.getBeforeFirstBoard_posGroupCount()*param.getPosCount();m1++) {
                    Position position = new Position();
                    position.setLevel(i);
                    position.setPos((100*j+10)*1000+100+10*(param.getBeforeFirstBoard_posGroupCount()*param.getPosCount()-m1));
                    position.setJunction(100*j+10);
                    position.setDirection(2);
                    position.setDistance(0-param.getBoardCloser_posSpace()-m1*param.getPosSpace()-param.getColWidth()*(m1/param.getPosCount()));
                    position.setType(4);
                    position.setLift_area("A");
                    if(param.getJunCount()>1) {
                        position.setJunction2(100*j+20);
                        position.setDirection2(2);
                        position.setDistance2(position.getDistance()-(2*param.getBoardCloser_posSpace()+(param.getBeforeFirstBoard_posGroupCount()-1)*param.getColWidth()+(param.getPosCount()*param.getAfterSecondBoard_posGroupCount()-1)*param.getPosSpace()));
                        position.setType2(4);
                    }else {
                        position.setJunction2(0);
                        position.setDirection2(0);
                        position.setDistance2(0);
                        position.setType2(0);
                    }
                    list.add(position);
                }
                for(int m2=0;m2<param.getBetweenFirstBoard_toSecondBoard_posCroupCount()*param.getPosCount();m2++) {
                    Position position = new Position();
                    position.setLevel(i);
                    position.setPos((100*j+10)*1000+100+10*(param.getBeforeFirstBoard_posGroupCount()*param.getPosCount()+m2+1));
                    position.setJunction(100*j+10);
                    position.setDirection(2);
                    position.setDistance(param.getBoardCloser_posSpace()+m2*param.getPosSpace()+param.getColWidth()*(m2/param.getPosCount()));
                    position.setType(4);
                    position.setLift_area("A");
                    if(param.getJunCount()>1) {
                        position.setJunction2(100*j+20);
                        position.setDirection2(2);
                        position.setDistance2(position.getDistance()-(2*param.getBoardCloser_posSpace()+(param.getBeforeFirstBoard_posGroupCount()-1)*param.getColWidth()+(param.getPosCount()*param.getAfterSecondBoard_posGroupCount()-1)*param.getPosSpace()));
                        position.setType2(4);
                    }else {
                        position.setJunction2(0);
                        position.setDirection2(0);
                        position.setDistance2(0);
                        position.setType2(0);
                    }
                    list.add(position);
                }

                for(int m3=0;m3<param.getBetweenFirstBoard_toSecondBoard_posCroupCount()*param.getPosCount();m3++) {
                    Position position = new Position();
                    position.setLevel(i);
                    position.setPos((100*j+10)*1000+100+10*((param.getBeforeFirstBoard_posGroupCount()+param.getBetweenFirstBoard_toSecondBoard_posCroupCount())*param.getPosCount()+m3+1));
                    position.setJunction(100*j+10);
                    position.setDirection(2);
                    position.setDistance((2*param.getBoardCloser_posSpace()+(param.getBeforeFirstBoard_posGroupCount()-1)*param.getColWidth()+(param.getPosCount()*param.getAfterSecondBoard_posGroupCount()-1)*param.getPosSpace())+param.getBoardCloser_posSpace()+m3*param.getPosSpace()+param.getColWidth()*(m3/param.getPosCount()));
                    position.setType(4);
                    position.setLift_area("A");
                    if(param.getJunCount()>1) {
                        position.setJunction2(100*j+20);
                        position.setDirection2(2);
                        position.setDistance2(position.getDistance()-(2*param.getBoardCloser_posSpace()+(param.getBeforeFirstBoard_posGroupCount()-1)*param.getColWidth()+(param.getPosCount()*param.getAfterSecondBoard_posGroupCount()-1)*param.getPosSpace()));
                        position.setType2(4);
                    }else {
                        position.setJunction2(0);
                        position.setDirection2(0);
                        position.setDistance2(0);
                        position.setType2(0);
                    }
                    list.add(position);
                }

            }
        }
        return list;
    }

//    //计算巷道位置
//	public List<Position>  getLocationPosition(Parameter parameter) {
//		List<Position> list = new ArrayList<Position>();
//
//		for(int i=1;i <= parameter.getLevelCount();i++) {
//			for(int j=1;j <=parameter.getAiseCount();j++){
//				for(int m=1;m <=parameter.getBox_count();m++) {
//					Position position = new Position();
//					position.setLevel(i);
//					position.setPos((100*j+10)*1000+100+10*m);
//					position.setJunction(100*j+10);
//					position.setDirection(2);
//					position.setDistance(parameter.getStart_firstBoard()+(m-1)*parameter.getLocationSpace());
//					position.setType(4);
//					position.setLift_area("A");
//					if(parameter.getJunCount()>1) {
//						position.setJunction2(100*j+20);
//						position.setDirection2(2);
//						position.setDistance2(position.getDistance()-parameter.getFirstBoard_secoundBoard());
//						position.setType2(4);
//					}else {
//						position.setJunction2(0);
//						position.setDirection2(0);
//						position.setDistance2(0);
//						position.setType2(0);
//					}
//					list.add(position);
//			}
//		  }
//	    }
//
//		return list;
//	}

    //计算转弯板位置
    public List<Position> getJunctionBoard(Param param) {
        List<Position> list =new ArrayList<Position>();

        for(int i=1;i <= param.getLevelCount();i++) {
            for(int j=1;j <= param.getAiseCount();j++) {
                for(int m=1;m <= param.getJunCount(); m++) {
                    Position position = new Position();
                    position.setPos(100*j+10*m);
                    position.setLevel(i);
                    if(m==1) {
                        position.setDirection(4);
                        position.setJunction(10000);
                        position.setDistance(1+param.getAiseSpace()*(j-1));
                        position.setType(1);
                        position.setLift_area("A");
                        position.setJunction2(0);
                        position.setDirection2(0);
                        position.setDistance2(0);
                        position.setType2(0);
                    }else {
                        position.setDirection(2);
                        position.setJunction(100*j+10*(m-1));
                        position.setDistance(2*param.getBoardCloser_posSpace()+(param.getBeforeFirstBoard_posGroupCount()-1)*param.getColWidth()+(param.getPosCount()*param.getAfterSecondBoard_posGroupCount()-1)*param.getPosSpace());
                        position.setType(1);
                        position.setLift_area("A");
                        position.setJunction2(20000);
                        position.setDirection2(4);
                        position.setDistance2((j-1)*param.getAiseSpace());
                        position.setType2(1);
                    }


                    list.add(position);
                }
            }
        }
        return list;
    }
    //计算货位
    public List<Location> putLocation(List<Position> list){

        List<Location>  li = new ArrayList<Location>();
        for(Position position : list) {
            for(int i =1;i<=2;i++) {
                Location location = new Location();
                location.setLevel(position.getLevel());
                location.setPos(position.getPos());
                location.setLocation(position.getPos()+i);
                location.setAisle(position.getJunction()*10+1);
                location.setState(0);
                location.setArea(position.getLift_area());
                location.setBox_number("");
                location.setPriority(0);
                location.setWeight(0);
                location.setType(0);
                location.setLift_area(position.getLift_area());
                location.setContainer_status(0);

                li.add(location);
            }
        }
        return li;
    }
}
