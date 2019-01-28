<%@tag description="Overall Page template" pageEncoding="UTF-8"%>
<%@attribute name="header" fragment="true"%>
<%@attribute name="footer" fragment="true"%>
<%@ tag import="br.fatec.model.Usuario" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<link rel="stylesheet" href="/resources/css/estilos.css">
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<link rel="stylesheet"
		href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
		integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
		crossorigin="anonymous">
	<link rel="stylesheet"
		href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
		integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp"
		crossorigin="anonymous">
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
		integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
		crossorigin="anonymous"></script>
	<script
		src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.9/angular.min.js"></script>
	<script
		src='https://ajax.googleapis.com/ajax/libs/angularjs/1.6.9/angular-resource.js'></script>
	<jsp:invoke fragment="header" />
</head>
<body style="margin: 0px; padding: 0px; border-collapse: collapse;">
	<div class="menu" id="menu" style="height: auto;">
		<table align="center" width="1004px" border="0" cellpadding="0" cellspacing="0" style="width: 100%;">
			<tr style="background-color: #20507b;">
				<td class="topo"></td></tr>
				<tr>
				<td align="center" style="background-image: url('../resources/images/menu.png'); height: 45px;">
					<%Usuario usuario = (Usuario)session.getAttribute("user");%>
					<form action="/logout" method="post" style="float: right; padding-right: 10px;">
						&nbsp;&nbsp;<input type="submit" id="logoutButton" class="logout" value="Logout"></input>
					</form>
					<label style="float: right; color: #ffffff;">Logado como: <%= usuario.getLogin() %>  |</label>
				</td>
				</tr>
		</table>
	</div>
	<div id="body">
		<jsp:doBody />
	</div>
	<div id="pagefooter">
	<div class="rodape" name="rodape">
		<table align="center" style="width: 1004px;" >
			<tr>
				<td align="center">
					<hr style="width: 1004px" /> <label
					style="font-family: georgia; font-size: 13px">Copyright &#169;
						2018 CommentApp. Todos os direitos reservados.</label> <br />
					<hr style="width: 1004px" />
				</td>
			</tr>
		</table>
	</div>
</div>
</body>
</html>