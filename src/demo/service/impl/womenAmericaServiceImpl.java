package demo.service.impl;

import org.springframework.stereotype.Service;

/*@Service 用于标注业务层组件*/
import demo.service.IwomenService;
@Service("womenAmericaServiceImpl")
public class womenAmericaServiceImpl implements IwomenService {

	@Override
	public void jiehun(String name) {
		System.out.println(name+" 和 bushi 结婚！");
	}

	@Override
	public String getMan(String name) {
		return "bushi";
	}


}
