package demo.service.impl;
import org.springframework.stereotype.Service;
import demo.service.IwomenService;
@Service
public class womenJapanServiceImpl implements IwomenService {

	@Override
	public void jiehun(String name) {
	   System.out.println(name+" 和 岗村林茨 结婚！");
	}

	@Override
	public String getMan(String name) {
		return "岗村林茨";
	}

	

}
