package hbc315.HIDC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import hbc315.HIDC.service.QueryService;


/**
 * @author zcy
 */

@RestController
@RequestMapping("/qurey")
public class QueryController {

	@Autowired
	QueryService queryService;

	/**
	 * 查询手机半年内通话详单
	 * @param mobile
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	public String getToken(@RequestBody String mobile) {
		
		queryService.task("18513068661","090802");
		
		return "ok";
	}
}
