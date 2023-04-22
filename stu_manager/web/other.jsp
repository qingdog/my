<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>二维码测试...</h2>
	<img src="./wxpay">
</body>

<!--[if lte IE 8]>//如果需要支持低版本的IE8及以下
<script  type="text/javascript" src="https://cdn.goeasy.io/json2.js"></script>
<![endif]-->
<script type="text/javascript" src="https://cdn.goeasy.io/goeasy-1.0.17.js"></script>
<script type="text/javascript">
var goEasy = new GoEasy({
host:'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
appkey: "BC-b232d753ee1d44fe92c249671bd87e18", //替换为您的应用appkey
});
//GoEasy-OTP可以对appkey进行有效保护,详情请参考​ ​

goEasy.subscribe({
	channel: "my_channel", //替换为您自己的channel
	onMessage: function (message) {
		console.log("Channel:" + message.channel + " content:" + message.content);
	}
});

</script>
</html>
