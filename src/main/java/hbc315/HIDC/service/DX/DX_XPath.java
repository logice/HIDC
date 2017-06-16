package hbc315.HIDC.service.DX;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class DX_XPath {

	private static int BILL_PAGE = 50;
	
	public int readResult(InputStream inputResult){
		int totlePage = -1;
		try {			
			// 解析文件，生成document对象
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document document = builder.parse(inputResult);
			
			// 生成XPath对象
			XPath xpath = XPathFactory.newInstance().newXPath();
			
			//取通话记录的list
			NodeList calls = (NodeList) xpath.evaluate("/html/table[@class='ued-table']/tr", document,XPathConstants.NODESET);
			
			//取一共页数
			String number = (String) xpath.evaluate("/html/table[@class='ued-table']/tr[@class='trlast']/td/div/label[@class='fl']/span/text()", document,XPathConstants.STRING);
			totlePage = Integer.valueOf(number)/BILL_PAGE + 1;
			
			//遍历通话记录，存入map
			log(calls);
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return totlePage;
		
	}
	
	public void log(NodeList calls){
		
		JSONArray jsonArray = new JSONArray();
		
		for (int i = 1; i < calls.getLength()-2; i++) {
			Node call = calls.item(i);
			NodeList childNodes = call.getChildNodes();
			
			//读取一条信息到BillModel中
			JSONObject json = new JSONObject();
			int index = 0;
			
			for(int j = 0 ;j<childNodes.getLength();j++){
				if(childNodes.item(j).getNodeType()==Node.ELEMENT_NODE && !childNodes.item(j).getNodeName().equals("#text")){  
					//System.out.println(childNodes.item(j).getTextContent());
					
					switch(index){
					case 0:
						json.put("序号", Integer.valueOf(childNodes.item(j).getTextContent()));
						break;
					case 1:
						json.put("呼叫类型", childNodes.item(j).getTextContent());
						break;
					case 2:
						json.put("通话类型", childNodes.item(j).getTextContent());
						break;
					case 3:
						json.put("通话地点", childNodes.item(j).getTextContent());
						break;
					case 4:
						json.put("对方号码", childNodes.item(j).getTextContent());
						break;
					case 5:
						json.put("通话开始时间", childNodes.item(j).getTextContent());
						break;
					case 6:
						json.put("基本通话费", childNodes.item(j).getTextContent());
						break;
					case 7:
						json.put("长途通话费", childNodes.item(j).getTextContent());
						break;
					case 8:
						json.put("通话时长(秒)", childNodes.item(j).getTextContent());
						break;
					case 9:
						json.put("费用小计（元）", childNodes.item(j).getTextContent());
						break;
					}
					index++;
				}	
			}
			jsonArray.add(json);
		}
		//取一共页数
		Node call = calls.item(calls.getLength()-2);
		NodeList childNodes = call.getChildNodes();
		for(int j = 0 ;j<childNodes.getLength();j++){
			if(childNodes.item(j).getNodeType()==Node.ELEMENT_NODE && !childNodes.item(j).getNodeName().equals("")){  
				
			}			
		}
		
		System.out.println(jsonArray.toString());
	}
	
//	public static void main(String args[]) throws UnsupportedEncodingException{
//		String temp = "<html><h4 class=\"ued-title-3\">查询结果</h4><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" class=\"ued-table-nobor\">			<tr>			<th>客户号码：</th>			<td class=\"tl color-6\"> 18911206086</td>			<th>客户名称：</th>			<td class=\"tl color-6\">北京微梦创科网络技术有限公司</td>			<th>总费用：</th>			<td class=\"tl color-6\">￥ 0.00</td>		</tr>			</table><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" class=\"ued-table\">			<tr>			<th>序号</th>			<th>呼叫类型</th>			<th>通话类型</th>			<th>通话地点</th>			<th>对方号码</th>			<th>通话开始时间</th>			<th>基本通话费</th>			<th>长途通话费</th>			<th>通话时长(秒)</th>			<th>费用小计（元）</th>		</tr>									<tr>					<td>1</td>					<td>被叫</td>					<td>本地</td>					<td>北京</td>					<td>18901185861</td>					<td>2016-04-01 09:01:50</td>					<td>0.00</td>					<td>0.00</td>					<td>122</td>					<td>0.00</td>				</tr>							<tr>					<td>2</td>					<td>被叫</td>					<td>本地</td>					<td>北京</td>					<td>13552416399</td>					<td>2016-04-01 15:38:45</td>					<td>0.00</td>					<td>0.00</td>					<td>19</td>					<td>0.00</td>				</tr>							<tr>					<td>3</td>					<td>被叫</td>					<td>本地</td>					<td>北京</td>					<td>13552416399</td>					<td>2016-04-01 16:08:09</td>					<td>0.00</td>					<td>0.00</td>					<td>7</td>					<td>0.00</td>				</tr>							<tr>					<td>4</td>					<td>被叫</td>					<td>本地</td>					<td>北京</td>					<td>13910500078</td>					<td>2016-04-02 12:19:41</td>					<td>0.00</td>					<td>0.00</td>					<td>17</td>					<td>0.00</td>				</tr>							<tr>					<td>5</td>					<td>主叫</td>					<td>本地</td>					<td>北京</td>					<td>18610662429</td>					<td>2016-04-02 14:26:42</td>					<td>0.00</td>					<td>0.00</td>					<td>227</td>					<td>0.00</td>				</tr>							<tr>					<td>6</td>					<td>主叫</td>					<td>本地</td>					<td>北京</td>					<td>13911263189</td>					<td>2016-04-02 16:11:10</td>					<td>0.00</td>					<td>0.00</td>					<td>29</td>					<td>0.00</td>				</tr>							<tr>					<td>7</td>					<td>主叫</td>					<td>本地</td>					<td>北京</td>					<td>18910017600</td>					<td>2016-04-03 10:51:43</td>					<td>0.00</td>					<td>0.00</td>					<td>25</td>					<td>0.00</td>				</tr>							<tr>					<td>8</td>					<td>主叫</td>					<td>本地</td>					<td>北京</td>					<td>18910017600</td>					<td>2016-04-03 11:16:41</td>					<td>0.00</td>					<td>0.00</td>					<td>15</td>					<td>0.00</td>				</tr>							<tr>					<td>9</td>					<td>主叫</td>					<td>国内长途</td>					<td>北京</td>					<td>15849313052</td>					<td>2016-04-03 11:18:05</td>					<td>0.00</td>					<td>0.00</td>					<td>35</td>					<td>0.00</td>				</tr>							<tr>					<td>10</td>					<td>主叫</td>					<td>国内长途</td>					<td>北京</td>					<td>15849313052</td>					<td>2016-04-03 11:23:12</td>					<td>0.00</td>					<td>0.00</td>					<td>38</td>					<td>0.00</td>				</tr>							<tr>					<td>11</td>					<td>被叫</td>					<td>本地</td>					<td>北京</td>					<td>18910017600</td>					<td>2016-04-03 11:28:41</td>					<td>0.00</td>					<td>0.00</td>					<td>1</td>					<td>0.00</td>				</tr>							<tr>					<td>12</td>					<td>主叫</td>					<td>本地</td>					<td>北京</td>					<td>18910017600</td>					<td>2016-04-03 11:29:47</td>					<td>0.00</td>					<td>0.00</td>					<td>40</td>					<td>0.00</td>				</tr>							<tr>					<td>13</td>					<td>主叫</td>					<td>本地</td>					<td>北京</td>					<td>18910017600</td>					<td>2016-04-03 11:30:59</td>					<td>0.00</td>					<td>0.00</td>					<td>30</td>					<td>0.00</td>				</tr>							<tr>					<td>14</td>					<td>主叫</td>					<td>本地</td>					<td>北京</td>					<td>18910017600</td>					<td>2016-04-03 11:57:57</td>					<td>0.00</td>					<td>0.00</td>					<td>11</td>					<td>0.00</td>				</tr>							<tr>					<td>15</td>					<td>主叫</td>					<td>国内长途</td>					<td>北京</td>					<td>13213631592</td>					<td>2016-04-03 12:51:54</td>					<td>0.00</td>					<td>0.00</td>					<td>88</td>					<td>0.00</td>				</tr>							<tr>					<td>16</td>					<td>被叫</td>					<td>国内长途</td>					<td>北京</td>					<td>13213631592</td>					<td>2016-04-03 12:55:08</td>					<td>0.00</td>					<td>0.00</td>					<td>18</td>					<td>0.00</td>				</tr>							<tr>					<td>17</td>					<td>被叫</td>					<td>本地</td>					<td>北京</td>					<td>15801032636</td>					<td>2016-04-03 13:10:38</td>					<td>0.00</td>					<td>0.00</td>					<td>28</td>					<td>0.00</td>				</tr>							<tr>					<td>18</td>					<td>主叫</td>					<td>本地</td>					<td>北京</td>					<td>18910017600</td>					<td>2016-04-03 15:31:15</td>					<td>0.00</td>					<td>0.00</td>					<td>7</td>					<td>0.00</td>				</tr>							<tr>					<td>19</td>					<td>被叫</td>					<td>本地</td>					<td>北京</td>					<td>18910017600</td>					<td>2016-04-03 16:07:55</td>					<td>0.00</td>					<td>0.00</td>					<td>73</td>					<td>0.00</td>				</tr>							<tr>					<td>20</td>					<td>主叫</td>					<td>本地</td>					<td>北京</td>					<td>18910017600</td>					<td>2016-04-03 16:11:21</td>					<td>0.00</td>					<td>0.00</td>					<td>10</td>					<td>0.00</td>				</tr>							<tr>					<td>21</td>					<td>主叫</td>					<td>本地</td>					<td>北京</td>					<td>15801032636</td>					<td>2016-04-03 16:37:49</td>					<td>0.00</td>					<td>0.00</td>					<td>79</td>					<td>0.00</td>				</tr>							<tr>					<td>22</td>					<td>被叫</td>					<td>国内长途</td>					<td>北京</td>					<td>09132190154</td>					<td>2016-04-03 17:16:53</td>					<td>0.00</td>					<td>0.00</td>					<td>600</td>					<td>0.00</td>				</tr>							<tr>					<td>23</td>					<td>被叫</td>					<td>国内长途</td>					<td>北京</td>					<td>09132190472</td>					<td>2016-04-03 17:27:24</td>					<td>0.00</td>					<td>0.00</td>					<td>599</td>					<td>0.00</td>				</tr>							<tr>					<td>24</td>					<td>主叫</td>					<td>本地</td>					<td>北京</td>					<td>18910017600</td>					<td>2016-04-03 19:41:52</td>					<td>0.00</td>					<td>0.00</td>					<td>5</td>					<td>0.00</td>				</tr>							<tr>					<td>25</td>					<td>主叫</td>					<td>本地</td>					<td>北京</td>					<td>01088810010</td>					<td>2016-04-04 09:47:32</td>					<td>0.00</td>					<td>0.00</td>					<td>19</td>					<td>0.00</td>				</tr>							<tr>					<td>26</td>					<td>被叫</td>					<td>本地</td>					<td>北京</td>					<td>01088810010</td>					<td>2016-04-04 10:34:06</td>					<td>0.00</td>					<td>0.00</td>					<td>143</td>					<td>0.00</td>				</tr>							<tr>					<td>27</td>					<td>被叫</td>					<td>本地</td>					<td>北京</td>					<td>13466362263</td>					<td>2016-04-04 11:15:44</td>					<td>0.00</td>					<td>0.00</td>					<td>263</td>					<td>0.00</td>				</tr>							<tr>					<td>28</td>					<td>被叫</td>					<td>本地</td>					<td>北京</td>					<td>17601663150</td>					<td>2016-04-04 11:57:16</td>					<td>0.00</td>					<td>0.00</td>					<td>119</td>					<td>0.00</td>				</tr>							<tr>					<td>29</td>					<td>主叫</td>					<td>国内长途</td>					<td>北京</td>					<td>13903881712</td>					<td>2016-04-04 14:56:46</td>					<td>0.00</td>					<td>0.00</td>					<td>520</td>					<td>0.00</td>				</tr>							<tr>					<td>30</td>					<td>被叫</td>					<td>??地</td>					<td>北京</td>					<td>08610010</td>					<td>2016-04-05 09:11:52</td>					<td>0.00</td>					<td>0.00</td>					<td>54</td>					<td>0.00</td>				</tr>							<tr>					<td>31</td>					<td>主叫</td>					<td>本地</td>					<td>北京</td>					<td>18910017600</td>					<td>2016-04-05 09:16:40</td>					<td>0.00</td>					<td>0.00</td>					<td>37</td>					<td>0.00</td>				</tr>							<tr>					<td>32</td>					<td>被叫</td>					<td>本地</td>					<td>北京</td>					<td>13426026416</td>					<td>2016-04-05 17:04:23</td>					<td>0.00</td>					<td>0.00</td>					<td>15</td>					<td>0.00</td>				</tr>							<tr>					<td>33</td>					<td>主叫</td>					<td>本地</td>					<td>北京</td>					<td>13426026416</td>					<td>2016-04-05 17:05:04</td>					<td>0.00</td>					<td>0.00</td>					<td>24</td>					<td>0.00</td>				</tr>							<tr>					<td>34</td>					<td>被叫</td>					<td>本地</td>					<td>北京</td>					<td>13910023969</td>					<td>2016-04-05 22:06:40</td>					<td>0.00</td>					<td>0.00</td>					<td>19</td>					<td>0.00</td>				</tr>							<tr>					<td>35</td>					<td>被叫</td>					<td>本地</td>					<td>北京</td>					<td>13910023969</td>					<td>2016-04-05 22:09:31</td>					<td>0.00</td>					<td>0.00</td>					<td>9</td>					<td>0.00</td>				</tr>							<tr>					<td>36</td>					<td>主叫</td>					<td>本地</td>					<td>北京</td>					<td>18910017600</td>					<td>2016-04-05 22:48:39</td>					<td>0.00</td>					<td>0.00</td>					<td>9</td>					<td>0.00</td>				</tr>							<tr>					<td>37</td>					<td>主叫</td>					<td>本地</td>					<td>北京</td>					<td>01085192337</td>					<td>2016-04-06 09:17:08</td>					<td>0.00</td>					<td>0.00</td>					<td>39</td>					<td>0.00</td>				</tr>							<tr>					<td>38</td>					<td>主叫</td>					<td>本地</td>					<td>北京</td>					<td>01085192325</td>					<td>2016-04-06 09:54:33</td>					<td>0.00</td>					<td>0.00</td>					<td>94</td>					<td>0.00</td>				</tr>							<tr>					<td>39</td>					<td>被叫</td>					<td>本地</td>					<td>北京</td>					<td>18500190171</td>					<td>2016-04-06 14:32:27</td>					<td>0.00</td>					<td>0.00</td>					<td>33</td>					<td>0.00</td>				</tr>							<tr>					<td>40</td>					<td>被叫</td>					<td>本地</td>					<td>北京</td>					<td>13911795729</td>					<td>2016-04-06 21:21:56</td>					<td>0.00</td>					<td>0.00</td>					<td>36</td>					<td>0.00</td>				</tr>							<tr>					<td>41</td>					<td>被叫</td>					<td>本地</td>					<td>北京</td>					<td>18901185861</td>					<td>2016-04-07 14:32:36</td>					<td>0.00</td>					<td>0.00</td>					<td>45</td>					<td>0.00</td>				</tr>							<tr>					<td>42</td>					<td>主叫</td>					<td>国内长途</td>					<td>北京</td>					<td>13213631592</td>					<td>2016-04-08 21:46:25</td>					<td>0.00</td>					<td>0.00</td>					<td>140</td>					<td>0.00</td>				</tr>							<tr>					<td>43</td>					<td>主叫</td>					<td>本地</td>					<td>北京</td>					<td>18910270034</td>					<td>2016-04-09 11:46:47</td>					<td>0.00</td>					<td>0.00</td>					<td>11</td>					<td>0.00</td>				</tr>							<tr>					<td>44</td>					<td>被叫</td>					<td>本地</td>					<td>北京</td>					<td>18910270034</td>					<td>2016-04-09 12:02:12</td>					<td>0.00</td>					<td>0.00</td>					<td>26</td>					<td>0.00</td>				</tr>							<tr>					<td>45</td>					<td>主叫</td>					<td>国内长途</td>					<td>北京</td>					<td>13903881712</td>					<td>2016-04-09 14:09:31</td>					<td>0.00</td>					<td>0.00</td>					<td>174</td>					<td>0.00</td>				</tr>							<tr>					<td>46</td>					<td>被叫</td>					<td>本地</td>					<td>北京</td>					<td>18518099863</td>					<td>2016-04-09 16:09:26</td>					<td>0.00</td>					<td>0.00</td>					<td>50</td>					<td>0.00</td>				</tr>							<tr>					<td>47</td>					<td>被叫</td>					<td>本地</td>					<td>北京</td>					<td>18518099863</td>					<td>2016-04-09 16:19:21</td>					<td>0.00</td>					<td>0.00</td>					<td>17</td>					<td>0.00</td>				</tr>							<tr>					<td>48</td>					<td>被叫</td>					<td>本地</td>					<td>北京</td>					<td>18518099863</td>					<td>2016-04-09 17:30:57</td>					<td>0.00</td>					<td>0.00</td>					<td>17</td>					<td>0.00</td>				</tr>							<tr>					<td>49</td>					<td>主叫</td>					<td>本地</td>					<td>北京</td>					<td>18518099863</td>					<td>2016-04-09 17:31:36</td>					<td>0.00</td>					<td>0.00</td>					<td>23</td>					<td>0.00</td>				</tr>							<tr>					<td>50</td>					<td>被叫</td>					<td>本地</td>					<td>北京</td>					<td>18518099863</td>					<td>2016-04-09 18:04:57</td>					<td>0.00</td>					<td>0.00</td>					<td>40</td>					<td>0.00</td>				</tr>													<tr class=\"trlast\">		<td colspan=\"10\">			<div class=\"ued-page pd-10 clearfix\">						<label class=\"fl\">共<span class=\"color-6 fs-16\">189</span>条</label> 				<label class=\"fr\"> 											<a href='javascript:page(1)'>首页</a>																												<a href=\"javascript:void(0)\">1</a>																																																<a href=\"javascript:page(2)\" class=\"on\">2</a>																																				<a href=\"javascript:page(3)\" class=\"on\">3</a>																																				<a href=\"javascript:page(4)\" class=\"on\">4</a>											 											<a href='javascript:page(2)'>下一页</a>																<a href=\"javascript:page(4)\">尾页</a>									</label>			</div>		</td>	</tr>			<tr class=\"trlast\">			<td colspan=\"10\">				<div class=\"ued-page pd-10 clearfix\">							<label><a class=\"color-6 fs-16\" onclick='javascript:billDetailDownload();return false;'>下载详单</a></label> 				</div>			</td>		</tr>	</table><script type=\"text/javascript\">function page(page){	$.ajax({		type: \"POST\",		url: func_rootpath+\"/iframe/feequery/billDetailQuery.action\",		data: {			requestFlag: \"synchronization\",			billDetailType:$(\"#qryFlag\").val(),			qryMonth:$(\"#downloadQryDate\").val(),			startTime:$(\"#downloadStartTime\").val(),			endTime:$(\"#downloadEndTime\").val(),			accNum:$(\"#qryAccNo\").html(),			billPage:page		},		dataType: \"html\",		success: function(page){			$(\"#userDetailLst\").html(page);			unWait();			$(\"#userDetailLst\").show();			loadIframeHeight();// 重新加载iframe高度 只能在最后执行		},		error:function(){			unWait();			alert(\"对不起，详单记录查询失败，请稍后重试！\");		}	});}function billDetailDownload() {	$(\"#billDetailForm\").attr(\"action\",func_rootpath + \"/iframe/feequery/billDetailDownload.action\");	$(\"#billDetailForm\").submit();}</script></html>";
//		InputStream is = new ByteArrayInputStream(temp.getBytes("utf-8"));
//		DX_XPathReadMessage dx = new DX_XPathReadMessage();
//		dx.readResult(is);
//	}

}